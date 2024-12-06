package enshud.s3.checker;

import java.util.ArrayList;
import java.util.List;

public class SubprogramDeclarationGroup {
	private SubprogramDeclaration subprogramDeclaration;
	
	public SubprogramDeclarationGroup(SubprogramDeclaration subprogramDeclaration) {
		this.subprogramDeclaration = subprogramDeclaration;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
