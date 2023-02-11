package ex.mcppl.vm.lib;

import ex.mcppl.vm.lib.file.OpenFile;
import ex.mcppl.vm.lib.file.WriteFile;
import ex.mcppl.vm.lib.math.*;
import ex.mcppl.vm.lib.system.*;
import ex.mcppl.vm.lib.vm.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LibLoader {
    public HashMap<String,ArrayList<BasicLibrary>> libs = new HashMap<>();
    public HashMap<String,ArrayList<BasicLibrary>> loader = new HashMap<>();


    public void init(){
        ArrayList<BasicLibrary> system = new ArrayList<>();
        system.add(new Printf());
        system.add(new Shutdown());
        system.add(new GarbageCollection());
        system.add(new Input());
        system.add(new ExecuteCommand());
        system.add(new GetInfo());
        libs.put("system",system);

        ArrayList<BasicLibrary> math = new ArrayList<>();
        math.add(new Sin());
        math.add(new Pai());
        math.add(new E());
        math.add(new Cos());
        math.add(new Tan());
        math.add(new Sqrt());
        math.add(new Cbrt());
        libs.put("math",math);

        ArrayList<BasicLibrary> vm = new ArrayList<>();
        vm.add(new GetValueList());
        vm.add(new GetValueText());
        vm.add(new SetVMDebug());
        vm.add(new ValueTypeEquals());
        vm.add(new CreateThread());
        libs.put("vm",vm);

        ArrayList<BasicLibrary> file = new ArrayList<>();
        file.add(new OpenFile());
        file.add(new WriteFile());
        libs.put("file",file);
    }

    public boolean isLoaderLiba(String name){
        return loader.containsKey(name);
    }
    public boolean isLoaderFunction(String lib_name,String function_name){
        ArrayList<BasicLibrary> bls = loader.get(lib_name);BasicLibrary bl = null;
        if(bls==null)return false;
        for(BasicLibrary bll:loader.get(lib_name))if(bll.getLibFunctionName().equals(function_name))bl = bll;
        if(bl == null)return false;
        return true;
    }

    public boolean isLibs(String name){
        return libs.containsKey(name);
    }

    public boolean isFunction(String lib_name,String function_name){
        ArrayList<BasicLibrary> bls = libs.get(lib_name);BasicLibrary bl = null;
        if(bls==null)return false;
        for(BasicLibrary bll:libs.get(lib_name))if(bll.getLibFunctionName().equals(function_name))bl = bll;

        if(bl == null)return false;
        return true;
    }
}
