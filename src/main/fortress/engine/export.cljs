(ns fortress.engine.export
  "Report generation logic: PDF + EDN export.
   Uses the installed 'pdf-lib' npm module via shadow-cljs."
  (:require [cljs.core.async :refer [go]]
            [cljs.core.async.interop :refer-macros [<p!]]
            [clojure.string :as str]
            ["pdf-lib" :refer [PDFDocument rgb StandardFonts]]))

(defn download-file [filename blob]
  (let [url (.createObjectURL js/URL blob)
        a   (.createElement js/document "a")]
    (set! (.-href a) url)
    (set! (.-download a) filename)
    (.appendChild (.-body js/document) a)
    (.click a)
    (.removeChild (.-body js/document) a)
    (.revokeObjectURL js/URL url)))

(defn export-edn [profile]
  (let [edn-str (pr-str profile)
        blob    (js/Blob. #js [edn-str] #js {:type "application/edn"})]
    (download-file "fortress_history.edn" blob)))

(defn export-pdf [profile]
  (go
    (try
      (let [pdf-doc   (<p! (.create PDFDocument))
            page      (.addPage pdf-doc)
            font      (<p! (.embedFont pdf-doc (.-Helvetica StandardFonts)))
            bold-font (<p! (.embedFont pdf-doc (.-HelveticaBold StandardFonts)))
            
            ;; Dimensions
            page-size (.getSize page)
            height    (.-height page-size)
            
            ;; Helpers
            draw-text (fn [text x y f size color]
                        (.drawText page text
                                   #js {:x x :y y
                                        :font f :size size :color color}))
                                        
            black (rgb 0 0 0)
            gray  (rgb 0.4 0.4 0.4)
            green (rgb 0 0.5 0)
            red   (rgb 0.8 0 0)]
            
        ;; Header
        (draw-text "FKF DECISION SIMULATOR - COACH REPORT" 50 (- height 50) bold-font 18 black)
        (draw-text (str "Player Name: " (:player-name profile)) 50 (- height 80) font 12 black)
        (draw-text (str "Overall Grade: " (str/upper-case (name (:grade profile)))) 50 (- height 100) font 12 black)
        (draw-text (str "Total Points: " (:total-points profile)) 50 (- height 120) font 12 black)
        (draw-text (str "Best Streak: " (:best-streak profile)) 50 (- height 140) font 12 black)
        
        ;; History Log Title
        (draw-text "Recent Decisions Log:" 50 (- height 180) bold-font 14 black)
        
        ;; Log Entries (max ~20 to fit on one page for now)
        (loop [entries (take 20 (reverse (:history profile)))
               y-pos   (- height 210)]
          (when (seq entries)
            (let [dec (first entries)
                  {:keys [scenario-title was-correct? time-taken-ms points-earned timed-out?]} dec
                  
                  status-txt (cond
                               timed-out? "TIMEOUT"
                               was-correct? "CORRECT"
                               :else "INCORRECT")
                  status-col (cond
                               timed-out? red
                               was-correct? green
                               :else red)
                  
                  time-txt   (str (Math/round (/ time-taken-ms 1000.0)) "s")
                  pts-txt    (str (if (> points-earned 0) "+" "") points-earned " pts")]
                  
              (draw-text scenario-title 50 y-pos font 11 black)
              (draw-text status-txt 300 y-pos bold-font 10 status-col)
              (draw-text time-txt 400 y-pos font 10 gray)
              (draw-text pts-txt 450 y-pos font 10 black)
              
              (when (> y-pos 50)
                (recur (rest entries) (- y-pos 25))))))
                
        ;; Generate and Download
        (let [pdf-bytes (<p! (.save pdf-doc))
              blob      (js/Blob. #js [pdf-bytes] #js {:type "application/pdf"})]
          (download-file "coach_report.pdf" blob)))
      (catch js/Error e
        (js/console.error "Error generating PDF:" e)))))
