(ns fortress.views.app
  "Root view component — router outlet.
   Renders the active page based on :current-route."
  (:require [re-frame.core :as rf]
            [fortress.subs.app :as app-subs]
            [fortress.views.home :as home]
            [fortress.views.play :as play]
            [fortress.views.result :as result]
            [fortress.views.history :as history]
            [fortress.views.coach :as coach]))

(defn- page-for
  "Map route keyword to page component."
  [route]
  (case route
    :home     [home/home-page]
    :play     [play/play-page]
    :result   [result/result-page]
    :history  [history/history-page]
    :coach    [coach/coach-page]
    [home/home-page]))

(defn root-view
  "Application root component. Wraps page in layout."
  []
  (let [route    @(rf/subscribe [::app-subs/current-route])
        loading? @(rf/subscribe [::app-subs/loading?])
        error    @(rf/subscribe [::app-subs/error])]
    [:div.app-root
     ;; Header
     [:header.app-header
      [:div.container.flex-between
       [:h1.app-title "⚽ FKF Simulator"]
       [:span.grade-badge
        {:aria-label "Player grade"}
        (name @(rf/subscribe [::app-subs/grade]))]]]

     ;; Error banner
     (when error
       [:div.error-banner.container
        {:role "alert"}
        [:p error]
        [:button.btn
         {:on-click #(rf/dispatch [:fortress.events.app/clear-error])}
         "Dismiss"]])

     ;; Loading overlay
     (when loading?
       [:div.loading-overlay.flex-center
        [:p.anim-pulse "Loading…"]])

     ;; Page content
     [:main.app-main.container
      (page-for route)]

     ;; Footer
     [:footer.app-footer
      [:div.container
       [:p.text-muted "Clojure Fortress • 100% Offline • Free & Open Source"]]]]))
