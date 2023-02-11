package ex.mcppl.compile.ast;

import ex.mcppl.compile.VMException;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.code.ByteCode;

import java.util.ArrayList;

public abstract class AstTree {
    public abstract AstTree child(int i);
    public abstract int getChildren();
    public abstract ArrayList<AstTree> children();
    public abstract void location(int em);
    public abstract Element.AstType getType();
    public abstract ByteCode eval(Element e) throws VMException;
}
