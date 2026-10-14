import java.util.*;

public class FlowAnalyzer {

    private static int[][] sampleNetwork() {

        return new int[][] {

            {0, 16, 13, 0, 0, 0},

            {0, 0, 10, 12, 0, 0},

            {0, 4, 0, 0, 14, 0},

            {0, 0, 9, 0, 0, 20},

            {0, 0, 0, 7, 0, 4},

            {0, 0, 0, 0, 0, 0}
        };
    }

    // =========================================
    // FORD-FULKERSON
    // =========================================

    public static int fordFulkerson() {

        int[][] capacity =
                sampleNetwork();

        int[][] residual =
                copy(capacity);

        int flow = 0;

        while (true) {

            int[] parent =
                    new int[6];

            Arrays.fill(
                    parent,
                    -1
            );

            parent[0] = 0;

            boolean[] visited =
                    new boolean[6];

            if (
                    !dfs(
                            residual,
                            0,
                            5,
                            visited,
                            parent
                    )
            ) {

                break;
            }

            int bottleneck =
                    Integer.MAX_VALUE;

            for (
                    int v = 5;
                    v != 0;
                    v = parent[v]
            ) {

                bottleneck =
                        Math.min(
                                bottleneck,
                                residual[parent[v]][v]
                        );
            }

            for (
                    int v = 5;
                    v != 0;
                    v = parent[v]
            ) {

                int u =
                        parent[v];

                residual[u][v]
                        -= bottleneck;

                residual[v][u]
                        += bottleneck;
            }

            flow += bottleneck;
        }

        return flow;
    }

    private static boolean dfs(
            int[][] residual,
            int current,
            int sink,
            boolean[] visited,
            int[] parent) {

        if (current == sink) {
            return true;
        }

        visited[current] = true;

        for (
                int next = 0;
                next < residual.length;
                next++
        ) {

            if (
                    !visited[next]
                    &&
                    residual[current][next] > 0
            ) {

                parent[next] =
                        current;

                if (
                        dfs(
                                residual,
                                next,
                                sink,
                                visited,
                                parent
                        )
                ) {

                    return true;
                }
            }
        }

        return false;
    }

    // =========================================
    // EDMONDS-KARP
    // =========================================

    public static int edmondsKarp() {

        int[][] capacity =
                sampleNetwork();

        int[][] residual =
                copy(capacity);

        int flow = 0;

        while (true) {

            int[] parent =
                    new int[6];

            Arrays.fill(
                    parent,
                    -1
            );

            parent[0] = 0;

            Queue<Integer> queue =
                    new ArrayDeque<>();

            queue.add(0);

            while (
                    !queue.isEmpty()
                    &&
                    parent[5] == -1
            ) {

                int current =
                        queue.poll();

                for (
                        int next = 0;
                        next < 6;
                        next++
                ) {

                    if (
                            parent[next] == -1
                            &&
                            residual[current][next] > 0
                    ) {

                        parent[next] =
                                current;

                        queue.add(next);
                    }
                }
            }

            if (parent[5] == -1) {
                break;
            }

            int bottleneck =
                    Integer.MAX_VALUE;

            for (
                    int v = 5;
                    v != 0;
                    v = parent[v]
            ) {

                bottleneck =
                        Math.min(
                                bottleneck,
                                residual[parent[v]][v]
                        );
            }

            for (
                    int v = 5;
                    v != 0;
                    v = parent[v]
            ) {

                int u =
                        parent[v];

                residual[u][v]
                        -= bottleneck;

                residual[v][u]
                        += bottleneck;
            }

            flow += bottleneck;
        }

        return flow;
    }

    // =========================================
    // DINIC
    // =========================================

    public static int dinic() {

        int[][] capacity =
                sampleNetwork();

        int[][] residual =
                copy(capacity);

        int n =
                residual.length;

        int totalFlow = 0;

        while (true) {

            int[] level =
                    new int[n];

            Arrays.fill(
                    level,
                    -1
            );

            Queue<Integer> queue =
                    new ArrayDeque<>();

            level[0] = 0;

            queue.add(0);

            while (!queue.isEmpty()) {

                int u =
                        queue.poll();

                for (
                        int v = 0;
                        v < n;
                        v++
                ) {

                    if (
                            level[v] == -1
                            &&
                            residual[u][v] > 0
                    ) {

                        level[v] =
                                level[u] + 1;

                        queue.add(v);
                    }
                }
            }

            if (level[5] == -1) {
                break;
            }

            while (true) {

                int pushed =
                        sendFlow(
                                residual,
                                level,
                                0,
                                5,
                                Integer.MAX_VALUE
                        );

                if (pushed == 0) {
                    break;
                }

                totalFlow += pushed;
            }
        }

        return totalFlow;
    }

    private static int sendFlow(
            int[][] residual,
            int[] level,
            int current,
            int sink,
            int flow) {

        if (current == sink) {
            return flow;
        }

        for (
                int next = 0;
                next < residual.length;
                next++
        ) {

            if (
                    level[next]
                    ==
                    level[current] + 1
                    &&
                    residual[current][next] > 0
            ) {

                int pushed =
                        sendFlow(
                                residual,
                                level,
                                next,
                                sink,
                                Math.min(
                                        flow,
                                        residual[current][next]
                                )
                        );

                if (pushed > 0) {

                    residual[current][next]
                            -= pushed;

                    residual[next][current]
                            += pushed;

                    return pushed;
                }
            }
        }

        return 0;
    }

    // =========================================
    // BIPARTITE MATCHING
    // =========================================

    public static int lawyerCaseMatching() {

        /*
         * Left side = Lawyers
         *
         * Right side = Cases
         */

        int[][] edges = {

            {0, 0},
            {0, 1},

            {1, 0},
            {1, 2},

            {2, 1},
            {2, 3},

            {3, 0},
            {3, 3}
        };

        int[] match =
                new int[4];

        Arrays.fill(
                match,
                -1
        );

        int matching = 0;

        for (int lawyer = 0;
             lawyer < 4;
             lawyer++) {

            boolean[] visited =
                    new boolean[4];

            if (
                    matchCase(
                            lawyer,
                            edges,
                            match,
                            visited
                    )
            ) {

                matching++;
            }
        }

        return matching;
    }

    private static boolean matchCase(
            int lawyer,
            int[][] edges,
            int[] match,
            boolean[] visited) {

        for (int[] edge : edges) {

            if (edge[0] != lawyer) {
                continue;
            }

            int caseId =
                    edge[1];

            if (visited[caseId]) {
                continue;
            }

            visited[caseId] = true;

            if (
                    match[caseId] == -1
                    ||
                    matchCase(
                            match[caseId],
                            edges,
                            match,
                            visited
                    )
            ) {

                match[caseId] =
                        lawyer;

                return true;
            }
        }

        return false;
    }

    private static int[][] copy(
            int[][] matrix) {

        int[][] result =
                new int[matrix.length][];

        for (int i = 0;
             i < matrix.length;
             i++) {

            result[i] =
                    matrix[i].clone();
        }

        return result;
    }
}