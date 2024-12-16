package enshud.s3.checker;

public class Type implements Element {
	private GeneralType generalType;
	private ArrayType arrayType;
	
	public Type(GeneralType generalType, ArrayType arrayType) {
		this.generalType = generalType;
		this.arrayType = arrayType;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		
		if (generalType != null) {
			generalType.accept(visitor);
		} else {
			arrayType.accept(visitor);
		}
	}
	
	public GeneralType getGeneralType() {
		return generalType;
	}
	
	public ArrayType getArrayType() {
		return arrayType;
	}
}
