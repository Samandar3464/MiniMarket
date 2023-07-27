package uz.pdp.minimarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.minimarket.entity.Market;
import uz.pdp.minimarket.entity.Product;
import uz.pdp.minimarket.entity.SoldProduct;
import uz.pdp.minimarket.entity.User;
import uz.pdp.minimarket.exception.RecordNotFoundException;
import uz.pdp.minimarket.exception.UserNotFoundException;
import uz.pdp.minimarket.model.common.ApiResponse;
import uz.pdp.minimarket.model.request.Dto;
import uz.pdp.minimarket.model.request.SoldProductDto;
import uz.pdp.minimarket.repository.MarketRepository;
import uz.pdp.minimarket.repository.ProductRepository;
import uz.pdp.minimarket.repository.SoldProductRepository;
import uz.pdp.minimarket.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import static uz.pdp.minimarket.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class SoldProductService implements BaseService<SoldProductDto, UUID> {

    private final SoldProductRepository soldProductRepository;
    private final ProductRepository productRepository;
    private final MarketRepository marketRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse create(SoldProductDto dto) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        Market market = marketRepository.findById(dto.getMarketId()).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        User user = userRepository.findById(dto.getSoldUserId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        if (product.getQuantity() < dto.getQuantity()) {
            throw new RecordNotFoundException(NOT_ENOUGHT_QUENTITY);
        }
        product.setQuantity(product.getQuantity() - dto.getQuantity());
        SoldProduct soldProduct = SoldProduct.builder()
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .soldDate(LocalDateTime.now())
                .product(product)
                .market(market)
                .soldUser(user)
                .build();
        productRepository.save(product);
        soldProductRepository.save(soldProduct);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse getById(UUID uuid) {
        SoldProduct soldProduct = soldProductRepository.findById(uuid).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        return new ApiResponse(soldProduct, true);
    }

    @Override
    public ApiResponse update(SoldProductDto dto) {
        SoldProduct soldProduct = soldProductRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        Market market = marketRepository.findById(dto.getMarketId()).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        User user = userRepository.findById(dto.getSoldUserId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RecordNotFoundException(PRODUCT_NOT_FOUND));
        if (soldProduct.getProduct().getId().equals(dto.getProductId())) {

            if (soldProduct.getQuantity() < dto.getQuantity()) {
                double v = dto.getQuantity() - soldProduct.getQuantity();
                if (v > product.getQuantity()) {
                    throw new RecordNotFoundException(NOT_ENOUGHT_QUENTITY);
                } else {
                    product.setQuantity(product.getQuantity() - v);
                }
            } else if (soldProduct.getQuantity() > dto.getQuantity()) {
                double v = soldProduct.getQuantity() - dto.getQuantity();
                product.setQuantity(product.getQuantity() + v);
            }

        } else {
            if (product.getQuantity() < dto.getQuantity()) {
                throw new RecordNotFoundException(NOT_ENOUGHT_QUENTITY);
            }
            product.setQuantity(product.getQuantity() - dto.getQuantity());

        }
        soldProduct.setProduct(product);
        soldProduct.setMarket(market);
        soldProduct.setSoldUser(user);
        productRepository.save(product);
        soldProductRepository.save(soldProduct);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse delete(UUID uuid) {
        return null;
    }

    public ApiResponse getAllByMarketId(Dto dto) {
        Pageable page = PageRequest.of(dto.getPage(), dto.getSize());
        Page<SoldProduct> soldProducts = soldProductRepository.findAllByMarketIdAndSoldDateBetween(dto.getId(), dto.getStartDate(), dto.getEndDate(), page);
        return new ApiResponse(soldProducts, true);
    }


    public ApiResponse getAllByMarketIdAndSorted(Dto dto) {
        Pageable page = PageRequest.of(dto.getPage(), dto.getSize());
        Page<SoldProduct> soldProducts = soldProductRepository.getAll(dto.getId(), dto.getStartDate(), dto.getEndDate(), page);

//        List<SoldProductTotalResponse> totalResponses = new ArrayList<>();
//        soldProducts.forEach(soldProduct -> {
//
//            totalResponses.add(
//                SoldProductTotalResponse.from(productRepository.
//                        findById(soldProduct.getProduct().getId()).orElseThrow(()-> new RecordNotFoundException(PRODUCT_NOT_FOUND)),
//                        soldProduct.getPrice(),
//                        soldProduct.getQuantity()));});
        return new ApiResponse(soldProducts, true);
    }


}
