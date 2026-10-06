import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AIDisasterAssistant extends JFrame {

    // ================= COLORS =================
    private final Color BG = new Color(7, 17, 31);
    private final Color PANEL = new Color(13, 27, 45);
    private final Color PANEL2 = new Color(16, 35, 58);
    private final Color CYAN = new Color(38, 217, 255);
    private final Color WHITE = new Color(238, 247, 255);
    private final Color MUTED = new Color(147, 169, 189);
    private final Color RED = new Color(255, 77, 103);
    private final Color GREEN = new Color(57, 217, 138);
    private final Color YELLOW = new Color(255, 184, 77);

    // ================= COMPONENTS =================
    private JComboBox<String> disasterBox;
    private JSlider severitySlider;
    private JLabel severityValue;

    private JLabel riskScore;
    private JLabel riskLevel;
    private JLabel responseLevel;

    private JTextArea outputArea;
    private JPanel riskMapPanel;

    // ================= CONSTRUCTOR =================
    public AIDisasterAssistant() {

        setTitle("AI Disaster Assistant - Hackathon Project");

        setSize(1200, 750);

        setMinimumSize(new Dimension(950, 650));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        buildUI();
    }

    // ================= MAIN UI =================
    private void buildUI() {

        JPanel root = new JPanel(new BorderLayout());

        root.setBackground(BG);

        // Header
        root.add(createHeader(), BorderLayout.NORTH);

        // Main content
        JPanel content = new JPanel(new BorderLayout(15, 15));

        content.setBackground(BG);

        content.setBorder(
                new EmptyBorder(15, 20, 15, 20)
        );

        // Left control panel
        content.add(
                createControlPanel(),
                BorderLayout.WEST
        );

        // Center panels
        JPanel center = new JPanel(
                new GridLayout(2, 1, 15, 15)
        );

        center.setBackground(BG);

        center.add(createRiskPanel());

        center.add(createOutputPanel());

        content.add(
                center,
                BorderLayout.CENTER
        );

        // Right emergency panel
        content.add(
                createEmergencyPanel(),
                BorderLayout.EAST
        );

        root.add(
                content,
                BorderLayout.CENTER
        );

        setContentPane(root);
    }

    // ================= HEADER =================
    private JPanel createHeader() {

        JPanel header = new JPanel(
                new BorderLayout()
        );

        header.setBackground(
                new Color(8, 24, 42)
        );

        header.setBorder(
                new EmptyBorder(
                        16, 22, 16, 22
                )
        );

        JLabel title = new JLabel(
                "⚠  AI DISASTER ASSISTANT"
        );

        title.setForeground(CYAN);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        JLabel subtitle = new JLabel(
                "Predict Risk  •  Early Warning  •  Emergency Response"
        );

        subtitle.setForeground(MUTED);

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        JPanel left = new JPanel(
                new GridLayout(2, 1)
        );

        left.setOpaque(false);

        left.add(title);

        left.add(subtitle);

        JLabel online = new JLabel(
                "● SYSTEM ONLINE"
        );

        online.setForeground(GREEN);

        online.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        header.add(
                left,
                BorderLayout.WEST
        );

        header.add(
                online,
                BorderLayout.EAST
        );

        return header;
    }

    // ================= CONTROL PANEL =================
    private JPanel createControlPanel() {

        JPanel panel =
                createPanel(
                        "DISASTER SCENARIO"
                );

        panel.setPreferredSize(
                new Dimension(245, 0)
        );

        JPanel inside = new JPanel();

        inside.setBackground(PANEL);

        inside.setLayout(
                new BoxLayout(
                        inside,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel typeLabel =
                label("Select Disaster Type");

        disasterBox =
                new JComboBox<>(
                        new String[]{
                                "Flood",
                                "Earthquake",
                                "Fire",
                                "Extreme Weather"
                        }
                );

        styleCombo(disasterBox);

        JLabel severityLabel =
                label("Severity");

        severitySlider =
                new JSlider(
                        1,
                        10,
                        6
                );

        severitySlider.setBackground(PANEL);

        severitySlider.setForeground(CYAN);

        severitySlider.addChangeListener(
                e -> severityValue.setText(
                        severitySlider.getValue()
                                + " / 10"
                )
        );

        severityValue =
                label("6 / 10");

        severityValue.setForeground(CYAN);

        severityValue.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        JButton analyze =
                button(
                        "RUN AI RISK ANALYSIS"
                );

        analyze.addActionListener(
                e -> analyzeRisk()
        );

        JButton reset =
                button("RESET");

        reset.addActionListener(
                e -> resetDashboard()
        );

        inside.add(typeLabel);

        inside.add(
                Box.createVerticalStrut(8)
        );

        inside.add(disasterBox);

        inside.add(
                Box.createVerticalStrut(22)
        );

        inside.add(severityLabel);

        inside.add(
                Box.createVerticalStrut(5)
        );

        inside.add(severitySlider);

        inside.add(severityValue);

        inside.add(
                Box.createVerticalStrut(22)
        );

        inside.add(analyze);

        inside.add(
                Box.createVerticalStrut(10)
        );

        inside.add(reset);

        inside.add(
                Box.createVerticalGlue()
        );

        panel.add(
                inside,
                BorderLayout.CENTER
        );

        return panel;
    }

    // ================= RISK PANEL =================
    private JPanel createRiskPanel() {

        JPanel panel =
                createPanel(
                        "RISK ANALYSIS"
                );

        panel.setLayout(
                new BorderLayout(
                        15,
                        15
                )
        );

        JPanel top =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                12,
                                12
                        )
                );

        top.setOpaque(false);

        riskScore =
                bigValue("0 / 100");

        riskLevel =
                bigValue("NOT ANALYZED");

        responseLevel =
                bigValue("NORMAL");

        top.add(
                statCard(
                        "CURRENT RISK",
                        riskScore
                )
        );

        top.add(
                statCard(
                        "RISK LEVEL",
                        riskLevel
                )
        );

        top.add(
                statCard(
                        "RESPONSE LEVEL",
                        responseLevel
                )
        );

        panel.add(
                top,
                BorderLayout.NORTH
        );

        riskMapPanel =
                new JPanel() {

                    @Override
                    protected void paintComponent(
                            Graphics g
                    ) {

                        super.paintComponent(g);

                        drawRiskMap(g);
                    }
                };

        riskMapPanel.setBackground(
                new Color(
                        8,
                        25,
                        41
                )
        );

        riskMapPanel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                30,
                                80,
                                105
                        )
                )
        );

        panel.add(
                riskMapPanel,
                BorderLayout.CENTER
        );

        return panel;
    }

    // ================= OUTPUT PANEL =================
    private JPanel createOutputPanel() {

        JPanel panel =
                createPanel(
                        "AI ASSESSMENT & EARLY WARNINGS"
                );

        outputArea =
                new JTextArea();

        outputArea.setEditable(false);

        outputArea.setLineWrap(true);

        outputArea.setWrapStyleWord(true);

        outputArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        outputArea.setForeground(WHITE);

        outputArea.setBackground(PANEL2);

        outputArea.setBorder(
                new EmptyBorder(
                        12,
                        12,
                        12,
                        12
                )
        );

        outputArea.setText(
                "Welcome to AI Disaster Assistant.\n\n"
                        + "Select a disaster type and severity.\n"
                        + "Then click RUN AI RISK ANALYSIS.\n\n"
                        + "The system will calculate:\n"
                        + "• Risk Score\n"
                        + "• Risk Level\n"
                        + "• High Risk Zones\n"
                        + "• Early Warnings\n"
                        + "• Safety Recommendations"
        );

        JScrollPane scroll =
                new JScrollPane(
                        outputArea
                );

        scroll.setBorder(null);

        panel.add(
                scroll,
                BorderLayout.CENTER
        );

        return panel;
    }

    // ================= EMERGENCY PANEL =================
    private JPanel createEmergencyPanel() {

        JPanel panel =
                createPanel(
                        "EMERGENCY RESPONSE"
                );

        panel.setPreferredSize(
                new Dimension(
                        230,
                        0
                )
        );

        JPanel inside =
                new JPanel();

        inside.setBackground(PANEL);

        inside.setLayout(
                new BoxLayout(
                        inside,
                        BoxLayout.Y_AXIS
                )
        );

        // SOS
        JButton sos =
                emergencyButton(
                        "🆘  SOS"
                );

        sos.addActionListener(
                e -> showMessage(
                        "SOS request simulated.\n\n"
                                + "In the real application, "
                                + "this can notify emergency "
                                + "contacts and responders."
                )
        );

        // Shelter
        JButton shelter =
                emergencyButton(
                        "🏠  FIND SHELTER"
                );

        shelter.addActionListener(
                e -> showMessage(
                        "Nearest Demo Shelter\n\n"
                                + "Community Safe Center\n"
                                + "Distance: 1.8 km\n\n"
                                + "In the final project, "
                                + "verified shelters can be "
                                + "loaded from a database/API."
                )
        );

        // Safe Route
        JButton route =
                emergencyButton(
                        "🛣  SAFE ROUTE"
                );

        route.addActionListener(
                e -> showMessage(
                        "Safe evacuation route generated.\n\n"
                                + "The production version will "
                                + "avoid high-risk zones while "
                                + "calculating the route."
                )
        );

        // Contacts
        JButton contacts =
                emergencyButton(
                        "📞  EMERGENCY CONTACTS"
                );

        contacts.addActionListener(
                e -> showMessage(
                        "Emergency Contacts - India\n\n"
                                + "112 - Emergency\n"
                                + "101 - Fire\n"
                                + "108 - Ambulance\n\n"
                                + "Local disaster-management "
                                + "contacts can also be added."
                )
        );

        JLabel note =
                label(
                        "<html><div style='width:190px'>"
                                + "<b>Prototype Note:</b><br><br>"
                                + "This application is a "
                                + "hackathon prototype and "
                                + "not an official emergency "
                                + "service."
                                + "</div></html>"
                );

        note.setForeground(MUTED);

        inside.add(sos);

        inside.add(
                Box.createVerticalStrut(10)
        );

        inside.add(shelter);

        inside.add(
                Box.createVerticalStrut(10)
        );

        inside.add(route);

        inside.add(
                Box.createVerticalStrut(10)
        );

        inside.add(contacts);

        inside.add(
                Box.createVerticalStrut(25)
        );

        inside.add(note);

        inside.add(
                Box.createVerticalGlue()
        );

        panel.add(
                inside,
                BorderLayout.CENTER
        );

        return panel;
    }

    // ================= AI RISK CALCULATION =================
    private void analyzeRisk() {

        String disaster =
                (String)
                        disasterBox.getSelectedItem();

        int severity =
                severitySlider.getValue();

        int baseRisk;

        switch (disaster) {

            case "Flood":
                baseRisk = 18;
                break;

            case "Earthquake":
                baseRisk = 28;
                break;

            case "Fire":
                baseRisk = 35;
                break;

            default:
                baseRisk = 24;
        }

        /*
         * DEMO AI RISK ENGINE
         *
         * Risk =
         * Disaster Base Risk
         * +
         * Severity Impact
         */

        int score =
                (int)
                        Math.round(
                                baseRisk
                                        + severity * 6.2
                        );

        score =
                Math.min(
                        100,
                        Math.max(
                                5,
                                score
                        )
                );

        String level;

        String response;

        Color levelColor;

        if (score >= 80) {

            level = "CRITICAL";

            response = "CRITICAL";

            levelColor = RED;

        } else if (score >= 60) {

            level = "HIGH";

            response = "HIGH";

            levelColor = RED;

        } else if (score >= 35) {

            level = "MEDIUM";

            response = "ELEVATED";

            levelColor = YELLOW;

        } else {

            level = "LOW";

            response = "NORMAL";

            levelColor = GREEN;
        }

        riskScore.setText(
                score + " / 100"
        );

        riskLevel.setText(level);

        riskLevel.setForeground(
                levelColor
        );

        responseLevel.setText(
                response
        );

        responseLevel.setForeground(
                levelColor
        );

        List<String> warnings =
                getWarnings(disaster);

        List<String> recommendations =
                getRecommendations(
                        disaster
                );

        StringBuilder result =
                new StringBuilder();

        result.append(
                "========================================\n"
        );

        result.append(
                "       AI RISK ASSESSMENT\n"
        );

        result.append(
                "========================================\n\n"
        );

        result.append(
                "Disaster Type : "
        );

        result.append(disaster);

        result.append("\n");

        result.append(
                "Severity      : "
        );

        result.append(severity);

        result.append("/10\n");

        result.append(
                "Risk Score    : "
        );

        result.append(score);

        result.append("/100\n");

        result.append(
                "Risk Level    : "
        );

        result.append(level);

        result.append("\n\n");

        // EARLY WARNINGS
        result.append(
                "⚠ EARLY WARNINGS\n"
        );

        result.append(
                "----------------------------------------\n"
        );

        for (
                String warning :
                warnings
        ) {

            result.append("• ");

            result.append(warning);

            result.append("\n");
        }

        result.append("\n");

        // SAFETY
        result.append(
                "🛡 SAFETY RECOMMENDATIONS\n"
        );

        result.append(
                "----------------------------------------\n"
        );

        for (
                String recommendation :
                recommendations
        ) {

            result.append("• ");

            result.append(
                    recommendation
            );

            result.append("\n");
        }

        result.append("\n");

        int zones =
                Math.max(
                        1,
                        score / 12
                );

        result.append(
                "📍 HIGH-RISK ZONES DETECTED: "
        );

        result.append(zones);

        result.append("\n\n");

        result.append(
                "🧠 AI SYSTEM INFORMATION\n"
        );

        result.append(
                "----------------------------------------\n"
        );

        result.append(
                "This MVP currently uses a "
                        + "rule-based risk engine.\n\n"
                        + "For the advanced version, "
                        + "we can connect:\n"
                        + "• Weather APIs\n"
                        + "• Earthquake APIs\n"
                        + "• Satellite/fire data\n"
                        + "• Government alerts\n"
                        + "• Historical disaster datasets\n"
                        + "• Machine Learning model\n"
        );

        outputArea.setText(
                result.toString()
        );

        riskMapPanel.repaint();

        JOptionPane.showMessageDialog(
                this,
                "Risk Analysis Completed!\n\n"
                        + "Risk Score: "
                        + score
                        + "/100\n"
                        + "Risk Level: "
                        + level,
                "AI Analysis",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ================= WARNINGS =================
    private List<String> getWarnings(
            String disaster
    ) {

        List<String> warnings =
                new ArrayList<>();

        switch (disaster) {

            case "Flood":

                warnings.add(
                        "HIGH WATER RISK: "
                                + "Low-lying areas may become unsafe."
                );

                warnings.add(
                        "ROAD HAZARD: "
                                + "Avoid flooded roads and underpasses."
                );

                warnings.add(
                        "SUPPLY ALERT: "
                                + "Keep water, medicines and food ready."
                );

                break;

            case "Earthquake":

                warnings.add(
                        "STRUCTURAL RISK: "
                                + "Damaged buildings may collapse."
                );

                warnings.add(
                        "AFTERSHOCK ALERT: "
                                + "Be prepared for additional shaking."
                );

                warnings.add(
                        "UTILITY HAZARD: "
                                + "Stay away from damaged electrical lines."
                );

                break;

            case "Fire":

                warnings.add(
                        "FIRE PERIMETER: "
                                + "Leave high-risk areas immediately."
                );

                warnings.add(
                        "SMOKE HAZARD: "
                                + "Smoke may travel beyond the visible fire."
                );

                warnings.add(
                        "EVACUATION: "
                                + "Keep emergency roads clear."
                );

                break;

            default:

                warnings.add(
                        "SEVERE WEATHER: "
                                + "Conditions may intensify quickly."
                );

                warnings.add(
                        "WIND/LIGHTNING: "
                                + "Avoid exposed locations."
                );

                warnings.add(
                        "TRAVEL RISK: "
                                + "Avoid unnecessary travel."
                );
        }

        return warnings;
    }

    // ================= RECOMMENDATIONS =================
    private List<String> getRecommendations(
            String disaster
    ) {

        List<String> list =
                new ArrayList<>();

        switch (disaster) {

            case "Flood":

                list.add(
                        "Move to higher ground."
                );

                list.add(
                        "Keep drinking water and medicines ready."
                );

                list.add(
                        "Never walk or drive through moving flood water."
                );

                break;

            case "Earthquake":

                list.add(
                        "Drop, Cover and Hold On during shaking."
                );

                list.add(
                        "Stay away from damaged buildings."
                );

                list.add(
                        "Use stairs instead of elevators."
                );

                break;

            case "Fire":

                list.add(
                        "Move away from smoke and fire."
                );

                list.add(
                        "Stay low when smoke is present."
                );

                list.add(
                        "Never re-enter a burning building."
                );

                break;

            default:

                list.add(
                        "Stay indoors away from windows."
                );

                list.add(
                        "Keep your phone charged."
                );

                list.add(
                        "Avoid trees and exposed electrical equipment."
                );
        }

        return list;
    }

    // ================= DRAW RISK MAP =================
    private void drawRiskMap(
            Graphics g
    ) {

        Graphics2D g2 =
                (Graphics2D)
                        g.create();

        int width =
                riskMapPanel.getWidth();

        int height =
                riskMapPanel.getHeight();

        // Grid
        g2.setColor(
                new Color(
                        10,
                        38,
                        58
                )
        );

        for (
                int x = 0;
                x < width;
                x += 40
        ) {

            g2.drawLine(
                    x,
                    0,
                    x,
                    height
            );
        }

        for (
                int y = 0;
                y < height;
                y += 40
        ) {

            g2.drawLine(
                    0,
                    y,
                    width,
                    y
            );
        }

        int score = 0;

        try {

            score =
                    Integer.parseInt(
                            riskScore
                                    .getText()
                                    .split("/")[0]
                                    .trim()
                    );

        } catch (Exception ignored) {
        }

        int centerX =
                width / 2;

        int centerY =
                height / 2;

        // LOW RISK ZONE
        drawZone(
                g2,
                centerX,
                centerY,
                170,
                GREEN
        );

        // MEDIUM RISK ZONE
        drawZone(
                g2,
                centerX,
                centerY,
                120,
                YELLOW
        );

        // HIGH RISK ZONE
        drawZone(
                g2,
                centerX,
                centerY,
                70,
                RED
        );

        // Title
        g2.setColor(WHITE);

        g2.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        g2.drawString(
                "LIVE RISK MAP - DEMO",
                20,
                28
        );

        // Legend
        g2.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        g2.setColor(MUTED);

        g2.drawString(
                "Green = Low   Yellow = Medium   Red = High",
                20,
                48
        );

        // Center
        g2.setColor(WHITE);

        g2.fillOval(
                centerX - 7,
                centerY - 7,
                14,
                14
        );

        g2.setColor(CYAN);

        g2.drawString(
                "Incident Center",
                centerX + 12,
                centerY + 4
        );

        // Risk
        if (score > 0) {

            if (score >= 60) {

                g2.setColor(RED);

            } else if (score >= 35) {

                g2.setColor(YELLOW);

            } else {

                g2.setColor(GREEN);
            }

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            18
                    )
            );

            g2.drawString(
                    "Risk: "
                            + score
                            + "/100",
                    20,
                    height - 25
            );
        }

        g2.dispose();
    }

    // ================= RISK ZONE =================
    private void drawZone(
            Graphics2D g2,
            int x,
            int y,
            int radius,
            Color color
    ) {

        Color transparent =
                new Color(
                        color.getRed(),
                        color.getGreen(),
                        color.getBlue(),
                        25
                );

        g2.setColor(
                transparent
        );

        g2.fillOval(
                x - radius,
                y - radius,
                radius * 2,
                radius * 2
        );

        Color border =
                new Color(
                        color.getRed(),
                        color.getGreen(),
                        color.getBlue(),
                        150
                );

        g2.setColor(border);

        g2.drawOval(
                x - radius,
                y - radius,
                radius * 2,
                radius * 2
        );
    }

    // ================= PANEL =================
    private JPanel createPanel(
            String title
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panel.setBackground(PANEL);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        35,
                                        72,
                                        96
                                )
                        ),
                        new EmptyBorder(
                                14,
                                14,
                                14,
                                14
                        )
                )
        );

        JLabel heading =
                new JLabel(title);

        heading.setForeground(CYAN);

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        panel.add(
                heading,
                BorderLayout.NORTH
        );

        return panel;
    }

    // ================= STAT CARD =================
    private JPanel statCard(
            String title,
            JLabel value
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(PANEL2);

        card.setBorder(
                new EmptyBorder(
                        10,
                        12,
                        10,
                        12
                )
        );

        JLabel titleLabel =
                label(title);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                value,
                BorderLayout.CENTER
        );

        return card;
    }

    // ================= BIG VALUE =================
    private JLabel bigValue(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setForeground(WHITE);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        return label;
    }

    // ================= LABEL =================
    private JLabel label(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setForeground(WHITE);

        label.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        return label;
    }

    // ================= NORMAL BUTTON =================
    private JButton button(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setForeground(
                Color.BLACK
        );

        button.setBackground(
                CYAN
        );

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        button.setBorder(
                new EmptyBorder(
                        11,
                        10,
                        11,
                        10
                )
        );

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        return button;
    }

    // ================= EMERGENCY BUTTON =================
    private JButton emergencyButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        button.setForeground(WHITE);

        button.setBackground(PANEL2);

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                40,
                                75,
                                100
                        )
                )
        );

        return button;
    }

    // ================= COMBOBOX =================
    private void styleCombo(
            JComboBox<String> box
    ) {

        box.setBackground(PANEL2);

        box.setForeground(WHITE);

        box.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        box.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );
    }

    // ================= MESSAGE =================
    private void showMessage(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "AI Disaster Assistant",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ================= RESET =================
    private void resetDashboard() {

        disasterBox.setSelectedIndex(0);

        severitySlider.setValue(6);

        riskScore.setText(
                "0 / 100"
        );

        riskLevel.setText(
                "NOT ANALYZED"
        );

        riskLevel.setForeground(
                WHITE
        );

        responseLevel.setText(
                "NORMAL"
        );

        responseLevel.setForeground(
                WHITE
        );

        outputArea.setText(
                "Dashboard reset.\n\n"
                        + "Select a disaster type "
                        + "and severity.\n\n"
                        + "Then click "
                        + "RUN AI RISK ANALYSIS."
        );

        riskMapPanel.repaint();
    }

    // ================= MAIN METHOD =================
    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
                                UIManager
                                        .getSystemLookAndFeelClassName()
                        );

                    } catch (Exception ignored) {
                    }

                    new AIDisasterAssistant()
                            .setVisible(true);
                }
        );
    }
}