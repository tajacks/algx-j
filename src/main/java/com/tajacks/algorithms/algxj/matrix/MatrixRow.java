package com.tajacks.algorithms.algxj.matrix;

/**
 * Represents a row in an exact cover matrix. When providing {@link #values}, abide by the following contract:
 *     <ol>
 *         <li>The length of values must exactly match the number of {@link MatrixColumn} objects during creation of {@link ExactCoverMatrix}.</li>
 *         <li>The value {@code i} at position {@code values[i]} will correspond to a {@link MatrixColumn} in {@link ExactCoverMatrix} at the same position.</li>
 *     </ol>
 * <p>
 * In other words, there cannot be more values than columns, and, the first value in {@link #values} will be treated as within the first column. Each value represents a cell within a column.
 *
 * @param valueLabel - A non-null, non-empty, {@link String} identifier which represents the set of values in an expressible format.
 * @param values     - An Array of boolean values which abides by the above stated contract
 */
public record MatrixRow(String valueLabel, boolean[] values) {
    public MatrixRow {
        if (valueLabel == null || valueLabel.isEmpty()) {
            throw new IllegalArgumentException("Constraint label cannot be null or empty");
        }
    }
}
