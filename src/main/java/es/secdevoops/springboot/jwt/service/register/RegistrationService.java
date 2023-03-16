package es.secdevoops.springboot.jwt.service.register;

import es.secdevoops.springboot.jwt.dto.AuthenticationResponse;
import es.secdevoops.springboot.jwt.dto.RegisterRequest;
import es.secdevoops.springboot.jwt.entities.Role;
import es.secdevoops.springboot.jwt.entities.UserAccount;
import es.secdevoops.springboot.jwt.repository.RoleRepository;
import es.secdevoops.springboot.jwt.repository.UserAccountRepository;
import es.secdevoops.springboot.jwt.service.auth.JwtService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;
    private final JwtService jwtService;

    public AuthenticationResponse registerUser(RegisterRequest request) {
        var user = new UserAccount(request.getEmail(), passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(roleRepository.findByRolename(Role.USER_ROLE)));
        var savedUser = userAccountRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        var user = new UserAccount(request.getEmail(), passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(roleRepository.findByRolename(Role.ADMIN_ROLE)));
        var savedUser = userAccountRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
