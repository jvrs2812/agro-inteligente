package com.agro.inteligente.Configuration.Security;

import com.agro.inteligente.User.Domain.UserDto;
import com.agro.inteligente.User.Repository.Adapters.IAdapterUserRepository;
import com.agro.inteligente.User.Repository.Models.UserModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {

    private final IAdapterUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserDto> dto = this.repository.findByEmail(username);

        if (dto.isEmpty())
            throw new UsernameNotFoundException("usuário não encontrado");

        return UserModelRepository.toModel(dto.get());
    }
}
