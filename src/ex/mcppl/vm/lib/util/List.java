package ex.mcppl.vm.lib.util;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.buf.ExString;
import ex.mcppl.vm.buf.ExValue;
import ex.mcppl.vm.buf.ExValueName;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.ArrayList;
import java.util.HashMap;

public class List implements BasicLibrary {

    HashMap<String, ArrayList<ExObject>> arrays = new HashMap<>();

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
                if(v==null)throw new VMRuntimeException("Not found value.",executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
                obj = new ExString(v.getValue().getData());
            }
            values.put(s,obj);
        }

        return executor(values,executor);
    }

    private ExObject executor(HashMap<String,ExObject> values,Executor executor)throws VMRuntimeException{
        ExObject status = values.get("status");if(status == null)throw new VMRuntimeException("util.list : status value is null.",executor.getOutput(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
        ExObject name = values.get("name");if(name == null)throw new VMRuntimeException("util.list : name value is null.",executor.getOutput(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
        ExObject var = values.get("var");if(var == null)throw new VMRuntimeException("util.list : var value is null.",executor.getOutput(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
        ExObject index = values.get("index");if(index == null)throw new VMRuntimeException("util.list : index value is null.",executor.getOutput(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);

        if(!index.getType().equals(ExObject.Type.INT))throw new VMRuntimeException("util.list : Cannot  index value  cast integer.",executor.getOutput(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);

        if(status.getData().equals("create")){
            if(arrays.containsKey(name.getData())) throw new VMRuntimeException("util.list : Create arrays has same name:"+name.getData(),executor.getOutput(), VMRuntimeException.Type.SAME_NAME_EXCEPTION);
            if(name.getType().equals(ExObject.Type.NULL)) throw new VMRuntimeException("util.list : Name is null.",executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
            arrays.put(name.getData(),new ArrayList<>());
        }else if(status.getData().equals("set")){
            try {
                if (name.getType().equals(ExObject.Type.NULL))
                    throw new VMRuntimeException("util.list : Name is null.", executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                if (var.getType().equals(ExObject.Type.NULL))
                    throw new VMRuntimeException("util.list : Var is null", executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                arrays.get(name.getData()).set(Integer.parseInt(index.getData()), var);
            }catch (NullPointerException npe){
                throw new VMRuntimeException("util.list : Not found array:"+name.getData(),executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
            }catch (IndexOutOfBoundsException ioobe){
                throw new VMRuntimeException("util.list : "+ioobe.getLocalizedMessage(),executor.getOutput(), VMRuntimeException.Type.EXCEPTION);
            }
        }else if(status.getData().equals("get")){
            try {
                if (name.getType().equals(ExObject.Type.NULL)) throw new VMRuntimeException("util.list : Name is null.", executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                executor.push(arrays.get(name.getData()).get(Integer.parseInt(index.getData())));
                return arrays.get(name.getData()).get(Integer.parseInt(index.getData()));
            }catch (NullPointerException npe){
                throw new VMRuntimeException("util.list : Not found array:"+name.getData(),executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
            }catch (IndexOutOfBoundsException ioobe){
                throw new VMRuntimeException("util.list : "+ioobe.getLocalizedMessage(),executor.getOutput(), VMRuntimeException.Type.EXCEPTION);
            }
        }else if(status.getData().equals("add")){
            try {
                if (name.getType().equals(ExObject.Type.NULL)) throw new VMRuntimeException("util.list : Name is null.", executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
                arrays.get(name.getData()).add(var);
            }catch (NullPointerException npe){
                throw new VMRuntimeException("util.list : Not found array:"+name.getData(),executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
            }
        }else throw new VMRuntimeException("util.list : Unknown status code:"+status.getData(),executor.getOutput(), VMRuntimeException.Type.UNKNOWN_NAME );

        return new ExObject(ExObject.Type.VOID,"");
    }

    @Override
    public String getLibFunctionName() {
        return "list";
    }
}
