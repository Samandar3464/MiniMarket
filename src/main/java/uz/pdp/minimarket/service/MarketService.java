package uz.pdp.minimarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.minimarket.entity.Market;
import uz.pdp.minimarket.entity.User;
import uz.pdp.minimarket.exception.RecordNotFoundException;
import uz.pdp.minimarket.exception.UserNotFoundException;
import uz.pdp.minimarket.model.common.ApiResponse;
import uz.pdp.minimarket.model.request.MarketDto;
import uz.pdp.minimarket.model.response.BranchResponseListForAdmin;
import uz.pdp.minimarket.repository.MarketRepository;
import uz.pdp.minimarket.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static uz.pdp.minimarket.enums.Constants.*;


@RequiredArgsConstructor
@Service
public class MarketService implements BaseService<MarketDto, Integer> {

    private final MarketRepository marketRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(MarketDto branch) {
        Optional<Market> byName = marketRepository.findByName(branch.getName());
        if (byName.isPresent()) {
            throw new RecordNotFoundException(MARKET_NAME_ALREADY_EXIST);
        }
        Market marketNew = Market.from(branch);
        marketRepository.save(marketNew);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Market market = marketRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        return new ApiResponse(market, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(MarketDto dto) {
        Market market = marketRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setName(market.getName());
        market.setActive(dto.isActive());
        market.setLongitude(dto.getLongitude());
        market.setLatitude(dto.getLatitude());
        marketRepository.save(market);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Market market = marketRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setDelete(true);
        market.setActive(false);
        marketRepository.save(market);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getByUserId(Integer integer) {
        Market market = marketRepository.findByIdAndDeleteFalse(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        return new ApiResponse(market, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Market> all = marketRepository.findAllByDeleteFalse(pageable);
        return new ApiResponse(new BranchResponseListForAdmin(
                all.getContent(), all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }

    public ApiResponse deActive(Integer integer) {
        Market market = marketRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setActive(false);
        marketRepository.save(market);
        return new ApiResponse(market, true);
    }

    public ApiResponse activate(Integer integer, LocalDate newActiveDay) {
        Market market = marketRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setActive(true);
        market.setActiveDay(newActiveDay);
        marketRepository.save(market);
        return new ApiResponse(market, true);
    }
}
