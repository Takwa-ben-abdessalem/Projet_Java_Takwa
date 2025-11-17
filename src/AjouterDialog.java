import javax.swing.*;
import java.awt.*;

public class AjouterDialog extends JDialog {

    public AjouterDialog(JFrame parent, Bibliotheque bibliotheque, Runnable refreshCallback) {
        super(parent, "Ajouter un média", true);
        setLayout(new GridLayout(0, 2, 10, 10));
        setSize(350, 300);
        setLocationRelativeTo(parent);

        String[] types = {"Livre", "Magazine", "CD"};

        // Combobox type
        add(new JLabel("Type :"));
        JComboBox<String> typeBox = new JComboBox<>(types);
        add(typeBox);

        // Titre
        add(new JLabel("Titre :"));
        JTextField titreField = new JTextField();
        add(titreField);

        // Année
        add(new JLabel("Année :"));
        JTextField anneeField = new JTextField();
        add(anneeField);

        // Champs optionnels selon le type
        JLabel label1 = new JLabel("Auteur / Numéro / Artiste :");
        JTextField field1 = new JTextField();
        add(label1);
        add(field1);

        JLabel label2 = new JLabel("ISBN / — / Durée (sec):");
        JTextField field2 = new JTextField();
        add(label2);
        add(field2);

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.setBackground(new Color(80, 140, 200));
        btnAjouter.setForeground(Color.WHITE);
        btnAjouter.setFocusPainted(false);

        JButton btnAnnuler = new JButton("Annuler");

        add(btnAjouter);
        add(btnAnnuler);

        // Action bouton Ajouter
        btnAjouter.addActionListener(e -> {
            try {
                String titre = titreField.getText();
                int annee = Integer.parseInt(anneeField.getText());
                String t = (String) typeBox.getSelectedItem();

                switch (t) {
                    case "Livre":
                        bibliotheque.ajouterMedia(new Livre(titre, annee, field1.getText(), field2.getText()));
                        break;
                    case "Magazine":
                        bibliotheque.ajouterMedia(new Magazine(titre, annee, Integer.parseInt(field1.getText())));
                        break;
                    case "CD":
                        bibliotheque.ajouterMedia(new CD(titre, annee, field1.getText(), Integer.parseInt(field2.getText())));
                        break;
                }

                JOptionPane.showMessageDialog(this, "✅ Ajout réussi !");
                refreshCallback.run(); // rafraîchit le tableau
                dispose(); // ferme la fenêtre

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Erreur : " + ex.getMessage());
            }
        });

        // Action bouton Annuler
        btnAnnuler.addActionListener(e -> dispose());

        setVisible(true);
    }
}
