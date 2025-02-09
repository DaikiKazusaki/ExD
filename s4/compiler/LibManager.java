package enshud.s4.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibManager {	
	private List<String> libStatement = new ArrayList<>();
	
	public LibManager() {
		libStatement.add("");
	}
	
	/**
	 * 必要なlibのみを記載するメソッド
	 * 
	 * @param isNecessaryOfLib
	 * @return
	 */
	public List<String> addLibToStatement(boolean[] isNecessaryOfLib){		
		if (isNecessaryOfLib[0] == true) {
			libStatement.addAll(getMULT());
		}
		if (isNecessaryOfLib[1] == true) {
			libStatement.addAll(getDIV());
		}
		if (isNecessaryOfLib[2] == true) {
			libStatement.addAll(getRDINT());
		}
		if (isNecessaryOfLib[3] == true) {
			libStatement.addAll(getRDCH());
		}
		if (isNecessaryOfLib[4] == true) {
			libStatement.addAll(getRDSTR());
		}
		if (isNecessaryOfLib[5] == true) {
			libStatement.addAll(getRDLN());
		}
		if (isNecessaryOfLib[6] == true) {
			libStatement.addAll(getWRTINT());
		}
		if (isNecessaryOfLib[7] == true) {
			libStatement.addAll(getWRTCH());
		}
		if (isNecessaryOfLib[8] == true) {
			libStatement.addAll(getWRTSTR());
		}
		if (isNecessaryOfLib[9] == true) {
			libStatement.addAll(getWRTLN());
		}
		
		return libStatement;
	}
	
	public List<String> getMULT() {
		List<String> multStatement = new ArrayList<>();
		multStatement = Arrays.asList(
			";============================================================",
		    "; MULT: 掛け算を行うサブルーチン",
		    "; GR1 * GR2 -> GR2",
		    "MULT\tSTART",
		    "\tPUSH\t0,GR1\t; GR1の内容をスタックに退避",
		    "\tPUSH\t0,GR3\t; GR3の内容をスタックに退避",
		    "\tPUSH\t0,GR4\t; GR4の内容をスタックに退避",
		    "\tLAD\tGR3,0\t; GR3を初期化",
		    "\tLD\tGR4,GR2",
		    "\tJPL\tLOOP",
		    "\tXOR\tGR4,=#FFFF",
		    "\tADDA\tGR4,=1",
		    "LOOP\tSRL\tGR4,1",
		    "\tJOV\tONE",
		    "\tJUMP\tZERO",
		    "ONE\tADDL\tGR3,GR1",
		    "ZERO\tSLL\tGR1,1",
		    "\tAND\tGR4,GR4",
		    "\tJNZ\tLOOP",
		    "\tCPA\tGR2,=0",
		    "\tJPL\tEND",
		    "\tXOR\tGR3,=#FFFF",
		    "\tADDA\tGR3,=1",
		    "END\tLD\tGR2,GR3",
		    "\tPOP\tGR4",
		    "\tPOP\tGR3",
		    "\tPOP\tGR1",
		    "\tRET",
		    "\tEND"
		);
		
		return multStatement;
	}
	
	public List<String> getDIV() {
		List<String> divStatement = new ArrayList<>();
		divStatement = Arrays.asList(
			";============================================================",
		    "; DIV: 割り算を行うサブルーチン",
		    "; GR1 / GR2 -> 商は GR2, 余りは GR1",
		    "DIV\tSTART",
		    "\tPUSH\t0,GR3",
		    "\tST\tGR1,A",
		    "\tST\tGR2,B",
		    "\tCPA\tGR1,=0",
		    "\tJPL\tSKIPA",
		    "\tXOR\tGR1,=#FFFF",
		    "\tADDA\tGR1,=1",
		    "SKIPA\tCPA\tGR2,=0",
		    "\tJZE\tSKIPD",
		    "\tJPL\tSKIPB",
		    "\tXOR\tGR2,=#FFFF",
		    "\tADDA\tGR2,=1",
		    "SKIPB\tLD\tGR3,=0",
		    "LOOP\tCPA\tGR1,GR2",
		    "\tJMI\tSTEP",
		    "\tSUBA\tGR1,GR2",
		    "\tLAD\tGR3,1,GR3",
		    "\tJUMP\tLOOP",
		    "STEP\tLD\tGR2,GR3",
		    "\tLD\tGR3,A",
		    "\tCPA\tGR3,=0",
		    "\tJPL\tSKIPC",
		    "\tXOR\tGR1,=#FFFF",
		    "\tADDA\tGR1,=1",
		    "SKIPC\tXOR\tGR3,B",
		    "\tCPA\tGR3,=0",
		    "\tJZE\tSKIPD",
		    "\tJPL\tSKIPD",
		    "\tXOR\tGR2,=#FFFF",
		    "\tADDA\tGR2,=1",
		    "SKIPD\tPOP\tGR3",
		    "\tRET",
		    "A\tDS\t1",
		    "B\tDS\t1",
		    "\tEND"
		);
		
		return divStatement;
	}
	
	/**
	 * RDINT
	 * 
	 * @return
	 */
	public List<String> getRDINT() {
		List<String> rdintStatement = new ArrayList<>();
		rdintStatement = Arrays.asList(
			";============================================================",
		    "; RDINT: 入力装置から数値データを読み込み、",
		    "; その内容をGR2が指すアドレスに格納するサブルーチン",
		    "RDINT\tSTART",
		    "\tPUSH\t0,GR1\t; GR1の内容をスタックに退避",
		    "\tPUSH\t0,GR3\t; GR3の内容をスタックに退避",
		    "\tPUSH\t0,GR4\t; GR4の内容をスタックに退避",
		    "\tPUSH\t0,GR5\t; GR5の内容をスタックに退避",
		    "\tPUSH\t0,GR6\t; GR6の内容をスタックに退避",
		    "\tLD\tGR5,GR2\t; GR2が指す番地をGR5にコピー",
		    "\tLD\tGR2,=0\t; GR2を初期化",
		    "\tLD\tGR3,=0\t; GR3を初期化",
		    "\tIN\tINAREA,INLEN\t; 入力を受け取る",
		    "\tCPA\tGR3,INLEN",
		    "\tJZE\tERROR",
		    "\tLD\tGR4,INAREA,GR3",
		    "\tLAD\tGR3,1,GR3",
		    "\tLD\tGR6,GR4",
		    "\tCPL\tGR4,=#002D",
		    "\tJZE\tLOOP",
		    "\tCPL\tGR4,='0'",
		    "\tJMI\tERROR",
		    "\tCPL\tGR4,='9'",
		    "\tJPL\tERROR",
		    "\tXOR\tGR4,=#0030",
		    "\tADDA\tGR2,GR4",
		    "LOOP\tCPA\tGR3,INLEN",
		    "\tJZE\tCODE",
		    "\tLD\tGR1,=10",
		    "\tCALL\tMULT",
		    "\tLD\tGR4,INAREA,GR3",
		    "\tCPL\tGR4,='0'",
		    "\tJMI\tERROR",
		    "\tCPL\tGR4,='9'",
		    "\tJPL\tERROR",
		    "\tXOR\tGR4,=#0030",
		    "\tADDA\tGR2,GR4",
		    "\tLAD\tGR3,1,GR3",
		    "\tJUMP\tLOOP",
		    "CODE\tCPL\tGR6,=#002D",
		    "\tJNZ\tEND",
		    "\tXOR\tGR2,=#FFFF",
		    "\tLAD\tGR2,1,GR2",
		    "\tJUMP\tEND",
		    "\t;　エラーを出力する",
		    "ERROR\tOUT\tERRSTR,ERRLEN",
		    "END\tST\tGR2,0,GR5",
		    "\tLD\tGR2,GR5",
		    "\tPOP\tGR6",
		    "\tPOP\tGR5",
		    "\tPOP\tGR4",
		    "\tPOP\tGR3",
		    "\tPOP\tGR1",
		    "\tRET",
		    "ERRSTR\tDC\t'illegal input'",
		    "ERRLEN\tDC\t13",
		    "INAREA\tDS\t6",
		    "INLEN\tDS\t1",
		    "\tEND"
		);
		
		return rdintStatement;
	}
	
	public List<String> getRDCH() {
		List<String> rdchStatement = new ArrayList<>();
		rdchStatement = Arrays.asList(
			";============================================================",
		    "; RDCH: 入力装置から文字を読み込み、",
		    "; その内容をGR2が指すアドレスに格納するサブルーチン",
		    "RDCH\tSTART",
		    "\tIN\tINCHAR,INLEN",
		    "\tLD\tGR1,INCHAR",
		    "\tST\tGR1,0,GR2",
		    "\tRET",
		    "INCHAR\tDS\t1",
		    "INLEN\tDS\t1",
		    "\tEND"
		);
		
		return rdchStatement;
	}
	
	public List<String> getRDSTR() {
		List<String> rdstrStatement = new ArrayList<>();
		rdstrStatement = Arrays.asList(
			";============================================================",
		    "; RDSTR: 入力装置から、GR1の文字数を読み込む。",
		    "; 読み込んだ文字列は、GR2 が指すアドレスから順に格納される",
		    "RDSTR\tSTART",
		    "\tPUSH\t0,GR3\t; GR3の内容をスタックに退避",
		    "\tPUSH\t0,GR4\t; GR4の内容をスタックに退避",
		    "\tPUSH\t0,GR5\t; GR5の内容をスタックに退避",
		    "\tLAD\tGR4,0\t; GR4を初期化",
		    "\tIN\tINSTR,INLEN",
		    "LOOP\tCPA\tGR4,GR1",
		    "\tJZE\tEND",
		    "\tCPA\tGR4,INLEN",
		    "\tJZE\tEND",
		    "\tLD\tGR5,GR2",
		    "\tADDA\tGR5,GR4",
		    "\tLD\tGR3,INSTR,GR4",
		    "\tST\tGR3,0,GR5",
		    "\tLAD\tGR4,1,GR4",
		    "\tJUMP\tLOOP",
		    "END\tPOP\tGR5",
		    "\tPOP\tGR4",
		    "\tPOP\tGR3",
		    "\tRET",
		    "INSTR\tDS\t256",
		    "INLEN\tDS\t1",
		    "\tEND"
		);
		
		return rdstrStatement;
	}
	
	public List<String> getRDLN() {
		List<String> rdlnStatement = new ArrayList<>();
		rdlnStatement = Arrays.asList(
		    ";============================================================",
		    "; 入力装置からの文字列を改行まで読み飛ばすサブルーチン",
		    "RDLN\tSTART",
		    "\tIN\tINAREA,INLEN",
		    "\tRET",
		    "INAREA\tDS\t256",
		    "INLEN\tDS\t1",
		    "\tEND"
		);
		
		return rdlnStatement;
	}
	
	public List<String> getWRTINT() {
		List<String> wrtintStatement = new ArrayList<>();
		wrtintStatement = Arrays.asList(
			";============================================================",
		    "; GR2の内容（数値データ）を出力装置に書き出すサブルーチン",
		    "; このサブルーチンが呼ばれたとき，",
		    "; GR7には，出力用番地の先頭アドレスが，",
		    "; GR6には，現在出力用番地に入っている文字数が，",
		    "; それぞれ格納されている．",
		    "WRTINT\tSTART",
		    "\tPUSH\t0,GR1\t; GR1の内容をスタックに退避",
		    "\tPUSH\t0,GR2\t; GR2の内容をスタックに退避",
		    "\tPUSH\t0,GR3\t; GR3の内容をスタックに退避",
		    "\tPUSH\t0,GR2\t; 数値データをもう一度スタックに退避",
		    "\tLD\tGR3,=0\t; GR3はインデックスとして用いる",
		    "\t; 数値データが負数である場合は，正の数に変換",
		    "\tCPA\tGR2,=0",
		    "\tJPL\tLOOP1",
		    "\tXOR\tGR2,=#FFFF",
		    "\tADDA\tGR2,=1",
		    "\t; 数値データを変換しながら，バッファに格納",
		    "LOOP1\tLD\tGR1,GR2",
		    "\tLD\tGR2,=10",
		    "\tCALL\tDIV",
		    "\tXOR\tGR1,=#0030",
		    "\tST\tGR1,BUFFER,GR3",
		    "\tLAD\tGR3,1,GR3",
		    "\tCPA\tGR2,=0",
		    "\tJNZ\tLOOP1",
		    "\t; 数値データが負数であれば，'-'を追加",
		    "\tPOP\tGR2",
		    "\tCPA\tGR2,=0",
		    "\tJZE\tLOOP2",
		    "\tJPL\tLOOP2",
		    "\tLD\tGR1,='-'",
		    "\tST\tGR1,BUFFER,GR3",
		    "\tLAD\tGR3,1,GR3",
		    "\t; BUFFERを逆順にたどりながら，出力用バッファに格納",
		    "LOOP2\tLAD\tGR3,-1,GR3",
		    "\tLD\tGR1,BUFFER,GR3",
		    "\tLD\tGR2,GR7",
		    "\tADDA\tGR2,GR6",
		    "\tST\tGR1,0,GR2",
		    "\tLAD\tGR6,1,GR6",
		    "\tCPA\tGR3,=0",
		    "\tJNZ\tLOOP2",
		    "END\tPOP\tGR3",
		    "\tPOP\tGR2",
		    "\tPOP\tGR1",
		    "\tRET",
		    "BUFFER\tDS\t6",
		    "\tEND"
		);
		
		return wrtintStatement;
	}
	
	public List<String> getWRTCH() {
		List<String> wrtchStatement = new ArrayList<>();
		wrtchStatement = Arrays.asList(
			";============================================================",
		    "; GR2の内容（文字）を出力装置に書き出すサブルーチン",
		    "; このサブルーチンが呼ばれたとき，",
		    "; GR7には，出力用番地の先頭アドレスが，",
		    "; GR6には，現在出力用番地に入っている文字数が，",
		    "; それぞれ格納されている．",
		    "WRTCH\tSTART",
		    "\tPUSH\t0,GR1\t; GR1の内容をスタックに退避",
		    "\tLD\tGR1,GR7",
		    "\tADDA\tGR1,GR6\t; GR1に次の文字を格納する番地を代入",
		    "\tST\tGR2,0,GR1",
		    "\tLAD\tGR6,1,GR6",
		    "\tPOP\tGR1",
		    "\tRET",
		    "\tEND"
		);
		
		return wrtchStatement;
	}
	
	public List<String> getWRTSTR() {
		List<String> wrtstrStatement = new ArrayList<>();
		wrtstrStatement = Arrays.asList(
			";============================================================",
		    "; GR2の指すメモリ番地から，長さGR1の文字列を出力装置に書き出すサブルーチン",
		    "; このサブルーチンが呼ばれたとき，",
		    "; GR7には，出力用番地の先頭アドレスが，",
		    "; GR6には，現在出力用番地に入っている文字数が，",
		    "; それぞれ格納されている．",
		    "WRTSTR\tSTART",
		    "\tPUSH\t0,GR3\t; GR3の内容をスタックに退避",
		    "\tPUSH\t0,GR4\t; GR4の内容をスタックに退避",
		    "\tPUSH\t0,GR5\t; GR5の内容をスタックに退避",
		    "\tLAD\tGR3,0\t; GR3は制御変数として用いる",
		    "LOOP\tCPA\tGR3,GR1",
		    "\tJZE\tEND",
		    "\tLD\tGR4,GR2",
		    "\tADDA\tGR4,GR3\t; 出力する文字の格納番地を計算",
		    "\tLD\tGR5,0,GR4\t; 出力する文字をレジスタにコピー",
		    "\tLD\tGR4,GR7",
		    "\tADDA\tGR4,GR6\t; 出力先の番地を計算",
		    "\tST\tGR5,0,GR4\t; 出力装置に書き出し",
		    "\tLAD\tGR3,1,GR3",
		    "\tLAD\tGR6,1,GR6",
		    "\tJUMP\tLOOP",
		    "END\tPOP\tGR5",
		    "\tPOP\tGR4",
		    "\tPOP\tGR3",
		    "\tRET",
		    "\tEND"
		);
		
		return wrtstrStatement;
	}
	
	public List<String> getWRTLN() {
		List<String> wrtlnStatement = new ArrayList<>();
		wrtlnStatement = Arrays.asList(
			";============================================================",
		    "; 改行を出力装置に書き出すサブルーチン",
		    "; 実質的には，GR7で始まるアドレス番地から長さGR6の文字列を出力する",
		    "WRTLN\tSTART",
		    "\tPUSH\t0,GR1",
		    "\tPUSH\t0,GR2",
		    "\tPUSH\t0,GR3",
		    "\tST\tGR6,OUTLEN",
		    "\tLAD\tGR1,0",
		    "LOOP\tCPA\tGR1,OUTLEN",
		    "\tJZE\tEND",
		    "\tLD\tGR2,GR7",
		    "\tADDA\tGR2,GR1",
		    "\tLD\tGR3,0,GR2",
		    "\tST\tGR3,OUTSTR,GR1",
		    "\tLAD\tGR1,1,GR1",
		    "\tJUMP\tLOOP",
		    "END\tOUT\tOUTSTR,OUTLEN",
		    "\tLAD\tGR6,0\t; 文字列を出力して，GR6を初期化",
		    "\tPOP\tGR3",
		    "\tPOP\tGR2",
		    "\tPOP\tGR1",
		    "\tRET",
		    "OUTSTR\tDS\t256",
		    "OUTLEN\tDS\t1",
		    "\tEND"
		);
		
		return wrtlnStatement;
	}
}
