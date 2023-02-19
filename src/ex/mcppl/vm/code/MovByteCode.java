package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.buf.ExValue;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.thread.ThreadManager;

import java.util.ArrayList;

public class MovByteCode implements ByteCode{
    String text;
    String name;
    ArrayList<ByteCode> bc;

    public enum TYPE{
        LOCAL,GLOBAL
    }

    public MovByteCode(String name,String text, ArrayList<ByteCode> values,TYPE type){
        this.text = text;
        this.bc = values;
        this.name = name;
        this.type = type;
    }

    TYPE type;

    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        try {

            for (ByteCode bcc : bc) {
                bcc.exe(executor);
            }

            ExObject Return = executor.getStack().pop();

            for (ExValue v : executor.getThread().getAllValues()) {
                if (v.getName().equals(name)) {
                    v.setValue(Return);
                    return;
                }
            }

            if(type.equals(TYPE.GLOBAL)){
                ThreadManager.all_values.add(new ExValue(name,text,Return));
            }else if(type.equals(TYPE.LOCAL)) executor.getThread().getAllValues().add(new ExValue(name, text, Return));
        }catch (VMRuntimeException vre){
            throw vre;
        }catch (Exception e){
            e.printStackTrace();
            throw new VMRuntimeException("Create value throw exception:"+e.getLocalizedMessage(),executor.getPlayer(), VMRuntimeException.Type.EXCEPTION);
        }
    }
}
