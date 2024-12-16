package enshud.s4.compiler;

public class SemanticException extends Exception {
	private static final long serialVersionUID = 1L;

	public SemanticException(String index) {
		super("Semantic Error: line " + index);
	}
}
