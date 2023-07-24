package com.example.demo.label;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/label")
public class LabelController {

    private LabelCrudUseCase labelCrudUseCase;

    public LabelController(LabelCrudUseCase labelCrudUseCase){
        this.labelCrudUseCase = labelCrudUseCase;
    }

    @PostMapping
    public String createLabel(@RequestBody Label label){
        labelCrudUseCase.save(label);
        return "success";
    }
}
