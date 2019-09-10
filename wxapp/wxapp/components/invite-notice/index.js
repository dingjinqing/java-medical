var util = require("../../utils/util.js");
global.wxComponent({
  properties: {
    invite_id: {
      type: Number,
      value: 0,
      observer(newVal, oldVal, changedPath) {
        this.onInviteIdChange();
      }
    }
  },
  data: {
    invite: {},
  },
  methods: {
    bindInviteClose: function(e) {
      var that = this
      var cache_invite = 'cache_invite_' + this.data.invite_id;
      util.setCache(cache_invite, 2);
      this.setData({
        "invite.invite_flag": 0
      });
    },
    onInviteIdChange() {
      console.log("invite_id change");
      var invite_id = this.data.invite_id;
      var _this = this;
      if (invite_id > 0) {
        var invite = {};
        var cache_invite = 'cache_invite_' + invite_id;
        if (util.getCache(cache_invite) == '') {
          util.setCache(cache_invite, 1);
        }
        var invite_flag = util.getCache(cache_invite);
        if (invite_id == util.getCache('user_id')) {
          util.setCache(cache_invite, 0);
          invite_flag = 0;
        }
        if (invite_flag == 1) {
          util.api('/api/wxapp/user/share', function(res) {
            if (res.error == 0) {
              invite.invite_id = invite_id;
              invite.username = res.content.username;
              invite.user_avatar = res.content.user_avatar;
            } else {
              invite.invite_flag = 1; //获取失败不显示分享信息
            }
            invite.invite_flag = invite_flag;
            _this.setData({
              invite: invite
            });
          }, {
            invite_id: invite_id
          });
        }
      }
    }
  }
})