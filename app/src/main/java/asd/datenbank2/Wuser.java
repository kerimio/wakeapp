package asd.datenbank2;

public class Wuser {

    int id;
    String name;
    long studiengaeng_id;
    int punkte;

    public Wuser(){}

    public Wuser(int id, String name, int punkte, int studiengangid) {
        this.id = id;
        this.name = name;
        this.punkte = punkte;
        this.studiengaeng_id = studiengangid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public void setStudiengaeng_id(int studiengaeng_id) {
        this.studiengaeng_id = studiengaeng_id;
    }

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public long getPunkte(){
        return this.punkte;


    }
}



