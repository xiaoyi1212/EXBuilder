package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.exe.Executor;

import java.util.ArrayList;

public class GroupByteCode implements ByteCode{
    ArrayList<ByteCode> bcs;
    public GroupByteCode(ArrayList<ByteCode> bc){
        this.bcs = bc;
    }

    public ArrayList<ByteCode> getBcs() {
        return bcs;
    }

    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        for(ByteCode bc:bcs){
            bc.exe(executor);
        }
    }
}
