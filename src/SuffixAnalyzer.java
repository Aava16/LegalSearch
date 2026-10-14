import java.util.Arrays;

public class SuffixAnalyzer {

    public static int[] suffixArray(String text) {

        int n = text.length();

        Integer[] suffix =
                new Integer[n];

        int[] rank =
                new int[n];

        int[] newRank =
                new int[n];

        for (int i = 0; i < n; i++) {

            suffix[i] = i;

            rank[i] =
                    text.charAt(i);
        }

        for (int k = 1; k < n; k *= 2) {

            final int step = k;

            Arrays.sort(
                    suffix,
                    (a, b) -> {

                        if (rank[a] != rank[b]) {

                            return Integer.compare(
                                    rank[a],
                                    rank[b]
                            );
                        }

                        int ra =
                                a + step < n
                                        ? rank[a + step]
                                        : -1;

                        int rb =
                                b + step < n
                                        ? rank[b + step]
                                        : -1;

                        return Integer.compare(
                                ra,
                                rb
                        );
                    }
            );

            newRank[suffix[0]] = 0;

            for (int i = 1;
                 i < n;
                 i++) {

                int previous =
                        suffix[i - 1];

                int current =
                        suffix[i];

                int previousSecond =
                        previous + step < n
                                ? rank[previous + step]
                                : -1;

                int currentSecond =
                        current + step < n
                                ? rank[current + step]
                                : -1;

                newRank[current] =
                        newRank[previous]
                        +
                        (
                            rank[previous]
                            != rank[current]
                            ||
                            previousSecond
                            != currentSecond
                            ? 1 : 0
                        );
            }

            System.arraycopy(
                    newRank,
                    0,
                    rank,
                    0,
                    n
            );

            if (rank[suffix[n - 1]] == n - 1) {
                break;
            }
        }

        int[] result =
                new int[n];

        for (int i = 0; i < n; i++) {

            result[i] =
                    suffix[i];
        }

        return result;
    }

    public static int[] lcp(
            String text,
            int[] suffixArray) {

        int n = text.length();

        int[] rank =
                new int[n];

        for (int i = 0; i < n; i++) {

            rank[suffixArray[i]] = i;
        }

        int[] lcp =
                new int[n];

        int k = 0;

        for (int i = 0; i < n; i++) {

            int r = rank[i];

            if (r == n - 1) {

                k = 0;

                continue;
            }

            int j =
                    suffixArray[r + 1];

            while (
                    i + k < n
                    &&
                    j + k < n
                    &&
                    text.charAt(i + k)
                    ==
                    text.charAt(j + k)
            ) {

                k++;
            }

            lcp[r] = k;

            if (k > 0) {
                k--;
            }
        }

        return lcp;
    }

    public static String report(String text) {

        int[] suffixArray =
                suffixArray(text);

        int[] lcp =
                lcp(text, suffixArray);

        StringBuilder report =
                new StringBuilder();

        report.append(
                "SUFFIX ARRAY\n"
        );

        report.append(
                Arrays.toString(suffixArray)
        );

        report.append(
                "\n\nKASAI LCP\n"
        );

        report.append(
                Arrays.toString(lcp)
        );

        report.append(
                "\n\nREPEATED STRUCTURES\n"
        );

        for (int i = 0;
             i < lcp.length - 1;
             i++) {

            if (lcp[i] >= 3) {

                report.append(
                        "• Common prefix length "
                        + lcp[i]
                        + " between suffixes "
                        + suffixArray[i]
                        + " and "
                        + suffixArray[i + 1]
                        + "\n"
                );
            }
        }

        return report.toString();
    }
}