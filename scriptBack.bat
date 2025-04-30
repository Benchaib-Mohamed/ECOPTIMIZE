set "PATH_BACK=C:\Users\bilal\OneDrive\Documents\GitHub\ECOPTIMIZE\ecoptimize-backend\target\ecoptimize-backend-1.0-SNAPSHOT-jar-with-dependencies.jar"
set "IP=172.31.250.133"
set "USER=toto"
set "PWD=toto"
set "PATH_BACK_VM=/home/toto/sirius/backend/ecoptimize-backend-1.0-SNAPSHOT-jar-with-dependencies.jar"

scp -r "%PATH_BACK%" %USER%@%IP%:"/home/toto/sirius/backend"
ssh "%USER%@%IP%" "java -jar %PATH_BACK_VM%"
