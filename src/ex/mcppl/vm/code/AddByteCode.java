package ex.mcppl.vm.code;

import ex.mcppl.vm.VMRuntimeException;
import ex.mcppl.vm.buf.*;
import ex.mcppl.vm.exe.Executor;

public class AddByteCode implements ByteCode{

    private boolean isYEPType(ExObject obj){
        return obj.getType().equals(ExObject.Type.INT)||
                obj.getType().equals(ExObject.Type.DOUBLE);
    }

    @Override
    public void exe(Executor executor) throws VMRuntimeException {
        ExObject obj = executor.getStack().pop();
        ExObject obj1 = executor.getStack().pop();


        boolean isdouble = false;
        if((obj.getType().equals(ExObject.Type.INT) ||obj.getType().equals(ExObject.Type.DOUBLE))
        &&(obj1.getType().equals(ExObject.Type.INT)|| obj1.getType().equals(ExObject.Type.DOUBLE))){


            if(obj.getType().equals(ExObject.Type.DOUBLE))isdouble = true;
            if(obj1.getType().equals(ExObject.Type.DOUBLE))isdouble = true;

            if(isdouble){
                double r = Double.parseDouble(obj.getData()) + Double.parseDouble(obj1.getData());

                executor.getStack().push(new ExDouble(r));
            }else {
                int r = Integer.parseInt(obj.getData()) + Integer.parseInt(obj1.getData());
                executor.getStack().push(new ExInt(r));
            }

        }else if(obj.getType().equals(ExObject.Type.STRING)||obj1.getType().equals(ExObject.Type.STRING)) {
            StringBuilder sb = new StringBuilder();
            sb.append(obj.getData());
            sb.append(obj1.getData());
            executor.getStack().push(new ExString(sb.toString()));
        }else if(obj.getType().equals(ExObject.Type.OBJECT)&&isYEPType(obj1)){
            double d = Double.parseDouble(((ExValueName)obj).getValue().getData());
            executor.push(new ExDouble(d+Double.parseDouble(obj1.getData())));
        }else if(obj1.getType().equals(ExObject.Type.OBJECT)&&isYEPType(obj)) {
            double d = Double.parseDouble(((ExValueName)obj1).getValue().getData());
            executor.push(new ExDouble(d+Double.parseDouble(obj.getData())));
        }else if(obj.getType().equals(ExObject.Type.OBJECT)&&obj1.getType().equals(ExObject.Type.OBJECT)){
            double d = Double.parseDouble(((ExValueName)obj1).getValue().getData());
            double a = Double.parseDouble(((ExValueName)obj).getValue().getData());
            executor.push(new ExDouble(d+a));
        }else throw new VMRuntimeException("Unknown value type can't cast Integer/Double/String", executor.getPlayer());

    }
}
