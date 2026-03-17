(ns fortress.views.home
  "Home / landing page — scenario selector and player stats.
   Phase 0: shows welcome message and app status.
   Phase 2+: will show scenario cards."
  (:require [re-frame.core :as rf]
            [fortress.subs.app :as app-subs]
            [fortress.subs.scenario :as scenario-subs]
            [fortress.config :as config]))

(defn- stats-card
  "Player stats summary card."
  []
  (let [profile @(rf/subscribe [::app-subs/profile])]
    [:div.card.stats-card.anim-fade-in
     [:h3 "Your Stats"]
     [:div.stats-grid
      [:div.stat-item
       [:span.stat-value (str (:total-points profile))]
       [:span.stat-label "Points"]]
      [:div.stat-item
       [:span.stat-value (str (:current-streak profile))]
       [:span.stat-label "Streak"]]
      [:div.stat-item
       [:span.stat-value (str (:correct-decisions profile) "/" (:total-decisions profile))]
       [:span.stat-label "Accuracy"]]
      [:div.stat-item
       [:span.stat-value (name (:grade profile))]
       [:span.stat-label "Grade"]]]
       
     [:div {:style {:display "flex" :gap "var(--sp-4)" :margin-top "var(--sp-6)"}}
      [:button.btn.btn-secondary
       {:on-click #(rf/dispatch [:fortress.router.core/navigate :history])
        :style {:flex 1}}
       "View History Ledger"]
      [:button.btn.btn-secondary
       {:on-click #(rf/dispatch [:fortress.router.core/navigate :coach])
        :style {:flex 1}}
       "Coach Analysis Mode"]]]))

(defn- scenario-selector
  "List of available scenarios."
  []
  (let [scenarios @(rf/subscribe [::scenario-subs/scenarios])]
    [:div.scenario-list.flex-col
     {:style {:gap "var(--sp-4)" :margin-top "var(--sp-6)"}}
     [:h3 "Available Scenarios"]
     (if (empty? scenarios)
       [:p.text-muted "Loading scenarios..."]
       (for [s scenarios]
         ^{:key (:id s)}
         [:div.card.scenario-card
          {:style {:cursor "pointer" :transition "transform var(--transition-fast)"}
           :on-click #(rf/dispatch [:fortress.events.scenario/select-scenario (:id s)])}
          [:div.flex-between
           [:h3 (:title s)]
           [:span.badge
            {:class (case (:difficulty s)
                      :beginner "text-green"
                      :intermediate "text-gold"
                      :advanced "text-red"
                      "text-muted")}
            (name (:difficulty s))]]
          [:p.text-muted {:style {:font-size "var(--fs-sm)"}}
           (:description s)]
          [:p {:style {:font-size "var(--fs-sm)" :margin-top "var(--sp-2)"}}
           "⏱️ " (/ (:time-limit-ms s) 1000) "s"]]))]))

(defn- welcome-banner
  "Welcome message and quickstart."
  []
  [:div.card.welcome-banner.anim-fade-in
   [:h2 "Build From The Back 🏟️"]
   [:p "Train your decision-making under press. Choose the right pass,
        beat the press, and build your way to legend status."]
   [:p.text-muted (str "v" config/version " • Phase 2 — Timer & Scenarios")]])

(defn home-page
  "Home page root component."
  []
  (fn []
    [:div.home-page
     [welcome-banner]
     [stats-card]
     [scenario-selector]]))
