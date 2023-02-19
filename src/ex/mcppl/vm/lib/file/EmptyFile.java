package ex.mcppl.vm.lib.file;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;
import ex.mcppl.vm.thread.ThreadManager;

import java.io.File;
import java.util.HashMap;

public class EmptyFile implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        HashMap<String,ExObject> value = new HashMap<>();
        for(String s:values.keySet()){
            ExObject obj = values.get(s);
            if (obj instanceof ExValueName) {
                ExValue v=null;
                for(ExValue valuev:executor.getThread().getAllValues()){
                    if(valuev.getName().equals(((ExValueName)obj).getName())){
                        v= valuev;
                    }
                }
                for(ExValue value1: ThreadManager.all_values){
                    if(value1.getName().equals(((ExValueName)obj).getName())){
                        v= value1;
                    }
                }
                if(v==null)throw new VMRuntimeException("Not found value.",executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
                obj = new ExString(v.getValue().getData());
            }
            value.put(s,obj);
        }

        return executor(value,executor);
    }

    private ExObject executor(HashMap<String,ExObject> values,Executor executor) throws VMRuntimeException{
        ExObject obj = values.get("name");
        if(obj == null)throw new VMRuntimeException("file.emptyfile : File name is null.",executor.getOutput(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);
        return new ExBool(new File(obj.getData()).exists());
    }

    @Override
    public String getLibFunctionName() {
        return "emptyfile";
    }
}
