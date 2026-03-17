(ns fortress.views.history
  "History View — Displays past performance records and streaks."
  (:require [re-frame.core :as rf]
            [fortress.subs.app :as app-subs]))

(defn history-page []
  (let [profile @(rf/subscribe [::app-subs/profile])]
    (fn []
      [:div.history-page.anim-fade-in
       [:h2.mb-4 "Player Journey"]
       
       ;; Summary Metrics
       [:div.card.mb-6 {:style {:display "flex" :justify-content "space-around"}}
        [:div {:style {:text-align "center"}}
         [:p.text-muted "Grade"]
         [:p {:style {:font-weight "bold" :font-size "var(--fs-2xl)" :text-transform "capitalize"}}
          (name (:grade profile))]]
        [:div {:style {:text-align "center"}}
         [:p.text-muted "Total Points"]
         [:p {:style {:font-weight "bold" :font-size "var(--fs-2xl)"}}
          (:total-points profile)]]
        [:div {:style {:text-align "center"}}
         [:p.text-muted "Best Streak"]
         [:p {:style {:font-weight "bold" :font-size "var(--fs-2xl)"}}
          (str "🔥 " (:best-streak profile))]]]
          
       ;; History Ledger
       [:h3.mb-4 "Recent Decisions"]
       (if (empty? (:history profile))
         [:p.text-muted "No decisions recorded yet. Play a scenario to start building your streak!"]
         
         [:div.history-list
          (for [record (reverse (:history profile))]
            ^{:key (:timestamp record)}
            [:div.card.mb-2
             {:style {:display "flex"
                      :justify-content "space-between"
                      :align-items "center"
                      :border-left (str "4px solid " 
                                        (if (:was-correct? record) "var(--accent-green)" "var(--accent-red)"))}}
             [:div
              [:p {:style {:font-weight "bold"}} (:scenario-title record)]
              [:p.text-muted {:style {:font-size "var(--fs-xs)"}}
               (str (Math/round (/ (:time-taken-ms record) 1000.0)) "s elapsed")]]
             [:div {:style {:text-align "right"}}
              [:p {:style {:font-weight "bold"
                           :color (if (> (:points-earned record) 0) "var(--accent-green)" "var(--accent-red)")}}
               (str (if (> (:points-earned record) 0) "+" "") (:points-earned record))]
              (when (:timed-out? record)
                [:span.text-muted {:style {:font-size "var(--fs-xs)"}} "Timeout"])]])])
                
       ;; Actions
       [:div.actions.mt-6
        [:button.btn.btn-primary.w-full
         {:on-click #(rf/dispatch [:fortress.router.core/navigate :home])}
         "Back to Training"]]])))
