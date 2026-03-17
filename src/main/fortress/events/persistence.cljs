(ns fortress.events.persistence
  "Handles saving and loading the player profile to/from localStorage,
   using AES-GCM encryption. Re-frame effects bridge the promise-based Web Crypto API
   with core.async."
  (:require [re-frame.core :as rf]
            [fortress.security.crypto :as crypto]
            [fortress.config :as config]
            [fortress.security.edn-reader :as edn-reader]
            [cljs.core.async :refer [go]]
            [cljs.core.async.interop :refer-macros [<p!]]))

(def ^:private master-passphrase "fortress-fkf-simulator-local-key-2026")
(def ^:private salt "static-salt-for-local-demo")
(def ^:private storage-key "fortress_player_profile")

(rf/reg-fx
 :encrypt-and-save
 (fn [profile]
   (go
     (try
       (let [key (<p! (crypto/derive-key master-passphrase salt))
             edn-str (pr-str profile)
             encrypted (<p! (crypto/encrypt-str key edn-str))]
         (.setItem js/window.localStorage storage-key encrypted))
       (catch js/Error e
         (js/console.error "Failed to save encrypted profile:" e))))))

(rf/reg-fx
 :load-and-decrypt
 (fn [[on-success-evt on-failure-evt]]
   (go
     (try
       (if-let [encrypted (.getItem js/window.localStorage storage-key)]
         (let [key (<p! (crypto/derive-key master-passphrase salt))
               decrypted-str (<p! (crypto/decrypt-str key encrypted))
               profile (edn-reader/safe-read-edn decrypted-str)]
           (rf/dispatch (conj on-success-evt profile)))
         (rf/dispatch on-failure-evt))
       (catch js/Error e
         (js/console.error "Failed to load/decrypt profile:" e)
         (rf/dispatch on-failure-evt))))))

(rf/reg-event-fx
 ::save-profile
 (fn [{:keys [db]} _]
   (if (:encryption-enabled? config/features)
     {:encrypt-and-save (:profile db)}
     {})))

(rf/reg-event-fx
 ::load-profile
 (fn [_ _]
   (if (:encryption-enabled? config/features)
     {:load-and-decrypt [::profile-loaded ::profile-load-failed]}
     {:dispatch [::profile-load-failed]})))

(rf/reg-event-db
 ::profile-loaded
 (fn [db [_ profile]]
   (assoc db :profile profile)))

(rf/reg-event-db
 ::profile-load-failed
 (fn [db _]
   ;; Use standard default profile if nothing found
   db))
