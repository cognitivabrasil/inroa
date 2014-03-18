cd src/
mvn clean
cd ..

rm -R -f src/target/
rm -R -f src/bin/

rm feb_*.orig.tar.gz 
rm feb_*.changes
rm feb_*.dsc
rm feb_*debian*
rm *.deb

