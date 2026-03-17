goog.provide('fortress.dev');
console.log("\uD83D\uDD27 Fortress dev tools loaded");
/**
 * Print current re-frame app-db to console.
 * Call from REPL: (fortress.dev/dump-db)
 */
fortress.dev.dump_db = (function fortress$dev$dump_db(){
var db = cljs.core.deref(re_frame.db.app_db);
console.log("\uD83D\uDCE6 app-db:",cljs.core.pr_str.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([db], 0)));

if(fortress.db.schema.valid_db_QMARK_(db)){
} else {
console.warn("\u26A0\uFE0F app-db spec violation!",cljs.core.pr_str.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([fortress.db.schema.explain_db(db)], 0)));
}

return db;
});

//# sourceMappingURL=fortress.dev.js.map
