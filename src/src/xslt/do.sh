java -classpath /home/paulo/saxon9he.jar net.sf.saxon.Transform -s:../test/java/metadata/teste1.xml -xsl:dc2obaa.xsl > out.xml
xmllint --format out.xml 

