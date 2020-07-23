package com.yte.intern.project.manageclients.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yte.intern.project.common.entity.BaseEntity;
import com.yte.intern.project.manageenrollment.entity.Enrollment;
import com.yte.intern.project.security.models.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "CLIENT_SEQ")
@Table(name = "clients")
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@json")
public class Client extends BaseEntity/* implements UserDetails*/ {

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @NotBlank //ToDo unique
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Column(name = "TC_KIMLIK_NO", unique = true)
    private String tcKimlikNo;

    @OneToMany(mappedBy = "client", cascade=CascadeType.ALL, orphanRemoval=true)
    //@JsonManagedReference
    Set<Enrollment> enrollments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "client_roles",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Client() {
        super();
    }

    public Client(String name, String surname, String username, String email, String encode, String tcKimlikNo) {
        super();
        this.name = name; this.surname=surname; this.username=username; this.email=email;
        this.password=encode; this.tcKimlikNo=tcKimlikNo;
    }
}
