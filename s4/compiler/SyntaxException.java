package enshud.s4.compiler;

public class SyntaxException extends Exception {
	private static final long serialVersionUID = 1L;
	
    public SyntaxException (String lineNum) {
        super("Syntax error: line " + lineNum);
    }
}

