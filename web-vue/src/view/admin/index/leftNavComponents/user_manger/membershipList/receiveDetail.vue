<template>
  <div class="receiveDetail">
    <div class="receiveDetailMain">
      <div class="top spDiv">
        <div>
          <span>手机号码</span>
          <el-input
            v-model="phoneNum"
            placeholder="请输入手机号码"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>昵称</span>
          <el-input
            v-model="nameInput"
            placeholder="请输入昵称"
            size="small"
          ></el-input>
        </div>
        <div class="receiveDetailDate">
          <span>领取时间</span>
          <el-date-picker
            v-model="dateInput"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            default-value="2010-10-01"
            size="small"
          >
          </el-date-picker>
        </div>
      </div>

      <div class="top middle">
        <div>
          <span>会员卡</span>
          <el-select
            v-model="membershipCardValue"
            placeholder="请选择"
            size="small"
          >
            <el-option
              v-for="item in membershipCardOptins"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
        <div class="middleType">
          <span class="middleTypeSpan">会员卡类型</span>
          <el-select
            v-model="CardTypeValue"
            placeholder="请选择"
            size="small"
          >
            <el-option
              v-for="item in CardTypeOptins"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
        <div>
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
        </div>
      </div>

      <!--底部表格-->
      <div
        class="content"
        v-if="page_one"
      >
        <table width='100%'>
          <thead>
            <tr>
              <td>领取时间</td>
              <td>会员卡号</td>
              <td>会员</td>
              <td>会员卡|类型</td>
              <td>状态</td>
              <td>余额(元)</td>
              <td>门店服务次数(次)</td>
              <td>兑换商品次数(次)</td>
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
              <td>{{item.title}}</td>
              <td>{{item.title}}</td>
              <td>{{item.title}}</td>
              <td>{{item.title}}</td>
              <td>{{item.title}}</td>
              <td>{{item.title}}</td>
              <td class="link">{{item.status}}</td>
              <td class="tb_decorate_a">
                {{item.path}}
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
      <div
        class="content_two"
        v-else
      >
        <table width='100%'>
          <thead>
            <tr>
              <td>名称</td>

              <td>链接</td>
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

              <td class="tb_decorate_a">
                {{item.path}}
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
  </div>
</template>
<script>
export default {
  data () {
    return {
      phoneNum: '',
      nameInput: '',
      dateInput: '',
      membershipCardOptins: [
        {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }
      ],
      membershipCardValue: '',
      CardTypeOptins: [
        {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }
      ],
      CardTypeValue: '',
      page_one: true,
      tbodyFlag: false,
      trList: [
        {
          title: '111',
          path: 'pages/index/index',
          classification: '分类1',
          status: '营业中',
          spanId: ''
        },
        {
          title: '门店列表页',
          path: 'pages/storelist/storelist',
          spanId: '',
          classification: '分类2',
          status: '歇业中'
        },
        {
          title: '购物车页',
          path: 'pages/cart/cart',
          classification: '分类3',
          spanId: '',
          status: '营业中'
        }

      ],
      clickIindex: null,
      noImg: 'http://mpimg2.weipubao.cn/image/admin/no_data.png'
    }
  },
  methods: {
    // 行点击
    handleClick (index) {
      this.clickIindex = index
    }
  }
}
</script>
<style scoped>
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
.receiveDetail {
  padding: 10px;
  padding-bottom: 68px;
  /* padding-right: 23px; */
  min-width: 1300px;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
.receiveDetailMain {
  padding: 15px 25px;
  position: relative;
  background-color: #fff;
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
}
.top {
  display: flex;
}
.top > div {
  display: flex;
  width: 280px;
}
.top > div:nth-of-type(2) {
  margin: 0 100px;
}
.top > div > span {
  display: inline-block;
  width: 80px;
  line-height: 30px;
  line-height: 30px;
  text-align: right;
  margin-right: 25px;
  color: #333;
}
.receiveDetailDate {
  width: 480px !important;
}
.middle {
  margin-top: 20px;
}
.spDiv {
  padding-left: 22px;
}
.middleType {
  margin: 0 122px !important;
  margin-left: 80px !important;
  width: 330px !important;
}
.middleTypeSpan {
  width: 100px !important;
}

.content {
  margin-top: 30px;
}
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
}
.clickClass {
  background-color: #eee !important;
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
/* thead td:nth-of-type(1) {
  width: 220px;
}
thead td:nth-of-type(2) {
  width: 104px;
} */

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
.content_two td:nth-of-type(2) {
  width: 490px !important;
}
</style>
<style>
.receiveDetailMain .top .el-input__inner {
  width: 150px !important;
}
.receiveDetail .receiveDetailMain .receiveDetailDate .el-input__inner {
  width: 350px !important;
}
</style>
