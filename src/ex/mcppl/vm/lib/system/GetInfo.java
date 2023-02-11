package ex.mcppl.vm.lib.system;

import ex.mcppl.manage.Main;
import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.lib.BasicLibrary;

import java.util.HashMap;

public class GetInfo implements BasicLibrary {
    @Override
    public ExObject invoke(HashMap<String, ExObject> values, Executor executor) throws VMRuntimeException {
        try {
            ExObject data = values.get("info");


            if (data == null) throw new VMRuntimeException("system.sysinfo : Not found value 'info'", executor.getPlayer());
            if (data instanceof ExValueName) {

                ExValue v=null;
                for(ExValue value: AllValueBuffer.values){
                    if(value.getName().equals(((ExValueName)data).getName())){
                        v= value;
                    }
                }
                if(v==null)throw new VMRuntimeException("Not found value.", executor.getPlayer());
                data = new ExString(v.getValue().getData());
            }
            switch (data.getData()){
                case "os.name":executor.push(new ExString(System.getProperty("os.name")));break;
                case "vm.path":executor.push(new ExString(getAppPath(GetInfo.class)));break;
                case "vm.version":executor.push(new ExString(Main.vm_version));break;
            }
        }catch (NullPointerException npe){
            throw new VMRuntimeException("system.sysinfo : Unknown value name.", executor.getPlayer());
        }
        return new ExObject(ExObject.Type.VOID,"");

    }
    private static String getAppPath(Class cls){
        ClassLoader loader =cls.getClassLoader();
        String clsName =cls.getName() + ".class";
        Package pack =cls.getPackage();
        String path = "";
        if(pack !=null){
            String packName =pack.getName();
            clsName =clsName.substring(packName.length() + 1);
            if(packName.indexOf( ".") < 0) path =packName + "/";
            else{
                int start = 0,end = 0;
                end =packName.indexOf( ".");
                while(end != - 1){
                    path =path +packName.substring(start,end) + "/";
                    start =end + 1;
                    end =packName.indexOf( ".",start);
                }
                path =path +packName.substring(start) + "/";
            }
        }
        java.net.URL url =loader.getResource(path +clsName);
        String realPath =url.getPath();
        int pos =realPath.indexOf( "file:");
        if(pos > - 1) realPath =realPath.substring(pos + 5);
        pos =realPath.indexOf(path +clsName);
        realPath =realPath.substring( 0,pos - 1);
        if(realPath.endsWith( "!"))
            realPath =realPath.substring( 0,realPath.lastIndexOf( "/"));
        try{
            realPath =java.net.URLDecoder.decode(realPath, "utf-8");
        } catch(Exception e){ throw new RuntimeException(e);}
        return realPath;
    }

    @Override
    public String getLibFunctionName() {
        return "sysinfo";
    }
}
