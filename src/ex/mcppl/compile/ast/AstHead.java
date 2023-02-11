package ex.mcppl.compile.ast;

import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.code.ByteCode;

import java.util.ArrayList;

public class AstHead extends AstTree{

    static ArrayList<AstTree> at = new ArrayList<>();

    public AstTree child(int i) {
        return at.get(i);
    }

    @Override
    public int getChildren() {
        return at.size();
    }

    @Override
    public ArrayList<AstTree> children() {
        return at;
    }

    @Override
    public void location(int em) {
        System.out.println("HEAD");
    }

    @Override
    public Element.AstType getType() {
        return Element.AstType.BASE;
    }

    @Override
    public ByteCode eval(Element e) {
        return null;
    }
}
