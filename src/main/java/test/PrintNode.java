package test;

import com.aztech.flow.core.spells.IManaNode;
import com.aztech.flow.core.spells.IPacket;
import net.minecraft.nbt.NBTTagCompound;

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

    @Override
    public IManaNode readNbt(NBTTagCompound nbt) {
        return this;
    }

    @Override
    public NBTTagCompound writeNbt() {
        return null;
    }
}
