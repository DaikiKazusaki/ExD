package enshud.s4.compiler;

import java.util.List;

public class FormalParameterNameGroup implements Node {
    private List<FormalParameterName> formalParameterNameList;

    public FormalParameterNameGroup(List<FormalParameterName> formalParameterNameList) {
        this.formalParameterNameList = formalParameterNameList;
    }

    public List<FormalParameterName> getFormalParameterNameList() {
        return formalParameterNameList;
    }

    @Override
    public void accept(Visitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
