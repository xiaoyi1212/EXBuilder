package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.ExDouble;
import ex.mcppl.vm.buf.ExInt;
import ex.mcppl.vm.buf.ExObject;
import ex.mcppl.vm.buf.ExValueName;
import ex.mcppl.vm.exe.Executor;

public class MulByteCode implements ByteCode{
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
                &&(obj1.getType().equals(ExObject.Type.INT)||
                obj1.getType().equals(ExObject.Type.DOUBLE))){

            if(obj.getType().equals(ExObject.Type.DOUBLE))isdouble = true;
            if(obj1.getType().equals(ExObject.Type.DOUBLE))isdouble = true;

            if(isdouble){
                double r = Double.parseDouble(obj.getData()) * Double.parseDouble(obj1.getData());
                executor.getStack().push(new ExDouble(r));
            }else {
                int r = Integer.parseInt(obj.getData()) * Integer.parseInt(obj1.getData());
                executor.getStack().push(new ExInt(r));
            }

        }else if(obj.getType().equals(ExObject.Type.OBJECT)&&isYEPType(obj1)){
            double d = Double.parseDouble(((ExValueName)obj).getValue(executor.getThread()).getData());
            executor.push(new ExDouble(d*Double.parseDouble(obj1.getData())));
        }else if(obj1.getType().equals(ExObject.Type.OBJECT)&&isYEPType(obj)) {
            double d = Double.parseDouble(((ExValueName)obj1).getValue(executor.getThread()).getData());
            executor.push(new ExDouble(d*Double.parseDouble(obj.getData())));
        }else if(obj.getType().equals(ExObject.Type.OBJECT)&&obj1.getType().equals(ExObject.Type.OBJECT)){
            double d = Double.parseDouble(((ExValueName)obj1).getValue(executor.getThread()).getData());
            double a = Double.parseDouble(((ExValueName)obj).getValue(executor.getThread()).getData());

            executor.push(new ExDouble(d*a));
        }else throw new VMRuntimeException("Unknown type value can't cast Integer/Double", executor.getPlayer(), VMRuntimeException.Type.CAST_VALUE_EXCEPTION);
    }
}
