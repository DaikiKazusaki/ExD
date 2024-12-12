package enshud.s3.checker;

public class Symbol {
	private String name;
	private String type;
	
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
}
