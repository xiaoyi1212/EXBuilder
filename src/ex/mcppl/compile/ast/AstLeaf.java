package ex.mcppl.compile.ast;

import ex.mcppl.compile.LexToken;
import ex.mcppl.compile.VMException;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.code.ByteCode;
import ex.mcppl.vm.exe.Executor;

import java.util.ArrayList;

public class AstLeaf extends AstTree{
    private ArrayList<AstTree> empty;
    private LexToken.TokenD token;
    public Element.AstType type;
    public AstLeaf(){
        empty = new ArrayList<>();
    }

    public AstLeaf(LexToken.TokenD token){
        type = Element.AstType.BASE;
        empty = new ArrayList<>();
        this.token = token;
    }

    @Override
    public AstTree child(int i) {
        return empty.get(i);
    }

    @Override
    public int getChildren() {
        return empty.size();
    }

    @Override
    public ArrayList<AstTree> children() {
        return empty;
    }

    @Override
    public void location(int em) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < em; i++) sb.append("\t");
        System.out.println(sb.toString()+type+">"+token+">>"+empty.size());
        for (AstTree tree:empty)tree.location(em+1);
    }

    @Override
    public Element.AstType getType() {
        return type;
    }

    @Override
    public ByteCode eval(Element e) throws VMException {
        return new ByteCode() {
            @Override
            public void exe(Executor executor) throws VMRuntimeException {

            }
        };
    }
}
