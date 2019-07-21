<template>
  <div class="commonLinks">
    <table width='100%'>
      <thead>
        <tr>
          <td>名称</td>
          <td>链接</td>
          <td>操作</td>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="(item,index) in trList"
          :key="index"
          :class="clickIindex===index?'clickClass':''"
          @click="handleClick(index)"
        >
          <td>{{item.title}}</td>
          <td class="link">{{item.path}}</td>
          <td class="tb_decorate_a">
            <div
              class="icon fa fa-share-alt"
              @mouseover="handleOver(index)"
              @mouseleave="handleLeave(index)"
              @click="handleShare(index)"
            ><span
                :class="item.spanId===index?'spanClass':''"
                class="icon_hiddle"
              >分享<i></i></span></div>
          </td>
        </tr>
      </tbody>
    </table>
    <!--分享弹窗-->
    <el-dialog
      title="扫一扫，分享给好友吧~"
      :visible.sync="dialogVisibleShare"
      width="25%"
      :modal='false'
    >
      <div class="shareDialog">
        <div class="shareDialog_content"><img
            style="height:160px"
            :src="shareImg"
          ></div>
        <div
          class="shareDialog_bottom"
          @click="downs()"
        >下载二维码</div>
      </div>

      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-input
          v-model="pathInput"
          placeholder="请输入内容"
          size="mini"
          ref="copy"
        ></el-input>
        <span
          style="cursor:pointer"
          @click="clickCopy()"
        >复制</span>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      trList: [
        {
          title: '首页',
          path: 'pages/index/index',
          spanId: ''
        },
        {
          title: '门店列表页',
          path: 'pages/storelist/storelist',
          spanId: ''
        },
        {
          title: '购物车页',
          path: 'pages/cart/cart',
          spanId: ''
        },
        {
          title: '个人中心页',
          path: 'pages/usercenter/usercenter',
          spanId: ''
        },
        {
          title: '订单列表页',
          path: 'pages/orderlist/orderlist',
          spanId: ''
        },
        {
          title: '全部商品',
          path: 'pages/searchs/search',
          spanId: ''
        },
        {
          title: '商家分类',
          path: 'pages/sort/sort',
          spanId: ''
        },
        {
          title: '分销返利中心',
          path: 'pages/distribution/distribution',
          spanId: ''
        },
        {
          title: '授权手机号',
          path: 'pages/auth/auth',
          spanId: ''
        },
        {
          title: '积分商品列表',
          path: 'pages/searchs/search?is_from=integral',
          spanId: ''
        },
        {
          title: '会员卡领取页（卡号+密码）',
          path: 'pages/getcardpage/getcardpage?type=1',
          spanId: ''
        },
        {
          title: '会员卡领取页（领取码）',
          path: 'pages/getcardpage/getcardpage?type=2',
          spanId: ''
        },
        {
          title: '客服',
          path: 'pages/customer/customer',
          spanId: ''
        }
      ],
      clickIindex: null,
      dialogVisibleShare: false,
      pathInput: '',
      shareImg: 'http://mpdev.weipubao.cn/upload/4748160/qrcode/33/T33P307bfc9947d3756c206033bd06eb13b0_20190614100251.jpg'
    }
  },
  methods: {
    ...mapActions(['choisePagePath']),
    // 行选中高亮
    handleClick (index) {
      this.clickIindex = index
      this.choisePagePath(this.trList[index].path)
    },
    // 鼠标移入
    handleOver (index) {
      this.trList[index].spanId = index
    },
    // 鼠标移出
    handleLeave (index) {
      this.trList[index].spanId = ''
    },
    // 点击分享
    handleShare (index) {
      console.log(index)
      this.dialogVisibleShare = true
      this.pathInput = this.trList[index].path
      console.log(this.pathInput)
    },
    // 复制
    clickCopy () {
      this.$refs.copy.select() // 选择对象
      document.execCommand('Copy') // 执行浏览器复制命令
    },
    // 下载图片
    downs () {
      // var alink = document.createElement('a')
      // alink.href = this.shareImg
      // alink.download = name || 'pic' // 图片名
      // alink.click()
    }

  }
}
</script>
<style scoped>
.shareDialog_bottom {
  text-align: center;
  margin-top: 18px;
  cursor: pointer;
}
.shareDialog_content {
  display: flex;
  justify-content: center;
}
.shareDialog img {
  width: 160px;
}
.dialog-footer span {
  margin-left: 20px;
  color: #5a8bff;
}

.clickClass {
  background-color: #eee !important;
}
.spanClass {
  display: block !important;
}
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
}
thead {
  display: table-header-group;
  vertical-align: middle;
  border-color: inherit;
}
thead td {
  background: #faf9f8;
  text-align: center;
  color: #333;
  padding: 8px 10px;
  vertical-align: middle !important;
}
thead td:nth-of-type(1) {
  width: 238px;
}
thead td:nth-of-type(2) {
  width: 356px;
}
tbody td {
  text-align: center;
  border: 1px solid #eff1f5;
  color: #666;
}
td {
  padding: 8px 10px;
  vertical-align: middle !important;
  text-align: center;
}
.icon {
  color: #5a8bff;
  font-size: 20px;
  cursor: pointer;
  position: relative;
}
.icon_hiddle {
  display: block;
  width: 40px;
  height: 20px;
  line-height: 20px;
  background-color: rgba(122, 122, 122, 0.9);
  position: absolute;
  left: -10px;
  top: -25px;
  border-radius: 5px;
  font-size: 12px;
  color: #fff;
  display: none;
}
.icon_hiddle i {
  width: 0;
  height: 0;
  border-right: 5px solid transparent;
  border-left: 5px solid transparent;
  border-top: 5px solid rgba(122, 122, 122, 0.9);
  position: absolute;
  left: 15px;
  bottom: -5px;
}
</style>
<style>
.commonLinks .el-dialog__header {
  background-color: #fff !important;
}
.commonLinks .el-dialog__footer {
  display: flex !important;
  justify-content: center;
  position: relative;
}
.commonLinks .el-dialog__body {
  border-bottom: 1px solid #eee !important;
}
.commonLinks .el-dialog {
  margin-top: 25vh !important;
}
</style>
