import WorkersPack.Worker;
import com.thoughtworks.xstream.XStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class main extends JFrame {

    public static WorkersList workersList;
    public static JComboBox comboBox;
    ButtonGroup buttonGroup;

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

        buttonGroup = new ButtonGroup();

        final JRadioButton textFileButt = new JRadioButton("Text File");
        final JRadioButton xmlFileButt = new JRadioButton("XML File");
        JRadioButton binFileButt = new JRadioButton("BIN File");
        buttonGroup.add(textFileButt);
        buttonGroup.add(xmlFileButt);
        buttonGroup.add(binFileButt);

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
                    if (xmlFileButt.isSelected())
                        serializeObjXMLFile();
                    else if (textFileButt.isSelected())
                        serializeObjTextFile();
                } catch (IOException e1){
                    e1.printStackTrace();
                }

            }
        });

        deserializeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (xmlFileButt.isSelected())
                        deserializeObjXMLFile();
                    else if (textFileButt.isSelected())
                        deserializeObjTextFile();
                } catch (IOException e1){
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        add(addButton);
        add(editButton);
        add(removeButton);

        add(textFileButt);
        add(xmlFileButt);
        add(binFileButt);

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

    public void serializeObjTextFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("object.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(workersList.getWorker(comboBox.getSelectedIndex()));
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public void deserializeObjTextFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("object.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        workersList.addWorker((Worker) objectInputStream.readObject());
        comboBox.addItem(workersList.getWorker(workersList.getWorkers().size()-1).getName());
        fileInputStream.close();
        objectInputStream.close();
    }

    public void serializeObjXMLFile() throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream("object.xml");
        XStream xStream = new XStream();
        //xStream.alias("classserialize", workersList.getWorker(comboBox.getSelectedIndex()).getClass());
        xStream.toXML(workersList.getWorker(comboBox.getSelectedIndex()), fileOutputStream);
        fileOutputStream.close();

    }

    public void deserializeObjXMLFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("object.xml");
        XStream xStream = new XStream();
        //xStream.alias("classserialize", Worker.class);
        workersList.addWorker((Worker) xStream.fromXML(fileInputStream));
        comboBox.addItem(workersList.getWorker(workersList.getWorkers().size()-1).getName());
        fileInputStream.close();
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
