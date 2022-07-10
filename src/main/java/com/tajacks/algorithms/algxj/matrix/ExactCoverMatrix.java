package com.tajacks.algorithms.algxj.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a matrix of true or false values, where each row contains an array of true or false values equal
 * to the amount of columns.
 *
 * @param columns - The constraints within the matrix.
 * @param rows    - The possible values within the matrix, with a true corresponding to a satisfied constraint.
 */
public record ExactCoverMatrix(List<MatrixColumn> columns, List<MatrixRow> rows) {
    public ExactCoverMatrix {
        checkValidRowsAndColumns(columns, rows);
    }

    // Perform all row and column validity checks.
    private void checkValidRowsAndColumns(List<MatrixColumn> columns, List<MatrixRow> rows) throws IllegalArgumentException {
        checkColumnNameValidity(columns);
        checkRowNameValidity(rows);
        rows.forEach(row -> checkRowSizeValidity(columns, row));
    }

    // Only allow rows where the number of values is equal to the number of columns exactly.
    private void checkRowSizeValidity(List<MatrixColumn> columns, MatrixRow row) throws IllegalArgumentException {
        if (row.values().length != columns.size()) {
            throw new IllegalArgumentException("Mis-matched row size. Exactly " + columns.size() + " columns required, received: " + row.values().length + " from " + row);
        }
    }

    // Ensure that no rows have duplicated names. If names are duplicated, it's difficult to determine which rows exactly covered our universe.
    private void checkRowNameValidity(List<MatrixRow> rows) throws IllegalArgumentException {
        List<String> names = new ArrayList<>();
        for (MatrixRow row : rows) {
            if (names.contains(row.valueLabel())) {
                throw new IllegalArgumentException("Duplicate named row " + row.valueLabel());
            }
            names.add(row.valueLabel());
        }
    }

    // Ensure that no columns have duplicated names, and, don't include 'HEADER'
    private void checkColumnNameValidity(List<MatrixColumn> columns) throws IllegalArgumentException {
        List<String> names = new ArrayList<>(Collections.singleton("HEADER"));
        for (MatrixColumn column : columns) {
            if (names.contains(column.constraintLabel())) {
                throw new IllegalArgumentException("Duplicate named column " + column.constraintLabel());
            }
            names.add(column.constraintLabel());
        }
    }

    /**
     * Return a sparse matrix representation of this matrix object.
     *
     * @return - A two-dimensional boolean array where the first dimension represents entire rows and the second the values for each column within aforementioned row.
     */
    public boolean[][] asSparseMatrix() {
        boolean[][] sparseMatrix = new boolean[rows.size()][columns.size()];

        for (int s = 0; s < rows.size(); s++) {
            sparseMatrix[s] = rows.get(s).values();
        }
        return sparseMatrix;
    }
}
