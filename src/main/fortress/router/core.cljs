(ns fortress.router.core
  "Client-side routing with reitit.
   Routes are pure data — no side effects until dispatched."
  (:require [re-frame.core :as rf]
            [reitit.frontend :as reitit]
            [reitit.frontend.easy :as rfe]
            [fortress.db.interceptors :as interceptors]))

;; ============================================================
;; Route Definitions (pure data)
;; ============================================================

(def routes
  "Application routes — maps URL paths to route names."
  [["/"          {:name :home}]
   ["/play"      {:name :play}]
   ["/play/:id"  {:name :scenario}]
   ["/result"    {:name :result}]
   ["/history"   {:name :history}]
   ["/coach"     {:name :coach}]])

;; ============================================================
;; Navigation Event
;; ============================================================

(rf/reg-event-db
 ::navigate
 interceptors/fortress-interceptors
 (fn [db [_ route-name]]
   (assoc db :current-route route-name)))

;; ============================================================
;; Router Initialization
;; ============================================================

(defn on-navigate
  "Callback invoked by reitit when the URL changes.
   Dispatches a re-frame event to update :current-route."
  [match _history]
  (when match
    (rf/dispatch [::navigate (get-in match [:data :name])])))

(defn init-router!
  "Initialize the reitit router. Call once at app startup."
  []
  (rfe/start!
   (reitit/router routes)
   on-navigate
   {:use-fragment true}))  ;; Hash-based routing for static hosts
