// Bibliotheque.java
import java.io.*;
import java.util.*;

public class Bibliotheque implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Media> medias;
    private Map<String, Livre> livresMap; // clé = titre (en minuscules)

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public Map<String, Livre> getLivresMap() {
        return livresMap;
    }

    public void setLivresMap(Map<String, Livre> livresMap) {
        this.livresMap = livresMap;
    }

    public Bibliotheque() {
        medias = new ArrayList<>();
        livresMap = new HashMap<>();
    }

    // Ajout générique
    public void ajouterMedia(Media m) {
        medias.add(m);
        if (m instanceof Livre) {
            Livre l = (Livre) m;
            livresMap.put(l.getTitre().toLowerCase(), l);
        }
    }

    public void listerMedias() {
        medias.forEach(Media::afficherDetails);
    }

    public Livre rechercherLivreParTitre(String titre) throws LivreNonTrouveException {
        Livre l = livresMap.get(titre.toLowerCase());
        if (l == null) throw new LivreNonTrouveException("Livre '" + titre + "' non trouvé.");
        return l;
    }

    public Media rechercherMediaParTitre(String titre) throws LivreNonTrouveException {
        for (Media m : medias) {
            if (m.getTitre().equalsIgnoreCase(titre)) return m;
        }
        throw new LivreNonTrouveException("Media '" + titre + "' non trouvé.");
    }

    // Sérialisation
    public void sauvegarder(String chemin) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
            oos.writeObject(this);
        }
    }

    public static Bibliotheque charger(String chemin) throws IOException, ClassNotFoundException {
        File f = new File(chemin);
        if (!f.exists()) return new Bibliotheque();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
            Object o = ois.readObject();
            if (o instanceof Bibliotheque) return (Bibliotheque) o;
            else return new Bibliotheque();
        }
    }
}
