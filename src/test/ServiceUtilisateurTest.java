package test;

import org.junit.jupiter.api.Test;

import main.model.Utilisateur;
import main.service.ServiceUtilisateur;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

/**
 * Tests unitaires pour la classe ServiceUtilisateur.
 */
public class ServiceUtilisateurTest {
    private ServiceUtilisateur serviceUtilisateur;
    
    @BeforeEach
    public void setUp() {
        serviceUtilisateur = new ServiceUtilisateur();
    }
    
    @Test
    public void testAjouterUtilisateurValide() {
        boolean result = serviceUtilisateur.ajouterUtilisateur("Test", "User", "test.user@email.com", "Utilisateur");
        assertTrue(result);
        
        List<Utilisateur> utilisateurs = serviceUtilisateur.listerTousLesUtilisateurs();
        assertEquals(4, utilisateurs.size()); // 3 initiaux + 1 ajouté
    }
    
    @Test
    public void testAjouterUtilisateurInvalide() {
        boolean result = serviceUtilisateur.ajouterUtilisateur("", "User", "test.user@email.com", "Utilisateur");
        assertFalse(result);
        
        result = serviceUtilisateur.ajouterUtilisateur("Test", "User", "email-invalide", "Utilisateur");
        assertFalse(result);
        
        List<Utilisateur> utilisateurs = serviceUtilisateur.listerTousLesUtilisateurs();
        assertEquals(3, utilisateurs.size()); // Seulement les 3 initiaux
    }
    
    @Test
    public void testSupprimerUtilisateurExistant() {
        boolean result = serviceUtilisateur.supprimerUtilisateur(1);
        assertTrue(result);
        
        Optional<Utilisateur> utilisateur = serviceUtilisateur.trouverUtilisateurParId(1);
        assertTrue(utilisateur.isEmpty());
    }
    
    @Test
    public void testSupprimerUtilisateurInexistant() {
        boolean result = serviceUtilisateur.supprimerUtilisateur(999);
        assertFalse(result);
    }
    
    @Test
    public void testModifierUtilisateurValide() {
        boolean result = serviceUtilisateur.modifierUtilisateur(1, "NouveauNom", "NouveauPrenom", "nouveau@email.com", "NouveauRole");
        assertTrue(result);
        
        Optional<Utilisateur> utilisateur = serviceUtilisateur.trouverUtilisateurParId(1);
        assertTrue(utilisateur.isPresent());
        assertEquals("NouveauNom", utilisateur.get().getNom());
        assertEquals("NouveauPrenom", utilisateur.get().getPrenom());
        assertEquals("nouveau@email.com", utilisateur.get().getEmail());
        assertEquals("NouveauRole", utilisateur.get().getRole());
    }
    
    @Test
    public void testModifierUtilisateurInvalide() {
        boolean result = serviceUtilisateur.modifierUtilisateur(1, "", "Prenom", "email@valide.com", "Role");
        assertFalse(result);
        
        result = serviceUtilisateur.modifierUtilisateur(999, "Nom", "Prenom", "email@valide.com", "Role");
        assertFalse(result);
    }
    
    @Test
    public void testRechercherUtilisateursParNom() {
        List<Utilisateur> resultats = serviceUtilisateur.rechercherUtilisateursParNom("Dupont");
        assertFalse(resultats.isEmpty());
        assertEquals("Dupont", resultats.get(0).getNom());
    }
    
    @Test
    public void testRechercherUtilisateursParNomInexistant() {
        List<Utilisateur> resultats = serviceUtilisateur.rechercherUtilisateursParNom("Inexistant");
        assertTrue(resultats.isEmpty());
    }
    
    @Test
    public void testTrouverUtilisateurParIdExistant() {
        Optional<Utilisateur> utilisateur = serviceUtilisateur.trouverUtilisateurParId(1);
        assertTrue(utilisateur.isPresent());
        assertEquals(1, utilisateur.get().getId());
    }
    
    @Test
    public void testTrouverUtilisateurParIdInexistant() {
        Optional<Utilisateur> utilisateur = serviceUtilisateur.trouverUtilisateurParId(999);
        assertTrue(utilisateur.isEmpty());
    }
    
    @Test
    public void testListerTousLesUtilisateurs() {
        List<Utilisateur> utilisateurs = serviceUtilisateur.listerTousLesUtilisateurs();
        assertEquals(3, utilisateurs.size()); // Les 3 utilisateurs initiaux
    }
}