(ns fortress.events.timer
  "Timer event handlers.
   Manages the scenario countdown."
  (:require [re-frame.core :as rf]
            [fortress.db.interceptors :as interceptors]
            [fortress.config :as config]
            [fortress.effects.timer]))  ;; Ensures the FX is registered

;; ============================================================
;; Scenario Timer
;; ============================================================

(rf/reg-event-fx
 ::start
 interceptors/fortress-interceptors
 (fn [{:keys [db]} [_ time-limit-ms]]
   {:db (assoc db
               :timer-running? true
               :time-remaining-ms time-limit-ms)
    :start-timer {:tick-ms (:tick-ms config/timer-defaults)
                  :dispatch-event [::tick]}}))

(rf/reg-event-fx
 ::stop
 interceptors/fortress-interceptors
 (fn [{:keys [db]} _]
   {:db (assoc db :timer-running? false)
    :stop-timer nil}))

(rf/reg-event-fx
 ::tick
 interceptors/fortress-interceptors
 (fn [{:keys [db]} _]
   (if (:timer-running? db)
     (let [remaining (- (:time-remaining-ms db) (:tick-ms config/timer-defaults))]
       (if (<= remaining 0)
         ;; Timer expired!
         {:db (assoc db
                     :time-remaining-ms 0
                     :timer-running? false)
          :stop-timer nil
          :dispatch [:fortress.events.decision/time-expired]}
         
         ;; Normal tick
         {:db (assoc db :time-remaining-ms remaining)}))
     ;; Ignore tick if stopped (race condition catch)
     {})))
