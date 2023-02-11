package ex.mcppl.compile.ast;

import ex.mcppl.compile.LexToken;
import ex.mcppl.compile.VMException;
import ex.mcppl.compile.nbl.IntNBLExpression;
import ex.mcppl.compile.nbl.NBLExpression;
import ex.mcppl.compile.nbl.NBLValue;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.buf.ExDouble;
import ex.mcppl.vm.code.ByteCode;
import ex.mcppl.vm.code.GroupByteCode;
import ex.mcppl.vm.code.PushByteCode;

import java.time.chrono.MinguoDate;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class AstNBLTree extends AstLeaf{

    private ArrayList<LexToken.TokenD> tds;

    @Override
    public Element.AstType getType() {
        return Element.AstType.NBL;
    }

    public void setTds(ArrayList<LexToken.TokenD> tds) {
        this.tds = tds;
    }

    @Override
    public ByteCode eval(Element e) throws VMException {
        boolean isnofinal = false;
        boolean isdouble = false;

        for(LexToken.TokenD td:tds){
            if(td.getToken().equals(LexToken.Token.KEY))isnofinal = true;
            if(td.getToken().equals(LexToken.Token.DOUBLE))isdouble=true;
        }

        if(isdouble) {
            double a;
            if (isnofinal) {
                NBLValue nbl = new NBLValue(tds);
                ArrayList<ByteCode> bc = nbl.calculate(e,nbl.nblLexValue());
                return new GroupByteCode(bc);
            } else {
                a = new NBLExpression(tds).run();
                return new PushByteCode(new ExDouble(a));
            }
        }else {
            IntNBLExpression ine = new IntNBLExpression(tds);
            ArrayList<ByteCode> bc = ine.calculate(e,ine.nblLexValue());
            return new GroupByteCode(bc);
        }

    }
}
