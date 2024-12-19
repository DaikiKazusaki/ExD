package enshud.s4.compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WriteFile {
	private List<String> outputList = new ArrayList<>();
	
	public WriteFile() {
		// 以下の3行はcaslには必ず必要
		outputList.add("CASL" + '\t' + "START" + '\t' + "BEGIN");
		outputList.add("BEGIN" + '\t' + "LAD" + '\t' + "GR6, 0");
		outputList.add('\t' + "LAD" + '\t' + "GR7, LIBBUF");
	}
	
	/**
	 * outputListに要素を追加していくメソッド
	 * 
	 * @param line
	 */
	public void addOutputList(String line) {
		outputList.add(line);
	}
	
	/**
	 * outputListを取得するメソッド
	 * 
	 * @return
	 */
	public List<String> getOutputList(){
		return outputList;
	}
	
	/**
	 * casファイルに書き込みを行うメソッド
	 * 
	 * @param outputFileName
	 */
	public void writeFile(final String outputFileName) {	
		// CASLの最後に必要な要素を追加
		outputList.add("LIBBUF" + '\t' + "DS" + '\t' + "256");
		outputList.add('\t' + "END");
		
		try{
			// ファイルが存在しない場合は新規作成
            if (Files.notExists(Paths.get(outputFileName))) {
                Files.createFile(Paths.get(outputFileName));
            }
            
            // ファイル書き込み
            Files.write(Paths.get(outputFileName), outputList);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
