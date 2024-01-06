package sn.modelsisbackendmba.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.modelsisbackendmba.dto.ModelsIsResponseDTO;
import sn.modelsisbackendmba.dto.ProductDto;
import sn.modelsisbackendmba.dto.ProductTypeDto;
import sn.modelsisbackendmba.model.Product;
import sn.modelsisbackendmba.model.ProductType;
import sn.modelsisbackendmba.response.CustomResponse;
import sn.modelsisbackendmba.response.ResponseFactory;
import sn.modelsisbackendmba.service.ProductTypeService;
import sn.modelsisbackendmba.util.Constants;

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

            CustomResponse customResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_SUCCESS_BODY,
                    Constants.STATUS_VALUE_OK,
                    "Récupération de tous les types de produits réussie",
                    productTypes
            );

            resultMap = new HashMap<>();
            resultMap.put("result", customResponse);
            responseDTO.setModelsis(resultMap);
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
        log.info("Récupération de tous les types de produits à réussie avec succée");
        return responseDTO;
    }

    @PutMapping
    public ResponseEntity<ProductTypeDto> updateProductType(@RequestBody ProductType product) {
        try {
            ProductType updatedProduct = productTypeService.updateProductTypes(product);
            ProductTypeDto productDto = new ProductTypeDto();
            productDto.setType(updatedProduct.getType());
            productDto.setIdTypeProduct(updatedProduct.getId());

            log.info("Le Type de Produit {} a ete mis à jour avec succès : {}",updatedProduct.getType(), updatedProduct.getId());
            return ResponseEntity.ok(productDto);
        } catch (Exception ex) {
            log.error("Erreur lors de la mise à jour du type de produit : {}", ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
                     "Le Type de Produit est supprimé avec succès",
                     null
             );

             resultMap = new HashMap<>();
             resultMap.put("result", customResponse);
             responseDTO.setModelsis(resultMap);
         } catch (Exception ex) {
             CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                     Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                     Constants.STATUS_VALUE_BAD_REQUEST,
                     "Erreur lors de la suppression du type de produit : " + ex.getMessage(),
                     null
             );

             Map<String, CustomResponse> resultMap = new HashMap<>();
             resultMap.put("result", errorResponse);
             responseDTO.setModelsis(resultMap);

             log.error("Erreur lors de la suppression du type de produit : {}", ex.getMessage());
         }
         log.info("Le Type de Produit {} est supprimé avec succès : {}",result.getType(), productTypeId);
         return responseDTO;
     }

    @PostMapping
    public ResponseEntity<ModelsIsResponseDTO> addProductType(@RequestBody ProductType productType) {
        try {
            ProductType newProductType = productTypeService.addProductType(productType);

            CustomResponse customResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_SUCCESS_BODY,
                    Constants.STATUS_VALUE_OK,
                    "Nouveau type de produit ajouté avec succès",
                    newProductType
            );

            resultMap = new HashMap<>();
            resultMap.put("modelsis", customResponse);

            ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
            responseDTO.setModelsis(resultMap);

            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_BAD_REQUEST_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur lors de l'ajout du type de produit : " + ex.getMessage(),
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("modelsis", errorResponse);

            ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
            responseDTO.setModelsis(resultMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
    }


}
