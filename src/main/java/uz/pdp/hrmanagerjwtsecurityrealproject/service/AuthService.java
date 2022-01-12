package uz.pdp.hrmanagerjwtsecurityrealproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.User;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.enums.RoleName;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.ApiResponse;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.LoginDto;
import uz.pdp.hrmanagerjwtsecurityrealproject.payload.RegisterDto;
import uz.pdp.hrmanagerjwtsecurityrealproject.repository.RoleRepository;
import uz.pdp.hrmanagerjwtsecurityrealproject.repository.UserRepository;
import uz.pdp.hrmanagerjwtsecurityrealproject.security.JwtProvider;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {
      @Autowired
      UserRepository userRepository;

      @Autowired
      RoleRepository roleRepository;

      @Autowired
      @Lazy
      PasswordEncoder passwordEncoder;

      @Lazy
      @Autowired
      JavaMailSender javaMailSender;

      @Autowired
      JwtProvider jwtProvider;

      @Lazy
      @Autowired
      AuthenticationManager authenticationManager;

    public ApiResponse registerUser(RegisterDto registerDto) {

            boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
            if (existsByEmail){
                return new ApiResponse("Bunday email allaqachon mavjud",false);
            }


            User user = new User();
            user.setFirstname(registerDto.getFirstname());
            user.setLastname(registerDto.getLastname());
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_DIRECTOR)));
            user.setEmailCode(UUID.randomUUID().toString());
            userRepository.save(user);
            //USERNI EMAILIGA XABAR YUBORISH METHODINI CHAQIRAYAPMIZ
            sendEmail(user.getEmail(), user.getEmailCode());
            return  new ApiResponse("Muvaffaqqiyatli royxatdan otdingiz,emailingizga borgan kodni tasdiqlang",true);
        }


    public Boolean sendEmail(String sendEmail,String emailCode){

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("TestPdpOnline@gmail.com");
            message.setTo(sendEmail);
            message.setSubject("Akkountni tasdiqlash");
            message.setText("http://localhost:8080/api/auth/verifyEmail?emailCode="+emailCode+"&email="+sendEmail+" ");
            javaMailSender.send(message);
            return true;
        }catch (Exception e){
            return false;
        }


    }


    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("Akkount tasdiqlandi",true);
        }
        return new ApiResponse("Akkount allaqachon tasdiqlangan",false);
    }


    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            User user=(User)authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRoles());
            return new ApiResponse("Token",true,token);
        }catch (Exception e){
            return new ApiResponse("Parol yoki login xato",false);
        }


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> optionalUser = userRepository.findByEmail(username);
//        if (optionalUser.isPresent())
//            return optionalUser.get();
//        throw new UsernameNotFoundException(username+" topilmadi");
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username+" topilmadi"));
    }
}

