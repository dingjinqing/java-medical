<template>
  <div class="decRightContainer">
    <div class="decRightTop">
      <div>页面设置</div>
      <div
        @click="handleToChangeIcon"
        class="topRight"
      >
        <span>{{topIconFlag?'收起':'展开'}}</span>
        <img :src="topIconFlag?topIconUp:topIconDown">
      </div>
    </div>
    <!--页面设置模块-->
    <div
      v-if="topIconFlag"
      class="pageSetContent"
    >
      <pageSetupMain />
    </div>
    <!--模块配置-->
    <div v-if="!topIconFlag">
      <components
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
    pageSetupMain: () => import('./pageSetupModules/pageSetupMain'),
    RightMembershipCard: () => import('./pageSetupModules/marketingComponents/rightMembershipCard'),
    RightCoupon: () => import('./pageSetupModules/marketingComponents/rightCoupon'),
    RightCommodity: () => import('./pageSetupModules/commodityComponents/rightCommodity'),
    RightCommoditySearch: () => import('./pageSetupModules/commodityComponents/rightCommoditySearch')
  },
  props: {
    nowRightShowMoudlesIndex: Number,
    nowRightModulesData: Object,
    nowRightShowIndex: Number
  },
  data () {
    return {
      topIconUp: this.$imageHost + '/image/admin/shop_deco/icon_up.png',
      topIconDown: this.$imageHost + '/image/admin/shop_deco/icon_down.png',
      topIconFlag: true,
      modulesShow: false,
      thirdRightModulesList: [
        {
          id: 1,
          name: 'RightMembershipCard'
        },
        {
          id: 2,
          name: 'RightCoupon'
        },
        {
          id: 8,
          name: 'RightCommodity'
        },
        {
          id: 9,
          name: 'RightCommoditySearch'
        }
      ],
      showModule: null,
      modulesData: {},
      sortIndex: -1
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
        console.log(newData)
      },
      immediate: true
    },
    nowRightShowIndex: {
      handler (newData) {
        console.log(newData, this.nowRightModulesData)
        this.modulesData = this.nowRightModulesData
        this.sortIndex = newData
      },
      immediate: true
    }
  },
  methods: {
    // 点击顶部icon
    handleToChangeIcon () {
      this.$emit('handleToClearIndex')
      this.topIconFlag = !this.topIconFlag
      this.showModule = ''
    },
    handleToBackData (data) {
      console.log(data)
      this.$emit('handleToBackMiddleData', data)
    }
  }
}
</script>
<style lang="scss" scoped>
.decRightContainer {
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
  }
}
</style>
