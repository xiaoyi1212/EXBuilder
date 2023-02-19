package ex.mcppl.vm.lib.util;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;
import ex.mcppl.vm.thread.ThreadManager;

import java.util.HashMap;

public class Map implements BasicLibrary {
    HashMap<String, HashMap<ExObject,ExObject>> map = new HashMap<>();

    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {

        for(String s:values.keySet()){
            ExObject obj = values.get(s);
            if (obj instanceof ExValueName) {
                ExValue v=null;
                for(ExValue valuev:executor.getThread().getAllValues()){
                    if(valuev.getName().equals(((ExValueName)obj).getName())){
                        v= valuev;
                    }
                }
                for(ExValue value: ThreadManager.all_values){
                    if(value.getName().equals(((ExValueName)obj).getName())){
                        v= value;
                    }
                }
                if(v==null)throw new VMRuntimeException("Not found value.",executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
                obj = new ExString(v.getValue().getData());
            }
            values.put(s,obj);
        }

        return executor(values,executor);
    }

    private ExObject executor(HashMap<String,ExObject> values,Executor executor)throws VMRuntimeException{
        ExObject status = values.get("status");if(status == null)throw new VMRuntimeException("util.map : status value is null.",executor.getOutput(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
        ExObject name = values.get("name");if(name == null)throw new VMRuntimeException("util.map : name value is null.",executor.getOutput(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
        ExObject key = values.get("key");if(key == null)throw new VMRuntimeException("util.map : var value is null.",executor.getOutput(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
        ExObject var = values.get("var");if(var == null)throw new VMRuntimeException("util.map : index value is null.",executor.getOutput(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);

        if(status.getData().equals("create")){
            if(map.containsKey(name.getData())) throw new VMRuntimeException("util.map : Create arrays has same name:"+name.getData(),executor.getOutput(), VMRuntimeException.Type.SAME_NAME_EXCEPTION);
            if(name.getType().equals(ExObject.Type.NULL)) throw new VMRuntimeException("util.map : Name is null.",executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
            map.put(name.getData(),new HashMap<>());
        }else if(status.getData().equals("put")){
            try {
                if (name.getType().equals(ExObject.Type.NULL))
                    throw new VMRuntimeException("util.map : Name is null.", executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                if (var.getType().equals(ExObject.Type.NULL))
                    throw new VMRuntimeException("util.map : Var is null", executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                if(key.getType().equals(ExObject.Type.NULL))
                    throw new VMRuntimeException("util.map : Key is null",executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                map.get(name.getData()).put(key,var);
            }catch (NullPointerException npe){
                throw new VMRuntimeException("util.map : Not found map:"+name.getData(),executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
            }
        }else if(status.getData().equals("getkey")){
            try {
                if (name.getType().equals(ExObject.Type.NULL)) throw new VMRuntimeException("util.list : Name is null.", executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                if(key.getType().equals(ExObject.Type.NULL))
                    throw new VMRuntimeException("util.map : Key is null",executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                executor.push(map.get(name.getData()).get(key));
                return map.get(name.getData()).get(key);
            }catch (NullPointerException npe){
                throw new VMRuntimeException("util.map : Not found map:"+name.getData(),executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
            }
        }else if(status.getData().equals("containskey")) {
            try {
                if (name.getType().equals(ExObject.Type.NULL))
                    throw new VMRuntimeException("util.map : Name is null.", executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                if(key.getType().equals(ExObject.Type.NULL))
                    throw new VMRuntimeException("util.map : Key is null",executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                boolean b = map.get(name.getData()).containsKey(key);
                executor.push(new ExBool(b));
                return new ExBool(b);
            } catch (NullPointerException npe) {
                throw new VMRuntimeException("util.map : Not found map:" + name.getData(), executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
            }
        }else if(status.getData().equals("remove")){
            try {
                if (name.getType().equals(ExObject.Type.NULL))
                    throw new VMRuntimeException("util.map : Name is null.", executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                if(key.getType().equals(ExObject.Type.NULL))
                    throw new VMRuntimeException("util.map : Key is null",executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                map.get(name.getData()).remove(key);
            }catch (NullPointerException npe){
                throw new VMRuntimeException("util.map : Not found map:"+name.getData(),executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
            }
        }else throw new VMRuntimeException("util.map : Unknown status code:"+status.getData(),executor.getOutput(), VMRuntimeException.Type.UNKNOWN_NAME );

        return new ExObject(ExObject.Type.VOID,"");
    }

    @Override
    public String getLibFunctionName() {
        return "map";
    }
}
