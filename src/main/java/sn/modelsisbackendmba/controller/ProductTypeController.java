package sn.modelsisbackendmba.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsisbackendmba.dto.ProductDto;
import sn.modelsisbackendmba.dto.ProductTypeDto;
import sn.modelsisbackendmba.model.Product;
import sn.modelsisbackendmba.model.ProductType;
import sn.modelsisbackendmba.service.ProductTypeService;

import java.util.List;

@RestController
@RequestMapping("/product-type")
@Slf4j
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @PostMapping
    public ResponseEntity<ProductType> addProductType(@RequestBody ProductType productType) {
        try {
            ProductType newProductType = productTypeService.addProductType(productType);
            return ResponseEntity.ok(newProductType);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping
    public ResponseEntity<ProductTypeDto> updateProductType(@RequestBody ProductType product) {
        try {
            ProductType updatedProduct = productTypeService.updateProductTypes(product);
            ProductTypeDto productDto = new ProductTypeDto();
            productDto.setType(updatedProduct.getType());
            productDto.setIdTypeProduct(updatedProduct.getId());

            log.info("Le Type de Produit a ete mis à jour avec succès : {}", updatedProduct.getId());
            return ResponseEntity.ok(productDto);
        } catch (Exception ex) {
            log.error("Erreur lors de la mise à jour du type de produit : {}", ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping
    public List<ProductType> getAllProductTypes() {
        try {
            return productTypeService.findAllProductTypes();
        } catch (Exception ex) {
            System.out.println("Une erreur est survenue lors de la récupération des types de produits : " + ex.toString());
            return null;
        }
    }
    @DeleteMapping("/{productTypeId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productTypeId) {
        try {
            productTypeService.deleteProductType(productTypeId);
            log.info("Le Type de Produit est supprimé avec succès : {}", productTypeId);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("Erreur lors de la suppression du type de produit : {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
