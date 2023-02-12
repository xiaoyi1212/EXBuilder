package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.thread.ExThread;

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
        boolean iserror = false;
        VMRuntimeException vre = null;
        for(ByteCode bc:bcs){
            try {
                bc.exe(executor);
            }catch (VMRuntimeException e){

                iserror = true;
                vre = e;
                break;
            }
        }
        if(iserror) {
            for(ByteCode bc:getBcs()){
                if(bc instanceof CatchByteCode){
                    if(vre.getType().name().equals(((CatchByteCode) bc).getType())){
                        executor.getThread().status = ExThread.Status.LOADING;
                        bc.exe(executor);
                        iserror = false;
                        executor.getThread().status = ExThread.Status.RUNNING;
                        break;
                    }
                }
            }
        }
        if(iserror) throw vre;
    }
}
