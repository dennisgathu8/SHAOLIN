goog.provide('fortress.db.default_db');
/**
 * Fresh player profile for a new user.
 */
fortress.db.default_db.default_profile = cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"best-streak","best-streak",-1985668000),new cljs.core.Keyword(null,"total-decisions","total-decisions",-1736144350),new cljs.core.Keyword(null,"current-streak","current-streak",-1065385628),new cljs.core.Keyword(null,"total-points","total-points",2142237035),new cljs.core.Keyword(null,"history","history",-247395220),new cljs.core.Keyword(null,"grade","grade",2117054771),new cljs.core.Keyword(null,"id","id",-1388402092),new cljs.core.Keyword(null,"correct-decisions","correct-decisions",-1426787370),new cljs.core.Keyword(null,"player-name","player-name",-823697956)],[(0),(0),(0),(0),cljs.core.PersistentVector.EMPTY,new cljs.core.Keyword(null,"bronze","bronze",151868654),"default-player",(0),"Player"]);
/**
 * The initial re-frame app-db. Must conform to ::schema/app-db.
 */
fortress.db.default_db.default_db = cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"last-decision","last-decision",-307280282),new cljs.core.Keyword(null,"current-route","current-route",2067529448),new cljs.core.Keyword(null,"loading?","loading?",1905707049),new cljs.core.Keyword(null,"scenarios","scenarios",1618559369),new cljs.core.Keyword(null,"time-remaining-ms","time-remaining-ms",-207348680),new cljs.core.Keyword(null,"error","error",-978969032),new cljs.core.Keyword(null,"timer-running?","timer-running?",1947761339),new cljs.core.Keyword(null,"current-scenario","current-scenario",387065885),new cljs.core.Keyword(null,"profile","profile",-545963874)],[null,new cljs.core.Keyword(null,"home","home",-74557309),false,cljs.core.PersistentVector.EMPTY,(0),null,false,null,fortress.db.default_db.default_profile]);

//# sourceMappingURL=fortress.db.default_db.js.map
