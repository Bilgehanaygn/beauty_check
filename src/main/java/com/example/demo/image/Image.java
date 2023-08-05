package com.example.demo.image;


import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.jetbrains.annotations.NotNull;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name="_image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="image_id_generator", sequenceName = "_image_seq", allocationSize=50)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User user;

    @NotNull
    private String name;

    //Reviewer should be added

    @Enumerated(EnumType.ORDINAL)
    private Point point;

    @Enumerated(EnumType.STRING)
    private Description description;


    public Image(){}

}
