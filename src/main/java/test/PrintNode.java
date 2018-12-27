package test;

import com.aztech.flow.core.mana.graph.IManaNode;
import com.aztech.flow.core.mana.graph.IPacket;

public class PrintNode implements IManaNode {
    private int value;

    public PrintNode(int value) {
        this.value = value;
    }

    @Override
    public IPacket[] processPacket(IPacket packet, int inputId) {
        System.out.printf("Intercepted packet at node with value %d and inputId %d", this.value, inputId);
        return new IPacket[0];
    }
}
