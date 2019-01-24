export SBT_OPTS="-Xms4G -Xmx14G";
mkdir results;

cat autoRunner.sh >> results/params.txt;
cat src/main/resources/params.conf >> results/params.txt;

echo "" >> results/params.txt;
echo "Start Run" >> results/params.txt;
date >> results/params.txt;
sbt clean run >> results/run.log;
echo "" >> results/params.txt;
echo "End Run" >> results/params.txt;
date >> results/params.txt;

echo "" >> results/params.txt;
echo "Start Test" >> results/params.txt;
date >> results/params.txt;
sbt clean test >> results/test.log;
echo "" >> results/params.txt;
echo "End Test >> results/params.txt;
date >> results/params.txt;
