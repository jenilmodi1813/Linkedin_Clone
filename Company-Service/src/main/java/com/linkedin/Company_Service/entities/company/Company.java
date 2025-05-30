package com.linkedin.Company_Service.entities.company;


import com.linkedin.Company_Service.entities.Users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users createdBy;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column
    private String about;

    @Column(nullable = false)
    private String numEmployees;

    @Column
    private String website;

    @Column(length = 100)
    private String headLine;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private Set<CompanyLocations> locations;
}
