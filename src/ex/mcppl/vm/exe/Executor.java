package ex.mcppl.vm.exe;

import ex.mcppl.VMOutputStream;
import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.code.ByteCode;
import ex.mcppl.vm.lib.LibLoader;
import ex.mcppl.vm.thread.ExThread;

import java.util.ArrayList;
import java.util.Stack;

public class Executor {
    private Executor(){
    }
    public enum Status{
        RUNNING,LOADING,STOP,ERROR
    }
    Stack<ExObject> stack;
    FileByteCode fbc;
    ExThread thread;

    ArrayList<Function> functions;
    ArrayList<String> output_buffer;
    static Executor e;
    VMOutputStream player;
    LibLoader loader;
    boolean debug;
    public int stack_var;

    public VMOutputStream getOutput() {
        return player;
    }

    ArrayList<Thread> threads;

    public static Executor load(FileByteCode fbc, VMOutputStream player, LibLoader loader){
        e = new Executor();
        e.player = player;
        e.loader = loader;
        e.output_buffer = new ArrayList<>();
        e.stack = new Stack<>();
        e.fbc = fbc;
        e.functions = new ArrayList<>();
        e.debug = false;
        e.stack_var = 0;
        e.threads = new ArrayList<>();
        return e;
    }

    public ArrayList<Thread> getThreads() {
        return threads;
    }

    public ArrayList<String> getOutputBuffer() {
        return output_buffer;
    }

    public VMOutputStream getPlayer() {
        return player;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void addFunction(Function function){
        functions.add(function);
    }

    public LibLoader getLoader() {
        return loader;
    }

    public ArrayList<Function> getFunctions(){
        return functions;
    }

    public Stack<ExObject> getStack() {
        return stack;
    }
    public void push(ExObject obj){
        stack.push(obj);
        stack_var += 1;
    }

    public void shutdown(int e,VMOutputStream player){
        player.info("[EXVM-ThreadManager]: 正在强行终止解释器...");
        player.info("[EXVM-EXIT]:程序已退出,退出代码: "+e);
        System.exit(e);
    }

    public void setThreadStatus(ExThread.Status status) {
        this.thread.status = status;
    }

    public ArrayList<String> start(VMOutputStream player, ExThread thread) throws VMRuntimeException {
        this.thread = thread;
        long a = System.currentTimeMillis();

        try {
            for (ByteCode bc : fbc.getBcs()) {

                if(e.getOutputBuffer().size() > 100){
                    thread.status = ExThread.Status.ERROR;
                    shutdown(-1,player);
                    throw new VMRuntimeException("Out of memory error: 输出缓冲区溢出",player);
                }

                bc.exe(e);
            }
            player.info("[EXVM-EXIT]:程序已退出,退出代码: 0");
        }catch (VMRuntimeException e){
            throw e;
        }
        if(debug) {
            System.out.println("Done! Times(" + (System.currentTimeMillis() - a) + ")MS");
            System.out.println("LoadedFunctions:" + functions.size());
            System.out.println("StackSize:" + stack.size());
            System.out.println("ByteCodes:" + fbc.getBcs().size());
        }
        fbc.bcs.clear();

        return output_buffer;
    }
}
