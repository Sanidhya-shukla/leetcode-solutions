class Solution {

    public String shortestSuperstring(String[] words) {
        int n = words.length;
        int total = 1 << n;

        // overlap[i][j] = number of characters overlapping
        // when word j is placed after word i
        int[][] overlap = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;

                int max = Math.min(words[i].length(), words[j].length());

                for (int k = max; k >= 0; k--) {
                    if (words[i].endsWith(words[j].substring(0, k))) {
                        overlap[i][j] = k;
                        break;
                    }
                }
            }
        }

        // dp[mask][last] = minimum length of superstring
        // containing all words in mask and ending at 'last'
        int[][] dp = new int[total][n];
        int[][] parent = new int[total][n];

        for (int mask = 0; mask < total; mask++) {
            for (int i = 0; i < n; i++) {
                dp[mask][i] = Integer.MAX_VALUE;
                parent[mask][i] = -1;
            }
        }

        // Starting with each word
        for (int i = 0; i < n; i++) {
            dp[1 << i][i] = words[i].length();
        }

        // Bitmask DP
        for (int mask = 1; mask < total; mask++) {

            for (int last = 0; last < n; last++) {

                if ((mask & (1 << last)) == 0)
                    continue;

                if (dp[mask][last] == Integer.MAX_VALUE)
                    continue;

                for (int next = 0; next < n; next++) {

                    if ((mask & (1 << next)) != 0)
                        continue;

                    int newMask = mask | (1 << next);

                    int newLength = dp[mask][last]
                            + words[next].length()
                            - overlap[last][next];

                    if (newLength < dp[newMask][next]) {
                        dp[newMask][next] = newLength;
                        parent[newMask][next] = last;
                    }
                }
            }
        }

        // Find the best ending word
        int fullMask = total - 1;
        int last = 0;

        for (int i = 1; i < n; i++) {
            if (dp[fullMask][i] < dp[fullMask][last]) {
                last = i;
            }
        }

        // Reconstruct the order
        int[] order = new int[n];
        int mask = fullMask;

        for (int i = n - 1; i >= 0; i--) {
            order[i] = last;
            int prev = parent[mask][last];
            mask ^= (1 << last);
            last = prev;
        }

        // Construct answer
        StringBuilder ans = new StringBuilder();
        ans.append(words[order[0]]);

        for (int i = 1; i < n; i++) {
            int prev = order[i - 1];
            int curr = order[i];

            ans.append(words[curr].substring(overlap[prev][curr]));
        }

        return ans.toString();
    }
}
