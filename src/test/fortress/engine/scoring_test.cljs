(ns fortress.engine.scoring-test
  "Unit tests for pure scoring logic."
  (:require [cljs.test :refer-macros [deftest testing is]]
            [fortress.engine.scoring :as scoring]
            [fortress.config :as config]))

(def initial-profile
  {:total-decisions 0
   :correct-decisions 0
   :total-points 0
   :current-streak 0
   :best-streak 0
   :grade :bronze})

(def good-pass
  {:is-optimal? true
   :points 5})

(def bad-pass
  {:is-optimal? false
   :points -3})

(deftest apply-decision-test
  (testing "Good pass increases points, streak, and corrects"
    (let [profile (scoring/apply-decision initial-profile good-pass)]
      (is (= 1 (:total-decisions profile)))
      (is (= 1 (:correct-decisions profile)))
      (is (= 5 (:total-points profile)))
      (is (= 1 (:current-streak profile)))
      (is (= 1 (:best-streak profile)))))
      
  (testing "Bad pass drops streak and lowers points"
    (let [p1 (scoring/apply-decision initial-profile good-pass)
          p2 (scoring/apply-decision p1 good-pass)
          p3 (scoring/apply-decision p2 bad-pass)]
          
      (is (= 3 (:total-decisions p3)))
      (is (= 2 (:correct-decisions p3)))
      (is (= 7 (:total-points p3)))  ;; 5 + 5 - 3
      (is (= 0 (:current-streak p3)))
      (is (= 2 (:best-streak p3)))))  ;; Best streak remembered
      
  (testing "Timeout applies config penalty and drops streak"
    (let [p1 (scoring/apply-decision initial-profile good-pass)
          p2 (scoring/apply-decision p1 nil)]
          
      (is (= (+ 5 config/timeout-penalty) (:total-points p2)))
      (is (= 0 (:current-streak p2)))
      (is (= 1 (:best-streak p2))))))

(deftest compute-grade-test
  (testing "Grade boundaries compute correctly"
    (is (= :bronze (scoring/compute-grade 0)))
    (is (= :bronze (scoring/compute-grade 9)))
    (is (= :silver (scoring/compute-grade 10)))
    (is (= :silver (scoring/compute-grade 24)))
    (is (= :gold   (scoring/compute-grade 25)))
    (is (= :elite  (scoring/compute-grade 50)))
    (is (= :legend (scoring/compute-grade 100)))
    (is (= :legend (scoring/compute-grade 500)))))
