package enshud.s2.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Parser {    
    /**
     * サンプルmainメソッド．
     * 単体テストの対象ではないので自由に改変しても良い．
     */
    public static void main(final String[] args) {
        // normalの確認
        System.out.println(new Parser().run("data/ts/normal01.ts"));
        System.out.println(new Parser().run("data/ts/normal02.ts"));

        // synerrの確認
        System.out.println(new Parser().run("data/ts/synerr01.ts"));
        System.out.println(new Parser().run("data/ts/synerr02.ts"));
    }

    /**
     * Parser実行メソッド．
     * 
     * @param inputFileName 入力tsファイル名
     */
    public String run(final String inputFileName) {
        try {
            // ファイルを行ごとに読み込む
            List<String> buffer = Files.readAllLines(Paths.get(inputFileName));            
            List<List<String>> tokens = new ArrayList<>();
            
            for (int i = 0; i < buffer.size(); i++) {
                String[] line = buffer.get(i).split("\t");
                tokens.add(Arrays.asList(line));
            }
            
            /**
             * LL(1)解析を実行する
             * 構文エラーの場合，Exceptionクラスが実行され，処理が中断する
             * 構文エラーがない場合，"return OK"が実行される              
             */
            new LL1(tokens).program();
            return "OK"; 
        } catch (final IOException e) {
            return "File not found";
        } catch (final SyntaxException e) {
            // Syntax error発生時の処理
            return e.getMessage();
        }
    }
}
