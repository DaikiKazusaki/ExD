package enshud.s1.lexer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.IOException;

public class Lexer {
    private HashMap<String, String[]> tokens = new HashMap<>();
    private List<String> buffer = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private String outputFile;
    private String word = "";
    private State currentState;  // 現在の状態
    private Set<State> acceptStates;  // 受理状態
    private char[] operator = {'=', '<', '>' , ':'};

    /**
     * サンプルmainメソッド．
     * 単体テストの対象ではないので自由に改変しても良い．
     */
    public static void main(final String[] args) {
        System.out.println(new Lexer().run("data/pas/normal12.pas", "tmp/out1.ts"));
    }

    /**
     * 開発対象となるLexer実行メソッド．
     */
    public String run(final String inputFileName, final String outputFileName) {
        outputFile = outputFileName;
        try {
            buffer = Files.readAllLines(Paths.get(inputFileName));
            getChar();
            writeFile();
            return "OK";
        } catch (final IOException e) {
            return "File not found";
        }
    }

    // 状態の定義
    enum State {
        START, LETTER, NUMBER, OPERATOR, COMMENT, STRING, TOKEN
    }

    // コンストラクタ
    public Lexer() {
    	// トークンのセット
    	setToken();
    	
        // 初期状態はSTART
        currentState = State.START;

        // 受理状態はLETTER
        acceptStates = new HashSet<>();
        acceptStates.add(State.LETTER);
        acceptStates.add(State.NUMBER);
    }

    // 遷移関数
    public void transition(char input, int lineNum) {
        switch (currentState) {
            case START:
                if (Character.isLetter(input)) {
                    currentState = State.LETTER;
                    word += input; 
                } else if (Character.isDigit(input)) {
                    currentState = State.NUMBER;
                    word += input; 
                } else if (input == '{') {
                	processToken(lineNum);
                    currentState = State.COMMENT;
                    word = "";  // コメントはトークンに含まれない
                } else if (isOperator(input)) {
                    currentState = State.OPERATOR;
                    word += input;
                } else if (input == '\'') {
                    currentState = State.STRING;  // 文字列リテラル開始
                    word += input;
                } else if (tokens.containsKey(String.valueOf(input))){
                	currentState = State.TOKEN;
                	word += input;
                	processToken(lineNum);
                }
                break;

            case LETTER:
                if (Character.isLetterOrDigit(input)) {
                    word += input;
                } else if (input == '{') {
                	processToken(lineNum);
                    currentState = State.COMMENT;
                    word = "";  // コメントはトークンに含まれない
                } else {
                    processToken(lineNum);
                    currentState = State.START;
                    transition(input, lineNum);  // 新しい入力を処理
                }
                break;

            case NUMBER:
                if (Character.isDigit(input)) {
                    word += input;
                } else if (tokens.containsKey(String.valueOf(input))){
                	processToken(lineNum);
                	word += input;
                	currentState = State.TOKEN;
                } else if (input == '{') {
                	processToken(lineNum);
                    currentState = State.COMMENT;
                    word = "";  // コメントはトークンに含まれない
                } else {                
                    processToken(lineNum);  // 数値トークンの処理
                    currentState = State.START;
                    transition(input, lineNum);  // 新しい入力を処理
                }
                break;

            case OPERATOR:
            	if (tokens.containsKey(String.valueOf(input))) {
            		word += input;
            	} else if (input == '{') {
                	processToken(lineNum);
                    currentState = State.COMMENT;
                    word = "";  // コメントはトークンに含まれない
                } else {
            		processToken(lineNum);  // 演算子をトークンに変換
                    currentState = State.START;
            	}
                break;

            case COMMENT:
                if (input == '}') {
                    currentState = State.START;  // コメント終了
                }
                // コメント内は無視
                break;

            case STRING:
                if (input == '\'') {
                	word += input;
                    processToken(lineNum);  // 文字列リテラル終了
                	currentState = State.START;
                } else if (input == '{') {
                	processToken(lineNum);
                    currentState = State.COMMENT;
                    word = "";  // コメントはトークンに含まれない
                } else {
                    word += input;  // 文字列を継続
                }
                break;
                
            case TOKEN:
            	if (tokens.containsKey(word + input)) {
            		word += input;
            	} else if (tokens.containsKey(String.valueOf(input))) {
            		processToken(lineNum);
            		word += input;
        		} else if (input == '\''){
            		currentState = State.STRING;
            		word += input;
            	} else if (Character.isLetter(input)) {
            		processToken(lineNum);
            		word += input;
            		currentState = State.LETTER;
            	} else if (Character.isDigit(input)) {
            		processToken(lineNum);
            		word += input;
            		currentState = State.NUMBER;
            	} else if (input == '{') {
                	processToken(lineNum);
                    currentState = State.COMMENT;
                    word = "";  // コメントはトークンに含まれない
                } else {
            		processToken(lineNum);
            		if (!Character.isWhitespace(input)) {
            			word += input;
            		}
            		currentState = State.START;
            	}
            	break;
        }
    }

