properties([
  parameters([
    string(name: 'testParam', defaultValue: 'testParamValue')
  ])
])

pipeline {
    agent any
    stages {
        stage('Build'){
            agent{
                docker {
                    image 'maven:3.9.3-eclipse-temurin-17-alpine'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                echo "HERE IT IS: ${params.testParam}"
                git(url: 'https://github.com/Bilgehanaygn/beauty_check', branch: 'master')
                sh 'mvn -B -DskipTests clean package'
                sh 'mvn test'
            }
        }
        stage('Deploy'){
            agent none

            steps{
                sh 'docker-compose up -d'
            }
        }
    }

}