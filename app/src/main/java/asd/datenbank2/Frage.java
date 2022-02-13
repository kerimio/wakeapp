package asd.datenbank2;

public class Frage {
    int id;
    String frage;
    String richtige_antwort;
    String antwort_b;
    String antwort_c;
    String antwort_d;
    String hinweis;
    long studiengaeng_id;

    public Frage(){

    }

    public Frage(String frage, String richtige_antwort, String antwort_b, String antwort_c, String antwort_d, String hinweis, int studiengaeng_id)
    {

        this.frage = frage;
        this.richtige_antwort = richtige_antwort;
        this.antwort_b = antwort_b;
        this.antwort_c = antwort_c;
        this.antwort_d = antwort_d;
        this.hinweis = hinweis;
        this.studiengaeng_id = studiengaeng_id;
    }

    public void setId(int id)
    { this.id = id;
    }

    public void setFrage(String frage)
    { this.frage = frage;
    }

    public void setRichtigeAntwort(String richtige_antwort)
    { this.richtige_antwort = richtige_antwort;
    }

    public void setAntwortB(String antwort_b)
    { this.antwort_b = antwort_b;
    }

    public void setAntwortC(String antwort_c)
    { this.antwort_c = antwort_c;
    }

    public void setAntwortD(String antwort_d)
    { this.antwort_d = antwort_d;
    }

    public void setHinweis(String hinweis)
    { this.hinweis = hinweis;
    }

    public void setStudiengaengID(int studiengaeng_id)
    { this.studiengaeng_id = studiengaeng_id;
    }

    public String getFrage(){
        return this.frage;
    }

    public String getRichtigeAntwort(){
        return this.richtige_antwort;
    }

    public String getAntwortB(){
        return this.antwort_b;
    }

    public String getAntwortC(){
        return this.antwort_c;
    }

    public String getAntwortD(){
        return this.antwort_d;
    }

    public String getHinweis(){
        return this.hinweis;
    }
}

