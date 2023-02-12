package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.buf.ExValue;
import ex.mcppl.vm.exe.Executor;

import java.util.ArrayList;

public class SetByteCode implements ByteCode{

    String name;
    ArrayList<ByteCode> bc;

    public SetByteCode(String name, ArrayList<ByteCode> values){

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


            for (ExValue value : executor.getThread().getAllValues()) {
                if (value.getName().equals(name)) {
                    if (!value.getValue().getClass().isInstance(Return))
                        throw new VMRuntimeException("The new value of the variable does not match the original value type:" + name, executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
                    value.setValue(Return);
                }
            }
        }catch (VMRuntimeException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new VMRuntimeException("Create value throw exception:"+e.getLocalizedMessage(), executor.getPlayer(), VMRuntimeException.Type.EXCEPTION);
        }
    }
}
