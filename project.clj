(defproject storm-starter/storm-starter "0.0.1-SNAPSHOT"
  :dependencies [
;;    [org.twitter4j/twitter4j-core "2.2.6-SNAPSHOT"]
;;    [org.twitter4j/twitter4j-stream "2.2.6-SNAPSHOT"]
  ]
  :source-paths ["src/clj"]
  :profiles {:dev
             {:dependencies
              [[storm "0.8.1"] [org.clojure/clojure "1.4.0"]]}}
  :repositories {
;;    "twitter4j" "http://twitter4j.org/maven2"
  }
  :resource-paths ["multilang"]
  :aot :all
  :java-source-paths ["src/jvm"]
  :min-lein-version "2.0.0"
  :javac-options {:debug "true"})