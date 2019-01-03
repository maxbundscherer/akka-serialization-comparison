export SBT_OPTS="-Xms1G -Xmx8G";
cat autoRunner.sh >> ~/Desktop/params.txt;
sbt clean run >> ~/Desktop/run.log;
sbt clean test >> ~/Desktop/test.log;
