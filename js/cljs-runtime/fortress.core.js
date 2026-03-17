goog.provide('fortress.core');
/**
 * Mount the Reagent root component to the DOM.
 * Called on init and on hot-reload.
 */
fortress.core.mount_root_BANG_ = (function fortress$core$mount_root_BANG_(){
re_frame.core.clear_subscription_cache_BANG_();

return reagent.dom.render.cljs$core$IFn$_invoke$arity$2(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [fortress.views.app.root_view], null),document.getElementById("app"));
});
/**
 * Application init — called once on page load.
 * 1. Initialize re-frame db
 * 2. Start router
 * 3. Mount Reagent root
 */
fortress.core.init_BANG_ = (function fortress$core$init_BANG_(){
re_frame.core.dispatch_sync(new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("fortress.events.app","initialize-db","fortress.events.app/initialize-db",37985332)], null));

fortress.router.core.init_router_BANG_();

fortress.core.mount_root_BANG_();

return console.log("\uD83C\uDFF0 Clojure Fortress initialized \u2014 v0.1.0");
});

//# sourceMappingURL=fortress.core.js.map
