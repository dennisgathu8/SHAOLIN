(ns fortress.views.coach
  "Coach Analysis View — Provides detailed metrics, PDF export,
   and EDN export for performance reviews."
  (:require [re-frame.core :as rf]
            [fortress.subs.app :as app-subs]
            [fortress.engine.export :as export]))

(defn coach-page []
  (let [profile @(rf/subscribe [::app-subs/profile])]
    (fn []
      [:div.coach-page.anim-fade-in
       [:h2.mb-4 "Coach Analysis Panel"]
       
       [:div.card.mb-6
        [:h3.mb-2 "Player Metrics"]
        [:table {:style {:width "100%" :text-align "left" :margin-top "var(--sp-2)"}}
         [:tbody
          [:tr
           [:th {:style {:padding-bottom "var(--sp-2)"}} "Total Decisions Evaluated:"]
           [:td {:style {:padding-bottom "var(--sp-2)"}} (:total-decisions profile)]]
          [:tr
           [:th {:style {:padding-bottom "var(--sp-2)"}} "Correct Decisions:"]
           [:td {:style {:padding-bottom "var(--sp-2)"}} (:correct-decisions profile)]]
          [:tr
           [:th "Accuracy:"]
           [:td (if (pos? (:total-decisions profile))
                  (str (Math/round (* 100.0 (/ (:correct-decisions profile) (:total-decisions profile)))) "%")
                  "N/A")]]]]]
                  
       [:div.card.mb-6
        [:h3.mb-4 "Export Data"]
        [:p.text-muted.mb-4 "Download reports for external analysis or sharing with the technical team."]
        
        [:div {:style {:display "flex" :gap "var(--sp-4)"}}
         [:button.btn.btn-primary
          {:on-click #(export/export-pdf profile)
           :style {:flex 1 :background "var(--accent-gold)" :border-color "var(--accent-gold)" :color "black"}}
          "📄 Export PDF Report"]
          
         [:button.btn.btn-secondary
          {:on-click #(export/export-edn profile)
           :style {:flex 1}}
          "💾 Export Raw EDN"]]]
          
       ;; Actions
       [:div.actions.mt-6
        [:button.btn.btn-secondary.w-full
         {:on-click #(rf/dispatch [:fortress.router.core/navigate :home])}
         "Back to Main Menu"]]])))
