// Magazine.java
public class Magazine extends Media implements Empruntable {
    private static final long serialVersionUID = 1L;
    private int numero;

    public Magazine(String titre, int anneePublication, int numero) {
        super(titre, anneePublication);
        this.numero = numero;
    }

    public int getNumero() { return numero; }

    @Override
    public void afficherDetails() {
        System.out.println("Magazine: " + titre + " | N°: " + numero + " | Année: " + anneePublication
                + " | Disponible: " + (disponible ? "Oui" : "Non"));
    }
    @Override
    public String getType() {
        return "Magazine";
    }

    @Override
    public void emprunter() throws Exception {
        if (!disponible) throw new Exception("Magazine non disponible.");
        disponible = false;
    }

    @Override
    public void rendre() {
        disponible = true;
    }
}
