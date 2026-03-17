(ns fortress.views.components.zone-overlay
  "SVG Clickable Zone Overlay.
   Renders a large transparent clickable target area to make it easier
   to tap a player on a mobile screen.")

(defn zone-overlay
  "Renders an invisible (or highlighted) circle for comfortable touch targets."
  [{:keys [position radius on-click highlight?]
    :or {radius 8}}]  ;; ~8% of pitch width = large mobile touch target
  (let [{:keys [x y]} position]
    [:circle.zone-overlay
     {:cx x
      :cy y
      :r  radius
      :fill (if highlight? "rgba(52, 211, 153, 0.2)" "transparent")
      :stroke (if highlight? "var(--accent-green)" "none")
      :stroke-width (if highlight? 0.5 0)
      :style {:cursor "pointer"
              :pointer-events "all"}
      :on-click (fn [e]
                  (.stopPropagation e)
                  (when on-click (on-click)))}]))
