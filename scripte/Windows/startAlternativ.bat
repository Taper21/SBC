cd .\..\..\target

::baecker
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.Baecker 1
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.Baecker 2
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.Baecker 3

::baecker
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.LogistikMitarbeiter 1
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.LogistikMitarbeiter 2
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.LogistikMitarbeiter 3

::baecker
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.QualitaetsKontrolleur 1 10
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.QualitaetsKontrolleur 2 20
start java -cp SBC-0.0.1-SNAPSHOT.jar alternativ.mitarbeiter.QualitaetsKontrolleur 3 30