import { test } from 'node:test';
import assert from 'node:assert/strict';
import { restyleStyle, restyleLabel, restyleDrawio } from './restyle-drawio.mjs';

const keys = (s) => Object.fromEntries(s.split(';').filter((p) => p.includes('=')).map((p) => p.split('=')));

test('maps draw.io fills to the new fill and border', () => {
    const k = keys(restyleStyle('rounded=1;whiteSpace=wrap;html=1;fillColor=#ffe6cc;strokeColor=#d79b00;fontSize=16;', 'vertex'));
    assert.equal(k.fillColor, '#f0eeff');
    assert.equal(k.strokeColor, '#a99bff');
    assert.equal(k.strokeWidth, '1.25');
    assert.equal(k.arcSize, '16');
    assert.equal(k.absoluteArcSize, '1');
    assert.equal(k.fontColor, '#17124b');
    assert.equal(k.fontFamily, 'Roboto, Helvetica, Arial');
    assert.equal(k.fontSize, '16');
});

test('gives plain white boxes a gray border', () => {
    assert.equal(keys(restyleStyle('rounded=0;whiteSpace=wrap;html=1;', 'vertex')).strokeColor, '#8a8f99');
});

test('styles edges gray and keeps arrow, dash and curve', () => {
    const k = keys(restyleStyle('endArrow=classic;html=1;curved=1;dashed=1;', 'edge'));
    assert.equal(k.strokeColor, '#5c606b');
    assert.equal(k.strokeWidth, '1.25');
    assert.equal(k.endArrow, 'classic');
    assert.equal(k.dashed, '1');
    assert.equal(k.curved, '1');
});

test('changes only the font of text cells and edge labels', () => {
    for (const s of ['text;html=1;align=center;', 'edgeLabel;html=1;align=center;']) {
        const k = keys(restyleStyle(s, 'vertex'));
        assert.equal(k.fontColor, '#17124b');
        assert.equal(k.strokeColor, undefined);
        assert.equal(k.strokeWidth, undefined);
        assert.equal(k.arcSize, undefined);
    }
});

test('maps the fill of text cells that have one, such as UML class rows', () => {
    const k = keys(restyleStyle('text;strokeColor=#6c8ebf;fillColor=#dae8fc;align=left;verticalAlign=top;', 'vertex'));
    assert.equal(k.fillColor, '#e2f7fa');
    assert.equal(k.strokeColor, '#7fd9e6');
    assert.equal(k.strokeWidth, undefined);
    assert.equal(k.arcSize, undefined);
});

test('keeps swimlanes and named shapes square', () => {
    const lane = keys(restyleStyle('swimlane;fontStyle=0;childLayout=stackLayout;fillColor=#dae8fc;strokeColor=#6c8ebf;', 'vertex'));
    assert.equal(lane.fillColor, '#e2f7fa');
    assert.equal(lane.arcSize, undefined);
    const db = keys(restyleStyle('shape=cylinder3;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;size=15;', 'vertex'));
    assert.equal(db.size, '15');
    assert.equal(db.arcSize, undefined);
});

test('draws actors in the text color', () => {
    assert.equal(keys(restyleStyle('shape=umlActor;verticalLabelPosition=bottom;html=1;fillColor=#f5f5f5;strokeColor=#666666;', 'vertex')).strokeColor, '#17124b');
});

test('leaves group cells without a border', () => {
    const k = keys(restyleStyle('group;', 'vertex'));
    assert.equal(k.strokeColor, undefined);
    assert.equal(k.strokeWidth, undefined);
});

test('draws lifeline actors in the text color', () => {
    const k = keys(restyleStyle('shape=umlLifeline;participant=umlActor;perimeter=lifelinePerimeter;html=1;', 'vertex'));
    assert.equal(k.strokeColor, '#17124b');
});

test('keeps character references in a changed label', () => {
    const xml = '<mxCell id="2" value="A&#10;it&apos;s&lt;br&gt;&lt;font style=&quot;font-size: 13px;&quot;&gt;[b]&lt;/font&gt;" style="text;html=1;" vertex="1" parent="1"/>';
    const value = restyleDrawio(xml).match(/value="([^"]*)"/)[1];
    assert.ok(value.includes('A&#xa;it\'s'), value);
    assert.ok(!value.includes('&amp;'), value);
});

