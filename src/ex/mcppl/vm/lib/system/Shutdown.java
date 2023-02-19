package ex.mcppl.vm.lib.system;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;
import ex.mcppl.vm.thread.ThreadManager;

import java.util.HashMap;

public class Shutdown implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {

        try {
            ExObject data = values.get("exit");

            if (data == null) throw new VMRuntimeException("system.shutdown : Not found value 'exit'", executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
            if(data.getType().equals(ExObject.Type.NULL))throw new VMRuntimeException("system.shutdown : Value 'exit' is null", executor.getPlayer(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);

            if (data instanceof ExValueName) {

                ExValue v=null;
                for(ExValue value: executor.getThread().getAllValues()){
                    if(value.getName().equals(((ExValueName)data).getName())){
                        v= value;
                    }
                }
                for(ExValue value1: ThreadManager.all_values){
                    if(value1.getName().equals(((ExValueName)data).getName())){
                        v= value1;
                    }
                }
                if(v==null)throw new VMRuntimeException("Not found value.",executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
                data = new ExInt(Integer.parseInt(v.getValue().getData()));
            }

            if(data.getType().equals(ExObject.Type.NULL))throw new VMRuntimeException("system.shutdown : Value 'exit' is null", executor.getPlayer(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);

            executor.shutdown(Integer.parseInt(data.getData()), executor.getPlayer());
        }catch (ClassCastException e){
            throw new VMRuntimeException("system.shutdown : Value 'exit' type isn't integer.", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
        }catch (NumberFormatException e){
            throw new VMRuntimeException("system.shutdown : Cannot convert value 'exit' to an integer.",executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
        }
        return new ExObject(ExObject.Type.VOID,"");
    }

    @Override
    public String getLibFunctionName() {
        return "shutdown";
    }
}
