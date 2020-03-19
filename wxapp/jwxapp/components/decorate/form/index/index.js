// components/decorate/form/index/index.js
var util = require("../../../../utils/util.js")

global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    page_content: {
      type: Object,
      value: {},
      observer(newVal, oldVal, changedPath) {
        console.log(newVal)
        if (typeof newVal === 'object') {
          this.processModuleData(newVal);
        }
      }
    },
    getinfo:{
      type:Boolean,
      value:true
    },
    getmobile:{
      type:Boolean,
      value:true
    },
    authorized_mobile:{
      type:Number,
      value:0
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    pageData: {}
  },

  /**
   * 组件的方法列表
   */
  methods: {
    processModuleData(pageContent) {
      if (!pageContent) return;
      var pageData = pageContent.pageInfo || null;
      if (!pageData) return;

      pageData.is_phone_block = 0;
      pageContent.imgUrl = util.getImageUrl('');
      pageData.pageId = pageContent.pageId;
      
      for (var idx in pageData) {
        module = pageData[idx];
        var componentName = this._convertComponentName(module['module_name']);
        if (!componentName) continue;
        module.pageId = pageContent.pageId;
        module.imgUrl = util.getImageUrl('');
        module.imageUrl = util.getImageUrl('');
        module['component_name'] = componentName;
        module['idx'] = idx;
        module['main_setting'] = pageContent.main_setting;
      }
      // 授权用户信息
      if(pageContent.pageCfg.authorized_name && pageContent.pageCfg.authorized_name == 1){
        var user_name = util.getCache('nickName');
        var user_avatar = util.getCache('avatarUrl');
        if (!user_name || user_name == '用户' + parseInt(util.getCache('user_id') + 10000)
          || user_name == util.getCache('openid') || !user_avatar
          || user_avatar.indexOf('image/admin/head_icon.png') > -1) {
          this.setData({
            getinfo: false,
          })
        } else {
          this.setData({
            getinfo: true,
          })
        }
      }
      // 授权手机号
      if(pageContent.pageCfg.authorized_mobile){
        this.setData({
          authorized_mobile: pageContent.pageCfg.authorized_mobile
        })
      }
      console.log(pageData, pageContent.pageCfg)
      this.setData({
        pageData: pageData,
        pageCfg: pageContent.pageCfg,
        con_cfg: pageContent
      });
    },
    _convertComponentName(tmplateName) {
      var modules = {
        "m_input_name": "v-input-name",
        "m_input_mobile": "v-input-mobile",
        "m_address": "v-address",
        "m_input_email": "v-input-email",
        "m_sex": 'v-sex',
        "m_slide": "v-slide",
        "m_input_text": "v-input-text",
        "m_choose": "v-choose",
        "m_dates": "v-date",
        "m_imgs": "v-upimg",
        "m_upload_video":"v-upvideo",
        "m_scroll_image": "v-carousel",
        "m_rich_text": "v-rich-text",
        "m_image_small": "v-advertise",
        "m_text": "v-text",
        "m_dashed_line": "v-line",
        "m_blank": "v-space",
        "m_phone": "v-phone",
        "m_official_accounts": "v-official-accounts"
      };
      return modules[tmplateName];
    },
    bindComponentAttached(e) {
      var idx = e.detail.data.m.idx;
      this._form_components = this._form_components || {};
      this._form_components[idx] = e.detail;
    },
    // 提交表单
    bindSubmit(e) {
      var that = this;
      setTimeout(function() {
        that.submitForm()
      }, 1000);
    },
    // 新用户提交表单时
    getUserInfo: function (e) {
      var that = this;
      util.getUserInfoCommon(e, function(userInfo) {
        if (userInfo) {
          that.setData({
            getinfo:true
          })
          util.toast_success('更新成功');
        } else {
          util.toast_fail('更新失败');
        }
      });
    },
    // 调用提交订单接口
    submitForm() {
      if(this.data.getinfo == true && util.getCache("mobile") == "" && this.data.authorized_mobile == 1){
        this.setData({
          getmobile: false,
        })
        return false
      }
      for (var idx in this.data.pageData) {
        var m = this.data.pageData[idx];
        var c = this._form_components[idx];
        if (m.module_name && c) {
          c.check();
          if (c.data.m.error) {
            util.showModal('提示', c.data.m.error);
            return false;
          }
          this.data.pageData[idx] = c.data.m;
        }
      }
      if (this.submit) return false;
      this.submit = true;
      var _this = this;
      let pageDatas = []
      // debugger
      // this.data.pageData.forEach(item => {
      //   console.log(item)
      //   pageDatas.push({
      //     moduleName: item.module_name,
      //     moduleValue: item.module_value,
      //     moduleType: item.form_title,
      //     curIdx: item.idx
      //   })
      // })
      for (const key in this.data.pageData) {
        const item = this.data.pageData[key];
        if (typeof item === 'object' && item.module_value) {
          pageDatas.push({
            moduleName: item.module_name,
            moduleValue: JSON.stringify(item.module_value),
            moduleType: item.form_title,
            curIdx: item.idx
          })
        }
      }
      util.api('/api/wxapp/form/submit', function(res) {
        if (res.error == 0) {
          util.jumpLink('/pages1/formsuccess/formsuccess?submit_id=' + res.content);
        } else {
          util.showModal('提示', '表单提交失败');
        }
      }, {
        detailList: pageDatas,
        pageId: this.data.pageData.pageId,
        userId: util.getCache('user_id')
      });
    }
  }
})
