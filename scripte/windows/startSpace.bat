cd ..\..\target

::baecker
start java -cp SBC-0.0.1-SNAPSHOT.jar xvsm.Baecker 1
start java -cp SBC-0.0.1-SNAPSHOT.jar xvsm.Baecker 2
start java -cp SBC-0.0.1-SNAPSHOT.jar xvsm.Baecker 3

::baecker
start java -cp SBC-0.0.1-SNAPSHOT.jar xvsm.Logistik 1
start java -cp SBC-0.0.1-SNAPSHOT.jar xvsm.Logistik 2
start java -cp SBC-0.0.1-SNAPSHOT.jar xvsm.Logistik 3

::baecker
start java -cp SBC-0.0.1-SNAPSHOT.jar xvsm.Kontrolleur 1 10
start java -cp SBC-0.0.1-SNAPSHOT.jar xvsm.Kontrolleur 2 20
start java -cp SBC-0.0.1-SNAPSHOT.jar xvsm.Kontrolleur 3 30