package enshud.s4.compiler;

public interface Element {
	public abstract void accept(Visitor visitor) throws SemanticException;
}
