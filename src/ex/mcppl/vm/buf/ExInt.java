package ex.mcppl.vm.buf;

public class ExInt extends ExObject{
    Type type;
    int data;
    public ExInt(int data){
        this.type = Type.INT;
        this.data = data;
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
