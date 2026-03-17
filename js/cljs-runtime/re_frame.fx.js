goog.provide('re_frame.fx');
re_frame.fx.kind = new cljs.core.Keyword(null,"fx","fx",-1237829572);
re_frame.fx.reg_fx = (function re_frame$fx$reg_fx(id,handler){
return re_frame.registrar.register_handler(re_frame.fx.kind,id,handler);
});
/**
 * An interceptor whose `:after` actions the contents of `:effects`. As a result,
 *   this interceptor is Domino 3.
 * 
 *   This interceptor is silently added (by reg-event-db etc) to the front of
 *   interceptor chains for all events.
 * 
 *   For each key in `:effects` (a map), it calls the registered `effects handler`
 *   (see `reg-fx` for registration of effect handlers).
 * 
 *   So, if `:effects` was:
 *    {:dispatch  [:hello 42]
 *     :db        {...}
 *     :undo      "set flag"}
 * 
 *   it will call the registered effect handlers for each of the map's keys:
 *   `:dispatch`, `:undo` and `:db`. When calling each handler, provides the map
 *   value for that key - so in the example above the effect handler for :dispatch
 *   will be given one arg `[:hello 42]`.
 * 
 *   You cannot rely on the ordering in which effects are executed, other than that
 *   `:db` is guaranteed to be executed first.
 */
re_frame.fx.do_fx = re_frame.interceptor.__GT_interceptor.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"id","id",-1388402092),new cljs.core.Keyword(null,"do-fx","do-fx",1194163050),new cljs.core.Keyword(null,"after","after",594996914),(function re_frame$fx$do_fx_after(context){
if(re_frame.trace.is_trace_enabled_QMARK_()){
var _STAR_current_trace_STAR__orig_val__11044 = re_frame.trace._STAR_current_trace_STAR_;
var _STAR_current_trace_STAR__temp_val__11045 = re_frame.trace.start_trace(new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"op-type","op-type",-1636141668),new cljs.core.Keyword("event","do-fx","event/do-fx",1357330452)], null));
(re_frame.trace._STAR_current_trace_STAR_ = _STAR_current_trace_STAR__temp_val__11045);

try{try{var effects = new cljs.core.Keyword(null,"effects","effects",-282369292).cljs$core$IFn$_invoke$arity$1(context);
var effects_without_db = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(effects,new cljs.core.Keyword(null,"db","db",993250759));
var temp__5804__auto___11277 = new cljs.core.Keyword(null,"db","db",993250759).cljs$core$IFn$_invoke$arity$1(effects);
if(cljs.core.truth_(temp__5804__auto___11277)){
var new_db_11278 = temp__5804__auto___11277;
var fexpr__11067_11279 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,new cljs.core.Keyword(null,"db","db",993250759),false);
(fexpr__11067_11279.cljs$core$IFn$_invoke$arity$1 ? fexpr__11067_11279.cljs$core$IFn$_invoke$arity$1(new_db_11278) : fexpr__11067_11279(new_db_11278));
} else {
}

var seq__11069 = cljs.core.seq(effects_without_db);
var chunk__11070 = null;
var count__11071 = (0);
var i__11072 = (0);
while(true){
if((i__11072 < count__11071)){
var vec__11094 = chunk__11070.cljs$core$IIndexed$_nth$arity$2(null, i__11072);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11094,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11094,(1),null);
var temp__5802__auto___11283 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5802__auto___11283)){
var effect_fn_11284 = temp__5802__auto___11283;
(effect_fn_11284.cljs$core$IFn$_invoke$arity$1 ? effect_fn_11284.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_11284(effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: no handler registered for effect:",effect_key,". Ignoring.",((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"event","event",301435442),effect_key))?["You may be trying to return a coeffect map from an event-fx handler. ","See https://day8.github.io/re-frame/use-cofx-as-fx/"].join(''):null)], 0));
}


var G__11285 = seq__11069;
var G__11286 = chunk__11070;
var G__11287 = count__11071;
var G__11288 = (i__11072 + (1));
seq__11069 = G__11285;
chunk__11070 = G__11286;
count__11071 = G__11287;
i__11072 = G__11288;
continue;
} else {
var temp__5804__auto__ = cljs.core.seq(seq__11069);
if(temp__5804__auto__){
var seq__11069__$1 = temp__5804__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__11069__$1)){
var c__5525__auto__ = cljs.core.chunk_first(seq__11069__$1);
var G__11291 = cljs.core.chunk_rest(seq__11069__$1);
var G__11292 = c__5525__auto__;
var G__11293 = cljs.core.count(c__5525__auto__);
var G__11294 = (0);
seq__11069 = G__11291;
chunk__11070 = G__11292;
count__11071 = G__11293;
i__11072 = G__11294;
continue;
} else {
var vec__11105 = cljs.core.first(seq__11069__$1);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11105,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11105,(1),null);
var temp__5802__auto___11297 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5802__auto___11297)){
var effect_fn_11298 = temp__5802__auto___11297;
(effect_fn_11298.cljs$core$IFn$_invoke$arity$1 ? effect_fn_11298.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_11298(effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: no handler registered for effect:",effect_key,". Ignoring.",((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"event","event",301435442),effect_key))?["You may be trying to return a coeffect map from an event-fx handler. ","See https://day8.github.io/re-frame/use-cofx-as-fx/"].join(''):null)], 0));
}


var G__11299 = cljs.core.next(seq__11069__$1);
var G__11300 = null;
var G__11301 = (0);
var G__11302 = (0);
seq__11069 = G__11299;
chunk__11070 = G__11300;
count__11071 = G__11301;
i__11072 = G__11302;
continue;
}
} else {
return null;
}
}
break;
}
}finally {if(re_frame.trace.is_trace_enabled_QMARK_()){
var end__10550__auto___11303 = re_frame.interop.now();
var duration__10551__auto___11304 = (end__10550__auto___11303 - new cljs.core.Keyword(null,"start","start",-355208981).cljs$core$IFn$_invoke$arity$1(re_frame.trace._STAR_current_trace_STAR_));
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(re_frame.trace.traces,cljs.core.conj,cljs.core.assoc.cljs$core$IFn$_invoke$arity$variadic(re_frame.trace._STAR_current_trace_STAR_,new cljs.core.Keyword(null,"duration","duration",1444101068),duration__10551__auto___11304,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"end","end",-268185958),re_frame.interop.now()], 0)));

