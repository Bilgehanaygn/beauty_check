package com.example.demo.token;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="token_id_generator", sequenceName = "_token_seq", allocationSize=50)
    public Integer id;

    public String token;

    public boolean revoked;
    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    public User user;
}
