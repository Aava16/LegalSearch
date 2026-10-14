import java.util.*;

public class LegalSearchEngine {

    private final DocumentRepository repository;

    private final AhoCorasick ahoCorasick;

    public LegalSearchEngine(
            DocumentRepository repository) {

        this.repository =
                repository;

        ahoCorasick =
                new AhoCorasick(
                        Arrays.asList(
                                "breach",
                                "liability",
                                "penalty",
                                "confidentiality",
                                "termination",
                                "damages",
                                "negligence",
                                "contract"
                        )
                );
    }

    // =========================================
    // CO1 SEARCH
    // =========================================

    public List<LegalDocument> search(
            String query,
            String algorithm) {

        List<LegalDocument> result =
                new ArrayList<>();

        for (
                LegalDocument document :
                repository.all()
        ) {

            String text =
                    document
                            .getContent()
                            .toLowerCase();

            String pattern =
                    query.toLowerCase();

            boolean found;

            switch (algorithm) {

                case "KMP":

                    found =
                            !KMP.search(
                                    text,
                                    pattern
                            ).isEmpty();

                    break;

                case "Rabin-Karp":

                    found =
                            !RabinKarp.search(
                                    text,
                                    pattern
                            ).isEmpty();

                    break;

                case "Z Algorithm":

                    found =
                            !ZAlgorithm.search(
                                    text,
                                    pattern
                            ).isEmpty();

                    break;

                default:

                    found = false;
            }

            if (found) {

                result.add(document);
            }
        }

        return result;
    }

    // =========================================
    // AHO-CORASICK
    // =========================================

    public List<String> detectRiskTerms(
            LegalDocument document) {

        return ahoCorasick.search(
                document.getContent()
        );
    }

    // =========================================
    // CO3 DOCUMENT COMPARISON
    // =========================================

    public String compare(
            LegalDocument first,
            LegalDocument second) {

        int distance =
                DPAnalyzer.levenshtein(
                        first.getContent(),
                        second.getContent()
                );

        int weighted =
                DPAnalyzer.weightedEditDistance(
                        first.getContent(),
                        second.getContent()
                );

        int maxLength =
                Math.max(
                        first.getContent().length(),
                        second.getContent().length()
                );

        int similarity;

        if (maxLength == 0) {

            similarity = 100;

        } else {

            similarity =
                    (int)
                    Math.round(
                            100.0
                            *
                            (
                                1.0
                                -
                                (double) distance
                                / maxLength
                            )
                    );
        }

        return
                "DOCUMENT COMPARISON\n\n"

                + "Document A : "
                + first.getTitle()
                + "\n"

                + "Document B : "
                + second.getTitle()
                + "\n\n"

                + "Similarity          : "
                + similarity
                + "%\n"

                + "Levenshtein Distance: "
                + distance
                + "\n"

                + "Weighted Distance   : "
                + weighted
                + "\n";
    }
}