package enshud.s4.compiler;

public class Symbol {
	private String name;
	private String type;
	private String isArray;
	private String scope;
	private int size;
	
	public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
    public String isArray() {
    	return isArray;
    }

    public String getScope() {
        return scope;
    }
    
    public int getSize() {
    	return size;
    }
}
