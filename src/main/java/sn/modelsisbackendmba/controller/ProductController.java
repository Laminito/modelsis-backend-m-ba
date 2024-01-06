package sn.modelsisbackendmba.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsisbackendmba.dto.ProductDto;
import sn.modelsisbackendmba.model.Product;
import sn.modelsisbackendmba.repository.ProductRepository;
import sn.modelsisbackendmba.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    //Pour les Tests
    @Autowired
    private ProductRepository productRepository;
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository=productRepository;
    }


    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtos = new ArrayList<>();

        try {
            List<Product> products = productService.getAllProducts();

            for (Product product : products) {
                ProductDto productDto = new ProductDto();
                productDto.setIdProduct(product.getId());
                productDto.setName(product.getProductName());
                productDto.setType(product.getProductType().getType());
                productDto.setCreatedDate(product.getCreatedDate());
                productDtos.add(productDto);
            }

            log.info("Récupération de la liste des produits réussie avec succès");
        } catch (Exception ex) {
            log.error("Une erreur s'est produite lors de la récupération de la liste des produits : " + ex.toString());
                }

        return productDtos;
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            Product newProduct = productService.addProduct(product);
            log.info("Nouveau produit ajouté avec succès : {}", newProduct.getId());
            return ResponseEntity.ok("Produit ajouté avec succès");
        } catch (Exception ex) {
            log.error("Erreur lors de l'ajout du produit : {}", ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(product);
            // Création manuelle de ProductDto à partir des données de Product
            ProductDto productDto = new ProductDto();
            productDto.setIdProduct(updatedProduct.getId());
            productDto.setName(updatedProduct.getProductName());
            productDto.setType(updatedProduct.getProductType());
            log.info("Produit mis à jour avec succès : {}", updatedProduct.getId());
            return ResponseEntity.ok(productDto);
        } catch (Exception ex) {
            log.error("Erreur lors de la mise à jour du produit : {}", ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        try {
            productService.deleteProduct(productId);
            log.info("Produit supprimé avec succès : {}", productId);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("Erreur lors de la suppression du produit : {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}