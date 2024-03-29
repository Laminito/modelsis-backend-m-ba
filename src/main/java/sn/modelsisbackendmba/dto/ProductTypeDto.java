package sn.modelsisbackendmba.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductTypeDto {
    public Object type;
    public String idTypeProduct;
    public Object createdDate;
    public Object lastModifiedDate;
}