re_frame.trace.run_tracing_callbacks_BANG_(end__10550__auto___11303);
} else {
}
}}finally {(re_frame.trace._STAR_current_trace_STAR_ = _STAR_current_trace_STAR__orig_val__11044);
}} else {
var effects = new cljs.core.Keyword(null,"effects","effects",-282369292).cljs$core$IFn$_invoke$arity$1(context);
var effects_without_db = cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(effects,new cljs.core.Keyword(null,"db","db",993250759));
var temp__5804__auto___11305 = new cljs.core.Keyword(null,"db","db",993250759).cljs$core$IFn$_invoke$arity$1(effects);
if(cljs.core.truth_(temp__5804__auto___11305)){
var new_db_11306 = temp__5804__auto___11305;
var fexpr__11109_11307 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,new cljs.core.Keyword(null,"db","db",993250759),false);
(fexpr__11109_11307.cljs$core$IFn$_invoke$arity$1 ? fexpr__11109_11307.cljs$core$IFn$_invoke$arity$1(new_db_11306) : fexpr__11109_11307(new_db_11306));
} else {
}

var seq__11110 = cljs.core.seq(effects_without_db);
var chunk__11111 = null;
var count__11112 = (0);
var i__11113 = (0);
while(true){
if((i__11113 < count__11112)){
var vec__11128 = chunk__11111.cljs$core$IIndexed$_nth$arity$2(null, i__11113);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11128,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11128,(1),null);
var temp__5802__auto___11308 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5802__auto___11308)){
var effect_fn_11310 = temp__5802__auto___11308;
(effect_fn_11310.cljs$core$IFn$_invoke$arity$1 ? effect_fn_11310.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_11310(effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: no handler registered for effect:",effect_key,". Ignoring.",((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"event","event",301435442),effect_key))?["You may be trying to return a coeffect map from an event-fx handler. ","See https://day8.github.io/re-frame/use-cofx-as-fx/"].join(''):null)], 0));
}


var G__11313 = seq__11110;
var G__11314 = chunk__11111;
var G__11315 = count__11112;
var G__11316 = (i__11113 + (1));
seq__11110 = G__11313;
chunk__11111 = G__11314;
count__11112 = G__11315;
i__11113 = G__11316;
continue;
} else {
var temp__5804__auto__ = cljs.core.seq(seq__11110);
if(temp__5804__auto__){
var seq__11110__$1 = temp__5804__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__11110__$1)){
var c__5525__auto__ = cljs.core.chunk_first(seq__11110__$1);
var G__11317 = cljs.core.chunk_rest(seq__11110__$1);
var G__11318 = c__5525__auto__;
var G__11319 = cljs.core.count(c__5525__auto__);
var G__11320 = (0);
seq__11110 = G__11317;
chunk__11111 = G__11318;
count__11112 = G__11319;
i__11113 = G__11320;
continue;
} else {
var vec__11137 = cljs.core.first(seq__11110__$1);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11137,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11137,(1),null);
var temp__5802__auto___11322 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5802__auto___11322)){
var effect_fn_11323 = temp__5802__auto___11322;
(effect_fn_11323.cljs$core$IFn$_invoke$arity$1 ? effect_fn_11323.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_11323(effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: no handler registered for effect:",effect_key,". Ignoring.",((cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"event","event",301435442),effect_key))?["You may be trying to return a coeffect map from an event-fx handler. ","See https://day8.github.io/re-frame/use-cofx-as-fx/"].join(''):null)], 0));
}


var G__11324 = cljs.core.next(seq__11110__$1);
var G__11325 = null;
var G__11326 = (0);
var G__11327 = (0);
seq__11110 = G__11324;
chunk__11111 = G__11325;
count__11112 = G__11326;
i__11113 = G__11327;
continue;
}
} else {
return null;
}
}
break;
}
}
})], 0));
re_frame.fx.dispatch_later = (function re_frame$fx$dispatch_later(p__11154){
var map__11155 = p__11154;
var map__11155__$1 = cljs.core.__destructure_map(map__11155);
var effect = map__11155__$1;
var ms = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__11155__$1,new cljs.core.Keyword(null,"ms","ms",-1152709733));
var dispatch = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__11155__$1,new cljs.core.Keyword(null,"dispatch","dispatch",1319337009));
if(((cljs.core.empty_QMARK_(dispatch)) || ((!(typeof ms === 'number'))))){
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: ignoring bad :dispatch-later value:",effect], 0));
} else {
return re_frame.interop.set_timeout_BANG_((function (){
return re_frame.router.dispatch(dispatch);
}),ms);
}
});
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"dispatch-later","dispatch-later",291951390),(function (value){
if(cljs.core.map_QMARK_(value)){
return re_frame.fx.dispatch_later(value);
} else {
var seq__11164 = cljs.core.seq(cljs.core.remove.cljs$core$IFn$_invoke$arity$2(cljs.core.nil_QMARK_,value));
var chunk__11165 = null;
var count__11166 = (0);
var i__11167 = (0);
while(true){
if((i__11167 < count__11166)){
var effect = chunk__11165.cljs$core$IIndexed$_nth$arity$2(null, i__11167);
re_frame.fx.dispatch_later(effect);


var G__11330 = seq__11164;
var G__11331 = chunk__11165;
var G__11332 = count__11166;
var G__11333 = (i__11167 + (1));
seq__11164 = G__11330;
chunk__11165 = G__11331;
count__11166 = G__11332;
i__11167 = G__11333;
continue;
} else {
var temp__5804__auto__ = cljs.core.seq(seq__11164);
if(temp__5804__auto__){
var seq__11164__$1 = temp__5804__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__11164__$1)){
var c__5525__auto__ = cljs.core.chunk_first(seq__11164__$1);
var G__11334 = cljs.core.chunk_rest(seq__11164__$1);
var G__11335 = c__5525__auto__;
var G__11336 = cljs.core.count(c__5525__auto__);
var G__11337 = (0);
seq__11164 = G__11334;
chunk__11165 = G__11335;
count__11166 = G__11336;
i__11167 = G__11337;
continue;
} else {
var effect = cljs.core.first(seq__11164__$1);
re_frame.fx.dispatch_later(effect);


var G__11338 = cljs.core.next(seq__11164__$1);
var G__11339 = null;
var G__11340 = (0);
var G__11341 = (0);
seq__11164 = G__11338;
chunk__11165 = G__11339;
count__11166 = G__11340;
i__11167 = G__11341;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"fx","fx",-1237829572),(function (seq_of_effects){
if((!(cljs.core.sequential_QMARK_(seq_of_effects)))){
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: \":fx\" effect expects a seq, but was given ",cljs.core.type(seq_of_effects)], 0));
} else {
var seq__11182 = cljs.core.seq(cljs.core.remove.cljs$core$IFn$_invoke$arity$2(cljs.core.nil_QMARK_,seq_of_effects));
var chunk__11183 = null;
var count__11184 = (0);
var i__11185 = (0);
while(true){
if((i__11185 < count__11184)){
var vec__11204 = chunk__11183.cljs$core$IIndexed$_nth$arity$2(null, i__11185);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11204,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11204,(1),null);
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"db","db",993250759),effect_key)){
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: \":fx\" effect should not contain a :db effect"], 0));
} else {
}

