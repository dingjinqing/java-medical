var util = require("../../utils/util.js");

var decorate = {
  requestDecoratePageData(page_id, scene, cb, loading) {
    var _this = this;
    var page_id = page_id || 0;
    loading = loading || false;
    util.api('/api/wxapp/index', function (d) {
      console.log(d)
      var pageContent = d.content;
      console.log(pageContent)
      pageContent.main_setting = util.getColors();
      cb(pageContent);
    }, {
      page: page_id,
      scene: scene || "",
      module_asyn: 1
    }, null, loading);
  },

  requestFormPageData(page_id, cb) {
    var _this = this;
    util.api('/api/wxapp/form/get', function (d) {
      if (d.error === 0) {
        var pageContent = d.content;
        pageContent.page_info = JSON.parse(pageContent.page_content || '{}');
        pageContent.page_cfg = pageContent.form_cfg || [];
        pageContent.page_id = page_id;
        pageContent.main_setting = _this._getColors();
        cb(pageContent);
      }
    }, {
      page_id: page_id,
    });
  }
};
module.exports = decorate;