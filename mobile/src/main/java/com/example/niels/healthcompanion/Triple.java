package com.example.niels.healthcompanion;

/**
 * Created by niels on 22/01/2017.
 */

public class Triple {
    private String premier;
    private String deuxieme;
    private  int troisieme;

    Triple(String un, String deux, int trois)
    {
        premier = un;
        deuxieme = deux;
        troisieme = trois;
    }

    @Override
    public String toString()
    {
        return premier+' '+deuxieme;
    }

    public int getTroisieme() {
        return troisieme;
    }

    public String getDeuxieme() {
        return deuxieme;
    }

    public String getPremier() {
        return premier;
    }

    public void setDeuxieme(String deuxieme) {
        this.deuxieme = deuxieme;
    }

    public void setPremier(String premier) {
        this.premier = premier;
    }

    public void setTroisieme(int troisieme) {
        this.troisieme = troisieme;
    }
}
