package ex.mcppl.vm.buf;

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

    public ExObject getValue(){
        for(ExValue value:AllValueBuffer.values){
            if(value.getName().equals(string)){
                return value.getValue();
            }
        }
        return null;
    }
}
