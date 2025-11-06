package main.ui;

import main.model.Utilisateur;
import main.service.ServiceUtilisateur;
import main.ui.components.ModernButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Fenêtre principale de l'application UserManagerApp avec interface Swing moderne.
 */
public class MainFrame extends JFrame {
    private ServiceUtilisateur serviceUtilisateur;
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JLabel totalUsersLabel;
    
    // Couleurs modernes
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private final Color WARNING_COLOR = new Color(243, 156, 18);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color BACKGROUND_COLOR = new Color(245, 246, 250);
    
    public MainFrame() {
        this.serviceUtilisateur = new ServiceUtilisateur();
        initializeUI();
        loadUsers();
    }
    
    private void initializeUI() {
        setTitle("UserManagerApp - Gestion des Utilisateurs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Application d'un look moderne
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Panel principal avec bordure et fond
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // En-tête
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Barre d'outils
        mainPanel.add(createToolbarPanel(), BorderLayout.CENTER);
        
        // Table des utilisateurs
        mainPanel.add(createTablePanel(), BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        
        // Titre
        JLabel titleLabel = new JLabel("UserManagerApp");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        
        totalUsersLabel = new JLabel("Total : 0 utilisateur(s)");
        totalUsersLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        totalUsersLabel.setForeground(new Color(127, 140, 141));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(totalUsersLabel, BorderLayout.SOUTH);
        
        // Bouton actualiser
        ModernButton refreshButton = new ModernButton("Actualiser", PRIMARY_COLOR);
        refreshButton.addActionListener(e -> refreshUsers());
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(refreshButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createToolbarPanel() {
        JPanel toolbarPanel = new JPanel(new BorderLayout(10, 0));
        toolbarPanel.setBackground(BACKGROUND_COLOR);
        toolbarPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Boutons d'action
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonsPanel.setBackground(BACKGROUND_COLOR);
        
        ModernButton addButton = new ModernButton("Ajouter", SUCCESS_COLOR);
        ModernButton editButton = new ModernButton("Modifier", WARNING_COLOR);
        ModernButton deleteButton = new ModernButton("Supprimer", DANGER_COLOR);
        
        addButton.addActionListener(e -> showUserForm(null));
        editButton.addActionListener(e -> editSelectedUser());
        deleteButton.addActionListener(e -> deleteSelectedUser());
        
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        
        // Champ de recherche
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(BACKGROUND_COLOR);
        
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.putClientProperty("JTextField.placeholderText", "Rechercher par nom...");
        
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchUsers(searchField.getText().trim());
            }
        });
        
        searchPanel.add(searchField, BorderLayout.EAST);
        
        toolbarPanel.add(buttonsPanel, BorderLayout.WEST);
        toolbarPanel.add(searchPanel, BorderLayout.EAST);
        
        return toolbarPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Modèle de table
        String[] columnNames = {"ID", "Prénom", "Nom", "Email", "Rôle"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre la table non éditable
            }
        };
        
        usersTable = new JTable(tableModel);
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersTable.setRowHeight(30);
        usersTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usersTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        usersTable.getTableHeader().setBackground(new Color(52, 73, 94));
        usersTable.getTableHeader().setForeground(Color.WHITE);
        usersTable.setShowGrid(true);
        usersTable.setGridColor(new Color(236, 240, 241));
        
        // Centrer les colonnes ID et Rôle
        usersTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        usersTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        usersTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        usersTable.getColumnModel().getColumn(3).setPreferredWidth(250);
        usersTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        usersTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        usersTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        
        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private void loadUsers() {
        refreshUsers();
    }
    
    protected void refreshUsers() {
        List<Utilisateur> users = serviceUtilisateur.listerTousLesUtilisateurs();
        updateTable(users);
    }
    
    private void searchUsers(String searchTerm) {
        if (searchTerm.isEmpty()) {
            refreshUsers();
        } else {
            List<Utilisateur> results = serviceUtilisateur.rechercherUtilisateursParNom(searchTerm);
            updateTable(results);
        }
    }
    
    private void updateTable(List<Utilisateur> users) {
        tableModel.setRowCount(0);
        
        for (Utilisateur user : users) {
            tableModel.addRow(new Object[]{
                user.getId(),
                user.getPrenom(),
                user.getNom(),
                user.getEmail(),
                user.getRole()
            });
        }
        
        totalUsersLabel.setText("Total : " + users.size() + " utilisateur(s)");
    }
    
    private void showUserForm(Utilisateur user) {
        UserFormDialog dialog = new UserFormDialog(this, user, serviceUtilisateur);
        dialog.setVisible(true);
        
        // Rafraîchir après fermeture du dialogue
        if (dialog.isSuccess()) {
            refreshUsers();
        }
    }
    
    private void editSelectedUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) tableModel.getValueAt(selectedRow, 0);
            serviceUtilisateur.trouverUtilisateurParId(userId).ifPresent(this::showUserForm);
        } else {
            JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner un utilisateur à modifier.",
                "Aucune sélection",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void deleteSelectedUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) tableModel.getValueAt(selectedRow, 0);
            String userName = tableModel.getValueAt(selectedRow, 1) + " " + tableModel.getValueAt(selectedRow, 2);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Êtes-vous sûr de vouloir supprimer l'utilisateur : " + userName + " ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = serviceUtilisateur.supprimerUtilisateur(userId);
                if (success) {
                    JOptionPane.showMessageDialog(this,
                        "Utilisateur supprimé avec succès.",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                    refreshUsers();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Erreur lors de la suppression de l'utilisateur.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner un utilisateur à supprimer.",
                "Aucune sélection",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}