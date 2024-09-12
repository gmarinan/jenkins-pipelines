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
                        echo "Asignando variable ${pipelineConfig.var1}"
                        var1 = pipelineConfig.var1
                        var2 = pipelineConfig.var2
                    } else {
                        error "No configuration found for job ${env.JOB_NAME}"
                    }
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    echo "Usando var1: ${var1} y var2: ${var2}"
                    // Lógica de construcción aquí
                }
            }
        }
    }
}