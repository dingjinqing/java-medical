var util = require("../../../utils/util.js")
global.wxComponent({
  data: {
    pageData: {},
  },
  properties: {
    page_content: {
      type: Object, // 类型（必填），目前接受的类型包括：String, Number, Boolean, Object, Array, null（表示任意类型）
      value: {}, // 属性初始值（可选），如果未指定则会根据类型选择一个
      observer(newVal, oldVal, changedPath) {
        console.log("observer:newVal", newVal, "oldVal:", oldVal, "changedPath:", changedPath);
        if (typeof newVal == 'object')
          this.processModuleData(newVal);
      }
    }
  },

  methods: {
    processModuleData(page_content) {
      if (!page_content) return;
      var pageData = page_content.page_info || null;
      if (!pageData) return;

      pageData.is_phone_block = 0;
      page_content.imgUrl = util.getImageUrl('');
      pageData.page_id = page_content.page_id;
      
      for (var idx in pageData) {
        module = pageData[idx];
        var componentName = this._convertComponentName(module['module_name']);
        if (!componentName) continue;

        module.page_id = page_content.page_id;
        module.imgUrl = util.getImageUrl('');
        module.imageUrl = util.getImageUrl('');
        module['component_name'] = componentName;
        module['idx'] = idx;
        module['main_setting'] = page_content.main_setting;
      }
      this.setData({
        pageData: pageData,
        page_cfg: page_content.form_cfg,
        con_cfg: page_content
      });
    },
    bindComponentAttached(e) {
      var idx = e.detail.data.m.idx;
      this._form_components = this._form_components || {};
      this._form_components[idx] = e.detail;
    },
    bindToIndex(e) {
      util.jumpLink('/pages/index/index', 'reLaunch');
    },
    bindSubmit(e) {
      var _this = this;
      var form_id = e.detail.formId;
      setTimeout(function() {
        _this.submitForm(form_id)
      }, 1000);
    },
    submitForm(form_id) {
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
      var pagesData = JSON.stringify(this.data.pageData);
      console.log(pagesData)
      util.api('/api/wxapp/form/submit', function(res) {
        if (res.error == 0 && res.content > 0) {
          util.jumpLink('/pages/formsuccess/formsuccess?submit_id=' + res.content);
        } else {
          util.showModal('提示', '表单提交失败');
        }
      }, {
        pagesData: pagesData,
        page_id: this.data.pageData.page_id,
        open_id: util.getCache("openid"),
        form_id: form_id
      });
    },
    _convertComponentName(tmplateName) {
      var modules = {
        "m_scroll_image": "v-carousel",
        "m_dashed_line": "v-line",
        "m_blank": "v-space",
        "m_text": "v-text",
        "m_phone": "v-phone",
        "m_image_small": "v-advertise",
        "m_official_accounts": "v-official-accounts",
        "m_rich_text": "v-rich-text",
        "m_input_name": "v-input-name",
        "m_input_mobile": "v-input-mobile",
        "m_input_email": "v-input-email",
        "m_input_text": "v-input-text",
        "m_sex": 'v-sex',
        "m_address": "v-address",
        "m_choose": "v-choose",
        "m_dates": "v-date",
        "m_imgs": "v-upimg",
        "m_slide": "v-slide",
        "m_upload_video":"v-upvideo"
      };
      return modules[tmplateName];
    }
  },

})