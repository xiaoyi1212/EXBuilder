package ex.mcppl;

import ex.mcppl.compile.LexToken;
import ex.mcppl.compile.VMException;
import ex.mcppl.compile.parser.BasicParser;
import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.thread.ExThread;
import ex.mcppl.vm.thread.ThreadManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    public static final String vm_version = "EXVirtualMachine-v0.3.1:"+System.getProperty("java.vm.name")+"-"+System.getProperty("java.vm.specification.version");
    public static VMOutputStream player;
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

    static String name;
    static ArrayList<String> libs = new ArrayList<>();

    private static void command(String[] args) throws VMRuntimeException,VMException {
        for(String a:args){

            if(a.equals("-version")){
                System.out.println(vm_version);
                return;
            }else if(a.contains("-loadlib:")){
                libs.add(a.split(":")[1]);
            }
            else if(a.contains("-filename:")){
                name = a.split(":")[1];
                LinkedList<LexToken.TokenD> tds= new LexToken(name).launch();
                LinkedList<LexToken.TokenD> buf = new LinkedList<>();

                for (LexToken.TokenD td : tds) {
                    if (td.getToken().equals(LexToken.Token.TEXT)) continue;
                    buf.add(td);
                }
                ExThread thread = new ExThread(new BasicParser(buf,player).rust(name,player),player);
                thread.start();
                ThreadManager.addThreads(thread);
            }
        }
    }

    public static void main(String[] args) throws Exception{

        player = new VMOutputStream();

        if(args.length == 0){
            System.out.println("-filename:<File> 编译并运行指定文件.\n" +
                    "-version 查看版本.\n" +
                    "-loadlib:<LibJarName> 加载指定外部库文件");
            return;
        }

        command(args);
    }
}
