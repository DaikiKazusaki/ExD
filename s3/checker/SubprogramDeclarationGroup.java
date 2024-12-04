package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class SubprogramDeclarationGroup {
	List<SubprogramDeclaration> subprogramDeclaration = new ArrayList<>();
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
		for(SubprogramDeclaration item: subprogramDeclaration) {
			item.accept(visitor);
		}
	}
}
