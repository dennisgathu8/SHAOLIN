(ns fortress.views.result
  "Displays the outcome of a decision, feedback from the engine,
   points earned/lost, and current streak."
  (:require [re-frame.core :as rf]
            [fortress.subs.app :as app-subs]
            [fortress.subs.decision :as decision-subs]))

(defn result-page []
  (let [decision @(rf/subscribe [::decision-subs/last-decision])
        profile  @(rf/subscribe [::app-subs/profile])]
    (fn []
      (if-not decision
        [:div.result-page.anim-fade-in
         [:h2 "No decision found"]
         [:button.btn.btn-primary
          {:on-click #(rf/dispatch [:fortress.router.core/navigate :home])}
          "Return Home"]]
          
        (let [{:keys [timed-out? pass-option scenario-title]} decision
              {:keys [is-optimal? feedback points]} pass-option
              
              ;; Styling logic based on outcome
              success? (and (not timed-out?) is-optimal?)
              
              title-text (if timed-out?
                           "Time Expired!"
                           (if success? "Excellent Decision!" "Needs Improvement"))]
                           
          [:div.result-page.anim-fade-in
           [:div.card {:style {:text-align "center"
                               :border-top (str "4px solid var(--accent-" 
                                                (if timed-out? "red" (if success? "green" "gold")) ")")}}
            [:h2.mb-2 title-text]
            [:h4.text-muted.mb-4 scenario-title]
            
            ;; Points gained/lost
            [:div.points-display
             {:style {:font-size "var(--fs-2xl)"
                      :font-weight "bold"
                      :color (if (or timed-out? (< points 0)) "var(--accent-red)" "var(--accent-green)")
                      :margin-bottom "var(--sp-4)"}}
             (if timed-out? "-3 Points" (str (if (> points 0) "+" "") points " Points"))]
             
            ;; Coach Feedback
            [:div.feedback-box.mb-6
             {:style {:background "var(--bg-secondary)"
                      :padding "var(--sp-4)"
                      :border-radius "var(--radius-md)"}}
             [:p.mb-2 [:strong "Coach's Notes:"]]
             [:p (if timed-out? 
                   "You held the ball too long and were caught in possession." 
                   feedback)]]
                   
            ;; Current Stats Summary
            [:div.flex-between.mb-6
             [:div
              [:p.text-muted "Streak"]
              [:p {:style {:font-weight "bold" :font-size "var(--fs-xl)"}} 
               (str "🔥 " (:current-streak profile))]]
             [:div
              [:p.text-muted "Grade"]
              [:p {:style {:font-weight "bold" :font-size "var(--fs-xl)" :text-transform "capitalize"}}
               (name (:grade profile))]]]
               
            ;; Actions
            [:div.actions
             [:button.btn.btn-primary.w-full
              {:on-click #(rf/dispatch [:fortress.router.core/navigate :home])}
              "Next Scenario"]]]])))))
