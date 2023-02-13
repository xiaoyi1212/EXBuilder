package ex.mcppl.plugin.loader;

import ex.mcppl.plugin.Interface;

import java.net.URLClassLoader;

public class Plugin {
    Interface anInterface;
    String name;
    URLClassLoader loader;
    public Plugin(Interface anInterface, String name, URLClassLoader loader){
        this.anInterface = anInterface;
        this.name = name;
        this.loader = loader;
    }
}
