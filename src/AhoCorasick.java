import java.util.*;

public class AhoCorasick {

    private static class Node {

        int[] next =
                new int[26];

        int fail;

        List<String> output =
                new ArrayList<>();

        Node() {
            Arrays.fill(next, -1);
        }
    }

    private final List<Node> trie =
            new ArrayList<>();

    public AhoCorasick(
            Collection<String> patterns) {

        trie.add(new Node());

        for (String pattern : patterns) {

            add(pattern);
        }

        build();
    }

    private void add(String pattern) {

        int current = 0;

        for (
                char ch :
                pattern.toLowerCase().toCharArray()
        ) {

            if (ch < 'a' || ch > 'z') {
                continue;
            }

            int c = ch - 'a';

            if (trie.get(current).next[c] == -1) {

                trie.get(current).next[c] =
                        trie.size();

                trie.add(new Node());
            }

            current =
                    trie.get(current).next[c];
        }

        trie.get(current)
                .output
                .add(pattern);
    }

    private void build() {

        Queue<Integer> queue =
                new ArrayDeque<>();

        for (int c = 0; c < 26; c++) {

            int child =
                    trie.get(0).next[c];

            if (child == -1) {

                trie.get(0).next[c] = 0;

            } else {

                trie.get(child).fail = 0;

                queue.add(child);
            }
        }

        while (!queue.isEmpty()) {

            int current =
                    queue.poll();

            for (int c = 0; c < 26; c++) {

                int child =
                        trie.get(current).next[c];

                if (child == -1) {

                    trie.get(current).next[c] =
                            trie.get(
                                    trie.get(current).fail
                            ).next[c];

                } else {

                    trie.get(child).fail =
                            trie.get(
                                    trie.get(current).fail
                            ).next[c];

                    trie.get(child)
                            .output
                            .addAll(
                                    trie.get(
                                            trie.get(child).fail
                                    ).output
                            );

                    queue.add(child);
                }
            }
        }
    }

    public List<String> search(String text) {

        List<String> found =
                new ArrayList<>();

        int current = 0;

        for (
                char ch :
                text.toLowerCase().toCharArray()
        ) {

            if (ch < 'a' || ch > 'z') {

                current = 0;

                continue;
            }

            current =
                    trie.get(current)
                            .next[ch - 'a'];

            found.addAll(
                    trie.get(current)
                            .output
            );
        }

        return found;
    }
}