goog.provide('re_frame.trace');
re_frame.trace.id = cljs.core.atom.cljs$core$IFn$_invoke$arity$1((0));
re_frame.trace._STAR_current_trace_STAR_ = null;
re_frame.trace.reset_tracing_BANG_ = (function re_frame$trace$reset_tracing_BANG_(){
return cljs.core.reset_BANG_(re_frame.trace.id,(0));
});
/**
 * @define {boolean}
 */
re_frame.trace.trace_enabled_QMARK_ = goog.define("re_frame.trace.trace_enabled_QMARK_",false);
/**
 * See https://groups.google.com/d/msg/clojurescript/jk43kmYiMhA/IHglVr_TPdgJ for more details
 */
re_frame.trace.is_trace_enabled_QMARK_ = (function re_frame$trace$is_trace_enabled_QMARK_(){
return re_frame.trace.trace_enabled_QMARK_;
});
re_frame.trace.trace_cbs = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentArrayMap.EMPTY);
if((typeof re_frame !== 'undefined') && (typeof re_frame.trace !== 'undefined') && (typeof re_frame.trace.traces !== 'undefined')){
} else {
re_frame.trace.traces = cljs.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentVector.EMPTY);
}
if((typeof re_frame !== 'undefined') && (typeof re_frame.trace !== 'undefined') && (typeof re_frame.trace.next_delivery !== 'undefined')){
} else {
re_frame.trace.next_delivery = cljs.core.atom.cljs$core$IFn$_invoke$arity$1((0));
}
/**
 * Registers a tracing callback function which will receive a collection of one or more traces.
 *   Will replace an existing callback function if it shares the same key.
 */
