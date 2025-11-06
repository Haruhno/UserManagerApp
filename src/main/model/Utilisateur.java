package main.model;

/**
 * Classe qui represente un utilisateur dans le système.
 * Contient les informations d'un utilisateur.
 */
public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    
    /**
     * Constructeur pour créer un nouvel utilisateur
     * 
     * @param id l'identifiant unique de l'utilisateur
     * @param nom le nom de famille de l'utilisateur
     * @param prenom le prénom de l'utilisateur
     * @param email l'adresse email de l'utilisateur
     * @param role le rôle de l'utilisateur dans le système
     */
    public Utilisateur(int id, String nom, String prenom, String email, String role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    /**
     * Vérifie si l'utilisateur est valide.
     * 
     * @return true si l'utilisateur a un nom non vide et un email valide, false sinon
     */
    public boolean estValide() {
        return nom != null && !nom.trim().isEmpty() &&
               prenom != null && !prenom.trim().isEmpty() &&
               email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$") &&
               role != null && !role.trim().isEmpty();
    }
    
    @Override
    public String toString() {
        return String.format("Utilisateur{id=%d, nom='%s', prenom='%s', email='%s', role='%s'}", 
                           id, nom, prenom, email, role);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Utilisateur that = (Utilisateur) obj;
        return id == that.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}