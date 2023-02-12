package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.exe.Executor;

public class CatchByteCode implements ByteCode{
    GroupByteCode gbc;
    String type;
    public CatchByteCode(String type,GroupByteCode gbc){
        this.gbc = gbc;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        gbc.exe(executor);
    }
}
