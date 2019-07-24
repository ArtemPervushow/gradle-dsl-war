import javaposse.jobdsl.dsl.*

class GradleJobBuilder {
    String jobName
    String description
    String gitUrl
    String gitBranch
    String gradleTasks
    String switches
    String artifacts = '**/build/libs/*.jar'
    String credentialsId

    Job build(DslFactory factory){
        factory.job(jobName){
            description(this.description)
            logRotator{
                numToKeep 5
            }
            scm {
                git {
                    remote {
                        name('origin')
                        url(this.gitUrl)
                        credentials(this.credentialsId)
                    }
                    branch(this.gitBranch)
                }
            }
            triggers {
                scm '@daily'
            }
            steps {
                gradle gradleTasks, switches, true
            }

            publishers {
                deployPublisher{
                    adapters {
                        tomcat8xAdapter {
                            credentialsId(this.credentialsId)
                            url("http://localhost:8082")
                        }
                        contextPath("build/lib/")
                        onFailure(false)
                        war("gradle-war-1.0-SNAPSHOT.war")
                    }
                }
            }
        }
    }
}

folder("gradle-dsl-war-sample"){
    description 'This is folder for gradle build'
}

GradleJobBuilder builder = new GradleJobBuilder(
        jobName:"Gradle deploy war Task",
        description:"This is gradle deploy tasks",
        gitUrl:"https://github.com/ArtemPervushow/gradle-dsl-war.git",
        gitBranch:"master",
        gradleTasks:"war",
        credentialsId:"cacc3e70-7103-4613-b74b-eaa04c825483")

builder.build(this)
