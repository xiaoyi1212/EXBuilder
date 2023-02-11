package ex.mcppl.compile.ast;

import ex.mcppl.compile.LexToken;
import ex.mcppl.compile.VMException;
import ex.mcppl.compile.nbl.BoolExpression;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.buf.ExBool;
import ex.mcppl.vm.code.ByteCode;
import ex.mcppl.vm.code.GroupByteCode;
import ex.mcppl.vm.code.PushByteCode;

import java.util.ArrayList;

public class AstBoolStatement extends AstLeaf{
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
        return Element.AstType.BOOL;
    }

    public void setTds(ArrayList<LexToken.TokenD> tds) {
        this.tds = tds;
    }

    @Override
    public ByteCode eval(Element e) throws VMException {
        LexToken.TokenD td = getTokens();
        if(td.getData().equals("true")) return new PushByteCode(new ExBool(true));
        else if(td.getData().equals("false")) return new PushByteCode(new ExBool(false));
        else if(td.getData().equals("("))return new GroupByteCode(BoolExpression.calculate(e,BoolExpression.parseBoolExpr(tds)));
        else throw new VMException("Unknown type in boolean statement.",e.getPlayer());
    }
}
