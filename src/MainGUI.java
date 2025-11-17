import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class MainGUI extends JFrame {

    private Bibliotheque bibliotheque;
    private DefaultTableModel model;
    private JTable table;
    private JLabel statusLabel;
    private static final String CHEMIN_BIBLIOTHEQUE =
            "C:\\Users\\takwa.benabdessalem_.DESKTOP-ETP56KT\\Desktop\\M2\\Projets\\Projet_Java_TAKWA_BENABDESSALEM\\bibliotheque.dat";

    public MainGUI() {
        super("Gestion de Bibliothèque");
// --- Barre d'état en bas ---
        statusLabel = new JLabel("Prêt.");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        add(statusLabel, BorderLayout.SOUTH);

        // Charger la bibliothèque (persistance)
        try {
            File f = new File(CHEMIN_BIBLIOTHEQUE);
            if(f.exists()) {
                bibliotheque = Bibliotheque.charger(CHEMIN_BIBLIOTHEQUE);
                statusLabel.setText("📂 Bibliothèque chargée !");
            } else {
                bibliotheque = new Bibliotheque();
                statusLabel.setText("📂 Nouvelle bibliothèque créée !");
            }
        } catch (Exception e) {
            bibliotheque = new Bibliotheque();
            statusLabel.setText("⚠️ Erreur lors du chargement, nouvelle bibliothèque créée !");
        }


        // --- Layout principal ---
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        // --- Tableau moderne ---
        model = new DefaultTableModel(new Object[]{"Titre", "Type", "Année", "Disponible"}, 0);
        table = new JTable(model);
        table.setRowHeight(26);
        table.setShowGrid(false);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Barre de boutons en haut ---
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topBar.setBackground(new Color(230, 230, 240));

        JButton btnLister = createButton("Lister");
        JButton btnAjouter = createButton("Ajouter");
        JButton btnEmprunter = createButton("Emprunter");
        JButton btnRendre = createButton("Rendre");
        JButton btnSauvegarder = createButton("Sauvegarder");

        topBar.add(btnLister);
        topBar.add(btnAjouter);
        topBar.add(btnEmprunter);
        topBar.add(btnRendre);
        topBar.add(btnSauvegarder);

        add(topBar, BorderLayout.NORTH);

        // --- Barre d'état en bas ---
        statusLabel = new JLabel("Prêt.");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        add(statusLabel, BorderLayout.SOUTH);

        // ------------------- Actions -------------------

        btnLister.addActionListener(e -> afficherMedias());

        btnAjouter.addActionListener(e -> new AjouterDialog(this, bibliotheque, () -> afficherMedias()));

        btnEmprunter.addActionListener(e -> {
            String titre = JOptionPane.showInputDialog("Titre du média à emprunter :");
            if (titre == null) return;
            try {
                Media m = bibliotheque.rechercherMediaParTitre(titre);
                ((Empruntable) m).emprunter();
                afficherMedias();
                statusLabel.setText("✅ Emprunt réussi");
            } catch (Exception ex) {
                statusLabel.setText("❌ " + ex.getMessage());
            }
        });

        btnRendre.addActionListener(e -> {
            String titre = JOptionPane.showInputDialog("Titre du média à rendre :");
            if (titre == null) return;
            try {
                Media m = bibliotheque.rechercherMediaParTitre(titre);
                ((Empruntable) m).rendre();
                afficherMedias();
                statusLabel.setText("📚 Retour effectué");
            } catch (Exception ex) {
                statusLabel.setText("❌ " + ex.getMessage());
            }
        });

        btnSauvegarder.addActionListener(e -> {
            try {
                bibliotheque.sauvegarder(CHEMIN_BIBLIOTHEQUE);
                statusLabel.setText("💾 Sauvegarde réussie");
            } catch (Exception ex) {
                statusLabel.setText("❌ " + ex.getMessage());
            }
        });

        // Affichage fenêtre
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(90, 140, 200));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return b;
    }

    private void afficherMedias() {
        model.setRowCount(0);
        for (Media m : bibliotheque.getMedias()) {
            model.addRow(new Object[]{m.getTitre(), m.getType(), m.getAnneePublication(), m.isDisponible() ? "Oui" : "Non"});
        }
        statusLabel.setText("📋 Liste mise à jour");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
