package ex.mcppl.manage;

import ex.mcppl.compile.LexToken;
import ex.mcppl.compile.VMException;
import ex.mcppl.compile.parser.BasicParser;
import ex.mcppl.plugin.loader.SimplePluginManage;
import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.VMloader;
import ex.mcppl.vm.lib.math.E;
import ex.mcppl.vm.thread.ExThread;
import ex.mcppl.vm.thread.ThreadManager;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class FileManage {

    public static ArrayList<String> libs = new ArrayList<>();
    public static ArrayList<String> files = new ArrayList<>();
    static boolean isfile = false;

    public static void command(String[] args) throws VMRuntimeException, VMException {
        for(String a:args){
            if(a.equals("-version")){
                System.out.println(Main.vm_version);
                return;
            }else if(a.contains("-loadlib:")){
                libs.add(a.split(":")[1]);
            } else if(a.contains("-filename:")){
                isfile = true;
                String name = a.split(":")[1];
                files.add(name);
            }
        }

        try {
            for (String str : libs) SimplePluginManage.loadPlugins(new File(str));
            SimplePluginManage.launch();
        }catch (Exception e){
            System.out.println("加载外部库时发生错误!");
            e.printStackTrace();
        }

        if(isfile) {
           ArrayList<VMloader> l = launch();
           VMloader lmain = l.get(0);
           l.remove(0);
           ExThread main = new ExThread("main",lmain,l,Main.getOutput());
           ThreadManager.addThreads(main);
        }
        ThreadManager.launch();
    }

    private static ArrayList<VMloader> launch() throws VMException,VMRuntimeException{
        ArrayList<VMloader> loaders = new ArrayList<>();
        for(String name:files) {
            LinkedList<LexToken.TokenD> tds = new LexToken(name).launch();
            LinkedList<LexToken.TokenD> buf = new LinkedList<>();

            for (LexToken.TokenD td : tds) {
                if (td.getToken().equals(LexToken.Token.TEXT)) continue;
                buf.add(td);
            }

            VMloader loader =new BasicParser(name, buf, Main.getOutput()).rust();
            loaders.add(loader);
        }


       return loaders;
    }
}
