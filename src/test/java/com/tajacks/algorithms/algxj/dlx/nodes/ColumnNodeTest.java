package com.tajacks.algorithms.algxj.dlx.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnNodeTest {
    private final String NODE_NAME = "EXAMPLE";

    @Test
    public void createNodeFromStaticMethod_AndNoExceptions_AndLinked() {
        ColumnNode n = getNode(NODE_NAME);
        assertEquals(NODE_NAME, n.getName());
        assertEquals(n, n.getLeft());
        assertEquals(n, n.getRight());
        assertEquals(n, n.getUp());
        assertEquals(n, n.getDown());
        assertEquals(0, n.getNodesWithinColumn());
    }

    @Test
    public void createNode_AndLinkDown() {
        ColumnNode  n1  = getNode("1");
        DancingNode dn1 = DancingNode.createWithinColumn(n1, "DN1");

        assertEquals(1, n1.getNodesWithinColumn());
    }

    @Test
    public void createNode_AndLinkRight() {
        ColumnNode n1 = getNode("1");
        ColumnNode n2 = getNode("2");
        ColumnNode n3 = getNode("3");

        n1.addRight(n2);
        n1.addRight(n3);

        assertEquals(n1, n3.getLeft());
        assertEquals(n1, n2.getRight());

        assertEquals(n2, n1.getLeft());
        assertEquals(n2, n3.getRight());

        assertEquals(n3, n2.getLeft());
        assertEquals(n3, n1.getRight());
    }

    @Test
    public void createNodes_AndLinkRight_ThenCover_ThenUncover() {
        ColumnNode n1 = getNode("1");
        ColumnNode n2 = getNode("2");
        ColumnNode n3 = getNode("3");

        n1.addRight(n2);
        n1.addRight(n3);
        n3.cover();

        assertEquals(n1, n3.getLeft());
        assertEquals(n1, n2.getRight());
        assertEquals(n1, n2.getLeft());

        assertEquals(n2, n1.getLeft());
        assertEquals(n2, n1.getRight());
        assertEquals(n2, n3.getRight());

        n3.uncover();

        assertEquals(n1, n3.getLeft());
        assertEquals(n1, n2.getRight());

        assertEquals(n2, n1.getLeft());
        assertEquals(n2, n3.getRight());

        assertEquals(n3, n2.getLeft());
        assertEquals(n3, n1.getRight());
    }

    private ColumnNode getNode(String name) {
        return ColumnNode.fromName(name);
    }
}