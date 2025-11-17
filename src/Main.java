// Main.java
import java.util.Scanner;

public class Main {
    private static final String FICHIER = "bibliotheque.dat";

    public static void main(String[] args) {
        Bibliotheque b = null;
        try {
            b = Bibliotheque.charger(FICHIER);
            System.out.println("Bibliothèque chargée depuis " + FICHIER);
        } catch (Exception e) {
            System.out.println("Aucune sauvegarde trouvée — création d'une nouvelle bibliothèque.");
            b = new Bibliotheque();
        }

        // Données de démonstration
        if (b != null) {
            if (b != null && b.getLivresMap().isEmpty() && b.getMedias().isEmpty()) {
                b.ajouterMedia(new Livre("Le Petit Prince", 1943, "Antoine de Saint-Exupéry", "978-0156012195"));
                b.ajouterMedia(new Magazine("Science & Vie", 2024, 867));
                b.ajouterMedia(new CD("Thriller", 1982, "Michael Jackson", 2580));
            }
        }

        Scanner sc = new Scanner(System.in);
        boolean quitter = false;
        while (!quitter) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Lister tous les médias");
            System.out.println("2. Rechercher un livre par titre");
            System.out.println("3. Emprunter un média");
            System.out.println("4. Rendre un média");
            System.out.println("5. Ajouter un média (ex: Livre)");
            System.out.println("6. Sauvegarder et quitter");
            System.out.print("Choix: ");
            String choix = sc.nextLine();
            try {
                switch (choix) {
                    case "1":
                        b.listerMedias();
                        break;
                    case "2":
                        System.out.print("Titre: ");
                        String t2 = sc.nextLine();
                        try {
                            Livre l = b.rechercherLivreParTitre(t2);
                            l.afficherDetails();
                        } catch (LivreNonTrouveException ex) {
                            System.out.println("Erreur: " + ex.getMessage());
                        }
                        break;
                    case "3":
                        System.out.print("Titre du média à emprunter: ");
                        String t3 = sc.nextLine();
                        try {
                            Media m = b.rechercherMediaParTitre(t3);
                            if (m instanceof Empruntable) {
                                ((Empruntable) m).emprunter();
                                System.out.println("Emprunt réussi : " + m.getTitre());
                            } else {
                                throw new MediaNonEmpruntableException("Ce média ne peut pas être emprunté.");
                            }
                        } catch (LivreNonTrouveException | MediaNonEmpruntableException e) {
                            System.out.println("Erreur: " + e.getMessage());
                        } catch (LivreDejaEmprunteException e) {
                            System.out.println("Erreur: " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Erreur inattendue: " + e.getMessage());
                        }
                        break;
                    case "4":
                        System.out.print("Titre du média à rendre: ");
                        String t4 = sc.nextLine();
                        try {
                            Media m4 = b.rechercherMediaParTitre(t4);
                            if (m4 instanceof Empruntable) {
                                ((Empruntable) m4).rendre();
                                System.out.println("Retour effectué: " + m4.getTitre());
                            } else {
                                System.out.println("Ce média n'est pas empruntable.");
                            }
                        } catch (LivreNonTrouveException e) {
                            System.out.println("Erreur: " + e.getMessage());
                        }
                        break;
                    case "5":
                        System.out.println("Ajouter: 1=Livre, 2=Magazine, 3=CD");
                        String type = sc.nextLine();
                        if ("1".equals(type)) {
                            System.out.print("Titre: "); String titre = sc.nextLine();
                            System.out.print("Année: "); int year = Integer.parseInt(sc.nextLine());
                            System.out.print("Auteur: "); String auteur = sc.nextLine();
                            System.out.print("ISBN: "); String isbn = sc.nextLine();
                            b.ajouterMedia(new Livre(titre, year, auteur, isbn));
                            System.out.println("Livre ajouté.");
                        } else if ("2".equals(type)) {
                            System.out.print("Titre: "); String t = sc.nextLine();
                            System.out.print("Année: "); int y = Integer.parseInt(sc.nextLine());
                            System.out.print("Numéro: "); int num = Integer.parseInt(sc.nextLine());
                            b.ajouterMedia(new Magazine(t, y, num));
                            System.out.println("Magazine ajouté.");
                        } else if ("3".equals(type)) {
                            System.out.print("Titre: "); String t = sc.nextLine();
                            System.out.print("Année: "); int y = Integer.parseInt(sc.nextLine());
                            System.out.print("Artiste: "); String art = sc.nextLine();
                            System.out.print("Durée (sec): "); int d = Integer.parseInt(sc.nextLine());
                            b.ajouterMedia(new CD(t, y, art, d));
                            System.out.println("CD ajouté.");
                        } else {
                            System.out.println("Type inconnu.");
                        }
                        break;
                    case "6":
                        try {
                            b.sauvegarder(FICHIER);
                            System.out.println("Bibliothèque sauvegardée dans " + FICHIER);
                        } catch (Exception e) {
                            System.out.println("Erreur lors de la sauvegarde: " + e.getMessage());
                        }
                        quitter = true;
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (Exception e) {
                System.out.println("Erreur générale: " + e.getMessage());
            }
        }
        sc.close();
    }
}
