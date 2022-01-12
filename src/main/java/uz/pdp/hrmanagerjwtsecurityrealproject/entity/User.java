package uz.pdp.hrmanagerjwtsecurityrealproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Component
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;


    @Column(nullable = false)
    private String email;//userning emaili(USERNAME SIFATIDA KETADI)

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Timestamp createdAt;//royxatdan otganligi


    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedAt;//oxirgi marta qachon tahrirlanganligi


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private String emailCode;




    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private  boolean enabled;




    //USERNING HUQUQLARI ROYXATI
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    //USERNI USENAMENINI QAYTAROVCHI METHOD
    @Override
    public String getUsername() {
        return email;
    }

    //ACCOUNTNI AMAL QILISH MUDDATI QAYTARADI
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    //ACCOUNTNI BLOKLANGANLIGINI AYTARADI
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    //ACCOUNTNI ISHONCHLILIK MUDDATI TUGAGAN YOKI TUGAMAGANLIGINI QAYTARADI
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    //ACCOUNT YONIQ YOKI OCHIKLILIGINI QAYTARADI
    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
