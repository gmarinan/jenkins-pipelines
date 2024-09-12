pipeline {
    agent any
    environment {
        var1 = ''
        var2 = ''
    }
    stages {
        stage('Setup Variables') {
            steps {
                script {
                    // Leer el archivo YAML
                    def config = readYaml file: 'api-pipeline-variables.yml'
                    def pipelineConfig = config[env.JOB_NAME]
                    
                    if (pipelineConfig) {
                        echo "Pipeline ${env.JOB_NAME} encontrado"
                        echo "Asignando variables globales"
                        env.var1 = pipelineConfig.var1
                        env.var2 = pipelineConfig.var2
                    } else {
                        error "No configuration found for job ${env.JOB_NAME}"
                    }
                }
            }
        }
        stage('Build') {
            steps {
                echo "Usando var1: ${env.var1} y var2: ${env.var2}"
                // Lógica de construcción aquí
            }
        }
    }
    post {
        always {
            echo "En post - Usando var1: ${env.var1} y var2: ${env.var2}"
            // Aquí puedes usar las variables env.var1 y env.var2
        }
    }
}
