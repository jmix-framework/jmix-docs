// Keeps code clear of the source toolbox. site.js puts the toolbox (language label and copy button) in the top
// right corner of every code block, and it is always visible, so a first line that reaches under it would be
// covered. This marks those blocks with first-line-under-toolbox, and the stylesheet starts their code below the
// toolbox. Blocks with a shorter first line are not marked. The marks follow the font loading and the width.
(function () {
    const MARK = 'first-line-under-toolbox';
    // space kept between the end of the first line and the toolbox, in rem
    const CLEARANCE = 0.5;

    // Right end of the first rendered line, as it is at horizontal scroll 0, or null when the block has no layout.
    // A range has one rect per text fragment and per element, so the first rect is only the start of the line:
    // the end is the largest right edge among the rects on the same visual line. A first line that wraps (code in
    // a table cell) counts up to the end of its first visual line.
    const firstLineEnd = function (code) {
        const walker = document.createTreeWalker(code, NodeFilter.SHOW_TEXT);
        let node = walker.nextNode();
        if (!node) return null;
        const range = document.createRange();
        range.setStart(node, 0);
        let last = node;
        while (node && node.data.indexOf('\n') < 0) {
            last = node;
            node = walker.nextNode();
        }
        if (node) range.setEnd(node, node.data.indexOf('\n'));
        else range.setEnd(last, last.data.length);
        const rects = Array.from(range.getClientRects()).filter(function (rect) {
            return rect.width > 0;
        });
        if (!rects.length) return null;
        const line = rects.filter(function (rect) {
            return rect.top < rects[0].top + rects[0].height / 2;
        });
        return Math.max.apply(null, line.map(function (rect) {
            return rect.right;
        })) + code.scrollLeft;
    };

    const update = function () {
        const clearance = CLEARANCE * parseFloat(getComputedStyle(document.documentElement).fontSize);
        const blocks = [];
        document.querySelectorAll('.doc .listingblock > .content').forEach(function (content) {
            const toolbox = content.querySelector(':scope > .source-toolbox');
            const pre = content.querySelector(':scope > pre.highlight');
            const code = pre && pre.querySelector(':scope > code');
            if (toolbox && code) blocks.push({ pre: pre, code: code, toolbox: toolbox });
        });
        // all reads first, then all writes, so the layout is not forced once per block; only the horizontal
        // direction is measured, so the top padding that a mark adds cannot change the next result
        const marks = blocks.map(function (block) {
            const end = firstLineEnd(block.code);
            if (end === null) return block.pre.classList.contains(MARK);
            return end >= block.toolbox.getBoundingClientRect().left - clearance;
        });
        blocks.forEach(function (block, i) {
            block.pre.classList.toggle(MARK, marks[i]);
        });
    };

    // the font decides how wide a line is, so the first measurement waits for JetBrains Mono
    document.fonts.ready.then(update);

    let scheduled = false;
    window.addEventListener('resize', function () {
        if (scheduled) return;
        scheduled = true;
        requestAnimationFrame(function () {
            scheduled = false;
            update();
        });
    });
})()
