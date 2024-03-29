pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'java8'
        git 'git'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Mouslih1/social-network-microservice'
            }
        }

               stage('Build Maven') {
                   steps {
                       script {
                           // Parcourir les répertoires des microservices et exécuter Maven pour construire
                           def microservices = ['media-service','discovery', 'auth-service', 'feeds-service', 'Friend-service', 'interaction-service', 'notification-service', 'service-post', 'User-service', 'geteway']

                           microservices.each { service ->
                               dir(service) {
                                   checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/sofiawh/social-network']]])
                                   sh "mvn clean install"
                               }
                           }
                       }
                   }
               }
               stage('Run Tests') {
                   steps {
                       script {
                           // Parcourir les répertoires des microservices et exécuter les tests avec Maven
                           def microservices = ['discovery', 'auth-service', 'feeds-service', 'Friend-service', 'interaction-service', 'media-service', 'notification-service', 'service-post', 'User-service', 'geteway']

                           microservices.each { service ->
                               dir(service) {
                                   sh  "mvn test"
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
