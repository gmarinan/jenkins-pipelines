def configFile = 'api-pipeline-variables.yml'
def var1 = ''
def var2 = ''
def secVarId = ''

pipeline {
    agent any
    stages {
        stage('Setup Variables') {
            steps {
                script {
                    // Leer el archivo YAML
                    def config = readYaml file: configFile
                    def pipelineConfig = config[env.JOB_NAME]
                    
                    if (pipelineConfig) {
                        var1 = pipelineConfig.var1
                        var2 = pipelineConfig.var2
                        secVarId = pipelineConfig.secVarId
                        echo "Variables asignadas: var1=${var1}, var2=${var2}"
                    } else {
                        error "No configuration found for job ${env.JOB_NAME}"
                    }
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    echo "Usando var1: ${var1} y var2: ${var2} y credencial: ${secVarId}"
                    withCredentials([string(credentialsId: secVarId, variable: 'MY_SECRET_TEXT')]) {
                        // Usar la credencial en comandos o scripts
                        echo "La credencial ha sido cargada y está lista para ser usada."
                        
                        // Ejecutar un comando de shell usando la credencial
                        echo "El token es seguro y no se mostrará en los logs: ${MY_SECRET_TEXT}"
                    }
                }
            }
        }
    }
    post {
        always {
            echo "En post - Usando var1: ${var1} y var2: ${var2}"
        }
    }
}
