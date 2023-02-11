package ex.mcppl.vm.lib.system;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class Printf implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException{
        try {
            ExObject data = values.get("out");


            if (data == null) throw new VMRuntimeException("system.printf : Not found value 'out'", executor.getPlayer());
            if (data instanceof ExValueName) {

                ExValue v=null;
                for(ExValue value:AllValueBuffer.values){
                    if(value.getName().equals(((ExValueName)data).getName())){
                        v= value;
                    }
                }
                if(v==null)throw new VMRuntimeException("Not found value.",executor.getPlayer());
                data = new ExString(v.getValue().getData());
            }
            executor.getOutput().info(data.getData());

        }catch (NullPointerException npe){
            throw new VMRuntimeException("system.printf : Unknown value name.", executor.getPlayer());
        }
        return new ExObject(ExObject.Type.VOID,"");
    }

    @Override
    public String getLibFunctionName() {
        return "printf";
    }
}
