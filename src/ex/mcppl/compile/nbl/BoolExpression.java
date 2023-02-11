package ex.mcppl.compile.nbl;

import ex.mcppl.compile.LexToken;
import ex.mcppl.compile.VMException;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.buf.ExBool;
import ex.mcppl.vm.buf.ExDouble;
import ex.mcppl.vm.buf.ExInt;
import ex.mcppl.vm.buf.ExValueName;
import ex.mcppl.vm.code.*;

import java.util.ArrayList;
import java.util.Stack;

public class BoolExpression {

    public static ArrayList<ByteCode> calculate(Element e,ArrayList<LexToken.TokenD> tds) throws VMException {
        ArrayList<ByteCode> ret = new ArrayList<>();
        for (LexToken.TokenD td:tds){
            if(td.getToken().equals(LexToken.Token.NAME)){
                if(td.getData().equals("true")) ret.add(new PushByteCode(new ExBool(true)));
                else if(td.getData().equals("false")) ret.add(new PushByteCode(new ExBool(false)));
            }else if(td.getToken().equals(LexToken.Token.SEM)){
                switch (td.getData()){
                    case "!":ret.add(new NotByteCode());break;
                    case "&":ret.add(new AndByteCode());break;
                    case "|":ret.add(new OrByteCode());break;
                    case "==":ret.add(new EquByteCode());break;
                }
            }else if(td.getToken().equals(LexToken.Token.KEY)) {
                ExValueName valuea = null;
                if (e.value_names.contains(td.getData())) {
                    valuea = new ExValueName(td.getData());
                } else throw new VMException("Unknown value name.",e.getPlayer());
               ret.add(new PushByteCode(valuea));
            }else if(td.getToken().equals(LexToken.Token.DOUBLE))ret.add(new PushByteCode(new ExDouble(Double.parseDouble(td.getData()))));
            else if(td.getToken().equals(LexToken.Token.NUM))ret.add(new PushByteCode(new ExInt(Integer.parseInt(td.getData()))));
        }

        return ret;
    }

    public static ArrayList<LexToken.TokenD> parseBoolExpr(ArrayList<LexToken.TokenD> expression) {
        Stack<LexToken.TokenD> stack = new Stack<>();
        boolean b = false;
        LexToken.TokenD temp;
        ArrayList<LexToken.TokenD> out = new ArrayList<>();

        for(LexToken.TokenD c : expression) {
            switch (c.getData()){
                case "!":b = !b;break;
                case "&", "|","==":
                    while (stack.size() != 0){
                        temp = stack.pop();
                        if(temp.getData().equals("(")){stack.push(temp);break;}out.add(temp);
                    }stack.push(c);break;
                case "(":stack.push(c);break;
                case ")":
                while (!stack.isEmpty()) {
                    temp = stack.pop();
                    if (temp.getData().equals("(")) break;
                    out.add(temp);
                }break;
                default: out.add(c);break;
            }
        }
        if(b) stack.push(new LexToken.TokenD(LexToken.Token.SEM,"!"));
        while (!stack.isEmpty()) {out.add(stack.pop());}

        return out;
    }
}
