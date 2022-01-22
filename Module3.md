# Akademia springa - moduł 3 - branch `module_3_rest_api`

## REST Car service

### Zadanie

Zadanie podstawowe:
Napisz REST API dla listy pojazdów. Pojazd będzie miał pola: id, mark, model, color.
API które będzie obsługiwało metody webowe:

do pobierania wszystkich pozycji
do pobierania elementu po jego id
do pobierania elementów w określonym kolorze (query)
do dodawania pozycji
do modyfikowania pozycji
do modyfikowania jednego z pól pozycji
do usuwania jeden pozycji
Przy starcie aplikacji mają dodawać się 3 pozycje.

—————————

Dla ambitnych:

rozbuduj aplikacje o możliwość zwracania danych w postaci XML
dodaj obsługę Swgger UI
zaimplementuj HATEOAS

### W projekcie użyto:

* maven 3.6.3
* java11
* springboot 2.6.2
* lombook
* JUnit 5
* swaggerUI 3
* HATEOAS

### Info

Do metosy patch użyto refleksji, dzęki czemu wystarczy, że podamy tylko wybrane pola jakie chcemy zrobić update zamiast 
uzupełniania całego modelu nulami, co w sumie i tak jest niemożliwe ze względu na zastosowaną walidację pól modelu.

Po uruchomieniu swagger dostępny pod url: http://localhost:8080/swagger-ui/index.html

Aby Swagger 3 działał z użytą wersją spronga należało dodać odpowiedni wpis w konfiguracji (spowodowane jest to bugiem po stronie 
springfoxa)   
`spring.mvc.pathmatch.matching-strategy = ant-path-matcher` 

Niestety jeśli będzie potrzeba użycia actuatora trzeba będzie zamienić swaggera na openApi, lub zrobić downgrade wersji spronga (chyba, 
że wyjdzie wersja swaggera z poprawką)
