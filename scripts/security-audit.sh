#!/usr/bin/env bash
# ============================================================
# Clojure Fortress — Security Audit Script
# Run after every phase. MUST return 0 results to pass.
# ============================================================

set -euo pipefail

SEARCH_DIR="src/main"
EXIT_CODE=0

echo "🏰 Clojure Fortress — Security Audit"
echo "======================================"
echo ""

# 1. Check for eval usage
echo "▶ Checking for eval / js/eval / js/Function..."
if grep -rnI 'js/eval\|js/Function\|(eval ' "$SEARCH_DIR"; then
  echo "❌ FAIL: eval usage found!"
  EXIT_CODE=1
else
  echo "✅ PASS: No eval usage"
fi
echo ""

# 2. Check for dangerouslySetInnerHTML
echo "▶ Checking for dangerouslySetInnerHTML..."
if grep -rnI 'dangerouslySetInnerHTML' "$SEARCH_DIR"; then
  echo "❌ FAIL: dangerouslySetInnerHTML found!"
  EXIT_CODE=1
else
  echo "✅ PASS: No dangerouslySetInnerHTML"
fi
echo ""

# 3. Check for set! on global objects
echo "▶ Checking for unsafe set! on globals..."
if grep -rnI 'set! js/' "$SEARCH_DIR"; then
  echo "❌ FAIL: Global mutation via set! found!"
  EXIT_CODE=1
else
  echo "✅ PASS: No global set!"
fi
echo ""

# 4. Check for dynamic require
echo "▶ Checking for dynamic require..."
if grep -rnI '(require ' "$SEARCH_DIR" | grep -v ':require'; then
  echo "❌ FAIL: Dynamic require found!"
  EXIT_CODE=1
else
  echo "✅ PASS: No dynamic require"
fi
echo ""

# 5. Check for raw cljs.reader usage (should use safe-read-edn)
echo "▶ Checking for raw cljs.reader/read-string usage outside security module..."
if grep -rnI 'cljs.reader/read-string' "$SEARCH_DIR" | grep -v 'security/edn_reader.cljs' | grep -v 'security/crypto.cljs'; then
  echo "❌ FAIL: Raw cljs.reader usage found outside security module!"
  EXIT_CODE=1
else
  echo "✅ PASS: EDN reading contained in security module"
fi
echo ""

# 6. Check for inline event handlers as strings
echo "▶ Checking for inline JS event handlers..."
if grep -rnI 'onclick="\|onerror="\|onload="' "$SEARCH_DIR"; then
  echo "❌ FAIL: Inline event handlers found!"
  EXIT_CODE=1
else
  echo "✅ PASS: No inline event handlers"
fi
echo ""

# Summary
echo "======================================"
if [ $EXIT_CODE -eq 0 ]; then
  echo "🏰 All security checks passed!"
else
  echo "⚠️  Security violations found — fix before proceeding!"
fi

exit $EXIT_CODE
