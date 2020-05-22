var common = require("../common.js")
var pages = require("../../init/pages.js")
module.exports = {
  onLoad(e) {
    pages.addObj(this);
  },
  onUnload() {
    this.clearTimers(); 
    pages.removeObj(this);
  },
  ...common
}