gitaddall:
	git add */*/*.java
	git add */*/*/*.java
	git add */*/*/*/*.java

loc:
	wc -l  */*/*/*.java */*/*/*/*.java

jar:
	todo

run:
	java -classpath bin:hdf/fits.jar:hdf/jarhdf5-1.10.1.jar:hdf/slf4j-api-1.7.5.jar:hdf/slf4j-simple-1.7.5.jar:hdf/jarhdf-4.2.12.jar:hdf/netcdf.jar:hdf/slf4j-nop-1.7.5.jar:jars/glazedlists-1.11.0.jar:jars/javax.json-api-1.2-SNAPSHOT.jar:jars/swingx-all-1.6.5-1.jarjars/javax.json-1.1.jar:jars/trie-1.0-SNAPSHOT.jar \
	cellstore.viewer.MainView -Vm -Djava.library.path=./hdf



