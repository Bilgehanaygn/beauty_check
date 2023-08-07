package com.example.demo.image;


import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
    @JoinColumn(name="user_id", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private User user;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name="reviewer_id", nullable = true)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private User reviewer;

    @Enumerated(EnumType.STRING)
    private Point point;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Tag.class, fetch = FetchType.EAGER)
    @CollectionTable(name="_image_tags", joinColumns = @JoinColumn(name="image_id"))
    @Column(name = "tag")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Tag> tags;

    @Enumerated(EnumType.ORDINAL)
    private Status status;


    public Image(){}

}














