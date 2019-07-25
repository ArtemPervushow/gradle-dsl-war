import javaposse.jobdsl.dsl.*
import javaposse.jobdsl.*

folder('call-pipeline-folder'){
    description('This is folder for calling pipeline')
}

pipelineJob('call-pipeline-from-dsl-job'){
    definition {
        cps {
            script(readFileFromWorkspace('src/main/groovy/pipeline-script.groovy'))
            sanbox()
        }
    }
}
