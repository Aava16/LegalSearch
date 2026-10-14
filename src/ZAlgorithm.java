import java.util.ArrayList;
import java.util.List;

public class ZAlgorithm {

    public static List<Integer> search(
            String text,
            String pattern) {

        List<Integer> result =
                new ArrayList<>();

        if (pattern.isEmpty()) {
            return result;
        }

        String combined =
                pattern + "$" + text;

        int[] z =
                new int[combined.length()];

        int left = 0;
        int right = 0;

        for (int i = 1;
             i < combined.length();
             i++) {

            if (i <= right) {

                z[i] =
                        Math.min(
                                right - i + 1,
                                z[i - left]
                        );
            }

            while (
                    i + z[i] < combined.length()
                    &&
                    combined.charAt(z[i])
                    ==
                    combined.charAt(i + z[i])
            ) {

                z[i]++;
            }

            if (i + z[i] - 1 > right) {

                left = i;

                right =
                        i + z[i] - 1;
            }
        }

        for (
                int i = pattern.length() + 1;
                i < combined.length();
                i++
        ) {

            if (z[i] >= pattern.length()) {

                result.add(
                        i - pattern.length() - 1
                );
            }
        }

        return result;
    }
}