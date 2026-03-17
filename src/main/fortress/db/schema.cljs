(ns fortress.db.schema
  "Clojure.spec.alpha definitions for the entire app-db.
   Every data contract is defined here. The spec-checking interceptor
   validates the full ::app-db spec after EVERY re-frame event."
  (:require [clojure.spec.alpha :as s]))

;; ============================================================
;; Primitives
;; ============================================================

(s/def ::id (s/and string? #(re-matches #"^[a-z0-9\-]{3,36}$" %)))
(s/def ::timestamp pos-int?)
(s/def ::percentage (s/and number? #(<= 0 % 100)))

;; ============================================================
;; Position on Pitch (percentage-based for SVG viewBox)
;; ============================================================

(s/def ::x (s/and number? #(<= 0 % 100)))
(s/def ::y (s/and number? #(<= 0 % 100)))
(s/def ::position (s/keys :req-un [::x ::y]))

;; ============================================================
;; Player
;; ============================================================

(s/def ::player-name (s/and string? #(<= 1 (count %) 50)))
(s/def ::player-role #{:gk :cb :fb :cdm :cm :cam :winger :st})
(s/def ::shirt-number (s/and int? #(<= 1 % 99)))
(s/def ::pressing? boolean?)

(s/def ::player
  (s/keys :req-un [::id ::player-name ::player-role
                   ::shirt-number ::position ::pressing?]))

;; ============================================================
;; Pass Option
;; ============================================================

(s/def ::target-player-id ::id)
(s/def ::pass-type #{:short :long :through :back :switch})
(s/def ::risk-level #{:low :medium :high})
(s/def ::is-optimal? boolean?)
(s/def ::feedback string?)
(s/def ::points (s/and int? #(<= -10 % 10)))

(s/def ::pass-option
  (s/keys :req-un [::id ::target-player-id ::pass-type
                   ::risk-level ::is-optimal? ::feedback ::points]))

;; ============================================================
;; Scenario
;; ============================================================

(s/def ::title (s/and string? #(<= 1 (count %) 100)))
(s/def ::description string?)
(s/def ::difficulty #{:beginner :intermediate :advanced :elite})
(s/def ::formation string?)
(s/def ::time-limit-ms (s/and pos-int? #(<= 1000 % 30000)))
(s/def ::ball-carrier-id ::id)
(s/def ::teammates (s/coll-of ::player :min-count 1 :max-count 10))
(s/def ::opponents (s/coll-of ::player :min-count 1 :max-count 11))
(s/def ::pass-options (s/coll-of ::pass-option :min-count 2 :max-count 6))
(s/def ::coaching-note string?)

(s/def ::scenario
  (s/keys :req-un [::id ::title ::description ::difficulty ::formation
                   ::time-limit-ms ::ball-carrier-id ::teammates
                   ::opponents ::pass-options ::coaching-note]))

;; ============================================================
;; Decision Record
;; ============================================================

(s/def ::scenario-id ::id)
(s/def ::chosen-option-id ::id)
(s/def ::time-taken-ms nat-int?)
(s/def ::was-correct? boolean?)
(s/def ::points-earned ::points)

(s/def ::decision-record
  (s/keys :req-un [::scenario-id ::chosen-option-id ::timestamp
                   ::time-taken-ms ::was-correct? ::points-earned]))

;; ============================================================
;; Player Profile
;; ============================================================

(s/def ::total-decisions nat-int?)
(s/def ::correct-decisions nat-int?)
(s/def ::current-streak nat-int?)
(s/def ::best-streak nat-int?)
(s/def ::total-points int?)
(s/def ::history (s/coll-of ::decision-record))
(s/def ::grade #{:bronze :silver :gold :elite :legend})

(s/def ::player-profile
  (s/keys :req-un [::id ::player-name ::total-decisions ::correct-decisions
                   ::current-streak ::best-streak ::total-points
                   ::history ::grade]))

;; ============================================================
;; App DB (root state)
;; ============================================================

(s/def ::current-route keyword?)
(s/def ::current-scenario (s/nilable ::scenario))
(s/def ::timer-running? boolean?)
(s/def ::time-remaining-ms nat-int?)
(s/def ::last-decision (s/nilable ::decision-record))
(s/def ::scenarios (s/coll-of ::scenario))
(s/def ::profile ::player-profile)
(s/def ::loading? boolean?)
(s/def ::error (s/nilable string?))

(s/def ::app-db
  (s/keys :req-un [::current-route ::current-scenario ::timer-running?
                   ::time-remaining-ms ::last-decision ::scenarios
                   ::profile ::loading? ::error]))

;; ============================================================
;; Validation Helpers
;; ============================================================

(defn valid-db?
  "Returns true if db conforms to ::app-db spec."
  [db]
  (s/valid? ::app-db db))

(defn explain-db
  "Returns spec explain-data for db, or nil if valid."
  [db]
  (s/explain-data ::app-db db))
