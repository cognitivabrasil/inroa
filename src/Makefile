all: target/feb.war

install: 
	mkdir -p ${DESTDIR}/usr/share/FEB && install target/feb.war ${DESTDIR}/usr/share/FEB 

target/feb.war:
	mvn install

