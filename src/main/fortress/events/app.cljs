(ns fortress.events.app
  "General application events — init, error handling, navigation.
   All events use fortress-interceptors for spec validation."
  (:require [re-frame.core :as rf]
            [fortress.db.default-db :as default-db]
            [fortress.db.interceptors :as interceptors]))

;; ============================================================
;; Initialize
;; ============================================================

(rf/reg-event-fx
 ::initialize-db
 interceptors/fortress-interceptors
 (fn [_ _]
   {:db default-db/default-db
    :dispatch-n [[:fortress.events.persistence/load-profile]
                 [:fortress.events.scenario/load-scenarios]]}))

;; ============================================================
;; Error Handling
;; ============================================================

(rf/reg-event-db
 ::set-error
 interceptors/fortress-interceptors
 (fn [db [_ error-msg]]
   (assoc db :error error-msg)))

(rf/reg-event-db
 ::clear-error
 interceptors/fortress-interceptors
 (fn [db _]
   (assoc db :error nil)))

;; ============================================================
;; Loading State
;; ============================================================

(rf/reg-event-db
 ::set-loading
 interceptors/fortress-interceptors
 (fn [db [_ loading?]]
   (assoc db :loading? loading?)))
