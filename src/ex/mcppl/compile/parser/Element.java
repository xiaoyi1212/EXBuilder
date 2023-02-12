package ex.mcppl.compile.parser;

import ex.mcppl.manage.VMOutputStream;
import ex.mcppl.compile.VMException;
import ex.mcppl.compile.ast.AstHead;
import ex.mcppl.compile.ast.AstTree;
import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.VMloader;
import ex.mcppl.vm.code.ByteCode;
import ex.mcppl.vm.exe.FileByteCode;
import ex.mcppl.vm.exe.Function;
import ex.mcppl.vm.lib.LibLoader;
import ex.mcppl.vm.thread.ThreadManager;

import java.util.ArrayList;

public class Element {
    public enum AstType{
        VALUE,INVOKE,FUNCTION,INCLUDE,NBL,BASE,IF,WHILE,BOOL,SET,CATCH
    }
    AstHead head = new AstHead();
    public ArrayList<String> value_names = new ArrayList<>();
    public ArrayList<String> function_names = new ArrayList<>();
    VMOutputStream player;
    LibLoader loader;
    String filename;

    public Element(VMOutputStream player,LibLoader loader){
        this.player = player;
        this.loader = loader;
        loader.init();
    }

    public String getFilename() {
        return filename;
    }

    public LibLoader getLoader() {
        return loader;
    }

    public AstHead getHead() {
        return head;
    }

    public VMOutputStream getPlayer() {
        return player;
    }

    public VMloader parser(String file_name, VMOutputStream player) throws VMException, VMRuntimeException {

        this.filename = file_name;
        value_names.clear();
        function_names.clear();
        VMloader vMloader = new VMloader(this);
        ArrayList<ByteCode> bc = new ArrayList<>();
        for(AstTree tree:head.children()){
            ByteCode bcc = tree.eval(this);
            if(bcc instanceof Function) {
                vMloader.addFunction((Function) bcc);
                ThreadManager.all_functions.add((Function) bcc);
                continue;
            }
            bc.add(bcc);
        }

        vMloader.fbc=new FileByteCode(file_name,bc);

        //vMloader.launch(player,loader);

        value_names.clear();
        function_names.clear();

        return vMloader;
    }
}
