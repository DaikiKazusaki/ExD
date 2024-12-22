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
    public void visit(VariableDeclarationGroup variableDeclarationGroup) {
    	List<VariableNameGroup> variableNameGroupList = variableDeclarationGroup.getVariableNameGroupList();
    	
    	// variableNameGroupList.accept(this);
    }
    
    @Override
    public void visit(VariableNameGroup variableNameGroup) {}
    @Override
    public void visit(VariableName variableName) {}
    @Override
    public void visit(Type type) {}
    @Override
    public void visit(StandardType standardType) {}
    @Override
    public void visit(ArrayType arrayType) {}
    @Override
    public void visit(Int integer) {}
    @Override
    public void visit(Sign sign) {}
    @Override
    public void visit(SubprogramDeclarationGroup subprogramDeclarationGroup) {}
    @Override
    public void visit(SubprogramDeclaration subprogramDeclaration) {}
    @Override
    public void visit(SubprogramHead subprogramHead) {}
    @Override
    public void visit(ProcedureName procedureName) {}
    @Override
    public void visit(FormalParameter formalParameter) {}
    @Override
    public void visit(FormalParameterGroup formalParameterGroup) {}
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
