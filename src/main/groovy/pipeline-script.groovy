pipeline{
    agent none

    stages {
        stage('Parallel Build') {
            parallel{
                stage('Thread on one machine'){
                    agent any
                    steps {
                        lock('someResource') {
                            echo 'Thread one'
                        }
                    }
                    post {
                        always {
                            echo 'end thread one'
                        }
                    }
                }
                stage('Thread on second machine'){
                    agent any
                    steps {
                        echo 'Thread two'
                    }
                    post {
                        always {
                            echo 'end thread two'
                        }
                    }
                }
            }
        }
    }
}
