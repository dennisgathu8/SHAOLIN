(ns fortress.events.scenario-test
  "re-frame integration tests for scenarios and timer."
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [re-frame.core :as rf]
            [day8.re-frame.test :as rf-test]
            [fortress.db.default-db :as default-db]
            [fortress.events.app]
            [fortress.events.scenario]
            [fortress.events.timer]
            [fortress.events.persistence]
            [fortress.router.core]
            [fortress.subs.scenario]
            [fortress.subs.timer]))

;; Polyfill for Node.js test environment
(when-not (exists? js/requestAnimationFrame)
  (set! js/requestAnimationFrame (fn [cb] (js/setTimeout cb 16)))
  (set! js/cancelAnimationFrame (fn [id] (js/clearTimeout id))))

(deftest scenario-lifecycle-test
  (rf-test/run-test-sync
   
   ;; 1. Init DB
   (rf/dispatch [:fortress.events.app/initialize-db])
   
   (testing "Loading scenarios"
     (rf/dispatch [:fortress.events.scenario/load-scenarios])
     (let [scenarios @(rf/subscribe [:fortress.subs.scenario/scenarios])]
       (is (= 3 (count scenarios)))
       (is (= "scen-bulid-001" (:id (first scenarios))))))
       
   (testing "Selecting a scenario"
     ;; Select intermediate scenario
     (rf/dispatch [:fortress.events.scenario/select-scenario "scen-mid-002"])
     
     (let [current @(rf/subscribe [:fortress.subs.scenario/current-scenario])
           timer?  @(rf/subscribe [:fortress.subs.timer/running?])
           time-ms @(rf/subscribe [:fortress.subs.timer/remaining-ms])]
           
       (is (= "scen-mid-002" (:id current)))
       (is (= 4000 (:time-limit-ms current)))
       
       ;; Timer should have started with the scenario's time limit
       (is (true? timer?))
       (is (= 4000 time-ms))))
       
   (testing "Timer ticking"
     ;; Simulate one tick
     (rf/dispatch [:fortress.events.timer/tick])
     (let [time-ms @(rf/subscribe [:fortress.subs.timer/remaining-ms])]
       ;; 4000 - 100 = 3900 => testing the logic
       (is (= 3900 time-ms))))
       
   (testing "Clearing scenario"
     (rf/dispatch [:fortress.events.scenario/clear-scenario])
     (let [current @(rf/subscribe [:fortress.subs.scenario/current-scenario])
           timer?  @(rf/subscribe [:fortress.subs.timer/running?])]
       (is (nil? current))
       (is (false? timer?))))))
