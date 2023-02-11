package ex.mcppl.vm.thread;

import java.util.ArrayList;

public class ThreadManager {
    static ArrayList<ExThread> threads = new ArrayList<>();

    public static int getThreads(){return threads.size();}

    public static void addThreads(ExThread thread) {
        threads.add(thread);
    }
}
