package ex.mcppl.compile.ast;

import ex.mcppl.compile.LexToken;
import ex.mcppl.compile.VMException;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.buf.ExNone;
import ex.mcppl.vm.code.ByteCode;
import ex.mcppl.vm.code.GroupByteCode;
import ex.mcppl.vm.code.MovByteCode;
import ex.mcppl.vm.code.PushByteCode;

import java.util.ArrayList;

public class AstValueTree extends AstLeaf{

    ArrayList<LexToken.TokenD> tds;

    private int index = 0;

    private LexToken.TokenD getTokens(){
        if(index > tds.size())return null;
        LexToken.TokenD td = tds.get(index);
        index += 1;
        return td;
    }

    public AstValueTree(){
    }

    public void setTds(ArrayList<LexToken.TokenD> tds) {
        index = 0;
        this.tds = tds;
    }

    @Override
    public Element.AstType getType() {
        return Element.AstType.VALUE;
    }

    @Override
    public ByteCode eval(Element e) throws VMException {
        index = 0;
        String name,text;ArrayList<ByteCode> bcs = new ArrayList<>();
        LexToken.TokenD td = getTokens();

        if(!td.getToken().equals(LexToken.Token.KEY))throw new VMException("Variable names must not be defined with other types.",e.getPlayer());
        name = td.getData();td = getTokens();
        if(!(td.getToken().equals(LexToken.Token.SEM)&&td.getData().equals(":")))throw new VMException("Unknown lex in value creator.",e.getPlayer());
        td = getTokens();
        if(!td.getToken().equals(LexToken.Token.STR)) throw new VMException("Unknown value text.",e.getPlayer());
        text = td.getData();

        if(e.value_names.contains(name))throw new VMException("Cannot define variables with the same variable name.",e.getPlayer());

        for(AstTree tree:this.children()){
            if(tree instanceof AstNoneTree) {
                bcs.add(new PushByteCode(new ExNone()));
                break;
            }
            ByteCode bc = tree.eval(e);
            if(bc instanceof GroupByteCode){
                bcs.addAll(((GroupByteCode) bc).getBcs());
            }else bcs.add(bc);
        }
        e.value_names.add(name);
        index = 0;
        return new MovByteCode(name,text,bcs);// new MovByteCode();
    }
}
