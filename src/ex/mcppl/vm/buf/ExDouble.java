package ex.mcppl.vm.buf;

public class ExDouble extends ExObject{
    double data;
    Type type;
    public ExDouble(double data){
        this.data = data;
        this.type = Type.DOUBLE;
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
