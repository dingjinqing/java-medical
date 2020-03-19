var base = require("../../mixins/base.js");
var util = require("../../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  address_value: {},
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      var m = this.data.m = newVal;
      m.img_arr = [];
      m.image = false;
      m.img_len = 0;
    },
    bindUpImage: function(e) {
      var _this = this;
      var m = this.data.m;
      util.uploadImage(1, function(con) {
        var data = JSON.parse(con.data);
        if (m.size_types == 1) {
          var real_width = data.content.imgWidth;
          var real_height = data.content.imgHeight;
          if (real_width > m.width_size || real_height > m.height_size) {
            util.showModal('提示', '上传图片宽高不符合要求');
            return false;
          }
        }
        m.img_arr.push(data.content.imgUrl);
        m.img_len = parseInt(m.img_arr.length);
        m.image = true;
        m.module_value = m.img_arr;
        _this.$set();
      });
    },
    bindDelImage: function(e) {
      var d = this.eventData(e);
      var m = this.data.m;
      m.img_arr.splice(d.img_index, 1);
      m.img_len = parseInt(m.img_arr.length);
      m.module_value = m.img_arr;
      this.$set();
    },
    check() {
      var m = this.data.m;
      m.error = null;
      if (m.confirm == 1 && (!m.module_value || m.module_value.length == 0)) {
        m.error = '请上传图片';
      }
    }
  }
});