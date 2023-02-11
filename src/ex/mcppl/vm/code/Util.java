package ex.mcppl.vm.code;

public class Util {

    public static final String PUSH = "0100101";
    public static final String POP  = "0100111";
    public static final String JNE  = "0100100";
    public static final String EQU  = "0100110";
    public static final String IMP  = "0101000";
    public static final String INV  = "0101110";
    public static final String NOT  = "0101111";
    public static final String OR   = "0101111";
    public static final String AND  = "0111110";
    public static final String MOV  = "0110110";
    public static final String SET  = "0110111";
    public static final String SUB  = "0110100";
    public static final String MUL  = "0110101";
    public static final String ADD  = "0111111";
    public static final String DIV  = "1100110";
    public static final String NOL  = "0100000";

    public static byte bit2byte(String bString){
        byte result=0;
        for(int i=bString.length()-1,j=0;i>=0;i--,j++){
            result+=(Byte.parseByte(bString.charAt(i)+"")*Math.pow(2, j));
        }
        return result;
    }

    public static String getBinaryStrFromByte(byte b){
        String result ="";
        byte a = b; ;
        for (int i = 0; i < 8; i++){
            byte c=a;
            a=(byte)(a>>1);
            a=(byte)(a<<1);
            if(a==c){
                result="0"+result;
            }else{
                result="1"+result;
            }
            a=(byte)(a>>1);
        }
        return result;
    }
}
