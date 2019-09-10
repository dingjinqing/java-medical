var setting = require("./settings.js")
var merge = require("./merge.js")

function _initOptions(options, baseMixins, setting) {
  if (!Array.isArray(baseMixins) || baseMixins.length == 0) return options;
  options.mixins = options.mixins || [];
  options.mixins.unshift(...baseMixins);
  options = merge(options, setting);
  return options;
}

/**
 * 初始化 wxApp wxPage wxComponent
 */
function initGlobal(globalOptions) {

  globalOptions = globalOptions || {};

  global.wxApp = function (options) {
    App(_initOptions(options, globalOptions.baseAppMixins, setting.AppSettings));
  }

  global.wxPage = function (options) {
    Page(_initOptions(options, globalOptions.basePageMixins, setting.PageSettings));
  }

  global.wxComponent = function (options) {
    Component(_initOptions(options, globalOptions.baseComponentMixins, setting.ComponentSettings));
  }
}

module.exports = initGlobal;