goog.provide('fortress.router.core');
/**
 * Application routes — maps URL paths to route names.
 */
fortress.router.core.routes = new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["/",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"home","home",-74557309)], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["/play",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"play","play",-580418022)], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["/play/:id",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"scenario","scenario",-316635333)], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["/result",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"result","result",1415092211)], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["/history",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"history","history",-247395220)], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["/coach",new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"coach","coach",1551299182)], null)], null)], null);
re_frame.core.reg_event_db.cljs$core$IFn$_invoke$arity$3(new cljs.core.Keyword("fortress.router.core","navigate","fortress.router.core/navigate",23831889),fortress.db.interceptors.fortress_interceptors,(function (db,p__13441){
var vec__13442 = p__13441;
var _ = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__13442,(0),null);
var route_name = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__13442,(1),null);
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(db,new cljs.core.Keyword(null,"current-route","current-route",2067529448),route_name);
}));
/**
 * Callback invoked by reitit when the URL changes.
 * Dispatches a re-frame event to update :current-route.
 */
fortress.router.core.on_navigate = (function fortress$router$core$on_navigate(match,_history){
if(cljs.core.truth_(match)){
return re_frame.core.dispatch(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("fortress.router.core","navigate","fortress.router.core/navigate",23831889),cljs.core.get_in.cljs$core$IFn$_invoke$arity$2(match,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"data","data",-232669377),new cljs.core.Keyword(null,"name","name",1843675177)], null))], null));
} else {
return null;
}
});
/**
 * Initialize the reitit router. Call once at app startup.
 */
fortress.router.core.init_router_BANG_ = (function fortress$router$core$init_router_BANG_(){
return reitit.frontend.easy.start_BANG_(reitit.frontend.router.cljs$core$IFn$_invoke$arity$1(fortress.router.core.routes),fortress.router.core.on_navigate,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"use-fragment","use-fragment",-1617737154),true], null));
});

//# sourceMappingURL=fortress.router.core.js.map
