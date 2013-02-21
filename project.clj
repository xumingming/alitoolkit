(defproject alitoolkit "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.5"]
                 [ring.velocity "0.1.2"]
                 [com.alibaba.platform.shared/dubbo.all "2.0-SNAPSHOT"
                  :exclusions [com.alibaba.external/org.slf4j.slf4j-api
                               com.alibaba.external/org.slf4j.slf4j-log4j12]]
                 [com.alibaba.china.shared/marketing.shared "1.0-SNAPSHOT"]
                 [com.alibaba.china.shared/member.service.api "1.0.0-SNAPSHOT"]]
  :resource-paths ["conf"]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler alitoolkit.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
