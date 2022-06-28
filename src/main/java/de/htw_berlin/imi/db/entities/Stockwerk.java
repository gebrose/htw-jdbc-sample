package de.htw_berlin.imi.db.entities;

import java.util.ArrayList;
import java.util.List;

public class Stockwerk extends Entity {

    private int geschossnummer;

    // 1:n relationship, mapped by FK geschoss_id in raum
    private List<Raum> raeume = new ArrayList<>();

    public Stockwerk(final long id) {
        super(id);
    }

    public int getGeschossnummer() {
        return geschossnummer;
    }

    public void setGeschossnummer(final int geschossnummer) {
        this.geschossnummer = geschossnummer;
    }

    public List<Raum> getRaeume() {
        return raeume;
    }

    public void setRaeume(final List<Raum> raeume) {
        this.raeume = raeume;
    }
}
