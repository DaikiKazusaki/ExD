package enshud.s3.checker;

public class Type {
	private GeneralType generalType;
	private ArrayType arrayType;
	
	public Type(GeneralType generalType, ArrayType arrayType) {
		this.generalType = generalType;
		this.arrayType = arrayType;
	}
	
	// @Override
	public void accept(Visitor visitor) {
		if (generalType != null) {
			generalType.accept(visitor);
		} else {
			arrayType.accept(visitor);
		}
	}
}
