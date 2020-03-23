var base = require("../../mixins/base.js");
var util = require("../../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  address_value: {},
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      var m = this.data.m = newVal;
      m.video = false;
    },
    bindUpVideo: function (e) {
      var _this = this;
      var m = this.data.m;
      util.uploadVideo(function (res) {
          // var data = JSON.parse(res.data);
          // console.log(data);
          if(res.error == 0){
            m.video = true;
            console.log(res);
            m.video_data = res.content.videoUrl;
            m.video_img = res.content.videoSnapPath;
            m.module_value = { 
              "video_src": res.content.videoUrl,
              "video_img_src": res.content.videoSnapPath,
              "video_id": res.content.videoId
            };
            _this.$set();
          }
      })
    },
    bindDelVideo: function (e) {
      var d = this.eventData(e);
      var m = this.data.m;
      m.video = false;
      m.video_data = '';
      m.video_img = '';
      m.module_value = '';
      this.$set();
    },
    check() {
      var m = this.data.m;
      m.error = null;
      if (m.confirm == 1 && !m.video) {
        m.error = '请上传视频';
      }
    }
  }
});