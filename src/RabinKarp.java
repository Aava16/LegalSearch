import java.util.ArrayList;
import java.util.List;

public class RabinKarp {

    private static final long BASE = 256;

    private static final long MOD =
            1_000_000_007L;

    public static List<Integer> search(
            String text,
            String pattern) {

        List<Integer> result =
                new ArrayList<>();

        int n = text.length();

        int m = pattern.length();

        if (m == 0 || m > n) {
            return result;
        }

        long highPower = 1;

        long patternHash = 0;

        long textHash = 0;

        for (int i = 1; i < m; i++) {

            highPower =
                    (highPower * BASE) % MOD;
        }

        for (int i = 0; i < m; i++) {

            patternHash =
                    (patternHash * BASE
                            + pattern.charAt(i))
                            % MOD;

            textHash =
                    (textHash * BASE
                            + text.charAt(i))
                            % MOD;
        }

        for (int i = 0; i <= n - m; i++) {

            if (patternHash == textHash) {

                if (text.regionMatches(
                        i,
                        pattern,
                        0,
                        m)) {

                    result.add(i);
                }
            }

            if (i < n - m) {

                textHash =
                        (textHash
                                - text.charAt(i)
                                * highPower)
                                % MOD;

                if (textHash < 0) {
                    textHash += MOD;
                }

                textHash =
                        (textHash * BASE
                                + text.charAt(i + m))
                                % MOD;
            }
        }

        return result;
    }
}