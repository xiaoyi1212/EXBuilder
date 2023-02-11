package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.exe.Executor;

public class PushByteCode implements ByteCode{
    ExObject obj;
    public PushByteCode(ExObject obj){
        this.obj = obj;
    }

    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        executor.push(obj);
    }

    @Override
    public String toString() {
        return "push : "+obj.getData();
    }
}
