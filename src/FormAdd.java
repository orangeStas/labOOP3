import WorkersPack.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by OrangeUser on 3/30/2015.
 */
public class FormAdd extends JFrame {

    public ArrayList<Class<?>> classesList;
    public WorkersList workersList;
    Class<?>[] variables;
    Panel panel;
    ArrayList<TextArea> areasList;
    String[] opts;

    public FormAdd(WorkersList workersList){
        initGUI();
        setVisible(true);
        this.workersList = workersList;
    }

    public void initGUI(){
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
                remove(panel);
                panel = new Panel();
                panel.setLayout(new FlowLayout());
                JComboBox box = (JComboBox)e.getSource();
                variables = classesList.get(box.getSelectedIndex()).getDeclaredConstructors()[0].getParameterTypes();
                areasList = new ArrayList<TextArea>();
                for (Class<?> comp : variables){
                    TextArea textArea = new TextArea(comp.getSimpleName());
                    textArea.setPreferredSize(new Dimension( 200, 100));
                    panel.add(textArea);
                    areasList.add(textArea);
                }
                add(panel);
                revalidate();
                repaint();
                setLayout(new FlowLayout());
                pack();
            }
        });

        setLayout(new FlowLayout());
        pack();

    }

    public void addNewWorker(JComboBox box){
        try {
            opts = new String[variables.length];
            for (int i = 0; i < opts.length ; i++){
                opts[i] = areasList.get(i).getText();
            }
            workersList.insertWorker((Worker) classesList.get(box.getSelectedIndex()).getDeclaredConstructors()[0].newInstance(opts));
            main.comboBox.addItem(workersList.getWorkersName()[workersList.getWorkers().size()-1]);
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
