export SBT_OPTS="-Xms1G -Xmx6G";
mkdir results;
cat autoRunner.sh >> results/params.txt;
cat src/main/resources/params.conf >> results/params.txt;
sbt clean run >> results/run.log;
sbt clean test >> results/test.log;
sudo shutdown now;