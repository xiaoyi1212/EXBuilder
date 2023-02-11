package ex.mcppl.vm.buf;

public class ExString extends ExObject{
    Type type;
    String data;
    public ExString(String data) {
        this.type = Type.STRING;
        this.data = data;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getData() {
        return data;
    }
}
