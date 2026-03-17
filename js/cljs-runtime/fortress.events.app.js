goog.provide('fortress.events.app');
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("fortress.events.app","initialize-db","fortress.events.app/initialize-db",37985332),fortress.db.interceptors.fortress_interceptors,(function (_,___$1){
return fortress.db.default_db.default_db;
}));
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("fortress.events.app","set-error","fortress.events.app/set-error",-1702153136),fortress.db.interceptors.fortress_interceptors,(function (db,p__11634){
var vec__11635 = p__11634;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11635,(0),null);
var error_msg = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11635,(1),null);
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(db,new cljs.core.Keyword(null,"error","error",-978969032),error_msg);
}));
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("fortress.events.app","clear-error","fortress.events.app/clear-error",1419322442),fortress.db.interceptors.fortress_interceptors,(function (db,_){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(db,new cljs.core.Keyword(null,"error","error",-978969032),null);
}));
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("fortress.events.app","set-loading","fortress.events.app/set-loading",892381083),fortress.db.interceptors.fortress_interceptors,(function (db,p__11649){
var vec__11650 = p__11649;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11650,(0),null);
var loading_QMARK_ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11650,(1),null);
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(db,new cljs.core.Keyword(null,"loading?","loading?",1905707049),loading_QMARK_);
}));

//# sourceMappingURL=fortress.events.app.js.map
