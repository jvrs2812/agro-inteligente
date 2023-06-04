package com.ago.inteligente.User.Repository;

import com.ago.inteligente.User.Domain.UserDto;
import com.ago.inteligente.User.Domain.UserRegisterDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "user_table")
public class UserModelRepository implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",  updatable = false, unique = true, nullable = false)
    private UUID id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String cpf;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    static UserModelRepository toModelRegister(UserRegisterDto register){
        UserModelRepository userModel = new UserModelRepository();
        userModel.id = UUID.randomUUID();
        userModel.cpf = register.getCpf();
        userModel.email = register.getEmail();
        userModel.password = register.getPassword();

        return userModel;
    }

    public static UserModelRepository toModel(UserDto user){
        UserModelRepository userModel = new UserModelRepository();
        userModel.id = user.getId();
        userModel.cpf = user.getCpf();
        userModel.email = user.getEmail();
        userModel.password = user.getPassword();

        return userModel;
    }
    UserDto toDomain(){
        return UserDto.builder().
                cpf(this.cpf)
                .email(this.email)
                .password(this.password)
                .build();
    }

}
