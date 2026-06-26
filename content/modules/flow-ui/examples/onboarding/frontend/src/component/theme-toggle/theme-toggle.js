// tag::web-component[]
import {html} from 'lit';
import {Button} from '@vaadin/button';
import {defineCustomElement} from '@vaadin/component-base/src/define.js';

export class ThemeToggle extends Button { // <1>

    static get is() {
        return 'theme-toggle'; // <2>
    }

    static get template() { // <3>
        return html`
            <div class="vaadin-button-container">
                <span part="prefix" aria-hidden="true">
                    <slot name="prefix"></slot>
                </span>
                <span part="label">
                    <slot></slot>
                </span>
            </div>

            <slot name="tooltip"></slot>
        `;
    }

    static get properties() { // <4>
        return {
            ariaLabel: {
                type: String,
                value: 'Theme toggle',
                reflectToAttribute: true,
            },
            storageKey: {
                type: String,
                value: 'jmix.flowui.theme',
                observer: '_onStorageKeyChanged'
            }
        };
    }

    constructor() {
        super();

        this.addEventListener('click', () => this.toggleTheme());
        this.addEventListener('click', () => {
            const customEvent = new CustomEvent('theme-changed', {detail: {value: this.getCurrentTheme()}});
            this.dispatchEvent(customEvent); // <5>
        });
    }

    /** @protected */
    ready() {
        super.ready();
        this.applyStorageTheme();
    }

    applyStorageTheme() {
        let storageTheme = this.getStorageTheme();
        let currentTheme = this.getCurrentTheme();
        if (storageTheme && currentTheme !== storageTheme) {
            this.applyTheme(storageTheme);
        }
    }

    getStorageTheme() {
        return localStorage.getItem(this.storageKey);
    }

    getCurrentTheme() {
        return document.documentElement.getAttribute('theme');
    }

    toggleTheme() {
        const theme = this.getCurrentTheme();
        this.applyTheme(theme === 'dark' ? '' : 'dark');
    }

    applyTheme(theme) {
        document.documentElement.setAttribute('theme', theme);
        localStorage.setItem(this.storageKey, theme);
    }

    /** @protected */
    _onStorageKeyChanged(storageKey, oldStorageKey) {
        const theme = localStorage.getItem(oldStorageKey);
        localStorage.removeItem(oldStorageKey);

        if (theme) {
            localStorage.setItem(storageKey, theme);
        }
    }
}

defineCustomElement(ThemeToggle); // <6>
// end::web-component[]
