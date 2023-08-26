properties([
  parameters([
    string(name: 'testParam', defaultValue: 'testParamValue')
  ])
])

pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    }
    stages {
        stage('Build and Test'){
            agent{
                docker {
                    image 'maven:3.9.3-eclipse-temurin-17-alpine'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                git(url: 'https://github.com/Bilgehanaygn/beauty_check.git', branch: 'master', credentialsId: 'bilgehanaygn-github')
                sh 'mvn -B -DskipTests clean package'
                sh 'mvn test'
            }
        }
        stage('Deploy'){
            agent none

            steps {
                sh 'docker build -t bilgehanaygn/testrepo .'
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh 'docker push bilgehanaygn/testrepo'
            }
        }
    }

}