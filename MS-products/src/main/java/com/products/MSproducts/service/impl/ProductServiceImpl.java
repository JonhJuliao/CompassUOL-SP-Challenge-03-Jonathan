package com.products.MSproducts.service.impl;

import com.products.MSproducts.entity.Category;
import com.products.MSproducts.entity.Product;
import com.products.MSproducts.exception.ResourceNotFoundException;
import com.products.MSproducts.payload.ProductDto;
import com.products.MSproducts.repository.CategoryRepository;
import com.products.MSproducts.repository.ProductRepository;
import com.products.MSproducts.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ModelMapper mapper;

    private CategoryRepository categoryRepository;


    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        var product = mapToEntity(productDto);
        Set<Category> categories = new HashSet<>();
        for (Long categoryId : product.getCategory()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
            categories.add(category);
        }
        product.setCategories(categories);
        if(product.getImgUrl() == null){
            product.setImgUrl("");
        }
        var savedProduct = productRepository.save(product);
        return mapToDto(savedProduct);
    }

    @Override
    public ProductDto getProductById(long id) {
        var product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "id", id));
        return mapToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts(
            Integer pageNo,
            Integer pageSize,
            String orderBy,
            String direction) {
        var sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        List<Product> products = productRepository.findAll();

        List<ProductDto> productDtos = products.stream()
                .map(product -> mapToDto(product))
                .collect(Collectors.toList());
        return productDtos;
    }

    @Override
    public void deleteProductById(long id) {
        var product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "id", id));
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, long id) {
        var product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "id", id));

        if(productDto.getDate() != null){
            product.setDate(productDto.getDate());
        }
        if(productDto.getDescription() != null){
            product.setDescription(productDto.getDescription());
        }
        if(productDto.getName() != null){
            product.setName(productDto.getName());
        }
        if(productDto.getImgUrl() != null){
            product.setImgUrl(productDto.getImgUrl());
        }
        if(productDto.getPrice() != null){
            product.setPrice(productDto.getPrice());
        }
        if(productDto.getCategories() != null){
            Set<Category> categories = new HashSet<>();
            for (Long categoryId : productDto.getCategory()) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
                categories.add(category);
            }
            product.setCategories(categories);
        }

        var updatedProduct = productRepository.save(product);

        return mapToDto(updatedProduct);
    }

    private ProductDto mapToDto(Product product){
        var productDto = mapper.map(product, ProductDto.class);
        return productDto;
    }

    private Product mapToEntity(ProductDto productDto){
        var product = mapper.map(productDto, Product.class);
        return product;
    }
}
