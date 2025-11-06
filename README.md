
# UserManagerApp - Application de Gestion d'Utilisateurs

## Objectif du projet
UserManagerApp est une application Java de gestion d'utilisateurs développée avec Swing pour l'interface graphique.  
Elle met en avant une architecture claire avec séparation des responsabilités et inclut des tests unitaires pour valider le fonctionnement.

## Fonctionnalités principales
- **Interface moderne** : Design épuré avec couleurs et composants Swing personnalisés  
- **Gestion des utilisateurs** : Ajouter, modifier, supprimer, lister  
- **Recherche instantanée** : Filtrage par nom en temps réel  
- **Validation des données** : Vérification automatique des emails et champs obligatoires  
- **Feedback utilisateur** : Messages de confirmation et indications visuelles  
- **Architecture robuste** : Séparation claire des responsabilités (modèle, DAO, service, UI)

## Architecture du projet

UserManagerApp/
├── src/main/
│   ├── model/
│   │   └── Utilisateur.java          # Classe métier Utilisateur
│   ├── dao/
│   │   └── UtilisateurDAO.java       # Couche d'accès aux données
│   ├── service/
│   │   └── ServiceUtilisateur.java   # Couche métier et validation
│   └── ui/
│       ├── MainFrame.java            # Fenêtre principale
│       ├── UserFormDialog.java       # Dialogue formulaire
│       └── components/
│           └── ModernButton.java     # Boutons personnalisés
├── src/test/
│   ├── UtilisateurTest.java          # Tests unitaires modèle
│   └── ServiceUtilisateurTest.java   # Tests unitaires service
└── README.md


- **model** : Classe `Utilisateur` avec ses attributs et méthodes  
- **dao** : Gestion de la persistance des données en mémoire  
- **service** : Logique métier et validation des données  
- **ui** : Interface Swing avec composants personnalisés  
- **test** : Tests unitaires pour valider les fonctionnalités

## Prérequis
- Java JDK 8 ou supérieur (testé avec JDK 17)  
- JUnit 5 pour les tests (inclus dans la plupart des IDE)

## Lancer l'application

### Méthode 1 : Avec un IDE (recommandé)
1. Importer le projet dans IntelliJ, Eclipse ou NetBeans  
2. Configurer le JDK (8 ou supérieur)  
3. Exécuter la classe principale : `main.Main`  

### Méthode 2 : Ligne de commande

# Créer le dossier de sortie
mkdir bin

# Compiler toutes les classes
javac -d bin src/main/*.java src/main/model/*.java src/main/dao/*.java src/main/service/*.java src/main/ui/components/*.java src/main/ui/*.java

# Lancer l'application
java -cp bin main.Main

## Structure des données

La classe `Utilisateur` contient :

* `id` : Identifiant unique (int)
* `nom`, `prenom` : Chaînes non vides
* `email` : Adresse valide et unique
* `role` : Chaîne représentant le rôle utilisateur

## Tests unitaires

### Exécution

# Compiler les tests
javac -d bin -cp bin src/test/*.java

# Lancer avec JUnit
java -jar junit-platform-console-standalone-1.8.2.jar --class-path bin --scan-class-path

### Couverture

* Validation des données utilisateur
* Fonctionnalités CRUD
* Recherche et filtrage
* Gestion des erreurs et cas limites

## Utilisation de l'application

* **Tableau principal** : liste des utilisateurs
* **Boutons** : Ajouter, Modifier, Supprimer
* **Recherche** : Filtrage instantané par nom
* **Compteur** : Affiche le nombre total d’utilisateurs

### Ajouter un utilisateur

1. Cliquer sur "Ajouter"
2. Remplir le formulaire
3. Cliquer sur "Sauvegarder"

### Modifier un utilisateur

1. Sélectionner un utilisateur
2. Cliquer sur "Modifier"
3. Appliquer les changements et sauvegarder

### Supprimer un utilisateur

1. Sélectionner un utilisateur
2. Cliquer sur "Supprimer"
3. Confirmer la suppression

## Personnalisation

* **Ajouter de nouveaux rôles** : modifier le `JComboBox` dans `UserFormDialog.java`
* **Modifier les couleurs** : changer les constantes dans `MainFrame.java`

## Dépannage

* **Class not found** : vérifier compilation et packages
* **Interface ne s’affiche pas** : vérifier version du JDK
* **Performance** : conçu pour volumes modérés (persistance en mémoire)

## Améliorations futures

* Persistance dans fichier ou base de données
* Export CSV/PDF
* Interface multilingue
* Authentification et permissions
* Rapports statistiques
