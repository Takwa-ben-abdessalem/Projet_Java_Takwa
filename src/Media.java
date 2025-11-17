// Media.java
import java.io.Serializable;

public abstract class Media implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String titre;
    protected int anneePublication;
    protected boolean disponible;

    public Media(String titre, int anneePublication) {
        this.titre = titre;
        this.anneePublication = anneePublication;
        this.disponible = true;
    }

    public String getTitre() { return titre; }
    public int getAnneePublication() { return anneePublication; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public abstract void afficherDetails();
    public abstract String getType();

}
