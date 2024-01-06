package sn.modelsisbackendmba.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsisbackendmba.model.Product;
import sn.modelsisbackendmba.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@NoArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        try {
            log.info("Liste des produits retourner avec succes");
            return productRepository.findAllActiveProducts();
        } catch (Exception ex) {
            log.error("Une erreur est survenue lors de la récupération des produits : " + ex.toString());
            return Collections.emptyList(); // Renvoie une liste vide en cas d'erreur
        }
    }
    public Product addProduct(Product product){
        try {
            if (product == null) {
                if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
                   log.error("Le nom du produit est requis");
                    throw new IllegalArgumentException("Le nom du produit est requis.");
                }
                log.info("Produit ajouté avec succes");
                return productRepository.save(product);
            } else {
                log.error("Echec d'enregistrement du Produit");
                throw new IllegalArgumentException("Le nom du  produit à ajouter est null.");
            }

        } catch (Exception ex) {
            log.info("Une erreur est survenue lors de l'ajout du produit : " + ex.toString());
            throw new IllegalArgumentException("Erreur de validation lors de l'ajout du produit");
        }
    }

    public Product updateProduct(Product product) {
        try {
            String productId = product.getId();
            Optional<Product> existingProductName = productRepository.findByProductName(product.getProductName());
            if(!existingProductName.isPresent()){
                log.error("Le produit avec le nom "+product.getProductName()+" n'existe pas");
                throw new IllegalArgumentException("Le produit avec le nom "+product.getProductName()+"n'existe pas");
            }
            Optional<Product> existingProduct = productRepository.findById(existingProductName.get().getId());
            if(!existingProduct.isPresent()){
                log.error("Le produit avec l'id "+productId+" n'existe pas");
                throw new IllegalArgumentException("Le produit avec l'id "+productId+"n'existe pas");
            }
            Product productResult = existingProduct.get();
            return productRepository.updateProduct(productResult.getId(),productResult.getProductName(),productResult.getProductType());
        } catch (Exception ex) {
           log.info("Une erreur est survenue lors de la mise à jour du produit : " + ex.toString());
            throw new IllegalArgumentException("Erreur de validation lors de la mise à jour du produit");
        }
    }

    public void deleteProduct(String productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setActive(false);
            log.info("Suppression du produit à reussie avec succes");
            productRepository.save(product);
        } else {
            log.info("Produit non trouvé avec l'ID : " + productId);
            throw new IllegalArgumentException("Produit non trouvé avec l'ID : " + productId);
        }
    }
}
