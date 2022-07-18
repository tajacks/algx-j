## Solving Exact Cover with DLX 

This project is a Java implementation of the Dancing Links algorithm used to solve exact cover problems.

### Usage

Exact cover matrices require a `List<MatrixRow>` and `List<MatrixColumn>` to be created. The values represented 
in each row must match the number of columns, otherwise an `IllegalNodeColumnException` is thrown.

Creating a Matrix:

```java
        ExactCoverMatrix ecm = new ExactCoverMatrix(
                List.of(new MatrixColumn("Wallet"), new MatrixColumn("Keys"), new MatrixColumn("Phone")),
                List.of(new MatrixRow("1", new boolean[]{ true, false, true }), new MatrixRow("2", new boolean[]{ true, true, true }), new MatrixRow("3", new boolean[]{ false, false, false }))
        );
```

To create a solvable DLX Board, pass an `ExactCoverMatrix` to the constructor in `DLXBoard`:

```java
DLXBoard b = new DLXBoard(ecm);
```

If a solution is found, the answer(s) will be returned as a `List<List<Node>>`, where each `List<Node>` represents a solution. The following:


```java
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
```

Results in:

`[[1, 4], [5]]`

### Acknowledgements

https://github.com/rafalio/dancing-links-java

https://www.baeldung.com/java-sudoku

https://www.stolaf.edu/people/hansonr/sudoku/exactcover.htm

https://gieseanw.wordpress.com/2011/06/16/solving-sudoku-revisited/

https://garethrees.org/2007/06/10/zendoku-generation/#section-4
