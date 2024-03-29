package sn.modelsisbackendmba.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.modelsisbackendmba.model.ProductType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType,String> {

    @Query("SELECT pt FROM ProductType pt WHERE pt.active = true")
    List<ProductType> findAllActiveProductsType();

    Optional<ProductType> findByType(String type);

}
