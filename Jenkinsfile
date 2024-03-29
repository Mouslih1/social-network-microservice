pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'java17'
        git 'git'
    }
     environment {
            DOCKER_CREDENTIALS = 'marouane01'
     }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Mouslih1/social-network-microservice'
            }
        }

       stage('Build') {
           steps {
               script {
                   def microservices = ['discovery','geteway', 'media-service','auth-service', 'Friend-service', 'interaction-service', 'User-service']

                   microservices.each { service ->
                       dir(service) {
                           if (isUnix()) {
                               sh 'mvn clean install'
                           } else {
                               bat 'mvn clean install'
                           }
                       }
                   }
               }
           }
       }


        stage('Docker') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'docker-compose -f Docker-compose.yml up -d --build'
                    } else {
                        bat 'docker-compose -f Docker-compose.yml up -d --build'
                    }
                }
            }
        }

        stage('Report') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn clean site'
                    } else {
                        bat 'mvn clean site'
                    }
                }
            }
        }

        stage('Push to Docker Hub') {
                    steps {
                        script {
                            withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS, usernameVariable: 'marouane01', passwordVariable: 'Mouslih2001@')]) {

                            if (isUnix()) {
                                sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                                sh 'docker-compose -f Docker-compose.yml push'
                            } else {
                                bat 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                                bat 'docker-compose -f Docker-compose.yml push'
                            }
                        }
                    }
                }
    }

    post {
        success {
            echo 'Build succeeded!'
        }

        failure {
            echo 'Build failed!'
        }

        unstable {
            echo 'Build unstable!'
        }
    }
}
