import { test } from 'node:test';
import assert from 'node:assert/strict';
import { checkStylesheets } from './check-css.mjs';

const TOKENS = { name: 'tokens.css', text: ':root { --a: #fff; --b: red; }' };
const messages = (text) => checkStylesheets([TOKENS, { name: 'site.css', text }]).map((f) => f.message);

test('reports hex, functional and named color literals outside tokens.css', () => {
    assert.equal(messages('a { color: #333; }').length, 1);
    assert.equal(messages('a { background: rgb(0 0 0 / 0.5); }').length, 1);
    assert.equal(messages('a { border: 1px solid white; }').length, 1);
    assert.equal(messages('a { --local: #123; }').length, 1);
});

test('accepts tokens, keywords and system colors', () => {
    assert.deepEqual(messages('a { color: var(--a); background: transparent; fill: currentColor; }'), []);
    assert.deepEqual(messages('@media (forced-colors: active) { a { border-color: CanvasText; color: LinkText; } }'), []);
    assert.deepEqual(messages('a { text-decoration-color: color-mix(in oklab, currentColor 35%, transparent); }'), []);
});

test('ignores selectors, comments, strings, urls and property names', () => {
    assert.deepEqual(messages('#search-input:hover { white-space: nowrap; }'), []);
    assert.deepEqual(messages('a { /* color: red; */ content: "white"; mask: url(../img/icons/red.svg); }'), []);
    assert.deepEqual(messages('@media (prefers-reduced-motion: reduce) { html { scroll-behavior: auto; } }'), []);
});

test('allows literals in tokens.css', () => {
    assert.deepEqual(checkStylesheets([{ name: 'tokens.css', text: ':root { --x: #123456; }' }]), []);
});

test('reports custom properties that are used but never declared', () => {
    assert.deepEqual(messages('a { color: var(--missing); }'), ['--missing is used but never declared']);
    assert.deepEqual(messages('a { color: var(--missing, var(--a)); }'), []);
});
