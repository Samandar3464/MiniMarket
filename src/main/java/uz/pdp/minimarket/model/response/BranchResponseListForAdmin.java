package uz.pdp.minimarket.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.minimarket.entity.Market;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchResponseListForAdmin {

    private List<Market> marketResponseDtoList;
    private long allSize;
    private int allPage;
    private int currentPage;
}
