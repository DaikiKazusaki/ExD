package enshud.s3.checker;

public class ArrayType implements Element {
	private Integer minimumIndex;
	private Integer maximumIndex;
	private GeneralType generalType;
	private String lineNum;
	
	public ArrayType(Integer minimumIndex, Integer maximumIndex, GeneralType generalType, String lineNum) {
		this.minimumIndex = minimumIndex;
		this.maximumIndex = maximumIndex;
		this.generalType = generalType;
	}
	
	public Integer getMinimumIndex() {
		return minimumIndex;
	}
	
	public Integer getMaximumIndex() {
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
