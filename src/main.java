import WorkersPack.Worker;
import com.thoughtworks.xstream.XStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class main extends JFrame {

    public static WorkersList workersList;
    public static JComboBox comboBox;
    ButtonGroup buttonGroup;
    File file;
    HashMap<String, Object> tableObjects = new HashMap<String, Object>();

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
        //setLayout(new GridLayout(2, 4));
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
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
            for (Worker worker : workersList.getWorkers()) {
                writer.println(worker.getClass().getName());
                for (Method method : getGetMethods(worker.getClass())) {
                    if (!method.getReturnType().getTypeName().contains("String") || !method.getReturnType().getTypeName().contains("Integer")
                        || !method.getReturnType().getTypeName().contains("int") || !method.getReturnType().getTypeName().contains("oolean"))
                    {
                        tableObjects.put(method.invoke(worker).toString(), method.invoke(worker));
                        writer.println(method.invoke(worker).toString());
                    }
                    else
                        writer.println(method.invoke(worker).toString());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            assert writer != null;
            writer.close();
        }
    }

    public Method[] getGetMethods(Class<?> choosenClass) {
        ArrayList<Method> methodArrayList = new ArrayList<Method>();
        for (Method method : choosenClass.getMethods()) {
            if (method.getName().contains("get")) {
                methodArrayList.add(method);
            }
        }
        Method[] methods = new Method[methodArrayList.size() - 1];
        for (int i = 0 ; i < methods.length; i++){
            methods[i] = methodArrayList.get(i);
        }
        return methods;
    }

    public void deserializeObjTextFile() throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String className;
            while ((className = reader.readLine()) != null) {
                Worker worker = (Worker) Class.forName(className).getConstructor().newInstance();
                for (Method method : getSetMethods(worker.getClass())) {
                    if (method.getGenericParameterTypes()[0].getTypeName().contains("int") || method.getReturnType().getSimpleName().contains("Integer"))
                        method.invoke(worker, Integer.parseInt(reader.readLine()));
                    else if (method.getGenericParameterTypes()[0].getTypeName().contains("String"))
                        method.invoke(worker, reader.readLine());
                    else if (method.getGenericParameterTypes()[0].getTypeName().contains("oolean"))
                        method.invoke(worker, Boolean.valueOf(reader.readLine()));
                    else
                        method.invoke(worker, tableObjects.get(reader.readLine()));
                }
                workersList.addWorker(worker);
                comboBox.addItem(worker.getName());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            assert reader != null;
            reader.close();
        }
    }

    public Method[] getSetMethods(Class<?> choosenClass) {
        ArrayList<Method> methodArrayList = new ArrayList<Method>();
        for (Method method : choosenClass.getMethods()) {
            if (method.getName().contains("set")) {
                methodArrayList.add(method);
            }
        }
        Method[] methods = new Method[methodArrayList.size()];
        for (int i = 0 ; i < methods.length; i++){
            methods[i] = methodArrayList.get(i);
        }
        return methods;
    }

    public void serializeObjBINFile() throws IOException {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (Worker worker : workersList.getWorkers()) {
                objectOutputStream.writeObject(worker);
            }
            objectOutputStream.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            assert fileOutputStream != null;
            fileOutputStream.close();
            assert objectOutputStream != null;
            objectOutputStream.close();
        }
    }

    public void deserializeObjBINFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        Worker worker;
        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            while ((worker = (Worker) objectInputStream.readObject()) != null) {
                workersList.addWorker(worker);
                comboBox.addItem(workersList.getWorker(workersList.getWorkers().size() - 1).getName());
            }
        } catch (EOFException e){

        }
        finally {
            assert fileInputStream != null;
            fileInputStream.close();
            assert objectInputStream != null;
            objectInputStream.close();
        }

    }

    public void serializeObjXMLFile() throws IOException {
        FileOutputStream fileOutputStream = null;
        XStream xStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            xStream = new XStream();
            xStream.toXML(workersList, fileOutputStream);
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            assert fileOutputStream != null;
            fileOutputStream.close();
        }

    }

    public void deserializeObjXMLFile() throws IOException {
        FileInputStream fileInputStream = null;
        XStream xStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            xStream = new XStream();
            WorkersList tempList = (WorkersList) xStream.fromXML(fileInputStream);
            for (Worker worker : tempList.getWorkers()){
                workersList.addWorker(worker);
                comboBox.addItem(worker.getName());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            assert fileInputStream != null;
            fileInputStream.close();
        }

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
