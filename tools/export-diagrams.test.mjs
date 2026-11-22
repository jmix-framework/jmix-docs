import { test } from 'node:test';
import assert from 'node:assert/strict';
import { listPages, exportArgs, parseArgs } from './export-diagrams.mjs';

test('lists page names in file order', () => {
    const xml = '<mxfile><diagram name="scope-1" id="a"><mxGraphModel/></diagram><diagram id="b" name="components-1"><mxGraphModel/></diagram></mxfile>';
    assert.deepEqual(listPages(xml), ['scope-1', 'components-1']);
});

test('rejects a compressed page', () => {
    const xml = '<mxfile><diagram name="p1" id="a">7Z1Zd5s4FIB/jR+dw2770VuWTjLJ1Om0</diagram></mxfile>';
    assert.throws(() => listPages(xml), /compressed.*p1|p1.*compressed/);
});

test('builds export arguments with the fixed options', () => {
    assert.deepEqual(exportArgs('a.drawio', 3, 'out/x.svg'), [
        '-x', '-f', 'svg', '-p', '3', '--theme', 'light', '-t', '-b', '16',
        '--embed-svg-fonts', 'false', '-o', 'out/x.svg', 'a.drawio',
    ]);
});

test('parses the arguments with a repeated --page option', () => {
    assert.deepEqual(parseArgs(['a.drawio', 'img', '--prefix', 'x-', '--page', 'p1', '--page', 'p2']),
        { source: 'a.drawio', imagesDir: 'img', prefix: 'x-', pages: ['p1', 'p2'] });
    assert.deepEqual(parseArgs(['a.drawio', 'img']), { source: 'a.drawio', imagesDir: 'img', prefix: '', pages: [] });
});

test('rejects an option without a value and extra arguments', () => {
    assert.throws(() => parseArgs(['a.drawio', 'img', '--page']), /--page/);
    assert.throws(() => parseArgs(['a.drawio', 'img', 'extra']), /extra/);
    assert.throws(() => parseArgs(['a.drawio']), /Usage/);
});
