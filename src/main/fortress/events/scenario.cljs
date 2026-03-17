(ns fortress.events.scenario
  "Scenario lifecycle events: load, start, finalize."
  (:require [re-frame.core :as rf]
            [fortress.db.interceptors :as interceptors]
            [fortress.engine.scenarios :as scenarios]))

;; ============================================================
;; Scenario Loading
;; ============================================================

(rf/reg-event-db
 ::load-scenarios
 interceptors/fortress-interceptors
 (fn [db _]
   ;; In higher phases, this might be an HTTP effect.
   ;; For now, load from EDN catalog directly.
   (assoc db :scenarios scenarios/catalog)))

(rf/reg-event-fx
 ::select-scenario
 interceptors/fortress-interceptors
 (fn [{:keys [db]} [_ scenario-id]]
   (let [scenario (first (filter #(= (:id %) scenario-id) (:scenarios db)))]
     (if scenario
       {:db (assoc db
                   :current-scenario scenario
                   :last-decision nil)
        :dispatch-n [[:fortress.router.core/navigate :play]
                     [:fortress.events.timer/start (:time-limit-ms scenario)]]}
       {:db (assoc db :error (str "Scenario not found: " scenario-id))}))))

(rf/reg-event-fx
 ::clear-scenario
 interceptors/fortress-interceptors
 (fn [{:keys [db]} _]
   {:db (assoc db :current-scenario nil)
    :dispatch [:fortress.events.timer/stop]}))
