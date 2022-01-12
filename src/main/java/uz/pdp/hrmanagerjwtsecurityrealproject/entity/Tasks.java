package uz.pdp.hrmanagerjwtsecurityrealproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Tasks {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String dateline;

    @Column(nullable = false)
    private String status;

    @OneToOne(fetch = FetchType.LAZY)
    private Employee taskEmployee;

    @Column(nullable = false)
    @Email
    private String employeeEmail;
}
