<template>
  <div>
    <CustomHeader />
    <div
      class="admin_contant"
      v-show="flag"
    >
      <LeftNavigation />
      <div class="rightContainer">
        <Crumbs />
        <!-- <vue-scroll
          :ops="ops"
          style="height:100%"
        > -->
        <router-view
          v-if="adminRouterAlive"
          class="right_container"
        />
        <!-- </vue-scroll> -->
      </div>
    </div>
    <div
      v-show="!flag"
      class="hiddleContainer"
    >
      <img :src="imgUrl">
      <p>您当前账号暂无法使用该功能</p>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
// import Crumbs from '@/components/admin/crumbs'
// import CustomHeader from '@/view/admin/index/header'
// import LeftNavigation from '@/view/admin/index/leftNavigation'
import vuescroll from 'vuescroll'
import Vue from 'vue'
import 'vuescroll/dist/vuescroll.css'
Vue.use(vuescroll)
export default {
  components: {
    CustomHeader: () => import('@/view/admin/index/header'),
    LeftNavigation: () => import('@/view/admin/index/leftNavigation'),
    Crumbs: () => import('@/components/admin/crumbs'),
    vuescroll
  },
  data () {
    return {
      ops: {
        vuescroll: {
          mode: 'native'
        },
        scrollPanel: {},
        rail: {
          keepShow: true
        },
        bar: {
          hoverStyle: true,
          onlyShowBarOnScroll: false, // 是否只有滚动的时候才显示滚动条
          background: 'rgba(0,0,0,0.2)'
        }
      },
      adminRouterAlive: true,
      dialogVisible: false,
      flag: true,
      imgUrl: this.$imageHost + '/image/admin/no_authority.png'
    }
  },
  computed: {
    ...mapGetters(['menuFlag', 'activeFlag']),
    menuFlag_ () {
      return this.menuFlag
    },
    activeFlag_ () {
      return this.activeFlag
    }
  },
  provide () { // 提供
    return {
      adminReload: this.reload
    }
  },
  methods: {
    reload () {
      this.adminRouterAlive = false
      this.$nextTick(function () {
        this.adminRouterAlive = true
      })
    }
  },
  watch: {
    menuFlag_ (newData) {
      console.log(newData)
      if (newData === false) {
        this.flag = false
      } else {
        this.flag = true
      }
    },
    activeFlag_ () {
      this.flag = true
    }
  }
}
</script>
<style scoped lang='scss'>
.permissionDialog {
  /deep/ .el-dialog__header {
    text-align: center;
    background-color: #f3f3f3;
    height: 42px;
    line-height: 42px;
    font-size: 14px;
    color: #333;
    overflow: hidden;
    padding: 0 !important;
    span {
      font-size: 14px;
    }
  }
  /deep/ .el-dialog__footer {
    display: flex;
    justify-content: center;
  }
}
img {
  width: 320px;
}
p {
  font-size: 16px;
  margin-top: 20px;
}
.admin_contant {
  width: 100%;
  display: flex;
  position: absolute;
  overflow: hidden;
  bottom: 0;
  top: 85px;
}
.right_container {
  background-color: #e6e9f0;
  min-width: 100%;
  flex: 1;
  overflow-y: auto;
}
.rightContainer {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow-x: hidden;
  /* overflow-y: auto; */
  /deep/ .__vuescroll {
    background-color: #e6e9f0;
  }
}
.hiddleContainer {
  background-color: #fff;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  position: absolute;
  top: 85px;
  left: 0;
  width: 100%;
  color: #999;
}
</style>
