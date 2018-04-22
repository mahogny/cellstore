gitaddall:
	git add */*/*.java
	git add */*/*/*.java
	git add */*/*/*/*.java

loc:
	wc -l  */*/*/*.java */*/*/*/*.java
