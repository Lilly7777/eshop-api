package bg.courseproject.eshopapi.service;

import bg.courseproject.eshopapi.dto.AuthResponse;
import bg.courseproject.eshopapi.dto.UserDTO;
import bg.courseproject.eshopapi.entity.User;
import bg.courseproject.eshopapi.mapper.UserMapper;
import bg.courseproject.eshopapi.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final FirebaseAuth firebaseAuth;
    private final UserMapper userMapper;
    private final UserRepository userRepository;


    @Autowired
    public AuthService(FirebaseAuth firebaseAuth, UserMapper userMapper, UserRepository userRepository) {
        this.firebaseAuth = firebaseAuth;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public AuthResponse registerUser(UserDTO userDTO) throws Exception {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(userDTO.getEmail())
                .setPassword(userDTO.getPassword())
                .setDisplayName(userDTO.getFirstName() + " " + userDTO.getLastName());

        UserRecord userRecord = firebaseAuth.createUser(request);

        User userEntity = userMapper.fromDTO(userDTO);
        userEntity.setFid(userRecord.getUid());
        userRepository.save(userEntity);

        String firebaseToken = firebaseAuth.createCustomToken(userRecord.getUid());

        return new AuthResponse(firebaseToken);
    }
}
