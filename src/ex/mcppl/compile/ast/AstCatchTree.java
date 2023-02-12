package ex.mcppl.compile.ast;

import ex.mcppl.compile.LexToken;
import ex.mcppl.compile.VMException;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.code.ByteCode;
import ex.mcppl.vm.code.CatchByteCode;
import ex.mcppl.vm.code.GroupByteCode;

import java.util.ArrayList;

public class AstCatchTree extends AstLeaf{
    private ArrayList<LexToken.TokenD> tds;
    private int index = 0;

    private LexToken.TokenD getTokens(){
        if(index > tds.size())return null;
        LexToken.TokenD td = tds.get(index);
        index += 1;
        return td;
    }

    @Override
    public Element.AstType getType() {
        return Element.AstType.CATCH;
    }

    public void setTds(ArrayList<LexToken.TokenD> tds) {
        index = 0;
        this.tds = tds;
    }

    @Override
    public ByteCode eval(Element e) throws VMException {

        try {
            LexToken.TokenD td = getTokens();
            if (!td.getToken().equals(LexToken.Token.KEY))
                throw new VMException("Unknown exception type: '" + td.getData() + "'.", e.getPlayer());

            String type = td.getData();

            ArrayList<ByteCode> bc = new ArrayList<>();
            for (AstTree tree : this.children()) {
                bc.add(tree.eval(e));
            }
            index = 0;

            return new CatchByteCode(type, new GroupByteCode(bc));
        }catch (IndexOutOfBoundsException e1){
            throw new VMException("Not found exception name in catch statement.",e.getPlayer());
        }
    }
}
