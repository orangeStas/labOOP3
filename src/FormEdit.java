import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by OrangeUser on 4/1/2015.
 */
public class FormEdit extends JFrame {
    private WorkersList workersList;
    private int indexWorker;
    ArrayList<TextArea> areasList;
    //Class<?>[] typesOfArgs;
    Method[] arrayOfGetMethods;

    public FormEdit(WorkersList workersList, int indexWorker){
        this.workersList = workersList;
        this.indexWorker = indexWorker;
        initGUI();
        setVisible(true);
    }

    public void initGUI(){
        //setLocationRelativeTo(null);
        generateFieldsAndAreas();
        Button editWorkerButt = new Button("Apply changes");
        editWorkerButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editWorker();
            }
        });
        add(editWorkerButt);
        setLayout(new FlowLayout());
        pack();
    }

    public void generateFieldsAndAreas(){
        //typesOfArgs = workersList.getWorker(indexWorker).getClass().getDeclaredConstructors()[0].getParameterTypes();
        arrayOfGetMethods = getGetMethods(workersList.getWorker(indexWorker).getClass());
        Object[] valuesOfFields = getValuesOfFields(arrayOfGetMethods);

        areasList = new ArrayList<TextArea>();
        for (int i = 0; i < arrayOfGetMethods.length; i++){
            TextArea textArea = new TextArea(valuesOfFields[i].toString());
            textArea.setPreferredSize(new Dimension(200, 100));
            add(new Label(arrayOfGetMethods[i].getName().replaceAll("get", "")));
            add(textArea);
            areasList.add(textArea);
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

        methods = sortMethodArray(methods);

        return methods;
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

        methods = sortMethodArray(methods);
        return methods;
    }

    public Method[] sortMethodArray(Method[] methods) {
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

    public Object[] getValuesOfFields(Method[] methods){
        Object[] values = new Object[methods.length];

        for (int i = 0; i < methods.length ; i++){
            try {
                values[i] = methods[i].invoke(workersList.getWorker(indexWorker));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return values;
    }

    public void editWorker(){
        try {
            Method[] arrayOfSetMethods = getSetMethods(workersList.getWorker(indexWorker).getClass());
            for (int i = 0 ; i < areasList.size(); i++){
                String data = areasList.get(i).getText();
                if (arrayOfGetMethods[i].getReturnType().getSimpleName().contains("int") || arrayOfGetMethods[i].getReturnType().getSimpleName().contains("Integer"))
                    arrayOfSetMethods[i].invoke(workersList.getWorker(indexWorker), Integer.parseInt(data));
                else if (arrayOfGetMethods[i].getReturnType().getSimpleName().contains("String"))
                    arrayOfSetMethods[i].invoke(workersList.getWorker(indexWorker), data);
                else if (arrayOfGetMethods[i].getReturnType().getSimpleName().contains("oolean"))
                    arrayOfSetMethods[i].invoke(workersList.getWorker(indexWorker), Boolean.valueOf(data));
                else
                    continue;
            }
            main.comboBox.insertItemAt(workersList.getWorkersName()[indexWorker], indexWorker);
            main.comboBox.removeItemAt(indexWorker+1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
