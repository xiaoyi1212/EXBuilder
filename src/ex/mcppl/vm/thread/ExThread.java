package ex.mcppl.vm.thread;


import ex.mcppl.VMOutputStream;
import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.VMloader;

public class ExThread implements Runnable{
    VMloader loader;
    Thread thread;
    VMOutputStream outputStream;
    public Status status;

    @Override
    public void run() {
        try {
            loader.launch(outputStream,this);
            status = Status.DEATH;
        } catch (VMRuntimeException e) {
            status = Status.ERROR;
            outputStream.error("线程-"+Thread.currentThread().getName()+":发生运行异常| 状态重置为ERROR");
        }
    }

    public enum Status{
        RUNNING,LOADING,DEATH,ERROR,WAIT
    }


    public ExThread(VMloader loader,VMOutputStream outputStream){
        status = Status.LOADING;
        this.loader = loader;
        this.thread = new Thread(this);
        this.outputStream = outputStream;
    }

    public void start(){
        status = Status.RUNNING;
        thread.start();
    }
}
