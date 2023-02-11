package ex.mcppl.vm.thread;


import ex.mcppl.manage.VMOutputStream;
import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.VMloader;
import ex.mcppl.vm.exe.Function;

import java.util.ArrayList;

public class ExThread implements Runnable{
    ArrayList<VMloader> loader;
    VMloader main;
    Thread thread;
    VMOutputStream outputStream;
    public Status status;
    String thread_name;
    public ArrayList<Function> all_functions;

    private static int numname;
    private static synchronized int nextThreadNum() {
        return numname++;
    }
    @Override
    public void run() {
        try {
            main.launch(outputStream,this);
            status = Status.DEATH;
        } catch (VMRuntimeException e) {
            status = Status.ERROR;
            outputStream.error("线程-"+Thread.currentThread().getName()+":发生运行异常| 状态重置为ERROR");
        }
    }

    public enum Status{
        RUNNING,LOADING,DEATH,ERROR,WAIT
    }


    public ExThread(String name,VMloader main,ArrayList<VMloader> loaders,VMOutputStream outputStream){
        status = Status.LOADING;
        this.main = main;
        this.loader = loaders;
        this.all_functions = new ArrayList<>();
        this.thread = new Thread(this);
        this.outputStream = outputStream;
        this.thread_name = name;
    }

    public ExThread(Thread thread,VMOutputStream outputStream){
        this.thread = thread;
        this.outputStream = outputStream;
        this.thread_name = "ExThread-"+nextThreadNum();
    }

    public ExThread(VMloader main,ArrayList<VMloader> loaders,VMOutputStream outputStream){
        status = Status.LOADING;
        this.main = main;
        this.loader = loaders;
        this.thread = new Thread(this);
        this.outputStream = outputStream;
        this.thread_name = "ExThread-"+nextThreadNum();
    }

    public void start(){
        status = Status.RUNNING;
        thread.start();
    }
}
