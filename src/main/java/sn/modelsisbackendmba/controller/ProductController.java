package sn.modelsisbackendmba.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import sn.modelsisbackendmba.dto.ModelsIsResponseDTO;
import sn.modelsisbackendmba.dto.ProductDto;
import sn.modelsisbackendmba.model.Product;
import sn.modelsisbackendmba.repository.ProductRepository;
import sn.modelsisbackendmba.response.CustomResponse;
import sn.modelsisbackendmba.response.ResponseFactory;
import sn.modelsisbackendmba.service.ProductService;
import sn.modelsisbackendmba.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@Slf4j
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    //Pour les Tests
    @Autowired
    private ProductRepository productRepository;

    private Map<String, CustomResponse> resultMap;
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public ModelsIsResponseDTO getAllProducts() {
        ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
        try {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty() || products == null){
                CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                        Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                        Constants.STATUS_VALUE_BAD_REQUEST,
                        "La liste des produits et soit vide ou null ",
                        products
                );

                resultMap = new HashMap<>();
                resultMap.put("result", errorResponse);
                responseDTO.setModelsis(resultMap);
                log.error("La liste des produits et soit vide ou null : {} ",responseDTO.getModelsis().get(responseDTO).getData());
                return responseDTO;

            }

            List<ProductDto> productDtos = new ArrayList<>();
            for (Product product : products) {
                ProductDto productDto = new ProductDto();
                productDto.setIdProduct(product.getId());
                productDto.setName(product.getProductName());
                if (product.getProductType() != null) {
                    productDto.setType(product.getProductType().getType());
                }
                productDto.setCreatedDate(product.getCreatedDate());
                productDtos.add(productDto);
            }

            CustomResponse customResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_SUCCESS_BODY,
                    Constants.STATUS_VALUE_OK,
                    "Récupération de la liste des produits réussie avec succès",
                    productDtos
            );
            resultMap = new HashMap<>();
            resultMap.put("result", customResponse);
            log.info("Récupération de la liste des produits réussie avec succès");
            responseDTO.setModelsis(resultMap);
        }
        catch (DataAccessException ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur de base de données lors de la récupération des produits : " + ex.getMessage(),
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur de base de données lors de la récupération des produits ");
        }
        catch (Exception ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Une erreur s'est produite lors de la récupération de la liste des produits ",
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Une erreur s'est produite lors de la récupération de la liste des produits : " + ex.toString());
        }
        return responseDTO;
    }

    @GetMapping("/{productId}")
    public ModelsIsResponseDTO getProductById(@PathVariable String productId) {
        ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
        try {
            Product product = productService.getProductById(productId);

            if (product.equals(null)) {
                CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                        Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                        Constants.STATUS_VALUE_BAD_REQUEST,
                        "Aucun produit trouvé avec l'ID : " + productId,
                        product
                );
                resultMap = new HashMap<>();
                resultMap.put("result", errorResponse);
                responseDTO.setModelsis(resultMap);
                log.error("Aucun produit trouvé avec l'ID : " + productId);
                return  responseDTO;
            }
                ProductDto productDto = new ProductDto();
                productDto.setIdProduct(product.getId());
                productDto.setName(product.getProductName());
                if (product.getProductType() != null) {
                    productDto.setType(product.getProductType().getType());
                }
                productDto.setCreatedDate(product.getCreatedDate());

                CustomResponse customResponse = ResponseFactory.createCustomResponse(
                        Constants.STATUS_MESSAGE_SUCCESS_BODY,
                        Constants.STATUS_VALUE_OK,
                        "Récupération du produit réussie avec succès",
                        productDto
                );

                resultMap = new HashMap<>();
                resultMap.put("result", customResponse);
                responseDTO.setModelsis(resultMap);
                log.info("Récupération du produit réussie avec succès");

        } catch (DataAccessException ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur de base de données lors de la récupération du produit : " + ex.getMessage(),
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur de base de données lors de la récupération du produit : " + ex.getMessage());
        } catch (Exception ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Une erreur s'est produite lors de la récupération du produit : " + ex.getMessage(),
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Une erreur s'est produite lors de la récupération du produit : " + ex.getMessage());
        }
        return responseDTO;
    }

    @PostMapping
    public ModelsIsResponseDTO addProduct(@RequestBody Product product) {
        ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
        try {
            Product newProduct = productService.addProduct(product);
            if (newProduct == null){
                CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                        Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                        Constants.STATUS_VALUE_BAD_REQUEST,
                        "L'ajout d'un nouveau produit à echoué ",
                        null
                );

                resultMap = new HashMap<>();
                resultMap.put("result", errorResponse);
                responseDTO.setModelsis(resultMap);
                log.error("L'ajout d'un nouveau produit à echoué");
                return responseDTO;

            }

            ProductDto productDto = new ProductDto();
            productDto.setIdProduct(newProduct.getId());
            productDto.setName(newProduct.getProductName());
            productDto.setType(newProduct.getProductType().getType());
            productDto.setCreatedDate(newProduct.getCreatedDate());
            productDto.setLastModifiedDate(newProduct.getLastModifiedDate());

            CustomResponse customResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_SUCCESS_BODY,
                    Constants.STATUS_VALUE_OK,
                    "Produit ajouté avec succès",
                    productDto
            );

            resultMap = new HashMap<>();
            resultMap.put("result", customResponse);
            responseDTO.setModelsis(resultMap);

            log.info("Produit ajouté avec succès : {}", newProduct.toString());
        }
        catch (DataAccessException ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur de base de données lors de l'ajout d'un nouveau produit : " + ex.getMessage(),
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur de base de données lors de l'ajout d'un nouveau produit");
        }
        catch (Exception ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur lors de l'ajout d'un nouveau produit " ,
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);

            log.error("Erreur lors de l'ajout du produit : {}", ex.toString());
        }
        return responseDTO;
    }

    @PutMapping
    public ModelsIsResponseDTO updateProduct(@RequestBody Product product) {
        ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
        try {
            Product updatedProduct = productService.updateProduct(product);
            if(updatedProduct == null){
                CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                        Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                        Constants.STATUS_VALUE_BAD_REQUEST,
                        "La mise à du produit à echoué  ",
                        null
                );

                resultMap = new HashMap<>();
                resultMap.put("result", errorResponse);
                responseDTO.setModelsis(resultMap);
                log.error("La mise à jour du produit à echoué: {} ",responseDTO.getModelsis().get(responseDTO).getData());
                return responseDTO;
            }
            ProductDto productDto = new ProductDto();
            productDto.setIdProduct(updatedProduct.getId());
            productDto.setName(updatedProduct.getProductName());
            productDto.setType(updatedProduct.getProductType().getType());
            productDto.setLastModifiedDate(updatedProduct.getLastModifiedDate());

            log.info("Produit mis à jour avec succès : {}", updatedProduct.toString());

            CustomResponse customResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_SUCCESS_BODY,
                    Constants.STATUS_VALUE_OK,
                    "Mise à jour du produit réussie avec succès",
                    productDto
            );
            resultMap = new HashMap<>();
            resultMap.put("result", customResponse);
            responseDTO.setModelsis(resultMap);
            log.info("Mise à jour du produit réussie avec succès");
        } catch (DataAccessException ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur de base de données lors de la mise à jour du produit : " + ex.toString(),
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur de base de données lors de la mise à jour du produit : " + ex.toString());
        } catch (Exception ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Une erreur s'est produite lors de la mise à jour du produit : " + ex.toString(),
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Une erreur s'est produite lors de la mise à jour du produit : " + ex.toString());
        }
        return responseDTO;
    }

    @DeleteMapping("/{productId}")
    public ModelsIsResponseDTO deleteProduct(@PathVariable String productId) {
        ModelsIsResponseDTO responseDTO = new ModelsIsResponseDTO();
        try {
            productService.deleteProduct(productId);
            CustomResponse customResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_SUCCESS_BODY,
                    Constants.STATUS_VALUE_OK,
                    "Produit supprimé avec succès",
                    null
            );
           resultMap = new HashMap<>();
            resultMap.put("result", customResponse);
            responseDTO.setModelsis(resultMap);
            log.info("Produit supprimé avec succès : {}", productId);
        }
        catch (DataAccessException ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur de base de données lors de la suppression d'un nouveau produit ",
                    null
            );

            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur de base de données lors de la suppression d'un nouveau produit ");
        }
        catch (Exception ex) {
            CustomResponse errorResponse = ResponseFactory.createCustomResponse(
                    Constants.STATUS_MESSAGE_NOT_FOUND_BODY,
                    Constants.STATUS_VALUE_BAD_REQUEST,
                    "Erreur lors de la suppression du produit : " + ex.toString(),
                    null
            );
            resultMap = new HashMap<>();
            resultMap.put("result", errorResponse);
            responseDTO.setModelsis(resultMap);
            log.error("Erreur lors de la suppression du produit : {}", ex.toString());
        }
        return responseDTO;
    }

}