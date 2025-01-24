# 情報科学演習D

## Lexer.java

## Parser.java

## Checker.java
Checker.javaの機能はすべてCompiler.javaに包含されているため，Checher.javaは参照しないことを推奨します．

## Compiler.java
### ファイルの役割
| ファイル名 | 役割 |
| ---- | ---- |
| Compiler.java | メインのプログラム |
| Parser.java | 構文解析をしながらASTを作成するプログラム |
| SemanticValidationVisitor.java | |
| CompilationVisitor.java |  |
| LabelManager.java |  |

| LibManager.java |  |
| Optimize.java |  |
| SyntaxException.java |  |
| SemanticException.java |  |
| その他 | ASTの節点となるクラス |

### 拡張された機能
#### 覗き穴最適化
