package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private int tokenIndex = 0;
    private int LEXICALCOLS = 0;
    private int TOKENCOLS = 1;
    private List<List<String>> tokens = new ArrayList<>();
    private SyntaxException e;

    public Parser(List<List<String>> tokens) {
        this.tokens = tokens;
        e = new SyntaxException(tokens);
    }

    /**
     * プログラム
     * 
     */
    public Program program() throws SyntaxException {
    	// "program"の判定
        checkToken("SPROGRAM");

        // プログラム名の判定
        ProgramName programName = programName();

        // ";"の判定
        checkToken("SSEMICOLON");
        
        // ブロックの判定
        Block block = block();
        
        // 複合文の判定
        ComplexStatement complexStatement = complexStatement();
        
        // "."の判定
        checkToken("SDOT");
        
        return new Program(programName, block, complexStatement); 
    }
    
    /**
     * トークンが正しいことを判定するメソッド
     * 
     */
    public void checkToken(String token) throws SyntaxException {
    	if (tokens.get(tokenIndex).get(TOKENCOLS).equals(token)) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    }
    
    /**
     * トークンを取得するメソッド
     * 
     */
    public String getToken(int lineNum) {
    	return tokens.get(lineNum).get(TOKENCOLS);
    }
    
    /**
     * 字句を取得するメソッド
     * 
     */
    public String getLexicality(int lineNum) {
    	return tokens.get(lineNum).get(LEXICALCOLS);
    }
    
    /**
     * プログラム名の判定
     * 
     */
    public ProgramName programName() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SIDENTIFIER")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new ProgramName(getLexicality(tokenIndex - 1));
    }
    
    /**
     * ブロックの判定
     * 
     */
    public Block block() throws SyntaxException {
    	VariableDeclaration variableDeclaration = variableDeclaration();    	
    	SubprogramDeclarationGroup subprogramDeclarationGroup = subprogramDeclarationGroup();
    	
    	return new Block(variableDeclaration, subprogramDeclarationGroup);
    }
    
    /**
     * 変数宣言
     * 
     */
    public VariableDeclaration variableDeclaration() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	VariableDeclarationGroup variableDeclarationGroup = null;
    	
    	if (token.equals("SVAR")) {
    		tokenIndex++;
    		variableDeclarationGroup = variableDeclarationGroup();
    	}
    	
    	return new VariableDeclaration(variableDeclarationGroup);
    }
    
    /**
     * 変数宣言の並び
     * 
     */
    public VariableDeclarationGroup variableDeclarationGroup() throws SyntaxException {
    	VariableNameGroup variableNameGroup1 = variableNameGroup();
    	
    	// ":"の判定
    	checkToken("SCOLON");
    	
    	Type type1 = type();
    	
    	// ";"の判定
    	checkToken("SSEMICOLON");
    	
    	List<VariableNameGroup> variableNameGroup2 = new ArrayList<>();
    	List<Type> type2 = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SIDENTIFIER")) {
    		variableNameGroup2.add(variableNameGroup());
    		
    		// ":"の判定
        	checkToken("SCOLON");
        	
        	type2.add(type());
        	
        	// ";"の判定
        	checkToken("SSEMICOLON");
    	}
    	
    	return new VariableDeclarationGroup(variableNameGroup1, type1, variableNameGroup2, type2);
    }
    
    /**
     * 変数名の並び
     * 
     */
    public VariableNameGroup variableNameGroup() throws SyntaxException {
    	VariableName variableName1 = variableName();
    	List<VariableName> variableName2 = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SCOMMA")) {
    		tokenIndex++;
    		variableName2.add(variableName());
    	}
    	
    	return new VariableNameGroup(variableName1, variableName2);
    }
    
    /**
     * 変数名の判定
     * 
     */
    public VariableName variableName() throws SyntaxException {
    	if (getToken(tokenIndex).equals("SIDENTIFIER")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new VariableName(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 型の判定
     * 
     */
    public Type type() throws SyntaxException {
    	GeneralType generalType = null;
    	ArrayType arrayType = null;
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SINTEGER") || token.equals("SCHAR") || token.equals("SBOOLEAN")) {
    		generalType = generalType();
    	} else if (token.equals("SARRAY")) {
    		tokenIndex++;
    		arrayType = arrayType();
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new Type(generalType, arrayType);
    }
    
    /**
     * 標準型の判定
     * 
     */
    public GeneralType generalType() throws SyntaxException {
    	tokenIndex++;
    	return new GeneralType(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 配列型の判定
     * 
     */
    public ArrayType arrayType() throws SyntaxException {
    	// "["の判定
    	checkToken("SLBRACKET");
    	
    	// 添え字の最小値
    	Integer minimumInteger = integer();
    	
    	// ".."の判定
    	checkToken("SRANGE");
    	
    	// 添え字の最大値
    	Integer maximumInteger = integer();
    	
    	// "]"の判定
    	checkToken("SRBRACKET");
    	
    	// "of"の判定
    	checkToken("SOF");
    	
    	// 標準型の判定
    	GeneralType generalType = generalType();
    	
    	return new ArrayType(minimumInteger, maximumInteger, generalType);
    }
    
    /**
     * 整数の判定
     * 
     */
    public Integer integer() throws SyntaxException {
    	Sign sign = null;
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SPLUS") || token.equals("SMINUS")) {
    		sign = sign();
    	} 
    	
    	UnsignedInteger unsignedInteger = unsignedInteger();
    	
    	return new Integer(sign, unsignedInteger);
    }
    
    /**
     * 符号の判定
     * 
     */
    public Sign sign() throws SyntaxException {
    	tokenIndex++;
    	return new Sign(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 副プログラム宣言群の判定
     * 
     */
    public SubprogramDeclarationGroup subprogramDeclarationGroup() throws SyntaxException {
    	List<SubprogramDeclaration> subprogramDeclaration = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SPROCEDURE")) {    		
    		subprogramDeclaration.add(subprogramDeclaration());
    		
    		// ";"の判定
    		checkToken("SSEMICOLON");
    	}
    	
    	return new SubprogramDeclarationGroup(subprogramDeclaration);
    }
    
    /**
     * 副プログラム宣言
     * 
     */
    public SubprogramDeclaration subprogramDeclaration() throws SyntaxException {
    	// 副プログラム頭部の判定
    	SubprogramHead subprogramHead = subprogramHead();
    	
    	// 変数宣言の判定
    	VariableDeclaration variableDeclaration = variableDeclaration();
    	
    	// 複合文の判定
    	ComplexStatement complexStatement = complexStatement();
    	
    	return new SubprogramDeclaration(subprogramHead, variableDeclaration, complexStatement);
    }
    
    /**
     * 副プログラム頭部
     * 
     */
    public SubprogramHead subprogramHead() throws SyntaxException {
    	// "procedure"の判定
    	checkToken("SPROCEDURE");
    	
    	// 手続き名の判定
    	ProcedureName procedureName = procedureName();
    	
    	// 仮パラメータ名の判定
    	FormalParameter formalParameter = formalParameter();
    	
    	// ";"の判定
    	checkToken("SSEMICOLON");
    	
    	return new SubprogramHead(procedureName, formalParameter);
    }
    
    /**
     * 手続き名
     * 
     */
    public ProcedureName procedureName() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SIDENTIFIER")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new ProcedureName(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 仮パラメータ
     * 
     */
    public FormalParameter formalParameter() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	FormalParameterGroup formalParameterGroup = null;
    	
    	if (token.equals("SLPAREN")) {
    		tokenIndex++;
    		formalParameterGroup = formalParameterGroup();
    		checkToken("SRPAREN");
    	}
    	
    	return new FormalParameter(formalParameterGroup);
    }
    
    /**
     * 仮パラメータの並び
     * 
     */
    public FormalParameterGroup formalParameterGroup() throws SyntaxException {
    	FormalParameterNameGroup formalParameterNameGroup1 = formalParameterNameGroup();
    	
    	// ":"の判定
    	checkToken("SCOLON");
    	
    	GeneralType generalType1 = generalType();
    	
    	List<FormalParameterNameGroup> formalParameterNameGroup2 = new ArrayList<>();
    	List<GeneralType> generalType2 = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SCOMMA")) {
    		tokenIndex++;
    		formalParameterNameGroup2.add(formalParameterNameGroup());
    		
    		// ":"の判定
    		checkToken("SCOLON");
    		
    		generalType2.add(generalType());
    	}
    	
    	
    	return new FormalParameterGroup(formalParameterNameGroup1, generalType1, formalParameterNameGroup2, generalType2);
    }
    
    /**
     * 仮パラメータ名の並び
     * 
     */
    public FormalParameterNameGroup formalParameterNameGroup() throws SyntaxException {
    	FormalParameterName formalParameterName1 = formalParameterName();
    	List<FormalParameterName> formalParameterName2 = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SCOMMA")) {
    		tokenIndex++;
    		formalParameterName2.add(formalParameterName());
    	}
    	
    	return new FormalParameterNameGroup(formalParameterName1, formalParameterName2);
    }
    
    /**
     * 仮パラメータ名
     * 
     */
    public FormalParameterName formalParameterName() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SIDENTIFIER")) {
    		tokenIndex++;
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new FormalParameterName(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 複合文の判定
     * 
     */
    public ComplexStatement complexStatement() throws SyntaxException {
    	// "begin"の判定
    	checkToken("SBEGIN");
    	
    	// 文の並び
    	StatementGroup statementGroup = statementGroup();
    	
    	// "end"の判定
    	checkToken("SEND");
    	
    	return new ComplexStatement(statementGroup);	
    }
    
    /**
     * 文の並びの判定
     * 
     */
    public StatementGroup statementGroup() throws SyntaxException {
    	// 文の判定
    	Statement statement1 = statement();
    	
    	// ";"の判定
    	checkToken("SSEMICOLON");
    	
    	List<Statement> statement2 = new ArrayList<>();
    	// 繰り返しの判定
    	while (getToken(tokenIndex).equals("SIDENTIFIER") || getToken(tokenIndex).equals("SREADLN") || getToken(tokenIndex).equals("SWRITELN") || getToken(tokenIndex).equals("SBEGIN") || getToken(tokenIndex).equals("SIF") || getToken(tokenIndex).equals("SWHILE")) {
    		statement2.add(statement());
    		
    		// ";"の判定
        	checkToken("SSEMICOLON");
    	}
    	
    	return new StatementGroup(statement1, statement2);
    }
    
    /**
     * 文の判定
     * 
     */
    public Statement statement() throws SyntaxException {
    	BasicStatement basicStatement = null;
    	IfThen ifThen = null;
    	WhileDo whileDo = null;
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SIDENTIFIER") || token.equals("SREADLN") || token.equals("SWRITELN") || token.equals("SBEGIN")) {
    		basicStatement = basicStatement();
    	} else if (token.equals("SIF")) {
    		tokenIndex++;
    		ifThen = ifThen();
    	} else if (token.equals("SWHILE")) {
    		tokenIndex++;
    		whileDo = whileDo();
    	}
    	
    	return new Statement(basicStatement, ifThen, whileDo);
    }
    
    /**
     * if-thenの判定
     * 
     */
    public IfThen ifThen() throws SyntaxException {
    	Equation equation = equation();
    	
    	// "then"の判定
    	checkToken("STHEN");
    	
    	ComplexStatement complexStatement = complexStatement();
    	
    	Else Else = null;
    	if (getToken(tokenIndex).equals("SELSE")) {
    		Else = Else();
    	}
    	
    	return new IfThen(equation, complexStatement, Else);
    }
    
    /**
     * elseの判定
     * 
     */
    public Else Else() throws SyntaxException {
    	tokenIndex++;
    	ComplexStatement complexStatement = complexStatement();
    	
    	return new Else(complexStatement);
    }
    
    /**
     * while-doの判定
     * 
     */
    public WhileDo whileDo() throws SyntaxException {
    	Equation equation = equation();
    	
    	// "do"の判定
    	checkToken("SDO");
    	
    	ComplexStatement complexStatement = complexStatement();
    	
    	return new WhileDo(equation, complexStatement);
    }
    
    /**
     * 基本文の判定
     * 
     */
    public BasicStatement basicStatement() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	AssignStatement assignStatement = null;
    	ProcedureCallStatement procedureCallStatement = null;
    	InputOutputStatement inputOutputStatement = null;
    	ComplexStatement complexStatement = null;
    	
    	if (token.equals("SIDENTIFIER")) {
    		if (getToken(tokenIndex + 1).equals("SASSIGN") || getToken(tokenIndex + 1).equals("SLBRACKET")) {
    			assignStatement = assignStatement();
    		} else {
    			procedureCallStatement = procedureCallStatement();
    		}
    	} else if (token.equals("SREADLN") || token.equals("SWRITELN")) {
    		inputOutputStatement = inputOutputStatement();
    	} else if (token.equals("SBEGIN")) {
    		complexStatement = complexStatement();
    	}
    	
    	return new BasicStatement(assignStatement, procedureCallStatement, inputOutputStatement, complexStatement);
    }
    
    /**
     * 代入文の判定
     * 
     */
    public AssignStatement assignStatement() throws SyntaxException {
    	LeftSide leftSide = leftSide();
    	
    	// ":="の判定
    	checkToken("SASSIGN");
    	
    	Equation equation = equation();
    	
    	return new AssignStatement(leftSide, equation);
    }
    
    /**
     * 左辺の判定
     * 
     */
    public LeftSide leftSide() throws SyntaxException {
    	Variable variable = variable();
    	
    	return new LeftSide(variable);
    }
    
    /**
     * 変数の判定
     * 
     */
    public Variable variable() throws SyntaxException {
    	NaturalVariable naturalVariable = null;
    	VariableWithIndex variableWithIndex = null;
    	String token = getToken(tokenIndex);
    	String nextToken = getToken(tokenIndex + 1);
    	
    	if (token.equals("SIDENTIFIER") && nextToken.equals("SLBRACKET")) {
    		variableWithIndex = variableWithIndex();
    	} else if (token.equals("SIDENTIFIER")) {
    		naturalVariable = naturalVariable();
    	}
    	
    	return new Variable(naturalVariable, variableWithIndex);
    }
    
    /**
     * 純変数の判定
     * 
     */
    public NaturalVariable naturalVariable() throws SyntaxException {
    	VariableName variableName = variableName();
    	return new NaturalVariable(variableName);
    }
    
    
    /**
     * 添え字付き変数の判定
     * 
     */
    public VariableWithIndex variableWithIndex() throws SyntaxException {
    	VariableName variableName = variableName();
    	
    	// "["の判定
    	checkToken("SLBRACKET");
    	
    	Index index = index();
    	
    	// "]"の判定
    	checkToken("SRBRACKET");
    	
    	return new VariableWithIndex(variableName, index);
    }
    
    
    /**
     * 添え字の判定
     * 
     */
    public Index index() throws SyntaxException {
    	Equation equation = equation();
    	
    	return new Index(equation);
    }
    
    /**
     * 手続き呼び出し文の判定
     * 
     */
    public ProcedureCallStatement procedureCallStatement() throws SyntaxException {
    	ProcedureName procedureName = procedureName();
    	String token = getToken(tokenIndex);
    	EquationGroup equationGroup = null;
    	
    	if (token.equals("SLPAREN")) {
    		tokenIndex++;
    		equationGroup = equationGroup();
    		
    		// ")"の判定
    		checkToken("SRPAREN");
    	}
    	
    	return new ProcedureCallStatement(procedureName, equationGroup);
    }
    
    /**
     * 式の並びの判定
     * 
     */
    public EquationGroup equationGroup() throws SyntaxException {
    	Equation equation1 = equation();
    	List<Equation> equation2 = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SCOMMA")) {
    		tokenIndex++;
    		equation2.add(equation());
    	}
    	
    	return new EquationGroup(equation1, equation2);
    }
    
    /**
     * 式の判定
     * 
     */
    public Equation equation() throws SyntaxException {
    	// 単純式の判定
    	SimpleEquation simpleEquation1 = simpleEquation();
    	List<RelationalOperator> relationalOperator = new ArrayList<>();
    	List<SimpleEquation> simpleEquation2 = new ArrayList<>();
    	
    	String token = getToken(tokenIndex);
    	
    	// 関連演算子の判定
    	if (token.equals("SEQUAL") || token.equals("SNOTEQUAL") || token.equals("SLESS") || token.equals("SLESSEQUAL") || token.equals("SGREATEQUAL") || token.equals("SGREAT")) {
    		relationalOperator.add(relationalOperator());
    		simpleEquation2.add(simpleEquation());
    	}
    	
    	return new Equation(simpleEquation1, relationalOperator, simpleEquation2);
    }
    
    /**
     * 単純式の判定
     * 
     */
    public SimpleEquation simpleEquation() throws SyntaxException {
    	Sign sign = null;
    	if (getToken(tokenIndex).equals("SPLUS") || getToken(tokenIndex).equals("SMINUS")) {
    		sign = sign();
    	}
    	
    	Term term1 = term();
    	List<AdditionalOperator> additionalOperator = new ArrayList<>();
    	List<Term> term2 = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SPLUS") || getToken(tokenIndex).equals("SMINUS") || getToken(tokenIndex).equals("SOR")) {
    		additionalOperator.add(additionalOperator());
    		term2.add(term());
    	} 
    	
    	return new SimpleEquation(sign, term1, additionalOperator, term2);
    }
    
    /**
     * 項の判定
     * 
     */
    public Term term() throws SyntaxException {
    	Factor factor1 = factor();
    	List<MultipleOperator> multipleOperator = new ArrayList<>();
    	List<Factor> factor2 = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SSTAR") || getToken(tokenIndex).equals("SDIVD") || getToken(tokenIndex).equals("SMOD") || getToken(tokenIndex).equals("SAND")) {
    		multipleOperator.add(multipleOperator());
    		factor2.add(factor());
    	}
    	
    	return new Term(factor1, multipleOperator, factor2);
    }
    
    /**
     * 因子の判定
     * 
     */
    public Factor factor() throws SyntaxException {
    	Variable variable = null;
    	Constant constant = null;
    	Equation equation = null;
    	Factor factor = null;    	
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SIDENTIFIER")) {
    		variable = variable();
    	} else if (token.equals("SCONSTANT") || token.equals("SSTRING") || token.equals("SFALSE") || token.equals("STRUE")) { 
    		constant = constant();
    	} else if (token.equals("SLPAREN")) {
    		tokenIndex++;
    		equation = equation();
    		checkToken("SRPAREN");
    	} else if (token.equals("SNOT")) {
    		tokenIndex++;
    		factor = factor();
    	} else {
    		e.throwError(tokenIndex);
    	}
    	
    	return new Factor(variable, constant, equation, factor);
    }
    
    /**
     * 関係演算子の判定
     * 
     */
    public RelationalOperator relationalOperator() throws SyntaxException {
    	tokenIndex++;
    	return new RelationalOperator(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 加法演算子の判定
     * 
     */
    public AdditionalOperator additionalOperator() throws SyntaxException {
    	tokenIndex++;
    	return new AdditionalOperator(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 乗法演算子の判定
     * 
     */
    public MultipleOperator multipleOperator() throws SyntaxException {
    	tokenIndex++;
    	return new MultipleOperator(getLexicality(tokenIndex - 1));
    }
    
    /**
     * 入出力文の判定
     * 
     */
    public InputOutputStatement inputOutputStatement() throws SyntaxException {
    	VariableGroup variableGroup = null;
    	EquationGroup equationGroup = null;
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SREADLN")) {
    		tokenIndex++;
    		// "("の判定
    		if (getToken(tokenIndex).equals("SLPAREN")) {
    			tokenIndex++;
    			variableGroup = variableGroup();
    			// ")"の判定
    			checkToken("SRPAREN");
    		}
    	} else if (token.equals("SWRITELN")) {
    		tokenIndex++;
    		// "("の判定
    		if (getToken(tokenIndex).equals("SLPAREN")) {
    			tokenIndex++;
    			equationGroup = equationGroup();
    			// ")"の判定
    			checkToken("SRPAREN");
    		}
    	} 
    	
    	return new InputOutputStatement(variableGroup, equationGroup);
    }
    
    /**
     * 変数の並びの判定
     * 
     */
    public VariableGroup variableGroup() throws SyntaxException {
    	Variable variable1 = variable();
    	List<Variable> variable2 = new ArrayList<>();
    	
    	while (getToken(tokenIndex).equals("SCOMMA")) {
    		tokenIndex++;
    		variable2.add(variable());
    	}
    	
    	return new VariableGroup(variable1, variable2);
    }
    
    /**
     * 定数の判定
     * 
     */
    public Constant constant() throws SyntaxException {
    	UnsignedInteger unsignedInteger = null;
    	String token = getToken(tokenIndex);
    	String lexicality = null;
    	
    	if (token.equals("SCONSTANT")) {
    		unsignedInteger = unsignedInteger();
    	} else if (token.equals("SSTRING") || token.equals("SFALSE") || token.equals("STRUE")) {
    		tokenIndex++;
    		lexicality = getLexicality(tokenIndex - 1);
    	}
    	
    	return new Constant(lexicality, unsignedInteger);
    }
    
    /**
     * 符号なし整数の判定
     * 
     */
    public UnsignedInteger unsignedInteger() throws SyntaxException {
    	String token = getToken(tokenIndex);
    	
    	if (token.equals("SCONSTANT")) {
    		tokenIndex++;
    	} 
    	
    	return new UnsignedInteger(getLexicality(tokenIndex - 1));
    }
}