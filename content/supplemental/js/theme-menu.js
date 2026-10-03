// The color theme menu in the header: System, Light or Dark. The inline script in partials/head-styles.hbs sets
// data-theme and data-theme-preference on <html> before the stylesheets load; this script keeps them up to date,
// saves the choice, follows the system setting while System is chosen, and follows choices made in other tabs.
// The menu follows the WAI-ARIA menu button pattern with menuitemradio items.
(function () {
    const KEY = 'jmix-docs-theme';
    const LABELS = { system: 'System', light: 'Light', dark: 'Dark' };
    const root = document.documentElement;
    const menu = document.querySelector('.theme-menu');
    if (!menu) return;
    const toggle = menu.querySelector('.theme-menu-toggle');
    const list = menu.querySelector('.theme-menu-list');
    const items = Array.from(list.querySelectorAll('.theme-menu-item'));
    const system = window.matchMedia('(prefers-color-scheme: dark)');

    // the stored preference, or null when storage is blocked
    const read = function () {
        try {
            const value = localStorage.getItem(KEY);
            return value === 'light' || value === 'dark' ? value : 'system';
        } catch (e) {
            return null;
        }
    };
    const save = function (preference) {
        try {
            if (preference === 'system') localStorage.removeItem(KEY);
            else localStorage.setItem(KEY, preference);
        } catch (e) {
            // storage is blocked: the choice holds for this page only
        }
    };

    let preference = LABELS[root.getAttribute('data-theme-preference')] ? root.getAttribute('data-theme-preference') : read() || 'system';

    // every path goes through here: a choice, the system setting, another tab, a page restored from the cache
    const apply = function (next) {
        preference = next;
        const dark = next === 'dark' || (next === 'system' && system.matches);
        root.setAttribute('data-theme', dark ? 'dark' : 'light');
        root.setAttribute('data-theme-preference', next);
        const meta = document.querySelector('meta[name="color-scheme"]');
        if (meta) meta.content = dark ? 'dark' : 'light';
        const label = 'Color theme: ' + LABELS[next];
        toggle.setAttribute('aria-label', label);
        toggle.setAttribute('title', label);
        items.forEach(function (item) {
            item.setAttribute('aria-checked', String(item.dataset.themeOption === next));
        });
    };

    const isOpen = function () {
        return list.classList.contains('opened');
    };
    const open = function () {
        list.classList.add('opened');
        toggle.setAttribute('aria-expanded', 'true');
        const checked = items.find(function (item) {
            return item.dataset.themeOption === preference;
        });
        (checked || items[0]).focus();
    };
    const close = function (returnFocus) {
        list.classList.remove('opened');
        toggle.setAttribute('aria-expanded', 'false');
        if (returnFocus) toggle.focus();
    };

    // Enter and Space reach the buttons as clicks
    toggle.addEventListener('click', function () {
        if (isOpen()) close(false);
        else open();
    });
    toggle.addEventListener('keydown', function (event) {
        if (event.key === 'ArrowDown' || event.key === 'ArrowUp') {
            event.preventDefault();
            open();
        }
    });
    items.forEach(function (item) {
        item.addEventListener('click', function () {
            apply(item.dataset.themeOption);
            save(preference);
            close(true);
        });
    });
    list.addEventListener('keydown', function (event) {
        // Tab and Shift+Tab close the menu, and focus moves on from the item, which is hidden by then. Shift+Tab lands
        // on the button, which is inside the menu element, so the focusin listener below would not close the menu.
        if (event.key === 'Tab') {
            close(false);
            return;
        }
        const index = items.indexOf(document.activeElement);
        const target = {
            ArrowDown: (index + 1) % items.length,
            ArrowUp: (index - 1 + items.length) % items.length,
            Home: 0,
            End: items.length - 1,
        }[event.key];
        if (target !== undefined) {
            event.preventDefault();
            items[target].focus();
        }
    });
    menu.addEventListener('keydown', function (event) {
        if (event.key === 'Escape' && isOpen()) {
            event.preventDefault();
            close(true);
        }
    });
    // focus moving elsewhere (the / shortcut) or a click elsewhere closes the menu. Not focusout: Safari does
    // not focus a clicked button, so focus leaves the menu on mousedown and the click would never reach the item.
    document.addEventListener('focusin', function (event) {
        if (isOpen() && !menu.contains(event.target)) close(false);
    });
    // capture phase: site.js stops clicks in the navigation, on the burger and on the toolbar's nav toggle from bubbling
    document.addEventListener('click', function (event) {
        if (isOpen() && !menu.contains(event.target)) close(false);
    }, true);

    system.addEventListener('change', function () {
        if (preference === 'system') apply('system');
    });
    // other tabs write the key; this tab does not get its own storage events. key null means localStorage.clear().
    window.addEventListener('storage', function (event) {
        if (event.key === KEY || event.key === null) apply(read() || preference);
    });
    // a page restored from the back/forward cache missed the storage events of the meantime
    window.addEventListener('pageshow', function (event) {
        if (event.persisted) apply(read() || preference);
    });

    apply(preference);
})();
