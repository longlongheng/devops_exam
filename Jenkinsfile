pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *')
    }

    environment {
        REPO_URL = 'https://github.com/longlongheng/devops_exam.git'
        BRANCH_NAME = 'master'
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh '''
                    bash -lc "set -o pipefail; mvn clean package -DskipTests 2>&1 | tee build-output.txt"
                '''
            }
        }

        stage('Test with SQLite') {
            steps {
                sh '''
                    bash -lc "set -o pipefail; mvn clean test 2>&1 | tee test-output.txt"
                '''
            }
        }

        stage('Deploy with Ansible') {
            steps {
                sh '''
                    bash -lc "set -o pipefail; ansible-playbook -i ansible/inventory.ini ansible/deploy.yml 2>&1 | tee ansible-output.txt"
                '''
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'build-output.txt,test-output.txt,ansible-output.txt', allowEmptyArchive: true
        }

        failure {
            emailext(
                subject: "Jenkins Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
Build failed.

Repository:
${env.REPO_URL}

Job:
${env.JOB_NAME}

Build Number:
${env.BUILD_NUMBER}

Check Jenkins console output for details.
""",
                to: 'srengty@gmail.com',
                recipientProviders: [developers()]
            )
        }
    }
}
