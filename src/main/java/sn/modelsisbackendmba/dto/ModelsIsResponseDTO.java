package sn.modelsisbackendmba.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.modelsisbackendmba.response.CustomResponse;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelsIsResponseDTO {
    private Map<String, CustomResponse> modelsis;

}
