export SBT_OPTS="-Xms1G -Xmx8G";
sbt clean run >> ~/Desktop/run.log;
sbt clean test >> ~/Desktop/test.log;