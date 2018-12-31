package test;

import com.aztech.flow.core.mana.graph.Edge;
import com.aztech.flow.core.mana.graph.IManaNode;
import com.aztech.flow.core.mana.graph.ManaSystem;
import com.aztech.flow.core.mana.graph.Packet;

import java.util.Arrays;
import java.util.List;

public class CreateAndTestSystem {
    public static void main(String[] args) {
        /*PrintNode pn1 = new PrintNode(1), pn2 = new PrintNode(2), pn3 = new PrintNode(3);
        Edge e1 = new Edge(pn1, 0, pn2, 42), e2 = new Edge(pn2, 0, pn3, 41);


        List<IManaNode> nodes = Arrays.asList(pn1, pn2, pn3);
        List<Edge> edges = Arrays.asList(e1, e2);

        ManaSystem sys = new ManaSystem(nodes, edges);
        System.out.println("Nothing should happen...");
        sys.tick();
        System.out.println("One thing should print (value 2)");
        sys.insertPacket(new Packet(), e1);
        sys.tick();*/
    }
}
