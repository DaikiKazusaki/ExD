package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class SubprogramDeclarationGroup implements Element {
	private List<SubprogramDeclaration> subprogramDeclaration = new ArrayList<>();
	
	public SubprogramDeclarationGroup(List<SubprogramDeclaration> subprogramDeclaration) {
		this.subprogramDeclaration = subprogramDeclaration;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		for (SubprogramDeclaration item: subprogramDeclaration) {
			item.accept(visitor);
		}
	}
}
