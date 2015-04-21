import WorkersPack.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by OrangeUser on 3/30/2015.
 */
public class FormAdd extends JFrame {
    public ArrayList<Class<?>> classesList;
    public WorkersList workersList;
    Class<?>[] typesOfArgs;
    public Method[] setMethods;
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
        setMethods = getSetMethods(classesList.get(box.getSelectedIndex()));

        areasList = new ArrayList<TextArea>();
        for (int i = 0 ; i < setMethods.length; i++) {

            TextArea textArea = new TextArea(setMethods[i].getGenericParameterTypes()[0].getTypeName());
            textArea.setPreferredSize(new Dimension(200, 100));
            panel.add(new Label(setMethods[i].getName().replaceAll("set", "")));
            panel.add(textArea);
            areasList.add(textArea);
        }
        add(panel);
        revalidate();
        repaint();
        setLayout(new FlowLayout());
        pack();
    }

    public Method[] getSetMethods(Class<?> choosenClass) {
        ArrayList<Method> methodArrayList = new ArrayList<Method>();
        for (Method method : choosenClass.getMethods()) {
            if (method.getName().contains("set")) {
                methodArrayList.add(method);
            }
        }
        /*Method[] methods = new Method[methodArrayList.size()];
        for (int i = 0 ; i < methods.length; i++){
            methods[i] = methodArrayList.get(i);
        }
        */
        return methodArrayList.toArray(new Method[0]);
    }

    public void addNewWorker(JComboBox box){
        try {
            Class<?> aClass = classesList.get(box.getSelectedIndex());
            Worker worker = (Worker) aClass.getConstructor().newInstance();
            for (int i = 0 ; i < areasList.size(); i++){
                String data = areasList.get(i).getText();
                if (setMethods[i].getGenericParameterTypes()[0].getTypeName().contains("int") || setMethods[i].getReturnType().getSimpleName().contains("Integer"))
                    setMethods[i].invoke(worker, Integer.parseInt(data));
                else if (setMethods[i].getGenericParameterTypes()[0].getTypeName().contains("String"))
                    setMethods[i].invoke(worker, data);
                else if (setMethods[i].getGenericParameterTypes()[0].getTypeName().contains("oolean"))
                    setMethods[i].invoke(worker, Boolean.valueOf(data));
                else
                    continue;
            }
            workersList.addWorker(worker);
            main.comboBox.addItem(workersList.getWorkersName()[workersList.getWorkers().size() - 1]);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
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
