(ns fortress.security.edn-reader-test
  "Security tests for the safe EDN reader.
   Tests known attack vectors to ensure they are rejected."
  (:require [cljs.test :refer-macros [deftest testing is]]
            [fortress.security.edn-reader :as edn]))

;; ============================================================
;; Valid EDN — should parse correctly
;; ============================================================

(deftest safe-read-valid-edn-test
  (testing "Simple map"
    (is (= {:score 42} (edn/safe-read-edn "{:score 42}"))))
  (testing "Nested structure"
    (is (= {:a {:b [1 2 3]}} (edn/safe-read-edn "{:a {:b [1 2 3]}}"))))
  (testing "Keywords and strings"
    (is (= {:name "Wanyama" :role :cdm}
           (edn/safe-read-edn "{:name \"Wanyama\" :role :cdm}"))))
  (testing "Empty map"
    (is (= {} (edn/safe-read-edn "{}"))))
  (testing "Vector"
    (is (= [1 2 3] (edn/safe-read-edn "[1 2 3]"))))
  (testing "nil input"
    (is (nil? (edn/safe-read-edn nil))))
  (testing "Non-string input"
    (is (nil? (edn/safe-read-edn 42)))))

;; ============================================================
;; Attack vectors — MUST return nil
;; ============================================================

(deftest reject-eval-reader-test
  (testing "Clojure eval reader #="
    (is (nil? (edn/safe-read-edn "#=(js/alert 1)")))
    (is (nil? (edn/safe-read-edn "#=(println \"hacked\")")))))

(deftest reject-js-literal-test
  (testing "ClojureScript #js literal"
    (is (nil? (edn/safe-read-edn "#js {:foo 1}")))
    (is (nil? (edn/safe-read-edn "#js [1 2 3]")))))

(deftest reject-js-interop-test
  (testing "JS interop namespace"
    (is (nil? (edn/safe-read-edn "{:x js/alert}")))
    (is (nil? (edn/safe-read-edn "{:x js/document.cookie}")))))

(deftest reject-eval-symbols-test
  (testing "eval references"
    (is (nil? (edn/safe-read-edn "{:fn eval}")))
    (is (nil? (edn/safe-read-edn "(eval {:x 1})")))))

(deftest reject-function-constructor-test
  (testing "Function constructor"
    (is (nil? (edn/safe-read-edn "{:fn Function}")))
    (is (nil? (edn/safe-read-edn "{:x (Function \"return 1\")}")))))

(deftest reject-timer-attacks-test
  (testing "setTimeout injection"
    (is (nil? (edn/safe-read-edn "{:fn setTimeout}"))))
  (testing "setInterval injection"
    (is (nil? (edn/safe-read-edn "{:fn setInterval}")))))

(deftest reject-dom-access-test
  (testing "document access"
    (is (nil? (edn/safe-read-edn "{:x document.cookie}"))))
  (testing "window access"
    (is (nil? (edn/safe-read-edn "{:x window.location}")))))

;; ============================================================
;; Strict variant
;; ============================================================

(deftest strict-reader-throws-test
  (testing "safe-read-edn-strict throws on bad input"
    (is (thrown? js/Error (edn/safe-read-edn-strict "#=(js/alert 1)"))))
  (testing "safe-read-edn-strict returns valid data"
    (is (= {:ok true} (edn/safe-read-edn-strict "{:ok true}")))))
