(ns fortress.events.decision-test
  "Integration tests for decision evaluate and timeout events."
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [re-frame.core :as rf]
            [day8.re-frame.test :as rf-test]
            [fortress.events.app]
            [fortress.events.scenario]
            [fortress.events.decision]
            [fortress.events.persistence]
            [fortress.subs.scenario]
            [fortress.subs.decision]
            [fortress.router.core]
            [re-frame.db :as db]))

(deftest decision-evaluation-test
  (rf-test/run-test-sync
   
   (rf/dispatch [:fortress.events.app/initialize-db])
   (rf/dispatch [:fortress.events.scenario/load-scenarios])
   
   (testing "Evaluating a valid decision"
     ;; Select scenario #2 (scen-mid-002)
     (rf/dispatch [:fortress.events.scenario/select-scenario "scen-mid-002"])
     (let [scenario @(rf/subscribe [:fortress.subs.scenario/current-scenario])]
       (is (= "scen-mid-002" (:id scenario))))
       
     ;; Dispatch an evaluate event targeting a valid option
     ;; "scen-mid-002" has opt-3 pointing to "rw1"
     (rf/dispatch [:fortress.events.decision/evaluate "rw1"])
     
     (let [app-db @db/app-db
           last-dec @(rf/subscribe [:fortress.subs.decision/last-decision])]
           
       (is (nil? (:current-scenario app-db)) "Scenario cleared from view")
       (is (= :result (:current-route app-db)) "Navigated to result")
       
       (is (false? (:timed-out? last-dec)))
       (is (= "opt-3" (:id (:pass-option last-dec))))
       (is (= true (:is-optimal? (:pass-option last-dec))))
       
       (is (= 5 (:total-points (:profile app-db))))
       (is (= 1 (:current-streak (:profile app-db))))))
       
   (testing "Evaluating a timeout"
     (rf/dispatch [:fortress.events.scenario/select-scenario "scen-adv-001"])
     (rf/dispatch [:fortress.events.decision/time-expired])
     
     (let [app-db @db/app-db
           last-dec @(rf/subscribe [:fortress.subs.decision/last-decision])]
           
       (is (= :result (:current-route app-db)))
       (is (true? (:timed-out? last-dec)))
       (is (nil? (:pass-option last-dec)))
       
       ;; Total points = 5 - 3 = 2
       (is (= 2 (:total-points (:profile app-db))))
       (is (= 0 (:current-streak (:profile app-db))))
       (is (= 1 (:best-streak (:profile app-db))))))))
