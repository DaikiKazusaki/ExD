package enshud.s3.checker;

public class ArrayType {
	private Integer minimumIndex;
	private Integer maximumIndex;
	private GeneralType generalType;
	
	public ArrayType(Integer minimumIndex, Integer maximumIndex, GeneralType generalType) {
		this.minimumIndex = minimumIndex;
		this.maximumIndex = maximumIndex;
		this.generalType = generalType;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		minimumIndex.accept(visitor);
		maximumIndex.accept(visitor);
		generalType.accept(visitor);
	}
}
