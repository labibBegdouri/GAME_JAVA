JAVAC = javac
JAVA = java
JFLAGS = -encoding UTF-8
SRC = EntryPoint.java $(wildcard MAPS/*.java) $(wildcard Personnages/*.java) $(wildcard SAC/*.java)  $(wildcard Menu/*.java) $(wildcard Position/*.java)
BIN = bin
MAIN = EntryPoint

all:
	mkdir -p $(BIN)
	$(JAVAC) $(JFLAGS) -d $(BIN) $(SRC)

run: all
	$(JAVA) -cp $(BIN) $(MAIN)

clean:
	rm -rf $(BIN) *.class MAPS/*.class Personnages/*.class SAC/*.class Menu/*.class Position/*.class

.PHONY: all run clean
