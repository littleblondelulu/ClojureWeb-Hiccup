(defproject clojure-web "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.8.0"]
               [ring "1.4.0"]
               [hiccup "1.0.5"]
               [compojure "1.5.0"]]
  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :aot [clojure-web.core]
  :main clojure-web.core)