test('colors the bracketed line and makes the title medium', () => {
    assert.equal(
        restyleLabel('Jmix Application<br><font style="font-size: 13px;">[Java, Spring Boot, Vaadin]</font>'),
        '<span style="font-weight: 500;">Jmix Application</span><br><font style="font-size: 13px; color: #5c606b;">[Java, Spring Boot, Vaadin]</font>');
});

test('keeps a color that a label already sets', () => {
    const html = '<font style="font-size: 13px; color: #ff0000;">x</font>';
    assert.equal(restyleLabel(html), html);
});

test('running twice changes nothing the second time', () => {
    const xml = `<mxfile><diagram name="p"><mxGraphModel><root>
<mxCell id="2" value="A&lt;br&gt;&lt;font style=&quot;font-size: 13px;&quot;&gt;[b]&lt;/font&gt;" style="rounded=1;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1"/>
<mxCell id="3" style="endArrow=classic;html=1;" edge="1" parent="1"/>
</root></mxGraphModel></diagram></mxfile>`;
    const once = restyleDrawio(xml);
    assert.notEqual(once, xml);
    assert.equal(restyleDrawio(once), once);
});

test('maps C4 and other draw.io palette fills, ignoring case', () => {
    const cases = {
        '#083F75': ['#f0eeff', '#a99bff'], '#1061B0': ['#f0eeff', '#a99bff'], '#ffcc99': ['#f0eeff', '#a99bff'],
        '#23A2D9': ['#e2f7fa', '#7fd9e6'], '#63BEF2': ['#e2f7fa', '#7fd9e6'], '#cce5ff': ['#e2f7fa', '#7fd9e6'],
        '#cdeb8b': ['#e2f8ee', '#83e2b6'], '#ffcccc': ['#ffe4ee', '#fb8db4'], '#f9f7ed': ['#f6f7f9', '#cfd3da'],
    };
    for (const [fill, [newFill, border]] of Object.entries(cases)) {
        const k = keys(restyleStyle(`rounded=1;html=1;fillColor=${fill};strokeColor=#0D5091;`, 'vertex'));
        assert.deepEqual([k.fillColor, k.strokeColor], [newFill, border], fill);
    }
});

test('gives white text on a mapped fill the text color', () => {
    assert.equal(keys(restyleStyle('rounded=1;html=1;fillColor=#1061B0;fontColor=#FFFFFF;', 'vertex')).fontColor, '#17124b');
});

test('gives the C4 dark gray text the text color', () => {
    assert.equal(keys(restyleStyle('endArrow=blockThin;html=1;fontColor=#404040;', 'edge')).fontColor, '#17124b');
});

test('colors the light gray C4 description text secondary gray', () => {
    assert.equal(
        restyleLabel('<div><font style="font-size: 11px"><font color="#E6E6E6">%c4Description%</font></div><font color="#cccccc">x</font>'),
        '<div><font style="font-size: 11px; color: #5c606b;"><font color="#5c606b">%c4Description%</font></div><font color="#5c606b">x</font>');
});

test('restyles the label of an object element', () => {
    const xml = '<object placeholders="1" c4Name="A" label="&lt;font color=&quot;#cccccc&quot;&gt;%c4Description%&lt;/font&gt;" id="3">\n'
        + '<mxCell style="rounded=1;html=1;fillColor=#1061B0;fontColor=#ffffff;" parent="1" vertex="1"/></object>';
    const result = restyleDrawio(xml);
    assert.ok(result.includes('label="&lt;font color=&quot;#5c606b&quot;&gt;%c4Description%&lt;/font&gt;"'), result);
    assert.ok(result.includes('fillColor=#f0eeff'), result);
    assert.equal(restyleDrawio(result), result);
});

test('keeps the color of a bold title in a font element', () => {
    const html = '<font style="font-size: 16px"><b>%c4Name%</b></font><div>[%c4Type%]</div>';
    assert.equal(restyleLabel(html), html);
});

test('turns off the page shadow', () => {
    const xml = '<diagram name="p"><mxGraphModel dx="1" shadow="1" math="0"><root></root></mxGraphModel></diagram>';
    assert.equal(restyleDrawio(xml), '<diagram name="p"><mxGraphModel dx="1" shadow="0" math="0"><root></root></mxGraphModel></diagram>');
});
