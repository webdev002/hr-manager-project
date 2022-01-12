package uz.pdp.hrmanagerjwtsecurityrealproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.hrmanagerjwtsecurityrealproject.entity.Employee;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TasksDto {
    @NotNull
    private String name;

    @NotNull
    private String dateline;

    @NotNull
    private String status;

    @NotNull
    private UUID taskEmployeeId;

    @NotNull
    private String employeeEmail;
}
