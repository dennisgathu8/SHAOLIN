(ns fortress.db.interceptors
  "re-frame interceptors for Clojure Fortress security.
   The spec-checking interceptor is registered on EVERY event
   to ensure the app-db never enters an invalid state."
  (:require [re-frame.core :as rf]
            [fortress.db.schema :as schema]))

(def spec-check
  "Interceptor that validates app-db against ::schema/app-db
   after every event handler runs. In dev mode, throws on failure.
   In production (:advanced), asserts are elided but the interceptor
   still logs to console.warn."
  (rf/after
   (fn [db]
     (when-not (schema/valid-db? db)
       (let [explain (schema/explain-db db)]
         (js/console.warn "⚠️ Fortress: app-db spec violation!" (pr-str explain))
         ;; In dev builds, assert will throw. In release, it's elided.
         (assert false
                 (str "app-db spec violation: " (pr-str explain))))))))

(def fortress-interceptors
  "Standard interceptor chain for all fortress events.
   Add this to every reg-event-db / reg-event-fx."
  [spec-check])
