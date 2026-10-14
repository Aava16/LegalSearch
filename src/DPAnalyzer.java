import java.util.Arrays;

public class DPAnalyzer {

    // -----------------------------------------
    // LEVENSHTEIN EDIT DISTANCE
    // -----------------------------------------

    public static int levenshtein(
            String a,
            String b) {

        int n = a.length();
        int m = b.length();

        int[][] dp =
                new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= m; j++) {

                if (
                        a.charAt(i - 1)
                        ==
                        b.charAt(j - 1)
                ) {

                    dp[i][j] =
                            dp[i - 1][j - 1];

                } else {

                    dp[i][j] =
                            1 +
                            Math.min(
                                    dp[i - 1][j - 1],
                                    Math.min(
                                            dp[i - 1][j],
                                            dp[i][j - 1]
                                    )
                            );
                }
            }
        }

        return dp[n][m];
    }

    // -----------------------------------------
    // WEIGHTED EDIT DISTANCE
    // -----------------------------------------

    public static int weightedEditDistance(
            String a,
            String b) {

        int insertCost = 1;
        int deleteCost = 1;
        int replaceCost = 2;

        int n = a.length();
        int m = b.length();

        int[][] dp =
                new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {

            dp[i][0] =
                    i * deleteCost;
        }

        for (int j = 1; j <= m; j++) {

            dp[0][j] =
                    j * insertCost;
        }

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= m; j++) {

                if (
                        a.charAt(i - 1)
                        ==
                        b.charAt(j - 1)
                ) {

                    dp[i][j] =
                            dp[i - 1][j - 1];

                } else {

                    dp[i][j] =
                            Math.min(
                                    dp[i - 1][j]
                                            + deleteCost,

                                    Math.min(
                                            dp[i][j - 1]
                                                    + insertCost,

                                            dp[i - 1][j - 1]
                                                    + replaceCost
                                    )
                            );
                }
            }
        }

        return dp[n][m];
    }

    // -----------------------------------------
    // BITMASK DP / TSP
    // -----------------------------------------

    public static int tsp(int[][] distance) {

        int n =
                distance.length;

        int total =
                1 << n;

        int INF =
                1_000_000_000;

        int[][] dp =
                new int[total][n];

        for (int[] row : dp) {

            Arrays.fill(
                    row,
                    INF
            );
        }

        dp[1][0] = 0;

        for (
                int mask = 1;
                mask < total;
                mask++
        ) {

            for (
                    int current = 0;
                    current < n;
                    current++
            ) {

                if (
                        (mask
                        & (1 << current))
                        == 0
                ) {
                    continue;
                }

                if (
                        dp[mask][current]
                        == INF
                ) {
                    continue;
                }

                for (
                        int next = 0;
                        next < n;
                        next++
                ) {

                    if (
                            (mask
                            & (1 << next))
                            != 0
                    ) {
                        continue;
                    }

                    int nextMask =
                            mask
                            | (1 << next);

                    dp[nextMask][next] =
                            Math.min(
                                    dp[nextMask][next],

                                    dp[mask][current]
                                    +
                                    distance[current][next]
                            );
                }
            }
        }

        int answer = INF;

        int fullMask =
                total - 1;

        for (int i = 1; i < n; i++) {

            answer =
                    Math.min(
                            answer,

                            dp[fullMask][i]
                            +
                            distance[i][0]
                    );
        }

        return answer;
    }

    // -----------------------------------------
    // MATRIX CHAIN MULTIPLICATION
    // -----------------------------------------

    public static int matrixChain(
            int[] dimensions) {

        int n =
                dimensions.length - 1;

        int[][] dp =
                new int[n][n];

        for (
                int length = 2;
                length <= n;
                length++
        ) {

            for (
                    int i = 0;
                    i <= n - length;
                    i++
            ) {

                int j =
                        i + length - 1;

                dp[i][j] =
                        Integer.MAX_VALUE;

                for (
                        int k = i;
                        k < j;
                        k++
                ) {

                    int cost =
                            dp[i][k]
                            +
                            dp[k + 1][j]
                            +
                            dimensions[i]
                            * dimensions[k + 1]
                            * dimensions[j + 1];

                    dp[i][j] =
                            Math.min(
                                    dp[i][j],
                                    cost
                            );
                }
            }
        }

        return dp[0][n - 1];
    }
}