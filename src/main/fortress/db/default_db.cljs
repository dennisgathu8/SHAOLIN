(ns fortress.db.default-db
  "Initial app-db value. Conforms to ::schema/app-db spec.
   This is the starting state before any user interaction or
   data is loaded from encrypted storage."
  (:require [fortress.db.schema :as schema]))

(def default-profile
  "Fresh player profile for a new user."
  {:id              "default-player"
   :player-name     "Player"
   :total-decisions 0
   :correct-decisions 0
   :current-streak  0
   :best-streak     0
   :total-points    0
   :history         []
   :grade           :bronze})

(def default-db
  "The initial re-frame app-db. Must conform to ::schema/app-db."
  {:current-route     :home
   :current-scenario  nil
   :timer-running?    false
   :time-remaining-ms 0
   :last-decision     nil
   :scenarios         []
   :profile           default-profile
   :loading?          false
   :error             nil})

;; Compile-time validation (dev only — elided by :advanced)
(assert (schema/valid-db? default-db)
        (str "default-db does not conform to ::app-db: "
             (schema/explain-db default-db)))
