# 情報科学演習D
Pascal風言語[^1]をCASL2にコンパイルするコンパイラをJavaで作成したものです．一般的にコンパイラでは以下の手順でコンパイルをします．
1. **字句解析**：入力プログラムからトークン列を切り出し，字句解析や構文解析のための準備を行う．
2. **構文解析**：プログラムが構文的に正しいかを判定する．
4. **意味解析**：プログラムに意味的[^2]に正しいかを判定する．
5. **コード生成**：目的言語のコードを生成する．
6. **最適化**：生成したコードから冗長な部分を削除し，実行数を最小限にしたコードに変換する．

## 字句解析
### 仕様
`Lexer.java`は，Pascal 風言語で記述されたプログラムからトークン列を切り出す字句解析器である．字句解析器の仕様は以下の通りである．
- runメソッドの引数で入力ファイルと出力ファイルのファイル名それぞれ指定する．
  - 第1引数は入力ファイルのファイル名で，Pascal風言語で記述されたpasファイルである．
  - 第2引数は出力ファイルのファイル名で，入力ファイルのトークン列を出力したtsファイルである．
- 正常に処理が終了した場合は，文字列`OK`を返す．
- 入力ファイルが存在しない場合は，文字列`File not found`を返す．

### ファイルの役割
現在改良中

### 作成したオートマトン
字句解析器を作成する方法は，正規表現を用いる，またはオートマトンを作成するの2通りがあるが，今回はオートマトンを選択した．オートマトンの状態遷移図は以下のようである．(図準備中)

## 構文解析
### 仕様
`Parser.java`は，Pascal風プログラムを字句解析した結果(tsファイル)を入力とし，プログラムが構文的に正しいことを判定するプログラムである．
- runメソッドの第一引数で指定されたtsファイルを読み込み，構文解析を行う．
- 構文が正しい場合，文字列`OK`を出力する．
- 構文が正しくない場合，`Syntax error: line`という文字列とともに最初に検出された構文エラーの行番号を出力する．
- 複数の誤りが含まれる場合は，最初に検出された誤りのみを出力する．
- 入力ファイルが存在しない場合，`File not found`という文字列を出力する．

### ファイル名
ASTを用いずに構文解析を行うプログラム．作成したファイルの内容は以下の通りである．
| ファイル名 | 役割 |
| --- | --- |
| Parser.java | メインクラス |
| LL1.java | LL(1)解析をしながら |
| SyntaxException | 構文エラーを出力する例外クラス |

なお，意味解析やコンパイラではASTを用いたプログラムを書いているため，ここで作成したプログラムは全く使用していない．

## 意味解析
Checkerの機能はすべてCompilerに包含されており，CompilerではCheckerのコードをリファクタリングしたので，こちらを参照して下さい．

## コンパイラ
### 仕様
`Compiler.java`はPascal風言語をCASL2にコンパイルするコンパイラである．コンパイラへの入力はPascal風プログラムを字句解析した結果であり，出力はCASL2で記述したプログラムである．開発対象となるメソッドは `Compiler.java`の`run(String, String)'であり，このメソッドの仕様は以下のとおりである．
- 第1引数にはPascal風プログラムを字句解析したプログラム(tsファイル)のパスを指定する．
- 第2引数には出力先のファイル(casファイル)のパスを指定する．
- コンパイル結果は第2引数で指定したファイルに出力し，`OK`を返す．
- 構文的・意味的な誤りを発見した場合はエラーメッセージを返し，casファイルを生成してはいけない．
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
| FunctionTable.java | 関数表を扱うクラス |
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

(例)`normal01.ts`の場合は，`WRTSTR`と`WRTLN`のみがcasファイルに記載され，`MULT`や`DIV`はcasファイルに加えられないようになっている．

#### 未使用変数の表示・削除
プログラムに含まれる未使用変数をConsoleに出力し，その変数を記号表から削除する．入力ファイルを`normal12.ts`とすると，Consoleでは以下のように出力される．
~~~
Warning: in data/ts/normal12.ts
	bb is declared in line 4, but never used.
	b9 is declared in line 6, but never used.
~~~
また，`normal12.pas`では36個の変数[^3]が宣言されているが，生成されたcasファイルは未使用変数の2個を除いた34個の領域を確保している．
~~~
  VAR	DS	34
~~~

## 補足
### AST(抽象構文木)
AST(Abstract Structure Tree)は，ソースコードを木構造で表現したもので，ソースコードの解析や変換，最適化などに利用される．ASTの特徴は以下の通りである．
- 言語の文法に従った階層的な表現﻿が可能
- `(`などの具体的な言語構文の詳細は含まれていない[^4]
- ステートメントや式、識別子などの抽象構文用語で指定されている﻿

### Visitorパターン
Visitorパターンは，データ構造と処理を分離し，既存のクラスを修正することなく機能を拡張することができる．Visitorパターンの使い方は以下の通りである．
1. データ構造を保持するクラス（今回はASTの根にあたる`Program.java`）を記述する．
2. それぞれのクラスにVisitorクラスを受け入れるメソッド（`accept()`）を定義する．
3. アルゴリズムを記述するVisitorクラスを記述する．
4. データ構造のクラスのオブジェクトを生成し，データ構造のオブジェクト.accept(Visitorオブジェクト)のように記述する．

[^1]: 関数やレコード構造などのPascalの一部機能が削除された言語．Pascalのエンジン上で実行可能である．
[^2]: 配列の添え字にboolean型の変数・定数を代入することは構文的には正しいが，意味的には誤りなので，このエラーを出力する．
[^3]: サイズ*n*の配列を*n*個の変数と考える
[^4]: CASL2では`(`などの具体的な言語構文の詳細は含まれていないので，ASTとの相性がよい．
