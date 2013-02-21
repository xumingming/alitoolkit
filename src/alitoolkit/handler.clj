(ns alitoolkit.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.velocity.core :refer [render]]
            [clojure.string :refer [split]]
            [alitoolkit.dubbo :refer [range-add-cargo convert-mid->uid]]))

(defn batch-add-into-purchase-list
  [type id from-offer-id to-offer-id]
  (println "id: " id ", from-offer-id: " from-offer-id ", to-offer-id: " to-offer-id)
  (let [user-id (convert-mid->uid id)]
    (range-add-cargo user-id from-offer-id to-offer-id (int 10))
    "Success!"))

(defroutes app-routes
  (GET "/" [] (render "test.vm"))
  (POST "/purchase/batch-add" {params :params} []
        (let [type (:type params)
              id (:id params)
              from-offer-id (Long/parseLong (:from-offer-id params))
              to-offer-id (Long/parseLong (:to-offer-id params))]
          (batch-add-into-purchase-list type id from-offer-id to-offer-id)))
  (POST "/member/mid-to-uid" {params :params} []
        (let [mid (:mid params)
              uid (convert-mid->uid mid)]
          (str uid)))

  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
