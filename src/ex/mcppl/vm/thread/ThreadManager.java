package ex.mcppl.vm.thread;

import ex.mcppl.manage.Main;
import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExValue;
import ex.mcppl.vm.exe.Function;

import java.util.ArrayList;

public class ThreadManager {
    static ArrayList<ExThread> threads = new ArrayList<>();
    public static ArrayList<Function> all_functions = new ArrayList<>();
    public static ArrayList<ExValue> all_values = new ArrayList<>();

    public static int getThreads(){return threads.size();}

    public static void launch(){
        for(ExThread thread:threads){
            if(thread.thread_name.equals("main")){
                thread.start();
                return;
            }
        }
    }

    public static void addThreads(ExThread thread) throws VMRuntimeException{
        for(ExThread thread1:threads){
            if(thread.thread_name.equals(thread1.thread_name)){
                throw new VMRuntimeException("Cannot add have same name thread:"+thread.thread_name, Main.getOutput(), VMRuntimeException.Type.SAME_NAME_EXCEPTION);
            }
        }
        threads.add(thread);
    }
}
