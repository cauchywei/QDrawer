package org.sssta.qdrawer.ast.node

/**
 * Created by cauchywei on 15/12/13.
 */
class CodeRange {
    int startRow,startCol
    int endRow,endCol

    void union(CodeRange range) {
        if (startRow > range.startRow) {
            startRow = range.startRow
        }

        if (startCol > range.startCol) {
            startCol = range.startCol
        }

        if (endRow < range.endRow) {
            endRow = range.endRow
        }

        if (endCol < range.endCol) {
            endCol = range.endCol
        }
    }

}
