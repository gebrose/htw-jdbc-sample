package de.htw_berlin.imi.db.entities;

/**
 * Base class for all rooms.
 */
public abstract class Raum extends Entity {

    private String name;
    private String raumnummer;
    private double flaeche;
    private double hoehe;

    protected Raum(final long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getRaumnummer() {
        return raumnummer;
    }

    public void setRaumnummer(final String raumnummer) {
        this.raumnummer = raumnummer;
    }

    public double getFlaeche() {
        return flaeche;
    }

    public void setFlaeche(final double flaeche) {
        this.flaeche = flaeche;
    }

    public double getHoehe() {
        return hoehe;
    }

    public void setHoehe(final double hoehe) {
        this.hoehe = hoehe;
    }

}
