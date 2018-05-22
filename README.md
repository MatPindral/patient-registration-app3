

Aplikacja do rejestracji on-line dla pacjentów przychodni.
Technologie: JAVA 8, Spring: (Core, MVC, Data, Security), Maven, JUnit4, Mockito, 
PostgresSQL, JSP, JSTL.
Baza zdalna. Dane dla użytkownika o uprawnieniach administratora:
Login: admin
Hasło: adminadmin
Dane dla użytkownika o uprawnieniach pacjenta (możliwość stowrzenia własnego):
Login: Test
Hasło: testtest

Funkcjonlności programu:
Podstawowa - umożliwienie rezerwacji wizyty do konkretnego lekarza, na konkretny dzień i godzinę dla zarejestrowanego i zalogowanego pacjenta.
Rejestracja nowego użytkownia przez formularz on-line.
Logowanie przy użyciu danych przechowywanych w bazie.
Możliwość wylogowania się.
Kontrola dostępu do poszczególnych modułów przez Spring Security.
Wyświetlanie historii wizyt pacjenta.
Wyświetlanie lekarzy dostępnych w przychodni - opcja dostępna dla administratora.
Możliwość dodawania nowych lekarzy do bazy danych przez formularz.

Znane błędy:
rejestracja na wizytę umożliwia wyświetlanie i wybór wizyt z przeszłości.

Planowane rozwój aplikacji:
Umożliwienie użytkownikowi zmiany sowich danych i usunięcia konta.
Wprowadzenie panelu managera, który będzie miał możliwość rejestrowania i wyrejestrowywania dowolnego pacjenta z wizyty.
Umożliwienia managerowi elestycznego dodawania, usuwania i zmiany grafików dostępnych wizyt dla lekarzy (w tym momencie są wprowadzane do bazy danych "na sztywno", poprzez wywołanie funkcji addPredefinedTimetablesToDB() z klasy TimetablesService.
Umożliwienia administratorowi dodawania nowych administratorów, a także dodawanie i usuwanie managerów i pacjentów.
