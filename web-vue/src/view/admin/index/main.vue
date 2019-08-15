<template>
  <div>
    <Header />
    <div
      class="admin_contant"
      v-show="flag"
    >
      <LeftNavigation />
      <div class="rightContainer">
        <Crumbs />
        <router-view class="right_container" />
      </div>

      <!--模块无权限弹窗-->
      <div class="permissionDialog">
        <el-dialog
          title="系统通知"
          :visible.sync="dialogVisible"
          width="30%"
        >
          <span>此功能需要更高版本才可使用。如需了解详情我们的产品顾问将尽快与您联系！！！</span>
          <span
            slot="footer"
            class="dialog-footer"
          >
            <el-button
              size="small"
              @click="dialogVisible = false"
            >下次再说</el-button>
            <el-button
              type="primary"
              size="small"
              @click="dialogVisible = false"
            >了解更多</el-button>
          </span>
        </el-dialog>
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
import Crumbs from '@/components/admin/crumbs'
import Header from '@/view/admin/index/header'
import LeftNavigation from '@/view/admin/index/leftNavigation'
export default {
  components: { Header, LeftNavigation, Crumbs },
  data () {
    return {
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
