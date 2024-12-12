package enshud.s3.checker;

public class SemanticException extends Exception {
    private static final long serialVersionUID = 1L;

    // コンストラクタ
    public SemanticException(String lineNum) {
        super("Semantic error: line " + lineNum); // メッセージを直接渡す
    }
}
