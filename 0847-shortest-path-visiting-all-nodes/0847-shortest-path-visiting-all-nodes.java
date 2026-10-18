import java.util.*;

class Solution {
    public int shortestPathLength(int[][] graph) {
        int n = graph.length;

        int[][] dist = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[i][i] = 0;

            Queue<Integer> q = new LinkedList<>();
            q.offer(i);

            boolean[] visited = new boolean[n];
            visited[i] = true;

            while (!q.isEmpty()) {
                int u = q.poll();

                for (int v : graph[u]) {
                    if (!visited[v]) {
                        visited[v] = true;
                        dist[i][v] = dist[i][u] + 1;
                        q.offer(v);
                    }
                }
            }
        }

        // TSP DP
        int totalMasks = 1 << n;
        int[][] dp = new int[totalMasks][n];

        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // We can start from any node
        for (int i = 0; i < n; i++) {
            dp[1 << i][i] = 0;
        }

        // Build the TSP DP
        for (int mask = 1; mask < totalMasks; mask++) {

            for (int last = 0; last < n; last++) {

                if ((mask & (1 << last)) == 0)
                    continue;

                if (dp[mask][last] == Integer.MAX_VALUE)
                    continue;

                // Go to another node
                for (int next = 0; next < n; next++) {

                    if ((mask & (1 << next)) != 0)
                        continue;

                    int newMask = mask | (1 << next);

                    dp[newMask][next] = Math.min(
                        dp[newMask][next],
                        dp[mask][last] + dist[last][next]
                    );
                }
            }
        }

        // All nodes visited
        int allVisited = totalMasks - 1;
        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            answer = Math.min(answer, dp[allVisited][i]);
        }

        return answer;
    }
}