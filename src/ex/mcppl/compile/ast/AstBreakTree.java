package ex.mcppl.compile.ast;

import ex.mcppl.compile.VMException;
import ex.mcppl.compile.parser.Element;
import ex.mcppl.vm.code.BekByteCode;
import ex.mcppl.vm.code.ByteCode;

public class AstBreakTree extends AstLeaf{
    @Override
    public ByteCode eval(Element e) throws VMException {
        return new BekByteCode();
    }
}
