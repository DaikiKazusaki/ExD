package enshud.s4.compiler;

import java.util.List;

public class FormalParameterNameGroup implements Node {
    private List<FormalParameterName> formalParameterNameList;
    private String lineNum;

    public FormalParameterNameGroup(List<FormalParameterName> formalParameterNameList, String lineNum) {
        this.formalParameterNameList = formalParameterNameList;
        this.lineNum = lineNum;
    }

    public List<FormalParameterName> getFormalParameterNameList() {
        return formalParameterNameList;
    }
    
    public String getLineNum() {
    	return lineNum;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
