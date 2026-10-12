'use strict'

// Adds a content hash to the URLs of the UI stylesheets and scripts, so a browser that cached an
// old copy (the IntelliJ IDEA built-in browser keeps them for a long time) loads the new one.
//
// The extension hashes every CSS and JS file of the UI, including the files that other extensions
// add, and registers the `asset` Handlebars helper. The templates reference these files through it:
//
//   <link rel="stylesheet" href="{{{asset 'css/site.css'}}}">
//
// renders as `<uiRootPath>/css/site.css?v=<hash>`. Register this extension after the extensions
// that add UI files, because it sees only the files that exist when it runs.

const { createHash } = require('crypto')

const HASHED_EXTENSIONS = ['.css', '.js']

module.exports.register = function () {
  this.on('uiLoaded', async ({ uiCatalog }) => {
    const versions = {}
    for (const file of uiCatalog.findByType('asset')) {
      if (!HASHED_EXTENSIONS.some((ext) => file.path.endsWith(ext))) continue
      // other extensions may add a file as a stream, which can be read only once
      if (!Buffer.isBuffer(file.contents)) file.contents = await readStream(file.contents)
      versions[file.path] = createHash('sha256').update(file.contents).digest('hex').slice(0, 10)
    }
    uiCatalog.addFile({
      contents: Buffer.from(helperSource(versions)),
      path: 'helpers/asset.js',
      stem: 'asset',
      type: 'helper',
    })
  })
}

async function readStream (stream) {
  const chunks = []
  for await (const chunk of stream) chunks.push(Buffer.isBuffer(chunk) ? chunk : Buffer.from(chunk))
  return Buffer.concat(chunks)
}

function helperSource (versions) {
  return `'use strict'

const versions = ${JSON.stringify(versions, null, 2)}

module.exports = (path, { data: { root } }) => {
  const version = versions[path]
  if (!version) throw new Error('asset helper: the UI has no CSS or JS file ' + path)
  return root.uiRootPath + '/' + path + '?v=' + version
}
`
}
