package uz.pdp.minimarket.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.minimarket.model.common.ApiResponse;
import uz.pdp.minimarket.model.request.CategoryDto;
import uz.pdp.minimarket.service.CategoryService;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @GetMapping("/getAllByParentId/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return categoryService.getById(id);
    }

    @GetMapping("/getAllParentCategory")
    public ApiResponse getAllParentCategory() {
        return categoryService.getAllParentCategory();
    }


    @PutMapping("/update")
    public ApiResponse update(@RequestBody  @Valid CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return categoryService.delete(id);
    }

}
