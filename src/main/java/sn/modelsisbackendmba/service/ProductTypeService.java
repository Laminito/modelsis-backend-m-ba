package sn.modelsisbackendmba.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.modelsisbackendmba.model.ProductType;
import sn.modelsisbackendmba.repository.ProductTypeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
@Slf4j
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;


    public ProductType addProductType(ProductType productType) {
        try {
            if (productType != null) {
                if (productType.getType() == null || productType.getType().trim().isEmpty()) {
                    throw new IllegalArgumentException("Le nom du type de produit est requis.");
                }
                return productTypeRepository.save(productType);
            } else {
                throw new IllegalArgumentException("Le type de produit à ajouter est null.");
            }
        } catch (Exception ex) {
            log.info("Une erreur est survenue lors de l'ajout du type de produit : " + ex.toString());
            return null;
        }
    }

    public List<ProductType> findAllProductTypes() {
        try {
            return productTypeRepository.findAllActiveProductsType();
        } catch (Exception ex) {
            log.info("Une erreur est survenue lors de la récupération des types de produits : " + ex.toString());
            return Collections.emptyList();
        }
    }

    public ProductType deleteProductType(String productTypeId) {
        ProductType result;
        Optional<ProductType> optionalProductType = productTypeRepository.findById(productTypeId);
        if (optionalProductType.isPresent()) {
            ProductType productType = optionalProductType.get();
            productType.setActive(false); // Mettre à jour l'état du produit à false
            result = productTypeRepository.save(productType);
        } else {
            throw new IllegalArgumentException("Le Type de Produit est non trouvé avec l'ID : " + productTypeId);
        }
        return result;
    }

    public ProductType updateProductTypes(ProductType product) {
        try {
            String productId = product.getId();
            Optional<ProductType> existingProductType = productTypeRepository.findByType(product.getType());
            if(!existingProductType.isPresent()){
                log.error("Le  produit avec le type "+product.getType()+" n'existe pas");
                throw new IllegalArgumentException("Le produit avec le type "+product.getType()+"n'existe pas");
            }
            Optional<ProductType> existingProduct = productTypeRepository.findById(existingProductType.get().getId());
            if(!existingProduct.isPresent()){
                log.error("Le produit avec l'id "+productId+" n'existe pas");
                throw new IllegalArgumentException("Le le type de produit avec l'id "+productId+"n'existe pas");
            }
            ProductType productResult = existingProduct.get();
            return productTypeRepository.updateProductType(productResult.getId(),productResult.getType());
        } catch (Exception ex) {
            log.info("Une erreur est survenue lors de la mise à jour du type de produit : " + ex.toString());
            throw new IllegalArgumentException("Erreur de validation lors de la mise à jour du type de produit");
        }
    }
}
