package enshud.s3.checker;

public class VariableWithIndex {
	private VariableName variablename;
	private Index index;

	public VariableWithIndex(VariableName variableName, Index index) {
		this.variablename = variableName;
		this.index = index;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		variablename.accept(visitor);
		index.accept(visitor);
	}
	
}
