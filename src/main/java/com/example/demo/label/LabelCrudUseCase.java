package com.example.demo.label;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelCrudUseCase {

    @Autowired
    private LabelRepository labelRepository;

    public void save(Label label){
        labelRepository.save(label);
    }
}
