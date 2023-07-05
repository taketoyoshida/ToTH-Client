package model;

public class Blueprint {
    int ID;
    String name;

    double rate;

    public Blueprint(int id,String name,double rate){
        this.ID=id;
        this.name=name;
        this.rate=rate;
    }

    public double getRate(){
        return rate;
    }

    public String getName(){
        return name;
    }


}
