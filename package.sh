#!/usr/bin/env bash

set -euo pipefail

npm run build

while read action_name; do
  ncc build -o "actions/${action_name//_/-}/dist" --source-map "lib/actions/$action_name/index.js"
done < <(ls -1 src/actions)
