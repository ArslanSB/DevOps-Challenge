pipeline {
    agent any
    stages {
        stage('pre -build') {
            steps {
                echo 'Pre-build'
            }
        }
        stage('build') {
            steps {
                echo 'Build in progress.'
            }
        }
        stage('Unit tests') {
            steps {
                echo 'Running unit tests'
            }
        }
        stage('deploy') {
            steps {
                echo 'Deploying build'
            }
        }
        stage('Regression tests') {
            steps {
                echo 'Running E2E tests'
            }
        }
        stage('Release to prod') {
            steps {
                echo 'Releasing to prod'
            }
        }
    }
 post {
        always {
            echo 'Cleanup after everything!'
        }
    }
}
