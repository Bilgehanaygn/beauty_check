package com.example.demo.product;

import com.example.demo.label.Label;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name="_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="product_id_generator", sequenceName = "product_seq", allocationSize=50)
    private Long id;

    @NotNull
    public String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="product_attach_label", joinColumns = @JoinColumn(name="product_id"), inverseJoinColumns = @JoinColumn(name="label_id"))
    public List<Label> labels;
}
