<template>
  <div class="brandManagementContent">
    <div class="brandManagementContent_main">
      <ul>
        <li class="li">
          <div class="liNav">
            <span>手机号</span>
            <el-input
              v-model="phoneNum"
              placeholder="请输入手机号"
              size="small"
            ></el-input>
          </div>
          <div class="liNav">
            <span>微信昵称</span>
            <el-input
              v-model="vxName"
              placeholder="请输入微信昵称"
              size="small"
            ></el-input>
          </div>
          <div class="liNav">
            <span>来源</span>
            <el-select
              v-model="sourceValue"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in sourceOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div class="liNav">
            <span>会员卡</span>
            <el-select
              v-model="membershipCardVal"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in membershipCardOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </li>
      </ul>
      <ul class="uls">
        <li>
          <div class="liNav">
            <span>标签</span>
            <el-autocomplete
              v-model="labelVal"
              placeholder="请输入标签"
              :fetch-suggestions="querySearch"
              size="small"
              @select="handleSelect"
            ></el-autocomplete>
          </div>
        </li>
        <li>
          <div class="liNav date">
            <span>注册时间</span>
            <el-date-picker
              v-model="datePickerVal"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format='yyyy-MM-dd'
              size="small"
            >
            </el-date-picker>
          </div>
        </li>
        <li>
          <div class="liNav">
            <span>邀请人</span>
            <el-input
              v-model="phoneNum"
              placeholder="请输入邀请人名称"
              size="small"
            ></el-input>
          </div>
        </li>
      </ul>
      <ul
        class="hiddenUl"
        v-if='!arrorFlag'
      >
        <li>
          <div>
            <span>指定时间内登录有记录</span>
            <el-date-picker
              v-model="datePickerVal_one"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format='yyyy-MM-dd'
              size="small"
            >
            </el-date-picker>
          </div>
          <div class="hiddenRight">
            <span>客单价</span>
            <el-input
              v-model="unitPriceLeft"
              placeholder="请输入内容"
              size="small"
            ></el-input>
            <i>至</i>
            <el-input
              v-model="unitPriceRight"
              placeholder="请输入内容"
              size="small"
            ></el-input>
          </div>
        </li>
        <li>
          <div>
            <span>指定时间内有加购行为</span>
            <el-date-picker
              v-model="datePickerVal_two"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format='yyyy-MM-dd'
              size="small"
            >
            </el-date-picker>
          </div>
          <div class="hiddenRight">
            <span>累计购买次数</span>
            <el-input
              v-model="frequencyLeft"
              placeholder="请输入内容"
              size="small"
            ></el-input>
            <i>至</i>
            <el-input
              v-model="frequencyRight"
              placeholder="请输入内容"
              size="small"
            ></el-input>
          </div>
        </li>
        <li class="specialLi">
          <div>
            <span>指定时间内有交易行为</span>
            <el-date-picker
              v-model="datePickerVal_two"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format='yyyy-MM-dd'
              size="small"
            >
            </el-date-picker>
          </div>
          <div class="brand_title">
            <span class="nameClass">购买指定商品</span>
            <div
              class="choiseDivClass"
              @click="handleClickChoiseGood()"
            >
              <img :src="choiseGoodImgUrl">
              选择商品
            </div>
          </div>
        </li>
      </ul>
      <ul class="ulsThree">
        <li>
          <el-checkbox v-model="checkPhone">手机号</el-checkbox>
          <el-checkbox v-model="checkIntegr">有积分</el-checkbox>
          <el-checkbox v-model="balance">有余额</el-checkbox>
          <el-checkbox v-model="membershipCard">有会员卡</el-checkbox>
          <el-checkbox v-model="noLanding">已禁止登陆</el-checkbox>
          <el-checkbox v-model="importMembership">导入的会员</el-checkbox>
        </li>
        <li>
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
          <el-button
            type="info"
            size="small"
            plain
          >会员导出</el-button>
        </li>
      </ul>

      <ul class="arrowUl">
        <li>
          <div @click="handleToChangeArror()">
            <div
              v-if="arrorFlag"
              style="color:rgb(90, 139, 255);cursor:pointer"
            >更多&nbsp;<img :src="ArrowArr[0].img_1"></div>
            <div
              v-if="!arrorFlag"
              style="color:rgb(90, 139, 255);cursor:pointer"
            >收起&nbsp;<img :src="ArrowArr[1].img_2"></div>
          </div>
        </li>
      </ul>
    </div>

    <!--底部表格-->
    <div class="brandManagementContent_bottom">
      <div class="table_container">
        <table width='100%'>
          <thead>
            <tr>
              <td>ID</td>
              <td>昵称</td>
              <td>手机号</td>
              <td>邀请人</td>
              <td>余额</td>
              <td>积分</td>
              <td>会员卡</td>
              <td>来源</td>
              <td>注册时间</td>
              <td>操作</td>
            </tr>
          </thead>
          <tbody v-if="tbodyFlag">
            <tr
              v-for="(item,index) in trList"
              :key="index"
            >
              <td>
                <div class="tdCenter sp_">
                  <el-checkbox
                    @click.prevent="handleClick(item,index)"
                    v-model="item.ischecked"
                  ></el-checkbox>
                  <span>{{item.id}}</span>
                </div>

              </td>
              <td :class="isCenterFlag?'tdCenter':''">
                <span style="color: #5A8BFF;cursor:pointer">{{item.name}}</span>

              </td>
              <td class="tb_decorate_a">
                {{item.phoneNum}}
              </td>
              <td class="tb_decorate_a">
                {{item.person}}
              </td>
              <td class="tb_decorate_a">
                <span class="plusSpan">{{item.balance}}</span>
                <img :src="plusImg">
              </td>
              <td class="tb_decorate_a">
                <span class="plusSpan">{{item.integral}}</span>
                <img :src="plusImg">
              </td>
              <td class="tb_decorate_a">
                <div class="member">
                  <span>{{item.membershipCard}}</span>
                  <div>
                    <span>设置</span>
                    <span style="margin-top:8px">更多</span>
                  </div>
                </div>
              </td>
              <td class="tb_decorate_a">
                {{item.from}}
              </td>
              <td class="tb_decorate_a">
                {{item.date}}

              </td>
              <td class="tb_decorate_a">
                <div class="lastDiv">
                  <span>余额明细</span>
                  <span>积分明细</span>
                  <span>禁止登陆</span>
                </div>
                <div
                  class="lastDiv"
                  style="margin-top:5px"
                >
                  <span>打标签</span>
                  <span>查看详情</span>
                </div>
              </td>
            </tr>
          </tbody>

        </table>
        <!--表格底部-->
        <div class="tableFooter">
          <div class="footer_t">

            <el-checkbox v-model="allChecked">全选</el-checkbox>
            <div style="margin-left:50px">
              <el-select
                v-model="value_one"
                placeholder="请选择"
                size="small"
              >
                <el-option
                  v-for="item in options_one"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div style="margin:0 10px">
              <el-select
                v-model="value_two"
                placeholder="请选择"
                size="small"
              >
                <el-option
                  v-for="item in options_two"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div style="margin:0 10px">
              <el-select
                v-model="value_three"
                placeholder="请选择"
                size="small"
              >
                <el-option
                  v-for="item in options_three"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div style="margin:0 10px">
              <el-select
                v-model="value_four"
                placeholder="请选择"
                size="small"
              >
                <el-option
                  v-for="item in options_four"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <div style="margin:0 10px">
              <el-select
                v-model="value_five"
                placeholder="请选择"
                size="small"
              >
                <el-option
                  v-for="item in options_five"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
          </div>
          <div class="footer_b">
            <span>当前页面1/140，总记录2793条</span>
            <el-pagination
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage3"
              :page-size="20"
              layout="prev, pager, next, jumper"
              :total="1000"
            >
            </el-pagination>
          </div>
        </div>
      </div>
    </div>
    <ChoosingGoods />
  </div>
