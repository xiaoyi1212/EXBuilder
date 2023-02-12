package ex.mcppl.vm.lib.vm;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.exe.Function;
import ex.mcppl.vm.lib.BasicLibrary;
import ex.mcppl.vm.thread.ThreadManager;

import java.util.HashMap;

public class GetValueList implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        if(values.get("name")==null||values.get("var")==null||values.get("lib")==null)throw new VMRuntimeException("Not found function 'valuedict' value 'name' or 'var' or 'lib'", executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
        String name = values.get("name").getData();
        String lib = values.get("lib").getData();
        String value = values.get("var").getData();



        for(Function function:executor.getFunctions()){
            if(function.getName().equals(name)){
                ExObject obj = function.getValues().get(value);
                if(obj instanceof ExValueName name1){
                    for(ExValue value1: executor.getThread().getAllValues()){
                        if(value1.getName().equals(name1.getName())){
                            obj = value1.getValue();
                        }
                    }
                }

                if(obj==null)throw new VMRuntimeException("vm.valuedict : Not found function:'"+function.getName()+"' value:"+value+".", executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
                executor.push(obj);
                return obj;
            }
        }

        for(Function function: ThreadManager.all_functions){
            if(function.getLib().equals(lib)&&function.getName().equals(name)){




                ExObject obj = function.getValues().get(value);

                ExObject oo = null;

                if(obj instanceof ExValueName name1){
                    for(ExValue value1: executor.getThread().getAllValues()){
                        if(value1.getName().equals(name1.getName())){
                            oo = value1.getValue();
                        }
                    }
                }else oo = obj;

                if(oo==null)throw new VMRuntimeException("vm.valuedict : function:'"+function.getName()+"' value:"+value+" is null.", executor.getPlayer(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                executor.push(oo);
                return oo;
            }
        }

        return new ExObject();
    }

    @Override
    public String getLibFunctionName() {
        return "valuedict";
    }
}
