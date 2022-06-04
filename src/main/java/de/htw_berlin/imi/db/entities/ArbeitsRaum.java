package de.htw_berlin.imi.db.entities;

public abstract class ArbeitsRaum extends Raum {

    private int kapazitaet;

    protected ArbeitsRaum(long id) {
        super(id);
    }

    public int getKapazitaet() {
        return kapazitaet;
    }

    public void setKapazitaet(int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }
}
