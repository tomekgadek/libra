# Libra - system zarządzania domową biblioteką

Projekt jest pełnoprawną aplikacją typu full-stack do zarządzania kolekcją książek. Został zbudowany w oparciu o framework Spring Boot 3 po stronie backendu oraz bibliotekę React po stronie frontendu. Aplikacja umożliwia przeglądanie posiadanych zasobów, dodawanie nowych pozycji do bazy danych, edycję szczegółowych informacji o produkcie oraz zarządzanie grafikami okładek.

## Sposoby uruchomienia

### 1. Tryb deweloperski (niezależny)
W tym trybie backend oraz frontend działają jako oddzielne procesy, co ułatwia pracę nad kodem dzięki funkcji automatycznego odświeżania zmian (Hot Reload).

Uruchomienie backendu (API):
W głównym katalogu projektu należy wywołać polecenie:
`mvn spring-boot:run`
Interfejs programistyczny będzie dostępny pod adresem: http://localhost:8080

Uruchomienie frontendu:
W osobnym terminalu należy przejść do katalogu libra-app:

+ `cd libra-app`
+ `npm install` - przy pierwszym uruchomieniu
+ `npm start`

Aplikacja kliencka będzie dostępna pod adresem: <http://localhost:3000> i będzie automatycznie przekierowywać zapytania do API na port 8080.

### 2. Tryb zintegrowany (jedna paczka JAR)
W tym trybie aplikacja frontendowa jest kompilowana do postaci statycznej i serwowana bezpośrednio przez serwer aplikacyjny Spring Boot.

Budowanie projektu:

W głównym katalogu projektu należy wywołać polecenie: `mvn clean package -DskipTests`

Wtyczka **frontend-maven-plugin** zajmie się instalacją środowiska **Node.js**, budową frontendu oraz skopiowaniem plików do zasobów statycznych backendu.

Uruchomienie aplikacji:

Po pomyślnym zakończeniu budowania, gotowy plik JAR można uruchomić poleceniem: `java -jar target/libraapi-0.0.1-SNAPSHOT.jar`

Cały system będzie dostępny pod jednolitym adresem: <http://localhost:8080>

## Wykorzystane technologie
- Backend: Java 21, Spring Boot 3, Spring Data JPA, baza danych H2 (w pamięci operacyjnej).
- Frontend: React 18, React Router 6, axios, Bootstrap Icons.
- Narzędzia: Maven, npm, frontend-maven-plugin.
