package enshud.s3.checker;

public class NaturalVariable {
	private VariableName variableName;
	
	public NaturalVariable(VariableName variableName) {
		this.variableName = variableName;
	} 
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		variableName.accept(visitor);
	}
}
