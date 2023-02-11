package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExDouble;
import ex.mcppl.vm.buf.ExInt;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.buf.ExValueName;
import ex.mcppl.vm.exe.Executor;

public class DivByteCode implements ByteCode{
    private boolean isYEPType(ExObject obj){
        return obj.getType().equals(ExObject.Type.INT)||
                obj.getType().equals(ExObject.Type.DOUBLE);
    }
    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        ExObject obj = executor.getStack().pop();
        ExObject obj1 = executor.getStack().pop();


        boolean isdouble = false;
        if((obj.getType().equals(ExObject.Type.INT)
                ||obj.getType().equals(ExObject.Type.DOUBLE))
                &&(executor.getStack().peek().getType().equals(ExObject.Type.INT)||
                executor.getStack().peek().getType().equals(ExObject.Type.DOUBLE))){

            if(obj.getType().equals(ExObject.Type.DOUBLE))isdouble = true;
            if(obj1.getType().equals(ExObject.Type.DOUBLE))isdouble = true;

            if(isdouble){
                double r = Double.parseDouble(obj1.getData())/Double.parseDouble(obj.getData());
                executor.getStack().push(new ExDouble(r));
            }else {
                int r =  Integer.parseInt(executor.getStack().peek().getData())/Integer.parseInt(obj.getData());
                executor.getStack().push(new ExInt(r));
            }

        }else if(obj.getType().equals(ExObject.Type.OBJECT)&&isYEPType(obj1)){
            double d = Double.parseDouble(((ExValueName)obj).getValue().getData());

            executor.push(new ExDouble(Double.parseDouble(executor.getStack().peek().getData())/d));
        }else if(obj1.getType().equals(ExObject.Type.OBJECT)&&isYEPType(obj)) {
            double d = Double.parseDouble(((ExValueName)obj1).getValue().getData());

            executor.push(new ExDouble(Double.parseDouble(obj.getData())/d));
        }else if(obj.getType().equals(ExObject.Type.OBJECT)&&obj1.getType().equals(ExObject.Type.OBJECT)){
            double d = Double.parseDouble(((ExValueName)obj1).getValue().getData());
            double a = Double.parseDouble(((ExValueName)obj).getValue().getData());

            executor.push(new ExDouble(a/d));
        }else throw new VMRuntimeException("Unknown type value can't cast Integer/Double", executor.getPlayer());
    }
}