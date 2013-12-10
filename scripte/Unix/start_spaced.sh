cd ../../
mvn clean
mvn package
cd target
java -cp SBC-0.0.1-SNAPSHOT.jar gui.MainFrame &
java -cp SBC-0.0.1-SNAPSHOT.jar xvsm.Baecker B1 &  
java -cp SBC-0.0.1-SNAPSHOT.jar xvsm.Kontrolleur K1 2 &  
java -cp SBC-0.0.1-SNAPSHOT.jar xvsm.Logistik L1 &  
