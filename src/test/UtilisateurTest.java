package test;

import org.junit.jupiter.api.Test;

import main.model.Utilisateur;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Utilisateur.
 */
public class UtilisateurTest {
    
    @Test
    public void testUtilisateurValide() {
        Utilisateur utilisateur = new Utilisateur(1, "Dupont", "Jean", "jean.dupont@email.com", "Admin");
        assertTrue(utilisateur.estValide());
    }
    
    @Test
    public void testUtilisateurAvecNomVide() {
        Utilisateur utilisateur = new Utilisateur(1, "", "Jean", "jean.dupont@email.com", "Admin");
        assertFalse(utilisateur.estValide());
    }
    
    @Test
    public void testUtilisateurAvecEmailInvalide() {
        Utilisateur utilisateur = new Utilisateur(1, "Dupont", "Jean", "email-invalide", "Admin");
        assertFalse(utilisateur.estValide());
    }
    
    @Test
    public void testUtilisateurAvecRoleVide() {
        Utilisateur utilisateur = new Utilisateur(1, "Dupont", "Jean", "jean.dupont@email.com", "");
        assertFalse(utilisateur.estValide());
    }
    
    @Test
    public void testUtilisateurAvecPrenomNull() {
        Utilisateur utilisateur = new Utilisateur(1, "Dupont", null, "jean.dupont@email.com", "Admin");
        assertFalse(utilisateur.estValide());
    }
    
    @Test
    public void testGettersEtSetters() {
        Utilisateur utilisateur = new Utilisateur(1, "Dupont", "Jean", "jean.dupont@email.com", "Admin");
        
        assertEquals(1, utilisateur.getId());
        assertEquals("Dupont", utilisateur.getNom());
        assertEquals("Jean", utilisateur.getPrenom());
        assertEquals("jean.dupont@email.com", utilisateur.getEmail());
        assertEquals("Admin", utilisateur.getRole());
        
        utilisateur.setId(2);
        utilisateur.setNom("Martin");
        utilisateur.setPrenom("Marie");
        utilisateur.setEmail("marie.martin@email.com");
        utilisateur.setRole("Utilisateur");
        
        assertEquals(2, utilisateur.getId());
        assertEquals("Martin", utilisateur.getNom());
        assertEquals("Marie", utilisateur.getPrenom());
        assertEquals("marie.martin@email.com", utilisateur.getEmail());
        assertEquals("Utilisateur", utilisateur.getRole());
    }
    
    @Test
    public void testEqualsEtHashCode() {
        Utilisateur utilisateur1 = new Utilisateur(1, "Dupont", "Jean", "jean.dupont@email.com", "Admin");
        Utilisateur utilisateur2 = new Utilisateur(1, "Martin", "Marie", "marie.martin@email.com", "Utilisateur");
        Utilisateur utilisateur3 = new Utilisateur(2, "Dupont", "Jean", "jean.dupont@email.com", "Admin");
        
        assertEquals(utilisateur1, utilisateur2); // Même ID
        assertNotEquals(utilisateur1, utilisateur3); // IDs différents
        assertEquals(utilisateur1.hashCode(), utilisateur2.hashCode());
    }
    
    @Test
    public void testToString() {
        Utilisateur utilisateur = new Utilisateur(1, "Dupont", "Jean", "jean.dupont@email.com", "Admin");
        String toString = utilisateur.toString();
        
        assertTrue(toString.contains("Dupont"));
        assertTrue(toString.contains("Jean"));
        assertTrue(toString.contains("jean.dupont@email.com"));
        assertTrue(toString.contains("Admin"));
    }
}