all: target/feb.war target/classes/feb_config.jar

install: target/feb.war target/classes/feb_config.jar
	mkdir -p ${DESTDIR}/usr/share/FEB && mkdir -p ${DESTDIR}/var/lib/tomcat6/webapps && install target/feb.war ${DESTDIR}/var/lib/tomcat6/webapps && install sql/*.sql ${DESTDIR}/usr/share/FEB && install target/classes/feb_config.jar ${DESTDIR}/usr/share/FEB && mkdir -p ${DESTDIR}/usr/bin && install bin/feb_config ${DESTDIR}/usr/bin

target/feb.war:
	mvn install

target/classes/feb_config.jar: src/java/feb/FebChangeDatabaseInfo.java target/classes/manifest.mf
	mvn install && cd target/classes && jar -cfm feb_config.jar manifest.mf feb/FebChangeDatabaseInfo.class
