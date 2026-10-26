/*
 * Playwright for the docs tooling. Playwright is not a dependency of this repository: it is
 * resolved from wherever it is already installed (an npx cache is normal), and the browser
 * Playwright has already downloaded is used.
 */

import { createRequire } from 'node:module';
import { execSync } from 'node:child_process';

export function loadPlaywright() {
    const require = createRequire(import.meta.url);
    try {
        return require('playwright');
    } catch {
        // not installed here: fall back to any copy npx has already cached
        const found = execSync(
            'find ~/.npm/_npx -maxdepth 4 -type d -name playwright 2>/dev/null | head -1',
            { encoding: 'utf8', shell: '/bin/bash' },
        ).trim();
        if (!found) {
            throw new Error('playwright not found; run: npx playwright@latest install chromium');
        }
        return require(found);
    }
}

export function findInstalledChromium() {
    // Use the browser Playwright already downloaded, so the script does not
    // depend on the launcher's own revision pinning matching this machine.
    const out = execSync(
        'ls -d ~/Library/Caches/ms-playwright/chromium-*/chrome-mac*/ 2>/dev/null | tail -1',
        { encoding: 'utf8', shell: '/bin/bash' },
    ).trim();
    if (!out) return undefined;
    const candidates = execSync(
        `find "${out}" -maxdepth 4 -type f \\( -name 'Google Chrome for Testing' -o -name 'Chromium' \\) 2>/dev/null | head -1`,
        { encoding: 'utf8', shell: '/bin/bash' },
    ).trim();
    return candidates || undefined;
}

export async function launchChromium() {
    const { chromium } = loadPlaywright();
    const executablePath = findInstalledChromium();
    return chromium.launch(executablePath ? { executablePath } : {});
}
