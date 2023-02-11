package ex.mcppl.compile.ast;

import ex.mcppl.compile.LexToken;
import ex.mcppl.compile.VMException;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.manage.FileManage;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.code.ByteCode;
import ex.mcppl.vm.code.InvokeByteCode;

import java.util.ArrayList;
import java.util.HashMap;

public class AstInvokeTree extends AstLeaf{
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
        return Element.AstType.INVOKE;
    }

    public void setTds(ArrayList<LexToken.TokenD> tds) {
        this.tds = tds;
        index = 0;
    }

    @Override
    public ByteCode eval(Element e) throws VMException{
        index = 0;
        String lib = null;
        boolean isoutfile = false;
        String function = null;
        HashMap<String,ExObject> values = new HashMap<>();
        LexToken.TokenD td = tds.get(0);
        index += 1;

        if(!(td.getToken().equals(LexToken.Token.SEM)&&td.getData().equals(".")))throw new VMException("Unknown parser in invoke function",e.getPlayer());td = getTokens();
        if((td.getToken().equals(LexToken.Token.KEY)&&e.getLoader().isLibs(td.getData()))||(td.getToken().equals(LexToken.Token.NAME)&&td.getData().equals("this"))) lib = td.getData();

        if(FileManage.files.contains(td.getData()+".exf")){
            lib = td.getData();
            isoutfile = true;
        }

        if(lib==null)throw new VMException("Unknown library name:"+td.getData(),e.getPlayer());
        td = getTokens();

        if(!(td.getToken().equals(LexToken.Token.SEM)&&td.getData().equals(".")))throw new VMException("Unknown parser in invoke function",e.getPlayer());td = getTokens();

        if(td.getToken().equals(LexToken.Token.KEY)&&e.getLoader().isFunction(lib,td.getData())) function = td.getData();
        else if(td.getToken().equals(LexToken.Token.KEY)&&e.function_names.contains(td.getData())) function = td.getData();
        if(isoutfile)function = td.getData();
        if(function==null)throw new VMException("Unknown function name.",e.getPlayer());
        td = getTokens();
        if(!(td.getToken().equals(LexToken.Token.LP)&&td.getData().equals("(")))throw new VMException("Unknown parser in invoke function",e.getPlayer());
        td = getTokens();

        try {
            do {
                String l = null;
                ExObject f = null;
                do {
                    if (td.getToken().equals(LexToken.Token.LR) && td.getData().equals(")")) break;
                    if (td.getToken().equals(LexToken.Token.KEY)) l = td.getData();
                    td = getTokens();
                    if (!(td.getToken().equals(LexToken.Token.SEM) && td.getData().equals(":"))) throw new VMException("Unknown function value parser",e.getPlayer());
                    td = getTokens();

                    if (td.getToken().equals(LexToken.Token.STR)) f = new ExString(td.getData());
                    else if (td.getToken().equals(LexToken.Token.NUM)) f = new ExInt(Integer.parseInt(td.getData()));
                    else if (td.getToken().equals(LexToken.Token.DOUBLE))
                        f = new ExDouble(Double.parseDouble(td.getData()));
                    else if (td.getToken().equals(LexToken.Token.KEY)) f = new ExValueName(td.getData());
                    else if (td.getToken().equals(LexToken.Token.NAME)){
                        if(td.getData().equals("true")||td.getData().equals("false"))f = new ExBool(Boolean.parseBoolean(td.getData()));
                        else throw new VMException("Cannot usr key in function value.",e.getPlayer());
                    }
                    else throw new VMException("Unknown value type.",e.getPlayer());
                    td = getTokens();
                } while (!((td.getToken().equals(LexToken.Token.SEM) && td.getData().equals(",")) || (td.getToken().equals(LexToken.Token.LR) && td.getData().equals(")"))));
                values.put(l, f);
                td = getTokens();
            } while (!(td.getToken().equals(LexToken.Token.LR) && td.getData().equals(")")));
        }catch (IndexOutOfBoundsException e1){
        }catch (Exception e1){
            throw new VMException("Unknown parser error:"+e1.getLocalizedMessage(),e.getPlayer());
        }
        index = 0;
        return new InvokeByteCode(lib,function,values);
    }
}
