(ns fortress.views.components.pass-arrow
  "SVG Pass Arrow Component.
   Draws a directional arrow from the ball carrier to a target option.")

(defn- calculate-angle
  "Calculate angle in degrees between two points."
  [x1 y1 x2 y2]
  (let [dx (- x2 x1)
        dy (- y2 y1)]
    (* (/ (js/Math.atan2 dy dx) js/Math.PI) 180)))

(defn- calculate-distance
  "Calculate Euclidean distance between two points."
  [x1 y1 x2 y2]
  (let [dx (- x2 x1)
        dy (- y2 y1)]
    (js/Math.sqrt (+ (* dx dx) (* dy dy)))))

(defn pass-arrow
  "Renders a dashed SVG arrow indicating a passing lane."
  [{:keys [source-pos target-pos type]}]
  (let [{sx :x, sy :y} source-pos
        {tx :x, ty :y} target-pos
        
        ;; Slight offset so arrow doesn't overlap the player circle
        offset       5
        distance     (calculate-distance sx sy tx ty)
        angle        (calculate-angle sx sy tx ty)
        
        ;; Ensure we don't draw if they're basically on top of each other
        valid-dist?  (> distance (* offset 2))
        
        ;; Arrow styling based on pass type
        stroke-color (case type
                       :long    "var(--accent-blue)"
                       :through "var(--accent-gold)"
                       "var(--text-secondary)")
        stroke-dash  (case type
                       :long    "4,2"
                       :through "3,4"
                       "2,2")]
                       
    (when valid-dist?
      [:g.pass-arrow
       ;; We use a transform to start at source, rotate towards target
       {:transform (str "translate(" sx " " sy ") rotate(" angle ")")}
       
       ;; Define arrowhead marker if it doesn't already exist globally
       [:defs
        [:marker
         {:id "arrowhead"
          :markerWidth "5"
          :markerHeight "4"
          :refX "4"
          :refY "2"
          :orient "auto"}
         [:polygon
          {:points "0 0, 5 2, 0 4"
           :fill stroke-color}]]]
           
       ;; The line itself
       [:line
        {:x1 offset
         :y1 0
         :x2 (- distance offset)
         :y2 0
         :stroke stroke-color
         :stroke-width 0.8
         :stroke-dasharray stroke-dash
         :marker-end "url(#arrowhead)"}]])))
