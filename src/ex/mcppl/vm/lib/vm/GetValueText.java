package ex.mcppl.vm.lib.vm;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class GetValueText implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        ExObject name = values.get("var");
        if(name instanceof ExValueName) name = new ExString(((ExValueName) name).getName());

        for(ExValue value: AllValueBuffer.values) {
            if (value.getName().equals(name.getData())) {
                executor.getStack().push(new ExString(value.getText()));
                return new ExObject();
            }
        }

        throw new VMRuntimeException("vm.text : Not found value name.", executor.getPlayer());
    }

    @Override
    public String getLibFunctionName() {
        return "text";
    }
}
