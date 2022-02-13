package asd.datenbank2;

public class Studiengang {

    int id;
    String name;

    public Studiengang( String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}
