package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExBool;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.exe.Executor;

public class LopByteCode implements ByteCode{
    GroupByteCode bool,group;
    public LopByteCode(GroupByteCode bool, GroupByteCode group){
        this.bool = bool;
        this.group = group;
    }

    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        boolean isbreak = false;
        while (true) {
            for(ByteCode bc:bool.getBcs())bc.exe(executor);
            ExObject obj = executor.getStack().pop();
            if(!obj.getType().equals(ExObject.Type.BOOL))throw new VMRuntimeException("Cannot convert other types to Boolean types.", executor.getPlayer());
            ExBool booll = (ExBool) obj;
            boolean b = Boolean.parseBoolean(booll.getData());
            if(!b)break;
            for(ByteCode bc:group.getBcs()){
                if(bc instanceof BekByteCode){
                    isbreak = true;
                    break;
                }
                if(bc instanceof JneByteCode){
                    for(ByteCode bcc:bool.getBcs()){
                        bcc.exe(executor);
                    }

                    ExObject obj11 = executor.getStack().pop();
                    if(!obj11.getType().equals(ExObject.Type.BOOL))throw new VMRuntimeException("Cannot convert other types to Boolean types.", executor.getPlayer());
                    ExBool booll11 = (ExBool) obj11; boolean b1 = Boolean.parseBoolean(booll11.getData());
                    if(b1) {
                        for (ByteCode bccc : ((JneByteCode) bc).getGbc().getBcs()) {
                            if (bccc instanceof BekByteCode) {
                                isbreak = true;
                                break;
                            }
                            bccc.exe(executor);
                        }
                        if(isbreak)break;
                    }
                    continue;
                }
                bc.exe(executor);
            }
            if(isbreak)break;
        }
    }
}
