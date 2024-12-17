package enshud.s4.compiler;

import java.util.ArrayList;
import java.util.List;

public class Parser {
	private List<List<String>> tokenList = new ArrayList<>();
	private int tokenIndex = 0;
	private int LEXICALITYCOLS = 0;
	private int TOKNECOLS = 1;
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
		String token = tokenList.get(tokenIndex).get(TOKNECOLS);
		
		if (token.equals(input)) {
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
		String token = tokenList.get(input).get(TOKNECOLS);
		return token;
	}
	
	/**
	 * 
	 * 
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
		// 識別子の判定
		checkToken("SIDENTIFIER");
		
		return new ProgramName(getLexicality(tokenIndex - 1));
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
		// "var"の判定
		if (getToken(tokenIndex).equals("SVAR")) {
			return null;
		}
		
		// 変数宣言の並びの判定
		VariableDeclarationGroup variableDeclarationGroup = variableDeclarationGroup();
		
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
		List<VariableName> variableNameList = new ArrayList<>();
		
		// 変数名の判定
		variableNameList.add(variableName());		
		
		// 繰り返しの判定
		while (getToken(tokenIndex).equals("SCOMMA")) {
			variableNameList.add(variableName()); 
		}
		
		return new VariableNameGroup(variableNameList);
	}
	
	/**
	 * 変数名の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public VariableName variableName() throws SyntaxException {
		// 識別子の判定
		checkToken("SIDENTIFIER");
		
		return new VariableName(getLexicality(tokenIndex - 1));
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
		tokenIndex++;		
		return new StandardType(getLexicality(tokenIndex - 1));
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
		// 符号の判定
		Sign sign = null;
		if (getToken(tokenIndex).equals("ASIGN")) {
			sign = sign();
		}
		
		// 符号なし整数の判定
		UnsignedInteger unsignedInteger = unsignedInteger();
		
		return new Int(sign, unsignedInteger);
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
		// 副プログラム宣言の判定
		SubprogramDeclaration subprogramDeclaration = subprogramDeclaration();
		
		// ";"の判定
		checkToken("");
		
		return new SubprogramDeclarationGroup(subprogramDeclaration);
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
		// "procedure"の判定
		checkToken("SPROCEDURE");
		
		// 手続き名の判定
		ProcedureName procedureName = procedureName();
		
		// 仮パラメータの判定
		FormalParameter formalParameter = formalParameter();
		
		// ";"の判定
		checkToken("SSEMICOLON");
		
		return new SubprogramHead(procedureName, formalParameter);
	}
	
	/**
	 * 手続き名の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public ProcedureName procedureName() throws SyntaxException {
		// 識別子の確認
		checkToken("SSTRING");
		
		return new ProcedureName(getLexicality(tokenIndex));
	}
	
	/**
	 * 仮パラメータの判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public FormalParameter formalParameter() throws SyntaxException {
		// "("の判定
		if (getToken(tokenIndex).equals("SLPAREN")) {
			return null;
		}
		
		// 仮パラメータの並びの判定
		FormalParameterGroup formalParameterGroup = formalParameterGroup();
		
		// ")"の判定
		checkToken("SRPAREN");
		
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
		
		//仮パラメータ名の判定
		formalParameterNameList.add(formalParameterName());
		
		while (getToken(tokenIndex).equals("SCOMMA")) {
			tokenIndex++;
			formalParameterNameList.add(formalParameterName());
		}
		
		return new FormalParameterNameGroup(formalParameterNameList);
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
		List<Statement> statementList = new ArrayList<>();
		
		// 文の判定
		statementList.add(statement());
		
		// ";"の判定
		checkToken("SSEMICOLON");
		
		while (getToken(tokenIndex).equals("SIDENTIFIER") || getToken(tokenIndex).equals("SREADLN") || getToken(tokenIndex).equals("SWRITELN") || getToken(tokenIndex).equals("SBEGIN") || getToken(tokenIndex).equals("SIF") || getToken(tokenIndex).equals("SWHILE")) {
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
		BasicStatement basicStatement = null;
		IfThen ifThen = null;
		WhileDo whileDo = null;
		
		if (getToken(tokenIndex).equals("SIDENTIFIER") || getToken(tokenIndex).equals("SWRITELN") || getToken(tokenIndex).equals("SREADLN") || getToken(tokenIndex).equals("SBEGIN")) {
			basicStatement = basicStatament();
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
		// "if"の判定は済んでいるので，インクリメントのみを行う
		tokenIndex++;
		
		// 式の判定
		Equation equation = equation();
		
		// "then"の判定
		checkToken("STHEN");
		
		// 複合文の判定
		ComplexStatement complexStetement = complexStatement();
		
		ElseStatement elseStatement = null;
		// "else"の判定
		if (getToken(tokenIndex).equals("SELSE")) {
			elseStatement = elseStatement();
		}
		
		return new IfThen(equation, complexStetement, elseStatement);
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
		// "while"の判定は行われているので，インクリメントのみを行う
		tokenIndex++;
		
		// 式の判定
		Equation equation = equation();
		
		// "do"の判定
		checkToken("SDO");
		
		// 複合文の判定
		ComplexStatement complexStatement = complexStatement();
		
		return new WhileDo(equation, complexStatement);
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
		// 左辺の判定
		LeftSide leftSide = letSide();
		
		// ":="の判定
		checkToken("SASSIGN");
		
		// 式の判定
		Equation equation = equation();
		
		return new AssignStatement(leftSide, equation);
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
		NaturalVariable naturalVariable = null;
		VariableWithIndex variableWithIndex = null;
		
		if (!getToken(tokenIndex + 1).equals("SLBRACKET")) {
			naturalVariable = naturalVariable();
		} else {
			variableWithIndex = variableWithIndex();
		}
		
		return new Variable(naturalVariable, variableWithIndex);
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
		// 式の判定
		Equation equation = equation();
		
		return new Index(equation);
	}
	
	/**
	 * 手続き呼び出し文の判定
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	public ProcedureCallStatement procedureCallStatement() throws SyntaxException {
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
		
		return new ProcedureCallStatement(procedureName, equationGroup);
	}
	
	/**
	 * 式の並びの判定を行うメソッド
	 * 
	 * @return
	 * @throws SytnaxException
	 */
	public EquationGroup equationGroup() throws SytnaxException {
		List<Equation> equationList = new ArrayList<>();
		
		equationList.add(equation());
		
		while (getToken(tokenIndex).equals("SCOMMA")) {
			tokenIndex++;
			
			// 式の判定
			equationList.add(equation());
		}
		
		return new EquationGroup(equationList);
	}
	
	public Equation equation() throws SyntaxException {
		List<SimpleEquation> simpleEquationList = new ArrayList<>();
		List<RelationalOperator> relationalOperatorList = new ArrayList<>();
		
		simpleEquationList.add(simpleEquation());
		
		// while (getToken(tokenIndex).equals(""))
	}
}
