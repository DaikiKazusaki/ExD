package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class CharGroup {
	List<StringElement> stringElement = new ArrayList<>();
	
	public CharGroup(List<StringElement> stringElement) {
		this.stringElement = stringElement;
	}
	
	public List<StringElement> getStringElement(){
		return stringElement;
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
