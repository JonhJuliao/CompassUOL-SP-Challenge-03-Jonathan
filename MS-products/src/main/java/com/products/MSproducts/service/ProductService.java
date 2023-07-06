package com.products.MSproducts.service;

import com.products.MSproducts.payload.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    ProductDto getProductById(long id);

    List<ProductDto> getAllProducts(
            Integer pageNo ,
            Integer pageSize,
            String orderBy,
            String Direction);

    void deleteProductById(long id);

    ProductDto updateProduct(ProductDto productDto, long id);
}
