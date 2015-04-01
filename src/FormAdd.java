import WorkersPack.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by OrangeUser on 3/30/2015.
 */
public class FormAdd extends JFrame {

    public ArrayList<Class<?>> classesList;
    public WorkersList workersList;
    Class<?>[] typesOfArgs;
    Panel panel;
    ArrayList<TextArea> areasList;

    public FormAdd(WorkersList workersList){
        initGUI();
        setVisible(true);
        this.workersList = workersList;
    }

    public void initGUI(){
        //setLocationRelativeTo(null);
        final JComboBox workersBox = new JComboBox(getArrayProfessions());
        Button addButton = new Button("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewWorker(workersBox);
            }
        });

        add(workersBox);
        add(addButton);
        panel = new Panel();
        add(panel);
        workersBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseProfession(e);
            }
        });

        setLayout(new FlowLayout());
        pack();

    }

    public void chooseProfession(ActionEvent e){
        remove(panel);
        panel = new Panel();
        panel.setLayout(new FlowLayout());
        JComboBox box = (JComboBox)e.getSource();
        typesOfArgs = classesList.get(box.getSelectedIndex()).getDeclaredConstructors()[0].getParameterTypes();

        Field[] nameFields = getFieldsName(classesList.get(box.getSelectedIndex()));

        areasList = new ArrayList<TextArea>();
        for (int i = 0 ; i < typesOfArgs.length; i++) {
            TextArea textArea = new TextArea(typesOfArgs[i].getSimpleName());
            textArea.setPreferredSize(new Dimension(200, 100));
            panel.add(new Label(nameFields[i].getName()));
            panel.add(textArea);
            areasList.add(textArea);
        }
        add(panel);
        revalidate();
        repaint();
        setLayout(new FlowLayout());
        pack();
    }

    public Field[] getFieldsName(Class<?> choosenClass){
        Field[] fields = new Field[choosenClass.getFields().length];// + choosenClass.getDeclaredFields().length];
        Field[] tempFields = choosenClass.getFields();
        Field[] tempDeclaredFields = choosenClass.getDeclaredFields();
        for (int i = 0 ; i < tempFields.length ; i++) {
            fields[i] = tempFields[i];
        }
//        for (int i = tempFields.length, j = 0; i < fields.length; i++, j++){
//            fields[i] = tempDeclaredFields[j];
//        }
        return fields;
    }

    public void addNewWorker(JComboBox box){
        try {
            Object[] args = new Object[typesOfArgs.length];
            for (int i = 0 ; i < areasList.size(); i++){
                String data = areasList.get(i).getText();
                if (typesOfArgs[i].getSimpleName().contains("int") || typesOfArgs[i].getSimpleName().contains("Integer"))
                    args[i] = Integer.parseInt(data);
                else if (typesOfArgs[i].getSimpleName().contains("String"))
                    args[i] = data;
            }
            workersList.addWorker((Worker) classesList.get(box.getSelectedIndex()).getDeclaredConstructors()[0].newInstance(args));
            main.comboBox.addItem(workersList.getWorkersName()[workersList.getWorkers().size() - 1]);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String[] getArrayProfessions(){
        classesList = ClassFinder.find("WorkersPack.Professions");
        String[] professions = new String[classesList.size()];
        for (int i = 0 ; i < classesList.size(); i++) {
            professions[i] = classesList.get(i).getSimpleName();
        }
        return professions;
    }

}
