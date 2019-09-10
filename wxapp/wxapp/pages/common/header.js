let util = require("../../utils/util.js")
var header = {
  onClickBack(e) {
    util.jumpLink("/pages/index/index","redirectTo");
  }
}
module.exports = header;