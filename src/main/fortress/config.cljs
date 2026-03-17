(ns fortress.config
  "Application constants and feature flags.
   All values are pure data — no side effects.")

(def version "0.1.0")

(def app-name "FKF Decision Simulator")

(def features
  "Feature flags — toggle functionality without code changes."
  {:encryption-enabled? true       ;; Web Crypto AES-GCM for storage
   :coach-mode?         false      ;; Phase 5
   :pdf-export?         false      ;; Phase 5
   :offline-mode?       true       ;; Service worker
   :debug-specs?        ^boolean js/goog.DEBUG})  ;; Spec asserts in dev only

(def timer-defaults
  "Default timer settings for scenarios."
  {:min-ms   1000
   :max-ms   30000
   :tick-ms  100})    ;; Timer granularity

(def scoring
  "Scoring constants."
  {:time-bonus-threshold-ms 3000   ;; Bonus points if decided within 3s
   :time-bonus-points       2
   :streak-bonus-threshold  5      ;; Bonus at 5-streak
   :streak-bonus-points     3})

(def grade-thresholds
  "Grade boundaries by total accumulated points."
  {:legend 100
   :elite  50
   :gold   25
   :silver 10
   :bronze 0})

(def timeout-penalty -3)
