package uz.pdp.minimarket.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.minimarket.entity.FireBaseToken;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsToken {

    private String message;

    @JsonProperty("data")
    private FireBaseToken data;

    private String tokenType;
}
