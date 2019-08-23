import { common } from "../common"
import { pages } from "../../base/pages.js"

export var basic = {
  options: {
    multipleSlots: true,
    addGlobalClass: false
  },
  externalClasses: ['custom-class'],
  attached(e) {
    pages.addObj(this);
  },
  detached() {
    pages.removeObj(this);
  },
  method: {
    $emit() {
      this.triggerEvent.apply(this, arguments);
    },
    notify(path, eventName, ...args) {
      pages.notify(this, path, eventName, args);
    },
    notifyAll(eventName, ...args) {
      pages.notify(this, "*", eventName, args);
    },
    ...common
  },
  
}