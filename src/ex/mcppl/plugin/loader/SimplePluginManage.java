package ex.mcppl.plugin.loader;

import ex.mcppl.plugin.Interface;
import ex.mcppl.plugin.Library;
import ex.mcppl.vm.lib.BasicLibrary;
import ex.mcppl.vm.lib.LibLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SimplePluginManage {
    static ArrayList<Plugin> plugins = new ArrayList<>();

    public static void loadPlugins(File file) throws Exception {
        if(!file.exists())throw new FileNotFoundException("找不到指定库文件");

        JarFile jar = new JarFile(file);
        JarEntry entry = jar.getJarEntry("config.properties");
        Properties config = new Properties();
        config.load(jar.getInputStream(entry));

        URL url = file.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
        Class<Interface> c = (Class<Interface>) classLoader.loadClass(config.getProperty("class"));
        Interface i = c.newInstance();
        plugins.add(new Plugin(i,config.getProperty("name"),classLoader));
    }

    public static void launch(){
        for(Plugin plugin:plugins){
            plugin.anInterface.onLoadLibrary();
        }
    }
    public static void loaderLibrary(LibLoader loader){
        for(Plugin plugin:plugins){
            ArrayList<BasicLibrary> bl = new ArrayList<>();
            for(Library l:plugin.anInterface.registerLib())bl.add(l);
            loader.libs.put(plugin.anInterface.getLibraryName(),bl);
        }

    }
}
