import WorkersPack.Professions.Programmer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main extends JFrame {

    public static WorkersList workersList;
    public static JComboBox comboBox;

    public main(){
        workersList = new WorkersList();
        iniGUI();
    }

    public void iniGUI(){
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        workersList.insertWorker(new Programmer("Programmer1", 21, 2, "Java"));
        workersList.insertWorker(new Programmer("Programmer2", 26, 3, "C#"));

        comboBox = new JComboBox(workersList.getWorkersName());

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                String item = (String)box.getSelectedItem();
                int index = box.getSelectedIndex();
                System.out.println(item + " - " + index);
            }
        });
        add(comboBox);

        Button addButton = new Button("Add Worker");
        Button editButton = new Button("Edit");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormAdd(workersList);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        add(addButton);
        add(editButton);

        setLayout(new FlowLayout());
        pack();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new main().setVisible(true);
            }
        });
    }
}
