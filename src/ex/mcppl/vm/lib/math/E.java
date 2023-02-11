package ex.mcppl.vm.lib.math;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExDouble;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class E implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        executor.getStack().push(new ExDouble(2.718281828459045));
        return new ExDouble(2.718281828459045);
    }

    @Override
    public String getLibFunctionName() {
        return "e";
    }
}
