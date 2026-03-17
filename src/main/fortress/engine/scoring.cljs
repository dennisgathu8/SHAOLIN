(ns fortress.engine.scoring
  "Pure scoring and profiling logic.
   Computes how a decision affects a player's profile based on the scenario."
  (:require [fortress.config :as config]))

(defn compute-grade
  "Calculates the player grade based on total points."
  [points]
  (let [thresholds config/grade-thresholds]
    (cond
      (>= points (:legend thresholds)) :legend
      (>= points (:elite thresholds))  :elite
      (>= points (:gold thresholds))   :gold
      (>= points (:silver thresholds)) :silver
      :else :bronze)))

(defn apply-decision
  "Returns an updated player profile given a pass-option (or nil if time expired)."
  [profile selected-option]
  (let [is-correct? (if selected-option (:is-optimal? selected-option) false)
        points-won  (if selected-option (:points selected-option) config/timeout-penalty)
        
        new-total-decisions (inc (:total-decisions profile))
        new-correct         (if is-correct? (inc (:correct-decisions profile)) (:correct-decisions profile))
        new-points          (+ (:total-points profile) points-won)
        
        new-streak          (if is-correct? (inc (:current-streak profile)) 0)
        new-best-streak     (max (:best-streak profile) new-streak)
        
        new-grade           (compute-grade new-points)]
        
    (assoc profile
           :total-decisions new-total-decisions
           :correct-decisions new-correct
           :total-points new-points
           :current-streak new-streak
           :best-streak new-best-streak
           :grade new-grade)))
