package ex.mcppl.vm.lib.system;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class ExecuteCommand implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        try {
            ExObject data = values.get("cmd");


            if (data == null) throw new VMRuntimeException("system.sendcmd : Not found value 'cmd'", executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
            if (data instanceof ExValueName) {

                ExValue v = null;
                for (ExValue value : executor.getThread().getAllValues()) {
                    if (value.getName().equals(((ExValueName) data).getName())) {
                        v = value;
                    }
                }
                if (v == null) throw new VMRuntimeException("Not found value.", executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
                data = new ExString(v.getValue().getData());
            }
            Process p = Runtime.getRuntime().exec(data.getData());

        }catch (Exception e){
            throw new VMRuntimeException("system.sendcmd : "+e.getLocalizedMessage(),executor.getPlayer(), VMRuntimeException.Type.EXCEPTION);
        }
        return new ExObject();
    }

    @Override
    public String getLibFunctionName() {
        return "sendcmd";
    }
}
