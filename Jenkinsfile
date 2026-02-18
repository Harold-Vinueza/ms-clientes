pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "harolitodocker/ms-clientes"
        CONTAINER_NAME = "ms-clientes-container"
        PORT = "9091"
        NETWORK = "proyecto-azure_red-ms"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/Harold-Vinueza/ms-clientes.git'
            }
        }


        stage('Build (Maven)') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:latest .'
            }
        }

        stage('Login DockerHub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                sh 'docker push $DOCKER_IMAGE:latest'
            }
        }

        stage('Deploy (Run Container)') {
            steps {
                sh 'docker rm -f $CONTAINER_NAME || true'
                sh 'docker run -d --network $NETWORK -p $PORT:8081 --name $CONTAINER_NAME $DOCKER_IMAGE:latest'
            }
        }
    }
}
