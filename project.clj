(defproject storm-starter "0.0.1-SNAPSHOT"
  :source-paths ["src/clj"]
  :java-source-paths ["src/jvm"]
  :scala-source-path "src/scala"
  :resource-paths ["multilang"]
  :aot :all
  :repositories {
;;                 "twitter4j" "http://twitter4j.org/maven2"
                 }

  :dependencies [
;;                 [org.twitter4j/twitter4j-core "2.2.6-SNAPSHOT"]
;;                 [org.twitter4j/twitter4j-stream "2.2.6-SNAPSHOT"]
                   [commons-collections/commons-collections "3.2.1"]
                 ]

  :profiles {:dev
              {:dependencies [[storm "0.8.2"]
                              [org.clojure/clojure "1.4.0"]
                              [org.scala-lang/scala-library "2.10.1"]]}}
  :min-lein-version "2.0.0"
  :plugins [[lein-scalac "0.1.0"]]
  :prep-tasks ["scalac"]
  )
