package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class SimpleEquation implements Element {
	private Sign sign;
	private Term term ;
	private List<AdditionalOperator> additionalOperator = new ArrayList<>();
	private List<Term> termList = new ArrayList<>();
	private String lineNum;
	
	public SimpleEquation(Sign sign, Term term, List<AdditionalOperator> additionalOperator, List<Term> termList, String lineNum) {
		this.sign = sign;
		this.term = term;
		this.additionalOperator = additionalOperator;
		this.termList = termList;
		this.lineNum = lineNum;
	}
	
	public String getLineNum() {
		return lineNum;
	}
	
	public Term getTerm(){
		return term;
	}
	
	public List<Term> getTermList() {
		return termList;
	}
	
	public Term getTerm1() {
		return termList.get(0);
	}
	
	@Override
	public void accept(Visitor visitor) throws SemanticException {	
		visitor.visit(this);	
		
		if (sign != null) {
			sign.accept(visitor);
		}
		
		term.accept(visitor);
		
		for (int i = 0; i < additionalOperator.size(); i++) {
			additionalOperator.get(i).accept(visitor);
			termList.get(i).accept(visitor);
		}
	}
}
