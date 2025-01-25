package enshud.s1.lexer;

import java.util.HashMap;
import java.util.Map;

public class TokenRepository {
    private final Map<String, String[]> tokens;

    public TokenRepository() {
        tokens = new HashMap<>();
        initializeTokens();
    }

    private void initializeTokens() {
        tokens.put("and", new String[]{"SAND", "0"});
        tokens.put("array", new String[]{"SARRAY", "1"});
        tokens.put("begin", new String[]{"SBEGIN", "2"});
        tokens.put("boolean", new String[]{"SBOOLEAN", "3"});
        tokens.put("char", new String[]{"SCHAR", "4"});
        tokens.put("div", new String[]{"SDIVD", "5"});
        tokens.put("/", new String[]{"SDIVD", "5"});
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

    public String[] getTokenInfo(String word, State state) {
        return tokens.get(word);
    }
}
