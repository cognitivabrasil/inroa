import groovy.text.SimpleTemplateEngine

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
	'ppaSuffix': ppaSuffix,
	'nightly' : formattedDate,
	'tomcat' : 'tomcat7'
]

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
