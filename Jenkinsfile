pipeline {
    stages {
        agent any
        stage('Checkout Code'){
            steps {
                git(url: 'https://github.com/Bilgehanaygn/beauty_check', branch: 'master')
            }
        }
        docker {
            image 'maven:3.9.3-eclipse-temurin-17-alpine'
            args '-v /root/.m2:/root/.m2'
        }
        stage('Build'){
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test'){
            steps {
                sh 'mvn test'
            }
        }

        stage('Deploy'){
            agent any

            steps{
                sh 'docker-compose up -d'
            }
        }
    }

}