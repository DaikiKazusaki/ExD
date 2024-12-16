package enshud.s3.checker;

public class VariableWithIndex implements Element {
	private VariableName variableName;
	private Index index;

	public VariableWithIndex(VariableName variableName, Index index) {
		this.variableName = variableName;
		this.index = index;
	}
	
	public VariableName getVariableName() {
		return variableName;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		variableName.accept(visitor);
		index.accept(visitor);
	}
	
}
