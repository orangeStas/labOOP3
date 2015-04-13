package WorkersPack;

import java.io.Serializable;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public abstract class Worker implements Serializable {
    private String name;
    private int age;
    private int skill;

    public Worker(String name, int age, int skill){
        this.name = name;
        this.age = age;
        this.skill = skill;
    }

    public Worker(){

    }


    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public int getSkill(){
        return skill;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setSkill(int skill){
        this.skill = skill;
    }

}
