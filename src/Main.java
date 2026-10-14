import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashSet;
import java.util.List;

public class Main {

    private final DocumentRepository repository =
            new DocumentRepository();

    private final LegalSearchEngine engine;

    private final JFrame frame =
            new JFrame(
                    "LegalSearch — Intelligent Legal Analytics"
            );

    private final JTextArea output =
            new JTextArea();

    private final Color NAVY =
            new Color(10, 18, 35);

    private final Color PANEL =
            new Color(20, 31, 52);

    private final Color BLUE =
            new Color(70, 145, 255);

    private final Color WHITE =
            new Color(235, 241, 250);

    private final Color MUTED =
            new Color(155, 170, 195);


    public Main() {

        repository.seed();

        engine =
                new LegalSearchEngine(
                        repository
                );

        buildUI();
    }


    private void buildUI() {

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setSize(
                1200,
                760
        );

        frame.setLocationRelativeTo(null);


        JPanel root =
                new JPanel(
                        new BorderLayout(
                                18,
                                18
                        )
                );

        root.setBackground(NAVY);

        root.setBorder(
                new EmptyBorder(
                        18,
                        18,
                        18,
                        18
                )
        );


        root.add(
                createHeader(),
                BorderLayout.NORTH
        );

        root.add(
                createSidebar(),
                BorderLayout.WEST
        );

        root.add(
                createContent(),
                BorderLayout.CENTER
        );


        frame.setContentPane(root);

        frame.setVisible(true);
    }


    private JPanel createHeader() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setOpaque(false);


        JLabel title =
                new JLabel(
                        "LEGALSEARCH"
                );

