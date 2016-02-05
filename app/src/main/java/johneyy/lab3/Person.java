package johneyy.lab3;

import java.io.Serializable;


public class Person implements Serializable {
    public String name;
    public int age;
    public float stars;
    public String system;
    Person(){
        name="";
        age=0;
        stars=0;
        system = "";
    }
    Person(String name,int age,String system,float stars){
        this.name=name;
        this.age=age;
        this.stars=stars;
        this.system=system;
    }

    @Override
    public String toString(){
        return name+", "+age+" years, "+"System: "+system+", "+stars+" stars";
    }

}
