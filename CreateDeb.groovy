import groovy.text.SimpleTemplateEngine

Properties p = new Properties()
p.load(new FileInputStream(new File("/var/jenkins/buildnumber.properties")))

def pom = new XmlSlurper().parse(new File("src/pom.xml"))

String version = pom.version

def binding = ['feb' : [
	'version' : version ],
	'DESTDIR' : '${DESTDIR}',
	'build' : [
		'number' : p.getProperty(version,"1")
	],
	'tomcat' : 'tomcat7'
]

p.setProperty(version, ((p.getProperty(version, "1") as Integer) + 1) as String)
p.store(new FileOutputStream(new File("buildnumber.properties")), null)


def filePattern = ~/(.*)\.template$/  
def directory = new File(".")  
def engine = new SimpleTemplateEngine()

println "Matching Files:"  
directory.eachFileRecurse() { file ->
	if(file =~ /(.*)\.template$/) {
		println file
		String newfilename = file.toString().replaceAll(/\.template$/, "");
		println newfilename
		println "======================================="
		println engine.createTemplate(file).make(binding).toString()
		println "=======================================\n\n"
		def out = new File(newfilename)
		out.write engine.createTemplate(file).make(binding).toString()

	}
}  
