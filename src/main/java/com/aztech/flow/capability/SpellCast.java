package com.aztech.flow.capability;

import com.aztech.flow.Flow;
import com.aztech.flow.core.mana.graph.*;
import com.aztech.flow.mana.components.Components;
import com.aztech.flow.mana.components.WorldPos;
import com.aztech.flow.mana.nodes.CopyWorldBlockNode;
import com.aztech.flow.mana.nodes.PlaceIntoWorldNode;
import com.aztech.flow.mana.nodes.TranslationNode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

/**
 * Default implementation of ISpellCast
 */
public class SpellCast implements ISpellCast {
    private ManaSystem system;
    private Edge inputEdge;

    public SpellCast() {
        CopyWorldBlockNode copyNode = new CopyWorldBlockNode();
        PlaceIntoWorldNode placeNode = new PlaceIntoWorldNode();
        TranslationNode t0 = new TranslationNode(0, 0, 0);
        TranslationNode t1 = new TranslationNode(0, -5, 0);
        TranslationNode t2 = new TranslationNode(0, 10, 0);

        Edge e1 = new Edge(t0, 0, t1, 0);
        Edge e2 = new Edge(t1, 0, copyNode, 0);
        Edge e3 = new Edge(copyNode, 0, t2, 0);
        Edge e4 = new Edge(t2, 0, placeNode, 0);

        List<IManaNode> nodes = Arrays.asList(t0, t1, copyNode, t2, placeNode);
        List<Edge> edges = Arrays.asList(e1, e2, e3, e4);

        this.system = new ManaSystem(nodes, edges);
        this.inputEdge = e1;
    }

    @Override
    public void startSpellCast(World world, BlockPos blockPos) {
        IPacket packet = new Packet();
        packet.addComponent(Components.WORLD_POS_MANA_COMPONENT, new WorldPos(world, blockPos));
        this.system.insertPacket(packet, this.inputEdge);
    }

    @Override
    public void tick() {
        this.system.tick();
    }
}
