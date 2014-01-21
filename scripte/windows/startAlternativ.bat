cd ..\..\target

::baecker
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.Baecker 1 Wien
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.Baecker 2 Wien
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.Baecker 3 Wien

::baecker
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.LogistikMitarbeiter 1 Wien
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.LogistikMitarbeiter 2 Wien
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.LogistikMitarbeiter 3 Wien

::baecker
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.QualitaetsKontrolleur 1 10 Wien
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.QualitaetsKontrolleur 2 20 Wien
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.QualitaetsKontrolleur 3 30 Wien