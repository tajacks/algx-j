package com.tajacks.algorithms.algxj.dlx;

import com.tajacks.algorithms.algxj.dlx.nodes.ColumnNode;
import com.tajacks.algorithms.algxj.dlx.nodes.DancingNode;
import com.tajacks.algorithms.algxj.dlx.nodes.Node;
import com.tajacks.algorithms.algxj.matrix.ExactCoverMatrix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DLXBoard {
    private static final String           HEADER_LABEL = "HEADER";
    private final        ColumnNode       header;
    private final        ExactCoverMatrix matrix;
    private final        List<ColumnNode> columns;
    private              List<List<Node>> answers;

    public DLXBoard(ExactCoverMatrix matrix) {
        this.header        = ColumnNode.fromName(HEADER_LABEL);
        this.matrix        = matrix;
        this.columns       = buildColumnsAndNodes();
    }

    private List<ColumnNode> buildColumnsAndNodes() {
        List<ColumnNode> columns = new ArrayList<>();

        for (int i = matrix.columns().size() - 1; i >= 0; i--) {
            ColumnNode node = ColumnNode.fromName(matrix.columns().get(i).constraintLabel());
            header.addRight(node);
            columns.add(0, node);
        }

        boolean[][] sparseMatrix = matrix.asSparseMatrix();

        for (int o = sparseMatrix.length - 1; o >= 0; o--) {
            DancingNode lastAddedNode = null;
            for (int i = 0; i < columns.size(); i++) {
                if (sparseMatrix[o][i]) {
                    ColumnNode  columnNode = columns.get(i);
                    DancingNode newNode    = DancingNode.createWithinColumn(columnNode, matrix.rows().get(o).valueLabel());
                    if (lastAddedNode == null) {
                        lastAddedNode = newNode;
                    }
                    columnNode.addBelow(newNode);
                    lastAddedNode = lastAddedNode.addRight(newNode);
                }
            }
        }
        header.setNodesWithinColumn(columns.size());
        return columns;
    }

    public List<List<Node>> attemptSolve() {
        // Lazy load / Cache Answer
        if (this.answers != null) {
            return answers;
        }
        this.answers = new ArrayList<>();

        // Create a bucket to pass to solve method to store current solution.
        List<Node> currentAnswerBucket = new ArrayList<>();
        solve(currentAnswerBucket);
        return answers;
    }

    private void solve(List<Node> currentAnswerBucket) {
        if (header.getRight() != header) {
            Optional<ColumnNode> c = getColumnWithLowestNodes();

            if (c.isPresent()) {
                ColumnNode co = c.get();
                co.cover();

                for (Node node = co.getDown(); node != co; node = node.getDown()) {
                    // Add Node to partial solution
                    currentAnswerBucket.add(node);

                    // Cover columns from Nodes in row
                    for (Node j = node.getRight(); j != node; j = j.getRight()) {
                        j.assignedColumn().cover();
                    }

                    // Recursive call to solve
                    solve(currentAnswerBucket);

                    // Start to undo actions taken
                    node = currentAnswerBucket.remove(currentAnswerBucket.size() - 1);
                    co   = node.assignedColumn();

                    // Uncover previously covered columns
                    for (Node j = node.getLeft(); j != node; j = j.getLeft()) {
                        j.assignedColumn().uncover();
                    }
                }
                co.uncover();
            }
        } else {
            answers.add(new ArrayList<>(currentAnswerBucket));
        }
    }

    private Optional<ColumnNode> getColumnWithLowestNodes() {
        return columns.stream()
                      .filter(Predicate.not(ColumnNode::isCovered))
                      .min(Comparator.comparingInt(ColumnNode::getNodesWithinColumn));
    }
}
