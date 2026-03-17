goog.provide('fortress.config');
fortress.config.version = "0.1.0";
fortress.config.app_name = "FKF Decision Simulator";
/**
 * Feature flags — toggle functionality without code changes.
 */
fortress.config.features = new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"encryption-enabled?","encryption-enabled?",-1495894747),true,new cljs.core.Keyword(null,"coach-mode?","coach-mode?",-1845771785),false,new cljs.core.Keyword(null,"pdf-export?","pdf-export?",1880507173),false,new cljs.core.Keyword(null,"offline-mode?","offline-mode?",-469738512),true,new cljs.core.Keyword(null,"debug-specs?","debug-specs?",1841910260),goog.DEBUG], null);
/**
 * Default timer settings for scenarios.
 */
fortress.config.timer_defaults = new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"min-ms","min-ms",-752885818),(1000),new cljs.core.Keyword(null,"max-ms","max-ms",1428854348),(30000),new cljs.core.Keyword(null,"tick-ms","tick-ms",1972135455),(100)], null);
/**
 * Scoring constants.
 */
fortress.config.scoring = new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"time-bonus-threshold-ms","time-bonus-threshold-ms",-1732186213),(3000),new cljs.core.Keyword(null,"time-bonus-points","time-bonus-points",387171099),(2),new cljs.core.Keyword(null,"streak-bonus-threshold","streak-bonus-threshold",-857284606),(5),new cljs.core.Keyword(null,"streak-bonus-points","streak-bonus-points",588960315),(3)], null);
/**
 * Grade boundaries by correct-decision percentage.
 */
fortress.config.grade_thresholds = new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"legend","legend",-1027192245),(90),new cljs.core.Keyword(null,"elite","elite",-1105190752),(75),new cljs.core.Keyword(null,"gold","gold",-806826416),(60),new cljs.core.Keyword(null,"silver","silver",1044501468),(40),new cljs.core.Keyword(null,"bronze","bronze",151868654),(0)], null);

//# sourceMappingURL=fortress.config.js.map
