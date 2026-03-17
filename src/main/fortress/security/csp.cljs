(ns fortress.security.csp
  "Content Security Policy helpers for Clojure Fortress.
   Generates CSP directives as pure data for meta tags and headers."
  (:require [clojure.string :as str]))

(def csp-directives
  "CSP directives as a Clojure map — single source of truth."
  {:default-src   "'self'"
   :script-src    "'self'"
   :style-src     "'self' 'unsafe-inline'"
   :img-src       "'self' data:"
   :connect-src   "'none'"
   :object-src    "'none'"
   :base-uri      "'self'"
   :form-action   "'self'"
   :frame-ancestors "'none'"})

(defn directives->str
  "Convert CSP directive map to a header-string.
   (directives->str csp-directives)
   => \"default-src 'self'; script-src 'self'; ...\""
  [directives]
  (->> directives
       (map (fn [[k v]] (str (name k) " " v)))
       (clojure.string/join "; ")))

(def csp-header-value
  "Pre-computed CSP header string."
  (directives->str csp-directives))
