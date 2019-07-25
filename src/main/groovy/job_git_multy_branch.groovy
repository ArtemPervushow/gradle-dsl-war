import groovy.json.JsonSlurper

folder('sample-github-branches'){
    description('This is github branches sample folder')
}

URL gitAPIUrl = "https://api.github.com/repos/getgauge-examples/java-gradle-selenium/branches".toURL()
List branches = new JsonSlurper().parse(gitAPIUrl.newReader())

branches.each { currentBranch ->
    String branchName = currentBranch.name.replaceAll('/','-')

    folder "sample-github-branches/$branchName"

    job("sample-github-branches/$branchName/build"){
        scm {
            github "getgauge-examples/java-gradle-selenium", currentBranch.name
        }
        steps {
            gradle 'assemble'
        }
    }
}