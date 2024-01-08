# Backend - modelsis-backend-M-BA

## Introduction au projet
Ce projet est une application backend développée en Java 11 avec Spring Boot. Il gère les entités Product et ProductType avec des opérations CRUD via des API RESTful.

## Stack utilisée
- Java 17
- Spring Boot 2.7 ou supérieur
- Spring Rest
- Spring Data JPA
- Maven 3.2
- PostgreSQL 14
- Lombok (pas de getters et setters)

## Base Url par defaut(/api/modelsis)
Ex : localhost:port/api/modelsis/...

# Description des API du ProductController

Ce contrôleur offre des fonctionnalités pour la gestion des produits.

## Liste des API

### 1. GET /product
- Récupère tous les produits.
- Retourne une liste de produits avec leurs détails.

### 2. GET /product/{productId}
- Récupère un produit spécifique par son ID.
- Retourne les détails du produit correspondant à l'ID fourni.

### 3. POST /product
- Ajoute un nouveau produit.
- Requiert les détails du produit à ajouter dans le corps de la requête.
- Retourne les détails du produit ajouté.

### 4. PUT /product
- Met à jour un produit existant.
- Requiert les détails du produit mis à jour dans le corps de la requête.
- Retourne les détails du produit mis à jour.

### 5. DELETE /product/{productId}
- Supprime un produit par son ID.
- Ne retourne aucun contenu, juste un statut de succès ou d'échec.

# Description des API du ProductTypeController

Ce contrôleur offre des fonctionnalités pour la gestion des types de produits.

## Liste des API

### 1. GET /product-type
- Récupère tous les types de produits.
- Retourne une liste de tous les types de produits avec leurs détails.

### 2. GET /product-type/{productTypeId}
- Récupère un type de produit spécifique par son ID.
- Retourne les détails du type de produit correspondant à l'ID fourni.

### 3. GET /product-type/type/{productType}
- Récupère un type de produit par son nom/categorie.
- Retourne les détails du type de produit correspondant à la categorie fournie.

### 4. POST /product-type
- Ajoute un nouveau type de produit.
- Requiert les détails du type de produit à ajouter dans le corps de la requête.
- Retourne les détails du type de produit ajouté.

### 5. PUT /product-type
- Met à jour un type de produit existant.
- Requiert les détails du type de produit mis à jour dans le corps de la requête.
- Retourne les détails du type de produit mis à jour.

### 6. DELETE /product-type/{productTypeId}
- Supprime un type de produit par son ID.
- Ne retourne aucun contenu, juste un statut de succès ou d'échec.

# Tests Unitaires

## Ce référentiel contient des tests unitaires pour le contrôleur de produits  et type de produit de l'application ModelsIsBackendMBA. 

### Prérequis

    Java 8 ou supérieur
    Maven pour l'exécution des tests


### Remarque
    Ces tests sont écrits en utilisant JUnit 5 et Mockito pour simuler le comportement du service de produits et des types de produits.

### Exécution des tests

    Exécuter mvn test dans le répertoire racine pour lancer les tests.

 
# Test pour le contrôleur de produits

### Structure des tests

    ProductControllerTest : Cette classe contient plusieurs méthodes de test pour tester différentes fonctionnalités du contrôleur de produits.


### Détails des tests

    testGetAllProducts : Vérifie la récupération de tous les produits et la structure de la réponse.
    testGetAllProducts_EmptyList : Vérifie la réponse lorsque la liste de produits est vide.
    testAddProduct : Vérifie l'ajout d'un produit et la structure de la réponse.
    testAddProduct_Failure : Vérifie la réponse en cas d'échec de l'ajout d'un produit.
    testUpdateProduct : Vérifie la mise à jour d'un produit et la structure de la réponse.
    testUpdateProduct_Failure : Vérifie la réponse en cas d'échec de la mise à jour d'un produit.
    testDeleteProduct : Vérifie la suppression d'un produit et la structure de la réponse.
    testDeleteProduct_Failure : Vérifie la réponse en cas d'échec de la suppression d'un produit.
    testGetProductById : Vérifie la récupération d'un produit par son ID et la structure de la réponse.
    testGetProductById_Failure : Vérifie la réponse en cas d'échec de la récupération d'un produit par son ID.

### NB : Chaque test a été conçu pour vérifier un scénario spécifique et s'assurer du bon fonctionnement du contrôleur de produits.


# Tests pour le contrôleur de types de produits

### Détails des tests

    testGetAllProductTypes : Vérifie la récupération de tous les types de produits et la structure de la réponse.
    testGetAllProductTypesWhenEmpty : Vérifie la réponse lorsque la liste des types de produits est vide.
    testGetProductTypeById : Vérifie la récupération d'un type de produit par son ID et la structure de la réponse.
    testGetProductTypeById_Failure : Vérifie la réponse en cas d'échec de récupération d'un type de produit par son ID.
    testGetByProductType_WhenProductTypeExists : Vérifie la récupération d'un type de produit par son nom lorsqu'il existe.
    testGetByProductType_WhenProductTypeDoesNotExist : Vérifie la réponse lorsque le type de produit n'existe pas.
    testUpdateProductType_WhenProductTypeExists : Vérifie la mise à jour d'un type de produit existant.
    testUpdateProductType_WhenProductTypeDoesNotExist : Vérifie la réponse lorsqu'on tente de mettre à jour un type de produit inexistant.
    testDeleteProduct_WhenProductTypeExists : Vérifie la suppression d'un type de produit existant.
    testDeleteProduct_WhenProductTypeDoesNotExist : Vérifie la réponse lorsqu'on tente de supprimer un type de produit inexistant.
    testAddProductType_NewProductTypeAddedSuccessfully : Vérifie l'ajout d'un nouveau type de produit avec succès.
    testAddProductType_ProductTypeAlreadyExists : Vérifie la réponse lorsque le type de produit à ajouter existe déjà.

### NB : Chaque test a été conçu pour valider un scénario spécifique et s'assurer du bon fonctionnement du contrôleur de types de produits.

### Contributions

Les contributions sous forme de suggestions, d'améliorations ou de corrections de bugs sont les bienvenues ! N'hésitez pas à ouvrir une pull request pour discuter des changements proposés.
