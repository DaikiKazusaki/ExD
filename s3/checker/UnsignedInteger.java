package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class UnsignedInteger {
	List<String> number = new ArrayList<>();

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
