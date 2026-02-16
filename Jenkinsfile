pipeline {
    agent any

    environment {
        IMAGE_NAME = "ms-clientes"
        CONTAINER_NAME = "ms-clientes-container"
        PORT = "9091"
    }

    stages {

        stage('Construir proyecto') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Construir imagen Docker') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Eliminar contenedor anterior') {
            steps {
                sh 'docker rm -f $CONTAINER_NAME || true'
            }
        }

        stage('Levantar contenedor') {
            steps {
                sh 'docker run -d -p $PORT:8080 --name $CONTAINER_NAME $IMAGE_NAME'
            }
        }
    }
}
