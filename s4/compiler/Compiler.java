package enshud.s4.compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enshud.casl.CaslSimulator;

public class Compiler {
	/**
	 * サンプルmainメソッド．
	 * 単体テストの対象ではないので自由に改変しても良い．
	 * 
	 * @throws SemanticException 
	 * @throws SyntaxException 
	 */
	public static void main(final String[] args) throws SyntaxException, SemanticException {
		// Compilerを実行してcasを生成する
		System.out.println(new Compiler().run("data/ts/normal02.ts", "tmp/out.cas"));

		// 上記casを，CASLアセンブラ & COMETシミュレータで実行する
		CaslSimulator.run("tmp/out.cas", "tmp/out.ans");
	}

	/**
	 * TODO
	 * 
	 * 開発対象となるCompiler実行メソッド．
	 * 以下の仕様を満たすこと．
	 * 
	 * 仕様:
	 * 第一引数で指定されたtsファイルを読み込み，CASL IIプログラムにコンパイルする．
	 * コンパイル結果のCASL IIプログラムは第二引数で指定されたcasファイルに書き出し"OK"という文字列を返すこと．
	 * 構文的もしくは意味的なエラーを発見した場合はエラーメッセージを返すこと．
	 * （エラーメッセージの内容はChecker.run()の出力に準ずる．）
	 * 入力ファイルが見つからない場合は"File not found"を返すこと．
	 * 
	 * @param inputFileName 入力tsファイル名
	 * @param outputFileName 出力casファイル名
	 * @throws SyntaxException 
	 * @throws SemanticException 
	 */
	public String run(final String inputFileName, final String outputFileName) {
		try {
			// ファイルの内容を解析
			List<String> buffer = Files.readAllLines(Paths.get(inputFileName)); 			
			List<List<String>> tokenList = new ArrayList<>();
			
	        for (int i = 0; i < buffer.size(); i++) {
	            String[] line = buffer.get(i).split("\t");
	            tokenList.add(Arrays.asList(line));
	        }
            
            // 構文解析
	        Program program = new Parser(tokenList).program();
            
            // 意味解析
	        SemanticValidationVisitor semanticValidationVisitor = new SemanticValidationVisitor();
            program.accept(semanticValidationVisitor);           
            
            // casファイルを作成
            CompilationVisitor compilationVisitor = new CompilationVisitor(semanticValidationVisitor);
            program.accept(compilationVisitor);
            List<String> statementList = compilationVisitor.getOutputStatementList();
            writeFile(statementList, compilationVisitor, outputFileName);
            
            // コンパイルが終了したら"OK"を返す
            return "OK"; 
        } catch (final IOException e) {
            return "File not found";
        } catch (final SyntaxException e) {
        	return e.getMessage();
        } catch (final SemanticException e) {
        	return e.getMessage();
        }
	}
	
	/**
	 * ファイルに書き込むメソッド
	 * 
	 * @param statementList
	 * @param outputFileName
	 */
	public void writeFile(List<String> statementList, final CompilationVisitor compilationVisitor, final String outputFileName) {
		// CASLの最後に必要な要素を追加
		statementList.add("LIBBUF" + '\t' + "DS" + '\t' + "256");
		statementList.add('\t' + "END");

		// lib.casを記載
		boolean[] isNecessaryOfLib = compilationVisitor.getIsNessesaryOfLib();
		List<String> libStatementList = addLibToStatement(isNecessaryOfLib);
		statementList.addAll(libStatementList);
		
		try{
			// ファイルが存在しない場合は新規作成
            if (Files.notExists(Paths.get(outputFileName))) {
                Files.createFile(Paths.get(outputFileName));
            }
            
            // ファイル書き込み
            Files.write(Paths.get(outputFileName), statementList);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 
	 * 
	 * @param isNecessaryOfLib
	 * @return
	 */
	public List<String> addLibToStatement(boolean[] isNecessaryOfLib){
		Lib lib = new Lib();
		List<String> libStatement = new ArrayList<>();
		
		if (isNecessaryOfLib[0] == true) {
			libStatement.addAll(lib.getMULT());
		}
		if (isNecessaryOfLib[1] == true) {
			libStatement.addAll(lib.getDIV());
		}
		if (isNecessaryOfLib[2] == true) {
			libStatement.addAll(lib.getRDINT());
		}
		if (isNecessaryOfLib[3] == true) {
			libStatement.addAll(lib.getRDCH());
		}
		if (isNecessaryOfLib[4] == true) {
			libStatement.addAll(lib.getRDSTR());
		}
		if (isNecessaryOfLib[5] == true) {
			libStatement.addAll(lib.getRDLN());
		}
		if (isNecessaryOfLib[6] == true) {
			libStatement.addAll(lib.getWRTINT());
		}
		if (isNecessaryOfLib[7] == true) {
			libStatement.addAll(lib.getWRTCH());
		}
		if (isNecessaryOfLib[8] == true) {
			libStatement.addAll(lib.getWRTSTR());
		}
		if (isNecessaryOfLib[9] == true) {
			libStatement.addAll(lib.getWRTLN());
		}
		
		return libStatement;
	}
}
