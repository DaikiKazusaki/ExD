# 情報科学演習D

## Lexer
現在改良中

## Parser
ASTを用いずに構文解析を行うプログラム．意味解析やコンパイラでは全く使用していない．
| ファイル名 | 役割 |
| --- | --- |
| Parser.java | メインクラス |
| LL1.java | LL1解析をしながら |
| SyntaxException | 構文エラーを出力する例外クラス |

## Checker
Checkerの機能はすべてCompilerに包含されており，CompilerではCheckerのコードをリファクタリングしたので，こちらを参照して下さい．

## Compiler
### 仕様
コンパイラへの入力はPascal風プログラムを字句解析した結果であり，出力はCASL2で記述したプログラムである．開発対象となるメソッドは `Compiler.java`の`run(String, String)'であり，このメソッドの仕様は以下のとおりである．
- 第1引数にはPascal風プログラムを字句解析したプログラム(ts ファイル)のパスを指定する
- 第2引数には出力先のファイル(cas ファイル1)のパスを指定する．
- コンパイル結果は第2引数で指定したファイルに出力し，`OK`を返す．
- 構文的・意味的な誤りを発券した場合はエラーメッセージを返し，casファイルを生成してはいけない．
- 入力ファイルが見つからなかった場合は文字列`File not found`を返す．
- プログラムに未使用変数が含まれている場合，Consoleに表示する．表示する内容は，未使用変数名および宣言された行番号を含んでいるものとする．

### ファイルの役割
| ファイル名 | 役割 |
| ---- | ---- |
| Compiler.java | メインのプログラム |
| Parser.java | 構文解析をしながらASTを作成する |
| SemanticValidationVisitor.java | ASTをたどりながら構文解析を行う |
| CompilationVisitor.java | ASTをたどりながらCASL2のコードを生成する |
| LabelManager.java | CASL2で使用するラベルを生成するクラス |
| LibManager.java | lib.casに含まれるサブルーチンのうち，必要最小限のサブルーチンのみをcasファイルに加える |
| Optimize.java | 最適化を行うクラス |
| Node.java | accepterのインターフェース |
| Visitor.java | visitorの抽象クラス |
| Symbol.java | 記号の情報を格納する |
| SymbolTable.java | 記号表，Symbol.javaのインスタンスをリストとして持つ |
| FunctionTable.java | 関数表を |
| SyntaxException.java | 構文エラーを出力する例外クラス |
| SemanticException.java | 意味エラーを出力する例外クラス |
| その他 | ASTの節点となるクラス |

### 拡張された機能
#### 覗き穴最適化
覗き穴最適化は，生成されたコードの一部分に注目し，注目した箇所の命令をほかの命令に置き換えてより短くする最適化のことを指す．今回は`PUSH`と`POP`を用いてレジスタにロードしている定数を`LAD`を用いて直接レジスタにロードするものである．
``` 
  PUSH  1
  PUSH  10
  POP  GR2
  POP  GR1
```
上記のコードを最適化すると，以下のようになる．
```
  LAD  GR1, 1
  LAD  GR2, 10
```

#### lib.casの不使用
`lib.cas`を使用しない代わりに，`lib.cas`に登録されているサブルーチンの中で必要最小限のものがcasファイルに記載されるようになっている．

(例)`normal01.ts`の場合は，`WRTSTR`と`WRTLN`

#### 未使用変数の表示・削除
プログラムに含まれる未使用変数をConsoleに出力し，その変数を記号表から削除する．入力ファイルを`normal12.ts`とすると，Consoleでは以下のように出力される．
~~~
Warning: in data/ts/normal12.ts
	bb is declared in line 4, but never used.
	b9 is declared in line 6, but never used.
~~~
また，`normal12.pas`では36個の変数[^1]が宣言されているが，生成されたcasファイルは以下のように34個の領域を確保している．
~~~
VAR	DS	34
~~~
[^1]: サイズnの配列をn個の変数と考える
