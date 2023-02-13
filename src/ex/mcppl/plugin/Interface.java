package ex.mcppl.plugin;

import java.util.ArrayList;

public abstract class Interface implements BasicInterface{
    @Override
    public ArrayList<Library> registerLib() {
        return new ArrayList<>();
    }
    public abstract void onLoadLibrary();
}
