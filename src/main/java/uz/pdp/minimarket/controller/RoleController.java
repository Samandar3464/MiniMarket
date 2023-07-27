package uz.pdp.minimarket.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.minimarket.model.common.ApiResponse;
import uz.pdp.minimarket.model.request.RoleRequestDto;
import uz.pdp.minimarket.service.RoleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/save")
    public ApiResponse create(@RequestBody @Valid RoleRequestDto requestDto) {
        return roleService.create(requestDto);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody @Valid RoleRequestDto requestDto) {
        return roleService.update(requestDto);
    }

    @GetMapping("/getRoleByID/{id}")
    public ApiResponse getRoleByID(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    @GetMapping("/getList")
    public ApiResponse getList() {
        return roleService.getList();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse remove(@PathVariable Integer id) {
        return roleService.delete(id);
    }
}
