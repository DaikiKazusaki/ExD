package enshud.s4.compiler;

import java.util.ArrayList;
import java.util.List;

public class Optimize {    
    /**
     * 覗き穴最適化を行うメソッド
     * 
     * @param input
     * @return
     */
    public List<String> peepholeOptimization(List<String> input) {
        List<String> statementList = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            // 現在の命令を取得
            String current = input.get(i);

            // PUSHとPOPが連続する場合の最適化
            if (i + 3 < input.size()) {
                String next1 = input.get(i + 1);
                String next2 = input.get(i + 2);
                String next3 = input.get(i + 3);

                if (current.matches("\\tPUSH\\t\\d+") && next1.matches("\\tPUSH\\t\\d+") && 
                    next2.matches("\\tPOP\\tGR\\d+") && next3.matches("\\tPOP\\tGR\\d+")) {
                    // PUSHの値を抽出
                    String value1 = current.split("\\t")[2];
                    String value2 = next1.split("\\t")[2];
                    // POPのレジスタを抽出
                    String register1 = next3.split("\\t")[2];
                    String register2 = next2.split("\\t")[2];

                    // LAD命令に置き換え
                    statementList.add("\tLAD\t" + register1 + ", " + value1);
                    statementList.add("\tLAD\t" + register2 + ", " + value2);

                    // 最適化対象をスキップ
                    i += 3;
                    continue;
                }
            }

            // 次の命令が存在する場合にのみ処理を行う
            if (i + 1 < input.size()) {
                String next = input.get(i + 1);

                // PUSHとPOPのパターンを検出
                if (current.matches("\\tPUSH\\t\\d+") && next.matches("\\tPOP\\tGR\\d+")) {
                    // PUSHの値を抽出
                    String value = current.split("\\t")[2]; // PUSHの3番目の要素（値）を取得
                    // POPのレジスタを抽出
                    String register = next.split("\\t")[2]; // POPの3番目の要素（レジスタ）を取得

                    // LAD命令に置き換え
                    statementList.add("\tLAD\t" + register + ", " + value);

                    // 最適化対象をスキップ
                    i++;
                    continue;
                }
            }

            // 最適化対象外の命令はそのまま追加
            statementList.add(current);
        }

        return statementList;
    }
}
