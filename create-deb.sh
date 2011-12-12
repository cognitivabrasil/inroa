ln -s src feb-2.0
tar -cvzf feb_2.0.1.orig.tar.gz feb-2.0/*
cd feb-2.0
dpkg-buildpackage
