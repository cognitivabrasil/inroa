import groovy.text.SimpleTemplateEngine

Properties p = new Properties()
p.load(new FileInputStream(new File("/var/lib/jenkins/buildnumber.properties")))

def pom = new XmlSlurper().parse(new File("src/pom.xml"))

String fullVersion = (pom.version as String)
String version = (pom.version as String).replaceAll(/-SNAPSHOT/, "").
		replaceAll(/-RELEASE/, "")

String ppaSuffix
if(fullVersion =~ /RELEASE/) {
	ppaSuffix = ""
}
else {
	ppaSuffix = "-unstable"
}

def date = new Date()
String formattedDate = date.format('yyyyMMdd')

def binding = ['feb' : [
	'version' : version,
	'fullVersion' :fullVersion ],
	'DESTDIR' : '${DESTDIR}',
	'build' : [
		'number' : p.getProperty(version,"1"),
	],
	'ppaSuffix': ppaSuffix,
	'nightly' : formattedDate,
	'tomcat' : 'tomcat7'
]

p.setProperty(version, ((p.getProperty(version, "1") as Integer) + 1) as String)
p.store(new FileOutputStream(new File("/var/lib/jenkins/buildnumber.properties")), null)


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
