(ns fortress.subs.decision
  "Subscriptions for viewing decision results."
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::last-decision
 (fn [db _]
   (:last-decision db)))
