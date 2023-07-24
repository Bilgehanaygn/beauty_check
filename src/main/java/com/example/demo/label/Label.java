package com.example.demo.label;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Data
@AllArgsConstructor
@Table(name="_label")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="label_id_generator", sequenceName = "label_seq", allocationSize=50)
    private Long id;

    @NotNull
    private String description;

}
