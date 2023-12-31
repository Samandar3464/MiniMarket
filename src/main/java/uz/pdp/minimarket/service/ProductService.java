package uz.pdp.minimarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.minimarket.entity.*;
import uz.pdp.minimarket.exception.RecordAlreadyExistException;
import uz.pdp.minimarket.exception.RecordNotFoundException;
import uz.pdp.minimarket.model.common.ApiResponse;
import uz.pdp.minimarket.model.request.ProductDto;
import uz.pdp.minimarket.model.response.ProductResponse;
import uz.pdp.minimarket.model.response.ProductResponseList;
import uz.pdp.minimarket.repository.CategoryRepository;
import uz.pdp.minimarket.repository.MarketRepository;
import uz.pdp.minimarket.repository.MeasurementRepository;
import uz.pdp.minimarket.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static uz.pdp.minimarket.enums.Constants.*;


@Service
@RequiredArgsConstructor
public class ProductService implements BaseService<ProductDto, Integer> {

    private final ProductRepository productRepository;
    private final MeasurementRepository measurementRepository;
    private final MarketRepository marketRepository;
    private final AttachmentService attachmentService;
    private final CategoryRepository categoryRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(ProductDto dto) {
        if (productRepository.existsByMarketIdAndMeasurementIdAndName(dto.getMarketId(), dto.getMeasurementId(), dto.getName())) {
            throw new RecordAlreadyExistException(PRODUCT_ALREADY_EXIST);
        }
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new RecordNotFoundException(CATEGORY_NOT_FOUND));
        Market market = marketRepository.findById(dto.getMarketId()).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        Measurement measurement = measurementRepository.findById(dto.getMeasurementId()).orElseThrow(() -> new RecordNotFoundException(MEASUREMENT_NOT_FOUND));
        List<Attachment> attachments = attachmentService.saveToSystemListFile(dto.getPhotos());
        Product product = Product.builder()
                .market(market)
                .measurement(measurement)
                .photos(attachments)
                .category(category)
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .deleted(false)
                .active(false)
                .createdDate(LocalDateTime.now())
                .build();
        productRepository.save(product);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Product product = productRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        return new ApiResponse(ProductResponse.from(product, attachmentService.getUrlList(product.getPhotos())), true);
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(ProductDto dto) {
        Product product = productRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        Measurement measurement = measurementRepository.findById(dto.getMeasurementId()).orElseThrow(() -> new RecordNotFoundException(MEASUREMENT_NOT_FOUND));
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new RecordNotFoundException(CATEGORY_NOT_FOUND));

        product.setCategory(category);
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setMeasurement(measurement);
        product.setDescription(dto.getDescription());
        product.setQuantity(dto.getQuantity());
        productRepository.save(product);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Product product = productRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        product.setDeleted(false);
        productRepository.save(product);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllByMarketIdForOwners(Integer page, Integer size, Integer integer) {
        Pageable page1 = PageRequest.of(page, size);
        Page<Product> productList = productRepository.findAllByMarketIdAndActiveTrueAndDeletedFalseOrderByQuantityDesc(integer, page1);
        List<ProductResponse> productResponses = new ArrayList<>();
        productList.getContent().forEach(product1 -> {
            productResponses.add(ProductResponse.from(product1, attachmentService.getUrlList(product1.getPhotos())));
        });
        ProductResponseList productResponseList = ProductResponseList.builder()
                .products(productResponses)
                .totalElement(productList.getTotalElements())
                .totalPage(productList.getTotalPages())
                .size(productList.getSize())
                .build();
        return new ApiResponse(productResponseList, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllByMarketIdForUsers(Integer page, Integer size, Integer marketId) {
        Pageable page1 = PageRequest.of(page, size);
        Page<Product> productList = productRepository.findAllByQuantityGreaterThanAndMarketIdAndActiveTrueAndDeletedFalse(0, marketId, page1);
        List<ProductResponse> productResponses = new ArrayList<>();
        productList.getContent().forEach(product1 -> {
            productResponses.add(ProductResponse.from(product1, attachmentService.getUrlList(product1.getPhotos())));
        });
        ProductResponseList productResponseList = ProductResponseList.builder()
                .products(productResponses)
                .totalElement(productList.getTotalElements())
                .totalPage(productList.getTotalPages())
                .size(productList.getSize())
                .build();
        return new ApiResponse(productResponseList, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse activateProduct(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        product.setActive(true);
        productRepository.save(product);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllNeActivatedProducts(Integer page, Integer size) {
        Pageable page1 = PageRequest.of(page, size);
        Page<Product> productList = productRepository.findAllByQuantityGreaterThanAndActiveFalseAndDeletedFalse(0, page1);
        List<ProductResponse> productResponses = new ArrayList<>();
        productList.getContent().forEach(product1 -> {
            productResponses.add(ProductResponse.from(product1, attachmentService.getUrlList(product1.getPhotos())));
        });
        return new ApiResponse(productResponses, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAll(Integer page, Integer size) {
        Pageable page1 = PageRequest.of(page, size);
        List<Market> marketList = marketRepository.findAllByActiveTrueAndDeleteFalse();
        Page<Product> productList = productRepository.findAllByQuantityGreaterThanAndMarketInAndActiveTrueAndDeletedFalseOrderByCreatedDateDesc(0, marketList, page1);
        List<ProductResponse> productResponses = new ArrayList<>();
        productList.getContent().forEach(product1 -> {
            productResponses.add(ProductResponse.from(product1, attachmentService.getUrlList(product1.getPhotos())));
        });
        ProductResponseList productResponseList = ProductResponseList.builder()
                .products(productResponses)
                .totalElement(productList.getTotalElements())
                .totalPage(productList.getTotalPages())
                .size(productList.getSize())
                .build();
        return new ApiResponse(productResponseList, true);
    }


    public ApiResponse getAllByCategory(Integer categoryId, Integer page, Integer size) {
        Pageable page1 = PageRequest.of(page, size);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RecordNotFoundException(CATEGORY_NOT_FOUND));

        List<Category> categoryList = categoryRepository.findAllByParentCategoryAndActiveTrue(category);
        Page<Product> productList = null;
        if (categoryList.isEmpty()) {
            productList = productRepository.findAllByCategoryInAndQuantityGreaterThanAndActiveTrueAndDeletedFalse(List.of(category), 0, page1);
        } else {
            categoryList.add(category);
            productList = productRepository.findAllByCategoryInAndQuantityGreaterThanAndActiveTrueAndDeletedFalse(categoryList, 0, page1);
        }
        List<ProductResponse> productResponses = new ArrayList<>();
        productList.getContent().forEach(product1 -> {
            productResponses.add(ProductResponse.from(product1, attachmentService.getUrlList(product1.getPhotos())));
        });
        return new ApiResponse(productResponses, true);
    }
}
