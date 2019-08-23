import { AppSettings, PageSettings, ComponentSettings } from "./settings.js"
import { initOptions, mergeOptions } from "./options.js"

function _mergeMixins(options, baseMixins) {
  if (!Array.isArray(baseMixins) || baseMixins.length == 0) return;
  options.mixins = options.mixins || [];
  options.mixins.unshift(...baseMixins);
}

function _initOptions(options, baseMixins, setting) {
  var initOpts = {};
  initOptions(initOpts, setting);
  _mergeMixins(options, baseMixins);
  mergeOptions(initOpts, options, setting);
  return initOpts;
}

/**
 * 初始化 wxApp wxPage wxComponent
 */
export function initGlobal(globalOptions) {
  globalOptions = globalOptions || {};

  global.wxApp = function (options) {
    App(_initOptions(options, globalOptions.baseAppMixins, AppSettings));
  }

  global.wxPage = function (options) {
    Page(_initOptions(options, globalOptions.basePageMixins, PageSettings));
  }

  global.wxComponent = function (options) {
    Component(_initOptions(options, globalOptions.baseComponentMixins, ComponentSettings));
  }
}