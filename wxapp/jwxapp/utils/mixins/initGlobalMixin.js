var componentBasic = require("./component/basic.js")
var pageBasic = require("./page/basic.js")
var initGlobal = require("../init/initGlobal.js")


/**
 * 全局混入
 */
function initGlobalMixin(pageMixins, componentMixins, appMixins) {
  initGlobal({
    baseAppMixins: appMixins || [],
    basePageMixins: [pageBasic, ...(pageMixins || [])],
    baseComponentMixins: [componentBasic, ...(componentMixins || [])],
  });
}

module.exports = initGlobalMixin;
