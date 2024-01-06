# Backend - modelsis-backend-M-BA

## Introduction au projet
Ce projet est une application backend développée en Java 11 avec Spring Boot. Il gère les entités Product et ProductType avec des opérations CRUD via des API RESTful.

## Stack utilisée
- Java 11
- Spring Boot 2.7 ou supérieur
- Spring Rest
- Spring Data JPA
- Maven 3.2
- PostgreSQL 14
- Lombok (pas de getters et setters)

## Comment exécuter
1. Cloner le dépôt depuis GitHub : `git clone <lien-du-repo>`
2. Configurer les paramètres de connexion à la base de données dans `application.properties` ou via des variables d'environnement.
3. Exécuter la commande Maven pour démarrer l'application : `mvn spring-boot:run`

## Respect du standard HTTP pour les codes d’erreur
Les réponses HTTP suivent les standards : 
- 200 : Succès
- 404 : Ressource non trouvée
- 400 : Requête invalide
- 500 : Erreur serveur

## Paramètres de connexion à la base de données
Les paramètres de connexion à la base de données ne sont pas hardcodés mais configurés dans `application.properties` ou via des variables d'environnement.

## Validation des champs de saisie
Les validations des champs sont effectuées selon les critères suivants :
- Product Type : Unique et obligatoire
- Product Name : Unique et obligatoire

## API RESTful
Les endpoints et leurs descriptions :
- GET /product : Retourne un tableau de Produits
- POST /product : Insère un nouveau produit
- POST /productType : Insère un nouveau Type de Produit
- PUT /product : Met à jour un Produit
