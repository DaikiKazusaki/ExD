package enshud.s4.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
	private List<List<String>> tokenList = new ArrayList<>();
	private int tokenIndex = 0;
	private int LEXICALITYCOLS = 0;
	private int TOKENCOLS = 1;
	private int LINENUMBERCOLS = 3;
	
	public Parser(List<List<String>> tokenList) {
		this.tokenList = tokenList;
	}
	
	/**
	 * tokenIndex行目のトークンが期待されたものであるかをチェックするメソッド
	 * 
	 * @param input　期待されるトークン
	 * @throws SyntaxException
	 */
	public void checkToken(String input) throws SyntaxException {
		String token = tokenList.get(tokenIndex).get(TOKENCOLS);
		
		if (!token.equals(input)) {
			String lineNum = getLineNum();
			throw new SyntaxException(lineNum);
		}
		
		tokenIndex++;
	}
	
	/**
	 * 字句を取得するメソッド
	 * 
	 * @param index
	 * @return　字句
	 */
	public String getLexicality(int index) {
		return tokenList.get(index).get(LEXICALITYCOLS);
	}
	
	/**
	 * トークンを取得するメソッド
	 * 
	 * @return token
	 */
	public String getToken(int input) {
		String token = tokenList.get(input).get(TOKENCOLS);
		return token;
	}
	
	/**
	 * 行数を取得するメソッド
	 * 
	 * @return 
	 */
	public String getLineNum() {
		return tokenList.get(tokenIndex).get(LINENUMBERCOLS);
	}
	
	/**
	 * プログラムを判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public Program program() throws SyntaxException {
		// "program"のチェック
		checkToken("SPROGRAM");
		
		// プログラム名の確認
		ProgramName programName = programName();
		
		// ";"の確認
		checkToken("SSEMICOLON");
		
		// ブロックの確認
		Block block = block();
		
		// 複合文の確認
		ComplexStatement complexStatement = complexStatement();
		
		// "."の確認
		checkToken("SDOT");
		
		return new Program(programName, block, complexStatement);		
	}
	
	/**
	 * プログラム名を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public ProgramName programName() throws SyntaxException {
		String lineNum = getLineNum();
		// 識別子の判定
		checkToken("SIDENTIFIER");
		
		return new ProgramName(getLexicality(tokenIndex - 1), lineNum);
	}
	
	/**
	 * ブロックを判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public Block block() throws SyntaxException {
		// 変数宣言の判定
		VariableDeclaration variableDeclaration = variableDeclaration();
		
		// 副プログラム宣言群の判定
		SubprogramDeclarationGroup subprogramDeclarationGroup = subprogramDeclarationGroup();
		
		return new Block(variableDeclaration, subprogramDeclarationGroup);
	}
	
	/**
	 * 変数宣言を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public VariableDeclaration variableDeclaration() throws SyntaxException {
		VariableDeclarationGroup variableDeclarationGroup = null;
		
		// "var"の判定
		if (getToken(tokenIndex).equals("SVAR")) {
			tokenIndex++;
			
			variableDeclarationGroup = variableDeclarationGroup();
		} 
		
		return new VariableDeclaration(variableDeclarationGroup);
	}
	
	/**
	 * 変数宣言の並びを判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public VariableDeclarationGroup variableDeclarationGroup() throws SyntaxException {
		List<VariableNameGroup> variableNameGroupList = new ArrayList<>();
		List<Type> typeList = new ArrayList<>();
		
		// 変数名の並びの判定
		variableNameGroupList.add(variableNameGroup());
		
		// ":"の判定
		checkToken("SCOLON");
		
		// 型の判定
		typeList.add(type());
		
		// ";"の判定
		checkToken("SSEMICOLON");
		
		while (getToken(tokenIndex).equals("SIDENTIFIER")) {
			// 変数名の並びの判定
			variableNameGroupList.add(variableNameGroup());
			
			// ":"の判定
			checkToken("SCOLON");
			
			// 型の判定
			typeList.add(type());
			
			// ";"の判定
			checkToken("SSEMICOLON");
		}
		
		return new VariableDeclarationGroup(variableNameGroupList, typeList);
	}
	
	/**
	 * 変数名の並びの判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public VariableNameGroup variableNameGroup() throws SyntaxException {
		String lineNum = getLineNum();
		List<VariableName> variableNameList = new ArrayList<>();
		
		// 変数名の判定
		variableNameList.add(variableName());		
		
		// 繰り返しの判定
		while (getToken(tokenIndex).equals("SCOMMA")) {
			tokenIndex++;
			
			variableNameList.add(variableName()); 
		}
		
		return new VariableNameGroup(variableNameList, lineNum);
	}
	
	/**
	 * 変数名の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public VariableName variableName() throws SyntaxException {
		String lineNum = getLineNum();
		// 識別子の判定
		checkToken("SIDENTIFIER");
		
		return new VariableName(getLexicality(tokenIndex - 1), lineNum);
	}
	
	/**
	 * 型の判定を行うメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public Type type() throws SyntaxException {
		StandardType standardType = null;
		ArrayType arrayType = null;
		
		// トークンの取得
		String token = getToken(tokenIndex);
		
		// 標準型，配列型の判定
		if (token.equals("SINTEGER") || token.equals("SCHAR") || token.equals("SBOOLEAN")) {
			standardType = standardType();
		} else if (token.equals("SARRAY")) {
			arrayType = arrayType();
		} else {
			String lineNum = getLineNum();
			throw new SyntaxException(lineNum);
		}
		
		return new Type(standardType, arrayType);
	}
	
	/**
	 * 標準型を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public StandardType standardType() throws SyntaxException {
		String lineNum = getLineNum();
		tokenIndex++;		
		return new StandardType(getLexicality(tokenIndex - 1), lineNum);
	}
	
	/**
	 * 配列型を判定する判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public ArrayType arrayType() throws SyntaxException {
		// "array"の判定
		checkToken("SARRAY");
		
		// "["の判定
		checkToken("SLBRACKET");
		
		// 添え字の最小値の判定
		Int minimumInteger = integer();
		
		// ".."の判定
		checkToken("SRANGE");
		
		// 添え字の最大値の判定
		Int maximumInteger = integer();
		
		// "]"の判定
		checkToken("SRBRACKET");
		
		// "of"の判定
		checkToken("SOF");
		
		// 標準型の判定
		StandardType standardType = standardType();
		
		return new ArrayType(minimumInteger, maximumInteger, standardType);
	}
	
	/**
	 * 整数の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public Int integer() throws SyntaxException {
		String lineNum = getLineNum();
		Sign sign = null;
		List<String> signList = Arrays.asList("SPLUS", "SMINUS");
		
		// 符号の判定
		if (signList.contains(getToken(tokenIndex))) {
			sign = sign();
		}
		
		// 符号なし整数の判定
		UnsignedInteger unsignedInteger = unsignedInteger();
		
		return new Int(sign, unsignedInteger, lineNum);
	}
	
	/**
	 * 符号を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public Sign sign() throws SyntaxException {
		tokenIndex++;
		return new Sign(getLexicality(tokenIndex - 1));
	}
	
	/**
	 * 副プログラム宣言群を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public SubprogramDeclarationGroup subprogramDeclarationGroup() throws SyntaxException {
		List<SubprogramDeclaration> subprogramDeclarationList = new ArrayList<>();
		
		while (getToken(tokenIndex).equals("SPROCEDURE")) {
			// 副プログラム宣言の判定
			subprogramDeclarationList.add(subprogramDeclaration());
			
			// ";"の判定
			checkToken("SSEMICOLON");
		}
		
		return new SubprogramDeclarationGroup(subprogramDeclarationList);
	}
	
	/**
	 * 副プログラム宣言の判定を行うメソッド
	 * 
	 * @return
	 * @throws SyntaxException
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
	 * 副プログラム頭部の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public SubprogramHead subprogramHead() throws SyntaxException {
		String lineNum = getLineNum();
		
		// "procedure"の判定
		checkToken("SPROCEDURE");
		
		// 手続き名の判定
		ProcedureName procedureName = procedureName();
		
		// 仮パラメータの判定
		FormalParameter formalParameter = formalParameter();
		
		// ";"の判定
		checkToken("SSEMICOLON");
		
		return new SubprogramHead(procedureName, formalParameter, lineNum);
	}
	
	/**
	 * 手続き名の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public ProcedureName procedureName() throws SyntaxException {
		// 識別子の確認
		checkToken("SIDENTIFIER");
		
		return new ProcedureName(getLexicality(tokenIndex - 1));
	}
	
	/**
	 * 仮パラメータの判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public FormalParameter formalParameter() throws SyntaxException {
		FormalParameterGroup formalParameterGroup = null;
		
		// "("の判定
		if (getToken(tokenIndex).equals("SLPAREN")) {
			// "("の判定はすでに行われているので，インクリメントのみを行う
			tokenIndex++;
			
			// 仮パラメータの並びの判定
			formalParameterGroup = formalParameterGroup();
			
			// ")"の判定
			checkToken("SRPAREN");
		}
		
		return new FormalParameter(formalParameterGroup);
	}
	
	/**
	 * 仮パラメータを判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public FormalParameterGroup formalParameterGroup() throws SyntaxException {
		List<FormalParameterNameGroup> formalParameterNameGroupList = new ArrayList<>();
		List<StandardType> standardTypeList = new ArrayList<>();
		
		// 仮パラメータ名の並びの判定
		formalParameterNameGroupList.add(formalParameterNameGroup());
		
		// ":"の判定
		checkToken("SCOLON");
		
		// 標準型
		standardTypeList.add(standardType());
		
		while (getToken(tokenIndex).equals("SSEMICOLON")) {
			// 仮パラメータ名の並びの判定
			formalParameterNameGroupList.add(formalParameterNameGroup());
			
			// ":"の判定
			checkToken("SCOLON");
			
			// 標準型
			standardTypeList.add(standardType());
		}
		
		return new FormalParameterGroup(formalParameterNameGroupList, standardTypeList);
	}
	
	/**
	 * 仮パラメータ名の並びの判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public FormalParameterNameGroup formalParameterNameGroup() throws SyntaxException {
		List<FormalParameterName> formalParameterNameList = new ArrayList<>();
		String lineNum = getLineNum();
		
		//仮パラメータ名の判定
		formalParameterNameList.add(formalParameterName());
		
		while (getToken(tokenIndex).equals("SCOMMA")) {
			tokenIndex++;
			formalParameterNameList.add(formalParameterName());
		}
		
		return new FormalParameterNameGroup(formalParameterNameList, lineNum);
	}
	
	/**
	 * 仮パラメータ名の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public FormalParameterName formalParameterName() throws SyntaxException {
		// 識別子の判定
		if (!getToken(tokenIndex).equals("SIDENTIFIER")) {
			String lineNum = getLineNum();
			throw new SyntaxException(lineNum);
		}
		tokenIndex++;
		
		return new FormalParameterName(getLexicality(tokenIndex - 1));
	}
	
	/**
	 * 複合文の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public ComplexStatement complexStatement() throws SyntaxException {
		// "begin"の判定
		checkToken("SBEGIN");
		
		// 文の並びの判定
		StatementGroup statementGroup = statementGroup();
		
		// "end"の判定
		checkToken("SEND");
		
		return new ComplexStatement(statementGroup);
	}
	
	/**
	 * 文の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public StatementGroup statementGroup() throws SyntaxException {
	    List<String> firstOfStatementGroup = Arrays.asList("SIDENTIFIER", "SREADLN", "SWRITELN", "SBEGIN", "SIF", "SWHILE");
	    List<Statement> statementList = new ArrayList<>();
	    
	    // 文の判定
	    statementList.add(statement());
	    
	    // ";"の判定
	    checkToken("SSEMICOLON");
	    
	    while (firstOfStatementGroup.contains(getToken(tokenIndex))) {
	        // 文の判定
	        statementList.add(statement());
	        
	        // ";"の判定
	        checkToken("SSEMICOLON");
	    }
	    
	    return new StatementGroup(statementList);
	}

	
	/**
	 * 文を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public Statement statement() throws SyntaxException {
		List<String> firstOfStatement = Arrays.asList("SIDENTIFIER", "SREADLN", "SWRITELN", "SBEGIN");
		BasicStatement basicStatement = null;
		IfThen ifThen = null;
		WhileDo whileDo = null;
		
		if (firstOfStatement.contains(getToken(tokenIndex))) {
			basicStatement = basicStatement();
		} else if (getToken(tokenIndex).equals("SIF")) {
			ifThen = ifThen();
		} else if (getToken(tokenIndex).equals("SWHILE")) {
			whileDo = whileDo();
		}
		
		return new Statement(basicStatement, ifThen, whileDo);
	}
	
	/**
	 * if-then文の判定を行うメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public IfThen ifThen() throws SyntaxException {
		String lineNum = getLineNum();
		// "if"の判定は済んでいるので，インクリメントのみを行う
		tokenIndex++;
		
		// 式の判定
		Equation equation = equation();
		
		// "then"の判定
		checkToken("STHEN");
		
		// 複合文の判定
		ComplexStatement complexStatement = complexStatement();
		
		ElseStatement elseStatement = null;
		// "else"の判定
		if (getToken(tokenIndex).equals("SELSE")) {
			elseStatement = elseStatement();
		}
		
		return new IfThen(equation, complexStatement, elseStatement, lineNum);
	}
	
	/**
	 * else文の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public ElseStatement elseStatement() throws SyntaxException {
		// "else"の判定は行われているので，インクリメントのみを行う
		tokenIndex++;
		
		// 複合文の判定
		ComplexStatement complexStatement = complexStatement();
		
		return new ElseStatement(complexStatement);
	}
	
	/**
	 * while-do文の判定を行うメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public WhileDo whileDo() throws SyntaxException {
		String lineNum = getLineNum();
		
		// "while"の判定は行われているので，インクリメントのみを行う
		tokenIndex++;
		
		// 式の判定
		Equation equation = equation();
		
		// "do"の判定
		checkToken("SDO");
		
		// 複合文の判定
		ComplexStatement complexStatement = complexStatement();
		
		return new WhileDo(equation, complexStatement, lineNum);
	}
	
	/**
	 * 基本文を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public BasicStatement basicStatement() throws SyntaxException {
		AssignStatement assignStatement = null;
		ProcedureCallStatement procedureCallStatement = null;
		InputOutputStatement inputOutputStatement = null;
		ComplexStatement complexStatement = null;
		
		if (getToken(tokenIndex).equals("SIDENTIFIER")) {
			if (getToken(tokenIndex + 1).equals("SASSIGN") || getToken(tokenIndex + 1).equals("SLBRACKET")) {
				assignStatement = assignStatement();
			} else {
				procedureCallStatement = procedureCallStatement();
			}
		} else if (getToken(tokenIndex).equals("SREADLN") || getToken(tokenIndex).equals("SWRITELN")) {
			inputOutputStatement = inputOutputStatement();
		} else if (getToken(tokenIndex).equals("SBEGIN")) {
			complexStatement = complexStatement();
		} else {
			String lineNum = getLineNum();
			throw new SyntaxException(lineNum);
		}
		
		return new BasicStatement(assignStatement, procedureCallStatement, inputOutputStatement, complexStatement);
	}
	
	/**
	 * 代入文の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public AssignStatement assignStatement() throws SyntaxException {
		String lineNum = getLineNum();
		
		// 左辺の判定
		LeftSide leftSide = leftSide();
		
		// ":="の判定
		checkToken("SASSIGN");
		
		// 式の判定
		Equation equation = equation();
		
		return new AssignStatement(leftSide, equation, lineNum);
	}
	
	/**
	 * 左辺を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public LeftSide leftSide() throws SyntaxException {
		// 変数の判定
		Variable variable = variable();
		
		return new LeftSide(variable);
	}
	
	/**
	 * 変数の判定を行うメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public Variable variable() throws SyntaxException {
		String lineNum = getLineNum();
		NaturalVariable naturalVariable = null;
		VariableWithIndex variableWithIndex = null;
		
		if (!getToken(tokenIndex + 1).equals("SLBRACKET")) {
			naturalVariable = naturalVariable();
		} else {
			variableWithIndex = variableWithIndex();
		} 
		
		return new Variable(naturalVariable, variableWithIndex, lineNum);
	}
	
	/**
	 * 純変数の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public NaturalVariable naturalVariable() throws SyntaxException {
		// 変数名の判定
		VariableName variableName = variableName();
		
		return new NaturalVariable(variableName);
	}
	
	/**
	 * 添え字付き変数の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public VariableWithIndex variableWithIndex() throws SyntaxException {
		// 変数名の判定
		VariableName variableName = variableName();
		
		// "["の判定
		checkToken("SLBRACKET");
		
		// 添え字の判定
		Index index = index();
		
		// "]"の判定
		checkToken("SRBRACKET");
		
		return new VariableWithIndex(variableName, index);
	}
	
	/**
	 * 添え字を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public Index index() throws SyntaxException {
		String lineNum = getLineNum();
		// 式の判定
		Equation equation = equation();
		
		return new Index(equation, lineNum);
	}
	
	/**
	 * 手続き呼び出し文の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public ProcedureCallStatement procedureCallStatement() throws SyntaxException {
		String lineNum = getLineNum();
		
		// 手続き名の判定
		ProcedureName procedureName = procedureName();
		
		EquationGroup equationGroup = null;
		// "("の判定
		if (getToken(tokenIndex).equals("SLPAREN")) {
			tokenIndex++;
			
			// 式の並びの判定
			equationGroup = equationGroup();
			
			// ")"の判定
			checkToken("SRPAREN");
		}
		
		return new ProcedureCallStatement(procedureName, equationGroup, lineNum);
	}
	
	/**
	 * 式の並びの判定を行うメソッド
	 * 
	 * @return
	 * @throws SytnaxException
	 */
	public EquationGroup equationGroup() throws SyntaxException {
		List<Equation> equationList = new ArrayList<>();
		
		equationList.add(equation());
		
		while (getToken(tokenIndex).equals("SCOMMA")) {
			// ","の判定はすでに行われているので，インクリメントのみを行う
			tokenIndex++;
			
			// 式の判定
			equationList.add(equation());
		}
		
		return new EquationGroup(equationList);
	}
	
	/**
	 * 式を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public Equation equation() throws SyntaxException {
		String lineNum = getLineNum();
		List<String> setOfRelationalOperator = Arrays.asList("SEQUAL", "SNOTEQUAL", "SLESS", "SLESSEQUAL", "SGREAT", "SGREATEQUAL");
		List<SimpleEquation> simpleEquationList = new ArrayList<>();
		RelationalOperator relationalOperator = null;
		
		// 単純式の判定
		simpleEquationList.add(simpleEquation());
		
		if (setOfRelationalOperator.contains(getToken(tokenIndex))) {
			// 関係演算子の判定
			relationalOperator = relationalOperator();
			
			// 単純式の判定
			simpleEquationList.add(simpleEquation());
		}
		
		return new Equation(simpleEquationList, relationalOperator, lineNum);
	}
	
	/**
	 * 単純式の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public SimpleEquation simpleEquation() throws SyntaxException {
		String lineNum = getLineNum();
		Sign sign = null;
		List<Term> termList = new ArrayList<>();
		List<AdditionalOperator> additionalOperatorList = new ArrayList<>();
		List<String> setOfAdditionalOperator = Arrays.asList("SPLUS", "SMINUS", "SOR");
		
		// 符号の判定
		if (getToken(tokenIndex).equals("SPLUS") || getToken(tokenIndex).equals("SMINUS")) {
			sign = sign();
		}
		
		// 項の判定
		termList.add(term());
		
		while (setOfAdditionalOperator.contains(getToken(tokenIndex))) {
			//　加法演算子の判定
			additionalOperatorList.add(additionalOperator());
			
			// 項の判定
			termList.add(term());
		}
		
		return new SimpleEquation(sign, termList, additionalOperatorList, lineNum);
	}
	
	/**
	 * 項を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public Term term() throws SyntaxException {
		String lineNum = getLineNum();
		List<Factor> factorList = new ArrayList<>();
		List<MultipleOperator> multipleOperatorList = new ArrayList<>();
		List<String> setOfMultipleOperator = Arrays.asList("SSTAR", "SDIVD", "SMOD", "SAND");
		
		// 因子の判定
		factorList.add(factor());
		
		while (setOfMultipleOperator.contains(getToken(tokenIndex))) {
			// 乗法演算子の判定
			multipleOperatorList.add(multipleOperator());
			
			// 因子の判定
			factorList.add(factor());
		}
		
		return new Term(factorList, multipleOperatorList, lineNum);
	}
	
	/**
	 * 因子の判定を行うメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public Factor factor() throws SyntaxException {
		String lineNum = getLineNum();
		Variable variable = null;
		Constant constant = null;
		Equation equation = null;
		Factor factor = null;
		List<String> setOfConstant = Arrays.asList("SCONSTANT", "SSTRING", "SFALSE", "STRUE");
		
		if (getToken(tokenIndex).equals("SIDENTIFIER")) {
			variable = variable();
		} else if (setOfConstant.contains(getToken(tokenIndex))) {
			constant = constant();
		} else if (getToken(tokenIndex).equals("SLPAREN")) {
			// "("の判定はすでに行われているので，インクリメントのみを行う
			tokenIndex++;
			
			// 式の判定
			equation = equation();
			
			// ")"の判定
			checkToken("SRPAREN");
		} else if (getToken(tokenIndex).equals("SNOT")) {
			// "not"の判定はすでに行われているので，インクリメントのみを行う
			tokenIndex++;
			
			// 因子の判定
			factor = factor();
		} else {
			throw new SyntaxException(lineNum);
		}
		
		return new Factor(variable, constant, equation, factor, lineNum);
	}
	
	/**
	 * 関係演算子を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public RelationalOperator relationalOperator() throws SyntaxException {
		String relationalOperator = getLexicality(tokenIndex);
		tokenIndex++;
		
		return new RelationalOperator(relationalOperator);
	}
	
	/**
	 * 加法演算子の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public AdditionalOperator additionalOperator() throws SyntaxException {
		String additionalOperator = getLexicality(tokenIndex);
		tokenIndex++;
		
		return new AdditionalOperator(additionalOperator);
	}
	
	/**
	 * 乗法演算子を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public MultipleOperator multipleOperator() throws SyntaxException {
		String multipleOperator = getLexicality(tokenIndex);
		tokenIndex++;
		
		return new MultipleOperator(multipleOperator);
	}
	
	/**
	 * 入出力文の判定を行うメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public InputOutputStatement inputOutputStatement() throws SyntaxException {
		VariableGroup variableGroup = null;
		EquationGroup equationGroup = null;
		
		if (getToken(tokenIndex).equals("SREADLN")) {
			// "readln"の判定はすでに行われているので，インクリメントのみを行う
			tokenIndex++;
			
			if (getToken(tokenIndex).equals("SLPAREN")) {
				// "("の判定はすでに行われているので，インクリメントのみを行う
				tokenIndex++;
				
				// 変数の並びの判定
				variableGroup = variableGroup();
				
				// ")"の判定
				checkToken("SRPAREN");
			}
		} else if (getToken(tokenIndex).equals("SWRITELN")) {
			// "writeln"の判定はすでに行われているので，インクリメントのみを行う
			tokenIndex++;
			
			if (getToken(tokenIndex).equals("SLPAREN")) {
				// "("の判定はすでに行われているので，インクリメントのみを行う
				tokenIndex++;
				
				// 変数の並びの判定
				equationGroup = equationGroup();
				
				// ")"の判定
				checkToken("SRPAREN");
			}
		} else {
			String lineNum = getLineNum();
			throw new SyntaxException(lineNum);
		}
		
		return new InputOutputStatement(variableGroup, equationGroup);
	}
	
	/**
	 * 変数の並びの判定
	 * 
	 * @return
	 * @throws SyntaxEception
	 */
	public VariableGroup variableGroup() throws SyntaxException {
		List<Variable> variableList = new ArrayList<>();
		
		// 変数の判定
		variableList.add(variable());
		
		while (getToken(tokenIndex).equals("SCOMMA")) {
			// ","の判定はすでに行われているので，インクリメントのみを行う
			tokenIndex++;
			
			// 変数の判定
			variableList.add(variable());
		}
		
		return new VariableGroup(variableList);
	}
	
	/**
	 * 定数を判定するメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public Constant constant() throws SyntaxException {
		List<String> setOfConstant = Arrays.asList("SCONSTANT", "SSTRING", "SFALSE", "STRUE");
		
		if (setOfConstant.contains(getToken(tokenIndex))) {
			tokenIndex++;
			
			return new Constant(getLexicality(tokenIndex - 1));
		} else {
			String lineNum = getLineNum();
			throw new SyntaxException(lineNum);
		}
	}
	
	/**
	 * 符号なし整数の判定を行うメソッド
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public UnsignedInteger unsignedInteger() throws SyntaxException {
		// 数字の判定
		checkToken("SCONSTANT");
		
		return new UnsignedInteger(getLexicality(tokenIndex - 1));
	}
}
