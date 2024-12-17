package enshud.s4.compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enshud.casl.CaslSimulator;

public class Compiler {

	/**
	 * サンプルmainメソッド．
	 * 単体テストの対象ではないので自由に改変しても良い．
	 */
	public static void main(final String[] args) {
		// normalの確認
		for (int i = 1; i <= 9; i++) {
			System.out.println("0" + i + ": " + new Compiler().run("data/ts/normal0" + i + ".ts", null));
		}
		for (int i = 10; i <= 20; i++) {
			System.out.println(i + ": " + new Compiler().run("data/ts/normal" + i + ".ts", null));
		}

		// synerrの確認
		for (int i = 1; i <= 8; i++) {
			System.out.println("0" + i + ": " + new Compiler().run("data/ts/synerr0" + i + ".ts", null));
		}
		
		// Compilerを実行してcasを生成する
		// System.out.println(new Compiler().run("data/ts/normal01.ts", "tmp/out.cas"));

		// 上記casを，CASLアセンブラ & COMETシミュレータで実行する
		// CaslSimulator.run("tmp/out.cas", "tmp/out.ans");
	}

	/**
	 * TODO
	 * 
	 * 開発対象となるCompiler実行メソッド．
	 * 以下の仕様を満たすこと．
	 * 
	 * 仕様:
	 * 第一引数で指定されたtsファイルを読み込み，CASL IIプログラムにコンパイルする．
	 * コンパイル結果のCASL IIプログラムは第二引数で指定されたcasファイルに書き出し"OK"という文字列を返すこと．
	 * 構文的もしくは意味的なエラーを発見した場合はエラーメッセージを返すこと．
	 * （エラーメッセージの内容はChecker.run()の出力に準ずる．）
	 * 入力ファイルが見つからない場合は"File not found"を返すこと．
	 * 
	 * @param inputFileName 入力tsファイル名
	 * @param outputFileName 出力casファイル名
	 */
	public String run(final String inputFileName, final String outputFileName) {
		try {
            // ファイルを行ごとに読み込む
            List<String> buffer = Files.readAllLines(Paths.get(inputFileName));            
            List<List<String>> tokens = new ArrayList<>();
            
            for (int i = 0; i < buffer.size(); i++) {
                String[] line = buffer.get(i).split("\t");
                tokens.add(Arrays.asList(line));
            }
            
            // 構文解析
            Parser parser = new Parser(tokens);
            
            // 木の探索開始
            // Program program = parser.getProgram();
            // program.accept(new ListVisitor());
            
            // casファイルを作成するメソッド
            
            // 構文解析，意味解析が終了したら"OK"を返す
            return "OK"; 
        } catch (final IOException e) {
            return "File not found";
        } 
	}
}
