# FKF Decision Simulator — Build From The Back 🏟️

> A browser-based tactical training tool for FKF SportPesa League players, built with Clojure and ClojureScript.

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![ClojureScript](https://img.shields.io/badge/ClojureScript-1.11-5881d8.svg)](https://clojurescript.org)
[![shadow-cljs](https://img.shields.io/badge/shadow--cljs-2.28-orange.svg)](https://shadow-cljs.org)
[![Live](https://img.shields.io/badge/Live-GitHub%20Pages-brightgreen)](https://dennisgathu8.github.io/SHAOLIN/)

**Live App →** [https://dennisgathu8.github.io/SHAOLIN/](https://dennisgathu8.github.io/SHAOLIN/)

---

## What Is This?

Most footballers understand the theory of building from the back. What they lack is repetition under pressure — the muscle memory to read a press, identify the free man, and execute the right pass before the window closes.

This simulator puts players inside those high-pressure moments:

- A **GK** distributing against a 4-3-3 high press
- A **CDM** receiving back-to-goal with a marker breathing down their neck
- A **fullback** pinned on the touchline against a 3-vs-1 overload

Each scenario presents multiple pass targets. You now have a relaxed **15-20 second window** to decide. The engine grades your choice, updates your streak, and gives you coach-level feedback on why the decision was right or wrong.

---

## Architecture

This is a **100% client-side** application — no server, no database, no analytics. Everything runs in the browser.

```
src/main/fortress/
├── core.cljs                  # Entry point — wires up all namespaces
├── config.cljs                # Grade thresholds, scoring constants
├── db/
│   ├── schema.cljs            # clojure.spec app-db contract
│   ├── default_db.cljs        # Initial app state
│   └── interceptors.cljs      # re-frame spec validator (runs on every event)
├── engine/
│   ├── scenarios.cljs         # Hardcoded scenario catalog (pure EDN)
│   ├── scoring.cljs           # Pure scoring functions — no side effects
│   └── export.cljs            # PDF + EDN report generation (pdf-lib)
├── events/
│   ├── app.cljs               # App lifecycle events (init, error, loading)
│   ├── scenario.cljs          # Load and select scenarios
│   ├── decision.cljs          # Evaluate pass choice or handle timeout
│   ├── timer.cljs             # Start/stop countdown timer events
│   └── persistence.cljs       # Encrypt/decrypt profile to localStorage
├── effects/
│   └── timer.cljs             # requestAnimationFrame effect (60fps accuracy)
├── security/
│   ├── crypto.cljs            # AES-GCM 256 encryption via Web Crypto API
│   ├── edn_reader.cljs        # Whitelist EDN parser (blocks #= eval, #js, etc.)
│   └── csp.cljs               # Content-Security-Policy runtime helpers
├── subs/                      # re-frame subscriptions
├── router/                    # Hash-based client routing (reitit)
└── views/                     # Reagent UI components
    ├── home.cljs              # Scenario selector + player stats
    ├── play.cljs              # SVG pitch + countdown + click handler
    ├── result.cljs            # Post-decision feedback screen
    ├── history.cljs           # Scrollable decision ledger
    └── coach.cljs             # Analytics + PDF/EDN export
```

### Key Technical Decisions

**Re-frame + Spec Interceptor**
Every re-frame event passes through a custom `fortress-interceptors` chain that validates `app-db` against a `clojure.spec` schema before and after every mutation. Invalid state transitions are caught at the event boundary and surfaced as warnings — not silent corruptions.

**requestAnimationFrame Timer**
Scenario countdowns use `requestAnimationFrame` rather than `setInterval`. This keeps the timer synchronised with the browser's render loop, avoids drift under CPU load, and precisely records `time-taken-ms` for each decision.

**Zero-Trust Local Storage**
Player profiles are stored in `localStorage` as AES-GCM 256-bit encrypted blobs. Keys are derived via PBKDF2 (100,000 iterations, SHA-256). On load, decrypted strings are passed through a hardened EDN reader that pre-scans for forbidden patterns (`#=`, `#js`, `js/eval`) before parsing — defence-in-depth against any tampered storage injection.

**Pure Functional Scoring**
`fortress.engine.scoring` contains no side effects. It takes a profile and a decision, returns a new profile. This makes the scoring logic trivially testable and completely decoupled from the re-frame event lifecycle.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | ClojureScript (via shadow-cljs) |
| UI | Reagent (React bindings) |
| State | re-frame |
| Router | reitit |
| Styling | Vanilla CSS (CSS custom properties) |
| Crypto | Web Crypto API (browser-native) |
| PDF Export | pdf-lib |
| Testing | cljs.test + day8.re-frame/test |
| Build | shadow-cljs 2.28 |

---

## Getting Started

### Prerequisites

- Java 11+
- Node.js 18+
- `npm`

### Install

```bash
git clone https://github.com/dennisgathu8/SHAOLIN.git
cd SHAOLIN
npm install
```

### Development

```bash
npm run dev
# Open http://localhost:8280
```

### Run Tests

```bash
npm test
node out/test.js
```

### Security Audit

```bash
npm run security-audit
```

Scans the source tree for: `eval`, `dangerouslySetInnerHTML`, unsafe global `set!`, dynamic `require`, raw `cljs.reader` usage outside the security module, and inline event handlers.

### Production Build

```bash
npm run release
```

### Deploy to GitHub Pages

```bash
npm run deploy
```

---

## Test Coverage

```
Ran 20 tests containing 94 assertions.
0 failures, 0 errors.
```

Tests span:
- `db.schema-test` — clojure.spec contract validation
- `engine.scoring-test` — pure scoring and grade boundary unit tests
- `events.scenario-test` — scenario lifecycle integration tests
- `events.decision-test` — decision evaluation and timeout integration tests
- `security.edn-reader-test` — whitelist parser injection resistance
- `views.pitch-test` — SVG component rendering

---

## Grading System

| Grade | Points Required |
|-------|----------------|
| 🥉 Bronze | 0 – 9 |
| 🥈 Silver | 10 – 24 |
| 🥇 Gold | 25 – 49 |
| ⭐ Elite | 50 – 99 |
| 🏆 Legend | 100+ |

---

## Scenarios

| ID | Title | Difficulty | Time |
|----|-------|-----------|------|
| `scen-bulid-001` | GK Build-up vs High Press | Beginner | 15s |
| `scen-mid-002` | Midfield Pivot Turn | Intermediate | 15s |
| `scen-adv-001` | Fullback Overload Trap | Advanced | 20s |

---

## Roadmap

- [ ] Phase 7: Additional scenario packs (set pieces, pressing triggers)
- [ ] Phase 8: Multi-player leaderboard via minimal Clojure backend
- [ ] Phase 9: Mobile PWA install support

---

## Security Policy

See `scripts/security-audit.sh` for the automated audit checks run on every change.

The application enforces a strict Content Security Policy via HTTP response headers (`public/_headers`) blocking inline scripts, eval, and cross-origin resource loading.

---

## License

MIT © 2026 Dennis Gathu
