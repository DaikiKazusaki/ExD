package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class UnsignedInteger {
	private List<String> number = new ArrayList<>();

	public UnsignedInteger(List<String> number) {
		this.number = number;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	public List<String> getNumber() {
		return number;
	}
}
