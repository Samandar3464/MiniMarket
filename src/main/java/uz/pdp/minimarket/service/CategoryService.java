package uz.pdp.minimarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.minimarket.entity.Category;
import uz.pdp.minimarket.exception.RecordAlreadyExistException;
import uz.pdp.minimarket.model.common.ApiResponse;
import uz.pdp.minimarket.model.request.CategoryDto;
import uz.pdp.minimarket.repository.CategoryRepository;

import static uz.pdp.minimarket.enums.Constants.*;

@RequiredArgsConstructor
@Service
public class CategoryService implements BaseService<CategoryDto, Integer> {

    private final CategoryRepository categoryRepository;


    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(CategoryDto dto) {
        if (categoryRepository.existsByNameAndParentCategoryIdAndActiveTrue(dto.getName(), dto.getParentCategoryId())) {
            throw new RecordAlreadyExistException(CATEGORY_ALREADY_EXIST);
        }
        Category category = null;
        if (dto.getParentCategoryId()!=null){
            category = categoryRepository.findById(dto.getParentCategoryId()).orElseThrow(() -> new RecordAlreadyExistException(CATEGORY_NOT_FOUND));

        }
        Category category1 = Category.builder()
                .name(dto.getName())
                .parentCategory(category)
                .active(true)
                .build();
        categoryRepository.save(category1);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer parentId) {
        return new ApiResponse(categoryRepository.findAllByParentCategoryIdAndActiveTrue(parentId), true);
    }
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllParentCategory() {
        return new ApiResponse(categoryRepository.findAllByParentCategoryAndActiveTrue(null), true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(CategoryDto dto) {
        Category category = categoryRepository.findById(dto.getId()).orElseThrow(() -> new RecordAlreadyExistException(CATEGORY_NOT_FOUND));
        Category categoryParent = categoryRepository.findById(dto.getParentCategoryId()).orElseThrow(() -> new RecordAlreadyExistException(CATEGORY_NOT_FOUND));
        category.setParentCategory(categoryParent);
        category.setName(dto.getName());
        categoryRepository.save(category);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Category category = categoryRepository.findById(integer).orElseThrow(() -> new RecordAlreadyExistException(CATEGORY_NOT_FOUND));
        category.setActive(false);
        categoryRepository.save(category);
        return new ApiResponse(DELETED, true);
    }
}
