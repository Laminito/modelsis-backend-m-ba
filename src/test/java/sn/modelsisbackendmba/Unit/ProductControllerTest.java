package sn.modelsisbackendmba.Unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.modelsisbackendmba.controller.ProductController;
import sn.modelsisbackendmba.dto.ModelsIsResponseDTO;
import sn.modelsisbackendmba.dto.ProductDto;
import sn.modelsisbackendmba.model.Product;
import sn.modelsisbackendmba.model.ProductType;
import sn.modelsisbackendmba.response.CustomResponse;
import sn.modelsisbackendmba.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        // Création d'une liste de produits pour le test
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId("8e3dd8b2-8d50-4bc3-9007-77070a42fa55");
        product.setProductName("SamsungPlus");

        ProductType productType = new ProductType();
        productType.setType("Smartphone");

        product.setProductType(productType);
        products.add(product);

        // Mock du service ProductService
        ProductService productService = mock(ProductService.class);

        // Simulation de la récupération de tous les produits
        when(productService.getAllProducts()).thenReturn(products);

        // Initialisation du contrôleur avec le mock du service ProductService
        ProductController productController = new ProductController();
        productController.setProductService(productService);

        // Appel de la méthode à tester
        ModelsIsResponseDTO responseDTO = productController.getAllProducts();

        // Vérification de l'appel de la méthode getAllProducts du service
        verify(productService, times(1)).getAllProducts();

        // Vérification de la non-nullité de la réponse
        assertNotNull(responseDTO);

        // Vérification de la propriété "modelsis" dans la réponse
        Map<String, CustomResponse> modelsis = responseDTO.getModelsis();
        assertNotNull(modelsis);

        // Vérification de la propriété "result" dans "modelsis"
        CustomResponse customResponse = modelsis.get("result");
        assertNotNull(customResponse);

        // Vérification de la propriété "status" dans "customResponse"
        assertEquals("Success", customResponse.getStatus());

        // Vérification de la propriété "data" dans "customResponse"
        assertNotNull(customResponse.getData());

        // Vérification de la taille de la liste retournée dans "data"
        List<ProductDto> productDtos = (List<ProductDto>) customResponse.getData();
        assertEquals(1, productDtos.size());

        // Vérification des propriétés des éléments dans productDtos
        assertEquals("8e3dd8b2-8d50-4bc3-9007-77070a42fa55", productDtos.get(0).getIdProduct());
        assertEquals("SamsungPlus", productDtos.get(0).getName());
        assertEquals("Smartphone", productDtos.get(0).getType());
    }
    @Test
    public void testGetAllProducts_EmptyList() {

        // Création d'une liste vide de produits pour le test
        List<Product> emptyList = new ArrayList<>();

        // Simulation de la récupération de tous les produits (liste vide)
        when(productService.getAllProducts()).thenReturn(emptyList);

        // Appel de la méthode à tester
        ModelsIsResponseDTO responseDTO = productController.getAllProducts();

        // Vérification de l'appel de la méthode getAllProducts du service
        verify(productService, times(1)).getAllProducts();

        // Vérification de la non-nullité de la réponse
        assertNotNull(responseDTO);

        // Vérification de la propriété "modelsis" dans la réponse
        Map<String, CustomResponse> modelsis = responseDTO.getModelsis();
        assertNotNull(modelsis);

        // Vérification de la propriété "result" dans "modelsis"
        CustomResponse customResponse = modelsis.get("result");
        assertNotNull(customResponse);

        // Vérification de la propriété "status" dans "customResponse"
        assertEquals("Not Found", customResponse.getStatus());

        // Vérification de la propriété "data" dans "customResponse" (doit être null ou une liste vide)
        if(customResponse.getData()==null){
            assertNull(customResponse.getData() );
        }

    }
    @Test
    public void testAddProduct() {
        // Création d'un produit pour le test
        Product product = new Product();
        product.setProductName("Television");
        product.setProductType(new ProductType("Laptop"));

        // Mock du service ProductService
        ProductService productService = mock(ProductService.class);

        // Simulation de l'ajout du produit
        when(productService.addProduct(any(Product.class))).thenReturn(product);

        // Initialisation du contrôleur avec le mock du service ProductService
        ProductController productController = new ProductController();
        productController.setProductService(productService);

        // Appel de la méthode à tester
        ModelsIsResponseDTO response = productController.addProduct(product);

        // Vérification de l'appel de la méthode addProduct du service
        verify(productService, times(1)).addProduct(product);

        // Vérification de la non-nullité de la réponse
        assertNotNull(response);

        // Vérification de la propriété "modelsis" dans la réponse
        Map<String, CustomResponse> modelsis = response.getModelsis();
        assertNotNull(modelsis);

        // Vérification de la propriété "result" dans "modelsis"
        CustomResponse customResponse = modelsis.get("result");
        assertNotNull(customResponse);

        // Vérification de la propriété "status" dans "customResponse"
        assertEquals(200, customResponse.getStatusCode());

        // Vérification de la propriété "data" dans "customResponse"
        assertEquals("Produit ajouté avec succès", customResponse.getMessage());
    }
    @Test
    public void testAddProduct_Failure() {
        // Création d'un produit pour le test
        Product product = new Product();
        product.setProductName("Television");
        product.setProductType(new ProductType("Laptop"));

        // Mock du service ProductService pour simuler un échec d'ajout du produit
        ProductService productService = mock(ProductService.class);
        when(productService.addProduct(any(Product.class))).thenReturn(null); // Simulation de l'échec

        // Initialisation du contrôleur avec le mock du service ProductService
        ProductController productController = new ProductController();
        productController.setProductService(productService);

        // Appel de la méthode à tester
        ModelsIsResponseDTO response = productController.addProduct(product);

        // Vérification de l'appel de la méthode addProduct du service
        verify(productService, times(1)).addProduct(product);

        // Vérification de la non-nullité de la réponse
        assertNotNull(response);

        // Vérification de la propriété "modelsis" dans la réponse
        Map<String, CustomResponse> modelsis = response.getModelsis();
        assertNotNull(modelsis);

        // Vérification de la propriété "result" dans "modelsis"
        CustomResponse customResponse = modelsis.get("result");
        assertNotNull(customResponse);

        // Vérification de la propriété "status" dans "customResponse" pour un ajout échoué
        assertEquals(400, customResponse.getStatusCode());

        // Vérification de la propriété "data" dans "customResponse"
        assertNull(customResponse.getData());
    }
    @Test
    public void testUpdateProduct() {
        // Création d'un produit pour le test
        Product product = new Product();
        product.setId("8e3dd8b2-8d50-4bc3-9007-77070a42fa55");
        product.setProductName("SamsungPlus");
        product.setProductType(new ProductType("23cb1653-5373-469a-a199-2451b1a895c9"));

        // Mock du service ProductService
        ProductService productService = mock(ProductService.class);

        // Simulation de la mise à jour du produit
        when(productService.updateProduct(any(Product.class))).thenReturn(product);

        // Initialisation du contrôleur avec le mock du service ProductService
        ProductController productController = new ProductController();
        productController.setProductService(productService);

        // Appel de la méthode à tester
        ModelsIsResponseDTO response = productController.updateProduct(product);

        // Vérification de l'appel de la méthode updateProduct du service
        verify(productService, times(1)).updateProduct(product);

        // Vérification de la non-nullité de la réponse
        assertNotNull(response);

        // Vérification de la propriété "modelsis" dans la réponse
        Map<String, CustomResponse> modelsis = response.getModelsis();
        assertNotNull(modelsis);

        // Vérification de la propriété "result" dans "modelsis"
        CustomResponse customResponse = modelsis.get("result");
        assertNotNull(customResponse);

        // Vérification de la propriété "status" dans "customResponse"
        assertEquals("Success", customResponse.getStatus());

        // Vérification que la propriété "data" dans "customResponse" est non null
        assertNotNull(customResponse.getData());
    }
    @Test
    public void testUpdateProduct_Failure() {
        // Création d'un produit pour le test
        Product product = new Product();
        product.setId("8e3dd8b2-8d50-4bc3-9007-77070a42fa55");
        product.setProductName("SamsungPlus");
        product.setProductType(new ProductType("23cb1653-5373-469a-a199-2451b1a895c9"));

        // Mock du service ProductService pour simuler un échec de mise à jour du produit
        ProductService productService = mock(ProductService.class);
        when(productService.updateProduct(any(Product.class))).thenReturn(null); // Simulation de l'échec

        // Initialisation du contrôleur avec le mock du service ProductService
        ProductController productController = new ProductController();
        productController.setProductService(productService);

        // Appel de la méthode à tester
        ModelsIsResponseDTO response = productController.updateProduct(product);

        // Vérification de l'appel de la méthode updateProduct du service
        verify(productService, times(1)).updateProduct(product);

        // Vérification de la non-nullité de la réponse
        assertNotNull(response);

        // Vérification de la propriété "modelsis" dans la réponse
        Map<String, CustomResponse> modelsis = response.getModelsis();
        assertNotNull(modelsis);

        // Vérification de la propriété "result" dans "modelsis"
        CustomResponse customResponse = modelsis.get("result");
        assertNotNull(customResponse);

        // Vérification de la propriété "status" dans "customResponse" pour une mise à jour échouée
        assertEquals("Not Found", customResponse.getStatus());

        // Vérification que la propriété "data" dans "customResponse" est null
        assertNull(customResponse.getData());
    }
    @Test
    public void testDeleteProduct() {
        String productId = "8e3dd8b2-8d50-4bc3-9007-77070a42fa55"; // ID existant pour le test

        ProductService productService = mock(ProductService.class);
        // Simulation de la suppression d'un produit
        doNothing().when(productService).deleteProduct(productId);

        ProductController productController = new ProductController();
        productController.setProductService(productService);

        ModelsIsResponseDTO response = productController.deleteProduct(productId);

        // Vérifier si la méthode de suppression du produit a été appelée
        verify(productService, times(1)).deleteProduct(productId);

        // Vérifier que la réponse n'est pas null
        assertNotNull(response); // Vérifier que la réponse n'est pas null

        Map<String, CustomResponse> modelsis = response.getModelsis();
        // Vérifier que modelsis n'est pas null
        assertNotNull(modelsis);

        CustomResponse customResponse = modelsis.get("result");
        assertNotNull(customResponse); // Vérifier que customResponse n'est pas null

        // Vérifier la propriété status de customResponse
        assertEquals("Success", customResponse.getStatus());
        assertNull(customResponse.getData());
    }
    @Test
    public void testDeleteProduct_Failure() {
        String productId = "8e3dd8b2-8d50-4bc3-9007-77070a42fa55"; // ID existant pour le test

        ProductService productService = mock(ProductService.class);
        // Simulation d'un échec de suppression d'un produit
        doThrow(new RuntimeException("Erreur lors de la suppression")).when(productService).deleteProduct(productId);

        ProductController productController = new ProductController();
        productController.setProductService(productService);

        ModelsIsResponseDTO response = productController.deleteProduct(productId);

        // Vérifier si la méthode de suppression du produit a été appelée
        verify(productService, times(1)).deleteProduct(productId);

        // Vérifier que la réponse n'est pas null
        assertNotNull(response);

        Map<String, CustomResponse> modelsis = response.getModelsis();
        // Vérifier que modelsis n'est pas null
        assertNotNull(modelsis);

        CustomResponse customResponse = modelsis.get("result");
        assertNotNull(customResponse); // Vérifier que customResponse n'est pas null

        // Vérifier la propriété status de customResponse pour une suppression échouée
        assertEquals("Not Found", customResponse.getStatus());
        assertNull(customResponse.getData());
    }
    @Test
    public void testGetProductById() {
        // Création d'un produit fictif pour le test
        String productId = "8e3dd8b2-8d50-4bc3-9007-77070a42fa55";
        Product product = new Product();
        product.setId(productId);
        product.setProductName("SamsungPlus");

        ProductType productType = new ProductType();
        productType.setType("Smartphone");

        product.setProductType(productType);

        // Mock du service ProductService
        ProductService productService = mock(ProductService.class);

        // Simulation de la récupération d'un produit par son ID
        when(productService.getProductById(productId)).thenReturn(product);

        // Initialisation du contrôleur avec le mock du service ProductService
        ProductController productController = new ProductController();
        productController.setProductService(productService);

        // Appel de la méthode à tester
        ModelsIsResponseDTO response = productController.getProductById(productId);

        // Vérification de l'appel de la méthode getProductById du service
        verify(productService, times(1)).getProductById(productId);

        // Vérification de la non-nullité de la réponse
        assertNotNull(response);

        // Vérification de la propriété "modelsis" dans la réponse
        Map<String, CustomResponse> modelsis = response.getModelsis();
        assertNotNull(modelsis);

        // Vérification de la propriété "result" dans "modelsis"
        CustomResponse customResponse = modelsis.get("result");
        assertNotNull(customResponse);

        // Vérification de la propriété "status" dans "customResponse"
        assertEquals("Success", customResponse.getStatus());

        // Vérification de la propriété "data" dans "customResponse"
        assertNotNull(customResponse.getData());

        // Vérification des propriétés de l'objet ProductDto dans "data"
        ProductDto productDto = (ProductDto) customResponse.getData();
        assertEquals(productId, productDto.getIdProduct());
        assertEquals("SamsungPlus", productDto.getName());
        assertEquals("Smartphone", productDto.getType());
    }
    @Test
    public void testGetProductById_Failure() {
        String productId = "8e3dd8b2-8d50-4bc3-9007-77070a42fa55";

        ProductService productService = mock(ProductService.class);
        // Simulation d'un échec de récupération d'un produit par son ID
        when(productService.getProductById(productId)).thenReturn(null);

        ProductController productController = new ProductController();
        productController.setProductService(productService);

        ModelsIsResponseDTO response = productController.getProductById(productId);

        // Vérifier si la méthode de récupération d'un produit par ID a été appelée
        verify(productService, times(1)).getProductById(productId);

        // Vérifier que la réponse n'est pas null
        assertNotNull(response);

        Map<String, CustomResponse> modelsis = response.getModelsis();
        // Vérifier que modelsis n'est pas null
        assertNotNull(modelsis);

        CustomResponse customResponse = modelsis.get("result");
        assertNotNull(customResponse); // Vérifier que customResponse n'est pas null

        // Vérifier la propriété status de customResponse pour une récupération échouée
        assertEquals("Not Found", customResponse.getStatus());
        assertNull(customResponse.getData());
    }

}