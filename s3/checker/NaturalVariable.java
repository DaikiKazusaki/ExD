package enshud.s3.checker;

public class NaturalVariable implements Element {
	private VariableName variableName;
	
	public NaturalVariable(VariableName variableName) {
		this.variableName = variableName;
	} 
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		variableName.accept(visitor);
	}
}
