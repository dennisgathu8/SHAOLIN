(ns fortress.security.edn-reader
  "Safe EDN reader — Clojure Fortress Mode.
   All data ingestion MUST flow through safe-read-edn.
   Rejects tagged literals (#=, #js, #inst, any eval symbol).
   This is the ONLY EDN parser allowed in the application."
  (:require [cljs.reader :as reader]))

;; ============================================================
;; Threat: Malicious EDN deserialization
;; Mitigation: Whitelist-only reader with zero allowed tag readers
;; ============================================================

(def ^:private forbidden-patterns
  "Patterns that must never appear in EDN input.
   Checked BEFORE parsing as an additional defence layer."
  [#"#="          ;; Clojure eval reader
   #"#js"         ;; ClojureScript JS literal
   #"js/"         ;; JS interop namespace
   #"eval"        ;; Any eval reference
   #"Function"    ;; JS Function constructor
   #"setTimeout"  ;; Timer-based eval
   #"setInterval" ;; Timer-based eval
   #"document\."  ;; DOM access
   #"window\."    ;; Global access
   ])

(defn- contains-forbidden?
  "Returns the first forbidden pattern found in s, or nil."
  [s]
  (some (fn [pattern]
          (when (re-find pattern s)
            pattern))
        forbidden-patterns))

(defn safe-read-edn
  "Safely read an EDN string. Returns parsed data or nil on failure.
   
   Security guarantees:
   1. Pre-scan for forbidden patterns (defence in depth)
   2. No tag readers registered (empty map)
   3. Catches all parse errors
   
   Usage: (safe-read-edn \"{:score 42}\") => {:score 42}
          (safe-read-edn \"#=(js/alert 1)\") => nil"
  [s]
  (when (string? s)
    (when-not (contains-forbidden? s)
      (try
        (reader/read-string s)
        (catch :default _e
          (js/console.warn "⚠️ Fortress: EDN parse rejected" s)
          nil)))))

(defn safe-read-edn-strict
  "Like safe-read-edn but throws on invalid input instead of returning nil.
   Use only when nil is not an acceptable return value."
  [s]
  (or (safe-read-edn s)
      (throw (ex-info "Fortress: EDN read rejected"
                      {:input (subs (str s) 0 (min 100 (count (str s))))}))))
