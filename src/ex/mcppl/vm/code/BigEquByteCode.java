package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExBool;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.buf.ExValueName;
import ex.mcppl.vm.exe.Executor;

public class BigEquByteCode implements ByteCode{
    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        ExObject obj = executor.getStack().pop();
        if(obj instanceof ExValueName) {
            obj = ((ExValueName) obj).getValue(executor.getThread());
        }


        ExObject equ = executor.getStack().peek();
        if(equ instanceof ExValueName) {
            equ = ((ExValueName) equ).getValue(executor.getThread());
        }

        if(!(obj.getType().equals(ExObject.Type.DOUBLE)||obj.getType().equals(ExObject.Type.INT))) throw new VMRuntimeException("Cannot in value cast integer/double",executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
        if(!(equ.getType().equals(ExObject.Type.DOUBLE)||equ.getType().equals(ExObject.Type.INT))) throw new VMRuntimeException("Cannot in value cast integer/double",executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);

        executor.push(new ExBool( Double.parseDouble(equ.getData())>=Double.parseDouble(obj.getData())));
    }
}
