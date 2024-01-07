package sn.modelsisbackendmba.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.modelsisbackendmba.model.Product;
import sn.modelsisbackendmba.model.ProductType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,String > {

    Optional<Product> findByProductName(String productName);
    Product findByProductType(ProductType type);

    @Query("SELECT p FROM Product p WHERE p.active = true")
    List<Product> findAllActiveProducts();

}
