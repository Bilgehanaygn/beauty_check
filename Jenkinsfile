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
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]){
                    sh 'echo $USERNAME'
                    sh 'echo ${USERNAME}'
                    sh 'echo $PASSWORD'
                    sh 'echo ${PASSWORD}'
                    sh "docker login -u ${USERNAME} -p ${PASSWORD}"
                    sh 'docker push bilgehanaygn/testrepo'
                }
            }
        }
    }
    post {
        always {
            sh 'docker logout'
        }
    }

}