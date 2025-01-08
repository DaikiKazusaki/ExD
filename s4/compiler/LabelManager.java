package enshud.s4.compiler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

public class LabelManager {
	// <key: 開始番地，value: 終了番地>
    private Map<String, Integer> manageLabelNumber = new HashMap<>();
    private Stack<Map.Entry<String, String>> labelStack = new Stack<>();

    /**
     * ラベルを生成してスタックにPUSHするメソッド
     * 
     * @param labelType
     * @return 
     */
    public void pushLabel(String label) {
        // ラベルカウンタを更新
        manageLabelNumber.putIfAbsent(label, 0);
        int count = manageLabelNumber.get(label);
        manageLabelNumber.put(label, count + 1);

        String startLabel = label + count;
        String endLabel = "";

        // 特定のラベルタイプに応じて終了ラベルを生成
        if (label.equals("WHILE")) {
            endLabel = "ENDWHL" + count;
        } else if (label.equals("IF")) {
        	startLabel = "ENDIF" + count;
            endLabel = "ELSE" + count;
        } 

        // スタックにラベルをペアとして保存
        labelStack.push(Map.entry(startLabel, endLabel));
    }

    /**
     * スタックからラベルをPOPするメソッド
     * @return 
     * 
     * @return スタックから取り出されたラベルのペア
     */
    public Entry<String, String> popLabel() {
        if (labelStack.isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return labelStack.pop();
    }

    /**
     * スタックのトップにあるラベルを取得する（削除しない）
     * 
     * @return スタックのトップにあるラベルのペア
     */
    public Map.Entry<String, String> peekLabel() {
        if (labelStack.isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return labelStack.peek();
    }
}