re_frame.trace.register_trace_cb = (function re_frame$trace$register_trace_cb(key,f){
if(re_frame.trace.trace_enabled_QMARK_){
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$4(re_frame.trace.trace_cbs,cljs.core.assoc,key,f);
} else {
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Tracing is not enabled. Please set {\"re_frame.trace.trace_enabled_QMARK_\" true} in :closure-defines. See: https://github.com/day8/re-frame-10x#installation."], 0));
}
});
re_frame.trace.remove_trace_cb = (function re_frame$trace$remove_trace_cb(key){
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(re_frame.trace.trace_cbs,cljs.core.dissoc,key);

return null;
});
re_frame.trace.next_id = (function re_frame$trace$next_id(){
return cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$2(re_frame.trace.id,cljs.core.inc);
});
re_frame.trace.start_trace = (function re_frame$trace$start_trace(p__10576){
var map__10577 = p__10576;
var map__10577__$1 = cljs.core.__destructure_map(map__10577);
var operation = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__10577__$1,new cljs.core.Keyword(null,"operation","operation",-1267664310));
var op_type = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__10577__$1,new cljs.core.Keyword(null,"op-type","op-type",-1636141668));
var tags = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__10577__$1,new cljs.core.Keyword(null,"tags","tags",1771418977));
var child_of = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__10577__$1,new cljs.core.Keyword(null,"child-of","child-of",-903376662));
return new cljs.core.PersistentArrayMap(null, 6, [new cljs.core.Keyword(null,"id","id",-1388402092),re_frame.trace.next_id(),new cljs.core.Keyword(null,"operation","operation",-1267664310),operation,new cljs.core.Keyword(null,"op-type","op-type",-1636141668),op_type,new cljs.core.Keyword(null,"tags","tags",1771418977),tags,new cljs.core.Keyword(null,"child-of","child-of",-903376662),(function (){var or__5002__auto__ = child_of;
if(cljs.core.truth_(or__5002__auto__)){
return or__5002__auto__;
} else {
return new cljs.core.Keyword(null,"id","id",-1388402092).cljs$core$IFn$_invoke$arity$1(re_frame.trace._STAR_current_trace_STAR_);
}
})(),new cljs.core.Keyword(null,"start","start",-355208981),re_frame.interop.now()], null);
});
re_frame.trace.debounce_time = (50);
re_frame.trace.debounce = (function re_frame$trace$debounce(f,interval){
return goog.functions.debounce(f,interval);
});
re_frame.trace.schedule_debounce = re_frame.trace.debounce((function re_frame$trace$tracing_cb_debounced(){
var seq__10578_10631 = cljs.core.seq(cljs.core.deref(re_frame.trace.trace_cbs));
var chunk__10579_10632 = null;
var count__10580_10633 = (0);
var i__10581_10634 = (0);
while(true){
if((i__10581_10634 < count__10580_10633)){
var vec__10592_10637 = chunk__10579_10632.cljs$core$IIndexed$_nth$arity$2(null, i__10581_10634);
var k_10638 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__10592_10637,(0),null);
var cb_10639 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__10592_10637,(1),null);
try{var G__10597_10643 = cljs.core.deref(re_frame.trace.traces);
(cb_10639.cljs$core$IFn$_invoke$arity$1 ? cb_10639.cljs$core$IFn$_invoke$arity$1(G__10597_10643) : cb_10639(G__10597_10643));
}catch (e10595){var e_10644 = e10595;
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Error thrown from trace cb",k_10638,"while storing",cljs.core.deref(re_frame.trace.traces),e_10644], 0));
}

var G__10645 = seq__10578_10631;
var G__10646 = chunk__10579_10632;
var G__10647 = count__10580_10633;
var G__10648 = (i__10581_10634 + (1));
seq__10578_10631 = G__10645;
chunk__10579_10632 = G__10646;
count__10580_10633 = G__10647;
i__10581_10634 = G__10648;
continue;
} else {
var temp__5804__auto___10649 = cljs.core.seq(seq__10578_10631);
if(temp__5804__auto___10649){
var seq__10578_10650__$1 = temp__5804__auto___10649;
if(cljs.core.chunked_seq_QMARK_(seq__10578_10650__$1)){
var c__5525__auto___10652 = cljs.core.chunk_first(seq__10578_10650__$1);
var G__10653 = cljs.core.chunk_rest(seq__10578_10650__$1);
var G__10654 = c__5525__auto___10652;
var G__10655 = cljs.core.count(c__5525__auto___10652);
var G__10656 = (0);
seq__10578_10631 = G__10653;
chunk__10579_10632 = G__10654;
count__10580_10633 = G__10655;
i__10581_10634 = G__10656;
continue;
} else {
var vec__10598_10657 = cljs.core.first(seq__10578_10650__$1);
var k_10658 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__10598_10657,(0),null);
var cb_10659 = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__10598_10657,(1),null);
try{var G__10610_10660 = cljs.core.deref(re_frame.trace.traces);
(cb_10659.cljs$core$IFn$_invoke$arity$1 ? cb_10659.cljs$core$IFn$_invoke$arity$1(G__10610_10660) : cb_10659(G__10610_10660));
}catch (e10605){var e_10664 = e10605;
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["Error thrown from trace cb",k_10658,"while storing",cljs.core.deref(re_frame.trace.traces),e_10664], 0));
}

var G__10665 = cljs.core.next(seq__10578_10650__$1);
var G__10666 = null;
var G__10667 = (0);
var G__10668 = (0);
seq__10578_10631 = G__10665;
chunk__10579_10632 = G__10666;
count__10580_10633 = G__10667;
i__10581_10634 = G__10668;
continue;
}
} else {
}
}
break;
}

return cljs.core.reset_BANG_(re_frame.trace.traces,cljs.core.PersistentVector.EMPTY);
}),re_frame.trace.debounce_time);
re_frame.trace.run_tracing_callbacks_BANG_ = (function re_frame$trace$run_tracing_callbacks_BANG_(now){
if(((cljs.core.deref(re_frame.trace.next_delivery) - (25)) < now)){
(re_frame.trace.schedule_debounce.cljs$core$IFn$_invoke$arity$0 ? re_frame.trace.schedule_debounce.cljs$core$IFn$_invoke$arity$0() : re_frame.trace.schedule_debounce());

return cljs.core.reset_BANG_(re_frame.trace.next_delivery,(now + re_frame.trace.debounce_time));
} else {
return null;
}
});

//# sourceMappingURL=re_frame.trace.js.map
