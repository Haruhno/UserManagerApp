package main.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.model.Utilisateur;

/**
 * Classe DAO (Data Access Object) pour gérer la persistance des utilisateurs.
 * Simule une base de données en mémoire.
 */
public class UtilisateurDAO {
    private List<Utilisateur> utilisateurs;
    private int prochainId;
    
    /**
     * Constructeur qui initialise la liste des utilisateurs.
     */
    public UtilisateurDAO() {
        this.utilisateurs = new ArrayList<>();
        this.prochainId = 1;
        // Ajout de quelques utilisateurs de démonstration
        initialiserDonneesTest();
    }
    
    /**
     * Initialise quelques utilisateurs de test.
     */
    private void initialiserDonneesTest() {
        ajouter(new Utilisateur(prochainId++, "Dupont", "Jean", "jean.dupont@email.com", "Utilisateur"));
        ajouter(new Utilisateur(prochainId++, "Martin", "Marie", "marie.martin@email.com", "Admin"));
        ajouter(new Utilisateur(prochainId++, "Bernard", "Pierre", "pierre.bernard@email.com", "Utilisateur"));
    }
    
    /**
     * Ajoute un nouvel utilisateur à la liste.
     * 
     * @param utilisateur l'utilisateur à ajouter
     * @return true si l'ajout est réussi, false sinon
     */
    public boolean ajouter(Utilisateur utilisateur) {
        if (utilisateur == null || !utilisateur.estValide()) {
            return false;
        }
        
        // Vérifier si l'email existe déjà
        if (utilisateurs.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(utilisateur.getEmail()))) {
            return false;
        }
        
        // Assigner un ID si nécessaire
        if (utilisateur.getId() == 0) {
            utilisateur.setId(prochainId++);
        }
        
        utilisateurs.add(utilisateur);
        return true;
    }
    
    /**
     * Supprime un utilisateur par son ID.
     * 
     * @param id l'ID de l'utilisateur à supprimer
     * @return true si la suppression est réussie, false sinon
     */
    public boolean supprimer(int id) {
        return utilisateurs.removeIf(u -> u.getId() == id);
    }
    
    /**
     * Met à jour un utilisateur existant.
     * 
     * @param utilisateur l'utilisateur avec les nouvelles données
     * @return true si la mise à jour est réussie, false sinon
     */
    public boolean modifier(Utilisateur utilisateur) {
        if (utilisateur == null || !utilisateur.estValide()) {
            return false;
        }
        
        for (int i = 0; i < utilisateurs.size(); i++) {
            if (utilisateurs.get(i).getId() == utilisateur.getId()) {
                utilisateurs.set(i, utilisateur);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Récupère un utilisateur par son ID.
     * 
     * @param id l'ID de l'utilisateur à rechercher
     * @return un Optional contenant l'utilisateur s'il est trouvé
     */
    public Optional<Utilisateur> trouverParId(int id) {
        return utilisateurs.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }
    
    /**
     * Récupère tous les utilisateurs.
     * 
     * @return la liste de tous les utilisateurs
     */
    public List<Utilisateur> listerTous() {
        return new ArrayList<>(utilisateurs);
    }
    
    /**
     * Recherche des utilisateurs par nom.
     * 
     * @param nom le nom à rechercher
     * @return la liste des utilisateurs correspondants
     */
    public List<Utilisateur> rechercherParNom(String nom) {
        return utilisateurs.stream()
                .filter(u -> u.getNom().toLowerCase().contains(nom.toLowerCase()))
                .toList();
    }
    
    /**
     * Recherche des utilisateurs par email.
     * 
     * @param email l'email à rechercher
     * @return la liste des utilisateurs correspondants
     */
    public List<Utilisateur> rechercherParEmail(String email) {
        return utilisateurs.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .toList();
    }
}