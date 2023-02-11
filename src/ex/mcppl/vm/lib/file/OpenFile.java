package ex.mcppl.vm.lib.file;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class OpenFile implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        ExObject name = values.get("name");
        if (name == null) throw new VMRuntimeException("file.openfile : Not found value 'out'", executor.getPlayer());
        if (name instanceof ExValueName) {
            ExValue v=null;
            for(ExValue value: AllValueBuffer.values){
                if(value.getName().equals(((ExValueName)name).getName())){
                    v= value;
                }
            }
            if(v==null)throw new VMRuntimeException("Not found value.",executor.getPlayer());
            name = new ExString(v.getValue().getData());
        }
        if(name instanceof ExNone) throw new VMRuntimeException("file.openfile : filename is null", executor.getPlayer());

        try(BufferedReader reader = new BufferedReader(new FileReader(name.getData()))){
            String line;
            StringBuilder buf = new StringBuilder();
            while ((line = reader.readLine())!=null) buf.append(line);
            ExString s = new ExString(buf.toString());
            executor.getStack().push(new ExString(buf.toString()));
            return s;
        }catch (IOException e){
            executor.getStack().push(new ExNone());
            return new ExNone();
        }
    }

    @Override
    public String getLibFunctionName() {
        return "openfile";
    }
}
