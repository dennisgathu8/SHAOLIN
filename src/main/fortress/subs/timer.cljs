(ns fortress.subs.timer
  "Timer subscriptions."
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::running?
 (fn [db _]
   (:timer-running? db)))

(rf/reg-sub
 ::remaining-ms
 (fn [db _]
   (:time-remaining-ms db)))

(rf/reg-sub
 ::progress-percentage
 :<- [::remaining-ms]
 (fn [remaining-ms [_ total-ms]]
   (if (or (nil? total-ms) (zero? total-ms))
     0
     (* (/ (max 0 remaining-ms) total-ms) 100))))

(rf/reg-sub
 ::formatted-time
 :<- [::remaining-ms]
 (fn [remaining-ms _]
   (let [total-seconds (/ (max 0 remaining-ms) 1000)
         whole-seconds (js/Math.floor total-seconds)
         decimals      (js/Math.floor (* (- total-seconds whole-seconds) 10))]
     (str whole-seconds "." decimals "s"))))
