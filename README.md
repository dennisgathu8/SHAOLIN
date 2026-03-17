# ⚽ FKF Decision Simulator — Build From The Back

> Interactive Decision-Making Simulator for FKF SportPesa League Players.
> Train correct decisions under press when building from the back.

![Clojure](https://img.shields.io/badge/Clojure-100%25-5881D8?style=flat-square)
![License](https://img.shields.io/badge/license-MIT-green?style=flat-square)
![Offline](https://img.shields.io/badge/offline-ready-brightgreen?style=flat-square)

## 🏰 Clojure Fortress

This application is built with **pure Clojure/ClojureScript** and follows the "Clojure Fortress" security architecture:

- **Immutable state** — re-frame app-db with spec interceptors on every event
- **Safe EDN reader** — rejects `#=`, `#js`, eval, and all dangerous tagged literals
- **AES-GCM encryption** — all stored data encrypted via Web Crypto API
- **CSP headers** — strict Content Security Policy, no inline scripts
- **Zero dependencies on paid services** — 100% free, 100% offline

## 🚀 Quick Start

```bash
# Install dependencies
npm install

# Start dev server (localhost:8080)
npm run dev

# Run tests
npm run test

# Security audit
npm run security-audit

# Production build
npm run release
```

## 📁 Project Structure

```
src/main/fortress/
├── core.cljs           # Entry point
├── config.cljs         # Constants & feature flags
├── db/                 # State: specs, default-db, interceptors
├── router/             # Client-side routing (reitit)
├── events/             # re-frame event handlers
├── subs/               # re-frame subscriptions
├── views/              # Reagent UI components
├── engine/             # Decision evaluation & scoring
└── security/           # EDN reader, crypto, CSP
```

## 🎯 Features

- **SVG Pitch** — Interactive football pitch with clickable pass targets
- **Timed Decisions** — Countdown timer to simulate match pressure
- **Scenario Library** — Realistic build-up scenarios from Kenyan football
- **Encrypted History** — AES-GCM encrypted decision history
- **Coach Mode** — Detailed analysis of decision patterns
- **PDF Export** — Generate reports for coaches and academies
- **100% Offline** — Works on any phone after first visit

## 📱 Optimized For

- Low-end Android phones (Tecno, Infinix, Samsung J-series)
- 2G/3G connections (< 200 KB gzipped)
- Chrome 80+, Samsung Internet, Opera Mini

## 🛡️ Security

Run the security audit after every change:

```bash
bash scripts/security-audit.sh
```

## 📄 License

MIT — Free and open source.
