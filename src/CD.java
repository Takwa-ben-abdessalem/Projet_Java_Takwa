// CD.java
public class CD extends Media implements Empruntable {
    private static final long serialVersionUID = 1L;
    private String artiste;
    private int dureeSec; // durée en secondes

    public CD(String titre, int anneePublication, String artiste, int dureeSec) {
        super(titre, anneePublication);
        this.artiste = artiste;
        this.dureeSec = dureeSec;
    }

    public String getArtiste() { return artiste; }
    public int getDureeSec() { return dureeSec; }

    @Override
    public void afficherDetails() {
        System.out.println("CD: " + titre + " | Artiste: " + artiste + " | Année: " + anneePublication
                + " | Durée(s): " + dureeSec + " | Disponible: " + (disponible ? "Oui" : "Non"));
    }
    @Override
    public String getType() {
        return "CD";
    }

    @Override
    public void emprunter() throws Exception {
        if (!disponible) throw new Exception("CD non disponible.");
        disponible = false;
    }

    @Override
    public void rendre() {
        disponible = true;
    }
}
