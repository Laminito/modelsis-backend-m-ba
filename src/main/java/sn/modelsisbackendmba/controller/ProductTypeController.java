package sn.modelsisbackendmba.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    private Map<String, CustomResponse> resultMap;

    @GetMapping
    public ModelsIsResponseDTO getAllProductTypes() {
        ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
        try {
            List<ProductType> productTypes = productTypeService.findAllProductTypes();
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

        } catch (Exception ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur lors de la récupération des types de produits : " + ex.toString(),
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur lors de la récupération des types de produits : {}", ex.toString());
        }
        return responseDTO;
    }

    @GetMapping("/{productTypeId}")
    public ModelsIsResponseDTO getProductTypeById(@PathVariable String productTypeId) {
        ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
        try {
            ProductType productType = productTypeService.findProductTypeById(productTypeId);

            if (productType != null) {
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

                Map<String, CustomResponse> resultMap = new HashMap<>();
                resultMap.put("result", customResponse);
                responseDTO.setModelsis(resultMap);
                log.info("Récupération du type de produit réussie : {} ",productTypeDto);
            } else {
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
            }
        } catch (Exception ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur lors de la récupération du type de produit : " + ex.toString(),
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
        } catch (Exception ex) {
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

         } catch (Exception ex) {
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

        } catch (IllegalArgumentException ex) {
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
