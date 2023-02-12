package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExBool;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.buf.ExValue;
import ex.mcppl.vm.buf.ExValueName;
import ex.mcppl.vm.exe.Executor;

public class AndByteCode implements ByteCode{
    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        ExObject obj = executor.getStack().pop();
        if(obj.getType().equals(ExObject.Type.BOOL));
        else if(obj instanceof ExValueName) {
            obj = ((ExValueName) obj).getValue(executor.getThread());
        }else if(obj instanceof ExValue) {
        }else if(obj instanceof ExBool){
        }else throw new VMRuntimeException("Cannot convert other types to Boolean types.", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
        if(!obj.getType().equals(ExObject.Type.BOOL))throw new VMRuntimeException("Cannot convert other types to Boolean types.", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);

        ExObject obj1 = executor.getStack().pop();
        if(!obj1.getType().equals(ExObject.Type.BOOL));
        else if(obj1 instanceof ExValueName) {
            obj1 = ((ExValueName) obj1).getValue(executor.getThread());
        }else if(obj1 instanceof ExValue) {
        }else if(obj1 instanceof ExBool){
        }else throw new VMRuntimeException("Cannot convert other types to Boolean types.", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
        if(!obj1.getType().equals(ExObject.Type.BOOL))throw new VMRuntimeException("Cannot convert other types to Boolean types.", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);

        executor.push(new ExBool(Boolean.parseBoolean(obj.getData())&&Boolean.parseBoolean(obj1.getData())));
    }
}
