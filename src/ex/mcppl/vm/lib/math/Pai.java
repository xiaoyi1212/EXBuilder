package ex.mcppl.vm.lib.math;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExDouble;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class Pai implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        executor.getStack().push(new ExDouble(3.14159265358979323846));
        return new ExDouble(3.14159265358979323846);
    }

    @Override
    public String getLibFunctionName() {
        return "pai";
    }
}
