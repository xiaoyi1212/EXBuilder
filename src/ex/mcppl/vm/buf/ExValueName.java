package ex.mcppl.vm.buf;

import ex.mcppl.vm.thread.ExThread;

public class ExValueName extends ExObject{
    String string;
    public ExValueName(String name){
        this.string = name;
    }

    @Override
    public Type getType() {
        return Type.OBJECT;
    }

    public String getName() {
        return string;
    }

    public ExObject getValue(ExThread thread){

        for(ExValue value: thread.all_values){
            if(value.getName().equals(string)){
                return value.getValue();
            }
        }
        return null;
    }
}
