#cd ../../
#mvn clean
#mvn package
#cd target 
. gui.sh & 
xterm -e "./baecker1.sh" & 
xterm -e "./logistik1.sh"&
xterm -e "./baecker2.sh" & 
xterm -e "./logistik2.sh"&
xterm -e "./baecker3.sh" & 
xterm -e "./logistik3.sh"
