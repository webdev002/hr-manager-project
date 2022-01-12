package uz.pdp.hrmanagerjwtsecurityrealproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class LoginDto {
    @NotNull
    @Email
    private String username;

    @NotNull
    private String password;
}
