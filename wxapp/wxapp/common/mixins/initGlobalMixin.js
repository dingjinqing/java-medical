import { basic as ComponentBasic } from "./component/basic.js"
import { basic as PageBasic } from "./page/basic.js"
import { event as PageEventNotify } from "./page/event.js"
import { initGlobal } from "../../common/base/initGlobal.js"

/**
 * 全局混入
 */
export function initGlobalMixin(pageMixins, componentMixins, appMixins) {
  initGlobal({
    baseAppMixins: appMixins || [],
    basePageMixins: [PageBasic, PageEventNotify, ...(pageMixins || [])],
    baseComponentMixins: [ComponentBasic, ...(componentMixins || [])],
  });
}
