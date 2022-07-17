package com.tajacks.algorithms.algxj.dlx;

import com.tajacks.algorithms.algxj.dlx.nodes.Node;
import com.tajacks.algorithms.algxj.matrix.ExactCoverMatrix;
import com.tajacks.algorithms.algxj.matrix.MatrixColumn;
import com.tajacks.algorithms.algxj.matrix.MatrixRow;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DLXBoardTest {

    @Test
    void createDLXBoard_AndNoExceptions() {
        ExactCoverMatrix ecm = new ExactCoverMatrix(
                List.of(new MatrixColumn("Wallet"), new MatrixColumn("Keys"), new MatrixColumn("Phone")),
                List.of(new MatrixRow("1", new boolean[]{ true, false, true }), new MatrixRow("2", new boolean[]{ true, true, true }), new MatrixRow("3", new boolean[]{ false, false, false }))
        );
        DLXBoard b = new DLXBoard(ecm);
    }

    @Test
    void createDLXBoard_AndSolve() {
        ExactCoverMatrix ecm = new ExactCoverMatrix(
                List.of(new MatrixColumn("Wallet"), new MatrixColumn("Keys"), new MatrixColumn("Phone")),
                List.of(
                        new MatrixRow("1", new boolean[]{ true, false, true }),
                        new MatrixRow("2", new boolean[]{ true, true, false }),
                        new MatrixRow("3", new boolean[]{ false, false, false }),
                        new MatrixRow("4", new boolean[]{ false, true, false }),
                        new MatrixRow("5", new boolean[]{ true, true, true })
                )
        );
        DLXBoard         b       = new DLXBoard(ecm);
        List<List<Node>> results = b.attemptSolve();
        assertEquals(2, results.size());
        assertEquals("1", results.get(0).get(0).getName());
        assertEquals("4", results.get(0).get(1).getName());
        assertEquals("5", results.get(1).get(0).getName());
    }

    @Test
    void createDLXBoard_AndUnsolvable() {
        ExactCoverMatrix ecm = new ExactCoverMatrix(
                List.of(new MatrixColumn("Wallet"), new MatrixColumn("Keys"), new MatrixColumn("Phone")),
                List.of(
                        new MatrixRow("1", new boolean[]{ true, false, true }),
                        new MatrixRow("2", new boolean[]{ true, true, false }),
                        new MatrixRow("3", new boolean[]{ false, false, false })
                )
        );
        DLXBoard         b       = new DLXBoard(ecm);
        List<List<Node>> results = b.attemptSolve();
        assertTrue(results.isEmpty());
    }
}