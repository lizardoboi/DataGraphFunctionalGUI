package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataGraphInterface extends JFrame {

    private DataGraphPanel graphPanel;
    private Timer dataTimer;
    private int dataCounter;
    long previousTimestamp;
    long timeDiff;
    public DataGraphInterface() {
        setTitle("Data Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        //setResizable(false);

        graphPanel = new DataGraphPanel(100); // Число показываемых точек

        add(graphPanel, BorderLayout.CENTER);

        // Симуляция с обновлением каждые 100 миллисекунд
        dataCounter = 0;
        previousTimestamp = System.currentTimeMillis();
        dataTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double randomValue = Math.random() * 100; // Рандомные точки
                long timestamp = System.currentTimeMillis();// Время на данный момент
                timeDiff = timestamp - previousTimestamp;
                timestamp=timeDiff;
                graphPanel.addDataPoint(randomValue, timestamp);

                dataCounter++;
                if (dataCounter >= 40) { //После 40 апдейтов останавливаемся
                    dataTimer.stop();
                }
            }
        });
        JButton showDataButton = new JButton("Show Last N Data");
        showDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLastNDataPoints(5); // Вызов метода для отображения информации о последних 5 точках
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showDataButton);
        add(buttonPanel, BorderLayout.NORTH);
    }

    public void displayLastNDataPoints(int n) {
        String dataInfo = graphPanel.getLastNDataInfo(n);

        // Отображение информации о последних N записанных точках в диалоговом окне
        JOptionPane.showMessageDialog(null, dataInfo, "Last N Data Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void start() {
        dataTimer.start();
        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DataGraphInterface dataGraphInterface = new DataGraphInterface();
                dataGraphInterface.start();
            }
        });
    }
}