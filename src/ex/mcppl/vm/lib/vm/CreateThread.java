package ex.mcppl.vm.lib.vm;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.exe.Function;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class CreateThread implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        if(values.get("name")==null)throw new VMRuntimeException("Not found function 'thread' value 'name'", executor.getPlayer());
        String name = values.get("name").getData();
        Function function1 = null;
        for(Function function:executor.getFunctions()){
            if(function.getName().equals(name)){
                function1 = function;
            }
        }
        if(function1 == null) throw new VMRuntimeException("vm.valuedict : Not found function:'"+name, executor.getPlayer());

        Function finalFunction = function1;
        Thread thread = new Thread(() -> {
            try {
                finalFunction.invoke(executor, new HashMap<>());
            }catch (VMRuntimeException vre){
            }
        });
        thread.start();
        executor.getThreads().add(thread);

        return new ExObject(ExObject.Type.VOID,"");
    }

    @Override
    public String getLibFunctionName() {
        return "thread";
    }
}
