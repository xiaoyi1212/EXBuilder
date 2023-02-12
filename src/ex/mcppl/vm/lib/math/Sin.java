package ex.mcppl.vm.lib.math;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExDouble;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class Sin implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        try {
            double d = Double.parseDouble(values.get("input").getData());
            executor.getStack().push(new ExDouble(Math.sin(d)));
            return new ExDouble(Math.sin(d));
        }catch (Exception e){
            throw new VMRuntimeException("The parameter of the function sin cannot be converted to type Double.", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
        }
    }

    @Override
    public String getLibFunctionName() {
        return "sin";
    }
}
