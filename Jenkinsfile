pipelin {
//     agent any
    docker {
        image maven:3.9.3-eclipse-temurin-17-alpine
        args '-v /root/.m2:/root/.m2'
    }
    stages {
        stage('Build'){
            sh 'mvn -B -DskipTests'
        }
    }

}