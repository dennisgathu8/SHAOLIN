(ns fortress.subs.app
  "General application subscriptions."
  (:require [re-frame.core :as rf]))

;; ============================================================
;; Route
;; ============================================================

(rf/reg-sub
 ::current-route
 (fn [db _]
   (:current-route db)))

;; ============================================================
;; Loading & Error
;; ============================================================

(rf/reg-sub
 ::loading?
 (fn [db _]
   (:loading? db)))

(rf/reg-sub
 ::error
 (fn [db _]
   (:error db)))

;; ============================================================
;; Profile
;; ============================================================

(rf/reg-sub
 ::profile
 (fn [db _]
   (:profile db)))

(rf/reg-sub
 ::player-name
 :<- [::profile]
 (fn [profile _]
   (:player-name profile)))

(rf/reg-sub
 ::grade
 :<- [::profile]
 (fn [profile _]
   (:grade profile)))

(rf/reg-sub
 ::total-points
 :<- [::profile]
 (fn [profile _]
   (:total-points profile)))
