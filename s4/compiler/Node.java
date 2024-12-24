package enshud.s4.compiler;

public interface Node {
	public abstract void accept(Visitor visitor) throws SemanticException;
}
