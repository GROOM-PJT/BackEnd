pipeline {
  agent any
  environment {
    dockerHubRegistry = 'jeeseob/gromm_beckend'
    dockerHubRegistryCredential = 'docker-credential'
  }
  stages {
    stage('Checkout Application Git Branch') {
        steps {
           script {
                    SLACK_CHANNEL = "jenkins"
                    SLACK_SUCCESS_COLOR = "#2C953C";
                    SLACK_FAIL_COLOR = "#FF3232";
                    // Git Commit 계정
                    GIT_COMMIT_AUTHOR = sh(script: "git --no-pager show -s --format=%an ${env.GIT_COMMIT}", returnStdout: true).trim();
                    // Git Commit 메시지
                    GIT_COMMIT_MESSAGE = sh(script: "git --no-pager show -s --format=%B ${env.GIT_COMMIT}", returnStdout: true).trim();
            }
            git credentialsId: 'github-credential',
            url: 'https://github.com/GROOM-PJT/BackEnd',
            branch: 'Test/Jenkins'
        }
        post {
                failure {
                  echo 'Repository clone failure !'
                }
                success {
                  echo 'Repository clone success !'
                  slackSend (
                        channel: SLACK_CHANNEL,
                        color: SLACK_SUCCESS_COLOR,
                        message: "==================================================================\n배포 파이프라인이 시작되었습니다.\n${GIT_COMMIT_AUTHOR} - ${GIT_COMMIT_MESSAGE}\n==================================================================\n"
                    )
                }
        }
    }

   stage('Gradle Jar Build') {
    agent any
        steps {
            echo 'Bulid Gradle'
            dir ('.'){
                // application-pri.yaml 추가하는 부분과 local을 RDS로 변경하는 등 몇가지 보완 필요.
                sh """
                chmod +x gradlew
                ./gradlew clean build --exclude-task test
                """
            }
        }
        post {
            failure {
                error 'This pipeline stops gradle Jar Build'
                slackSend (
                    channel: SLACK_CHANNEL,
                    color: SLACK_FAIL_COLOR,
                    message: "Gradle Build Failure!\n==================================================================\n"
                )
            }
            success {
                echo 'Gradle Build Success!'
                slackSend (
                    channel: SLACK_CHANNEL,
                    color: SLACK_SUCCESS_COLOR,
                    message: "Gradle Build Success!\n"
                )
            }
        }
    }

    stage('Docker Image Build') {
    agent any
        steps {
            sh "docker build . -t ${dockerHubRegistry}:${currentBuild.number}"
            sh "docker build . -t ${dockerHubRegistry}:latest "
        }
        post {
            failure {
                echo 'Docker image build failure !'
                slackSend (
                    channel: SLACK_CHANNEL,
                    color: SLACK_FAIL_COLOR,
                    message: "Docker Image build Failure!\n==================================================================\n"
                )
            }
            success {
                echo 'Docker image build success !'
                slackSend (
                    channel: SLACK_CHANNEL,
                    color: SLACK_SUCCESS_COLOR,
                    message: "Docker Image Build Success!\n"
                )
            }
        }
    }

    stage('Docker Image Push') {
        steps {
            script {
                    sh "echo ${dockerHubRegistryCredential} | docker login -u jeeseob --password-stdin"
                    sh "docker push ${dockerHubRegistry}:${currentBuild.number}"
                    // docker.withRegistry('https://registry.hub.docker.com', dockerHubRegistryCredential){
                    //     appImage.push("${currentBuild.number}")
                    //     appImage.push("latest")
                    // }
                }
        }
        post {
            failure {
                echo 'Docker Image Push failure !'
                sh "docker rmi ${dockerHubRegistry}:${currentBuild.number}"
                sh "docker rmi ${dockerHubRegistry}:latest"
                slackSend (
                    channel: SLACK_CHANNEL,
                    color: SLACK_FAIL_COLOR,
                    message: "Docker Image Push Failure!\n==================================================================\n"
                )
            }
            success {
                echo 'Docker image push success !'
                sh "docker rmi ${dockerHubRegistry}:${currentBuild.number}"
                sh "docker rmi ${dockerHubRegistry}:latest"
                slackSend (
                    channel: SLACK_CHANNEL,
                    color: SLACK_SUCCESS_COLOR,
                    message: "Docker Image Push Success!\n"
                )
            }
        }
    }

    stage('K8S Manifest Update') {
        steps {
            git credentialsId: 'github-credential',
            url: 'https://github.com/GROOM-PJT/gitOps',
            branch: 'main'
            withCredentials([usernamePassword(credentialsId: 'github-credential', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]){
            sh("""
                pwd
                git config --local credential.helper "!f() { echo username=\\$GIT_USERNAME; echo password=\\$GIT_PASSWORD; }; f"
                git checkout main
                sed -i 's/groom_backend:*[0-9]\$/groom_backend:${currentBuild.number}/g' deployment.yaml
                git commit -am  "UPDATE: deployment-gromm_beckend ${currentBuild.number} image versioning"
                git push origin main
            """)
            }

        }
        post {
            failure {
                echo 'K8S Manifest Update failure !'
                slackSend (
                    channel: SLACK_CHANNEL,
                    color: SLACK_FAIL_COLOR,
                    message: "GitOps Repository Update Failure!\n==================================================================\n"
                )
            }
            success {
                echo 'K8S Manifest Update success !'
                slackSend (
                    channel: SLACK_CHANNEL,
                    color: SLACK_SUCCESS_COLOR,
                    message: "GitOps Repository Update Success!\n==================================================================\n"
                )
            }
        }
    }
}
}

