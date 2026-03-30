---
description: 'Generate a Spring Java Entity class file'
agent: 'edit'
---

# Generate Spring Java Entity class file


## Requirements:

### Pom.xml changes
- Include the below libraries in pom.xml file
- Include version 1.18.42 of of library "org.projectlombok:lombok" with scope as 'compile'
- Include version 3.2.0 of library "jakarta.persistence:jakarta.persistence-api"

### Generate Entity class
- Package location: com.testbootghcp.bootghcp.model
- For table schema reference use file, src/main/resources/schema.sql
- Class name: Userdetails
- Entity name: userdetails
- Table name: userdetails


### Lombok usage recommendation
- Use `@Data` for entity classes with simple getters/setters
- Use `@NoArgsConstructor` and `@AllArgsConstructor` for constructors
- Use `@RequiredArgsConstructor` for dependency injection
- Avoid `@Builder` unless complex object construction is needed

### Comments for reference
- Inspite of telling Agent to yuse the latest version of a library,it would not and hence recommended to mention library version too.





