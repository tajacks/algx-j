package com.tajacks.algorithms.algxj.matrix;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExactCoverMatrixTest {

    @Test
    void createMatrixWithProperRowSize_AndNoExceptions() {
        ExactCoverMatrix ecm = new ExactCoverMatrix(
                List.of(new MatrixColumn("Wallet"), new MatrixColumn("Keys"), new MatrixColumn("Phone")),
                List.of(new MatrixRow("1", new boolean[]{ true, false, true }), new MatrixRow("2", new boolean[]{ true, true, true }), new MatrixRow("3", new boolean[]{ false, false, false }))
        );
    }

    @Test
    void createMatrixWithIncorrectRowSize_AndException() {
        Throwable ex = assertThrows(IllegalArgumentException.class, () -> new ExactCoverMatrix(
                List.of(new MatrixColumn("Wallet"), new MatrixColumn("Keys"), new MatrixColumn("Phone")),
                List.of(new MatrixRow("1", new boolean[]{ true, false, true }), new MatrixRow("2", new boolean[]{ true, true, true }), new MatrixRow("3", new boolean[]{ false, false }))
        ));

        assertTrue(ex.getMessage().contains("Exactly 3 columns required"));
    }

    @Test
    void createSparseMatrix() {
        ExactCoverMatrix ecm = new ExactCoverMatrix(
                List.of(new MatrixColumn("Wallet"), new MatrixColumn("Keys"), new MatrixColumn("Phone")),
                List.of(new MatrixRow("1", new boolean[]{ true, false, true }), new MatrixRow("2", new boolean[]{ true, true, true }), new MatrixRow("3", new boolean[]{ false, false, false }))
        );

        assertArrayEquals(new boolean[]{ true, false, true }, ecm.asSparseMatrix()[0]);
        assertArrayEquals(new boolean[]{ true, true, true }, ecm.asSparseMatrix()[1]);
        assertArrayEquals(new boolean[]{ false, false, false }, ecm.asSparseMatrix()[2]);
    }

    @Test
    void createMatrixWithDuplicatedColumnNames_AndException() {
        assertThrows(IllegalArgumentException.class, () -> new ExactCoverMatrix(
                List.of(new MatrixColumn("Wallet"), new MatrixColumn("Keys"), new MatrixColumn("Keys")),
                List.of(new MatrixRow("1", new boolean[]{ true, false, true }), new MatrixRow("2", new boolean[]{ true, true, true }), new MatrixRow("3", new boolean[]{ false, false }))
        ));
    }

    @Test
    void createMatrixWithReservedColumnNames_AndException() {
        assertThrows(IllegalArgumentException.class, () -> new ExactCoverMatrix(
                List.of(new MatrixColumn("Wallet"), new MatrixColumn("HEADER"), new MatrixColumn("Keys")),
                List.of(new MatrixRow("1", new boolean[]{ true, false, true }), new MatrixRow("2", new boolean[]{ true, true, true }), new MatrixRow("3", new boolean[]{ false, false }))
        ));
    }

    @Test
    void createMatrixWitDuplicatedRowNames_AndException() {
        assertThrows(IllegalArgumentException.class, () -> new ExactCoverMatrix(
                List.of(new MatrixColumn("Wallet"), new MatrixColumn("HEADER"), new MatrixColumn("Keys")),
                List.of(new MatrixRow("1", new boolean[]{ true, false, true }), new MatrixRow("1", new boolean[]{ true, true, true }), new MatrixRow("3", new boolean[]{ false, false }))
        ));
    }
}