(ns fortress.security.crypto
  "Web Crypto API wrappers for AES-GCM encryption — Clojure Fortress.
   All localStorage/IndexedDB writes pass through encrypt-str.
   All reads pass through decrypt-str.
   
   Key derivation: PBKDF2 (100k iterations, SHA-256)
   Cipher: AES-GCM (256-bit key, 96-bit IV)
   
   Pure CLJS — no external crypto libraries."
  (:require [cljs.reader :as reader]))

;; ============================================================
;; Helpers
;; ============================================================

(defn- str->uint8array
  "Convert a string to Uint8Array via TextEncoder."
  [s]
  (.encode (js/TextEncoder.) s))

(defn- uint8array->str
  "Convert a Uint8Array to string via TextDecoder."
  [arr]
  (.decode (js/TextDecoder.) arr))

(defn- array-buffer->base64
  "Convert ArrayBuffer to base64 string for safe storage."
  [buf]
  (let [bytes (js/Uint8Array. buf)]
    (js/btoa (apply str (map #(js/String.fromCharCode %) (array-seq bytes))))))

(defn- base64->array-buffer
  "Convert base64 string back to ArrayBuffer."
  [b64]
  (let [binary (js/atob b64)
        len    (.-length binary)
        bytes  (js/Uint8Array. len)]
    (dotimes [i len]
      (aset bytes i (.charCodeAt binary i)))
    (.-buffer bytes)))

;; ============================================================
;; Key Derivation (PBKDF2)
;; ============================================================

(defn derive-key
  "Derive an AES-GCM-256 key from a passphrase using PBKDF2.
   Returns a js/Promise resolving to a CryptoKey."
  [passphrase salt-str]
  (let [subtle  (.-subtle js/crypto)
        raw-key (str->uint8array passphrase)
        salt    (str->uint8array salt-str)]
    (-> (.importKey subtle "raw" raw-key "PBKDF2" false ["deriveKey"])
        (.then (fn [base-key]
                 (.deriveKey subtle
                             #js {:name       "PBKDF2"
                                  :salt       salt
                                  :iterations 100000
                                  :hash       "SHA-256"}
                             base-key
                             #js {:name   "AES-GCM"
                                  :length 256}
                             false
                             #js ["encrypt" "decrypt"]))))))

;; ============================================================
;; Encrypt / Decrypt
;; ============================================================

(defn encrypt-str
  "Encrypt a string with AES-GCM. Returns a Promise resolving to
   a map {:iv <base64> :data <base64>} serialized as EDN string."
  [crypto-key plaintext]
  (let [subtle (.-subtle js/crypto)
        iv     (js/crypto.getRandomValues (js/Uint8Array. 12))
        data   (str->uint8array plaintext)]
    (-> (.encrypt subtle #js {:name "AES-GCM" :iv iv} crypto-key data)
        (.then (fn [encrypted]
                 (pr-str {:iv   (array-buffer->base64 (.-buffer iv))
                          :data (array-buffer->base64 encrypted)}))))))

(defn decrypt-str
  "Decrypt an EDN-encoded {:iv :data} map back to plaintext string.
   Returns a Promise resolving to the decrypted string."
  [crypto-key edn-str]
  (let [subtle  (.-subtle js/crypto)
        parsed  (cljs.reader/read-string edn-str)  ;; Internal use only, safe
        iv-buf  (base64->array-buffer (:iv parsed))
        data-buf (base64->array-buffer (:data parsed))]
    (-> (.decrypt subtle #js {:name "AES-GCM" :iv (js/Uint8Array. iv-buf)}
                  crypto-key data-buf)
        (.then uint8array->str))))
