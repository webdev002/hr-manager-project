package uz.pdp.hrmanagerjwtsecurityrealproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RegisterDto {
    @NotNull
    @Size(min = 3,max = 50)
    private String firstname;

    @NotNull
    @Size(min = 3,max = 50)
    private String lastname;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

}
