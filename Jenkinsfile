pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './gradlew -DskipTests clean assemble'
      }
    }
    stage('Test') {
      steps {
        sh './gradlew test --tests com.solstice.microstocks.QuoteServiceApplicationUnitTests'
      }
      post {
          always {
              junit 'build/test-results/test/*.xml'
          }
      }
    }
    stage('Deploy') {
      def server = Artifactory.server 'artifactory.udemy.123'
      def uploadSpec = """{
        "files": [
          {
            "pattern": "build/libs/*.jar",
            "target": "gradle-local/"
          }
       ]
      }"""
      server.upload(uploadSpec)
      echo 'Successfully deployed to Artifactory'
    }
  }
}