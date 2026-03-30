package com.testbootghcp.bootghcp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a user details record.
 *
 * This maps to the "userdetails" table. Fields mirror the schema defined in resources/schema.sql.
 */
@Entity
@Table(name = "userdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Userdetails {

    /** Primary key identifier. Matches ID BIGINT AUTO_INCREMENT PRIMARY KEY in schema.sql */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)    
    private String firstName;

    @Column(name = "lastname", nullable = false)   
    private String lastName;

    @Column(name = "emailid", nullable = false, unique = true)   
    private String emailId;

    @Column(name = "createdon", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "updatedon", nullable = false)
    private LocalDateTime updatedOn;

    // TODO: consider adding @PrePersist and @PreUpdate hooks to manage createdOn/updatedOn automatically
}
