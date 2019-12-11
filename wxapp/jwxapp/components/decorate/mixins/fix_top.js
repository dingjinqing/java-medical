var util = require("../../../utils/util.js");

var fix_top = {
  properties: {
    fix_global: Object
  },
  lifetimes: {
    attached() {
      this._top = {
        comp: this,
        fixed: false,
        height: 0
      };
      var page = util.getCurrentPage();
      this._page = page;
      page._fix_comps = page._fix_comps || [];
      page._fix_comps.push(this._top);
    },
    detached() {
      var comps = this._page._fix_comps;
      for (var i = 0; i < comps.length; i++) {
        if (comps[i].comp === this) {
          console.log("detached splice", i, this)
          comps.splice(i, 1);
          break;
        }
      }
    }
  },

  methods: {
    startFixed(height) {
      this._top.fixed = true;
      this._top.height = height;
    },
    stopFixed() {
      this._top.fixed = false;
      this._top.height = 0;
    },
    getFixeTop() {
      console.log(this._page._fix_comps)
      var fixTop = 0;
      var comps = this._page._fix_comps;
      for (var i = 0; i < comps.length; i++) {
        if (comps[i].comp === this) {
          break;
        }
        fixTop += comps[i].height;
      }
      return fixTop;
    }
  }
};

module.exports = fix_top;