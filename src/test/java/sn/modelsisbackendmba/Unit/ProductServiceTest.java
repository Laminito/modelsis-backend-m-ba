package sn.modelsisbackendmba.Unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.modelsisbackendmba.model.Product;
import sn.modelsisbackendmba.repository.ProductRepository;
import sn.modelsisbackendmba.service.ProductService;

import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }




    @Test
    public void testAddProduct_WithValidProduct() {
        Product product = new Product();
        // ... Initialiser les données du produit

        when(productRepository.save(product)).thenReturn(product);

        Product addedProduct = productService.addProduct(product);

        verify(productRepository, times(1)).save(product);
        assertNotNull(addedProduct);
        // ... Effectuez d'autres vérifications si nécessaire
    }

    // Ajoutez des tests similaires pour les autres méthodes du service (updateProduct, deleteProduct, getAllProducts)
}


