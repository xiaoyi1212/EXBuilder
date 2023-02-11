package ex.mcppl.vm.buf;

public class ExValue extends ExObject{
    String name;
    String text;
    ExObject value;
    public ExValue(String name,String text,ExObject value){
        this.name = name;
        this.text = text;
        this.value = value;
    }

    public ExObject getValue(){
        return value;
    }

    public void setValue(ExObject value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }
}
