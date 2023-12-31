package uz.pdp.minimarket.config;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.minimarket.exception.UserNotFoundException;
import uz.pdp.minimarket.repository.UserRepository;

import static uz.pdp.minimarket.enums.Constants.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new UserNotFoundException(USER_NOT_FOUND));
    }
}
