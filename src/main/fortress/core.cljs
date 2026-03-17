(ns fortress.core
  "Application entry point — Clojure Fortress.
   Initializes re-frame, mounts Reagent root, starts router."
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [fortress.events.app :as app-events]
            [fortress.events.persistence]
            [fortress.subs.app]
            [fortress.router.core :as router]
            [fortress.views.app :as views]))

(defn mount-root!
  "Mount the Reagent root component to the DOM.
   Called on init and on hot-reload."
  []
  (rf/clear-subscription-cache!)
  (rdom/render [views/root-view]
               (.getElementById js/document "app")))

(defn init!
  "Application init — called once on page load.
   1. Initialize re-frame db
   2. Start router
   3. Mount Reagent root"
  []
  (rf/dispatch-sync [::app-events/initialize-db])
  (router/init-router!)
  (mount-root!)
  (js/console.log "🏰 Clojure Fortress initialized — v0.1.0"))
