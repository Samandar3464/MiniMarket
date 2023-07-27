package uz.pdp.minimarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.minimarket.model.common.ApiResponse;
import uz.pdp.minimarket.model.request.MeasurementDto;
import uz.pdp.minimarket.service.MeasurementService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/measurement")
public class MeasurementController {

    private final MeasurementService measurementService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody MeasurementDto measurementDto) {
        return measurementService.create(measurementDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return measurementService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody MeasurementDto measurementDto) {
        return measurementService.update(measurementDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return measurementService.delete(id);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll() {
        return measurementService.getAll();
    }

}
