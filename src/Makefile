all: target/feb.war

install: 
	mkdir -p ${DESTDIR}/usr/share/FEB && mkdir -p ${DESTDIR}/var/lib/tomcat6/webapps && install target/feb.war ${DESTDIR}/var/lib/tomcat6/webapps && install sql/*.sql ${DESTDIR}/usr/share/FEB

target/feb.war:
	mvn install

