(ns alitoolkit.dubbo
  (:import [org.springframework.context.support ClassPathXmlApplicationContext])
  (:import [com.alibaba.china.marketing.service PurchaseService])
  (:import [com.alibaba.china.marketing.param PurchaseAddParam])
  (:import [com.alibaba.china.marketing.model PurchaseModel])

  (:import [com.alibaba.china.member.service MemberReadService])
  (:import [com.alibaba.china.member.service.models MemberModel]))

;; ===== DUBBO ====
(def context (atom nil))
(defn init []
  (let [tmp (ClassPathXmlApplicationContext. (into-array String ["dubbo.xml"]))
        _ (reset! context tmp)]
    (.start @context)))

(defn get-service
  [service-name]
  (.getBean @context service-name))

;; init spring context
(init)

;; ==== cargo ====
(defn add-cargo
  [user-id offer-id cnt]
  (let [^PurchaseService purchaseService (get-service "purchaseService")
        param (doto (PurchaseAddParam.)
                (.add offer-id cnt)
                (.setUserId user-id))
        ^PurchaseModel model (.addCargo purchaseService param)]
    model))

(defn batch-add-cargo
  [user-id offer-ids cnt]
  (doseq [offer-id offer-ids]
    (add-cargo user-id offer-id cnt)))

(defn range-add-cargo
  [user-id range-start range-end cnt]
  (let [offer-ids (range range-start range-end)]
    (batch-add-cargo user-id offer-ids cnt)))

;; ==== member ====

(defn convert-mid->uid
  [member-id]
  (let [^MemberReadService service (get-service "memberReadService")
        ^MemberModel member (.findMember service member-id)
        user-id (.getUserId member)]
    user-id))

(comment
  (add-cargo 2041560969 1282967360 (int 7))
  (convert-mid->uid "xummsell"))

