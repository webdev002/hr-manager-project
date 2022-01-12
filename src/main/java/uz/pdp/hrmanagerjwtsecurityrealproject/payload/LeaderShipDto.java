package uz.pdp.hrmanagerjwtsecurityrealproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Employee;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Tasks;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderShipDto {

    @NotNull
    private UUID employee;

    @NotNull
    private double employeeSalary;

    @NotNull
    private String workTime;

    @NotNull
    private Integer tasks;
}
