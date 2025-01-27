package enshud.s3.checker;

public class Symbol {
    /**
     * 記号表の情報
     * [変数名，標準型，配列の判定，フォーマルパラメータの判定，スコープ，サイズ，使用済みかの判定，行番号]
     */
    private String variableName;
    private String standardType;
    private boolean isArray;
    private boolean isFormalParameter;
    private String scope;
    private int size;
    private boolean isUsed;
    private String lineNum;

    // コンストラクタ
    public Symbol(String variableName, String standardType, boolean isArray, boolean isFormalParameter, String scope, int size, String lineNum) {
        this.variableName = variableName;
        this.standardType = standardType;
        this.isArray = isArray;
        this.isFormalParameter = isFormalParameter;
        this.scope = scope;
        this.size = size;
        this.isUsed = false;
        this.lineNum = lineNum;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getStandardType() {
        return standardType;
    }

    public boolean isArray() {
        return isArray;
    }

    public boolean isFormalParameter() {
        return isFormalParameter;
    }

    public String getScope() {
        return scope;
    }

    public int getSize() {
        return size;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public String getLineNum() {
        return lineNum;
    }
    
    public void setISUsed() {
    	this.isUsed = true;
    }
}
