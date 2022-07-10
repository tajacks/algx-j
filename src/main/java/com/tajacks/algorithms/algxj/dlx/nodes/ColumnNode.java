package com.tajacks.algorithms.algxj.dlx.nodes;

import com.tajacks.algorithms.algxj.dlx.exception.IllegalNodeColumnException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ColumnNode extends Node implements DownLinkable<DancingNode>, RightLinkable<ColumnNode> {
    private int     nodesWithinColumn;
    private boolean covered;

    private ColumnNode(String name) {
        super(name);
        this.nodesWithinColumn = 0;
    }

    public static ColumnNode fromName(String name) {
        ColumnNode columnNode = new ColumnNode(name);
        columnNode.linkToSelf();
        return columnNode;
    }

    public void cover() {
        removeSelfLeftRight();
        for (Node colNode : getNodesInColumn(NodeOrder.NATURAL)) {
            for (Node colNodeRowNode : getNodesInRowGivenNode(colNode, NodeOrder.NATURAL)) {
                colNodeRowNode.removeSelfUpDown();
                colNodeRowNode.assignedColumn().decrementDancingNodeCount();
            }
        }
        covered = true;
    }

    public void uncover() {
        for (Node colNode : getNodesInColumn(NodeOrder.REVERSED)) {
            for (Node colNodeRowNode : getNodesInRowGivenNode(colNode, NodeOrder.REVERSED)) {
                colNodeRowNode.restoreSelfUpDown();
                colNodeRowNode.assignedColumn().incrementDancingNodeCount();
            }
        }
        restoreSelfLeftRight();
        covered = false;
    }

    private List<Node> getNodesInColumn(NodeOrder orderBy) {
        List<Node> nodes = new ArrayList<>();
        for (Node i = this.down; i != this; i = i.down) {
            nodes.add(i);
        }
        if (orderBy.equals(NodeOrder.REVERSED)) {
            Collections.reverse(nodes);
        }
        return nodes;
    }

    private List<Node> getNodesInRowGivenNode(Node givenNode, NodeOrder orderBy) {
        List<Node> nodes = new ArrayList<>();
        for (Node i = givenNode.right; i != givenNode; i = i.right) {
            nodes.add(i);
        }
        if (orderBy.equals(NodeOrder.REVERSED)) {
            Collections.reverse(nodes);
        }
        return nodes;
    }

    public void incrementDancingNodeCount() {
        nodesWithinColumn++;
    }

    public void decrementDancingNodeCount() {
        nodesWithinColumn--;
    }

    public int getNodesWithinColumn() {
        return nodesWithinColumn;
    }

    public void setNodesWithinColumn(int nodesWithinColumn) {
        this.nodesWithinColumn = nodesWithinColumn;
    }

    public boolean isCovered() {
        return covered;
    }

    @Override
    public DancingNode addBelow(DancingNode n) throws IllegalNodeColumnException {
        if (n.assignedColumn() != this) {
            throw new IllegalNodeColumnException("Cannot down-link node " + n + " to column " + this.id + ". Outside column boundary.");
        }

        n.down    = this.down;
        n.down.up = n;
        n.up      = this;
        this.down = n;
        return n;
    }

    @Override
    public ColumnNode addRight(ColumnNode n) {
        n.right      = this.right;
        n.right.left = n;
        n.left       = this;
        this.right   = n;
        return n;
    }

    @Override
    public ColumnNode assignedColumn() {
        return this;
    }

    private enum NodeOrder {
        NATURAL,
        REVERSED
    }
}
