# Assignment

### Endpoints Exposed

- Create Employee 
  - `api/v1/create`
- Get Highest Salary 
  - `api/v1/highestSalary`
- Top Ten Highest Earning Employee Names
  - `api/v1/topTenHighestEarningEmployeeNames`
- Get All Employees
  - `api/v1/employees`
- Delete Employee
  - `api/v1/delete/{id}`
- Search Employee by Name
  - `api/v1/search/{searchQuery}`


### Implemented Below Endpoint Methods
- getAllEmployees()
- getEmployeesByNameSearch(String searchInput)
- getEmployeeById(Long id)
- getHighestSalaryOfEmployees()
- getTop10HighestEarningEmployeeNames()
- createEmployee(String name, int salary, int age)
- deleteEmployee(Long id)

### Tests
`RqChallengeApplicationTests` class contains tests for all the methods implemented above.

### Database Info
 As of now for demonstration purpose I am using H2 inmemory Database.


