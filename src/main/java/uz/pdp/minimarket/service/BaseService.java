package uz.pdp.minimarket.service;


import uz.pdp.minimarket.model.common.ApiResponse;

public interface BaseService <T ,R> {

    ApiResponse create(T t);

    ApiResponse getById(R r);

    ApiResponse update(T t);

    ApiResponse delete(R r);
}
