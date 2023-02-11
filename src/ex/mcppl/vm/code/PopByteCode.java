package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.exe.Executor;

public class PopByteCode implements ByteCode{
    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        executor.getStack().pop();
    }
}
