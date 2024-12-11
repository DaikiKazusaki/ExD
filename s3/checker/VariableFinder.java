package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class VariableFinder extends Visitor {
    private List<String> variableNames = new ArrayList<>();

    public List<String> getVariableNames() {
        return variableNames;
    }

    @Override
    public void visit(VariableName variableName) {
        // 変数名を収集
        variableNames.add(variableName.getVariableName()); 
    }
    
    public void printVariable() {
    	System.out.println(variableNames);
    }

    @Override
    public void visit(VariableDeclarationGroup variableDeclarationGroup) {
        for (VariableNameGroup variableNameGroup : variableDeclarationGroup.getVariableNameGroups()) {
            variableNameGroup.accept(this);
        }
    }

    @Override
    public void visit(VariableNameGroup variableNameGroup) {
        // 各変数名を訪問
        for (VariableName variableName : variableNameGroup.getVariableNames()) {
            variableName.accept(this);
        }
    }

    // 必要に応じて他の visit メソッドをオーバーライドする
}
