package ex.mcppl.vm.exe;

import ex.mcppl.manage.VMOutputStream;
import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.code.ByteCode;
import ex.mcppl.vm.code.CatchByteCode;
import ex.mcppl.vm.code.GroupByteCode;
import ex.mcppl.vm.lib.LibLoader;
import ex.mcppl.vm.thread.ExThread;

import java.util.ArrayList;
import java.util.Stack;

public class Executor {
    private Executor(){
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

    ArrayList<ExThread> threads;

    public static Executor load(FileByteCode fbc, VMOutputStream player, LibLoader loader,ArrayList<Function> functions){
        e = new Executor();
        e.player = player;
        e.loader = loader;
        e.output_buffer = new ArrayList<>();
        e.stack = new Stack<>();
        e.fbc = fbc;
        e.functions = functions;
        e.debug = false;
        e.stack_var = 0;
        e.threads = new ArrayList<>();
        return e;
    }

    public ExThread getThread() {
        return thread;
    }

    public void addThread(ExThread thread){
        thread.start();
        threads.add(thread);
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
        boolean iserror = false;
        VMRuntimeException vre = null;
            for (ByteCode bc : fbc.getBcs()) {
                try {
                    bc.exe(e);
                }catch (VMRuntimeException e){
                    thread.status = ExThread.Status.ERROR;
                    iserror = true;
                    vre = e;
                }

            }

            if(iserror) {
                for(ByteCode bc:fbc.getBcs()){
                    if(bc instanceof CatchByteCode){
                        if(vre.getType().name().equals(((CatchByteCode) bc).getType())){
                            thread.status = ExThread.Status.LOADING;
                            bc.exe(e);
                            iserror = false;
                            thread.status = ExThread.Status.RUNNING;
                            break;
                        }else continue;
                    }else continue;
                }
            }

            player.info("[EXVM-EXIT]:程序已退出,退出代码: 0");

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
