
class Solution {
    int count = 0;
    int rows, cols;

    public int uniquePathsIII(int[][] grid) {
        rows = grid.length;
        cols = grid[0].length;
        count = 0;

        int startRow = 0, startCol = 0;
        int emptyCells = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) {
                    emptyCells++;
                } else if (grid[i][j] == 1) {
                    startRow = i;
                    startCol = j;
                }
            }
        }

        dfs(grid, startRow, startCol, emptyCells + 1);
        return count;
    }

    private void dfs(int[][] grid, int r, int c, int remaining) {
        if (grid[r][c] == 2) {
            if (remaining == 0) {
                count++;
            }
            return;
        }

        int original = grid[r][c];
        grid[r][c] = -1;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int k = 0; k < 4; k++) {
            int nr = r + dr[k];
            int nc = c + dc[k];

            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
                continue;
            }

            if (grid[nr][nc] == 2) {
                dfs(grid, nr, nc, remaining - 1);
            } else if (grid[nr][nc] == 0) {
                dfs(grid, nr, nc, remaining - 1);
            }
        }

        grid[r][c] = original;
    }
}
