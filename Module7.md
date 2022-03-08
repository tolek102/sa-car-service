# Akademia springa - moduł 7 - branch `module_7_jdbc`

## Dodanie obsługi bazy danych z wykorzystaniej JDBC

### Zadanie

Napisz aplikacje, która z poziomu GUI pozwala na dodawanie i wyświetlanie pojazdów znajdujących się w bazie danych.   
Pojazd ma mieć pola, które umożliwią przechowanie marki, modelu, koloru oraz daty produkcji.  
Stwórz formularz z poziomu, którego będzie możliwe na wyświetlanie pojazdów z konkretnego przedziału dat.Np. wyświetlenie wszystkich 
pojazdów w bazie, których data produkcji jest w zakresie 2010 – 2015.

### W projekcie użyto:

* maven 3.6.3
* java11
* springboot 2.6.2
* lombook
* JUnit 5
* swaggerUI 3
* Thymeleaf
* html
* css
* JDBC
* MySQL database

### Info
Proste gui do obsługi Api

http://localhost:8080/cars


### SQL
create table cars(  
id INT NOT NULL AUTO_INCREMENT,  
mark VARCHAR(50) NOT NULL,  
model VARCHAR(50) NOT NULL,  
production_year YEAR,  
color ENUM( 'YELLOW', 'RED', 'BLUE', 'GREEN', 'WHITE', 'BLACK'),  
PRIMARY KEY (id)  
);

insert into cars(mark, model, production_year, color) values('Honda', 'Accord', 2019, 'RED');  
insert into cars(mark, model, production_year, color) values('Fiat', '126p', 1989, 'YELLOW');  
insert into cars(mark, model, production_year, color) values('Skoda', 'Fabia', 2000, 'GREEN');  