import { pages } from "../../base/pages.js"
import { common } from "../common.js"

export var basic = {
  onLoad(e) {
    pages.addObj(this);
  },
  onUnload() {
    pages.removeObj(this);
  },

  notify(path, eventName, ...args) {
    pages.notify(this, path, eventName, args);
  },

  notifyAll(eventName, ...args) {
    pages.notify(this, "*", eventName, args);
  },
  ...common
}