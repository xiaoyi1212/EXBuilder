package ex.mcppl.vm.lib.vm;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.code.InvokeByteCode;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.exe.Function;
import ex.mcppl.vm.lib.BasicLibrary;
import ex.mcppl.vm.thread.ExThread;

import java.util.HashMap;

public class CreateThread extends Thread implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        if(values.get("name")==null)throw new VMRuntimeException("Not found function 'thread' value 'name'", executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
        String name = values.get("name").getData();
        Function function1 = null;
        for(Function function:executor.getFunctions()){
            if(function.getName().equals(name)){
                function1 = function;
            }
        }
        if(function1 == null) throw new VMRuntimeException("vm.valuedict : Not found function:'"+name, executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);

        Function finalFunction = function1;



        Thread thread = new Thread(() -> {
            try {
                InvokeByteCode ibc = new InvokeByteCode(finalFunction.getLib(), finalFunction.getName(), new HashMap<>());
                Executor e = executor;
                e.getStack().clear();
                e.getThread().getAllValues().clear();
                ibc.exe(e);
            }catch (VMRuntimeException e){
            }

        });
        ExThread thread1 = new ExThread(thread,executor.getOutput());
        executor.addThread(thread1);

        return new ExObject(ExObject.Type.VOID,"");
    }

    @Override
    public String getLibFunctionName() {
        return "thread";
    }

}
