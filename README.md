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