var temp__5802__auto___11343 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5802__auto___11343)){
var effect_fn_11344 = temp__5802__auto___11343;
(effect_fn_11344.cljs$core$IFn$_invoke$arity$1 ? effect_fn_11344.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_11344(effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: in \":fx\" effect found ",effect_key," which has no associated handler. Ignoring."], 0));
}


var G__11347 = seq__11182;
var G__11348 = chunk__11183;
var G__11349 = count__11184;
var G__11350 = (i__11185 + (1));
seq__11182 = G__11347;
chunk__11183 = G__11348;
count__11184 = G__11349;
i__11185 = G__11350;
continue;
} else {
var temp__5804__auto__ = cljs.core.seq(seq__11182);
if(temp__5804__auto__){
var seq__11182__$1 = temp__5804__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__11182__$1)){
var c__5525__auto__ = cljs.core.chunk_first(seq__11182__$1);
var G__11352 = cljs.core.chunk_rest(seq__11182__$1);
var G__11353 = c__5525__auto__;
var G__11354 = cljs.core.count(c__5525__auto__);
var G__11355 = (0);
seq__11182 = G__11352;
chunk__11183 = G__11353;
count__11184 = G__11354;
i__11185 = G__11355;
continue;
} else {
var vec__11211 = cljs.core.first(seq__11182__$1);
var effect_key = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11211,(0),null);
var effect_value = cljs.core.nth.cljs$core$IFn$_invoke$arity$3(vec__11211,(1),null);
if(cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(new cljs.core.Keyword(null,"db","db",993250759),effect_key)){
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: \":fx\" effect should not contain a :db effect"], 0));
} else {
}

