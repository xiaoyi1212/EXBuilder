package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.AllValueBuffer;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.buf.ExValue;
import ex.mcppl.vm.exe.Executor;

import java.util.ArrayList;

public class MovByteCode implements ByteCode{
    String text;
    String name;
    ArrayList<ByteCode> bc;

    public MovByteCode(String name,String text, ArrayList<ByteCode> values){
        this.text = text;
        this.bc = values;
        this.name = name;
    }

    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        try {
            for (ByteCode bcc : bc) {
                bcc.exe(executor);
            }

            ExObject Return = executor.getStack().pop();

            for(ExValue v:AllValueBuffer.values){
                if(v.getName().equals(name)){
                    v.setValue(Return);
                    return;
                }
            }

            AllValueBuffer.values.add(new ExValue(name, text, Return));

        }catch (Exception e){
            e.printStackTrace();
            throw new VMRuntimeException("Create value throw exception:"+e.getLocalizedMessage(),executor.getPlayer());
        }
    }
}
