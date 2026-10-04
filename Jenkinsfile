pipeline {

    agent any

    environment {
        DOCKER_IMAGE = 'manjugowda200523/devops-monitoring-observability'
        MANIFEST_REPO = 'https://github.com/manjugowda-l/devops-monitoring-manifests.git'
    }

    stages {

        stage('Build') {
            steps {
                echo 'Starting Maven build...'

                bat '.\\mvnw.cmd clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'

                bat '.\\mvnw.cmd test'
            }
        }

        stage('SonarQube Analysis') {
            steps {

                withSonarQubeEnv('SonarQube') {

                    bat '.\\mvnw.cmd org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=devops-monitoring-observability'
                }
            }
        }

        stage('Docker Build') {
            steps {

                echo "Building Docker image: ${BUILD_NUMBER}"

                bat "docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} ."
            }
        }

        stage('Docker Push') {
            steps {

                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {

                    bat 'docker login -u "%DOCKER_USERNAME%" -p "%DOCKER_PASSWORD%"'

                    bat "docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}"
                }
            }
        }

        stage('Update Manifest Repo') {

            steps {

                echo "Updating Kubernetes manifest to image ${BUILD_NUMBER}"

                dir('manifests') {

                    git branch: 'main',
                        credentialsId: 'github-manifest-credentials',
                        url: "${MANIFEST_REPO}"

                    bat '''
                        powershell -Command "(Get-Content deployment.yaml) -replace 'image: manjugowda200523/devops-monitoring-observability:[0-9]+', 'image: manjugowda200523/devops-monitoring-observability:%BUILD_NUMBER%' | Set-Content deployment.yaml"
                    '''

                    bat 'git config user.name "Jenkins"'

                    bat 'git config user.email "jenkins@local"'

                    bat 'git add deployment.yaml'

                    bat 'git commit -m "Update image to build %BUILD_NUMBER%"'

                    withCredentials([
                        gitUsernamePassword(
                            credentialsId: 'github-manifest-credentials',
                            gitToolName: 'Default'
                        )
                    ]) {

                        bat 'git push origin main'
                    }
                }
            }
        }
    }

    post {

        success {
            echo 'CI/CD pipeline completed successfully!'
        }

        failure {
            echo 'CI/CD pipeline failed!'
        }
    }
}