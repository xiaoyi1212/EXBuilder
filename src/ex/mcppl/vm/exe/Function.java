package ex.mcppl.vm.exe;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.code.ByteCode;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;

public class Function implements ByteCode{
    String name;
    String lib;
    ArrayList<ByteCode> bc;
    HashMap<String,ExObject> values;
    public Function(String libname,String name,ArrayList<ByteCode> bc){
        this.lib = libname;
        this.name = name;
        this.bc = bc;
    }

    public HashMap<String, ExObject> getValues() {
        return values;
    }

    public String getLib() {
        return lib;
    }

    public String getName() {
        return name;
    }

    public ExObject invoke(Executor executor,HashMap<String,ExObject> values) throws VMRuntimeException{
        try{
            this.values = values;


            for(ByteCode bcc:bc) bcc.exe(executor);
            return executor.getStack().peek();
        }catch (EmptyStackException e){
            return new ExObject();
        }
    }

    @Override
    public String toString() {
        return getLib()+"."+getName();
    }

    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        executor.addFunction(this);
    }
}
