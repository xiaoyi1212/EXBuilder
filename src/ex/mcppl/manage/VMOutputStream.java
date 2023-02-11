package ex.mcppl.manage;

import ex.mcppl.compile.VMException;

public class VMOutputStream {
    public VMOutputStream() throws VMException {

    }


    public void info(String str){
        System.out.println(str);
    }
    public void error(String str){
        System.err.println(str);
    }
}
