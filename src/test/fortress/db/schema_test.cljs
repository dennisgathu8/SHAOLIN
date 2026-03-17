(ns fortress.db.schema-test
  "Generative and example-based tests for all spec definitions."
  (:require [cljs.test :refer-macros [deftest testing is]]
            [clojure.spec.alpha :as s]
            [fortress.db.schema :as schema]
            [fortress.db.default-db :as default-db]))

;; ============================================================
;; Primitive specs
;; ============================================================

(deftest id-spec-test
  (testing "Valid IDs"
    (is (s/valid? ::schema/id "abc-123"))
    (is (s/valid? ::schema/id "scenario-001"))
    (is (s/valid? ::schema/id "abc")))
  (testing "Invalid IDs"
    (is (not (s/valid? ::schema/id "")))
    (is (not (s/valid? ::schema/id "ab")))     ;; too short
    (is (not (s/valid? ::schema/id "ABC")))     ;; uppercase
    (is (not (s/valid? ::schema/id "has space")))))

(deftest position-spec-test
  (testing "Valid positions"
    (is (s/valid? ::schema/position {:x 50 :y 50}))
    (is (s/valid? ::schema/position {:x 0 :y 0}))
    (is (s/valid? ::schema/position {:x 100 :y 100})))
  (testing "Invalid positions"
    (is (not (s/valid? ::schema/position {:x -1 :y 50})))
    (is (not (s/valid? ::schema/position {:x 50 :y 101})))))

;; ============================================================
;; Player spec
;; ============================================================

(def sample-player
  {:id           "player-001"
   :player-name  "Olunga"
   :player-role  :st
   :shirt-number 9
   :position     {:x 50 :y 20}
   :pressing?    false})

(deftest player-spec-test
  (testing "Valid player"
    (is (s/valid? ::schema/player sample-player)))
  (testing "Invalid player — missing fields"
    (is (not (s/valid? ::schema/player (dissoc sample-player :id)))))
  (testing "Invalid player — bad role"
    (is (not (s/valid? ::schema/player (assoc sample-player :player-role :invalid))))))

;; ============================================================
;; Pass Option spec
;; ============================================================

(def sample-pass-option
  {:id               "pass-001"
   :target-player-id "player-002"
   :pass-type        :short
   :risk-level       :low
   :is-optimal?      true
   :feedback         "Good choice — quick combination"
   :points           3})

(deftest pass-option-spec-test
  (testing "Valid pass option"
    (is (s/valid? ::schema/pass-option sample-pass-option)))
  (testing "Invalid — bad pass type"
    (is (not (s/valid? ::schema/pass-option
                       (assoc sample-pass-option :pass-type :lob))))))

;; ============================================================
;; Scenario spec
;; ============================================================

(def sample-scenario
  {:id              "scen-001"
   :title           "GK Build-up vs High Press"
   :description     "The opponent is pressing high. Find the free CB."
   :difficulty      :beginner
   :formation       "4-3-3"
   :time-limit-ms   8000
   :ball-carrier-id "player-001"
   :teammates       [sample-player]
   :opponents       [(assoc sample-player :id "opp-001" :pressing? true)]
   :pass-options    [sample-pass-option
                     (assoc sample-pass-option :id "pass-002" :is-optimal? false :points -2)]
   :coaching-note   "Look for the free CB on the weak side."})

(deftest scenario-spec-test
  (testing "Valid scenario"
    (is (s/valid? ::schema/scenario sample-scenario)))
  (testing "Invalid — too few pass options"
    (is (not (s/valid? ::schema/scenario
                       (assoc sample-scenario :pass-options [(first (:pass-options sample-scenario))]))))))

;; ============================================================
;; App DB spec
;; ============================================================

(deftest app-db-spec-test
  (testing "default-db conforms to ::app-db"
    (is (s/valid? ::schema/app-db default-db/default-db))))
