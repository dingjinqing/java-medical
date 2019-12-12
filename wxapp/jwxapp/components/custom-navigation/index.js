var util = require("../../utils/util.js");
var fix_top = require("../common/fix_top.js");
global.wxComponent({
  mixins: [fix_top],
  /**
   * 组件的属性列表
   */
  properties: {
    has_bottom: Boolean,
    page_name: String,
    bgColor: String,
    is_first_page: {
      type: Number,
      value: 0
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    height: 0,
    show_back: false,
    show_bottom: true,
    page_title: ""
  },
  ready() {
    let that = this;
    var height = 0;
    if (typeof wx.getMenuButtonBoundingClientRect === 'function') {
      height = wx.getMenuButtonBoundingClientRect().bottom
      this.startFixed(height + 8);
    } else {
      wx.getSystemInfo({
        success: (res) => {
          height = res.statusBarHeight * 3
          this.startFixed(height + 8);
        }
      })
    }
    let pages = getCurrentPages();
    let show_back;
    if (pages.length > 1) {
      show_back = true
    } else {
      show_back = false
    }
    this.setData({
      show_back: show_back,
      show_bottom: this.data.has_bottom,
      height: height
    })
    let pageUrl = util.getCurrentPath();
    getTitle(pageUrl, that)
  },
  /**
   * 组件的方法列表
   */
  methods: {
    // 返回上一页面
    _navback() {
      wx.navigateBack()
    },
    //返回到首页
    _backhome() {
      util.jumpLink('/pages/index/index', 'reLaunch')
    }
  }
})
function getTitle(pageUrl, that) {
  let lastindex = pageUrl.lastIndexOf('/');
  var lastSegment = pageUrl.substring(lastindex + 1);
  var title = that.$t("components.navigation.title." + lastSegment)
  that.setData({
    page_title: title
  })
}