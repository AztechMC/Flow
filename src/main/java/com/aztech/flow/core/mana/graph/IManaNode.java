package com.aztech.flow.core.mana.graph;

/**
 * A node in the mana graph.
 */
public interface IManaNode {
    /**
     * Process one packet
     * @param packet Received packet
     * @param inputId Side from which the packet was received
     * @return Output packets. A non-null packet at position i corresponds to a packet sent to output connection number i.
     */
    IPacket[] processPacket(IPacket packet, int inputId);
}
