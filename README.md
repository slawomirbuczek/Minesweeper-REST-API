# Minesweeper-backend
REST API for my [Minesweeper](https://github.com/slawomirbuczek/Minesweeper) android app. Deployed on Heroku.  

## Current functionalities:  
* login and registration
* global ranking - top 50 records for each level
* player statistics for each level:
  * total games played
  * games won
  * total playing time
  * average time of game won
  * best time of game won

## Technology stack and its application in the project:
* Spring Boot:
  * app starter, auto configuration, spring boot magic
* Spring Security:
  * authentication with JWT
  * global ranking and player statistics are available only to authenticated users
* Spring JPA:
  * hibernate
* Spring Validation:
  * requests validation
* H2:
  * used locally for developing and testing
* PostgreSQL:
  * Heroku Postgres, used in production
* Swagger:
  * testing endpoints
* Lombok:
  * remove boiler plate code
