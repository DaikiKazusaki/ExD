package enshud.s3.checker;

public class ProgramName {
	private String name;
	
	public ProgramName(String name) {
		this.name = name;
	}
	
	public String getProgramName() {
		return name;
	}

	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		
	}
}
