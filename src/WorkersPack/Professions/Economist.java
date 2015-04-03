package WorkersPack.Professions;

import WorkersPack.OfficeWorker;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class Economist extends OfficeWorker {
    public Economist(String name, int age, int skill) {
        super(name, age, skill);
    }

    public Economist(){}

    @Override
    public int getSkill() {
        return skill;
    }

    @Override
    public void setSkill() {
        skill += 1;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void getReport() {

    }

    /*
    private void writeObject(ObjectOutputStream objectOutputStream) throws IllegalAccessException, IOException {
        Field[] fields = this.getClass().getFields();
        for (Field field : fields){
            if (field.getType().getSimpleName().contains("String")){
                objectOutputStream.writeUTF(String.valueOf(field.get(this)));
            }
            else if (field.getType().getSimpleName().contains("int") || field.getType().getSimpleName().contains("Integer")){
                objectOutputStream.writeInt(field.getInt(this));
            }
            else if (field.getType().getSimpleName().contains("oolean")){
                objectOutputStream.writeBoolean(field.getBoolean(this));
            }
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, IllegalAccessException {
        Field[] fields = this.getClass().getFields();
        for (Field field : fields){
            if (field.getType().getSimpleName().contains("String")){
                field.set(this, objectInputStream.readUTF());
            }
            else if (field.getType().getSimpleName().contains("int") || field.getType().getSimpleName().contains("Integer")){
                field.setInt(this, objectInputStream.readInt());
            }
            else if (field.getType().getSimpleName().contains("oolean")){
                field.setBoolean(this, objectInputStream.readBoolean());
            }
        }
    }
    */
}
