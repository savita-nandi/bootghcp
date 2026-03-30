---
description: 'Generate a Spring Java Repository class file'
agent: 'edit'
---

# Generate Spring Java Repository class file


## Requirements:

### Pom.xml changes
- No changes

### Generate Repository class
- Package location: com.testbootghcp.bootghcp.repository
- Class name: UserdetailsRepo
- Use JPARepository class on pojo Userdetails
- Add search case-insensitive methods to get Userdetails by Id, by either Firstname or Lastname,by EmailID and list all Userdetails.
- The string search methods should allow search with wildcard character "*"
- Add a save method to save a single Userdetail record
- Add a save method to save multiple Userdetail records
- Add an update method to update a Userdetail record by Id
- Add a case-insensitive update method to update a Userdetail record by EmailId
- Add a delete method to delete a Userdetail record by Id
- Add a case-insensitive delete method to delete a Userdetail record by EmailId



### Comments for reference
- No comments





