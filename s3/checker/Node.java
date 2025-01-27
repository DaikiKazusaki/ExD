package enshud.s3.checker;

public interface Node {
	public abstract void accept(Visitor visitor) throws SemanticException;
}