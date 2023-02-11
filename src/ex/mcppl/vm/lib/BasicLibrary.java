package ex.mcppl.vm.lib;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.exe.Executor;

import java.util.HashMap;

public interface BasicLibrary {
    public ExObject invoke(HashMap<String,ExObject> values, Executor executor) throws VMRuntimeException;
    public String getLibFunctionName();
}
