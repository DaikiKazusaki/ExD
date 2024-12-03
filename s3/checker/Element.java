package enshud.s3.checker;

public interface Element {
    public abstract void accept(Visitor visitor);
}
