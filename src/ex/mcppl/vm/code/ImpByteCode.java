package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.exe.Executor;

public class ImpByteCode implements ByteCode{
    String libname;
    public ImpByteCode(String libname){
        this.libname = libname;
    }
    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        if(executor.getLoader().isLibs(libname)){
            executor.getLoader().loader.put(libname, executor.getLoader().libs.get(libname));
            return;
        }
        if(libname.contains(".")){
            String name = libname.split("\\.")[0];
            return;
        }
        throw new VMRuntimeException("Not found library:"+libname, executor.getPlayer());
    }
}
