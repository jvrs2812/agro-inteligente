package com.ago.inteligente.Configuration.Security;

import com.ago.inteligente.User.Domain.UserDto;
import com.ago.inteligente.User.Repository.IAdpterUserRepository;
import com.ago.inteligente.User.Repository.UserModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {

    private final IAdpterUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserDto> dto = this.repository.findByEmail(username);

        if (dto.isEmpty())
            throw new UsernameNotFoundException("usuário não encontrado");

        return UserModelRepository.toModel(dto.get());
    }
}
