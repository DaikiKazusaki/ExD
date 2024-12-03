package enshud.casl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import jp.kusumotolab.jcasl2.JCasl2;
import jp.kusumotolab.jcomet2.JComet2;

/**
 * !!! このコードは編集禁止 !!!
 * （Compilerテストが本クラスを利用するため）
 *
 * 本クラスは自由に利用しても良いが，開発対象ではないことに注意．
 *
 */
public class CaslSimulator {
	private static final String LIBCAS = "data/cas/lib.cas";

	public static void main(final String[] args) throws IOException {
		CaslSimulator.run("data/cas/normal01.cas", "tmp/out.ans");
	}

	/**
	 * 第一引数で指定されたCASL IIプログラムファイルを読み込み，PyCaslアセンブラとPyCometシミュレータで実行する．
	 * 最終的なPyCometの実行結果は第二引数で指定されたファイルに書き出される．
	 *
	 * 使い方：
	 * CaslSimulator.run("data/cas/normal01.cas", "tmp/out.ans");
	 *
	 * @param inputFileName 入力casファイル名
	 * @param outputFileName 出力ファイル名
	 * @param params 標準入力に渡す文字列（オプショナル）
	 */
	public static void run(final String inputFileName, final String outputFileName, final String... params) {
		final String comFileName = removeFileNameExtension(outputFileName) + ".com";
		if (Files.notExists(Paths.get(inputFileName))) {
			System.err.println("[CaslSimulator] input file \"" + inputFileName + "\" does not exist.");
			return;
		}
		try {
			Files.deleteIfExists(Paths.get(comFileName));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		JCasl2.execute(new File(inputFileName), new File(comFileName));
		JComet2.execute(new File(comFileName), new File(outputFileName), params);
	}

	/**
	 * 第一引数で指定されたcasファイルの末尾にdata/cas/lib.casを追加する．
	 *
	 * 使い方：
	 * CaslSimulator.appendLibcas("tmp/out.cas");
	 *
	 * @param fileName 対象casファイル名
	 */
	public static void appendLibcas(final String fileName) {
		if (Files.notExists(Paths.get(fileName))) {
			System.err.println("[CaslSimulator] input file \"" + fileName + "\" does not exist.");
			return;
		}
		try {
			final List<String> libcas = Files.readAllLines(Paths.get(LIBCAS));
			Files.write(Paths.get(fileName), libcas, StandardOpenOption.APPEND);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private static String removeFileNameExtension(final String fileName) {
		final int n = fileName.lastIndexOf('.');
		if (n > 0) {
			return fileName.substring(0, n);
		}
		return fileName;
	}
}
