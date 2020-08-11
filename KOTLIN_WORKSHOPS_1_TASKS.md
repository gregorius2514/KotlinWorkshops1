# Welcome at Kotlin Workshops 1

## Zadanie 1

### Opis zadania

    Przepisz klasę encyjną `Run.java` na postać kotlinową

### Wprowadzenie

Pierwszym zadaniem będzie zapoznanie się z możliwościami kotlina jeśli chodzi możliwości tworzonych klas oraz `data` klas.
W ramach tego zadania pochylimy się nad najniższą warstwą w naszej aplikacji czyli encjami.

W javie klasy encyjne mają postać:
        
    @Entity
    public class Person {

        @Id
        private Integer id;

        private String firstname;

        private String lastname;

        private Person() {
        }

        public Run(...) {
        }

        // getters and setters

        @Override
        public String toString() {
        ...
        }

        @Override
        public boolean equals(Object o) {
        ...
        }

        @Override
        public int hashCode() {
        ...
        }
    }

W powyższym przykładzie widzimy:
- pola klasy,
- prywatny konstruktor, który ma zapobiegać stworzeniu pustego obiektu (bez zainicjowanych pól),
- właściwy konstruktor używany do `pełnej/właściwej` inicjalizacji klasy encyjnej
- metody `toString`, `equals`, `hashCode`, które są konieczne do poprawnego operowania na klasach encyjnych np. podczas ich porównywania, filtrowania itp. Metody te muszą być przegenerowane za każdym razem po np. po dodaniu/modyfikacji pól klasy encyjnej
- gettery oraz settery do pól, które chcemy móc modyfikować po zainicjalizowaniu obiektu
- wszystkie pola nie są final, więc możliwa jest ich podyfikacja poprzez settery, które zostały zaimplementowane/wygenerowane

Reprezentacja powyższej klasy encyjnej w kotlinie wygląda następująco

      @Entity
      data class Person(
          @Id
          private val id: Int? = null,
          val firstname: String,
          val lastname: String
      )

Powyższa `data class Person` posiada właściwości:
- wygenerowane metody `toString`, `equals`, `hashCode`
- pole `id` zostanie zainicjalizowane podczas tworzenia instancji klasy encyjnej, ale nie bedzie do niego dostępu (nie zostanie wygenerowany `getter` oraz `setter` dla tego pola
- pole `id` jest polem null'owalnym i podczas tworzenia instancji klasy `Person` nie musimy przekazywać wartości tego pola jako argument konstruktora
- dla pól `firstname` oraz `lastname` nie zostaną wygenerowane `settery`, ponieważ użyliśmy slowa kluczowego `val` od wartość zamiast `var` od zmienna
- znaki `(` oraz `)`, które występują po nazwie klasy oznaczają zawartość jedynego konstruktora, który zostanie umieszczony w wygenerowanym `bytecode'dzie` klasy `Person` po skompilowaniu kodu tej klasy z postaci języka Kotlin

## Zadanie 2

### Opis

Przepisanie klasy startującej naszą aplikację na kotlina `KotlinWorkshopsApplication`

### Wprowadzenie

W kotlinie istnieje annotacją `@JvmStatic`, której należy użyć w przypadku kiedy biblioteka java, której używamy
wymaga od nas używanie method/funkcji statycznych.
W obecnym przypadku będziemy musieli stworzyć funkcję `main` wewnątrz klasy `KotlinWorkshopsApplication`, która będzie wymagała tej annotacji by wygenerowany `bytecode` dla tej funcji wyglądał tak jak `bytecode` javy.

## Zadanie 3

### Opis

W ramach tego zadania poznamy interfejsy w kotlinie.
W ramach tego zadania będziemy musieli przepisać klasę `RunRepository` na interfejs Kotlin'a.

### Wprowadzenie

W kotlinie nie występują takie konstrukcje jak klasy abstrakcyjne, kotlin wspiera wyłącznie interfejsy.

Słówko `extends` oraz `implements` zostało zastąpione znaczkiem `:`.

Przykład kodu w Javie:

    public class Dog implements Pet { ... }
    public class Dog extends Pet { ... }
    
Przykład kodu w Kotlinie:

    class Dog : Pet { ... }
    
### Dodatkowa informacja

Dodatkowo w kotlinie wszystkie klasy są publiczne więc nie ma potrzeby dodawania słowa kluczowego `public`.


### Zadanie 4

### Opis

W ramach tego zadania sprawdzimy jak wygląda integracja kotlina ze springiem i jego mechanizmem wstrzykiwania zależności.
Przepiszmy klasę `UserDetailsView` na klasę kotlinową.

### Wprowadzenie

Podobnie jak z encjami w konstruktorze możemy używać annotacji także tych springowych takich jak `@Autowired` co powinniśmy
także tutaj uczynić.

W poniższym przykładzie mamy wstrzykiwanie zależności przez konstruktor

Przykład w Javie:

public class ScoreCalculator {

    @Autowired
    public ScoreCalculator(UserStatistics userStatistics) {
       ... 
    }
}

Przykład w Kotlinie:

class ScoreCalculator(
    @Autowired val userStatistics: Userstatistics
)


### Zadanie 5

### Opis

#TODO Wstrzykiwanie zależności przy użyciu `@Autowired` poprzez pole w kotlinie 


### Zadanie 6

### Opis

Przepisanie testu `RunService` gdzie bedziemy mogli usunac duza czesc boilerplaitu oraz uzyc klas napisanych w javie


### Zadanie 7

### Opis

ciag dalszy z zadania 6, przepisanie klas uzywanych w tescie `RunService`, class stworzonych wylaczenie na potrzeby testow,
takich jak builder dla obiektow `Run` oraz `RunDetails`