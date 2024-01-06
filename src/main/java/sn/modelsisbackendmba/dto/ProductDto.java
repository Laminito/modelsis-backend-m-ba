package sn.modelsisbackendmba.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto{
    public String idProduct;
    public Object type;
    public String name;
    public Object createdDate;
    public Object lastModifiedDate;
}




