// Livre.java
public class Livre extends Media implements Empruntable {
    private static final long serialVersionUID = 1L;
    private String auteur;
    private String isbn;

    public Livre(String titre, int anneePublication, String auteur, String isbn) {
        super(titre, anneePublication);
        this.auteur = auteur;
        this.isbn = isbn;
    }

    public String getAuteur() { return auteur; }
    public String getIsbn() { return isbn; }

    @Override
    public void afficherDetails() {
        System.out.println("Livre: " + titre + " | Auteur: " + auteur + " | Année: " + anneePublication
                + " | ISBN: " + isbn + " | Disponible: " + (disponible ? "Oui" : "Non"));
    }
    @Override
    public String getType() {
        return "Livre";
    }
    @Override
    public void emprunter() throws LivreDejaEmprunteException {
        if (!disponible) throw new LivreDejaEmprunteException("Le livre '" + titre + "' est déjà emprunté.");
        disponible = false;
    }

    @Override
    public void rendre() {
        disponible = true;
    }
}
