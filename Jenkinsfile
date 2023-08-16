pipeline {
    agent any
    stages {
        stage('Deploy'){
            agent any
            steps {
                git(url: 'https://github.com/Bilgehanaygn/beauty_check', branch: 'master')
                sh 'docker-compose up -d'
            }
        }
        stage('Build'){
            agent{
                docker {
                    image 'maven:3.9.3-eclipse-temurin-17-alpine'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
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