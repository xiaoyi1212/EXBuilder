package ex.mcppl.vm.buf;

public class ExBool extends ExObject{
    boolean data;
    Type type;
    public ExBool(boolean data){
        this.data = data;
        this.type = Type.BOOL;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getData() {
        return String.valueOf(data);
    }
}
