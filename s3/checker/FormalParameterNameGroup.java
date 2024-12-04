package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterNameGroup {
	List<FormalParameterName> formalParameterName = new ArrayList<>();
	
	public void accept(Visitor visitor) {
		for(FormalParameterName item: formalParameterName) {
			item.accept(visitor);
		}
	}
}
