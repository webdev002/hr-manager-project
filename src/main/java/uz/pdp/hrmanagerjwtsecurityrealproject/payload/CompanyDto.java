package uz.pdp.hrmanagerjwtsecurityrealproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull
    private String name;

    @NotNull
    private String director;

}
