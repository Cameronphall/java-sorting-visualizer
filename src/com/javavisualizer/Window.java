package com.javavisualizer;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Window {
    private JFrame window;

    public Window(){
        initialize();
    }

    public void initialize(){ 
        window = new JFrame();
        this.window.setTitle("Sorting Visiualizer");
        this.window.setLayout(new BorderLayout(10, 5));
        this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.window.setSize(800, 500);
        this.window.setLocationRelativeTo(null);
        

        SortingPanel panel = new SortingPanel();
        
        JButton regenerateButton = new JButton("Regenerate");

        regenerateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                panel.generateNewBars();
                panel.repaint();
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(regenerateButton);

        window.add(topPanel, BorderLayout.NORTH);


        


        


        window.add(panel, BorderLayout.CENTER);
        this.window.setVisible(true);

        String[] algorithms = {"Bubble Sort", "Insertion Sort", "Selection Sort", "Quick Sort", "Stalin Sort"};
        JComboBox<String> algorithmDropdown = new JComboBox<>(algorithms);

        algorithmDropdown.setSelectedIndex(0);

        topPanel.add(algorithmDropdown);

        JButton sortButton = new JButton("Sort");

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String selectedAlgorithm = (String) algorithmDropdown.getSelectedItem();

                switch (selectedAlgorithm) {
                    case "Bubble Sort":
                        panel.startBubbleSort();
                        break;
                    case "Insertion Sort":
                        panel.startInsertionSort();
                        break;
                    
                    case "Selection Sort":
                        panel.startSelectionSort();
                        break;
                    case "Quick Sort":
                        panel.startSelectionSort();
                        break;
                    case "Stalin Sort":
                        panel.startStalinSort();
                        break;
                    default:
                        break;
                }
            }
        });
        topPanel.add(sortButton);

        
    }
}