var temp__5802__auto___11356 = re_frame.registrar.get_handler.cljs$core$IFn$_invoke$arity$3(re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5802__auto___11356)){
var effect_fn_11357 = temp__5802__auto___11356;
(effect_fn_11357.cljs$core$IFn$_invoke$arity$1 ? effect_fn_11357.cljs$core$IFn$_invoke$arity$1(effect_value) : effect_fn_11357(effect_value));
} else {
re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"warn","warn",-436710552),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: in \":fx\" effect found ",effect_key," which has no associated handler. Ignoring."], 0));
}


var G__11358 = cljs.core.next(seq__11182__$1);
var G__11359 = null;
var G__11360 = (0);
var G__11361 = (0);
seq__11182 = G__11358;
chunk__11183 = G__11359;
count__11184 = G__11360;
i__11185 = G__11361;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),(function (value){
if((!(cljs.core.vector_QMARK_(value)))){
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: ignoring bad :dispatch value. Expected a vector, but got:",value], 0));
} else {
return re_frame.router.dispatch(value);
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"dispatch-n","dispatch-n",-504469236),(function (value){
if((!(cljs.core.sequential_QMARK_(value)))){
return re_frame.loggers.console.cljs$core$IFn$_invoke$arity$variadic(new cljs.core.Keyword(null,"error","error",-978969032),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2(["re-frame: ignoring bad :dispatch-n value. Expected a collection, but got:",value], 0));
} else {
var seq__11228 = cljs.core.seq(cljs.core.remove.cljs$core$IFn$_invoke$arity$2(cljs.core.nil_QMARK_,value));
var chunk__11229 = null;
var count__11230 = (0);
var i__11231 = (0);
while(true){
if((i__11231 < count__11230)){
var event = chunk__11229.cljs$core$IIndexed$_nth$arity$2(null, i__11231);
re_frame.router.dispatch(event);


var G__11364 = seq__11228;
var G__11365 = chunk__11229;
var G__11366 = count__11230;
var G__11367 = (i__11231 + (1));
seq__11228 = G__11364;
chunk__11229 = G__11365;
count__11230 = G__11366;
i__11231 = G__11367;
continue;
} else {
var temp__5804__auto__ = cljs.core.seq(seq__11228);
if(temp__5804__auto__){
var seq__11228__$1 = temp__5804__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__11228__$1)){
var c__5525__auto__ = cljs.core.chunk_first(seq__11228__$1);
var G__11368 = cljs.core.chunk_rest(seq__11228__$1);
var G__11369 = c__5525__auto__;
var G__11370 = cljs.core.count(c__5525__auto__);
var G__11371 = (0);
seq__11228 = G__11368;
chunk__11229 = G__11369;
count__11230 = G__11370;
i__11231 = G__11371;
continue;
} else {
var event = cljs.core.first(seq__11228__$1);
re_frame.router.dispatch(event);


var G__11372 = cljs.core.next(seq__11228__$1);
var G__11373 = null;
var G__11374 = (0);
var G__11375 = (0);
seq__11228 = G__11372;
chunk__11229 = G__11373;
count__11230 = G__11374;
i__11231 = G__11375;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"deregister-event-handler","deregister-event-handler",-1096518994),(function (value){
var clear_event = cljs.core.partial.cljs$core$IFn$_invoke$arity$2(re_frame.registrar.clear_handlers,re_frame.events.kind);
if(cljs.core.sequential_QMARK_(value)){
var seq__11241 = cljs.core.seq(value);
var chunk__11242 = null;
var count__11243 = (0);
var i__11244 = (0);
while(true){
if((i__11244 < count__11243)){
var event = chunk__11242.cljs$core$IIndexed$_nth$arity$2(null, i__11244);
clear_event(event);


var G__11376 = seq__11241;
var G__11377 = chunk__11242;
var G__11378 = count__11243;
var G__11379 = (i__11244 + (1));
seq__11241 = G__11376;
chunk__11242 = G__11377;
count__11243 = G__11378;
i__11244 = G__11379;
continue;
} else {
var temp__5804__auto__ = cljs.core.seq(seq__11241);
if(temp__5804__auto__){
var seq__11241__$1 = temp__5804__auto__;
if(cljs.core.chunked_seq_QMARK_(seq__11241__$1)){
var c__5525__auto__ = cljs.core.chunk_first(seq__11241__$1);
var G__11380 = cljs.core.chunk_rest(seq__11241__$1);
var G__11381 = c__5525__auto__;
var G__11382 = cljs.core.count(c__5525__auto__);
var G__11383 = (0);
seq__11241 = G__11380;
chunk__11242 = G__11381;
count__11243 = G__11382;
i__11244 = G__11383;
continue;
} else {
var event = cljs.core.first(seq__11241__$1);
clear_event(event);


var G__11385 = cljs.core.next(seq__11241__$1);
var G__11386 = null;
var G__11387 = (0);
var G__11388 = (0);
seq__11241 = G__11385;
chunk__11242 = G__11386;
count__11243 = G__11387;
i__11244 = G__11388;
continue;
}
} else {
return null;
}
}
break;
}
} else {
return clear_event(value);
}
}));
re_frame.fx.reg_fx(new cljs.core.Keyword(null,"db","db",993250759),(function (value){
if((!((cljs.core.deref(re_frame.db.app_db) === value)))){
return cljs.core.reset_BANG_(re_frame.db.app_db,value);
} else {
if(re_frame.trace.is_trace_enabled_QMARK_()){
var _STAR_current_trace_STAR__orig_val__11262 = re_frame.trace._STAR_current_trace_STAR_;
var _STAR_current_trace_STAR__temp_val__11263 = re_frame.trace.start_trace(new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"op-type","op-type",-1636141668),new cljs.core.Keyword("reagent","quiescent","reagent/quiescent",-16138681)], null));
(re_frame.trace._STAR_current_trace_STAR_ = _STAR_current_trace_STAR__temp_val__11263);

try{try{return null;
}finally {if(re_frame.trace.is_trace_enabled_QMARK_()){
var end__10550__auto___11391 = re_frame.interop.now();
var duration__10551__auto___11392 = (end__10550__auto___11391 - new cljs.core.Keyword(null,"start","start",-355208981).cljs$core$IFn$_invoke$arity$1(re_frame.trace._STAR_current_trace_STAR_));
cljs.core.swap_BANG_.cljs$core$IFn$_invoke$arity$3(re_frame.trace.traces,cljs.core.conj,cljs.core.assoc.cljs$core$IFn$_invoke$arity$variadic(re_frame.trace._STAR_current_trace_STAR_,new cljs.core.Keyword(null,"duration","duration",1444101068),duration__10551__auto___11392,cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"end","end",-268185958),re_frame.interop.now()], 0)));

re_frame.trace.run_tracing_callbacks_BANG_(end__10550__auto___11391);
} else {
}
}}finally {(re_frame.trace._STAR_current_trace_STAR_ = _STAR_current_trace_STAR__orig_val__11262);
}} else {
return null;
}
}
}));

//# sourceMappingURL=re_frame.fx.js.map
