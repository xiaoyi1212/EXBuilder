package ex.mcppl.vm.lib.file;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class WriteFile implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        try {
            ExObject data = values.get("out");
            ExObject name = values.get("name");
            if (data == null) throw new VMRuntimeException("file.writefile : Not found value 'out'", executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
            if(name == null)throw new VMRuntimeException("file.writefile : Not found value 'name'", executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
            if (data instanceof ExValueName) {
                ExValue v=null;
                for(ExValue value: executor.getThread().getAllValues()){
                    if(value.getName().equals(((ExValueName)data).getName())){
                        v= value;
                    }
                }
                if(v==null)throw new VMRuntimeException("Not found value.",executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
                data = new ExString(v.getValue().getData());
            }
            if (name instanceof ExValueName) {
                ExValue v=null;
                for(ExValue value: executor.getThread().getAllValues()){
                    if(value.getName().equals(((ExValueName)name).getName())){
                        v= value;
                    }
                }
                if(v==null)throw new VMRuntimeException("Not found value.",executor.getPlayer(), VMRuntimeException.Type.NOT_FOUND_VALUE_EXCEPTION);
                name = new ExString(v.getValue().getData());
            }

            if(name instanceof ExNone||data instanceof ExNone)throw new VMRuntimeException("file.writefile : filename or data is null.", executor.getPlayer(), VMRuntimeException.Type.NULL_PRINT_EXCEPTION);

            try(BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(name.getData()))){
                output.write(data.getData().getBytes(StandardCharsets.UTF_8));
                executor.getStack().push(new ExBool(true));
            }catch (IOException e){
                executor.getStack().push(new ExBool(false));
            }
        }catch (NullPointerException npe){
            throw new VMRuntimeException("file.writefile : Unknown value name.", executor.getPlayer(), VMRuntimeException.Type.UNKNOWN_NAME);
        }
        return new ExObject(ExObject.Type.VOID,"");
    }

    @Override
    public String getLibFunctionName() {
        return "writefile";
    }
}
