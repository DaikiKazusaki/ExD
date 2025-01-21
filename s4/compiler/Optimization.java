package enshud.s4.compiler;

import java.util.List;

public class Optimization {
	public List<String> optimize(List<String> input) {
	    // リスト内の命令を直接操作
	    for (int i = 0; i < input.size() - 1; i++) {
	        String currentLine = input.get(i).trim();
	        String nextLine = input.get(i + 1).trim();

	        // 現在の行と次の行がPUSHとPOPのペアであるかを確認
	        if (currentLine.startsWith("\tPUSH\t") && nextLine.startsWith("\tPOP\t")) {
	            // PUSH命令からロードする値を取得
	            String pushOperand = currentLine.split("\t")[2]; // PUSH命令のオペランド

	            // POP命令からロード先のレジスタを取得
	            String popRegister = nextLine.split("\t")[2]; // POP命令のレジスタ

	            // LD命令に変換してリストを置き換え
	            String ldInstruction = "\tLD\t" + popRegister + ", " + pushOperand;
	            input.set(i, ldInstruction); // LD命令に置き換え
	            input.remove(i + 1); // POP命令を削除

	            // リスト内容が変更されたので、インデックス調整
	            i--; // 一歩戻って再評価
	        }
	    }

	    return input;
	}
}
