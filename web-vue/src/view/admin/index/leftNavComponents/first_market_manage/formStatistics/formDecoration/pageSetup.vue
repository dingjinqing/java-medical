<template>
  <div class="decRightContainer">
    <div class="decRightTop">
      <div>表单信息</div>
      <div
        @click="handleToChangeIcon"
        class="topRight"
      >
        <span>{{topIconFlag?retract:open}}</span>
        <img :src="topIconFlag?topIconUp:topIconDown">
      </div>
    </div>
    <!--页面设置模块-->
    <div
      v-if="topIconFlag"
      class="pageSetContent"
    >
      <pageSetupMain
        :pageSet='pageSet'
        @hanelToPageSet='hanelToPageSet'
      />
    </div>
    <!--模块配置-->
    <div v-if="!topIconFlag">
      <components
        v-if="insertFlga"
        :is="showModule"
        :modulesData='modulesData'
        :sortIndex='sortIndex'
        @handleToBackData='handleToBackData'
      ></components>
    </div>
  </div>
</template>
<script>
export default {
  components: {
    pageSetupMain: () => import('./pageSetupModules/pageSetupMain'), // 表单信息配置
    // 表单信息模块
    RiName: () => import('./pageSetupModules/formModule/riName'), // 姓名模块
    RiCellPhoneNumber: () => import('./pageSetupModules/formModule/riCellPhoneNumber'), // 手机号模块
    RiProvinceAndCity: () => import('./pageSetupModules/formModule/riProvinceAndCity'), // 省市区模块
    RiEmail: () => import('./pageSetupModules/formModule/riEmail'), // 邮箱模块
    RiGender: () => import('./pageSetupModules/formModule/riGender'), // 性别模块
    RiDropDown: () => import('./pageSetupModules/formModule/riDropDown'), // 下拉模块
    RiInputBox: () => import('./pageSetupModules/formModule/riInputBox'), // 输入框模块
    RiOption: () => import('./pageSetupModules/formModule/riOption'), // 选项模块
    RiDateModule: () => import('./pageSetupModules/formModule/riDateModule'), // 日期模块
    RiPictureUpload: () => import('./pageSetupModules/formModule/riPictureUpload'), // 图片上传模块
    RiVideoUpload: () => import('./pageSetupModules/formModule/riVideoUpload'), // 视频上传模块
    // 图文类
    RiRotationChart: () => import('./pageSetupModules/graphicModule/riRotationChart'), // 轮播图模块
    RiRichText: () => import('./pageSetupModules/graphicModule/riRichText'), // 富文本模块
    RiPictureAds: () => import('./pageSetupModules/graphicModule/riPictureAds'), // 图片广告模块
    RiTextModule: () => import('./pageSetupModules/graphicModule/riTextModule'), // 文本模块
    RiGuide: () => import('./pageSetupModules/graphicModule/riGuide'), // 辅助线模块
    RiAuxiliaryBlank: () => import('./pageSetupModules/graphicModule/riAuxiliaryBlank'), // 辅助空白模块
    RiTelephone: () => import('./pageSetupModules/graphicModule/riTelephone'), // 电话模块
    RiOfficialAccount: () => import('./pageSetupModules/graphicModule/riOfficialAccount') // 公众号模块
  },
  props: {
    nowRightShowMoudlesIndex: Number,
    nowRightModulesData: Object,
    pageSetData: Object
  },
  data () {
    return {
      topIconUp: this.$imageHost + '/image/admin/shop_deco/icon_up.png',
      topIconDown: this.$imageHost + '/image/admin/shop_deco/icon_down.png',
      topIconFlag: true,
      modulesShow: false,
      thirdRightModulesList: [
        {
          id: 0,
          name: 'RiName'
        },
        {
          id: 1,
          name: 'RiCellPhoneNumber'
        },
        {
          id: 2,
          name: 'RiProvinceAndCity'
        },
        {
          id: 3,
          name: 'RiEmail'
        },
        {
          id: 4,
          name: 'RiGender'
        },
        {
          id: 5,
          name: 'RiDropDown'
        },
        {
          id: 6,
          name: 'RiInputBox'
        },
        {
          id: 7,
          name: 'RiOption'
        },
        {
          id: 8,
          name: 'RiDateModule'
        },
        {
          id: 9,
          name: 'RiPictureUpload'
        },
        {
          id: 10,
          name: 'RiVideoUpload'
        },
        {
          id: 11,
          name: 'RiRotationChart'
        },
        {
          id: 12,
          name: 'RiRichText'
        },
        {
          id: 13,
          name: 'RiPictureAds'
        },
        {
          id: 14,
          name: 'RiTextModule'
        },
        {
          id: 15,
          name: 'RiGuide'
        },
        {
          id: 16,
          name: 'RiAuxiliaryBlank'
        },
        {
          id: 17,
          name: 'RiTelephone'
        },
        {
          id: 18,
          name: 'RiOfficialAccount'
        }
      ],
      showModule: null,
      modulesData: {}, //  当前右侧模块显示数据
      sortIndex: -1,
      pageSet: {},
      retract: '收起', // 收起文本
      open: '展开', // 展开文本
      insertFlga: true // 解决模块自左插入当前高亮模块上部
    }
  },
  watch: {
    nowRightShowMoudlesIndex: {
      handler (newData) {
        console.log(newData)
        if (newData !== -1 && newData !== null) {
          this.topIconFlag = false
          this.thirdRightModulesList.forEach(item => {
            if (item.id === newData) {
              this.showModule = item.name
            }
          })
        } else {
          this.showModule = ''
        }
        this.insertFlga = false
        this.$nextTick(() => {
          this.insertFlga = true
          console.log(this.nowRightModulesData)
          this.modulesData = this.nowRightModulesData // 初始回显模块数据
          this.sortIndex = newData
        })
        console.log(newData)
      },
      immediate: true
    },
    pageSetData: {
      handler (newData) {
        console.log(newData)
        if (newData) {
          this.pageSet = newData
        }
      },
      immediate: true,
      deep: true
    },
    lang () {
      this.retract = this.$t('pageSetUp.retract')
      this.open = this.$t('pageSetUp.open')
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 点击顶部icon
    handleToChangeIcon () {
      this.$emit('handleToClearIndex', true)
      this.topIconFlag = !this.topIconFlag
      this.showModule = ''
    },
    // 右侧模块编辑向上层传回数据
    handleToBackData (data) {
      console.log(data)
      this.$emit('handleToBackMiddleData', data)
    },
    // 页面设置回显上层
    hanelToPageSet (res) {
      console.log(res)
      this.$emit('hanelToPageSet', res)
    }
  }
}
</script>
<style lang="scss" scoped>
.decRightContainer {
  font-size: 14px;
  .decRightTop {
    background-color: #f8f8f8;
    border: 1px solid #e5e5e5;
    padding: 10px 2%;
    margin-bottom: 10px;
    border-radius: 4px;
    display: flex;
    justify-content: space-between;
    .topRight {
      color: #5a8bff;
      margin-right: 20px;
      display: flex;
      align-items: center;
      cursor: pointer;
      span {
        display: inline-block;
        margin-right: 5px;
      }
    }
  }
  .pageSetContent {
    height: 500px;
    background-color: #f8f8f8;
    border: 1px solid #e5e5e5;
    padding: 10px 2%;
    border-radius: 4px;
    overflow-y: auto;
    overflow-x: hidden;
    padding-bottom: 20px;
  }
}
</style>
