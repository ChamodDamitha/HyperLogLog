java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=settings=profile,duration=30s,dumponexit=true,filename=hyperloglog-2-8-2017-attempt1-accuracy-0.001.jfr -Xmx8g -Xms8g -cp .:target/hyperLogLog-1.0-SNAPSHOT.jar:classes:target/classes:target/test-classes:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar:lib/stream-2.9.5.jar org.junit.runner.JUnitCore HyperLogLogTest