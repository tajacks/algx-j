package com.tajacks.algorithms.algxj.dlx.nodes;

import com.tajacks.algorithms.algxj.dlx.exception.IllegalNodeColumnException;

public final class DancingNode extends Node implements DownLinkable<DancingNode>, RightLinkable<DancingNode> {
    private final ColumnNode column;

    private DancingNode(ColumnNode c, String name) {
        super(name);
        this.column = c;
    }

    public static DancingNode createWithinColumn(ColumnNode column, String name) {
        DancingNode dancingNode = new DancingNode(column, name);
        dancingNode.linkToSelf();
        dancingNode.column.incrementDancingNodeCount();
        return dancingNode;
    }

    @Override
    public DancingNode addBelow(DancingNode n) {
        if (n.assignedColumn() != column) {
            throw new IllegalNodeColumnException("Cannot down-link node " + n + " to column " + column + " outside column boundary.");
        }
        n.down    = this.down;
        n.down.up = n;
        n.up      = this;
        this.down = n;
        return n;
    }

    @Override
    public ColumnNode assignedColumn() {
        return column;
    }

    @Override
    public DancingNode addRight(DancingNode n) {
        n.right      = this.right;
        n.right.left = n;
        n.left       = this;
        this.right   = n;
        return n;
    }
}
