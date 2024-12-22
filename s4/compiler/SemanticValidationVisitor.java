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
    public void visit(ProgramName programName) {}
    
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
    public void visit(VariableName variableName) {}
    
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
    public void visit(StandardType standardType) {}
    
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
    public void visit(Sign sign) {}
    
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
    public void visit(ProcedureName procedureName) {}
    
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
    public void visit(FormalParameterNameGroup formalParameterNameGroup) throws SemanticException {
    	List<FormalParameterName> formalParameterNameList = formalParameterNameGroup.getFormalParameterNameList();
    	
    	for (FormalParameterName formalParameterName: formalParameterNameList) {
    		formalParameterName.accept(this);
    	}
    }
    
    @Override
    public void visit(FormalParameterName formalParameterName) {}
    
    @Override
    public void visit(ComplexStatement complexStatement) throws SemanticException {
    	StatementGroup statementGroup = complexStatement.getStatementGroup();
    	
    	statementGroup.accept(this);
    }
    
    @Override
    public void visit(StatementGroup statementGroup) throws SemanticException {
    	List<Statement> statementList = statementGroup.getStatementList();
    	
    	for (Statement statement: statementList) {
    		statement.accept(this);
    	}
    }
    
    @Override
    public void visit(Statement statement) throws SemanticException {
    	BasicStatement basicStatement = statement.getBasicStatement();
    	IfThen ifThen = statement.getIfThen();
    	WhileDo whileDo = statement.getWhileDo();
    	
    	if (basicStatement != null) {
    		basicStatement.accept(this);
    	} else if (ifThen != null) {
    		ifThen.accept(this);
    	} else if (whileDo != null) {
    		whileDo.accept(this);
    	}
    }
    
    @Override
    public void visit(IfThen ifThen) throws SemanticException {
    	Equation equation = ifThen.getEquation();
    	ComplexStatement complexStatement = ifThen.getComplexStatement();
    	
    	equation.accept(this);
    	complexStatement.accept(this);
    }
    
    @Override
    public void visit(ElseStatement elseStatement) throws SemanticException {
    	ComplexStatement complexStatement = elseStatement.getComplexStatement();
    	
    	complexStatement.accept(this);
    }
    
    @Override
    public void visit(WhileDo whileDo) throws SemanticException {
    	Equation equation = whileDo.getEquation();
    	ComplexStatement complexStatement = whileDo.getComplexStatement();
    	
    	equation.accept(this);
    	complexStatement.accept(this);
    }
    
    @Override
    public void visit(BasicStatement basicStatement) throws SemanticException {
    	AssignStatement assignStatement = basicStatement.getAssignStatement();
    	ProcedureCallStatement procedureCallStatement = basicStatement.getProcedureCallStatement();
    	InputOutputStatement inputOutputStatement = basicStatement.getInputOutputStatement();
    	ComplexStatement complexStatement = basicStatement.getComplexStatement();
    	
    	if (assignStatement != null) {
    		assignStatement.accept(this);
    	} else if (procedureCallStatement != null) {
    		procedureCallStatement.accept(this);
    	} else if (inputOutputStatement != null) {
    		inputOutputStatement.accept(this);
    	} else if (complexStatement != null) {
    		complexStatement.accept(this);
    	}
    } 
    
    @Override
    public void visit(AssignStatement assignStatement) throws SemanticException {
    	LeftSide leftSide = assignStatement.getLeftSide();
    	Equation equation = assignStatement.getEquation();
    	
    	leftSide.accept(this);
    	equation.accept(this);
    }
    
    @Override
    public void visit(LeftSide leftSide) throws SemanticException {
    	Variable variable = leftSide.getVariable();
    	
    	variable.accept(this);
    }
    
    @Override
    public void visit(Variable variable) throws SemanticException {
    	NaturalVariable naturalVariable = variable.getNaturalVariable();
    	VariableWithIndex variableWithIndex = variable.getVariableWithIndex();
    	
    	if (naturalVariable != null) {
    		naturalVariable.accept(this);
    	} else if (variableWithIndex != null) {
    		variableWithIndex.accept(this);
    	}
    }
    
    @Override
    public void visit(NaturalVariable naturalVariable) throws SemanticException {
    	VariableName variableName = naturalVariable.getVariableName();
    	
    	variableName.accept(this);
    }
    
    @Override
    public void visit(VariableWithIndex variableWithIndex) throws SemanticException {
    	VariableName variableName = variableWithIndex.getVariableName();
    	Index index = variableWithIndex.getIndex();
    	
    	variableName.accept(this);
    	index.accept(this);
    }
    
    @Override
    public void visit(Index index) throws SemanticException {
    	Equation equation = index.getEquation();
    	
    	equation.accept(this);
    }
    
    @Override
    public void visit(ProcedureCallStatement procedureCallStatement) throws SemanticException {
    	ProcedureName procedureName = procedureCallStatement.getProcedureName();
    	EquationGroup equationGroup = procedureCallStatement.getEquationGroup();
    	
    	procedureName.accept(this);
    	if (equationGroup != null) {
    		equationGroup.accept(this);
    	}
    }
    
    @Override
    public void visit(EquationGroup equationGroup) throws SemanticException {
    	List<Equation> equationList = equationGroup.getEquationList();
    	
    	for (Equation equation: equationList) {
    		equation.accept(this);
    	}
    }
    
    @Override
    public void visit(Equation equation) throws SemanticException {
    	List<SimpleEquation> simpleEquationList = equation.getSimpleEquationList();
    	List<RelationalOperator> relationalOperatorList = equation.getRelationalOperatorList();
    	
    	simpleEquationList.get(0).accept(this);
    	for (int i = 0; i < relationalOperatorList.size(); i++) {
    		relationalOperatorList.get(i).accept(this);
    		simpleEquationList.get(i + 1).accept(this);
    	}
    }
    
    @Override
    public void visit(SimpleEquation simpleEquation) throws SemanticException {
    	Sign sign = simpleEquation.getSign();
    	List<Term> termList = simpleEquation.getTermList();
    	List<AdditionalOperator> additionalOperatorList = simpleEquation.getAdditionalOperatorList();
    	
    	if (sign != null) {
    		sign.accept(this);
    	} 
    	termList.get(0).accept(this);
    	for (int i = 0; i < additionalOperatorList.size(); i++) {
    		additionalOperatorList.get(i).accept(this);
    		termList.get(i + 1).accept(this);
    	}
    }
    
    @Override
    public void visit(Term term) throws SemanticException {
    	List<Factor> factorList = term.getFactorList();
    	List<MultipleOperator> multipleOperatorList = term.getMultipleOperatorList();
    	
    	factorList.get(0).accept(this);
    	for (int i = 0; i < multipleOperatorList.size(); i++) {
    		multipleOperatorList.get(i).accept(this);
    		factorList.get(i + 1).accept(this);
    	}
    }
    
    @Override
    public void visit(Factor factor) throws SemanticException {
    	Variable variable = factor.getVariable();
    	Constant constant = factor.getConstant();
    	Equation equation = factor.getEquation();
    	Factor notFactor = factor.getFactor();
    	
    	if (variable != null) {
    		variable.accept(this);
    	} else if (constant != null) {
    		constant.accept(this);
    	} else if (equation != null) {
    		equation.accept(this);
    	} else if (notFactor != null) {
    		notFactor.accept(this);
    	}
    }
    
    @Override
    public void visit(RelationalOperator relationalOperator) {}
    
    @Override
    public void visit(AdditionalOperator additionalOperator) {}
    
    @Override
    public void visit(MultipleOperator multipleOperator) {}
    
    @Override
    public void visit(InputOutputStatement inputOutputStatement) throws SemanticException {
    	VariableGroup variableGroup = inputOutputStatement.getVariableGroup();
    	EquationGroup equationGroup = inputOutputStatement.getEquationGroup();
    	
    	if (variableGroup != null) {
    		variableGroup.accept(this);
    	} else if (equationGroup != null) {
    		equationGroup.accept(this);
    	}
    }
    
    @Override
    public void visit(VariableGroup variableGroup) throws SemanticException {
    	List<Variable> variableList = variableGroup.getVariableList();
    	
    	for (Variable variable: variableList) {
    		variable.accept(this);
    	}
    }
    
    @Override
    public void visit(Constant constant) {}
    
    @Override
    public void visit(UnsignedInteger unsignedInteger) {}
}
