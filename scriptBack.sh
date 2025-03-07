#!/bin/bash

# IP du serveur, l'utilisateur et le mot de passe
IP="172.31.250.133"
USER="toto"
PWD="toto"
PATH_BACK_VM="/home/toto/sirius/backend/ecoptimize-backend-1.0-SNAPSHOT-jar-with-dependencies.jar"
PATH_BACK="/Users/mohamedbenchaib/ECOPTIMIZE/ecoptimize-backend/target/ecoptimize-backend-1.0-SNAPSHOT-jar-with-dependencies.jar"


scp -r 
# connexion à la vm back et lancement du serveur 
sshpass -p "$PWD" ssh $USER@$IP "java -jar $PATH_BACK_VM"





