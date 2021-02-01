# Minesweeper-ranking
Global ranking - backend for my [Minesweeper](https://github.com/slawomirbuczek/Minesweeper) android app. Deployed on Heroku.  
Ranking contains records of each game won during playing on the android app.  
Players credentials and records are stored in Heroku Postgres.

## Current functionalities:  
* registration & login;
* JWT authentication;
* getting top 50 records from ranking for a given level;
* adding records to ranking;

## Technology stack and its application in the project:
* Spring Boot:
  * app starter, auto configuration, spring boot magic;
* Spring Security:
  * authentication with JWT;
  * getting ranking and adding new records are available only to authenticated users;
* Spring JPA:
  * db contains four entities, one for user and three for each game level;
* Spring Validation:
  * validating credentials during registration;
* H2:
  * used locally for developing and testing;
* PostgreSQL:
  * Heroku Postgres, used in production;
* Swagger:
  * testing endpoints;
* Lombok:
  * remove  boilerplate code;
