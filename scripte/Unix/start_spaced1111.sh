#cd ../../
#mvn clean
#mvn package
#cd target 
. gui.sh & 
xterm -e "./baecker1.sh" & 
xterm -e "./kontrolleur1.sh" & 
xterm -e "./logistik1.sh"
