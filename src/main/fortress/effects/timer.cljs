(ns fortress.effects.timer
  "requestAnimationFrame-based timer effect.
   Provides a high-performance countdown timer that doesn't block the UI thread
   and relies on the browser's native animation loop."
  (:require [re-frame.core :as rf]))

;; Store the active requestAnimationFrame ID so we can cancel it
(defonce ^:private timer-id (atom nil))
;; Store the last tick time to calculate delta
(defonce ^:private last-tick (atom nil))

(defn- tick! [timestamp tick-ms dispatch-event]
  (let [last-time @last-tick
        delta     (- timestamp last-time)]
        
    (when (>= delta tick-ms)
      ;; Dispatch the tick event to re-frame
      (rf/dispatch dispatch-event)
      (reset! last-tick timestamp))
      
    ;; Queue the next frame
    (reset! timer-id (js/requestAnimationFrame
                      #(tick! % tick-ms dispatch-event)))))

(rf/reg-fx
 :start-timer
 (fn [{:keys [tick-ms dispatch-event]}]
   ;; Clean up any existing timer first
   (when-let [id @timer-id]
     (js/cancelAnimationFrame id))
     
   (reset! last-tick (js/performance.now))
   (reset! timer-id (js/requestAnimationFrame
                     #(tick! % tick-ms dispatch-event)))))

(rf/reg-fx
 :stop-timer
 (fn [_]
   (when-let [id @timer-id]
     (js/cancelAnimationFrame id))
   (reset! timer-id nil)
   (reset! last-tick nil)))