    /**
     * 字句をtokenに変換するメソッド
     * 
     * @param lineNum 行数
     */
    private void processToken(int lineNum) {
        if (!word.isEmpty()) {
        	if (currentState == State.NUMBER) {
                list.add(word + '\t' + "SCONSTANT" + '\t' + "44" + '\t' + lineNum);  // 数値
            } else if (currentState == State.STRING) {
                list.add(word + '\t' + "SSTRING" + '\t' + "45" + '\t' + lineNum);  // 文字列リテラルをトークンとして追加
            } else {
                String[] tokenInfo = returnTokens(word);
                if (tokenInfo != null) {
                	list.add(word + '\t' + tokenInfo[0] + '\t' + tokenInfo[1] + '\t' + lineNum);
                } else {
                	list.add(word + '\t' + "SIDENTIFIER" + '\t' + "43" + '\t' + lineNum);  // 識別子
                }
            }
            word = "";  // トークン処理後にクリア
        }
    }

    /**
     * 演算子の判定を行うメソッド
     * 
     * @param input bufferの(i, j)成分
     * @return　true 演算子の場合
     * @return false 演算子以外の場合
     */
    private boolean isOperator(char input) {
    	for (int i = 0; i < operator.length; i++) {
    		if (input == operator[i]) {
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * bufferの(i, j)成分をtransitionメソッドに渡す
     */
    private void getChar(){
        for (int i = 0; i < buffer.size(); i++) {
            String line = buffer.get(i);
            
            if (!line.isEmpty()) {
            	for (int j = 0; j < line.length(); j++) {
            		transition(line.charAt(j), i + 1);
                }
            }
            if (word != "") {
            	processToken(i + 1);
            }
        }
    }

    /**
     * ファイルに書き込みをするメソッド
     */
    private void writeFile() {
        try {
            // ファイルが存在しない場合は新規作成
            if (Files.notExists(Paths.get(outputFile))) {
                Files.createFile(Paths.get(outputFile));
            }

            // ファイルにリストの内容を書き込む
            Files.write(Paths.get(outputFile), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.clear();
    }

    /**
     * トークンを登録するメソッド
     */
    public void setToken() {
    	// データの登録
        tokens.put("and", new String[]{"SAND", "0"});
        tokens.put("array", new String[]{"SARRAY", "1"});
        tokens.put("begin", new String[]{"SBEGIN", "2"});
        tokens.put("boolean", new String[]{"SBOOLEAN", "3"});
        tokens.put("char", new String[]{"SCHAR", "4"});
        tokens.put("div", new String[]{"SDIVD", "5"});
        tokens.put("/", new String[] {"SDIVD", "5"});
        tokens.put("do", new String[]{"SDO", "6"});
        tokens.put("else", new String[]{"SELSE", "7"});
        tokens.put("end", new String[]{"SEND", "8"});
        tokens.put("false", new String[]{"SFALSE", "9"});
        tokens.put("if", new String[]{"SIF", "10"});
        tokens.put("integer", new String[]{"SINTEGER", "11"});
        tokens.put("mod", new String[]{"SMOD", "12"});
        tokens.put("not", new String[]{"SNOT", "13"});
        tokens.put("of", new String[]{"SOF", "14"});
        tokens.put("or", new String[]{"SOR", "15"});
        tokens.put("procedure", new String[]{"SPROCEDURE", "16"});
        tokens.put("program", new String[]{"SPROGRAM", "17"});
        tokens.put("readln", new String[]{"SREADLN", "18"});
        tokens.put("then", new String[]{"STHEN", "19"});
        tokens.put("true", new String[]{"STRUE", "20"});
        tokens.put("var", new String[]{"SVAR", "21"});
        tokens.put("while", new String[]{"SWHILE", "22"});
        tokens.put("writeln", new String[]{"SWRITELN", "23"});
        tokens.put("=", new String[]{"SEQUAL", "24"});
        tokens.put("<>", new String[]{"SNOTEQUAL", "25"});
        tokens.put("<", new String[]{"SLESS", "26"});
        tokens.put("<=", new String[]{"SLESSEQUAL", "27"});
        tokens.put(">=", new String[]{"SGREATEQUAL", "28"});
        tokens.put(">", new String[]{"SGREAT", "29"});
        tokens.put("+", new String[]{"SPLUS", "30"});
        tokens.put("-", new String[]{"SMINUS", "31"});
        tokens.put("*", new String[]{"SSTAR", "32"});
        tokens.put("(", new String[]{"SLPAREN", "33"});
        tokens.put(")", new String[]{"SRPAREN", "34"});
        tokens.put("[", new String[]{"SLBRACKET", "35"});
        tokens.put("]", new String[]{"SRBRACKET", "36"});
        tokens.put(";", new String[]{"SSEMICOLON", "37"});
        tokens.put(":", new String[]{"SCOLON", "38"});
        tokens.put("..", new String[]{"SRANGE", "39"});
        tokens.put(":=", new String[]{"SASSIGN", "40"});
        tokens.put(",", new String[]{"SCOMMA", "41"});
        tokens.put(".", new String[]{"SDOT", "42"});
    }

    /**
     * 字句に対応するトークンを返すメソッド
     * 
     * @param lexical　字句
     * @return　[token, id]
     */
    public String[] returnTokens(String lexical) {
        if (tokens.containsKey(lexical)) {
            return tokens.get(lexical); // 対応するトークンデータを返す
        } else {
            return null; // 見つからなかった場合はnullを返す
        }
    }
}
