import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by OrangeUser on 4/1/2015.
 */
public class FormEdit extends JFrame {
    private WorkersList workersList;
    private int indexWorker;
    ArrayList<TextArea> areasList;
    //Class<?>[] typesOfArgs;
    Field[] arrayOfFields;

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
        arrayOfFields = getFields(workersList.getWorker(indexWorker).getClass());
        Object[] valuesOfFields = getValuesOfFields(arrayOfFields);

        areasList = new ArrayList<TextArea>();
        for (int i = 0; i < arrayOfFields.length; i++){
            TextArea textArea = new TextArea(valuesOfFields[i].toString());
            textArea.setPreferredSize(new Dimension(200, 100));
            add(new Label(arrayOfFields[i].getName()));
            add(textArea);
            areasList.add(textArea);
        }

    }

    public Field[] getFields(Class<?> choosenClass){
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

    public Object[] getValuesOfFields(Field[] fields){
        Object[] values = new Object[fields.length];

        for (int i = 0; i < fields.length ; i++){
            if (fields[i].getType().getSimpleName().contains("int") || fields[i].getType().getSimpleName().contains("Integer"))
                try {
                    values[i] = fields[i].getInt(workersList.getWorker(indexWorker));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            else if (fields[i].getType().getSimpleName().contains("String"))
                try {
                    values[i] = (String)fields[i].get(workersList.getWorker(indexWorker));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            else
                try {
                    values[i] = fields[i].get(workersList.getWorker(indexWorker));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
        return values;
    }

    public void editWorker(){
        try {
            Object[] args = new Object[arrayOfFields.length];
            for (int i = 0 ; i < areasList.size(); i++){
                String data = areasList.get(i).getText();
                if (arrayOfFields[i].getType().getSimpleName().contains("int") || arrayOfFields[i].getType().getSimpleName().contains("Integer"))
                    arrayOfFields[i].setInt(workersList.getWorker(indexWorker), Integer.parseInt(data));
                else if (arrayOfFields[i].getType().getSimpleName().contains("String"))
                    arrayOfFields[i].set(workersList.getWorker(indexWorker), data);
                else
                    continue;
            }
            //workersList.insertWorker((Worker) workersList.getWorker(indexWorker).getClass().getDeclaredConstructors()[0].newInstance(args), indexWorker);
            main.comboBox.insertItemAt(workersList.getWorkersName()[indexWorker], indexWorker);
            main.comboBox.removeItemAt(indexWorker+1);
            //main.comboBox.addItem(workersList.getWorkersName()[workersList.getWorkers().size() - 1]);
//        } catch (InstantiationException e) {
//            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
        }
    }
}
