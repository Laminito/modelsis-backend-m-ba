package sn.modelsisbackendmba.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import sn.modelsisbackendmba.dto.ModelsIsResponseDTO;
import sn.modelsisbackendmba.dto.ProductTypeDto;
import sn.modelsisbackendmba.model.ProductType;
import sn.modelsisbackendmba.response.CustomResponse;
import sn.modelsisbackendmba.response.ResponseFactory;
import sn.modelsisbackendmba.service.ProductTypeService;
import sn.modelsisbackendmba.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product-type")
@Slf4j
@CrossOrigin("*")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    /**
     *   Pour les  besoins des testes unitaires setProductTypeService()
     */
    public void setProductTypeService(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }
    private Map<String, CustomResponse> resultMap;

    @GetMapping
    public ModelsIsResponseDTO getAllProductTypes() {
        ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
        try {
            List<ProductType> productTypes = productTypeService.findAllProductTypes();
            if(productTypes.isEmpty()){
                CustomResponse customResponse = ResponseFactory.createCustomResponse(
                        Constants.STATUS_MESSAGE_SUCCESS_BODY,
                        Constants.STATUS_VALUE_OK,
                        "Récupération de tous les types de produits a echoué",
                        productTypes
                );

                resultMap = new HashMap<>();
                resultMap.put("result", customResponse);
                responseDTO.setModelsis(resultMap);
                log.info("Récupération de tous les types de produits à réussie avec succée : {} ",responseDTO.getModelsis().get(responseDTO).getData());
                return  responseDTO;
            }
            List<ProductTypeDto> productTypeDtos = new ArrayList<>();
            for (ProductType product : productTypes) {
                ProductTypeDto productDto = new ProductTypeDto();
                productDto.setIdTypeProduct(product.getId());
                productDto.setType(product.getType());
                productDto.setCreatedDate(product.getCreatedDate());
                productDto.setLastModifiedDate((product.getLastModifiedDate()));
                productTypeDtos.add(productDto);
            }

            CustomResponse customResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_SUCCESS_BODY,
                    Constants.STATUS_VALUE_OK,
                    "Récupération de tous les types de produits réussie",
                    productTypeDtos
            );

            resultMap = new HashMap<>();
            resultMap.put("result", customResponse);
            responseDTO.setModelsis(resultMap);
            log.info("Récupération de tous les types de produits à réussie avec succée : {} ",productTypeDtos);

        }
        catch (DataAccessException ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur de base de données lors de la recuperation des types de produit ",
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur de base de données lors de la recuperation des types de produit : {} ",responseDTO.getModelsis().get(responseDTO).getData());
        }
        catch (Exception ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur lors de la récupération des types de produits ",
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur lors de la récupération des types de produits  : {}", ex.toString());
        }
        return responseDTO;
    }

    @GetMapping("/{productTypeId}")
    public ModelsIsResponseDTO getProductTypeById(@PathVariable String productTypeId) {
        ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
        try {
            ProductType productType = productTypeService.findProductTypeById(productTypeId);

            if (productType == null) {
                CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                        Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                        Constants.STATUS_VALUE_BAD_REQUEST,
                        "Aucun type de produit trouvé avec l'ID : " + productTypeId,
                        null
                );

                resultMap = new HashMap<>();
                resultMap.put("result", errorResponse);
                responseDTO.setModelsis(resultMap);
                log.error("Aucun type de produit trouvé avec l'ID : {} ", productTypeId);
                return responseDTO;
            }
                ProductTypeDto productTypeDto = new ProductTypeDto();
                productTypeDto.setIdTypeProduct(productType.getId());
                productTypeDto.setType(productType.getType());
                productTypeDto.setCreatedDate(productType.getCreatedDate());
                productTypeDto.setLastModifiedDate(productType.getLastModifiedDate());

                CustomResponse customResponse = ResponseFactory.createCustomResponse(
                        Constants.STATUS_MESSAGE_SUCCESS_BODY,
                        Constants.STATUS_VALUE_OK,
                        "Récupération du type de produit réussie",
                        productTypeDto
                );

                resultMap = new HashMap<>();
                resultMap.put("result", customResponse);
                responseDTO.setModelsis(resultMap);
                log.info("Récupération du type de produit réussie ");
                return responseDTO;
        }
        catch (DataAccessException ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur de base de données lors de la récupération du type de produit ",
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur de base de données lors de la récupération du type de produit : " + ex.toString());
        }
        catch (Exception ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur lors de la récupération du type de produit ",
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur lors de la récupération du type de produit : {}", ex.toString());
        }
        return responseDTO;
    }

    @GetMapping("/type/{productType}")
    public ModelsIsResponseDTO getByProductType(@PathVariable String productType) {
        ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
        try {
            ProductType productTypes = productTypeService.getByProductType(productType);

            if (productTypes == null) {
                CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                        Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                        Constants.STATUS_VALUE_BAD_REQUEST,
                        "Aucun type de produit trouvé avec cette categorie ",
                        null
                );

                resultMap = new HashMap<>();
                resultMap.put("result", errorResponse);
                responseDTO.setModelsis(resultMap);
                log.error("Aucun type de produit trouvé avec cette categorie ");

            }
                ProductTypeDto productTypeDto = new ProductTypeDto();
                productTypeDto.setIdTypeProduct(productTypes.getId());
                productTypeDto.setType(productTypes.getType());
                productTypeDto.setCreatedDate(productTypes.getCreatedDate());
                productTypeDto.setLastModifiedDate(productTypes.getLastModifiedDate());


                CustomResponse customResponse = ResponseFactory.createCustomResponse(
                        Constants.STATUS_MESSAGE_SUCCESS_BODY,
                        Constants.STATUS_VALUE_OK,
                        "Récupération du type de produit réussie",
                        productTypeDto
                );

                Map<String, CustomResponse> resultMap = new HashMap<>();
                resultMap.put("result", customResponse);
                responseDTO.setModelsis(resultMap);
                log.info("Récupération du type de produit réussie ");

        }
        catch (DataAccessException ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur de base de données lors de la recuperation du type de produit ",
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur de base de données lors de la recuperation du type de produit  : " + ex.toString());
        }
        catch (Exception ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur lors de la récupération du type de produit ",
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur lors de la récupération du type de produit : {}", ex.toString());
        }
        return responseDTO;
    }

    @PutMapping
    public ModelsIsResponseDTO updateProductType(@RequestBody ProductType product) {
        ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
        try {
            ProductType updatedProduct = productTypeService.updateProductType(product);
            if(updatedProduct == null){
                CustomResponse customResponse = ResponseFactory.createCustomResponse(
                        Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                        Constants.STATUS_VALUE_BAD_REQUEST,
                        "Erreur lor de la mise à jour du produit ",
                        null
                );

                resultMap = new HashMap<>();
                resultMap.put("result", customResponse);
                responseDTO.setModelsis(resultMap);
                log.info("Le Type de Produit {} n'à pas été mis à jour : {}", updatedProduct.getType(), updatedProduct.getId());

            }
            ProductTypeDto productDto = new ProductTypeDto();
            productDto.setType(updatedProduct.getType());
            productDto.setIdTypeProduct(updatedProduct.getId());

            CustomResponse customResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_SUCCESS_BODY,
                    Constants.STATUS_VALUE_OK,
                    "Le Type de Produit " + updatedProduct.getType() + " a été mis à jour avec succès",
                    productDto
            );

            resultMap = new HashMap<>();
            resultMap.put("result", customResponse);
            responseDTO.setModelsis(resultMap);
            log.info("Le Type de Produit {} a été mis à jour avec succès : {}", updatedProduct.getType(), updatedProduct.getId());
        }
        catch (DataAccessException ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur de base de données lors de la mise à jour du type de produit ",
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur de base de données lors de la mise à jour du type produit : " + ex.toString());
        }
        catch (Exception ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur lors de la mise à jour du type de produit ",
                    null
            );

            Map<String, CustomResponse> resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);

            log.error("Erreur lors de la mise à jour du type de produit : {}", ex.toString());
        }
        return responseDTO;
    }

    @DeleteMapping("/{productTypeId}")
    public ModelsIsResponseDTO deleteProduct(@PathVariable String productTypeId) {
         ProductType result = null;
         ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
         try {
             result = productTypeService.deleteProductType(productTypeId);
             CustomResponse customResponse = ResponseFactory.createCustomResponse(
                     Constants.STATUS_MESSAGE_SUCCESS_BODY,
                     Constants.STATUS_VALUE_OK,
                     "Le Type de Produit "+result.getType()+" a été supprimé avec succès ",
                     null
             );

             resultMap = new HashMap<>();
             resultMap.put("result", customResponse);
             responseDTO.setModelsis(resultMap);
             log.info("Le Type de Produit {} a été supprimé avec succès",result.getType());

         }
         catch (DataAccessException ex) {
             CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                     Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                     Constants.STATUS_VALUE_BAD_REQUEST,
                     "Erreur de base de données lors de la suppression d'un nouveau type de  produit ",
                     null
             );

             resultMap = new HashMap<>();
             resultMap.put("result", errorResponse);
             responseDTO.setModelsis(resultMap);
             log.error("Erreur de base de données lors de la suppression d'un nouveau type de produit ");
         }
         catch (Exception ex) {
             CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                     Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                     Constants.STATUS_VALUE_BAD_REQUEST,
                     "Erreur lors de la suppression du type de produit ",
                     null
             );

             resultMap = new HashMap<>();
             resultMap.put("result", errorResponse);
             responseDTO.setModelsis(resultMap);
             log.error("Erreur lors de la suppression du type de produit : {}", ex.toString());
         }
         return responseDTO;
     }

    @PostMapping
    public ModelsIsResponseDTO addProductType(@RequestBody ProductType productType) {
        ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
        try {
            ProductType newProductType = productTypeService.addProductType(productType);
            if(newProductType == null){
                CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                        Constants.STATUS_MESSAGE_BAD_REQUEST_BODY,
                        Constants.STATUS_VALUE_BAD_REQUEST,
                        "Le type de produit "+productType.getType()+" existe deja ",
                        null
                );
                resultMap = new HashMap<>();
                resultMap.put("modelsis", errorResponse);
                responseDTO.setModelsis(resultMap);
                log.error("Le type de produit {} existe deja ",productType.getType());
                return responseDTO;
            }

            ProductTypeDto productTypeDto = new ProductTypeDto();
            productTypeDto.setIdTypeProduct(newProductType.getId());
            productTypeDto.setType(newProductType.getType());
            productTypeDto.setCreatedDate(newProductType.getCreatedDate());

            CustomResponse customResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_SUCCESS_BODY,
                    Constants.STATUS_VALUE_OK,
                    "Nouveau type de produit ajouté avec succès",
                    productTypeDto
            );

            resultMap = new HashMap<>();
            resultMap.put("modelsis", customResponse);
            responseDTO.setModelsis(resultMap);
            log.info("Nouveau type de produit ajouté avec succès : {} ",productTypeDto);

        }
        catch (DataAccessException ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur de base de données lors de l'ajout d'un nouveau type de produit ",
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur de base de données lors de l'ajout d'un nouveau type de produit ");
        }
        catch (Exception ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_BAD_REQUEST_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur lors de l'ajout du type de produit ",
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("modelsis", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur lors de l'ajout du type de produit : " + ex.toString());
            return responseDTO;
        }
        return responseDTO;
    }
}
