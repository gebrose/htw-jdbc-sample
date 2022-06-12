package de.htw_berlin.imi.db.web;

/**
 * Data transfer object (DTO) class:
 * decouples representation from the entity class.
 * <p>
 * NB: we cannot create BueroRaum objects without an id.
 * Objects of this class simply hold field values
 */
public class BueroDto {

    private String name;

    private String raumnummer;

    private int kapazitaet;

    // TODO: add missing fields


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

    public int getKapazitaet() {
        return kapazitaet;
    }

    public void setKapazitaet(final int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }
}
