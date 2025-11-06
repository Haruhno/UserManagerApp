package main.service;

import java.util.List;
import java.util.Optional;

import main.dao.UtilisateurDAO;
import main.model.Utilisateur;

/**
 * Service pour gérer les opérations métier sur les utilisateurs.
 * Fait le lien entre l'interface utilisateur et le DAO.
 */
public class ServiceUtilisateur {
    private UtilisateurDAO utilisateurDAO;
    
    /**
     * Constructeur qui initialise le DAO.
     */
    public ServiceUtilisateur() {
        this.utilisateurDAO = new UtilisateurDAO();
    }
    
    /**
     * Ajoute un nouvel utilisateur après validation.
     * 
     * @param nom le nom de l'utilisateur
     * @param prenom le prénom de l'utilisateur
     * @param email l'email de l'utilisateur
     * @param role le rôle de l'utilisateur
     * @return true si l'ajout est réussi, false sinon
     */
    public boolean ajouterUtilisateur(String nom, String prenom, String email, String role) {
        if (nom == null || nom.trim().isEmpty() || 
            prenom == null || prenom.trim().isEmpty() ||
            email == null || !estEmailValide(email) ||
            role == null || role.trim().isEmpty()) {
            return false;
        }
        
        Utilisateur utilisateur = new Utilisateur(0, nom.trim(), prenom.trim(), email.trim(), role.trim());
        return utilisateurDAO.ajouter(utilisateur);
    }
    
    /**
     * Supprime un utilisateur par son ID.
     * 
     * @param id l'ID de l'utilisateur à supprimer
     * @return true si la suppression est réussie, false sinon
     */
    public boolean supprimerUtilisateur(int id) {
        return utilisateurDAO.supprimer(id);
    }
    
    /**
     * Modifie un utilisateur existant.
     * 
     * @param id l'ID de l'utilisateur à modifier
     * @param nom le nouveau nom
     * @param prenom le nouveau prénom
     * @param email le nouvel email
     * @param role le nouveau rôle
     * @return true si la modification est réussie, false sinon
     */
    public boolean modifierUtilisateur(int id, String nom, String prenom, String email, String role) {
        if (nom == null || nom.trim().isEmpty() || 
            prenom == null || prenom.trim().isEmpty() ||
            email == null || !estEmailValide(email) ||
            role == null || role.trim().isEmpty()) {
            return false;
        }
        
        Utilisateur utilisateur = new Utilisateur(id, nom.trim(), prenom.trim(), email.trim(), role.trim());
        return utilisateurDAO.modifier(utilisateur);
    }
    
    /**
     * Récupère tous les utilisateurs.
     * 
     * @return la liste de tous les utilisateurs
     */
    public List<Utilisateur> listerTousLesUtilisateurs() {
        return utilisateurDAO.listerTous();
    }
    
    /**
     * Récupère un utilisateur par son ID.
     * 
     * @param id l'ID de l'utilisateur
     * @return un Optional contenant l'utilisateur s'il est trouvé
     */
    public Optional<Utilisateur> trouverUtilisateurParId(int id) {
        return utilisateurDAO.trouverParId(id);
    }
    
    /**
     * Recherche des utilisateurs par nom.
     * 
     * @param nom le nom à rechercher
     * @return la liste des utilisateurs correspondants
     */
    public List<Utilisateur> rechercherUtilisateursParNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            return List.of();
        }
        return utilisateurDAO.rechercherParNom(nom.trim());
    }
    
    /**
     * Vérifie si un email est valide.
     * 
     * @param email l'email à vérifier
     * @return true si l'email est valide, false sinon
     */
    private boolean estEmailValide(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}