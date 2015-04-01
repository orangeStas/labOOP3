import WorkersPack.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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
        Button removeButton = new Button("Remove");
        Button serializeButton = new Button("Serialize object");
        final Button deserializeButton = new Button("Deserialize object");



        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormAdd(workersList);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormEdit(workersList, comboBox.getSelectedIndex());
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeWorker();
            }
        });

        serializeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    serializeObj();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        deserializeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deserializeObj();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        add(addButton);
        add(editButton);
        add(removeButton);
        add(serializeButton);
        add(deserializeButton);
        setLayout(new FlowLayout());
        pack();
    }

    public void removeWorker(){
        int index = comboBox.getSelectedIndex();
        comboBox.removeItemAt(index);
        workersList.removeWorker(index);
    }

    public void serializeObj() throws IOException {
        FileOutputStream fos = new FileOutputStream("temp.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(workersList.getWorker(comboBox.getSelectedIndex()));
        oos.flush();
        oos.close();
    }

    public void deserializeObj() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("temp.out");
        ObjectInputStream oin = new ObjectInputStream(fis);
        workersList.addWorker((Worker) oin.readObject());
        comboBox.addItem(workersList.getWorker(workersList.getWorkers().size()-1).getName());
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
