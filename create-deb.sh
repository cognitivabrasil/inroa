ln -s src feb-1.9
tar -cvzf feb_1.9.orig.tar.gz feb-1.9/*
cd feb-1.9
dpkg-buildpackage
