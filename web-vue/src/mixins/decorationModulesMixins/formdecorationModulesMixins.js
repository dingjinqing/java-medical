export default {
  methods: {
    // 页面装修模块数据填充处理
    handleToAddModules (index) {
      let obj = {}
      switch (index) {
        case 0: // 姓名模块
          obj = {
            'module_name': 'm_input_name',
            'form_title': '姓名', // 标题文字input值
            'image_type': 0, // 展现形式radio
            'name_url': '', // 图标图片地址
            'confirm': 0, // 条件验证
            'ok_ajax': 0 // 是否点击确认
          }
          break
        case 1: // 姓名模块
          obj = {
            'module_name': 'm_input_mobile',
            'form_title': '手机号', // 标题文字input值
            'image_type': 0, // 展现形式radio
            'name_url': '', // 图标图片地址
            'confirm': 0, // 条件验证
            'ok_ajax': 0// 是否点击确认
          }
          break
        case 2: // 省市区模块
          obj = {
            'module_name': 'm_address',
            'form_title': '省/市/区',
            'with_detail': 0,
            'confirm': 0,
            'ok_ajax': 0
          }
          break
        case 3: // 邮箱模块
          obj = {
            'module_name': 'm_input_email',
            'form_title': '邮箱', // 标题文字input值
            'image_type': 0, // 展现形式radio
            'name_url': '', // 图标图片地址
            'confirm': 0, // 条件验证
            'ok_ajax': 0// 是否点击确认
          }
          break
        case 4: // 性别模块
          obj = {
            'module_name': 'm_sex',
            'form_title': '性别',
            'show_types': 0,
            'confirm': 0,
            'ok_ajax': 0
          }
          break
        case 5: // 下拉模块
          obj = {
            'module_name': 'm_slide',
            'form_title': '下拉',
            'selects': {
              '1': '选项1',
              '2': '选项2'
            },
            'confirm': 0,
            'ok_ajax': 0
          }
          break
        case 6: // 输入框模块
          obj = {
            'module_name': 'm_input_text',
            'show_types': '0', // 展现形式radio
            'form_title': '输入框',
            'placeholder': '', // 提示语input值
            'confirm': 0,
            'least_number': 1,
            'most_number': 500,
            'ok_ajax': 0
          }
          break
        case 7: // 选项模块
          obj = {
            'module_name': 'm_choose',
            'form_title': '选项',
            'show_types': 0,
            'selects': {
              '1': '选项1',
              '2': '选项2'
            },
            'confirm': 0,
            'ok_ajax': 0
          }
          break
        case 8: // 日期模块
          obj = {
            'module_name': 'm_dates',
            'form_title': '日期',
            'date_types': 0, // 时间格式radio
            'confirm': 0,
            'ok_ajax': 0
          }
          break
        case 9: // 图片上传模块
          obj = {
            'module_name': 'm_imgs',
            'form_title': '图片上传',
            'max_number': '6',
            'size_types': 0,
            'width_size': '',
            'height_size': '',
            'confirm': 0,
            'ok_ajax': 1
          }
          break
        case 10: // 视频上传模块
          obj = {
            'module_name': 'm_upload_video',
            'form_title': '视频上传',
            'confirm': 0,
            'ok_ajax': 1
          }
          break
        case 11: // 轮播图模块
          obj = {
            'module_name': 'm_scroll_image',
            'img_items': [

            ],
            'is_preview': '0'
          }
          break
        case 12: // 富文本模块
          obj = {
            'module_name': 'm_rich_text',
            'rich_text': ''
          }
          break
        case 13: // 图片广告模块
          obj = {
            'module_name': 'm_image_small',
            'title': '',
            'title_link': '',
            'img_url': '',
            'is_preview': '0'
          }
          break
        case 14: // 文本模块
          obj = {
            'module_name': 'm_text',
            'title': '',
            'fonts_size': '2',
            'fonts_color': '#333333',
            'bgs_color': '#ffffff',
            'show_pos': '1',
            'title_link': ''
          }
          break
        case 15: // 辅助线模块
          obj = {
            'module_name': 'm_dashed_line'
          }
          break
        case 16: // 空白模块
          obj = {
            'module_name': 'm_blank',
            'blank_height': 10
          }
          break
        case 17: // 电话模块
          obj = {
            'module_name': 'm_phone',
            'title': 'zzzzz',
            'show_type': '0',
            'sps_icon': '',
            'align_type': '0',
            'color': '#000000',
            'background_color': '#ffffff'
          }
          break
        case 18: // 公众号模块
          obj = {
            'module_name': 'm_official_accounts'
          }
          break
      }
      console.log(obj)
      return obj
    },
    // 对模块某些数据进行非空校验
    handleToJudgeModulesData (data) {
      console.log(this.pageSetData, data)
      let flag = true
      // 判断页面设置页面名称是否为空
      if (!this.pageSetData.page_name) {
        this.$message.error({
          message: '表单标题不能为空',
          showClose: true
        })
        flag = false
      }
      // 判断装修模块数量是否为空
      if (!data.length) {
        this.$message.error({
          message: '请添加表单模块',
          showClose: true
        })
        flag = false
      }
      // 判断是否保存表单
      let isSaveModule = false
      let isHaveOkajax = false
      data.forEach((item, index) => {
        if (item.ok_ajax) {
          isSaveModule = true
          isHaveOkajax = true
        } else if (item.ok_ajax === 0) {
          isHaveOkajax = true
        }
      })
      if (isSaveModule) {
        data.forEach((item, index) => {
          if (item.ok_ajax === 0) {
            item.ok_ajax = 1
          }
        })
      } else {
        if (isHaveOkajax) {
          this.$message.error({
            message: '请保存表单',
            showClose: true
          })
          flag = false
        }
      }
      return flag
    },
    // 处理保存数据
    handleToSaveModulesData (data, pageSetData, lastIdx) {
      console.log(data)
      let lastPageSetData = JSON.parse(JSON.stringify(pageSetData))
      let idx = lastIdx + 1
      let params = {}
      let obj = {}
      data.forEach(item => {
        // 处理checkbox值
        Object.keys(item).forEach(itemC => {
          console.log(typeof true, item[itemC])
          if (typeof item[itemC] === 'boolean') {
            console.log(this.handleToTurnBoolean(item[itemC]))
            item[itemC] = this.handleToTurnBoolean(item[itemC])
          }
        })
        // 处理cur_idx
        if (!item.cur_idx) {
          obj[`c_${idx}`] = item
          item.cur_idx = idx
          idx = idx + 1
        } else {
          obj[`c_${item.cur_idx}`] = item
        }
      })
      Object.keys(lastPageSetData).forEach(item => {
        console.log(typeof true, lastPageSetData[item])
        if (typeof lastPageSetData[item] === 'boolean') {
          console.log(this.handleToTurnBoolean(lastPageSetData[item]))
          lastPageSetData[item] = this.handleToTurnBoolean(lastPageSetData[item])
        }
      })
      params['pageId'] = null
      params['shopId'] = Number(localStorage.getItem('V-ShopId'))
      params['pageName'] = lastPageSetData.page_name
      params['status'] = null
      params['pageContent'] = JSON.stringify(obj)
      params['formCfg'] = JSON.stringify(lastPageSetData)
      params['startTime'] = lastPageSetData.start_time
      params['endTime'] = lastPageSetData.end_time
      params['validityPeriod'] = lastPageSetData.is_forever_valid
      return params
    },
    // 数字与Boolean互转
    handleToTurnBoolean (flag) {
      let res = ''
      switch (flag) {
        case 0:
          res = false
          break
        case 1:
          res = true
          break
        case false:
          res = 0
          break
        case true:
          res = 1
          break
      }
      return res
    }
  }
}
