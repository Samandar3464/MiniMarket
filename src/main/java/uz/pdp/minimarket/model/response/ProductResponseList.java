package uz.pdp.minimarket.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseList {

    private List<ProductResponse> products;

    private long totalElement;
    private int totalPage;
    private int size;
}
