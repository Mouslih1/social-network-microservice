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

        stage('Build') {
            steps {
                script {
                    def microservices = ['discovery', 'auth-service', 'Friend-service', 'interaction-service', 'User-service', 'gateway']

                    microservices.each { service ->
                        dir(service) {
                            checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Mouslih1/social-network-microservice']]])
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
