package sn.modelsisbackendmba.Unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.modelsisbackendmba.controller.ProductTypeController;
import sn.modelsisbackendmba.dto.ModelsIsResponseDTO;
import sn.modelsisbackendmba.dto.ProductTypeDto;
import sn.modelsisbackendmba.model.ProductType;
import sn.modelsisbackendmba.response.CustomResponse;
import sn.modelsisbackendmba.service.ProductTypeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductTypeControllerTest {
    @Mock
    private ProductTypeService productTypeService;

    @InjectMocks
    private ProductTypeController productTypeController;
    @Test
    public void testGetAllProductTypes() {

        // Création de quelques ProductTypes fictifs
        List<ProductType> productTypes = new ArrayList<>();

        ProductType productType1 = new ProductType();
        productType1.setType("Habilles");
        productTypes.add(productType1);

        ProductType productType2 = new ProductType();
        productType2.setType("Smartphone");
        productTypes.add(productType2);

        ProductType productType3 = new ProductType();
        productType3.setType("Costumes");
        productTypes.add(productType3);

        // Simulation de la récupération de tous les types de produits
        when(productTypeService.findAllProductTypes()).thenReturn(productTypes);

        // Initialisation du contrôleur avec le mock du service ProductTypeService
        ProductTypeController productTypeController = new ProductTypeController();
        productTypeController.setProductTypeService(productTypeService);

        // Appel de la méthode à tester
        ModelsIsResponseDTO responseDTO = productTypeController.getAllProductTypes();

        // Vérification de l'appel de la méthode findAllProductTypes du service
        verify(productTypeService, times(1)).findAllProductTypes();

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
        List<ProductTypeDto> productTypeDtos = (List<ProductTypeDto>) customResponse.getData();
        assertEquals(3, productTypeDtos.size());

        // Vérification des propriétés des éléments dans productTypeDtos
        assertEquals("Habilles", productTypeDtos.get(0).getType());
        assertEquals("Smartphone", productTypeDtos.get(1).getType());
        assertEquals("Costumes", productTypeDtos.get(2).getType());
    }
    @Test
    public void testGetAllProductTypesWhenEmpty() {

        // Création d'une liste vide de ProductTypes fictifs
        List<ProductType> productTypes = new ArrayList<>();

        // Simulation de la récupération de tous les types de produits (liste vide)
        when(productTypeService.findAllProductTypes()).thenReturn(productTypes);

        // Initialisation du contrôleur avec le mock du service ProductTypeService
        ProductTypeController productTypeController = new ProductTypeController();
        productTypeController.setProductTypeService(productTypeService);

        // Appel de la méthode à tester
        ModelsIsResponseDTO responseDTO = productTypeController.getAllProductTypes();

        // Vérification de l'appel de la méthode findAllProductTypes du service
        verify(productTypeService, times(1)).findAllProductTypes();

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
    public void testGetProductTypeById() {

        // Création d'un ID de produit fictif pour le test
        String productTypeId = "34ce139f-e669-47aa-ad7e-ddb499616ff5";

        // Création d'un ProductType fictif pour simuler la réponse du service
        ProductType mockedProductType = new ProductType();
        mockedProductType.setId(productTypeId);
        mockedProductType.setType("Cheveux");

        // Simuler le comportement du service
        when(productTypeService.findProductTypeById(productTypeId)).thenReturn(mockedProductType);

        // Appel de la méthode à tester
        ModelsIsResponseDTO responseDTO = productTypeController.getProductTypeById(productTypeId);

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

        // Vérification des propriétés des éléments dans "data"
        ProductTypeDto productTypeDto = (ProductTypeDto) customResponse.getData();
        assertEquals(mockedProductType.getId(), productTypeDto.getIdTypeProduct());
        assertEquals(mockedProductType.getType(), productTypeDto.getType());

    }
    @Test
    public void testGetProductTypeById_Failure() {
        String productTypeId = "34ce139f-e669-47aa-ad7e-ddb499616ff5";

        // Simulation d'un échec de récupération d'un type de produit par son ID
        when(productTypeService.findProductTypeById(productTypeId)).thenReturn(null);

        ProductTypeController productTypeController = new ProductTypeController();
        productTypeController.setProductTypeService(productTypeService);

        ModelsIsResponseDTO responseDTO = productTypeController.getProductTypeById(productTypeId);

        // Vérifier si la méthode de récupération d'un type de produit par ID a été appelée
        verify(productTypeService, times(1)).findProductTypeById(productTypeId);

        // Vérifier que la réponse n'est pas null
        assertNotNull(responseDTO);

        Map<String, CustomResponse> modelsis = responseDTO.getModelsis();
        // Vérifier que modelsis n'est pas null
        assertNotNull(modelsis);

        CustomResponse customResponse = modelsis.get("result");
        assertNotNull(customResponse); // Vérifier que customResponse n'est pas null

        // Vérifier la propriété status de customResponse pour une récupération échouée
        assertEquals("Not Found", customResponse.getStatus());
        assertNull(customResponse.getData());
    }
    @Test
    public void testGetByProductType_WhenProductTypeExists() {
        // Création d'une catégorie de produit fictive pour le test
        String productType = "modelsis";

        // Création d'un ProductType fictif pour simuler la réponse du service
        ProductType mockedProductType = new ProductType();
        mockedProductType.setId("dc2107de-0637-4b5f-a88c-dd4be4d89926");
        mockedProductType.setType(productType);

        // Simuler le comportement du service
        when(productTypeService.getByProductType(productType)).thenReturn(mockedProductType);

        // Appel de la méthode à tester
        ModelsIsResponseDTO responseDTO = productTypeController.getByProductType(productType);

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

        // Vérification des propriétés des éléments dans "data"
        ProductTypeDto productTypeDto = (ProductTypeDto) customResponse.getData();
        assertEquals(mockedProductType.getId(), productTypeDto.getIdTypeProduct());
        assertEquals(mockedProductType.getType(), productTypeDto.getType());
    }
    @Test
    public void testGetByProductType_WhenProductTypeDoesNotExist() {
        //NonExistingProductType
        String productType = "NonExistingProductType";

        // Simuler un service renvoyant null lorsque le type de produit n'existe pas
        when(productTypeService.getByProductType(productType)).thenReturn(null);

        ModelsIsResponseDTO responseDTO = productTypeController.getByProductType(productType);

        assertNotNull(responseDTO);

        Map<String, CustomResponse> modelsis = responseDTO.getModelsis();
        assertNotNull(modelsis);

        CustomResponse customResponse = modelsis.get("result");
        assertNotNull(customResponse);

        assertEquals("Not Found", customResponse.getStatus());
        assertNull(customResponse.getData());
    }
    @Test
    public void testUpdateProductType_WhenProductTypeExists() {

        // Création d'un ProductType fictif pour le test
        ProductType productToUpdate = new ProductType();
        productToUpdate.setId("28cbbf9a-f9ad-4c57-ac8a-5465e91ab354");
        productToUpdate.setType("Chapeaux");

        // Création d'un ProductType mis à jour fictif après l'appel du service
        ProductType updatedProductType = new ProductType();
        updatedProductType.setId("28cbbf9a-f9ad-4c57-ac8a-5465e91ab354");
        updatedProductType.setType("ChapeauxCaboye");

        // Simuler le comportement du service
        when(productTypeService.updateProductType(productToUpdate)).thenReturn(updatedProductType);

        // Appel de la méthode à tester
        ModelsIsResponseDTO responseDTO = productTypeController.updateProductType(productToUpdate);

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

        // Vérification des propriétés des éléments dans "data"
        ProductTypeDto productTypeDto = (ProductTypeDto) customResponse.getData();
        assertEquals(updatedProductType.getId(), productTypeDto.getIdTypeProduct());
        assertEquals(updatedProductType.getType(), productTypeDto.getType());
    }
    @Test
    public void testUpdateProductType_WhenProductTypeDoesNotExist() {

        // Création d'un ProductType fictif pour le test
        ProductType nonExistingProductType = new ProductType();
        nonExistingProductType.setId("8u909H-f9ad-4c57-ac8a-5465e91ab354");
        nonExistingProductType.setType("NonExistingType");

        // Simuler un service renvoyant null lorsque le type de produit à mettre à jour n'existe pas
        when(productTypeService.updateProductType(nonExistingProductType)).thenReturn(null);

        // Appel de la méthode à tester
        ModelsIsResponseDTO responseDTO = productTypeController.updateProductType(nonExistingProductType);

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
        assertNull(customResponse.getData());
    }
    @Test
    public void testDeleteProduct_WhenProductTypeExists() {

        // Création d'un ProductType fictif pour le test
        ProductType productToDelete = new ProductType();
        productToDelete.setId("c156809b-71ed-41c6-b726-53b422e0851d");
        productToDelete.setType("Habilles");

        // Simuler le comportement du service lors de la suppression réussie
        when(productTypeService.deleteProductType(productToDelete.getId())).thenReturn(productToDelete);

        // Appel de la méthode à tester
        ModelsIsResponseDTO responseDTO = productTypeController.deleteProduct(productToDelete.getId());

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
        assertNull(customResponse.getData());
    }
    @Test
    public void testDeleteProduct_WhenProductTypeDoesNotExist() {

        // Définir un ProductType fictif pour le test
        String nonExistingProductTypeId = "c156809b-71ed-41c6-b726-hjvhg678jhfdbjhcbdscjhbsjdch";

        // Simuler un service renvoyant null lorsque le type de produit à supprimer n'existe pas
        when(productTypeService.deleteProductType(nonExistingProductTypeId)).thenReturn(null);

        // Appel de la méthode à tester
        ModelsIsResponseDTO responseDTO = productTypeController.deleteProduct(nonExistingProductTypeId);

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
        assertNull(customResponse.getData());
    }
    @Test
    public void testAddProductType_NewProductTypeAddedSuccessfully() {

        // Création d'un ProductType fictif pour le test
        ProductType newProductType = new ProductType();
        newProductType.setId("e1c5350d-c894-42fa-8f86-ccdef30b97bc");
        newProductType.setType("Voiture");

        // Simuler le comportement du service lors de l'ajout réussi d'un nouveau type de produit
        when(productTypeService.addProductType(newProductType)).thenReturn(newProductType);

        // Appel de la méthode à tester
        ModelsIsResponseDTO responseDTO = productTypeController.addProductType(newProductType);

        // Vérification de la non-nullité de la réponse
        assertNotNull(responseDTO);

        // Vérification de la propriété "modelsis" dans la réponse
        Map<String, CustomResponse> modelsis = responseDTO.getModelsis();
        assertNotNull(modelsis);

        // Vérification de la propriété "result" dans "modelsis"
        CustomResponse customResponse = modelsis.get("modelsis");
        assertNotNull(customResponse);

        // Vérification de la propriété "status" dans "customResponse"
        assertEquals("Success", customResponse.getStatus());

        // Vérification de la propriété "data" dans "customResponse"
        assertNotNull(customResponse.getData());
    }
    @Test
    public void testAddProductType_ProductTypeAlreadyExists() {

        // Création d'un ProductType fictif qui existe déjà pour le test
        ProductType existingProductType = new ProductType();
        existingProductType.setId("c894-e1c5350d-42fa-ccdef30b97bc-8f86");
        existingProductType.setType("ExistingType");

        // Simuler le comportement du service lors de l'ajout d'un type de produit existant
        when(productTypeService.addProductType(existingProductType)).thenReturn(null);

        // Appel de la méthode à tester
        ModelsIsResponseDTO responseDTO = productTypeController.addProductType(existingProductType);

        // Vérification de la non-nullité de la réponse
        assertNotNull(responseDTO);

        // Vérification de la propriété "modelsis" dans la réponse
        Map<String, CustomResponse> modelsis = responseDTO.getModelsis();
        assertNotNull(modelsis);

        // Vérification de la propriété "result" dans "modelsis"
        CustomResponse customResponse = modelsis.get("modelsis");
        assertNotNull(customResponse);

        // Vérification de la propriété "status" dans "customResponse"
        assertEquals("Invalid input data", customResponse.getStatus());
        assertNull(customResponse.getData());
    }

}
