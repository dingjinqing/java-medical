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
    pageSetupMain: () => import('./pageSetupModules/pageSetupMain') // 表单信息配置
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
        if (newData) {
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
