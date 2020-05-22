var util = require("../../../utils/util.js");
var base = require("../base/base.js")
global.wxComponent({
  mixins: [base],
  properties: {
    custom_info:Object
  },
  methods: {
    jumpLink(e){
      let link = e.currentTarget.dataset.link;
      if(link != ''){ 
        util.jumpLink(link, "navigateTo");
      }
    }
  }
});