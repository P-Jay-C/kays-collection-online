package edu.tcu.cs.kayscollectiononline.User;
import edu.tcu.cs.kayscollectiononline.system.exception.ObjectNotFoundException;
import edu.tcu.cs.kayscollectiononline.wizard.Wizard;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public AppUser findById(Long id){
        return  userRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("user",id)
        );
    }

    public List<AppUser> findAll(){
        return userRepository.findAll();
    }

    public AppUser save(AppUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public AppUser update(Long id, AppUser update) {
        return userRepository.findById(id)
                .map(oldWizard -> {
                    oldWizard.setUsername(update.getUsername());
                    oldWizard.setEnabled(update.isEnabled());
                    oldWizard.setRoles(update.getRoles());

                    return this.userRepository.save(oldWizard);
                })
                .orElseThrow(()-> new ObjectNotFoundException("user",id));

    }

    public void delete(Long id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("user",id));

        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(MyUserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("username" + username + "is not found"));

    }
}
