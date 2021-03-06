import WorkersPack.Worker;
import com.thoughtworks.xstream.XStream;

import javax.swing.*;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class main extends JFrame {

    public WorkersList workersList;
    public static JComboBox comboBox;
    public JComboBox stylesBox;
    ButtonGroup buttonGroup;
    File file;
    HashMap<String, Object> tableObjects = new HashMap<String, Object>();
    public File[] filesArray;
    public static JRadioButton formatXMLButt;
    JRadioButton archiveXMLButt;
    public static HashMap<String, String> tableStyles;


    public main() throws IOException {
        workersList = WorkersList.getInstance();
        iniGUI();
    }

    public void iniGUI() throws IOException {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        comboBox = new JComboBox(workersList.getWorkersName());

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
            }
        });
        add(comboBox);

        Button addButton = new Button("Add Worker");
        Button editButton = new Button("Edit");
        Button removeButton = new Button("Remove");

        Button chooseFileButt = new Button("Open File");

        formatXMLButt = new JRadioButton("Formatting XML");
        tableStyles = getTableStyles();
        filesArray = getFilesArray();
        stylesBox = new JComboBox(getFilesName());

        archiveXMLButt = new JRadioButton("Archiving XML");


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
                } catch (IOException | ClassNotFoundException | IllegalAccessException | TransformerException e1) {
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
                } catch (IOException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | TransformerException e1){
                    e1.printStackTrace();
                }
            }
        });

        add(addButton);
        add(editButton);
        add(removeButton);

        add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_START);

        add(chooseFileButt);

        add(formatXMLButt);
        add(stylesBox);

        add(archiveXMLButt);

        add(textFileButt);
        add(xmlFileButt);
        add(binFileButt);

        add(serializeButton);
        add(deserializeButton);

        setLayout(new FlowLayout());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = 1000;
        int sizeHeight = 100;
        int locationX = (screenSize.width - sizeWidth) / 2;
        int locationY = (screenSize.height - sizeHeight) / 2;
        setBounds(locationX, locationY, sizeWidth, sizeHeight);


        pack();
    }

    public HashMap<String, String> getTableStyles() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "\\Table styles.txt")));
        String line = "";
        while ((line = reader.readLine()) != null){
            try {
                map.put(line.split("\\-")[0], line.split("\\-")[1]);
            } catch (Exception e){
                System.out.println("Таблица не получена");
                return null;
            }

        }
        reader.close();
        return map;
    }

    public File[] getFilesArray(){
        File folder = new File(System.getProperty("user.dir") + "\\FormattingStyles");
        File[] files = folder.listFiles();
        return files;
    }

    public String[] getFilesName(){
        String[] fileNamesArray;
        if (tableStyles == null)
            return new String[0];
        fileNamesArray = new String[tableStyles.size()];
        int count = 0;
        for (int i = 0 ; i < filesArray.length; i++){
            for (Map.Entry entry : tableStyles.entrySet()){
                if (entry.getKey().toString().equals(filesArray[i].getName())) {
                    fileNamesArray[count] = filesArray[i].getName();
                    count++;
                }
            }
        }
        return fileNamesArray;
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
                if (!method.getName().startsWith("getClass"))
                    methodArrayList.add(method);
            }
        }

        return sortMethods(methodArrayList.toArray(new Method[0]));
    }

    public Method[] sortMethods(Method[] methods) {
        for (int i = 0 ; i < methods.length; i++) {
            for (int j = 0; j < methods.length - 1; j++) {
                if (methods[j].getName().compareTo(methods[j+1].getName()) < 0)
                {
                    Method tempMethod = methods[j];
                    methods[j] = methods[j+1];
                    methods[j+1] = tempMethod;
                }
            }
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
            if (method.getName().startsWith("set") && method.getParameters().length == 1) {
                methodArrayList.add(method);
            }
        }

        return sortMethods(methodArrayList.toArray(new Method[0]));
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
            if (fileInputStream != null)
                fileInputStream.close();
            if (objectInputStream != null)
                objectInputStream.close();
        }

    }

    public void serializeObjXMLFile() throws IOException, TransformerException {
        FileOutputStream fileOutputStream = null;
        XStream xStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            xStream = new XStream();
            xStream.toXML(workersList, fileOutputStream);

            if (fileOutputStream != null)
                fileOutputStream.close();
            if (formatXMLButt.isSelected())
                try {
                    //formatXML(file);
                    XSLFormatter formatter = new XSLFormatter(file, stylesBox.getItemAt(stylesBox.getSelectedIndex()).toString(), filesArray);
                    formatter.formatXML();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            if (archiveXMLButt.isSelected()){
                Archiver archiver = new Archiver(file);
                IFormatter archiverAdapter = new ArchiverAdapter(archiver);
                archiverAdapter.formatXML();
                file.delete();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {

        }

    }

    public void deserializeObjXMLFile() throws IOException, TransformerException {
        FileInputStream fileInputStream = null;
        XStream xStream = null;
        WorkersList tempList = null;
        xStream = new XStream();
        if (formatXMLButt.isSelected() && !file.getName().endsWith(".zip")){
            XSLFormatter formatter = new XSLFormatter(file, filesArray);
            tempList = (WorkersList) xStream.fromXML(formatter.deformatXML());
        }
        else if (file.getName().endsWith(".zip")) {
            Archiver archiver = new Archiver(file);
            IFormatter archiverAdapter = new ArchiverAdapter(archiver);
            if (!formatXMLButt.isSelected())
                tempList = (WorkersList) xStream.fromXML(archiverAdapter.deformatXML());
            else {
                File newFile = new File(System.getProperty("user.dir") + "\\" + file.getName().replaceAll("zip", "xml"));

                String xmlString = archiverAdapter.deformatXML();

                PrintWriter writer = new PrintWriter(newFile, "UTF-16");
                //PrintWriter writer = new PrintWriter(newFile);
                writer.write(xmlString);
                writer.close();

                XSLFormatter formatter = new XSLFormatter(newFile, filesArray);
                tempList = (WorkersList) xStream.fromXML(formatter.deformatXML());
                newFile.delete();
            }

        }
        else {
            try {
                fileInputStream = new FileInputStream(file);
                tempList = (WorkersList) xStream.fromXML(fileInputStream);
            } catch (Exception e){
                e.printStackTrace();
            }
            finally {
                if (fileInputStream != null)
                    fileInputStream.close();
            }
        }
        if  (tempList != null) {
            for (Worker worker : tempList.getWorkers()) {
                workersList.addWorker(worker);
                comboBox.addItem(worker.getName());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    new main().setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
