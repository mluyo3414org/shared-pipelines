def call(String file, Map defaults) {
  //use the Pipeline Utility Steps plugin readProperties step to read the jenkinsFileParams custom marker file 
  //https://jenkins.io/doc/pipeline/steps/pipeline-utility-steps/#readproperties-read-properties-from-files-in-the-workspace-or-text
  def props = readProperties defaults: defaults, file: file
  for ( e in props ) {
    env.setProperty(e.key, e.value)
  }
}
