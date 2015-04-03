import WorkersPack.Worker;
import com.thoughtworks.xstream.XStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class main extends JFrame {

    public static WorkersList workersList;
    public static JComboBox comboBox;
    ButtonGroup buttonGroup;
    File file;

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
                //System.out.println(item + " - " + index);
                //System.out.println(workersList.getWorker(index).toString());
            }
        });
        add(comboBox);

        Button addButton = new Button("Add Worker");
        Button editButton = new Button("Edit");
        Button removeButton = new Button("Remove");

        Button chooseFileButt = new Button("Open File");

        buttonGroup = new ButtonGroup();

        final JRadioButton textFileButt = new JRadioButton("Text File");
        final JRadioButton xmlFileButt = new JRadioButton("XML File");
        final JRadioButton binFileButt = new JRadioButton("BIN File");
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

        chooseFileButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });

        serializeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (xmlFileButt.isSelected())
                        serializeObjXMLFile();
                    else if (binFileButt.isSelected())
                        serializeObjBINFile();
                    else if (textFileButt.isSelected())
                        serializeObjTextFile();
                } catch (IOException e1){
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
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
                    else if (binFileButt.isSelected())
                        deserializeObjBINFile();
                    else if (textFileButt.isSelected())
                        deserializeObjTextFile();
                } catch (IOException e1){
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
        });

        add(addButton);
        add(editButton);
        add(removeButton);

        add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_START);

        add(chooseFileButt);

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

    public void selectFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int ret = fileChooser.showDialog(null, "Choose file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
    }

    public void serializeObjTextFile() throws IOException, ClassNotFoundException, IllegalAccessException {
        PrintWriter writer = new PrintWriter(file);
        Worker selectedWorker = workersList.getWorker(comboBox.getSelectedIndex());
        writer.println(selectedWorker.getClass().getName());
        for (Field field : workersList.getWorker(comboBox.getSelectedIndex()).getClass().getFields()){
            writer.println(field.get(selectedWorker).toString());

        }
        writer.close();
    }

    public void deserializeObjTextFile() throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String className = reader.readLine();
        Worker worker = (Worker) Class.forName(className).getDeclaredConstructors()[1].newInstance();
        for (Field field : Class.forName(className).getFields()){
            if (field.getType().getName().contains("String"))
                field.set(worker, reader.readLine());
            else if (field.getType().getName().contains("Integer") || field.getType().getName().contains("int")){
                field.setInt(worker, Integer.parseInt(reader.readLine()));
            }
            else if (field.getType().getName().contains("oolean"))
                field.setBoolean(worker, Boolean.valueOf(reader.readLine()));
        }
        workersList.addWorker(worker);
        comboBox.addItem(workersList.getWorker(workersList.getWorkers().size()-1).getName());
    }

    public void serializeObjBINFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(workersList.getWorker(comboBox.getSelectedIndex()));
        objectOutputStream.flush();
        fileOutputStream.close();
        objectOutputStream.close();
    }

    public void deserializeObjBINFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        workersList.addWorker((Worker) objectInputStream.readObject());
        comboBox.addItem(workersList.getWorker(workersList.getWorkers().size()-1).getName());
        fileInputStream.close();
        objectInputStream.close();
    }

    public void serializeObjXMLFile() throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        XStream xStream = new XStream();
        xStream.toXML(workersList.getWorker(comboBox.getSelectedIndex()), fileOutputStream);
        fileOutputStream.close();

    }

    public void deserializeObjXMLFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        XStream xStream = new XStream();
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
