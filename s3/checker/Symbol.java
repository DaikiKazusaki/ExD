package enshud.s3.checker;

public class Symbol extends Entry {
	private String name;
	private String type;
<<<<<<< HEAD
	private int size = claclateSize(name, type);
=======
>>>>>>> f34e927478a730505bd3101200faafb7d4a41f51
	
	public Symbol(String name, String type) {
        this.name = name;
        this.type = type;
    }
	
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
<<<<<<< HEAD

    public int getSize() {
        return size;
    }
    
    /**
	 * 変数のサイズを計算するメソッド
	 * 
	 * @param name: 変数名，char型で使用する
	 * @param type: 型
	 * @return size: 変数のサイズ
	 */
	public int claclateSize(String name, String type) {
		int size;
		
		if (type.equals("true") || type.equals("false")) {
			size = 1;
		} else if (type.equals("integer")) {
			size = 16;
		} else {
			size = 8 * name.length();
		}
		
		return size;
	}
=======
>>>>>>> f34e927478a730505bd3101200faafb7d4a41f51
}
