package enshud.s1.lexer;

public class Utils {
    private static final String[] OPERATORS = {
        "=", "<", ">", ":",
        ".", "<>", "<=", ">=", ":=", ".."
    };

    /**
     * 入力文字列から先頭の2つのオペレーターを優先的に取得。
     * @param input 入力文字列
     * @return 最長一致するオペレーター文字列（または null）
     */
    public static String getOperator(String input) {
        // 最大2文字までの部分文字列を検査
        for (int len = 2; len >= 1; len--) {
            if (input.length() >= len) {
                String sub = input.substring(0, len);
                if (isRegisteredOperator(sub)) {
                    return sub; // 登録済みオペレーターなら返す
                }
            }
        }
        return null;
    }

    /**
     * 文字列が登録されたオペレーターか確認。
     * @param operator 確認対象の文字列
     * @return オペレーターかどうか
     */
    private static boolean isRegisteredOperator(String operator) {
        for (String op : OPERATORS) {
            if (op.equals(operator)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 単一文字がオペレーターか確認。
     * @param input 入力文字
     * @return オペレーターかどうか
     */
    public static boolean isOperator(char input) {
        return isRegisteredOperator(String.valueOf(input));
    }
}
