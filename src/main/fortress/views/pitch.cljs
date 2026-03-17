(ns fortress.views.pitch
  "Interactive SVG Pitch Component.
   Renders the grass, lines, players, arrows, and handles decision clicks.
   Utilizes a 100x100 viewBox so all coordinates are percentages."
  (:require [fortress.views.components.player-marker :refer [player-marker]]
            [fortress.views.components.pass-arrow :refer [pass-arrow]]
            [fortress.views.components.zone-overlay :refer [zone-overlay]]))

;; ============================================================
;; SVG Background Components (Static)
;; ============================================================

(defn- pitch-lines
  "Static SVG grass background and pitch markings."
  []
  [:g.pitch-markings
   ;; Grass background
   [:rect {:x 0 :y 0 :width 100 :height 100 :fill "var(--pitch-green)"}]
   
   ;; Striped grass effect
   (for [stripe (range 0 100 10)]
     ^{:key stripe}
     [:rect {:x 0 :y stripe :width 100 :height 10 :fill (if (even? (/ stripe 10))
                                                          "transparent"
                                                          "var(--pitch-green-light)")}])
                                                          
   ;; Standard Pitch Lines (white)
   [:g {:stroke "var(--pitch-line)"
        :stroke-width 0.4
        :fill "none"}
    ;; Outer boundary
    [:rect {:x 2 :y 2 :width 96 :height 96}]
    ;; Halfway line
    [:line {:x1 2 :y1 50 :x2 98 :y2 50}]
    ;; Center circle
    [:circle {:cx 50 :cy 50 :r 10}]
    ;; Center spot
    [:circle {:cx 50 :cy 50 :r 0.5 :fill "var(--pitch-line)"}]
    
    ;; Top Penalty Box
    [:rect {:x 20 :y 2 :width 60 :height 16}]
    ;; Top 6-yard Box
    [:rect {:x 38 :y 2 :width 24 :height 6}]
    ;; Top Penalty Spot
    [:circle {:cx 50 :cy 12 :r 0.5 :fill "var(--pitch-line)"}]
    ;; Top D
    [:path {:d "M 40 18 A 10 10 0 0 0 60 18"}]
    
    ;; Bottom Penalty Box
    [:rect {:x 20 :y 82 :width 60 :height 16}]
    ;; Bottom 6-yard Box
    [:rect {:x 38 :y 92 :width 24 :height 6}]
    ;; Bottom Penalty Spot
    [:circle {:cx 50 :cy 88 :r 0.5 :fill "var(--pitch-line)"}]
    ;; Bottom D
    [:path {:d "M 40 82 A 10 10 0 0 1 60 82"}]]])

;; ============================================================
;; Interactive Pitch
;; ============================================================

(defn pitch
  "Main pitch component.
   Expects a valid scenario map as prop."
  [{:keys [scenario on-decision-select]}]
  (let [{:keys [teammates opponents ball-carrier-id pass-options]} scenario
        
        ;; Map for quick lookup
        teammate-map  (into {} (map (juxt :id identity) teammates))
        ball-carrier  (get teammate-map ball-carrier-id)
        
        ;; Ensure we only show pass options targeting existing players
        valid-options (filter #(contains? teammate-map (:target-player-id %))
                              pass-options)]
                              
    [:div.pitch-container
     [:svg
      {:viewBox "0 0 100 100"
       :preserveAspectRatio "xMidYMid meet"
       :aria-label "Football pitch interactive scenario"}
       
      ;; 1. Base grass and lines
      [pitch-lines]
      
      ;; 2. Pass Arrows (below players)
      (when ball-carrier
        [:g.pass-arrows
         (for [opt valid-options]
           (let [target (get teammate-map (:target-player-id opt))]
             ^{:key (:id opt)}
             [pass-arrow {:source-pos (:position ball-carrier)
                          :target-pos (:position target)
                          :type       (:pass-type opt)}]))])
      
      ;; 3. Opponents (red)
      [:g.opponents
       (for [opp opponents]
         ^{:key (:id opp)}
         [player-marker {:player opp}])]
         
      ;; 4. Teammates (blue)
      [:g.teammates
       (for [team teammates]
         ^{:key (:id team)}
         [player-marker {:player team
                         :ball-carrier? (= (:id team) ball-carrier-id)}])]
                         
      ;; 5. Clickable Zone Overlays for viable pass options
      [:g.touch-zones
       (for [opt valid-options]
         (let [target (get teammate-map (:target-player-id opt))]
           ^{:key (str "zone-" (:id opt))}
           [zone-overlay {:position (:position target)
                          :highlight? false  ;; set to true during feedback
                          :on-click #(when on-decision-select
                                       (on-decision-select (:id opt)))}]))]]]))
