package uz.pdp.minimarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.minimarket.entity.Role;
import uz.pdp.minimarket.exception.RecordAlreadyExistException;
import uz.pdp.minimarket.exception.RecordNotFoundException;
import uz.pdp.minimarket.model.common.ApiResponse;
import uz.pdp.minimarket.model.request.RoleRequestDto;
import uz.pdp.minimarket.repository.RoleRepository;

import java.util.List;

import static uz.pdp.minimarket.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class RoleService implements BaseService<RoleRequestDto, Integer> {

    private final RoleRepository roleRepository;

    @Override
    public ApiResponse create(RoleRequestDto requestDto) {
        if (roleRepository.findByName(requestDto.getName()).isPresent()) {
            throw new RecordAlreadyExistException(ROLE_ALREADY_EXIST);
        }
        Role role = new Role(requestDto.getName());
        roleRepository.save(role);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse getById(Integer id) {
        return new ApiResponse(roleRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ROLE_NOT_FOUND)), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(RoleRequestDto dto) {
        if (roleRepository.findByName(dto.getName()).isPresent()) {
            throw new RecordAlreadyExistException(ROLE_ALREADY_EXIST);
        }
        Role role = roleRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(ROLE_NOT_FOUND));
        role.setName(dto.getName());
        return new ApiResponse(roleRepository.save(role), true);
    }

    @Override
    public ApiResponse delete(Integer id) {
//        Role role = roleRepository.findById(id)
//                .orElseThrow(() -> new RecordNotFoundException(ROLE_NOT_AVAILABLE));
//        roleRepository.save(role);
//        return new ApiResponse(SUCCESSFULLY, true);
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getList() {
        List<Role> roles = roleRepository.findAll();
        return new ApiResponse(roles, true);
    }
}
