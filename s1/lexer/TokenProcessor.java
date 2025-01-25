package enshud.s1.lexer;

import java.util.ArrayList;
import java.util.List;

public class TokenProcessor {
    private final List<String> tokens;
    private final TokenRepository tokenRepository;
    private StringBuilder currentToken;

    public TokenProcessor(TokenRepository tokenRepository) {
        this.tokens = new ArrayList<>();
        this.tokenRepository = tokenRepository;
        this.currentToken = new StringBuilder();
    }

    public void addChar(char input) {
        currentToken.append(input);
    }

    public void processToken(State state, int lineNum) {
        if (currentToken.length() == 0) {
            return;
        } else if (currentToken.length() == 2 && state == State.OPERATOR) {
            String word = currentToken.toString();
            String[] tokenInfo = tokenRepository.getTokenInfo(word, state);
        	tokens.add(formatToken(word, tokenInfo, lineNum));
        	currentToken.setLength(0);
        }
        String word = currentToken.toString();
        String[] tokenInfo = tokenRepository.getTokenInfo(word, state);

        if (tokenInfo != null) {
            tokens.add(formatToken(word, tokenInfo, lineNum));
        } else if (state == State.LETTER) {
            tokens.add(formatToken(word, new String[]{"SIDENTIFIER", "43"}, lineNum));
        } else if (state == State.NUMBER) {
            tokens.add(formatToken(word, new String[]{"SCONSTANT", "44"}, lineNum));
        } else if (state == State.STRING) { // 文字列トークンの処理
            tokens.add(formatToken(word, new String[]{"SSTRING", "45"}, lineNum));
        }

        currentToken.setLength(0);
    }


    private String formatToken(String word, String[] tokenInfo, int lineNum) {
        return word + "\t" + tokenInfo[0] + "\t" + tokenInfo[1] + "\t" + lineNum;
    }

    public List<String> getTokenList() {
        return tokens;
    }
}
