(ns alitoolkit.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.velocity.core :refer [render]]
            [clojure.string :refer [split]]
            [alitoolkit.dubbo :refer [batch-add-cargo range-add-cargo convert-mid->uid]]))

(defroutes app-routes
  (GET "/" [] (render "test.vm"))
  (POST "/purchase/range-add" {params :params} []
        (println "range-add: params: " params)
        (let [type (:type params)
              id (:id params)
              from-offer-id (Long/parseLong (:from-offer-id params))
              to-offer-id (Long/parseLong (:to-offer-id params))
              user-id (convert-mid->uid id)]
          (range-add-cargo user-id from-offer-id to-offer-id (int 10))
          "Success!"))
  (POST "/purchase/batch-add" {params :params} []
        (println "batch-add: params: " params)
        (let [type (:type params)
              id (:id params)
              offer-ids (:offer-ids params)
              offer-ids (split offer-ids #",")
              offer-ids (map #(Long/parseLong %) offer-ids)
              _ (println "aaa" offer-ids)
              user-id (convert-mid->uid id)]
          (println "user-id: " user-id ", offer-ids: " offer-ids)
          (batch-add-cargo user-id offer-ids (int 10))
          "Success!"))
  
  (POST "/member/mid-to-uid" {params :params} []
        (let [mid (:mid params)
              uid (convert-mid->uid mid)]
          (str uid)))

  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
