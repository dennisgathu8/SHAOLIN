(ns fortress.events.decision
  "Event handlers for player decisions (clicking a pass target or timing out)."
  (:require [re-frame.core :as rf]
            [fortress.db.interceptors :as interceptors]
            [fortress.engine.scoring :as scoring]
            [fortress.config :as config]))

(rf/reg-event-fx
 ::evaluate
 interceptors/fortress-interceptors
 (fn [{:keys [db]} [_ target-player-id]]
   (let [scenario (:current-scenario db)
         profile  (:profile db)
         
         ;; Find the pass option corresponding to the clicked target
         selected-opt (first (filter #(= (:target-player-id %) target-player-id)
                                     (:pass-options scenario)))
                                     
         is-optimal? (:is-optimal? selected-opt)
         points-won  (:points selected-opt)
                                     
         ;; Update player profile
         updated-profile (scoring/apply-decision profile selected-opt)
         
         ;; Create the decision record conforming to ::schema/decision-record
         decision-record {:scenario-id (:id scenario)
                          :chosen-option-id (:id selected-opt)
                          :timestamp (.getTime (js/Date.))
                          :time-taken-ms (- (:time-limit-ms scenario) (:time-remaining-ms db))
                          :was-correct? is-optimal?
                          :points-earned points-won
                          
                          ;; Extra view helpers (not in strict spec, so we keep them separated or valid)
                          :scenario-title (:title scenario)
                          :pass-option selected-opt
                          :timed-out? false}]
                          
     {:db (-> db
              (assoc :profile updated-profile
                     :last-decision decision-record
                     :current-scenario nil)
              (update-in [:profile :history] conj decision-record))
      :dispatch-n [[:fortress.events.timer/stop]
                   [:fortress.events.persistence/save-profile]
                   [:fortress.router.core/navigate :result]]})))

(rf/reg-event-fx
 ::time-expired
 interceptors/fortress-interceptors
 (fn [{:keys [db]} _]
   (let [scenario (:current-scenario db)
         profile  (:profile db)
         
         ;; Update player profile with a nil option (timeout penalty)
         updated-profile (scoring/apply-decision profile nil)
         
         decision-record {:scenario-id (:id scenario)
                          :chosen-option-id "timeout"  ;; Must be string for ::id spec > 3 chars
                          :timestamp (.getTime (js/Date.))
                          :time-taken-ms (:time-limit-ms scenario)
                          :was-correct? false
                          :points-earned config/timeout-penalty
                          
                          :scenario-title (:title scenario)
                          :pass-option nil
                          :timed-out? true}]
                          
     {:db (-> db
              (assoc :profile updated-profile
                     :last-decision decision-record
                     :current-scenario nil)
              (update-in [:profile :history] conj decision-record))
      :dispatch-n [[:fortress.events.persistence/save-profile]
                   [:fortress.router.core/navigate :result]]})))