</template>
<script>
import ChoosingGoods from '@/components/admin/choosingGoods'
export default {
  components: { ChoosingGoods },
  data () {
    return {
      phoneNum: '',
      vxName: '',
      sourceOptions: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      sourceValue: '',
      membershipCardOptions: [{
        value: '选项1',
        label: '会员1'
      }, {
        value: '选项2',
        label: '会员2'
      }, {
        value: '选项3',
        label: '会员3'
      }],
      membershipCardVal: '',
      labelVal: '',
      datePickerVal: '',
      checkPhone: false,
      checkIntegr: false,
      balance: false,
      membershipCard: false,
      noLanding: false,
      importMembership: false,
      datePickerVal_one: '',
      datePickerVal_two: '',
      datePickerVal_three: '',
      frequencyLeft: '',
      frequencyRight: '',
      ArrowArr: [
        {
          img_1: this.$imageHost + '/image/admin/show_more.png'
        },
        {
          img_2: this.$imageHost + '/image/admin/hid_some.png'
        }
      ],
      arrorFlag: true,
      unitPriceLeft: '',
      unitPriceRight: '',
      choiseGoodImgUrl: this.$imageHost + '/image/admin/icon_jia.png',
      tbodyFlag: true,
      trList: [
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '限次卡核销服务',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        },
        {
          id: '111',
          name: '用户12811',
          phoneNum: '13167356120',
          person: '帅飞',
          balance: '1.00',
          integral: '1000',
          membershipCard: '',
          from: '后台',
          date: '2019-07-30 10:41:31',
          ischecked: false
        }
      ],
      clickIindex: '',
      isCenterFlag: '',
      allChecked: false,
      options_one: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      options_two: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      options_three: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      options_four: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      options_five: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      currentPage3: 1,
      value_one: '',
      value_two: '',
      value_three: '',
      value_four: '',
      value_five: '',
      plusImg: this.$imageHost + '/image/admin/add_some.png'
    }
  },
  watch: {
    allChecked (newData) {
      if (newData === true) {
        this.trList.map((item, index) => {
          item.ischecked = true
        })
      } else {
        this.trList.map((item, index) => {
          item.ischecked = false
        })
      }
    }
  },
  mounted () {
    this.restaurants = this.loadAll()
  },
  methods: {
    //
    querySearch (queryString, cb) {
      var restaurants = this.restaurants
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
      // 调用 callback 返回建议列表的数据
      cb(results)
    },
    createFilter (queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    loadAll () {
      return [
        { 'value': '三全鲜食（北新泾店）', 'address': '长宁区新渔路144号' },
        { 'value': 'Hot honey 首尔炸鸡（仙霞路）', 'address': '上海市长宁区淞虹路661号' },
        { 'value': '新旺角茶餐厅', 'address': '上海市普陀区真北路988号创邑金沙谷6号楼113' },
        { 'value': '泷千家(天山西路店)', 'address': '天山西路438号' },
        { 'value': '胖仙女纸杯蛋糕（上海凌空店）', 'address': '上海市长宁区金钟路968号1幢18号楼一层商铺18-101' },
        { 'value': '贡茶', 'address': '上海市长宁区金钟路633号' }
      ]
    },
    // 选中输入框建议列表项
    handleSelect (item) {
      console.log(item)
    },
    // 改变箭头事件
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },
    // 点击选择商品按钮
    handleClickChoiseGood () {
      this.$http.$emit('choosingGoodsFlag', true)
    },
    // 当前页发生变化
    handleCurrentChange () {

    },
    // 会员列表表格选中
    handleClick (item, index) {
      this.trList[index].ischecked = true
    }
  }
}
</script>
<style scoped>
.brandManagementContent {
  padding: 10px;
  padding-bottom: 68px;
  /* padding-right: 23px; */
  min-width: 1300px;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  z-index: 10;
}
.brandManagementContent_main {
  position: relative;
  background-color: #fff;
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
  padding: 15px 25px;
}
.li {
  padding: 8px 0;
  display: flex;
}
.liNav {
  width: 280px;
  display: flex;
}
.liNav span {
  display: block;
  width: 80px;
  line-height: 30px;
  height: 30px;
  text-align: right;
  color: #333;
  margin-right: 25px;
}
.uls {
  margin-top: 10px;
  display: flex;
}
.ulsThree {
  margin-top: 15px;
  display: flex;
  padding-left: 40px;
}
.ulsThree li:nth-of-type(2) {
  margin-left: 130px;
}
.uls span {
  width: 56px;
}
.date {
  width: 455px;
}
.arrowUl {
  margin-top: 15px;
  display: flex;
  justify-content: center;
}
.hiddenUl {
  margin-top: 20px;
  padding: 0 65px 0 15px;
}
.hiddenUl span {
  margin-right: 30px;
}
.hiddenUl li {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}
.hiddenRight {
  display: flex;
}
.hiddenRight span,
i {
  white-space: nowrap;
  height: 40px;
  line-height: 40px;
}
.hiddenRight i {
  display: inline-block;
  margin: 0 10px;
}
.specialLi {
  margin-right: 213px;
}
.brand_title {
  display: flex;
  justify-content: flex-start;
}
.nameClass {
  white-space: nowrap;
  margin: 0 5px;
}
.choiseDivClass {
  width: 120px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  color: #5a8bff;
  border: 1px solid #ccc;
  cursor: pointer;
  display: inline-block;
}
.brandManagementContent_bottom {
  margin-top: 10px;
  padding: 10px;
  background: #fff;
}

