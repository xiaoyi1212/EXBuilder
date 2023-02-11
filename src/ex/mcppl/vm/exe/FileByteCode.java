package ex.mcppl.vm.exe;

import ex.mcppl.vm.code.ByteCode;

import java.util.ArrayList;

public class FileByteCode {
    String filename;
    ArrayList<ByteCode> bcs;
    ArrayList<Function> functions;

    public FileByteCode(String filename,ArrayList<ByteCode> bcs){
        this.filename = filename;
        this.bcs = bcs;
    }

    public ArrayList<ByteCode> getBcs() {
        return bcs;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return "Name:"+filename+"|BCSize:"+bcs.size();
    }
}
