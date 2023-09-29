pipeline {
    agent {
        kubernetes {
            yaml '''
              apiVersion: v1
              kind: Pod
              metadata:
                name: qlack-webdesktop
                namespace: jenkins
              spec:
                affinity:
                        podAntiAffinity:
                          preferredDuringSchedulingIgnoredDuringExecution:
                          - weight: 50
                            podAffinityTerm:
                              labelSelector:
                                matchExpressions:
                                - key: jenkins/jenkins-jenkins-agent
                                  operator: In
                                  values:
                                  - "true"
                              topologyKey: kubernetes.io/hostname
                securityContext:
                    runAsUser: 0
                    runAsGroup: 0
                containers:
                - name: qlack-webdesktop-builder
                  image: eddevopsd2/maven-java-npm-docker:mvn3.6.3-jdk8-npm6.14.4-docker
                  volumeMounts:
                  - name: maven
                    mountPath: /root/.m2/
                    subPath: qlack-webdesktop
                  tty: true
                  securityContext:
                    privileged: true
                    runAsUser: 0
                    fsGroup: 0
                imagePullSecrets:
                - name: regcred
                volumes:
                - name: maven
                  persistentVolumeClaim:
                    claimName: maven-nfs-pvc
            '''
            workspaceVolume persistentVolumeClaimWorkspaceVolume(claimName: 'workspace-nfs-pvc', readOnly: false)
        }
    }
    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    stages {
        stage('Build') {
            steps {
                container (name: 'qlack-webdesktop-builder'){
                    sh 'mvn clean install'
                }
            }
        }
        stage('Sonar Analysis') {
            steps {
                container (name: 'qlack-webdesktop-builder'){
                    withSonarQubeEnv('sonar'){
                        sh 'update-alternatives --set java /usr/lib/jvm/java-11-openjdk-amd64/bin/java'
                        sh 'mvn sonar:sonar -Dsonar.projectName=Qlack-WebDesktop -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.token=${SONAR_GLOBAL_KEY} -Dsonar.working.directory="/tmp"'
                    }
                }
            }
        }
        stage('Produce bom.xml'){
            steps{
                container (name: 'qlack-webdesktop-builder'){
                    sh 'mvn org.cyclonedx:cyclonedx-maven-plugin:makeAggregateBom'
                }
            }
        }
        stage('Dependency-Track Analysis'){
            steps{
                container (name: 'qlack-webdesktop-builder'){
                    sh '''
                        cat > payload.json <<__HERE__
                        {
                            "project": "f45fcd24-c172-4e15-9c3c-749e651d1385",
                            "bom": "$(cat target/bom.xml |base64 -w 0 -)"
                        }
                        __HERE__
                    '''

                    sh '''
                        curl -X "PUT" ${DEPENDENCY_TRACK_URL} -H 'Content-Type: application/json' -H 'X-API-Key: '${DEPENDENCY_TRACK_API_KEY} -d @payload.json
                    '''
                }
            }
        }
    }
    post {
        changed {
            emailext subject: '$DEFAULT_SUBJECT',
                body: '$DEFAULT_CONTENT',
                to: 'qlack@eurodyn.com'
        }
    }
}