package ex.mcppl.vm.lib.vm;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class ValueTypeEquals implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        ExObject value_first = values.get("f");
        ExObject value_second = values.get("s");
        if(value_first instanceof ExValueName&&value_second instanceof ExValueName){
            ExValue v=null;
            for(ExValue value: AllValueBuffer.values){
                if(value.getName().equals(((ExValueName)value_first).getName())){
                    v= value;
                }
            }
            if(v==null)throw new VMRuntimeException("Not found value.", executor.getPlayer());

            ExValue va=null;
            for(ExValue value: AllValueBuffer.values){
                if(value.getName().equals(((ExValueName)value_second).getName())){
                    va= value;
                }
            }
            if(va==null)throw new VMRuntimeException("Not found value.", executor.getPlayer());

            executor.push(new ExBool(v.getValue().getClass().isInstance(va.getValue())));

        }else if(value_first instanceof ExValue&&value_second instanceof ExValue){
            executor.push(new ExBool(((ExValue) value_first).getValue().getClass().isInstance(((ExValue) value_second).getValue())));
        }else throw new VMRuntimeException("vm.equals : The parameter is not of type and is not a variable.", executor.getPlayer());
        return new ExObject();
    }

    @Override
    public String getLibFunctionName() {
        return "equals";
    }
}
