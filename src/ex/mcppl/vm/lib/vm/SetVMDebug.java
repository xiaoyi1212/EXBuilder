package ex.mcppl.vm.lib.vm;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class SetVMDebug implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        ExObject data = values.get("status");


        if (data == null) throw new VMRuntimeException("vm.debug : Not found value 'status'", executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
        if (data instanceof ExValueName) {

            ExValue v=null;
            for(ExValue value: executor.getThread().getAllValues()){
                if(value.getName().equals(((ExValueName)data).getName())){
                    v= value;
                }
            }
            if(v==null)throw new VMRuntimeException("vm.debug : Not found value.", executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
            if(!(v.getValue() instanceof ExBool))throw new VMRuntimeException("The variable '"+v.getName()+"' cannot be converted to a boolean type.", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
            data = new ExBool(Boolean.parseBoolean(v.getValue().getData()));
        }
        if(!(data instanceof ExBool))throw new VMRuntimeException("The value '"+data.getData()+"' cannot be converted to a boolean type.", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
        executor.setDebug(Boolean.parseBoolean(data.getData()));

        return new ExObject();
    }

    @Override
    public String getLibFunctionName() {
        return "debug";
    }
}
