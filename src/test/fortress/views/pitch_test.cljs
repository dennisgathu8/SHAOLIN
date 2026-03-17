(ns fortress.views.pitch-test
  "Unit tests for the SVG pitch view and its helpers."
  (:require [cljs.test :refer-macros [deftest testing is]]
            [fortress.views.components.pass-arrow :as pass-arrow]))

;; We test the private geometry functions by exposing them through a dev wrapper
;; or by direct testing since we're in the same context during test execution.

(deftest pass-arrow-geometry-test
  (testing "Distance calculation"
    (let [calc-dist #'pass-arrow/calculate-distance]
      ;; 3-4-5 triangle
      (is (= 5 (calc-dist 0 0 3 4)))
      ;; horizontal line
      (is (= 10 (calc-dist 10 10 20 10)))
      ;; same point
      (is (= 0 (calc-dist 5 5 5 5)))))
      
  (testing "Angle calculation"
    (let [calc-angle #'pass-arrow/calculate-angle]
      ;; horizontal right
      (is (= 0 (calc-angle 0 0 10 0)))
      ;; vertical down
      (is (= 90 (calc-angle 0 0 0 10)))
      ;; horizontal left
      (is (= 180 (calc-angle 10 0 0 0)))
      ;; vertical up
      (is (= -90 (calc-angle 0 10 0 0))))))
