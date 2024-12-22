package enshud.s4.compiler;

import java.util.List;

public class SemanticValidationVisitor extends Visitor {
    @Override
    public void visit(Program program) throws SemanticException {
    	ProgramName programName = program.getProgramName();
    	Block block = program.getBlock();
    	ComplexStatement complexStatement = program.getComplexStatement();
    	
    	programName.accept(this);
    	block.accept(this);
    	complexStatement.accept(this);
    }
    
    @Override
    public void visit(ProgramName programName) {
    	String name = programName.getProgramName();
    }
    
    @Override
    public void visit(Block block) throws SemanticException {
    	VariableDeclaration variableDeclaration = block.getVariableDeclaration();
    	SubprogramDeclarationGroup subprogramDeclarationGroup = block.getSubprogramDeclarationGroup();
    	
    	variableDeclaration.accept(this);
    	subprogramDeclarationGroup.accept(this);
    }
    
    @Override
    public void visit(VariableDeclaration variableDeclaration) throws SemanticException {
    	VariableDeclarationGroup variableDeclarationGroup = variableDeclaration.getVariableDeclarationGroup();
    	
    	variableDeclarationGroup.accept(this);
    }
    
    @Override
    public void visit(VariableDeclarationGroup variableDeclarationGroup) throws SemanticException {
    	List<VariableNameGroup> variableNameGroupList = variableDeclarationGroup.getVariableNameGroupList();
    	
    	for (VariableNameGroup variableNameGroup : variableNameGroupList) {
            variableNameGroup.accept(this);
        }
    }
    
    @Override
    public void visit(VariableNameGroup variableNameGroup) throws SemanticException {
    	List<VariableName> variableNameList = variableNameGroup.getVariableNameList();
    	
    	for (VariableName variableName: variableNameList) {
    		variableName.accept(this);
    	}
    }
    
    @Override
    public void visit(VariableName variableName) {
    	String name = variableName.getVariableName();
    }
    
    @Override
    public void visit(Type type) throws SemanticException {
    	StandardType standardType = type.getStandardType();
    	ArrayType arrayType = type.getArrayType();
    	
    	if (standardType != null) {
    		standardType.accept(this);
    	} else if (arrayType != null) {
    		arrayType.accept(this);
    	}
    }
    
    @Override
    public void visit(StandardType standardType) {
    	String type = standardType.getStandardType();
    }
    
    @Override
    public void visit(ArrayType arrayType) throws SemanticException {
    	Int minimumInteger = arrayType.getMinimumInteger();
    	Int maximumInteger = arrayType.getMinimumInteger();
    	StandardType standardType = arrayType.getStandardType();
    	
    	minimumInteger.accept(this);
    	maximumInteger.accept(this);
    	standardType.accept(this);
    }
    
    @Override
    public void visit(Int integer) throws SemanticException {
    	Sign sign = integer.getSign();
    	UnsignedInteger unsignedInteger = integer.getUnsignedInteger();
    	
    	if (sign != null) {
    		sign.accept(this);
    	}
    	unsignedInteger.accept(this);
    }
    
    @Override
    public void visit(Sign sign) {
    	String name = sign.getSign();
    }
    
    @Override
    public void visit(SubprogramDeclarationGroup subprogramDeclarationGroup) throws SemanticException {
    	List<SubprogramDeclaration> subprogramDeclarationList = subprogramDeclarationGroup.getSubprogramDeclaration();
    	
    	for (SubprogramDeclaration subprogramDeclaration: subprogramDeclarationList) {
    		subprogramDeclaration.accept(this);
    	}
    }
    
    @Override
    public void visit(SubprogramDeclaration subprogramDeclaration) throws SemanticException {
    	SubprogramHead subprogramHead = subprogramDeclaration.getSubprogramHead();
    	VariableDeclaration variableDeclaration = subprogramDeclaration.getVariableDeclaration();
    	ComplexStatement complexStatement = subprogramDeclaration.getComplexStatement();
    	
    	subprogramHead.accept(this);
    	variableDeclaration.accept(this);
    	complexStatement.accept(this);
    }
    
    @Override
    public void visit(SubprogramHead subprogramHead) throws SemanticException {
    	ProcedureName procedureName = subprogramHead.getProcedureName();
    	FormalParameter formalParameter = subprogramHead.getFormalParameter();
    	
    	procedureName.accept(this);
    	formalParameter.accept(this);
    }
    
    @Override
    public void visit(ProcedureName procedureName) {
    	String name = procedureName.getProcedureName();
    }
    
    @Override
    public void visit(FormalParameter formalParameter) throws SemanticException {
    	FormalParameterGroup formalParameterGroup = formalParameter.getFormalParameterGroup();
    	
    	formalParameterGroup.accept(this);
    }
    
    @Override
    public void visit(FormalParameterGroup formalParameterGroup) throws SemanticException {
    	List<FormalParameterNameGroup> formalParameterNameGroupList = formalParameterGroup.getFormalParameterNameGroupList();
    	List<StandardType> standardTypeList = formalParameterGroup.getStandardTypeList();
    	
    	for (int i = 0; i < formalParameterNameGroupList.size(); i++) {
    		FormalParameterNameGroup formalParameterNameGroup = formalParameterNameGroupList.get(i);
    		StandardType standardType = standardTypeList.get(i);
    		
    		formalParameterNameGroup.accept(this);
    		standardType.accept(this);
    	}
    }
    
    @Override
    public void visit(FormalParameterNameGroup formalParameterNameGroup) {}
    
    @Override
    public void visit(FormalParameterName formalParameterName) {}
    
    @Override
    public void visit(ComplexStatement complexStatement) {}
    
    @Override
    public void visit(StatementGroup statementGroup) {}
    
    @Override
    public void visit(Statement statement) {}
    
    @Override
    public void visit(IfThen ifThen) {}
    
    @Override
    public void visit(ElseStatement elseStatement) {}
    
    @Override
    public void visit(WhileDo whileDo) {}
    
    @Override
    public void visit(BasicStatement basicStatement) {}
    
    @Override
    public void visit(AssignStatement assignStatement) {}
    
    @Override
    public void visit(LeftSide leftSide) {}
    
    @Override
    public void visit(Variable variable) {}
    
    @Override
    public void visit(NaturalVariable naturalVariable) {}
    
    @Override
    public void visit(VariableWithIndex variableWithIndex) {}
    
    @Override
    public void visit(Index index) {}
    
    @Override
    public void visit(ProcedureCallStatement procedureCallStatement) {}
    
    @Override
    public void visit(EquationGroup equationGroup) {}
    
    @Override
    public void visit(Equation equation) {}
    
    @Override
    public void visit(SimpleEquation simpleEquation) {}
    
    @Override
    public void visit(Term term) {}
    
    @Override
    public void visit(Factor factor) {}
    
    @Override
    public void visit(RelationalOperator relationalOperator) {}
    
    @Override
    public void visit(AdditionalOperator additionalOperator) {}
    
    @Override
    public void visit(MultipleOperator multipleOperator) {}
    
    @Override
    public void visit(InputOutputStatement inputOutputStatement) {}
    
    @Override
    public void visit(VariableGroup variableGroup) {}
    
    @Override
    public void visit(Constant constant) {}
    
    @Override
    public void visit(UnsignedInteger unsignedInteger) {}
}
