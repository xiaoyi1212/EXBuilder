package ex.mcppl.vm;

import ex.mcppl.manage.VMOutputStream;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.exe.Executor;
import ex.mcppl.vm.exe.FileByteCode;
import ex.mcppl.vm.exe.Function;
import ex.mcppl.vm.thread.ExThread;

import java.util.ArrayList;

public class VMloader {
    public FileByteCode fbc;
    public ArrayList<String> output_buffer;
    public Executor e;
    public Element element;
    public ArrayList<Function> functions;

    @Override
    public String toString() {
        return fbc.getFilename()+"||"+functions;
    }

    public void addFunction(Function function){
        functions.add(function);
    }

    public VMloader(Element e){
        this.element = e;
        this.functions = new ArrayList<>();
    }

    public void launch(VMOutputStream player, ExThread thread) throws VMRuntimeException {


        e = Executor.load(fbc, player, element.getLoader(),functions);
        e.getFunctions().addAll(functions);
        output_buffer = e.start(player, thread);

    }
}
