(ns fortress.views.components.player-marker
  "SVG Player Marker Component.
   Renders a player circle with shirt number.
   Highlights if pressing, or if it's the ball carrier.")

(defn player-marker
  "Renders an SVG <g> containing the player circle and number."
  [{:keys [player ball-carrier? on-click]}]
  (let [{:keys [id shirt-number position pressing?]} player
        {:keys [x y]} position
        
        ;; Styling based on role and status
        fill-color (cond
                     pressing? "var(--accent-red)"
                     ball-carrier? "var(--accent-gold)"
                     :else "var(--text-primary)")
        text-color (if ball-carrier? "var(--bg-primary)" "var(--bg-primary)")
        radius     (if ball-carrier? 4.5 3.5)]
    
    [:g.player-marker
     {:transform (str "translate(" x " " y ")")
      :style     {:cursor (if on-click "pointer" "default")}
      :on-click  #(when on-click (on-click id))}
     
     ;; Outer ring for pressers
     (when pressing?
       [:circle
        {:r (+ radius 2)
         :fill "none"
         :stroke "var(--accent-red)"
         :stroke-width 0.8
         :stroke-dasharray "2,1"
         :class "anim-pulse"}])
         
     ;; Main player body
     [:circle
      {:r radius
       :fill fill-color
       :stroke "var(--bg-primary)"
       :stroke-width 0.5}]
       
     ;; Shirt Number
     [:text
      {:x 0
       :y 1.2    ;; optical vertical centering
       :text-anchor "middle"
       :fill text-color
       :font-size "4px"
       :font-weight "bold"
       :font-family "var(--font-family)"
       :pointer-events "none"}  ;; Don't block clicks on the circle
      shirt-number]]))
