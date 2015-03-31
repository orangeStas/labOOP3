package WorkersPack;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public abstract class Worker {
    public String name;
    public int age;
    public int skill;

    public Worker(String name, int age, int skill){
        this.name = name;
        this.age = age;
        this.skill = skill;
    }

    public abstract int getSkill();

    public abstract void setSkill();

    public abstract String getName();

}
