package com.example.demo.product;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private ProductCrudUseCase productCrudUseCase;
    public ProductController(ProductCrudUseCase productCrudUseCase){
        this.productCrudUseCase = productCrudUseCase;
    }

    @PostMapping
    public String createLabel(@RequestBody Product product){
        productCrudUseCase.save(product);
        return "success";
    }
}
