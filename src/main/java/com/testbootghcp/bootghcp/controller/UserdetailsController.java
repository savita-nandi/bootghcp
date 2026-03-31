package com.testbootghcp.bootghcp.controller;

import com.testbootghcp.bootghcp.model.Userdetails;
import com.testbootghcp.bootghcp.repository.UserdetailsRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * REST controller exposing CRUD endpoints for Userdetails.
 *
 * This controller uses the repository directly (per prompt) and maps 1:1 to
 * repository helper methods. It includes validation, error handling, and
 * returns ResponseEntity to control HTTP status codes.
 */

/** Url: http://localhost:8080/api/userdetails/ */
@RestController
@RequestMapping("/api/userdetails")
@Validated
public class UserdetailsController {

    private final UserdetailsRepo repo;
    private final Environment env;

    public UserdetailsController(UserdetailsRepo repo, Environment env) {
        this.repo = repo;
        this.env = env;
    }
    
    /**
     * GET /api/userdetails/testcloudapi
     * Test Api without hitting database to verify controller is working and environment is set up correctly.
     */
    @GetMapping("/testcloudapi")
    public ResponseEntity<String> testCloudApi() {
        
    	try {
    		//return ResponseEntity.ok("Cloud API is working! Current time: " + convertDateTimeToLocalTZ());
    		return ResponseEntity.ok("Cloud API is working! Current time: " + LocalDateTime.now().toString());
        } 
    	catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /**
     * GET /api/userdetails/testapi
     * Test Api without hitting database to verify controller is working and environment is set up correctly.
     */
    @GetMapping("/testapi")
    public ResponseEntity<String> testApi() {
        
    	try {
			/*
			 * return ResponseEntity.ok("API is working! Current time: " +
			 * convertDateTimeToLocalTZ());
			 */
    		return ResponseEntity.ok("API is working! Current time: " + LocalDateTime.now().toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    
    /**
     * GET /api/userdetails/
     * List all users.
     *
     * @return 200 OK with list of users
     */
    @GetMapping("/")
    public ResponseEntity<List<Userdetails>> getAll() {
        try {
            
        	//printActiveProfiles();
        	
        	List<Userdetails> all = repo.findAll();
            return ResponseEntity.ok(all);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    
    public void printActiveProfiles() {
		String[] activeProfiles = env.getActiveProfiles();
		if (activeProfiles.length == 0) {
			System.out.println("No active profiles.");
		} else {
			System.out.println("Active profiles:");
			for (String profile : activeProfiles) {
				System.out.println("- " + profile);
			}
		}
	}
    
    /**
     * GET /api/userdetails/{id}
     * Get a user by id.
     *
     * @param id the user id (must be positive)
     * @return 200 OK with the user or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable @Positive(message = "Id must be positive") Long id) {
        try {
            return repo.findById(id)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new EntityNotFoundException("User not found for id: " + id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred: " + e.getMessage()));
        }
    }

    /**
     * GET: http://localhost:8080/api/userdetails/search/firstname?fragment=john
     * Search users by partial or full first name (case-insensitive).
     *
     * @param fragment fragment to search (not blank)
     * @return 200 OK with matching users or 400 Bad Request if invalid input
     */
    @GetMapping("/search/firstname")
    public ResponseEntity<?> searchByFirstName(@RequestParam @NotBlank(message = "fragment must not be blank") String fragment) {
        try {
            List<Userdetails> results = repo.findByFirstNameContainingIgnoreCase(fragment);
            if (results.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorResponse("No users found with first name containing: " + fragment));
			}
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred: " + e.getMessage()));
        }
    }

    /**
     * GET: http://localhost:8080/api/userdetails/search/lastname?fragment=Brenton
     * Search users by partial or full last name fragment (case-insensitive).
     *
     * @param fragment fragment to search (not blank)
     * @return 200 OK with matching users
     */
    @GetMapping("/search/lastname")
    public ResponseEntity<?> searchByLastName(@RequestParam @NotBlank(message = "fragment must not be blank") String fragment) {
        try {
            List<Userdetails> results = repo.findByLastNameContainingIgnoreCase(fragment);
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred: " + e.getMessage()));
        }
    }

    /**
     * GET: http://localhost:8080/api/userdetails/search/email?fragment=john
     * Search users by email fragment ie full or partial (case-insensitive).
     *
     * @param fragment fragment to search (not blank)
     * @return 200 OK with matching users
     */
    @GetMapping("/search/email")
    public ResponseEntity<?> searchByEmail(@RequestParam @NotBlank(message = "fragment must not be blank") String fragment) {
        try {
            List<Userdetails> results = repo.findByEmailIdContainingIgnoreCase(fragment);
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred: " + e.getMessage()));
        }
    }

    /**
     * POST: http://localhost:8080/api/userdetails/create
     * Create a new user record.
     *
     * @param user the user to create (validated)
     * @return 201 Created with Location header and saved entity body
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody Userdetails user) {
        try {
            // Ensure timestamps are set
            LocalDateTime now = LocalDateTime.now();
            if (user.getCreatedOn() == null) user.setCreatedOn(now);
            user.setUpdatedOn(now);

            Userdetails saved = repo.saveUser(user);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(location);
            return new ResponseEntity<>(saved, headers, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred: " + e.getMessage()));
        }
    }

    /**
     * POST: http://localhost:8080/api/userdetails/createbatch 
     * Create multiple users in a single request.
     *
     * @param users list of users to create (must not be empty)
     * @return 201 Created with list of saved users
     */
    @PostMapping("/createbatch")
    public ResponseEntity<?> createUsersBatch(@RequestBody @NotEmpty(message = "users list must not be empty") List<@Valid Userdetails> users) {
        try {
            LocalDateTime now = LocalDateTime.now();
            users.forEach(u -> {
                if (u.getCreatedOn() == null) u.setCreatedOn(now);
                u.setUpdatedOn(now);
            });
            List<Userdetails> saved = repo.saveUsers(users);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred: " + e.getMessage()));
        }
    }

    /**
     * PUT /api/userdetails/{id}
     * Update a user identified by id. Only non-null fields in `updated` will be applied.
     *
     * @param id the id of the user to update
     * @param updated the update payload
     * @return 200 OK with updated entity or 404 if not found
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable @Positive(message = "Id must be positive") Long id,
                                        @Valid @RequestBody Userdetails updated) {
        try {
            Userdetails result = repo.updateById(id, updated);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred: " + e.getMessage()));
        }
    }

    /**
     * PUT /api/userdetails/by-email?email=...
     * Update a user identified by email (case-insensitive). Only non-null fields in `updated` will be applied.
     *
     * @param email the email to identify the user (not blank)
     * @param updated the update payload
     * @return 200 OK with updated entity or 404 if not found
     */
    @PutMapping("/update/byemail")
    public ResponseEntity<?> updateByEmail(@RequestParam @NotBlank(message = "email must not be blank") String email,
                                           @Valid @RequestBody Userdetails updated) {
        try {
            Userdetails result = repo.updateByEmailId(email, updated);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred: " + e.getMessage()));
        }
    }

    /**
     * DELETE /api/userdetails/{id}
     * Delete a user by id.
     *
     * @param id the id to delete
     * @return 204 No Content on success or 404 if not found
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable @Positive(message = "Id must be positive") Long id) {
        try {
            boolean deleted = repo.deleteByIdIfExists(id);
            if (deleted) return ResponseEntity.noContent().build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User not found for id: " + id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred: " + e.getMessage()));
        }
    }

    /**
     * DELETE /api/userdetails/by-email?email=...
     * Delete user(s) by email (case-insensitive). Returns 204 if deletion occurred.
     *
     * @param email the email to delete
     * @return 204 No Content or 404 Not Found
     */
    @DeleteMapping("/delete/byemail")
    public ResponseEntity<?> deleteByEmail(@RequestParam @NotBlank(message = "email must not be blank") String email) {
        try {
            long deletedCount = repo.deleteByEmailIdIgnoreCase(email);
            if (deletedCount > 0) return ResponseEntity.noContent().build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User not found for email: " + email));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred: " + e.getMessage()));
        }
    }

    /**
     * Simple error response DTO returned on failures.
     */
    public static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
    
    public String convertDateTimeToLocalTZ() {

    	ZonedDateTime istNow = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        String formattedDate = istNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
		return formattedDate;
	}
}
