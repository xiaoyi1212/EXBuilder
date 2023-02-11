package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.exe.Executor;

public interface ByteCode {
    public void exe(Executor executor) throws VMRuntimeException;
    //public byte[] getBinary();
}
