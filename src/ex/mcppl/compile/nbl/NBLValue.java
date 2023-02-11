package ex.mcppl.compile.nbl;

import ex.mcppl.compile.LexToken;
import ex.mcppl.compile.VMException;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.buf.AllValueBuffer;
import ex.mcppl.vm.buf.ExDouble;
import ex.mcppl.vm.buf.ExValue;
import ex.mcppl.vm.buf.ExValueName;
import ex.mcppl.vm.code.*;

import java.util.ArrayList;
import java.util.Stack;

public class NBLValue {

    ArrayList<LexToken.TokenD> tds;
    public NBLValue(ArrayList<LexToken.TokenD> tds){
        this.tds = tds;
    }

    public ArrayList<LexToken.TokenD> nblLexValue() {
        Stack<LexToken.TokenD> opStack = new Stack<>();
        ArrayList<LexToken.TokenD> suffixList = new ArrayList<>();
        for (int i = 0; i < tds.size(); i++) {
            LexToken.TokenD tmp;LexToken.TokenD t = tds.get(i);
            switch (t.getData()){
                case "(":opStack.push(t);break;
                case "+": case "-":
                    while (opStack.size() != 0){
                        tmp = opStack.pop();
                        if(tmp.getData().equals("(")){opStack.push(tmp);break;}suffixList.add(tmp);
                    }opStack.push(t);break;
                case "*": case "/": case "%":
                    while (opStack.size() != 0) {
                        tmp = opStack.pop();
                        if (tmp.getData().equals("+")||tmp.getData().equals("-")||tmp.getData().equals("(")) {opStack.push(tmp);break;}suffixList.add(tmp);
                    }opStack.push(t);break;
                case ")":
                    while (!opStack.isEmpty()) {
                        tmp = opStack.pop();
                        if (tmp.getData().equals("(")) {break;}suffixList.add(tmp);
                    }break;
                default: suffixList.add(t);break;
            }
        }
        while (!opStack.isEmpty()) {suffixList.add(opStack.pop());}return suffixList;
    }
    public ArrayList<ByteCode> calculate(Element e,ArrayList<LexToken.TokenD> tds)throws VMException {
        ArrayList<ByteCode> bbc = new ArrayList<>();
        for(LexToken.TokenD td:tds){
            if(td.getToken().equals(LexToken.Token.NAME)) throw new RuntimeException("The keyword appears when parsing the expression");
            if(td.getToken().equals(LexToken.Token.NUM))bbc.add(new PushByteCode(new ExDouble(Double.parseDouble(td.getData()))));
            else if(td.getToken().equals(LexToken.Token.KEY)){
                ExValueName valuea = null;
                if(e.value_names.contains(td.getData())){
                    valuea = new ExValueName(td.getData());
                }else throw new VMException("Unknown value name.",e.getPlayer());
                bbc.add(new PushByteCode(valuea));
            }
            else if(td.getToken().equals(LexToken.Token.SEM)){
                if (td.getData().equals("+")) bbc.add(new AddByteCode());
                else if (td.getData().equals("-")) bbc.add(new SubByteCode());
                else if (td.getData().equals("*")) bbc.add(new MulByteCode());
                else if (td.getData().equals("/")) bbc.add(new DivByteCode());
                else if (td.getData().equals("%")) bbc.add(new RemByteCode());
            }
        }




        return bbc;
    }
}
