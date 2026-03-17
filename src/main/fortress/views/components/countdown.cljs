(ns fortress.views.components.countdown
  "SVG Timer Component.
   Draws a circular progress ring that depletes as time runs out.")

(defn countdown-ring
  "Renders an SVG circle with a stroke-dashoffset to show progress."
  [{:keys [total-ms remaining-ms]}]
  (let [percentage (if (and total-ms (> total-ms 0))
                     (* (/ (max 0 remaining-ms) total-ms) 100)
                     0)
        radius       16
        circumference (* 2 js/Math.PI radius)
        dash-offset  (- circumference (* (/ percentage 100) circumference))
        
        ;; Color changes as time gets low
        stroke-color (cond
                       (> percentage 50) "var(--accent-green)"
                       (> percentage 20) "var(--accent-gold)"
                       :else             "var(--accent-red)")]
    
    [:div.countdown-container
     ;; The SVG Ring
     [:svg {:width "48" :height "48" :viewBox "0 0 40 40"}
      ;; Background track
      [:circle
       {:cx "20"
        :cy "20"
        :r radius
        :fill "none"
        :stroke "var(--bg-card)"
        :stroke-width "4"}]
      ;; Progress ring (rotated so it starts at the top)
      [:circle
       {:cx "20"
        :cy "20"
        :r radius
        :fill "none"
        :stroke stroke-color
        :stroke-width "4"
        :stroke-dasharray circumference
        :stroke-dashoffset dash-offset
        :stroke-linecap "round"
        :style {:transition "stroke-dashoffset 100ms linear, stroke 300ms ease"
                :transform "rotate(-90deg)"
                :transform-origin "50% 50%"}}]]
                
     ;; The text inside the ring
     [:div.countdown-text
      (let [total-sec (/ (max 0 remaining-ms) 1000)
            whole     (js/Math.floor total-sec)
            dec       (js/Math.floor (* (- total-sec whole) 10))]
        (str whole "." dec))]]))
