#/bin/bash

PROJECT_SUFFIX=$1
SOURCES=$2

VERSION=`mvn  org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version -f src/pom.xml | grep -v '\[' | grep -v Downl`
NAME=`mvn  org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.name -f src/pom.xml | grep -v '\[' | grep -v Downl`
GROUP_ID=`mvn  org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.groupId -f src/pom.xml | grep -v '\[' | grep -v Downl`
ARTIFACT_ID=`mvn  org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.artifactId -f src/pom.xml | grep -v '\[' | grep -v Downl`

PROJECT_KEY="${GROUP_ID}:${ARTIFACT_ID}-${PROJECT_SUFFIX}"
PROJECT_NAME="${NAME}-${PROJECT_SUFFIX}"

echo ${PROJECT_KEY}
echo ${PROJECT_NAME}

../../sonar-runner-2.3/bin/sonar-runner -D sonar.language=${PROJECT_SUFFIX} -D sonar.projectKey=${PROJECT_KEY} -D sonar.projectName=${PROJECT_NAME} -D sonar.projectVersion=${VERSION} -D sonar.sources=${SOURCES}
