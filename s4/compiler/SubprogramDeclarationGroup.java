package enshud.s4.compiler;

public class SubprogramDeclarationGroup implements Element {
	private SubprogramDeclaration subprogramDeclaration;

	public SubprogramDeclarationGroup(SubprogramDeclaration subprogramDeclaration) {
		this.subprogramDeclaration = subprogramDeclaration;
	}

	@Override
	public void accept(Visitor visitor) throws SemanticException {
		visitor.visit(this);
	}

}
