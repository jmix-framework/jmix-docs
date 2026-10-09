// Accessibility for markup that site.js and the Lunr search UI control: aria-expanded on the toggles
// whose state site.js keeps in classes, a name for the copy buttons, focus for the skip link, and
// keyboard access to the search results.
(function () {
    // site.js toggles .is-active on nav items, the explore panel and the toolbar's nav toggle (it sets the burger's aria-expanded itself)
    const syncNavToggle = function (item) {
        const toggle = item.querySelector(':scope > .nav-item-toggle');
        if (toggle) toggle.setAttribute('aria-expanded', String(item.classList.contains('is-active')));
    };
    const explorePanel = document.querySelector('.nav-panel-explore');
    const exploreToggle = explorePanel && explorePanel.querySelector('.context');
    const navToggle = document.querySelector('.toolbar .nav-toggle');
    const syncPanels = function () {
        if (exploreToggle) exploreToggle.setAttribute('aria-expanded', String(explorePanel.classList.contains('is-active')));
        if (navToggle) navToggle.setAttribute('aria-expanded', String(navToggle.classList.contains('is-active')));
    };
    document.querySelectorAll('.nav-item').forEach(syncNavToggle);
    syncPanels();
    const observer = new MutationObserver(function (records) {
        records.forEach(function (record) {
            if (record.target.classList.contains('nav-item')) syncNavToggle(record.target);
        });
        syncPanels();
    });
    const nav = document.querySelector('.nav');
    if (nav) observer.observe(nav, { subtree: true, attributes: true, attributeFilter: ['class'] });
    if (navToggle) observer.observe(navToggle, { attributes: true, attributeFilter: ['class'] });

    // site.js names the copy buttons through a hidden icon's alt text and an invisible toast
    document.querySelectorAll('.doc .source-toolbox .copy-button').forEach(function (button) {
        button.setAttribute('aria-label', 'Copy to clipboard');
        const toast = button.querySelector('.copy-toast');
        if (toast) toast.setAttribute('aria-hidden', 'true');
    });

    // site.js intercepts in-page links to scroll them, so the skip link moves focus itself
    const skipLink = document.querySelector('.skip-link');
    const main = document.getElementById('main-content');
    if (skipLink && main) {
        skipLink.addEventListener('click', function () {
            main.focus({ preventScroll: true });
        });
    }

    // search-ui.js runs the search 100 ms after every keydown in the input and re-renders the results;
    // Escape there clears the query and the results
    const searchInput = document.getElementById('search-input');
    const searchArea = searchInput && searchInput.closest('.search-field');
    if (searchArea) {
        const clearSearch = function () {
            searchInput.dispatchEvent(new KeyboardEvent('keydown', { key: 'Escape' }));
        };
        // keep Tab away from search-ui.js, or the result that just received focus is replaced
        document.addEventListener('keydown', function (event) {
            if (event.target === searchInput && event.key === 'Tab') event.stopPropagation();
        }, true);
        searchArea.addEventListener('keydown', function (event) {
            if (event.key === 'Escape' && event.target !== searchInput) {
                searchInput.focus();
                clearSearch();
            }
        });
        searchArea.addEventListener('focusout', function (event) {
            if (!event.relatedTarget || !searchArea.contains(event.relatedTarget)) clearSearch();
        });
    }
})()
