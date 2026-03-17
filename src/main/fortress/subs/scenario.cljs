(ns fortress.subs.scenario
  "Subscriptions for scenarios."
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::scenarios
 (fn [db _]
   (:scenarios db)))

(rf/reg-sub
 ::current-scenario
 (fn [db _]
   (:current-scenario db)))

(rf/reg-sub
 ::current-scenario-id
 :<- [::current-scenario]
 (fn [scenario _]
   (:id scenario)))
