package enshud.s3.checker;

public class Symbol {
	private String name;
	private String type;
	private String lineNumber;
	private int size;
	private boolean isAssigned;
	
	public Symbol(String name, String type, String lineNumber, int size, boolean isAssigned) {
        this.name = name;
        this.type = type;
        this.lineNumber = lineNumber;
        this.size = size;
        this.isAssigned = isAssigned;
    }
	
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public int getSize() {
        return size;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    // セッター
    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }
}
