package ex.mcppl.vm.lib.vm;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.AllValueBuffer;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.buf.ExValue;
import ex.mcppl.vm.buf.ExValueName;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.exe.Function;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class GetValueList implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        if(values.get("name")==null||values.get("var")==null)throw new VMRuntimeException("Not found function 'valuedict' value 'name' or 'var'", executor.getPlayer());
        String name = values.get("name").getData();
        String value = values.get("var").getData();
        for(Function function:executor.getFunctions()){
            if(function.getName().equals(name)){

                ExObject obj = function.getValues().get(value);
                if(obj instanceof ExValueName){
                    ExValueName name1 = (ExValueName) obj;
                    for(ExValue value1: AllValueBuffer.values){
                        if(value1.getName().equals(name1.getName())){
                            obj = value1.getValue();
                        }
                    }
                }

                if(obj==null)throw new VMRuntimeException("vm.valuedict : Not found function:'"+function.getName()+"' value:"+value+".", executor.getPlayer());
                executor.push(obj);
            }
        }
        return new ExObject();
    }

    @Override
    public String getLibFunctionName() {
        return "valuedict";
    }
}
