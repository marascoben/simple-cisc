package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import components.Computer;

public class FrontPanel extends JFrame {

    // General Purpose Register Panels (16-bit)
    private IndicatorPanel r0Panel, r1Panel, r2Panel, r3Panel;

    // Index Register Panels (16-bit)
    private IndicatorPanel x1Panel, x2Panel, x3Panel;

    // Memory Buffer Register Panel (16-bit)
    private IndicatorPanel mbrPanel;

    // Memory Address Register Panel (12-bit)
    private IndicatorPanel marPanel;

    // Program counter panel (12-bit)
    private IndicatorPanel pcPanel;

    // Instruction register panel (16-bit)
    private IndicatorPanel irPanel;

    // Memory fault register panel (4-bit)
    private IndicatorPanel mfrPanel;

    // Condition code panel (4-bit)
    private IndicatorPanel ccPanel;

    // Computer object
    private Computer computer;

    public FrontPanel(Computer computer) {
        super("Minicomputer");

        this.computer = computer;

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));

        // General Purpose Register Panels (16-bit)
        r0Panel = new IndicatorPanel("R0", true);
        r1Panel = new IndicatorPanel("R1", true);
        r2Panel = new IndicatorPanel("R2", true);
        r3Panel = new IndicatorPanel("R3", true);

        // Index Register Panels (16-bit)
        x1Panel = new IndicatorPanel("X1", true);
        x2Panel = new IndicatorPanel("X2", true);
        x3Panel = new IndicatorPanel("X3", true);

        // Memory Buffer Register Panel (16-bit)
        mbrPanel = new IndicatorPanel("Memory Buffer", true);

        // Memory Address Register Panel (12-bit)
        marPanel = new IndicatorPanel("Memory Address", true, 12);

        // Program counter panel (12-bit)
        pcPanel = new IndicatorPanel("Program Counter", true, 12);

        // Instruction register panel (16-bit)
        irPanel = new IndicatorPanel("Instruction", false);

        // Memory fault register panel (4-bit)
        mfrPanel = new IndicatorPanel("Memory Fault", false, 4);

        // Condition code panel (4-bit)
        ccPanel = new IndicatorPanel("Condition Code", false, 4);

        // Simple input panel for the user to enter a binary word and a run button to
        // execute the word
        JPanel bottom = new JPanel();
        JTextField input = new JTextField(16);
        JButton run = new JButton("Run");
        run.addActionListener(e -> {
            try {
                char word = (char) Integer.parseInt(input.getText(), 2);
                computer.runInstruction(word);
                updateIndicators();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
        });

        JButton init = new JButton("Init");
        init.addActionListener(e -> {
            computer.reset();
            updateIndicators();
        });

        bottom.add(input);
        bottom.add(run);
        bottom.add(init);
        add(bottom, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);

        panel.add(r0Panel);
        panel.add(r1Panel);
        panel.add(r2Panel);
        panel.add(r3Panel);
        panel.add(x1Panel);
        panel.add(x2Panel);
        panel.add(x3Panel);
        panel.add(mbrPanel);
        panel.add(marPanel);
        panel.add(pcPanel);
        panel.add(irPanel);
        panel.add(mfrPanel);
        panel.add(ccPanel);

        setSize(850, 500);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void updateIndicators() {
        r0Panel.setValue(computer.processor.R0);
        r1Panel.setValue(computer.processor.R1);
        r2Panel.setValue(computer.processor.R2);
        r3Panel.setValue(computer.processor.R3);
        x1Panel.setValue(computer.processor.X1);
        x2Panel.setValue(computer.processor.X2);
        x3Panel.setValue(computer.processor.X3);
        mbrPanel.setValue(computer.processor.MBR);
        marPanel.setValue(computer.processor.MAR);
        pcPanel.setValue(computer.processor.PC);
        irPanel.setValue(computer.processor.getIR());
        mfrPanel.setValue(computer.processor.getMFR());
        ccPanel.setValue(computer.processor.getCC());
    }
}