.clickClass {
  background-color: #eee !important;
}
ul {
  padding-left: 30px;
}
ul li {
  line-height: 30px;
  display: flex;
  white-space: nowrap;
  /* margin-top: 30px; */
}
ul li:nth-of-type(1) {
  margin-top: 0;
}
.choiseDialog {
  overflow-y: auto;
}
.choiseDialog ul {
  display: flex;
  margin-top: 10px;
}
.choiseDialog ul li {
  margin-top: 0;
}
.choiseDialog ul li:nth-of-type(2) {
  margin: 0 30px;
}
.choiseDialog ul li:nth-of-type(3) {
  margin-right: 30px;
}
.middleBbtnDiv {
  padding: 10px 30px;
}
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
  width: 100%;
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
  width: 105px;
  clear: both;
  overflow: hidden;
  /* display: flex;
  align-items: center; */
}
thead td:nth-of-type(1) .tdTopText {
  float: left;
  margin-left: 3px;
}
thead td:nth-of-type(2) {
  width: 132px;
}
thead td:nth-of-type(3) {
  width: 104px;
}
.sp_ {
  display: flex;
  justify-content: center;
}
.sp_ span {
  margin-left: 10px;
}
thead td:nth-of-type(4) {
  width: 71px;
}
thead td:nth-of-type(5) {
  width: 145px;
}
thead td:nth-of-type(6) {
  width: 145px;
}
thead td:nth-of-type(7) {
  width: 145px;
}
thead td:nth-of-type(8) {
  width: 132px;
}
thead td:nth-of-type(9) {
  width: 72px;
}
tbody td {
  text-align: center;
  border: 1px solid #eff1f5;
  color: #666;
}
td {
  padding: 8px 7px;
  vertical-align: middle !important;
  text-align: center;
  font-size: 12px;
}
.plusSpan {
  /* display: inline-block;
  margin-top: -3px; */
  position: relative;
  top: -3px;
}
img {
  margin-left: 10px;
}
.tdCenter {
  text-align: center;
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

.tdCenter {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
}
.level_1 {
  margin-left: 10px;
}
.level_2 {
  margin-left: 15px;
}
.tableFooter {
  height: 100px;
}
.footer_t {
  overflow: hidden;
  padding-left: 30px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.footer_b {
  overflow: hidden;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.tb_decorate_a img {
  margin-left: 15px;
  cursor: pointer;
}
.member {
  display: flex;
  justify-content: space-between;
}
.member > div {
  display: flex;
  flex-direction: column;
  color: #5a8bff;
}
.member > div > span {
  cursor: pointer;
}
.lastDiv {
  padding: 5px 0 5px 50px;
  text-align: left;
  color: #5a8bff;
}
.lastDiv span {
  cursor: pointer;
  margin-right: 10px;
}
</style>
<style>
.liNav .el-input__inner {
  width: 150px !important;
}
.hiddenRight .el-input__inner {
  width: 150px !important;
}
.date .el-input__inner {
  width: 350px !important;
}
.ulsThree .el-button {
  padding: 9px 15px !important;
  font-size: 12px !important;
  border-radius: 3px !important;
}
.footer_t .el-input__inner {
  width: 110px !important;
}
.footer_b .el-pagination {
  display: inline-block;
}
</style>
