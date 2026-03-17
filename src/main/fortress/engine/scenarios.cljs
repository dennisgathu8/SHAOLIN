(ns fortress.engine.scenarios
  "Scenario definitions — Pure EDN data.
   These represent the tactical challenges presented to the player.
   In Phase 1/2, they are hardcoded. In later phases, they could be loaded
   from an encrypted external file or backend.")

(def catalog
  "List of all available scenarios.
   Each must conform to ::schema/scenario."
  [{:id "scen-bulid-001"
    :title "GK Build-up vs High Press"
    :description "The opponent is pressing high in a 4-3-3. Find the free player to break the first line."
    :difficulty :beginner
    :formation "4-3-3"
    :time-limit-ms 15000
    :ball-carrier-id "gk1"
    :teammates [{:id "gk1"  :player-name "GK"  :player-role :gk :shirt-number 1  :position {:x 50 :y 90} :pressing? false}
                {:id "cb1" :player-name "CB"  :player-role :cb :shirt-number 4  :position {:x 30 :y 75} :pressing? false}
                {:id "cb2" :player-name "CB"  :player-role :cb :shirt-number 5  :position {:x 70 :y 75} :pressing? false}
                {:id "cdm" :player-name "CDM" :player-role :cdm :shirt-number 6 :position {:x 50 :y 60} :pressing? false}
                {:id "fb1"  :player-name "FB"  :player-role :fb :shirt-number 3  :position {:x 10 :y 50} :pressing? false}]
    :opponents [{:id "st1"  :player-name "ST"  :player-role :st :shirt-number 9  :position {:x 40 :y 72} :pressing? true}
                {:id "lw1"  :player-name "LW"  :player-role :winger :shirt-number 11 :position {:x 20 :y 65} :pressing? true}
                {:id "rw1"  :player-name "RW"  :player-role :winger :shirt-number 7  :position {:x 80 :y 65} :pressing? true}]
    :pass-options [{:id "opt-1" :target-player-id "cb1" :pass-type :short :risk-level :high :is-optimal? false :feedback "Too risky under pressure" :points -2}
                   {:id "opt-2" :target-player-id "cb2" :pass-type :short :risk-level :low  :is-optimal? true  :feedback "Good choice — free man found on weak side" :points 5}
                   {:id "opt-3" :target-player-id "cdm" :pass-type :through :risk-level :medium :is-optimal? false :feedback "Midfield blocked by cover shadow" :points 1}
                   {:id "opt-4" :target-player-id "fb1"  :pass-type :long :risk-level :low :is-optimal? true :feedback "Excellent switch to weak side fullback" :points 4}]
    :coaching-note "Look for the free man outside the pressing zone structure."}

   {:id "scen-mid-002"
    :title "Midfield Pivot Turn"
    :description "You receive the ball back to goal. A midfielder is biting on your heels."
    :difficulty :intermediate
    :formation "4-2-3-1"
    :time-limit-ms 15000
    :ball-carrier-id "cdm1"
    :teammates [{:id "cdm1" :player-name "CDM" :player-role :cdm :shirt-number 6 :position {:x 50 :y 55} :pressing? false}
                {:id "cdm2" :player-name "CDM" :player-role :cdm :shirt-number 8 :position {:x 30 :y 55} :pressing? false}
                {:id "cam"  :player-name "CAM" :player-role :cam :shirt-number 10 :position {:x 50 :y 35} :pressing? false}
                {:id "rw1"  :player-name "RW"  :player-role :winger :shirt-number 7 :position {:x 85 :y 30} :pressing? false}
                {:id "cb1"  :player-name "CB"  :player-role :cb :shirt-number 5 :position {:x 50 :y 75} :pressing? false}]
    :opponents [{:id "cam-opp" :player-name "CAM" :player-role :cam :shirt-number 10 :position {:x 50 :y 52} :pressing? true}
                {:id "cm-opp"  :player-name "CM"  :player-role :cm :shirt-number 8  :position {:x 35 :y 48} :pressing? true}
                {:id "st-opp"  :player-name "ST"  :player-role :st :shirt-number 9  :position {:x 50 :y 65} :pressing? false}]
    :pass-options [{:id "opt-1" :target-player-id "cdm2" :pass-type :short :risk-level :medium :is-optimal? true :feedback "Safe lateral pass to reset the angle" :points 4}
                   {:id "opt-2" :target-player-id "cam"  :pass-type :through :risk-level :high :is-optimal? false :feedback "Blindsided — turnover likely" :points -3}
                   {:id "opt-3" :target-player-id "rw1"  :pass-type :long :risk-level :medium :is-optimal? true :feedback "Great vision — one-touch switch to space" :points 5}
                   {:id "opt-4" :target-player-id "cb1"  :pass-type :back :risk-level :high :is-optimal? false :feedback "ST is waiting for the back-pass trigger" :points -5}]
    :coaching-note "If you can't turn, play the way you face. Check your shoulders before receiving."}

   {:id "scen-adv-001"
    :title "Fullback Overload Trap"
    :description "Trapped on the sideline against a touchline press. Very little time."
    :difficulty :advanced
    :formation "4-4-2"
    :time-limit-ms 20000
    :ball-carrier-id "fb1"
    :teammates [{:id "fb1" :player-name "FB" :player-role :fb :shirt-number 2 :position {:x 90 :y 60} :pressing? false}
                {:id "cb1" :player-name "CB" :player-role :cb :shirt-number 4 :position {:x 65 :y 70} :pressing? false}
                {:id "rm1" :player-name "RM" :player-role :winger :shirt-number 7 :position {:x 85 :y 40} :pressing? false}
                {:id "cm1" :player-name "CM" :player-role :cm :shirt-number 8 :position {:x 60 :y 50} :pressing? false}]
    :opponents [{:id "lm1" :player-name "LM" :player-role :winger :shirt-number 11 :position {:x 90 :y 55} :pressing? true}
                {:id "fb-opp" :player-name "FB" :player-role :fb :shirt-number 3 :position {:x 85 :y 45} :pressing? true}
                {:id "cm-opp" :player-name "CM" :player-role :cm :shirt-number 8 :position {:x 65 :y 55} :pressing? true}]
    :pass-options [{:id "opt-1" :target-player-id "cb1" :pass-type :short :risk-level :high :is-optimal? false :feedback "Playing back into pressure trap" :points -4}
                   {:id "opt-2" :target-player-id "rm1" :pass-type :through :risk-level :high :is-optimal? false :feedback "Lane completely blocked by 2 players" :points -3}
                   {:id "opt-3" :target-player-id "cm1" :pass-type :short :risk-level :medium :is-optimal? true :feedback "Bounced it inside to the free man" :points 5}]
    :coaching-note "The sideline is an extra defender. Bounce the ball inside immediately."}])
