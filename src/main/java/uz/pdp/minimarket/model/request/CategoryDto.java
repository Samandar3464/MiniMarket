package uz.pdp.minimarket.model.request;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {


    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Integer parentCategoryId;
}