        title.setForeground(
                WHITE
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        30
                )
        );


        JLabel subtitle =
                new JLabel(
                        "   INTELLIGENT LEGAL DOCUMENT ANALYTICS  •  DSA CO1–CO4"
                );

        subtitle.setForeground(
                MUTED
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );


        panel.add(
                title,
                BorderLayout.WEST
        );

        panel.add(
                subtitle,
                BorderLayout.CENTER
        );


        return panel;
    }


    private JPanel createSidebar() {

        JPanel panel =
                new JPanel();

        panel.setPreferredSize(
                new Dimension(
                        230,
                        0
                )
        );

        panel.setLayout(
                new GridLayout(
                        8,
                        1,
                        8,
                        8
                )
        );

        panel.setBackground(
                PANEL
        );


        addButton(
                panel,
                "⌂  Dashboard",
                e -> dashboard()
        );


        addButton(
                panel,
                "⌕  Legal Search",
                e -> legalSearch()
        );


        addButton(
                panel,
                "⚖  Risk Analysis",
                e -> riskAnalysis()
        );


        addButton(
                panel,
                "⇄  Compare Contracts",
                e -> compareContracts()
        );


        addButton(
                panel,
                "∑  Suffix Intelligence",
                e -> suffixAnalysis()
        );


        addButton(
                panel,
                "◈  DP Analytics",
                e -> dpAnalytics()
        );


        addButton(
                panel,
                "⇢  Network Optimizer",
                e -> networkAnalysis()
        );


        addButton(
                panel,
                "▤  Documents",
                e -> documents()
        );


        return panel;
    }


    private void addButton(
            JPanel panel,
            String text,
            java.awt.event.ActionListener action) {

        JButton button =
                new JButton(text);

        button.setFocusPainted(false);

        button.setForeground(
                WHITE
        );

        button.setBackground(
                new Color(
                        28,
                        43,
                        72
                )
        );

        button.setBorder(
                new EmptyBorder(
                        12,
                        10,
                        12,
                        10
                )
        );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.addActionListener(
                action
        );

        panel.add(button);
    }


    private JPanel createContent() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setOpaque(false);


        output.setEditable(false);

        output.setLineWrap(true);

        output.setWrapStyleWord(true);

        output.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14
                )
        );

        output.setForeground(
                WHITE
        );

        output.setBackground(
                new Color(
                        14,
                        24,
                        42
                )
        );

        output.setBorder(
                new EmptyBorder(
                        22,
                        22,
                        22,
                        22
                )
        );


        panel.add(
                new JScrollPane(output),
                BorderLayout.CENTER
        );


        dashboard();

        return panel;
    }


    private void showPage(
            String title,
            String content) {

        output.setText(
                title
                + "\n"
                + "============================================================\n\n"
                + content
        );
    }


    // =========================================
    // DASHBOARD
    // =========================================

    private void dashboard() {

        StringBuilder text =
                new StringBuilder();


        text.append(
                "SYSTEM STATUS\n\n"
        );

        text.append(
                "DOCUMENTS INDEXED       "
        );

        text.append(
                repository.size()
        );

        text.append("\n");

        text.append(
                "STRING ALGORITHMS       4\n"
        );

        text.append(
                "SUFFIX STRUCTURES       2+\n"
        );

        text.append(
                "DP MODULES              4\n"
        );

        text.append(
                "NETWORK ALGORITHMS      4\n\n"
        );


        text.append(
                "LEGAL RISK SNAPSHOT\n\n"
        );


        for (
                LegalDocument document :
                repository.all()
        ) {

            List<String> terms =
                    engine.detectRiskTerms(
                            document
                    );

            String level;

            if (terms.size() >= 4) {

                level = "HIGH";

            } else if (terms.size() >= 2) {

                level = "MEDIUM";

            } else {

                level = "LOW";
            }


            text.append(
                    String.format(
                            "%-30s %-8s %d terms%n",
                            document.getTitle(),
                            level,
                            terms.size()
                    )
            );
        }


        text.append(
                "\n\n"
        );

        text.append(
                "PROJECT PIPELINE\n\n"
        );

        text.append(
                "Documents → String Search → "
                + "Suffix Intelligence → "
                + "Contract Comparison → "
                + "Network Optimization"
        );


        showPage(
                "LEGALSEARCH / COMMAND CENTER",
                text.toString()
        );
    }


    // =========================================
    // CO1
    // =========================================

    private void legalSearch() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                0,
                                1,
                                8,
                                8
                        )
                );


        JTextField query =
                new JTextField();


        JComboBox<String> algorithm =
                new JComboBox<>(
                        new String[]{
                                "KMP",
                                "Rabin-Karp",
                                "Z Algorithm"
                        }
                );


        panel.add(
                new JLabel(
                        "Enter legal phrase:"
                )
        );

        panel.add(query);

        panel.add(
                new JLabel(
                        "Search algorithm:"
                )
        );

        panel.add(algorithm);


        int result =
                JOptionPane.showConfirmDialog(
                        frame,
                        panel,
                        "Legal Search",
                        JOptionPane.OK_CANCEL_OPTION
                );


        if (
                result
                !=
                JOptionPane.OK_OPTION
        ) {
            return;
        }


        String searchText =
                query.getText().trim();


        if (searchText.isEmpty()) {
            return;
        }


        List<LegalDocument> matches =
                engine.search(
                        searchText,
                        (String)
                        algorithm.getSelectedItem()
                );


        StringBuilder text =
                new StringBuilder();


        text.append(
                "QUERY : "
        );

        text.append(
                searchText
        );

        text.append("\n");


        text.append(
                "ALGORITHM : "
        );

        text.append(
                algorithm.getSelectedItem()
        );

        text.append("\n\n");


        if (matches.isEmpty()) {

            text.append(
                    "No matching legal documents found."
            );

        } else {

            text.append(
                    "MATCHING DOCUMENTS\n\n"
            );


            for (
                    LegalDocument document :
                    matches
            ) {

                text.append(
                        "● "
                );

                text.append(
                        document.getTitle()
                );

                text.append(
                        "  ["
                );

                text.append(
                        document.getType()
                );

                text.append(
                        "]\n"
                );
            }
        }


        showPage(
                "CO1 / INTELLIGENT LEGAL SEARCH",
                text.toString()
        );
    }


    // =========================================
    // AHO CORASICK
    // =========================================

    private void riskAnalysis() {

        StringBuilder text =
                new StringBuilder();


        text.append(
                "MULTI-PATTERN LEGAL RISK DETECTION\n\n"
        );


        for (
                LegalDocument document :
                repository.all()
        ) {

            List<String> terms =
                    engine.detectRiskTerms(
                            document
                    );


            String level;

            if (terms.size() >= 4) {

                level = "HIGH";

            } else if (terms.size() >= 2) {

                level = "MEDIUM";

            } else {

                level = "LOW";
            }


            text.append(
                    document.getTitle()
            );

            text.append("\n");

            text.append(
                    "Risk Level : "
            );

            text.append(
                    level
            );

            text.append("\n");

            text.append(
                    "Detected Terms : "
            );

            text.append(
                    new LinkedHashSet<>(
                            terms
                    )
            );

            text.append(
                    "\n\n"
            );
        }


        showPage(
                "CO1 / AHO-CORASICK RISK ENGINE",
                text.toString()
        );
    }


    // =========================================
    // CO2
    // =========================================

    private void suffixAnalysis() {

        LegalDocument document =
                repository.get(1);


        String report =
                SuffixAnalyzer.report(
                        document.getContent()
                );


        showPage(
                "CO2 / SUFFIX INTELLIGENCE",
                "ANALYZED DOCUMENT\n\n"
                + document.getTitle()
                + "\n\n"
                + report
        );
    }


    // =========================================
    // CO3
    // =========================================

    private void compareContracts() {

        String[] names =
                repository.all()
                        .stream()
                        .map(
                                LegalDocument::getTitle
                        )
                        .toArray(
                                String[]::new
                        );


        JComboBox<String> first =
                new JComboBox<>(
                        names
                );


        JComboBox<String> second =
                new JComboBox<>(
                        names
                );


        JPanel panel =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                10,
                                10
                        )
                );


        panel.add(
                new JLabel(
                        "Document A"
                )
        );

        panel.add(first);


        panel.add(
                new JLabel(
                        "Document B"
                )
        );

        panel.add(second);


        int result =
                JOptionPane.showConfirmDialog(
                        frame,
                        panel,
                        "Compare Legal Documents",
                        JOptionPane.OK_CANCEL_OPTION
                );


        if (
                result
                !=
                JOptionPane.OK_OPTION
        ) {
            return;
        }


        LegalDocument firstDocument =
                repository.get(
                        first.getSelectedIndex()
                        + 1
                );


        LegalDocument secondDocument =
                repository.get(
                        second.getSelectedIndex()
                        + 1
                );


        showPage(
                "CO3 / CONTRACT INTELLIGENCE",

                engine.compare(
                        firstDocument,
                        secondDocument
                )
        );
    }


    private void dpAnalytics() {

        int[][] distance = {

                {0, 10, 15, 20},

                {10, 0, 35, 25},

                {15, 35, 0, 30},

                {20, 25, 30, 0}
        };


        int tsp =
                DPAnalyzer.tsp(
                        distance
                );


        int matrix =
                DPAnalyzer.matrixChain(
                        new int[]{
                                10,
                                20,
                                30,
                                40,
                                30
                        }
                );


        String text =
                "ADVANCED DYNAMIC PROGRAMMING\n\n"

                + "BITMASK DP / TSP\n"
                + "Minimum route cost : "
                + tsp
                + "\n\n"

                + "MATRIX CHAIN MULTIPLICATION\n"
                + "Minimum operations : "
                + matrix
                + "\n\n"

                + "EDIT DISTANCE\n"
                + "Implemented inside Contract Intelligence.\n\n"

                + "WEIGHTED EDIT DISTANCE\n"
                + "Implemented inside Contract Intelligence.";


        showPage(
                "CO3 / DP ANALYTICS",
                text
        );
    }


    // =========================================
    // CO4
    // =========================================

    private void networkAnalysis() {

        int ford =
                FlowAnalyzer.fordFulkerson();

        int edmonds =
                FlowAnalyzer.edmondsKarp();

        int dinic =
                FlowAnalyzer.dinic();

        int matching =
                FlowAnalyzer.lawyerCaseMatching();


        String text =
                "NETWORK FLOW RESULTS\n\n"

                + "Ford-Fulkerson       : "
                + ford
                + "\n"

                + "Edmonds-Karp         : "
                + edmonds
                + "\n"

                + "Dinic                : "
                + dinic
                + "\n\n"

                + "LAWYER–CASE ASSIGNMENT\n\n"

                + "Maximum Bipartite Matching : "
                + matching
                + "\n\n"

                + "APPLICATION\n"
                + "Lawyers are represented as the left partition "
                + "and legal cases as the right partition. "
                + "An edge represents a compatible lawyer-case assignment.";


        showPage(
                "CO4 / NETWORK OPTIMIZER",
                text
        );
    }


    // =========================================
    // DOCUMENTS
    // =========================================

    private void documents() {

        StringBuilder text =
                new StringBuilder();


        text.append(
                "LEGAL DOCUMENT REPOSITORY\n\n"
        );


        for (
                LegalDocument document :
                repository.all()
        ) {

            text.append(
                    "["
            );

            text.append(
                    document.getId()
            );

            text.append(
                    "] "
            );

            text.append(
                    document.getTitle()
            );

            text.append(
                    "  •  "
            );

            text.append(
                    document.getType()
            );

            text.append(
                    "\n"
            );

            text.append(
                    document.getContent()
            );

            text.append(
                    "\n\n"
            );
        }


        showPage(
                "DOCUMENT REPOSITORY",
                text.toString()
        );
    }


    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                Main::new
        );
    }


	public Color getBLUE() {
		return BLUE;
	}
}