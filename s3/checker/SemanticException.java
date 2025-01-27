package enshud.s3.checker;

public class SemanticException extends Exception {
	private static final long serialVersionUID = 1L;

	public SemanticException(String index) {
		super("Semantic error: line " + index);
	}
}
