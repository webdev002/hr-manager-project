package uz.pdp.hrmanagerjwtsecurityrealproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class LeaderShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    private Set<Employee> employee;

    @Column(nullable = false)
    private double employeeSalary;

    @Column(nullable = false)
    private String workTime;

    @OneToMany
    private Set<Tasks> tasks;


}
