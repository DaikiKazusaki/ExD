package enshud.s3.checker;

public class VariableWithIndex implements Element {
	private VariableName variablename;
	private Index index;

	public VariableWithIndex(VariableName variableName, Index index) {
		this.variablename = variableName;
		this.index = index;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		variablename.accept(visitor);
		index.accept(visitor);
	}
	
}
