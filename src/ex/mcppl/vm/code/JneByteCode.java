package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExBool;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.exe.Executor;

public class JneByteCode implements ByteCode{
    GroupByteCode gbc;
    GroupByteCode bool;

    public JneByteCode(GroupByteCode bool,GroupByteCode gbc){
        this.bool = bool;
        this.gbc = gbc;
    }

    public GroupByteCode getBool() {
        return bool;
    }

    public GroupByteCode getGbc() {
        return gbc;
    }

    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        for(ByteCode bc:bool.getBcs()){
            bc.exe(executor);
        }

        ExObject obj = executor.getStack().pop();
        if(!obj.getType().equals(ExObject.Type.BOOL))throw new VMRuntimeException("Cannot convert other types to Boolean types.", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
        ExBool booll = (ExBool) obj; boolean b = Boolean.parseBoolean(booll.getData());
        if(b)for(ByteCode bc:gbc.getBcs())bc.exe(executor);

    }
}
