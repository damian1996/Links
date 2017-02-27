Wykonawca : Damian Stachura

Przesyłam cały projekt z Intellija.
Plik Linki.java zawiera main, więc aplikację uruchamia się poprzez odpalenie tego pliku po kompilacji.

W GUI dostępne są trzy przyciski : 
1) umożliwia wycofanie się do ostatniej sprawdzanej strony
2) Rozpoczyna obliczenia dla adresu wpisanego w JTextFielda.
3) Pokazuje historię zapisaną w bazie sqlite'a.

Gdy program parsuję URL, to pokazuję się JDialog informujący o tym.

Kliknięcie na którykolwiek wiersz z historii jest równoważne rozpoczęciu obliczeń dla wybranej strony.

Podanie niepoprawnego URLa powoduje pojawienie się komunikatu o błędzie oraz nie jest odnotowany w bazie danych.

Wybranie linku w liście w GUI działa tak samo jak kliknięcie na wiersz w historii.
