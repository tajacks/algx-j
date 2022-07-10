package com.tajacks.algorithms.algxj.dlx.nodes;

import java.util.concurrent.atomic.AtomicLong;

public abstract sealed class Node permits ColumnNode, DancingNode {
    protected static final AtomicLong NEXT_ID  = new AtomicLong(0);
    protected final        long       id       = NEXT_ID.getAndIncrement();
    protected final        String     name;
    protected              Node       left;
    protected              Node       right;
    protected              Node       up;
    protected              Node       down;
    private                int        hashCode = 0;

    protected Node(String name) {
        this.name = name;
    }

    protected void removeSelfLeftRight() {
        this.left.setRight(this.right);
        this.right.setLeft(this.left);
    }

    protected void restoreSelfLeftRight() {
        this.left.setRight(this);
        this.right.setLeft(this);
    }

    protected void removeSelfUpDown() {
        this.up.setDown(this.down);
        this.down.setUp(this.up);
    }

    protected void restoreSelfUpDown() {
        this.up.setDown(this);
        this.down.setUp(this);
    }

    protected void linkToSelf() {
        this.up = this.down = this.left = this.right = this;
    }

    public abstract ColumnNode assignedColumn();


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Node getLeft() {
        return left;
    }

    protected void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    protected void setRight(Node right) {
        this.right = right;
    }

    public Node getUp() {
        return up;
    }

    protected void setUp(Node up) {
        this.up = up;
    }

    public Node getDown() {
        return down;
    }

    protected void setDown(Node down) {
        this.down = down;
    }

    @Override
    final public int hashCode() {
        // Lazy initialize HashCode
        if (this.hashCode == 0) {
            this.hashCode = Long.hashCode(id);
        }
        return hashCode;
    }

    @Override
    final public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        return node.id == this.id;
    }

    @Override
    final public String toString() {
        return name;
    }
}
