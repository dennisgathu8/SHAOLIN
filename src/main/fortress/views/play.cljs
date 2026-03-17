(ns fortress.views.play
  "Play View — Displays the active scenario and pitch.
   Dispatches decisions when a target is clicked."
  (:require [re-frame.core :as rf]
            [fortress.views.pitch :refer [pitch]]
            [fortress.subs.scenario :as scenario-subs]
            [fortress.subs.timer :as timer-subs]
            [fortress.views.components.countdown :refer [countdown-ring]]))

;; ============================================================
;; View Component
;; ============================================================

(defn play-page []
  (let [scenario      @(rf/subscribe [::scenario-subs/current-scenario])
        remaining-ms  @(rf/subscribe [::timer-subs/remaining-ms])
        timer-running @(rf/subscribe [::timer-subs/running?])]
        
    (if-not scenario
      [:div.play-page
       [:h2 "No active scenario"]
       [:button.btn.btn-primary
        {:on-click #(rf/dispatch [:fortress.router.core/navigate :home])}
        "Return Home"]]
        
      [:div.play-page
       [:div.scenario-header.card.anim-fade-in
        [:div.flex-between
         [:h2 (:title scenario)]
         ;; Timer Overlay
         [countdown-ring {:total-ms (:time-limit-ms scenario) 
                          :remaining-ms remaining-ms}]]
        [:p.text-muted (:description scenario)]]
        
       ;; The Pitch Component
       [:div.pitch-wrapper.anim-fade-in
        {:style {:margin-top "var(--sp-4)"}}
        [pitch {:scenario scenario
                :on-decision-select (fn [opt-id]
                                      (when timer-running
                                        (rf/dispatch [:fortress.events.decision/evaluate opt-id])))}]]])))
