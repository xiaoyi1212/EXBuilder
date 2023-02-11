package ex.mcppl.manage;

import java.util.HashSet;

public class Main {
    public static final String vm_version = "EXVirtualMachine-v0.3.2:"+System.getProperty("java.vm.name")+"-"+System.getProperty("java.vm.specification.version");
    public static VMOutputStream output;
    private static HashSet<String> str = new HashSet<>(){{
           add("exe");
           add("if");
           add("while");
           add("function");
           add("value");
           add("include");
           add("false");
           add("true");
           add("set");
           add("this");
           add("break");
           add("null");
    }};

    public static boolean isKey(String data){
        return str.contains(data);
    }


    public static void main(String[] args) throws Exception{

        output = new VMOutputStream();

        /*
        args = new String[2];
        args[0]="-filename:debug.exf";
        args[1]="-filename:bug.exf";
       
         */

        if(args.length == 0){
            System.out.println("-filename:<File> 编译并运行指定文件.\n" +
                    "-version 查看版本.\n" +
                    "-loadlib:<LibJarName> 加载指定外部库文件");
            return;
        }

        FileManage.command(args);
    }

    public static VMOutputStream getOutput() {
        return output;
    }
}
