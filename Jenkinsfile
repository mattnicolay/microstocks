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
              junit 'target/surefire-reports/*.xml'
          }
      }
    }
  }
}