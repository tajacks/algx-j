package com.tajacks.algorithms.algxj.matrix;

/**
 * @param constraintLabel - A non-null, non-empty, {@link String} identifier which represents column in an expressible format. This value cannot be the literal String value {@code HEADER}, as this is reserved.
 */
public record MatrixColumn(String constraintLabel) {
    public MatrixColumn {
        if (constraintLabel == null || constraintLabel.isEmpty()) {
            throw new IllegalArgumentException("Constraint label cannot be null or empty");
        }
    }
}
