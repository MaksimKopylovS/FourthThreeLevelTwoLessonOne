package max_sk.HomeWork.services;


import lombok.RequiredArgsConstructor;
import max_sk.HomeWork.dto.UserDTO;
import max_sk.HomeWork.model.Role;
import max_sk.HomeWork.model.User;
import max_sk.HomeWork.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Transactional
    public void registrationUser(UserDTO userDTO){
        entityManager.createNativeQuery("insert into users(USERNAME, PASSWORD, EMAIL) values(:a,:b,:c)")
                .setParameter("a", userDTO.getUserName() )
                .setParameter("b", userDTO.getRegPassword())
                .setParameter("c", userDTO.getMail())
                .executeUpdate();

    }
}