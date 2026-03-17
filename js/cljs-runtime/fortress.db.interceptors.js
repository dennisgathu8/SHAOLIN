goog.provide('fortress.db.interceptors');
/**
 * Interceptor that validates app-db against ::schema/app-db
 * after every event handler runs. In dev mode, throws on failure.
 * In production (:advanced), asserts are elided but the interceptor
 * still logs to console.warn.
 */
fortress.db.interceptors.spec_check = re_frame.core.after((function (db){
if(fortress.db.schema.valid_db_QMARK_(db)){
return null;
} else {
var explain = fortress.db.schema.explain_db(db);
console.warn("\u26A0\uFE0F Fortress: app-db spec violation!",cljs.core.pr_str.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([explain], 0)));

return null;
}
}));
/**
 * Standard interceptor chain for all fortress events.
 * Add this to every reg-event-db / reg-event-fx.
 */
fortress.db.interceptors.fortress_interceptors = new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [fortress.db.interceptors.spec_check], null);

//# sourceMappingURL=fortress.db.interceptors.js.map
