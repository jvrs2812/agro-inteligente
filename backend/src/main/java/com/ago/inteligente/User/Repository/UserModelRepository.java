package com.ago.inteligente.User.Repository;

import com.ago.inteligente.User.Domain.UserRegisterDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "user_table")
public class UserModelRepository {

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

    public String getPassword() {
        return password;
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

    static UserModelRepository toModel(UserRegisterDto register){
        UserModelRepository userModel = new UserModelRepository();
        userModel.id = UUID.randomUUID();
        userModel.cpf = register.getCpf();
        userModel.email = register.getEmail();
        userModel.password = register.getPassword();

        return userModel;
    }

}
