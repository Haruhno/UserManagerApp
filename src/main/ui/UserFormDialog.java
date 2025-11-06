package main.ui;

import main.model.Utilisateur;
import main.service.ServiceUtilisateur;
import main.ui.components.ModernButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Dialogue pour ajouter ou modifier un utilisateur.
 */
public class UserFormDialog extends JDialog {
    private ServiceUtilisateur serviceUtilisateur;
    private Utilisateur user;
    private boolean success = false;
    
    // Composants du formulaire
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JComboBox<String> roleComboBox;
    private JButton saveButton;
    private JButton cancelButton;
    
    // Couleurs
    private final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private final Color NEUTRAL_COLOR = new Color(149, 165, 166);
    
    public UserFormDialog(Frame parent, Utilisateur user, ServiceUtilisateur serviceUtilisateur) {
        super(parent, true);
        this.user = user;
        this.serviceUtilisateur = serviceUtilisateur;
        
        initializeUI();
        populateFields();
        setupValidation();
    }
    
    private void initializeUI() {
        setTitle(user == null ? "Ajouter un utilisateur" : "Modifier l'utilisateur");
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Formulaire
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        
        // Boutons
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        // Labels
        JLabel prenomLabel = new JLabel("Prénom *:");
        JLabel nomLabel = new JLabel("Nom *:");
        JLabel emailLabel = new JLabel("Email *:");
        JLabel roleLabel = new JLabel("Rôle *:");
        
        // Champs
        prenomField = createTextField();
        nomField = createTextField();
        emailField = createTextField();
        
        // ComboBox des rôles
        roleComboBox = new JComboBox<>(new String[]{"Utilisateur", "Admin", "Manager", "Superviseur"});
        roleComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Ajout des composants au panel
        formPanel.add(prenomLabel);
        formPanel.add(prenomField);
        formPanel.add(nomLabel);
        formPanel.add(nomField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(roleLabel);
        formPanel.add(roleComboBox);
        
        return formPanel;
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        
        cancelButton = new ModernButton("Annuler", NEUTRAL_COLOR);
        saveButton = new ModernButton("Sauvegarder", SUCCESS_COLOR);
        saveButton.setEnabled(false);
        
        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveUser());
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        
        return buttonPanel;
    }
    
    private void populateFields() {
        if (user != null) {
            prenomField.setText(user.getPrenom());
            nomField.setText(user.getNom());
            emailField.setText(user.getEmail());
            roleComboBox.setSelectedItem(user.getRole());
        }
    }
    
    private void setupValidation() {
        KeyAdapter validationListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                validateForm();
            }
        };
        
        prenomField.addKeyListener(validationListener);
        nomField.addKeyListener(validationListener);
        emailField.addKeyListener(validationListener);
        
        roleComboBox.addActionListener(e -> validateForm());
        
        // Validation initiale
        validateForm();
    }
    
    private void validateForm() {
        String prenom = prenomField.getText().trim();
        String nom = nomField.getText().trim();
        String email = emailField.getText().trim();
        String role = (String) roleComboBox.getSelectedItem();
        
        boolean isValid = !prenom.isEmpty() && 
                         !nom.isEmpty() && 
                         !email.isEmpty() && 
                         role != null &&
                         isValidEmail(email);
        
        saveButton.setEnabled(isValid);
        
        // Validation visuelle
        setFieldValidation(prenomField, !prenom.isEmpty());
        setFieldValidation(nomField, !nom.isEmpty());
        setFieldValidation(emailField, isValidEmail(email));
    }
    
    private void setFieldValidation(JTextField field, boolean isValid) {
        if (field.getText().isEmpty()) {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        } else if (isValid) {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        } else {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(244, 67, 54), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }
    }
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    private void saveUser() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String role = (String) roleComboBox.getSelectedItem();
        
        boolean operationSuccess;
        
        if (user == null) {
            // Ajout d'un nouvel utilisateur
            operationSuccess = serviceUtilisateur.ajouterUtilisateur(nom, prenom, email, role);
        } else {
            // Modification d'un utilisateur existant
            operationSuccess = serviceUtilisateur.modifierUtilisateur(user.getId(), nom, prenom, email, role);
        }
        
        if (operationSuccess) {
            success = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Impossible de sauvegarder l'utilisateur. Vérifiez que l'email n'existe pas déjà.",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isSuccess() {
        return success;
    }
}