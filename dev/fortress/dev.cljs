(ns fortress.dev
  "Development-time helpers. Included via shadow-cljs :preloads
   in dev mode only. Elided from production builds."
  (:require [fortress.db.schema :as schema]
            [re-frame.db :as rf-db]))

(js/console.log "🔧 Fortress dev tools loaded")

(defn dump-db
  "Print current re-frame app-db to console.
   Call from REPL: (fortress.dev/dump-db)"
  []
  (let [db @re-frame.db/app-db]
    (js/console.log "📦 app-db:" (pr-str db))
    (when-not (schema/valid-db? db)
      (js/console.warn "⚠️ app-db spec violation!"
                       (pr-str (schema/explain-db db))))
    db))
