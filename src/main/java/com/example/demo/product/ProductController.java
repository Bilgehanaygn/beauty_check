package com.example.demo.product;

import com.example.demo.sms.TwilioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private ProductCrudUseCase productCrudUseCase;
    private TwilioService twilioService;
    public ProductController(ProductCrudUseCase productCrudUseCase, TwilioService twilioService){
        this.productCrudUseCase = productCrudUseCase;
        this.twilioService = twilioService;
    }


    @GetMapping
    public String test(){
        return twilioService.showEnvironmentVariable("isim");
    }

    @PostMapping
    public String createLabel(@RequestBody Product product){
        productCrudUseCase.save(product);
        return "success";
    }
}
