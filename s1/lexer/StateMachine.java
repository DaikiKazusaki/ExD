package enshud.s1.lexer;

public class StateMachine {
    private State currentState;
    private final TokenProcessor tokenProcessor;

    public StateMachine(TokenProcessor tokenProcessor) {
        this.currentState = State.START;
        this.tokenProcessor = tokenProcessor;
    }

    public void transition(char input, int lineNum) {
        currentState = switch (currentState) {
            case START -> handleStart(input, lineNum);
            case LETTER -> handleLetter(input, lineNum);
            case NUMBER -> handleNumber(input, lineNum);
            case OPERATOR -> handleOperator(input, lineNum);
            case COMMENT -> handleComment(input);
            case STRING -> handleString(input, lineNum);
        };
    }

    public void endOfLine(int lineNum) {
        tokenProcessor.processToken(currentState, lineNum);
        currentState = State.START;
    }

    private State handleStart(char input, int lineNum) {
        if (Character.isLetter(input)) {
            tokenProcessor.addChar(input);
            return State.LETTER;
        } else if (Character.isDigit(input)) {
            tokenProcessor.addChar(input);
            return State.NUMBER;
        } else if (input == '{') {
            return State.COMMENT;
        } else if (input == '\'') {
            tokenProcessor.addChar(input);
            return State.STRING;
        } else if (Utils.isOperator(input)) {
            tokenProcessor.addChar(input);
            return State.OPERATOR;
        } else {
            tokenProcessor.addChar(input);
            tokenProcessor.processToken(State.START, lineNum);
            return State.START;
        }
    }

    private State handleLetter(char input, int lineNum) {
        if (Character.isLetterOrDigit(input)) {
            tokenProcessor.addChar(input);
            return State.LETTER;
        } else {
            tokenProcessor.processToken(State.LETTER, lineNum);
            return handleStart(input, lineNum);
        }
    }

    private State handleNumber(char input, int lineNum) {
        if (Character.isDigit(input)) {
            tokenProcessor.addChar(input);
            return State.NUMBER;
        } else {
            tokenProcessor.processToken(State.NUMBER, lineNum);
            return handleStart(input, lineNum);
        }
    }

    private State handleOperator(char input, int lineNum) {
        if (Utils.isOperator(input)) {
            tokenProcessor.addChar(input);
            return State.OPERATOR;
        } else {
            tokenProcessor.processToken(State.OPERATOR, lineNum);
            return handleStart(input, lineNum);
        }
    }

    private State handleComment(char input) {
        return (input == '}') ? State.START : State.COMMENT;
    }

    private State handleString(char input, int lineNum) {
        tokenProcessor.addChar(input);
        if (input == '\'') { // 文字列の終端を検知
            tokenProcessor.processToken(State.STRING, lineNum); // トークン処理
            return State.START; // 状態をリセット
        }
        return State.STRING;
    }

}

