package ex.mcppl.vm.lib.system;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class Shutdown implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {

        try {
            ExObject data = values.get("exit");

            if (data == null) throw new VMRuntimeException("system.shutdown : Not found value 'exit'", executor.getPlayer());
            if(data.getType().equals(ExObject.Type.NULL))throw new VMRuntimeException("system.shutdown : Value 'exit' is null", executor.getPlayer());

            if (data instanceof ExValueName) {

                ExValue v=null;
                for(ExValue value: AllValueBuffer.values){
                    if(value.getName().equals(((ExValueName)data).getName())){
                        v= value;
                    }
                }
                if(v==null)throw new VMRuntimeException("Not found value.",executor.getPlayer());
                data = new ExInt(Integer.parseInt(v.getValue().getData()));
            }

            if(data.getType().equals(ExObject.Type.NULL))throw new VMRuntimeException("system.shutdown : Value 'exit' is null", executor.getPlayer());

            executor.shutdown(Integer.parseInt(data.getData()), executor.getPlayer());
        }catch (ClassCastException e){
            throw new VMRuntimeException("system.shutdown : Value 'exit' type isn't integer.", executor.getPlayer());
        }catch (NumberFormatException e){
            throw new VMRuntimeException("system.shutdown : Cannot convert value 'exit' to an integer.",executor.getPlayer());
        }
        return new ExObject(ExObject.Type.VOID,"");
    }

    @Override
    public String getLibFunctionName() {
        return "shutdown";
    }
}
