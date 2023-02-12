package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExBool;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.buf.ExValue;
import ex.mcppl.vm.buf.ExValueName;
import ex.mcppl.vm.exe.Executor;

public class NotByteCode implements ByteCode{
    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        ExObject obj = executor.getStack().pop();
        if(obj.getType().equals(ExObject.Type.BOOL));
        else if(obj instanceof ExValueName) {
            obj = ((ExValueName) obj).getValue(executor.getThread());
        }else if(obj instanceof ExValue) {
        }else if(obj instanceof ExBool){
        }else throw new VMRuntimeException("Cannot convert other types to Boolean types in not", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
        if(!obj.getType().equals(ExObject.Type.BOOL))throw new VMRuntimeException("Cannot convert other types to Boolean types in not", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);

        executor.push(new ExBool(!Boolean.parseBoolean(obj.getData())));
    }
}
