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

  requestFormPageData(pageId, cb) {
    var _this = this;
    util.api('/api/wxapp/form/get', function (d) {
      if (d.error === 0) {
        var pageContent = d.content;
        pageContent.pageInfo = JSON.parse(pageContent.pageContent || '{}');
        pageContent.pageCfg = JSON.parse(pageContent.formCfg || '{}');
        pageContent.pageId = pageId;

        pageContent.main_setting = _this._getColors();
        cb(pageContent);
      }
    }, {
      pageId: pageId
    });
  }
};
module.exports = decorate;