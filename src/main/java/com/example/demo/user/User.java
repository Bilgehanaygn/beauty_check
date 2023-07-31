package com.example.demo.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;


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

    public User(){
        this.phoneNum = "";
    }


    public UserViewModel entityToViewModel(){
        return new UserViewModel(this.getPhoneNum(), this.getName(), this.getAge());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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