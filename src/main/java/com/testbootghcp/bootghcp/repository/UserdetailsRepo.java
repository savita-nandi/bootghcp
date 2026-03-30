package com.testbootghcp.bootghcp.repository;

import com.testbootghcp.bootghcp.model.Userdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;

/**
 * Repository for accessing Userdetails entities.
 *
 * Provides convenience search methods that perform wildcard searches ("contains")
 * so callers can pass partial name/email fragments and find matching users.
 */
@Repository
public interface UserdetailsRepo extends JpaRepository<Userdetails, Long> {

    /**
     * Find a user by their id. (Provided by JpaRepository, declared here for clarity.)
     *
     * @param id the user id
     * @return optional userdetails
     */
    Optional<Userdetails> findById(Long id);

    /**
     * Search users whose first name contains the given fragment (case-insensitive).
     * Example: "ann" matches "Ann", "Joanna".
     *
     * @param firstNameFragment fragment to search
     * @return list of matching users
     */
    List<Userdetails> findByFirstNameContainingIgnoreCase(String firstNameFragment);

    /**
     * Search users whose last name contains the given fragment (case-insensitive).
     *
     * @param lastNameFragment fragment to search
     * @return list of matching users
     */
    List<Userdetails> findByLastNameContainingIgnoreCase(String lastNameFragment);

    /**
     * Search users whose email contains the given fragment (case-insensitive).
     * Because email is unique in the schema, in typical use callers will pass the full email
     * and receive 0 or 1 results; partial searches are also supported.
     *
     * @param emailFragment fragment to search in emailId
     * @return list of matching users
     */
    List<Userdetails> findByEmailIdContainingIgnoreCase(String emailFragment);

    /**
     * List all users. (Provided by JpaRepository, declared here for clarity.)
     *
     * @return list of all userdetails
     */
    List<Userdetails> findAll();

    // --- Additional convenience methods requested ---

    /**
     * Find a user by exact email (case-insensitive).
     * Used by update/delete helpers where we need to locate the entity first.
     *
     * @param email the email to search
     * @return optional userdetails
     */
    Optional<Userdetails> findByEmailIdIgnoreCase(String email);

    /**
     * Save a single Userdetails instance. This simply delegates to JpaRepository.save
     * but provides a clearer method name for callers.
     *
     * @param user the user to save
     * @return the saved entity (with id populated)
     */
    default Userdetails saveUser(Userdetails user) {
        return save(user);
    }

    /**
     * Save multiple Userdetails records in a batch. Delegates to JpaRepository.saveAll.
     *
     * @param users iterable of users to save
     * @return list of saved entities
     */
    default List<Userdetails> saveUsers(Iterable<Userdetails> users) {
        return saveAll(users);
    }

    /**
     * Update an existing Userdetails record located by id. Only non-null fields on
     * the provided `updated` object will be copied over (except id).
     *
     * This method runs within a transaction to ensure the read-modify-write is atomic.
     *
     * @param id the id of the entity to update
     * @param updated a Userdetails object containing updated values
     * @return the updated entity
     * @throws EntityNotFoundException if no entity with the given id exists
     */
    @Transactional
    default Userdetails updateById(Long id, Userdetails updated) {
        Userdetails existing = findById(id).orElseThrow(() -> new EntityNotFoundException("Userdetails not found for id: " + id));
        // Copy fields if provided (null-check to allow partial updates)
        if (updated.getFirstName() != null) existing.setFirstName(updated.getFirstName());
        if (updated.getLastName() != null) existing.setLastName(updated.getLastName());
        if (updated.getEmailId() != null) existing.setEmailId(updated.getEmailId());
        // update timestamp
        existing.setUpdatedOn(LocalDateTime.now());
        return save(existing);
    }

    /**
     * Update an existing Userdetails record located by email (case-insensitive).
     * Only non-null fields on the provided `updated` object will be copied over (except id).
     *
     * @param email the email (case-insensitive) of the entity to update
     * @param updated a Userdetails object containing updated values
     * @return the updated entity
     * @throws EntityNotFoundException if no entity with the given email exists
     */
    @Transactional
    default Userdetails updateByEmailId(String email, Userdetails updated) {
        Userdetails existing = findByEmailIdIgnoreCase(email).orElseThrow(() -> new EntityNotFoundException("Userdetails not found for email: " + email));
        if (updated.getFirstName() != null) existing.setFirstName(updated.getFirstName());
        if (updated.getLastName() != null) existing.setLastName(updated.getLastName());
        if (updated.getEmailId() != null) existing.setEmailId(updated.getEmailId());
        existing.setUpdatedOn(LocalDateTime.now());
        return save(existing);
    }

    /**
     * Delete a record by id if it exists. Returns true if an entity was deleted.
     *
     * @param id entity id to delete
     * @return true if deletion occurred, false if no entity with id existed
     */
    @Transactional
    default boolean deleteByIdIfExists(Long id) {
        if (existsById(id)) {
            deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Delete record(s) by email (case-insensitive). Returns the number of deleted records.
     * Derived delete query; Spring Data will implement this automatically.
     *
     * @param email the email to delete by
     * @return number of records deleted (0 or 1 in normal schema where email is unique)
     */
    long deleteByEmailIdIgnoreCase(String email);
}