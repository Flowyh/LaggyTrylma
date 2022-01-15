Chińskie warcaby
zadanie zaliczeniowa na Technologię Programowania by:
- Maciej Bazela
- Krzysztof Nowak


### Urchomienie
Wkorzystywana wersja Javy: 16

Projekt można uruchomić z poziomu Mavena:
```shell
mvn compile
mvn test
mvn exec:java -Dexec.mainClass="com.laggytrylma.backend.Main" # uruchamia serwer na 0.0.0.0:21375
mvn exec:java -Dexec.mainClass="com.laggytrylma.frontend.Main" # uruchamia klienta (zalecane jest uruchomienie dwóch)
```

lub korzystając z plików .jar znajdujących się w folderze `bin/`
```shell
java -jar bin/server.jar
java -jar bin/client.jar
```

Po uruchomieniu klientów, podajemy nazwy użytkowników oraz łączymy się z serwerem.
Jeden z klientów powinien stworzyć nową grę, o określonej liczby graczy. 
Nowa gra, z możliwością dołączenia, pojawi się na liście gier pozostałych klientów.
W momencie, gdy dołączy odpowiednia liczba graczy, gra rozpoczyna się.
