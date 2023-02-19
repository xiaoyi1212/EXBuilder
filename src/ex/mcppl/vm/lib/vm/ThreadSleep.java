package ex.mcppl.vm.lib.vm;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class ThreadSleep implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        HashMap<String,ExObject> value = new HashMap<>();

        for(String s:values.keySet()){
            ExObject obj = values.get(s);
            if (obj instanceof ExValueName) {
                ExValue v=null;
                for(ExValue valuev:executor.getThread().getAllValues()){
                    if(valuev.getName().equals(((ExValueName)obj).getName())){
                        v= valuev;
                    }
                }
                if(v==null)throw new VMRuntimeException("Not found value.",executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
                obj = new ExString(v.getValue().getData());
            }
            value.put(s,obj);
        }

        return executor(value,executor);

    }

    private ExObject executor(HashMap<String, ExObject> value, Executor executor) throws VMRuntimeException{
        try {
            if (value.get("sleep") == null)
                throw new VMRuntimeException("vm.sleep : Not found out value", executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);


            if (!(value.get("sleep") instanceof ExInt))
                throw new VMRuntimeException("vm.sleep : Cannot cast integer value", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
            Thread.sleep(Long.parseLong(value.get("sleep").getData()));
        }catch (InterruptedException e){
            throw new VMRuntimeException("vm.sleep : Thread sleep throe InterruptedException",executor.getPlayer(), VMRuntimeException.Type.VM_ERROR);
        }
        return new ExObject(ExObject.Type.VOID,"");
    }

    @Override
    public String getLibFunctionName() {
        return "sleep";
    }
}
