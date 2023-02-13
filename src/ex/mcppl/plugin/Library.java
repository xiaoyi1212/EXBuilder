package ex.mcppl.plugin;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.buf.ExString;
import ex.mcppl.vm.buf.ExValue;
import ex.mcppl.vm.buf.ExValueName;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public abstract class Library implements BasicLibrary {
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

    public abstract ExObject executor(HashMap<String,ExObject> values,Executor executor) throws VMRuntimeException;
}
