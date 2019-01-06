package com.aztech.flow.spells.runes;

import com.aztech.flow.Flow;
import com.aztech.flow.core.api.spells.IDrop;
import com.aztech.flow.core.api.spells.IRune;
import com.aztech.flow.spells.aspects.Aspects;
import com.aztech.flow.spells.aspects.WorldPos;
import net.minecraft.nbt.NBTTagCompound;

public class TranslationRune implements IRune {
    @Override
    public IDrop[] processDrop(IDrop packet, int inputId) {
        Flow.logger.info("TranslationRune::processDrop");
        if(packet.hasAspect(Aspects.WORLD_POS_ASPECT)) {
            Flow.logger.info("Had WORLD_POS_MANA_ASPECT");
            WorldPos worldPos = packet.getAspect(Aspects.WORLD_POS_ASPECT);
            Flow.logger.info(String.format("Old pos: %d %d %d", worldPos.blockPos.getX(), worldPos.blockPos.getY(), worldPos.blockPos.getZ()));
            worldPos.blockPos = worldPos.blockPos.add(this.dx, this.dy, this.dz);
            Flow.logger.info(String.format("New pos: %d %d %d", worldPos.blockPos.getX(), worldPos.blockPos.getY(), worldPos.blockPos.getZ()));
        }
        return new IDrop[]{packet};
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if(nbt != null) {
            this.dx = nbt.getInteger("dx");
            this.dy = nbt.getInteger("dy");
            this.dz = nbt.getInteger("dz");
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("dx", this.dx);
        tag.setInteger("dy", this.dy);
        tag.setInteger("dz", this.dz);
        return tag;
    }

    private int dx, dy, dz;

    public TranslationRune(int dx, int dy, int dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public TranslationRune() {
        this.dx = this.dy = this.dz = 0;
    }
}
