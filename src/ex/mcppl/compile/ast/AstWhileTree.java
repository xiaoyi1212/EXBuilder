package ex.mcppl.compile.ast;

import ex.mcppl.compile.LexToken;
import ex.mcppl.compile.VMException;
import ex.mcppl.compile.nbl.BoolExpression;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.buf.ExBool;
import ex.mcppl.vm.code.*;

import java.util.ArrayList;

public class AstWhileTree extends AstLeaf{
    private ArrayList<LexToken.TokenD> tds;
    private ArrayList<LexToken.TokenD> bool;
    private int index = 0;
    private int bool_index = 0;

    private LexToken.TokenD getTokens(){
        if(index > tds.size())return null;
        LexToken.TokenD td = tds.get(index);
        index += 1;
        return td;
    }
    private LexToken.TokenD getBoolTokens(){
        if(bool_index > bool.size())return null;
        LexToken.TokenD td = bool.get(bool_index);
        bool_index += 1;
        return td;
    }

    @Override
    public Element.AstType getType() {
        return Element.AstType.WHILE;
    }

    public void setTds(ArrayList<LexToken.TokenD> tds) {
        this.tds = tds;
    }

    public void setBool(ArrayList<LexToken.TokenD> bool) {
        this.bool = bool;
    }

    @Override
    public ByteCode eval(Element e) throws VMException {
        LexToken.TokenD td = getBoolTokens();
        bool.remove(bool.size()-1);boolean isbool = false;

        for(LexToken.TokenD t:bool)
            if (t.getToken().equals(LexToken.Token.SEM) || t.getToken().equals(LexToken.Token.LP)) {
                isbool = true;
                break;
            }

        ArrayList<ByteCode> bc = new ArrayList<>(),bol = new ArrayList<>();
        for(AstTree TREE:children())bc.add(TREE.eval(e));

        if(!isbool){
            if(td.getData().equals("true")) {
                bol.add(new PushByteCode(new ExBool(true)));
                return new LopByteCode(new GroupByteCode(bol),new GroupByteCode(bc));
            }else if(td.getData().equals("false")){
                return new NolByteCode();
            }else throw new VMException("Cannot use keywords in Boolean expressions in While statements.", e.getPlayer());
        }else if(isbool){
            return new LopByteCode(new GroupByteCode(BoolExpression.calculate(e,BoolExpression.parseBoolExpr(bool))),new GroupByteCode(bc));
        }else throw new VMException("Cannot in while statement boolean use key.",e.getPlayer());
    }
}
