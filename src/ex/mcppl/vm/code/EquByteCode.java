package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExBool;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.buf.ExValueName;
import ex.mcppl.vm.exe.Executor;

public class EquByteCode implements ByteCode{
    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        ExObject obj = executor.getStack().pop();
        if(obj instanceof ExValueName) {
            obj = ((ExValueName) obj).getValue();
        }


        ExObject equ = executor.getStack().peek();
        if(equ instanceof ExValueName) {
            equ = ((ExValueName) equ).getValue();
        }

        if(!obj.getType().equals(equ.getType()))executor.push(new ExBool(false));
        else executor.push(new ExBool(obj.getData().equals(equ.getData())));
    }
}
