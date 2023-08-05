package com.example.demo.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@Table(name="_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="user_id_generator", sequenceName = "_user_seq", allocationSize=50)
    private Long id;

    @NotNull
    private String phoneNum;

    private String name;

    private Integer age;

    private String otp;

    private Date otpRequestedTime;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    public User(){
        this.phoneNum = "";
    }


    public UserViewModel entityToViewModel(){
        return new UserViewModel(this.getPhoneNum(), this.getName(), this.getAge(), this.role.name());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(this.role.getAuthority()));
    }

    @Override
    public String getPassword() {
        return otp;
    }

    @Override
    public String getUsername() {
        return phoneNum;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
