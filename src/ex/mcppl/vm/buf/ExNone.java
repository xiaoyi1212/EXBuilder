package ex.mcppl.vm.buf;

public class ExNone extends ExObject{
    @Override
    public Type getType() {
        return Type.NULL;
    }

    @Override
    public String getData() {
        return "null";
    }
}
