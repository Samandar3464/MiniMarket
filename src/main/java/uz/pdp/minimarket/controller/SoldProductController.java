package uz.pdp.minimarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.minimarket.model.common.ApiResponse;
import uz.pdp.minimarket.model.request.Dto;
import uz.pdp.minimarket.model.request.SoldProductDto;
import uz.pdp.minimarket.service.SoldProductService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/soldProduct")
public class SoldProductController {

    private final SoldProductService soldProductService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody SoldProductDto soldProductDto) {
        return soldProductService.create(soldProductDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable UUID id) {
        return soldProductService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody SoldProductDto soldProductDto) {
        return soldProductService.update(soldProductDto);
    }

    @PostMapping("/getAllByMarketId")
    public ApiResponse getAllByMarketIdForOwners(@RequestBody Dto dto) {
        return soldProductService.getAllByMarketId(dto);
    }
    @PostMapping("/getAllByMarketIdAndSort")
    public ApiResponse getAllByMarketIdAndSort(@RequestBody Dto dto) {
        return soldProductService.getAllByMarketIdAndSorted(dto);
    }
}
