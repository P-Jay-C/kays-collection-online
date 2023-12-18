package edu.tcu.cs.kayscollectiononline.User;
import edu.tcu.cs.kayscollectiononline.system.exception.ObjectNotFoundException;
import edu.tcu.cs.kayscollectiononline.wizard.Wizard;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

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
}
