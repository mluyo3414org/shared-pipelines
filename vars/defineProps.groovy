def call(String file, Map defaults) {
  //use the Pipeline Utility Steps plugin readProperties step to read the jenkinsFileParams custom marker file 
  def props = readProperties defaults: defaults, file: file
  for ( e in props ) {
    env.setProperty(e.key, e.value)
  }
}
