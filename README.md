# shared-pipelines
---   Work in progress ---

Example for Shared Library + Pipeline Catalog - https://docs.cloudbees.com/docs/admin-resources/latest/pipeline-templates-user-guide/
CloudBees Core Modern Platforms 2.204.3.7 (Managed Master and OC)
Example Application for node: https://github.com/buzz-microservices/frontend-todo (needs to be update)

Currentl structure:
- Three pod templates (node, kaniko,maven) under resources/k8s/agents
- Shared library - this repo (vars/defineProps.yaml) to read build.yaml from application repo https://github.com/buzz-microservices/frontend-todo/blob/master/build.yaml. 
- Template Pipeline Catalog - this repo (templates/node-java) has one Jenkinsfile with its corresponding template.yaml with  parameters used in the Jenkinsfile.


  - name: githubCredentialId
    displayName: GitHub Credential ID
    type: CREDENTIALS
  - name: repo
    displayName: GitHub Repo
    type: string
  - name: repoOwner
    displayName: GitHub Repo Owner
    type: string  
  - name: nexusCredentialId
    displayName: Nexus Credential ID
    type: CREDENTIALS
  - name: applicationName
    displayName: Application Name
    type: string

Process to setup catalog:

1- Create a Managed Master

2- One plugin needed is needed - https://plugins.jenkins.io/pipeline-utility-steps/ to read yaml file in the application repo at runtime (it is optional, we can use readFile instead  and modify shared library). Example yaml file containing application version: https://github.com/buzz-microservices/frontend-todo/blob/master/build.yaml  which  is  used in order to tag the image:
       
       -d gcr.io/na-csa-msuarez/${applicationName}:${version}-${commitHash}
       
{applicationName} comes from the  values in the catalog and {commitHash} derived in the  pipeline.
Install plugin otherwise catalog import will fail.

3- Create pipeline template catalog in your Managed Master instance. Pipeline Template Catalog --> Add Catalog. Select Git/GitHub under Catalog source code repository location and fill the location of the repository with this github's repo.

4- Click "Run Catalog Import Now" and make sure import is successful under the "Import Log".

5- Creating job based on catalog: Click  "New Item" and select "Java/Node Application". Give your job a name.

6- Filling catalog parameters: Write "frontend-todo"  for github repo and "buzz-microservices" for "GitHub Repo Owner". Use your github credentials,  nexus credentials are not used yet. This is the example  application used -https://github.com/buzz-microservices/frontend-todo

7- Job will fail due to missing secret for registry used  for Kaniko - https://github.com/GoogleContainerTools/kaniko#kubernetes-secret
