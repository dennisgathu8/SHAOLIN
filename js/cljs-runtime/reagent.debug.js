goog.provide('reagent.debug');
reagent.debug.has_console = (typeof console !== 'undefined');
reagent.debug.tracking = false;
if((typeof reagent !== 'undefined') && (typeof reagent.debug !== 'undefined') && (typeof reagent.debug.warnings !== 'undefined')){
} else {
reagent.debug.warnings = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(null);
}
if((typeof reagent !== 'undefined') && (typeof reagent.debug !== 'undefined') && (typeof reagent.debug.track_console !== 'undefined')){
} else {
reagent.debug.track_console = (function (){var o = ({});
(o.warn = (function() { 
var G__7735__delegate = function (args){
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$variadic(reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"warn","warn",-436710552)], null),cljs.core.conj,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.str,args)], 0));
};
var G__7735 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__7736__i = 0, G__7736__a = new Array(arguments.length -  0);
while (G__7736__i < G__7736__a.length) {G__7736__a[G__7736__i] = arguments[G__7736__i + 0]; ++G__7736__i;}
  args = new cljs.core.IndexedSeq(G__7736__a,0,null);
} 
return G__7735__delegate.call(this,args);};
G__7735.cljs$lang$maxFixedArity = 0;
G__7735.cljs$lang$applyTo = (function (arglist__7737){
var args = cljs.core.seq(arglist__7737);
return G__7735__delegate(args);
});
G__7735.cljs$core$IFn$_invoke$arity$variadic = G__7735__delegate;
return G__7735;
})()
);

(o.error = (function() { 
var G__7738__delegate = function (args){
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$variadic(reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"error","error",-978969032)], null),cljs.core.conj,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.str,args)], 0));
};
var G__7738 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__7742__i = 0, G__7742__a = new Array(arguments.length -  0);
while (G__7742__i < G__7742__a.length) {G__7742__a[G__7742__i] = arguments[G__7742__i + 0]; ++G__7742__i;}
  args = new cljs.core.IndexedSeq(G__7742__a,0,null);
} 
return G__7738__delegate.call(this,args);};
G__7738.cljs$lang$maxFixedArity = 0;
G__7738.cljs$lang$applyTo = (function (arglist__7743){
var args = cljs.core.seq(arglist__7743);
return G__7738__delegate(args);
});
G__7738.cljs$core$IFn$_invoke$arity$variadic = G__7738__delegate;
return G__7738;
})()
);

return o;
})();
}
reagent.debug.track_warnings = (function reagent$debug$track_warnings(f){
(reagent.debug.tracking = true);

cljs.core.reset_BANG_(reagent.debug.warnings,null);

(f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f());

var warns = cljs.core.deref(reagent.debug.warnings);
cljs.core.reset_BANG_(reagent.debug.warnings,null);

(reagent.debug.tracking = false);

return warns;
});

//# sourceMappingURL=reagent.debug.js.map
