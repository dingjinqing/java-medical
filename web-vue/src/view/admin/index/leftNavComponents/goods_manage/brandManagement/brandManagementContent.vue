<template>
  <div class="brandManagementContent">
    <div class="brandManagementContent_main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="全部品牌"
          name="first"
        >
          <ul class="topUl">
            <li>分类名称：
              <el-autocomplete
                popper-class="my-autocomplete"
                v-model="state3"
                :fetch-suggestions="querySearch"
                placeholder="请输入内容"
                @select="handleSelect"
                size="small"
              >
                <template slot-scope="props">
                  <div class="name">{{ props.item.value }}</div>
                </template>
              </el-autocomplete>
            </li>
            <li>
              <div class="block">
                <span class="demonstration">创建时间：</span>
                <el-date-picker
                  v-model="value9"
                  type="daterange"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  default-value="2010-10-01"
                  size="small"
                >
                </el-date-picker>
              </div>
            </li>
            <li>品牌分类：
              <el-select
                v-model="valueClss"
                placeholder="请选择"
                size="small"
              >
                <el-option
                  v-for="item in optionsClss"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </li>
            <li>是否为推荐品牌：
              <el-select
                v-model="valueIsClss"
                placeholder="请选择"
                size="small"
              >
                <el-option
                  v-for="item in optionsIsClss"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </li>
          </ul>
          <div class="topBtn">
            <el-button
              type="primary"
              size="small"
              @click="handleAddBrand()"
            >添加品牌</el-button>
            <el-button
              type="primary"
              size="small"
            >筛选</el-button>
          </div>
        </el-tab-pane>

        <el-tab-pane
          label="品牌分类"
          name="second"
        >
          <ul class="topUl">
            <li>分类名称：
              <el-autocomplete
                popper-class="my-autocomplete"
                v-model="state3"
                :fetch-suggestions="querySearch"
                placeholder="请输入内容"
                @select="handleSelect"
                size="small"
              >
                <template slot-scope="props">
                  <div class="name">{{ props.item.value }}</div>
                </template>
              </el-autocomplete>
            </li>
            <li>
              <div class="block">
                <span class="demonstration">创建时间：</span>
                <el-date-picker
                  v-model="value9"
                  type="daterange"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  default-value="2010-10-01"
                  size="small"
                >
                </el-date-picker>
              </div>
            </li>
            <li>
              <el-button
                type="primary"
                size="small"
              >筛选</el-button>
            </li>
          </ul>
          <div class="tapTwo">
            <el-button
              type="primary"
              size="small"
            >添加品牌分类</el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane
          label="品牌展示设置"
          name="third"
        >
          <div class="showMain">
            <div class="showTop">将全部品牌展示在商品分类页：
              <el-switch
                v-model="switchValue"
                active-color="#F7931E"
                inactive-color="#DDDDDD"
              >
              </el-switch>
              <span>已关闭</span>
              <span style="color:#999">开启后，将在商品分类页展示全部品牌列表</span>
              <div
                class="example"
                @mouseover="showOver()"
                @mouseleave="showLeave()"
              >查看实例
                <div
                  class="hover_show"
                  v-if="showFlag"
                >
                  <img :src="showHiddleImgUrl">
                </div>
              </div>
            </div>
            <div
              class="showTop"
              style="margin-top:30px"
            >推荐品牌：
              <el-switch
                v-model="switchValueBottom"
                active-color="#F7931E"
                inactive-color="#DDDDDD"
              >
              </el-switch>
              <span>已关闭</span>
              <span style="color:#999">开启后，将在商品分类页展示全部品牌列表</span>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div
      class="firstTab"
      v-if="bottomDivFlag"
    >
      <div class="content_two">
        <table width='100%'>
          <thead>
            <tr class="brandTr">
              <td :class="hiddle_1?'':'firstNameClass'">品牌名称</td>
              <td v-if="hiddle_1">品牌logo</td>
              <td v-if="hiddle_1">优先级</td>
              <td v-if="hiddle_1">品牌分类</td>
              <td :class="hiddle_1?'':'secondNameClass'">包含商品数量</td>
              <td :class="hiddle_1?'':'threeNameClass'">推荐品牌</td>
              <td :class="hiddle_1?'':'fourNameClass'">创建时间</td>
              <td>操作</td>
            </tr>
          </thead>
          <tbody v-if="tbodyFlag">
            <tr
              v-for="(item,index) in trList"
              :key="index"
            >
              <td>{{item.title}}</td>
              <td v-if="hiddle_1"><img :src="item.logoImg"></td>
              <td v-if="hiddle_1">{{item.priority}}</td>
              <td v-if="hiddle_1">{{item.classification}}</td>
              <td>{{item.num}}</td>
              <td>{{item.Recommend}}</td>
              <td>{{item.date}}</td>
              <td class="lastSpan">
                <span>编辑</span>
                <span>删除</span>
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

      <!--分页-->
      <div class="tapOneblock">
        <span class="demonstration">直接前往</span>
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage1"
          :page-size="20"
          layout="prev, pager, next, jumper"
          :total="100"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      activeName: 'first',
      restaurants: [],
      state3: '',
      value9: '',
      optionsClss: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      valueClss: '',
      optionsIsClss: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      valueIsClss: '',
      trList: [
        {
          title: '香奈儿',
          logoImg: this.$imageHost + '/upload/7467397/image/20190507/crop_N7Fu7EaKRtaZri18.gif',
          priority: '100',
          classification: '奢侈品',
          num: '12',
          Recommend: '是',
          date: '2019-06-05 14:33:01'
        },
        {
          title: '耐克',
          logoImg: this.$imageHost + '/upload/7467397/image/20190507/crop_N7Fu7EaKRtaZri18.gif',
          num: '15',
          priority: '90',
          classification: '运动系列',
          Recommend: '否',
          date: '2019-06-06 14:33:01'
        },
        {
          title: '李宁',
          logoImg: this.$imageHost + '/upload/7467397/image/20190507/crop_FMNIeOeRfzwSURLz.png',
          priority: '70',
          num: '18',
          classification: '运动系列',
          Recommend: '是',
          date: '2019-06-07 14:33:01'
        }

      ],
      clickIindex: null,
      tbodyFlag: true,
      noImg: 'http://mpimg2.weipubao.cn/image/admin/no_data.png',
      page_one: false,
      currentPage1: 1,
      hiddle_1: true,
      bottomDivFlag: true,
      switchValue: '',
      showHiddleImgUrl: this.$imageHost + '/image/admin/brand_show2.jpg',
      showFlag: false,
      switchValueBottom: false
    }
  },
  props: ['turnIndex'],
  mounted () {
    console.log(this.turnIndex)
    if (this.turnIndex === 1) {
      this.activeName = 'second'
    }
    this.restaurants = this.loadAll()
  },
  methods: {
    // tap切换
    handleClick (tab, event) {
      console.log(tab, event)

      switch (tab.index) {
        case '0':
          this.hiddle_1 = true
          this.bottomDivFlag = true
          break
        case '1':
          this.hiddle_1 = false
          this.bottomDivFlag = true
          break
        case '2':
          this.bottomDivFlag = false
          break
      }
    },
    // 返回输入建议 参数queryString用户主动输入的内容
    querySearch (queryString, cb) {
      console.log(queryString)
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
    // 建议列表数据
    loadAll () {
      return [
        { 'value': '三全鲜食（北新泾店）', 'address': '长宁区新渔路144号' },
        { 'value': 'Hot honey 首尔炸鸡（仙霞路）', 'address': '上海市长宁区淞虹路661号' },
        { 'value': '新旺角茶餐厅', 'address': '上海市普陀区真北路988号创邑金沙谷6号楼113' },
        { 'value': '泷千家(天山西路店)', 'address': '天山西路438号' },
        { 'value': '胖仙女纸杯蛋糕（上海凌空店）', 'address': '上海市长宁区金钟路968号1幢18号楼一层商铺18-101' }

      ]
    },
    // 建议列表选中
    handleSelect (item) {
      console.log(item)
    },
    // 当前页发生变化
    handleCurrentChange () {

    },
    // 鼠标划入查看案例
    showOver () {
      this.showFlag = true
    },
    // 鼠标划出查看案例
    showLeave () {
      this.showFlag = false
    },
    // 添加品牌按钮
    handleAddBrand () {
      let obj = {
        index: 2,
        turnIndex: null
      }
      this.$emit('turnComponents', obj)
    }
  }
}
</script>
<style scoped lang='scss'>
.brandManagementContent {
  padding: 10px;
  padding-right: 23px;
  min-width: 1400px;
  font-size: 14px;
  height: 100%;
  position: relative;
}
.brandManagementContent_main,
.firstTab {
  position: relative;
  background-color: #fff;
  /* height: 100%; */
  overflow: hidden;
  overflow-y: auto;
  padding-bottom: 96px;
  padding: 10px;
}
.firstTab {
  margin-top: 10px;
}
.my-autocomplete {
  li {
    line-height: normal;
    padding: 7px;

    .name {
      text-overflow: ellipsis;
      overflow: hidden;
    }
    .addr {
      font-size: 12px;
      color: #b4b4b4;
    }

    .highlighted .addr {
      color: #ddd;
    }
  }
}
.topUl {
  display: flex;
  justify-content: flex-start;
}
.topUl li:nth-of-type(1) {
  margin-right: 37px;
}
.topUl li:nth-of-type(2) {
  margin-right: 37px;
}
.topUl li:nth-of-type(3) {
  margin-right: 37px;
}
.topBtn {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}
.tapTwo {
  margin-top: 10px;
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
.brandTr td:nth-of-type(1) {
  width: 398px;
}
.brandTr td:nth-of-type(2) {
  width: 132px;
}
.brandTr td:nth-of-type(3) {
  width: 132px;
}
.brandTr td:nth-of-type(4) {
  width: 132px;
}
.brandTr td:nth-of-type(5) {
  width: 132px;
}
.brandTr td:nth-of-type(6) {
  width: 132px;
}
.brandTr td:nth-of-type(7) {
  width: 200px;
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
// .content_two td:nth-of-type(2) {
//   width: 490px !important;
// }
tbody img {
  width: 50px;
}
.lastSpan {
  font-size: 12px;
  color: #5a8bff;
}
.lastSpan span {
  cursor: pointer;
}
.tapOneblock {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}
.tapOneblock span {
  height: 32px;
  line-height: 32px;
}
.firstNameClass {
  width: 403px !important;
}
.secondNameClass {
  width: 182px !important;
}
.threeNameClass {
  width: 152px !important;
}
.fourNameClass {
  width: 308px !important;
}
.showMain {
  margin-top: 30px;
  min-height: 370px;
}
.example {
  display: inline-block;
  color: #5a8bff;
  position: relative;
  cursor: pointer;
}

.hover_show {
  position: absolute;
  left: 68px;
  top: -45px;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  z-index: 100;
  box-shadow: 1px 1px 10px 5px #eee;
}
.hover_show:before {
  content: " ";
  position: absolute;
  top: 48px;
  left: -7px;
  width: 14px;
  height: 14px;
  background-color: #fff;
  transform: rotate(-45deg);
  z-index: 4;
  box-shadow: -3px -3px 3px #e5e5e5;
}
.hover_show img {
  width: 200px;
  height: 355.74px;
  border: 1px solid #eee;
}
</style>
<style>
.brandManagementContent .el-input {
  width: 140px !important;
}
.tapOneblock .el-pagination__editor {
  width: 70px !important;
}
.brandManagementContent .is-active {
  background-color: null !important;
}
.brandManagementContent .el-tabs__content {
  overflow: visible !important;
}
</style>
