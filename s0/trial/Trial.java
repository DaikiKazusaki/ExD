package enshud.s0.trial;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Trial {

    /**
     * サンプルmainメソッド．
     * 単体テストの対象ではないので自由に改変しても良い．
     */
    public static void main(final String[] args) {
        // normalの確認
        System.out.println(new Trial().run("data/pas/normal01.pas"));
        System.out.println(new Trial().run("data/pas/normal02.pas"));
        System.out.println(new Trial().run("data/pas/normal03.pas"));
        System.out.println(new Trial().run("data/pas/normalXX.pas"));
    }

    /**
     * 開発対象となるTrial実行メソッド （練習用）．
     * 以下の仕様を満たすこと．
     * 
     * 仕様:
     * 第一引数で指定されたpascalファイルを読み込み，ファイル行数を文字列として返す．
     * 入力ファイルが見つからない場合は"File not found"を返す．
     * 
     * @param inputFileName 入力pascalファイル名
     */
    public String run(final String inputFileName) {
        Path filePath = Paths.get(inputFileName);
        long lineCount;
        
        if (Files.notExists(filePath)) {
            return "File not found";
        }

        try {
            lineCount = Files.lines(filePath).count();
            return Long.toString(lineCount);
        } catch (IOException e) {
            return "File not found";
        }
    }
}