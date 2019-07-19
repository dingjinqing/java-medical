<template>
  <div>
    <div class="pageJump_container">
      <div class="top_left">
        <div>页面名称：</div>
        <el-input
          v-model="pageName"
          placeholder="请输入链接名称"
          size="mini"
        ></el-input>
        <div></div>
      </div>
      <div class="top_middle">
        <div>页面分类：</div>
        <el-select
          v-model="value"
          placeholder="请输入要跳转的网页链接"
          size="mini"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
      <div class="top_right">
        <el-button
          type="primary"
          size="mini"
          @click="handleSearch()"
        >搜索</el-button>
      </div>
    </div>
    <div class="alerm">注意：由于微信限制，目前仅支持小程序关联的公众号文章链接</div>
    <div class="content">
      <table width='100%'>
        <thead>
          <tr>
            <td>名称</td>
            <td>链接</td>
            <td>操作</td>
          </tr>
        </thead>
        <tbody v-if="tbodyFlag">
          <tr
            v-for="(item,index) in trList"
            :key="index"
            :class="clickIindex===index?'clickClass':''"
            @click="handleClick(index)"
          >
            <td>{{item.title}}</td>
            <td class="link">{{item.path}}</td>
            <td
              class="tb_decorate_a"
              @click="deleRr(index)"
            >
              删除
            </td>
          </tr>
        </tbody>

      </table>
      <div
        class="noData"
        v-if="!tbodyFlag"
      >
        <img :src="noImg">
        <span>暂无相关数据</span>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      pageName: '',
      pagePath: '',
      tbodyFlag: true,
      noImg: 'http://mpimg2.weipubao.cn/image/admin/no_data.png',
      trList: [
        {
          title: '111',
          path: 'pages/index/index',
          classification: '分类1',
          spanId: ''
        },
        {
          title: '门店列表页',
          path: 'pages/storelist/storelist',
          spanId: '',
          classification: '分类2'
        },
        {
          title: '购物车页',
          path: 'pages/cart/cart',
          classification: '分类3',
          spanId: ''
        },
        {
          title: '个人中心页',
          path: 'pages/usercenter/usercenter',
          classification: '',
          spanId: ''
        },
        {
          title: '订单列表页',
          path: 'pages/orderlist/orderlist',
          classification: '',
          spanId: ''
        },
        {
          title: '全部商品',
          path: 'pages/searchs/search',
          classification: '',
          spanId: ''
        },
        {
          title: '商家分类',
          path: 'pages/sort/sort',
          classification: '',
          spanId: ''
        },
        {
          title: '分销返利中心',
          path: 'pages/distribution/distribution',
          classification: '',
          spanId: ''
        },
        {
          title: '授权手机号',
          path: 'pages/auth/auth',
          classification: '',
          spanId: ''
        },
        {
          title: '积分商品列表',
          path: 'pages/searchs/search?is_from=integral',
          classification: '',
          spanId: ''
        },
        {
          title: '会员卡领取页（卡号+密码）',
          path: 'pages/getcardpage/getcardpage?type=1',
          classification: '',
          spanId: ''
        },
        {
          title: '会员卡领取页（领取码）',
          path: 'pages/getcardpage/getcardpage?type=2',
          classification: '',
          spanId: ''
        },
        {
          title: '客服',
          path: 'pages/customer/customer',
          classification: '',
          spanId: ''
        }
      ],
      options: [{
        value: '测试页面1',
        label: ''
      }, {
        value: '测试页面2',
        label: ''
      }, {
        value: '测试页面3',
        label: ''
      }, {
        value: '测试页面4',
        label: ''
      }, {
        value: '测试页面5',
        label: ''
      }],
      clickIindex: null
    }
  },
  methods: {
    ...mapActions(['choisePagePath']),
    // 行选中高亮
    handleClick (index) {
      this.clickIindex = index
      this.choisePagePath(this.trList[index].path)
    },
    // 搜索
    handleSearch () {
      console.log(1)
    },
    // 删除
    deleRr (index) {
      console.log(index)
      this.trList.splice(index, 1)
    }
  }
}
</script>
<style scoped>
.tb_decorate_a {
  color: #0000ee;
  cursor: pointer;
}
.noData {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* width: 650px; */
  flex-direction: column;
  border: 1px solid #eee;
  margin-top: 10px;
}
.noData span {
  margin: 10px;
}
.clickClass {
  background-color: #eee !important;
}
.pageJump_container {
  display: flex;
  justify-content: space-around;
  padding-bottom: 10px;
  margin: 10px 0 0 10px;
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
  width: 120px;
}
thead td:nth-of-type(2) {
  width: 460px;
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
.top_left {
  display: flex;
  align-items: center;
  /* margin-left: 7px; */
}
.top_middle {
  display: flex;
  align-items: center;
}
.noData {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* width: 650px; */
  flex-direction: column;
  border: 1px solid #eee;
  margin-top: 10px;
}
.alerm {
  margin-bottom: 10px;
  padding-left: 28px;
  margin-top: 10px;
  font-size: 14px;
  color: red;
}
</style>
<style>
.pageJump_container .el-input {
  width: 170px !important;
}
</style>
