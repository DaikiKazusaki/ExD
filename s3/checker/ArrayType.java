package enshud.s3.checker;

public class ArrayType implements Element {
	private Int minimumIndex;
	private Int maximumIndex;
	private GeneralType generalType;
	private String lineNum;
	
	public ArrayType(Int minimumIndex, Int maximumIndex, GeneralType generalType, String lineNum) {
		this.minimumIndex = minimumIndex;
		this.maximumIndex = maximumIndex;
		this.generalType = generalType;
	}
	
	public Int getMinimumIndex() {
		return minimumIndex;
	}
	
	public Int getMaximumIndex() {
		return maximumIndex;
	}
	
	public GeneralType getGeneralType() {
		return generalType;
	}
	
	public String getLineNum() {
		return lineNum;
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
		minimumIndex.accept(visitor);
		maximumIndex.accept(visitor);
		generalType.accept(visitor);
	}
}
