package com.products.MSproducts.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.products.MSproducts.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {
    private long id;

    private String name;

    @JsonIgnore
    private Set<Product> products = new HashSet<>();
}
