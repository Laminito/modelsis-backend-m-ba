package sn.modelsisbackendmba.Unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sn.modelsisbackendmba.controller.ProductController;
import sn.modelsisbackendmba.dto.ProductDto;
import sn.modelsisbackendmba.model.Product;
import sn.modelsisbackendmba.model.ProductType;
import sn.modelsisbackendmba.service.ProductService;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;
    

    @Test
    public void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId("8e3dd8b2-8d50-4bc3-9007-77070a42fa55");
        product.setProductName("SamsungPlus");

        ProductType productType = new ProductType();
        productType.setType("Smartphone");

        product.setProductType(productType);
        products.add(product);
        when(productService.getAllProducts()).thenReturn(products);

        List<ProductDto> productDtos = productController.getAllProducts();

        verify(productService, times(1)).getAllProducts();
        assertEquals(1, productDtos.size());
        // Vérifiez que les éléments de productDtos correspondent aux produits fictifs
        assertEquals("8e3dd8b2-8d50-4bc3-9007-77070a42fa55", productDtos.get(0).getIdProduct());
        assertEquals("SamsungPlus", productDtos.get(0).getName());
        assertEquals("Smartphone", productDtos.get(0).getType());
    }

    @Test
    public void testAddProduct() {
        // Créez un mock du service ProductService
        ProductService productService = mock(ProductService.class);

        // Créez un objet Product pour le test
        Product product = new Product();
        product.setProductName("Television");
        product.setProductType(new ProductType("Laptop"));
        // Configurez le produit avec les données nécessaires pour le test

        // Initialisez le contrôleur avec le mock du service ProductService
        ProductController productController = new ProductController();
        productController.setProductService(productService); // Injectez le service dans le contrôleur

        // Définissez le comportement simulé du service lors de l'ajout du produit
        when(productService.addProduct(product)).thenReturn(product);

        // Appelez la méthode à tester
        ResponseEntity<String> response = productController.addProduct(product);

        // Vérifiez si le service a été appelé une fois avec le bon produit
        verify(productService, times(1)).addProduct(product);

        // Vérifiez si la réponse est conforme aux attentes
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produit ajouté avec succès", response.getBody());
    }

    @Test
    public void testUpdateProduct() {
        // Créez un mock du service ProductService
        ProductService productService = mock(ProductService.class);

        // Créez un objet Product pour le test
        Product product = new Product();
        product.setId("8e3dd8b2-8d50-4bc3-9007-77070a42fa55");
        product.setProductName("SamsungPlus");
        product.setProductType(new ProductType("23cb1653-5373-469a-a199-2451b1a895c9"));

        // Initialisez le contrôleur avec le mock du service ProductService
        ProductController productController = new ProductController();
        productController.setProductService(productService); // Injectez le service dans le contrôleur

        when(productService.updateProduct(product)).thenReturn(product);

        // Appelez la méthode à tester
        ResponseEntity<ProductDto> response = productController.updateProduct(product);

        verify(productService, times(1)).updateProduct(product);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());    }

    @Test
    public void testDeleteProduct() {
        String productId = "8e3dd8b2-8d50-4bc3-9007-77070a42fa55"; // ID existant pour le test

        ProductService productService = mock(ProductService.class);

        ProductController productController = new ProductController();
        productController.setProductService(productService);

        ResponseEntity<Void> response = productController.deleteProduct(productId);

        verify(productService, times(1)).deleteProduct(productId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

}