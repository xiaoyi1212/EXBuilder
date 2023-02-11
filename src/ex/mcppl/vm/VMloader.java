package ex.mcppl.vm;

import ex.mcppl.VMOutputStream;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.exe.FileByteCode;
import ex.mcppl.vm.thread.ExThread;

import java.util.ArrayList;

public class VMloader {
    public ArrayList<FileByteCode> fbc = new ArrayList<>();
    public ArrayList<String> output_buffer;
    public Executor e;
    public Element element;

    public VMloader(Element e){
        this.element = e;
    }

    public void launch(VMOutputStream player, ExThread thread) throws VMRuntimeException{
        for(FileByteCode fbcc:fbc){
            e = Executor.load(fbcc,player, element.getLoader());
            output_buffer = e.start(player,thread);
        }
    }
}
