<template>
  <div
    class="brandManagementContent"
    :class="hiddenOverFlag ? 'hiddenOverFlag' : ''"
  >
    <div class="brandManagementContent_main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          :label="$t('brandManagement.allBrands')"
          name="first"
        >
          <ul class="topUl">
            <li>
              {{$t('brandManagement.brandName')}}：
              <el-input
                v-model="state3"
                :placeholder="$t('brandManagement.inputPlaceText')"
                @select="handleSelect"
                size="small"
                clearable
                style="width: 170px;"
              >
                <template slot-scope="props">
                  <div class="name">{{ props.item.value }}</div>
                </template>
              </el-input>
            </li>
            <li>
              <div class="block">
                <span class="demonstration">{{$t('brandManagement.creatTime')}}：</span>
                <el-date-picker
                  v-model="brandStartTime"
                  size="small"
                  :placeholder="$t('brandManagement.selectDate')"
                >
                </el-date-picker>
                {{$t('brandManagement.to')}}
                <el-date-picker
                  v-model="brandEndTime"
                  size="small"
                  :placeholder="$t('brandManagement.selectDate')"
                  :picker-options="endTime"
                >
                </el-date-picker>
              </div>
            </li>
            <li>
              {{$t('brandManagement.brandClassify')}}：
              <el-select
                v-model="valueClss"
                size="small"
              >
                <el-option
                  v-for="item in optionsClss"
                  :key="item.classifyName"
                  :label="item.classifyName"
                  :value="item.classifyId"
                  :class="item.classifyId === 1 ? 'grandSelectClass' : ''"
                >
                </el-option>
              </el-select>
            </li>
            <li>
              {{$t('brandManagement.isRecommendBrand')}}：
              <el-select
                v-model="valueIsClss"
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
            <li>
              <el-button
                type="primary"
                size="small"
                @click="handleSXevent()"
              >{{$t('brandManagement.screen')}}</el-button>
            </li>
          </ul>
          <div class="topBtn">
            <el-button
              type="primary"
              size="small"
              @click="handleAddBrand()"
            >{{$t('brandManagement.addBrand')}}</el-button>
            <!-- <el-button
              type="primary"
              size="small"
              @click="handleSXevent()"
            >筛选</el-button> -->
          </div>
        </el-tab-pane>

        <el-tab-pane
          :label="$t('brandManagement.classifyName')"
          name="second"
        >
          <ul class="topUl">
            <li>
              {{$t('brandManagement.classifyName')}}：
              <el-input
                v-model="classifyName"
                size="small"
                :placeholder="$t('brandManagement.inputPlaceText')"
              ></el-input>
            </li>
            <li>
              <div class="block">
                <span class="demonstration">{{$t('brandManagement.creatTime')}}：</span>
                <el-date-picker
                  v-model="classifyBrandStartTime"
                  type="date"
                  size="small"
                  :placeholder="$t('brandManagement.selectDate')"
                >
                </el-date-picker>
                {{$t('brandManagement.to')}}
                <el-date-picker
                  v-model="classifyBrandEndTime"
                  type="date"
                  size="small"
                  :placeholder="$t('brandManagement.selectDate')"
                >
                </el-date-picker>
              </div>
            </li>
            <li>
              <el-button
                type="primary"
                size="small"
                @click="handleToScreen()"
              >{{$t('brandManagement.screen')}}</el-button>
            </li>
          </ul>
          <div class="tapTwo">
            <el-button
              type="primary"
              size="small"
              @click="handleBrandDialog()"
            >{{$t('brandManagement.addBrandClassify')}}</el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane
          :label="$t('brandManagement.brandDisplaySettings')"
          name="third"
        >
          <div class="showMain">
            <div class="showTop">
              {{$t('brandManagement.showTop')}}：
              <el-switch
                v-model="switchValue"
                active-color="#F7931E"
                inactive-color="#DDDDDD"
              >
              </el-switch>
              <span>{{ this.switchTextOne }}</span>
              <span style="color:#999">{{$t('brandManagement.switchTips')}}</span>
              <div
                class="example"
                @mouseover="showOver()"
                @mouseleave="showLeave()"
              >
                {{$t('brandManagement.viewExamples')}}
                <div
                  class="hover_show"
                  v-if="showFlag"
                >
                  <img :src="showHiddleImgUrl" />
                </div>
              </div>
            </div>
            <div
              class="showTop"
              style="margin-top:30px"
            >
              {{$t('brandManagement.recommendBrand')}}：
              <el-switch
                v-model="switchValueBottom"
                active-color="#F7931E"
                inactive-color="#DDDDDD"
              >
              </el-switch>
              <span>{{ this.switchTextSecond }}</span>
              <span style="color:#999">{{$t('brandManagement.switchTips')}}</span>
            </div>
            <!--隐藏模块-->
            <div v-if="hiddle_containerFlag">
              <div style="margin-top:20px;margin-left:75px">
                <div class="hiddleTitle">
                  {{$t('brandManagement.recommendTitle')}}：
                  <el-input
                    v-model="hiddleValTop"
                    :placeholder="$t('brandManagement.inputPlaceText')"
                    size="small"
                    style="width: 170px;"
                  ></el-input>
                </div>
                <div class="hiddleStyle"></div>
              </div>
              <div style="margin-top:20px;margin-left:75px">
                <div class="hiddleTitle specialDiv">
                  <div>{{$t('brandManagement.showStyle')}}：&nbsp;</div>
                  <div class="showDiv">
                    <div class="ra_div">
                      <el-radio
                        v-model="showRadio"
                        :label="$t('brandManagement.byBrandShow')"
                        text-color="#000"
                      ></el-radio>
                      <span style="color:#999"></span>
                      <div
                        class="example"
                        @mouseover="showOver(1)"
                        @mouseleave="showLeave(1)"
                      >
                        {{$t('brandManagement.viewExamples')}}
                        <div
                          class="hover_show"
                          v-if="showFlag_one"
                        >
                          <img :src="showHiddleImgUrl_one" />
                        </div>
                      </div>
                    </div>
                    <div class="ra_div">
                      <el-radio
                        v-model="showRadio"
                        :label="$t('brandManagement.byBrandClassifyShow')"
                        text-color="#000"
                      ></el-radio>
                      <span style="color:#999"></span>
                      <div
                        class="example"
                        @mouseover="showOver(2)"
                        @mouseleave="showLeave(2)"
                      >
                        {{$t('brandManagement.viewExamples')}}
                        <div
                          class="hover_show"
                          v-if="showFlag_two"
                        >
                          <img :src="showHiddleImgUrl_two" />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="hiddleStyle"></div>
              </div>
            </div>
            <div class="showBtn">
              <el-button
                type="primary"
                size="small"
                style="margin:30px 0 0 75px"
                @click="handleSaveTapThree()"
              >{{$t('brandManagement.save')}}</el-button>
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
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="trList"
          border
          style="width: 100%"
        >
          <el-table-column
            :label="secondGrandName"
            align="center"
          >
            <template slot-scope="scope">
              <p v-if="hiddle_1">{{ scope.row.brandName }}</p>
              <p v-if="!hiddle_1">{{ scope.row.classifyName }}</p>
            </template>
          </el-table-column>
          <el-table-column
            v-if="hiddle_1"
            :label="$t('brandManagement.brand')+'logo'"
            align="center"
          >
            <template slot-scope="scope">
              <img
                :src="scope.row.logo"
                alt=""
              >
            </template>
          </el-table-column>
          <el-table-column
            v-if="hiddle_1"
            prop="first"
            :label="$t('brandManagement.priority')"
            align="center"
          ></el-table-column>
          <el-table-column
            v-if="hiddle_1"
            prop="classifyName"
            :label="$t('brandManagement.brandClassify')"
            align="center"
          ></el-table-column>
          <el-table-column
            prop=""
            :label="$t('brandManagement.includeProductQuantity')"
            align="center"
          >
            <template slot-scope="scope">
              <p v-if="hiddle_1">{{ scope.row.goodsNum }}</p>
              <p v-if="!hiddle_1">{{ scope.row.brandNum }}</p>
            </template>
          </el-table-column>
          <el-table-column
            prop="first"
            :label="$t('brandManagement.classificationPriority')"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            :label="$t('brandManagement.creatTime')"
            align="center"
          ></el-table-column>
          <el-table-column
            :label="$t('brandManagement.operation')"
            align="center"
          >
            <template slot-scope="scope">
              <el-tooltip
                :content="$t('allGoodsLabel.update')"
                placement="top"
                v-if="hiddle_1"
              >
                <span
                  class="el-icon-edit-outline operateSpan"
                  @click="handleEditGoods(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('allGoodsLabel.delete')"
                placement="top"
                v-if="hiddle_1"
              >
                <span
                  class="el-icon-delete operateSpan"
                  @click="deleGrand(scope.row.id)"
                ></span>
              </el-tooltip>
              <span
                v-if="!hiddle_1"
                @click="handleToAddBrand(scope.row.classifyId)"
                style="color:#5a8bff;cursor:pointer"
              >添加品牌</span>
              <span
                @click="handlePagingEditGoods(scope.row)"
                v-if="!hiddle_1"
                style="color:#5a8bff;cursor:pointer"
              >编辑</span>
              <span
                @click="delePagingGrand(scope.row.classifyId)"
                v-if="!hiddle_1"
                style="color:#5a8bff;cursor:pointer"
              >删除</span>
            </template>
          </el-table-column>

        </el-table>
        <!--分页-->
        <pagination
          :page-params.sync="pageParams"
          @pagination="defaultAllBrandData"
        />
      </div>

    </div>
    <!--添加品牌分类弹窗-->
    <el-dialog
      :title="grandTitle"
      :visible.sync="dialogVisibleAddBrand"
      width="30%"
      :center="true"
    >
      <div class="dialogMain">
        <p>
          {{$t('brandManagement.brandClassifyName')}}：<el-input
            v-model="brandName"
            :placeholder="$t('brandManagement.inputPlaceText')"
            size="mini"
          ></el-input>
        </p>
        <p style="margin-top:10px">
          <span style="margin-right:11px">{{$t('brandManagement.classificationPriority')}}：</span>
          <el-input
            v-model="classificationName"
            :placeholder="$t('brandManagement.inputPlaceText')"
            size="mini"
          ></el-input>
        </p>
        <p>{{$t('brandManagement.dialogTips')}}</p>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisibleAddBrand = false">{{$t('brandManagement.cancel')}}</el-button>
        <el-button
          type="primary"
          @click="handleUpdateGrandClass()"
        >{{$t('brandManagement.sure')}}</el-button>
      </span>
    </el-dialog>
    <!--删除二次提醒-->
    <el-dialog
      :title="$t('brandManagement.dialogTwoTips')"
      :visible.sync="delDialogVisible"
      width="30%"
      :center="true"
    >
      <span>{{$t('brandManagement.dialogContent')}}</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="delDialogVisible = false">{{$t('brandManagement.cancel')}}</el-button>
        <el-button
          type="primary"
          @click="handleToTowDel()"
        >{{$t('brandManagement.sure')}}</el-button>
      </span>
    </el-dialog>
    <!--添加商品品牌弹窗-->
    <AddBrandDialog
      :callAddBrand.sync="brandDialogFlag"
      @handleToGetBackData="handleToGetBackData"
      :classification="classification"
      btnText="筛选"
    />
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import pagination from '@/components/admin/pagination/pagination'
import { saveShowBrandgetRequest, showBrandgetRequest, pagingBrandUpdateRequest, pagingBrandDelRequest, pagingBrandQueryRequest, brandAllGetRequest, brandDeleteGetRequest, classificationSelectRequest, addGrandClassRequest } from '@/api/admin/brandManagement.js'
// 工具导入
import { startOrEndDayWithFormat } from '@/util/date'
export default {
  components: { pagination,
    AddBrandDialog: () => import('@/components/admin/addBrandDialog')
  },
  data () {
    return {
      // 结束时间校验
      endTime: {
        disabledDate: time => {
          return time.getTime() < this.brandStartTime
        }
      },
      pageParams: {}, // 分页
      upDateClassifyId: '',
      activeName: 'first',
      restaurants: [],
      state3: '',
      value9: '',
      optionsClss: [],
      valueClss: '',
      valueIsClss: '',
      trList: [], // 表格数据
      clickIindex: null,
      tbodyFlag: true,
      noImg: this.$imageHost + '/image/admin/no_data.png',
      page_one: false,
      currentPage1: 1,
      hiddle_1: true,
      bottomDivFlag: true,
      switchValue: false,
      showHiddleImgUrl: this.$imageHost + '/image/admin/brand_show2.jpg',
      showHiddleImgUrl_one: this.$imageHost + '/image/admin/brand_show1.jpg',
      showHiddleImgUrl_two: this.$imageHost + '/image/admin/brand_show3.jpg',
      showFlag: false,
      switchValueBottom: false,
      dialogVisibleAddBrand: false,
      brandName: '',
      classificationName: '',
      totalRows: null,
      switchTextOne: '',
      switchTextSecond: '',
      hiddleValTop: '推荐标题',
      showRadio: '按品牌展示',
      showFlag_one: false,
      showFlag_two: false,
      hiddenOverFlag: false,
      hiddle_containerFlag: false,
      grandTitle: '',
      classifyName: '', //  分类名称
      createTime: '', // 创建时间
      timeValue2: '', // 分类名称页面中的时间
      addClassOrEdit: true, // 分类名称tap里面区分是添加商品分类还是编辑
      brandStartTime: '', // 全部品牌tap起始时间
      brandEndTime: '', // 全部品牌tap终止时间
      classifyBrandStartTime: '', // 分类名称tap页面初始时间
      classifyBrandEndTime: '', // 分类名称tap页面终止时间
      delDialogVisible: false, // 删除二次提醒弹窗flag
      delFlag: true,
      brandId: null,
      classifyId: null,
      secondGrandName: '',
      brandDialogFlag: false,
      classification: null
    }
  },
  props: ['turnIndex'],
  watch: {
    switchValue (newData) {
      switch (newData) {
        case true:
          this.switchTextOne = this.alreadyOpened
          break
        case false:
          this.switchTextOne = this.closed
          break
      }
    },
    switchValueBottom (newData) {
      switch (newData) {
        case true:
          this.switchTextSecond = this.alreadyOpened
          this.hiddle_containerFlag = true
          break
        case false:
          this.switchTextSecond = this.closed
          this.hiddle_containerFlag = false
          break
      }
    },
    createTime (newData) {
      console.log(newData)
    }
  },
  computed: {
    // secondGrandName () {
    //   return this.$t('brandManagement.brandName')
    // },
    optionsIsClss () {
      return this.$t('brandManagement.optionsIsClss')
    },
    alreadyOpened () {
      return this.$t('brandManagement.alreadyOpened')
    },
    closed () {
      return this.$t('brandManagement.closed')
    }
  },
  mounted () {
    console.log(this.$route)
    this.restaurants = this.loadAll()
    if (this.$route.params.toSecond) {
      this.activeName = 'second'
      let tab = { index: '1' }
      this.handleClick(tab)
    } else {
      // 初始化全部商品数据
      this.defaultAllBrandData()
      console.log(this.turnIndex)
      if (this.turnIndex === 1) {
        this.activeName = 'second'
        this.hiddle_1 = false
        this.bottomDivFlag = true
        this.secondGrandName = '分类名称'
        // 初始化品牌分类页数据
        this.defaultPageingGrand()
      }
    }
  },
  methods: {
    ...mapActions(['changeCrumbstitle', 'transmitEditGoodsId']),
    defaultAllBrandData () {
      let obj = {}
      obj.currentPage = this.pageParams.currentPage
      obj.pageRows = this.pageParams.pageRows
      // 初始化全部品牌表格数据
      brandAllGetRequest(obj).then((res) => {
        if (res.error === 0) {
          this.trList = res.content.dataList
          this.pageParams = res.content.page
          this.totalRows = res.content.page.totalRows
          this.$nextTick(() => {
            this.tbodyFlag = true
          })
        }
        if (res.content.page.totalRows === 0) {
          this.tbodyFlag = false
        }
        console.log(res)
      })
      // 品牌分类下拉框数据请求
      classificationSelectRequest().then((res) => {
        if (res.error === 0) {
          console.log(res.content)
          res.content.unshift({ classifyName: this.$t('brandManagement.whole'), classifyId: '' })
          this.optionsClss = res.content
        }

        console.log(res)
      })

      let arr = ['商品管理', '品牌管理']
      this.changeCrumbstitle(arr)
    },
    // 调起添加品牌弹窗
    handleToAddBrand (id) {
      this.classification = id
      this.brandDialogFlag = true
    },
    // 商品品牌弹窗回传数据
    handleToGetBackData (res) {
      console.log(res)
      this.defaultPageingGrand()
    },
    // tap切换
    handleClick (tab, event) {
      console.log(tab, event)
      switch (tab.index) {
        case '0':
          this.secondGrandName = '品牌名称'
          this.trList = []
          this.defaultAllBrandData()
          this.$nextTick(() => {
            this.hiddenOverFlag = false
            this.bottomDivFlag = true
            this.hiddle_1 = true
          })

          break
        case '1':
          this.secondGrandName = '分类名称'
          this.trList = []
          // 初始化品牌分类页数据
          this.defaultPageingGrand()
          this.$nextTick(() => {
            this.hiddenOverFlag = false
            this.bottomDivFlag = true
            this.hiddle_1 = false
          })
          break
        case '2':
          // 初始化tap3数据
          this.defaulttapThreeData()
          this.$nextTick(() => {
            this.bottomDivFlag = false
            this.hiddenOverFlag = true
          })
          break
      }
    },
    defaultPageingGrand () {
      console.log(startOrEndDayWithFormat(this.classifyBrandStartTime, true), startOrEndDayWithFormat(this.classifyBrandEndTime, false))
      let obj = {
        classifyName: this.classifyName,
        startAddTime: startOrEndDayWithFormat(this.classifyBrandStartTime, true),
        endAddTime: startOrEndDayWithFormat(this.classifyBrandEndTime, false),
        currentPge: 1,
        pageRows: 20
      }
      pagingBrandQueryRequest(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.trList = res.content.dataList
          this.totalRows = res.content.page.totalRows
          this.tbodyFlag = true
          if (res.content.page.totalRows === 0) {
            console.log(1)
            this.tbodyFlag = false
          }
        }
      })
    },
    // 品牌分类展示设置
    defaulttapThreeData () {
      console.log('初始化品牌展示设置测试')
      showBrandgetRequest().then((res) => {
        if (res.error === 0) {
          this.hiddleValTop = res.content.recommendTitle
          switch (res.content.showAllBrand) {
            case 0:
              this.switchValue = false
              break
            case 1:
              this.switchValue = true
          }
          switch (res.content.showRecommendBrandType) {
            case 0:
              this.switchValueBottom = false
              break
            case 2:
              this.switchValueBottom = true
              this.showRadio = '按品牌展示'
              break
            case 3:
              this.switchValueBottom = true
              this.showRadio = '按品牌分类展示'
          }
        }
        console.log(res.content)
      })
    },
    // 品牌分类tap页品牌分类编辑
    handlePagingEditGoods (data) {
      console.log(data)
      this.addClassOrEdit = false
      this.upDateClassifyId = data.classifyId
      this.brandName = data.classifyName
      this.classificationName = data.first

      this.dialogVisibleAddBrand = true
      this.grandTitle = '修改品牌分类'
    },
    // 品牌分类tap页品牌分类删除
    delePagingGrand (classifyId) {
      this.classifyId = classifyId
      this.delFlag = false
      this.delDialogVisible = true

      console.log(classifyId)
    },
    // 品牌分类弹窗确定事件
    handleUpdateGrandClass () {
      console.log(this.classificationName)
      let obj = {}
      if (this.addClassOrEdit) {
        obj = {
          classifyName: this.brandName,
          first: Number(this.classificationName)
        }
        addGrandClassRequest(obj).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message.success({
              message: '添加成功',
              type: 'success'
            })
            this.defaultPageingGrand()
            this.dialogVisibleAddBrand = false
          }
          if (res.error === 131004) {
            this.$message.error({
              message: '品牌分类名称已存在',
              type: 'success'
            })
          }
        })
      } else {
        obj = {
          classifyId: this.upDateClassifyId,
          classifyName: this.brandName,
          first: Number(this.classificationName)
        }
        pagingBrandUpdateRequest(obj).then((res) => {
          if (res.error === 0) {
            this.defaultPageingGrand()
            this.$message.success({
              message: '修改成功',
              type: 'success'
            })
            this.dialogVisibleAddBrand = false
          }
          console.log(res)
        })
      }
    },
    // tap3保存事件
    handleSaveTapThree () {
      let showAllBrand = ''
      let showRcommendBrandType = ''
      if (this.switchValue === true) {
        showAllBrand = 1
      } else {
        showAllBrand = 0
      }
      if (this.switchValueBottom === false) {
        showRcommendBrandType = 0
      } else if (this.showRadio === '按品牌展示') {
        showRcommendBrandType = 2
      } else if (this.showRadio === '按品牌分类展示') {
        showRcommendBrandType = 3
      }

      let obj = {
        recommendTitle: this.hiddleValTop,
        showAllBrand: showAllBrand,
        showRecommendBrandType: showRcommendBrandType
      }
      saveShowBrandgetRequest(obj).then((res) => {
        if (res.error === 0) {
          this.$message.success({
            message: '保存成功',
            type: 'success'
          })
        }
        console.log(res)
      })
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
    // 当前页发生变化defaultPageingGrand
    handleCurrentChange () {
      console.log(this.currentPage1)
      let obj = {
        'currentPage': this.currentPage1,
        'pageRows': 20
      }
      brandAllGetRequest(obj).then((res) => {
        this.trList = res.content.dataList
        this.totalRows = res.content.page.totalRows
        console.log(res)
      })
    },
    // 鼠标划入查看案例
    showOver (index) {
      switch (index) {
        case 1:
          this.showFlag_one = true
          break
        case 2:
          this.showFlag_two = true
          break
      }
      if (index !== 1 && index !== 2) this.showFlag = true
    },
    // 鼠标划出查看案例
    showLeave (index) {
      switch (index) {
        case 1:
          this.showFlag_one = false
          break
        case 2:
          this.showFlag_two = false
          break
      }
      if (index !== 1 || index !== 2) this.showFlag = false
    },
    // 添加品牌按钮
    handleAddBrand () {
      console.log(1)
      this.$router.push({
        name: 'addBrand'
      })
      this.transmitEditGoodsId('add')
      let obj = {
        index: 2,
        turnIndex: null
      }
      this.$emit('turnComponents', obj)
    },
    // 调用添加品牌分类弹窗
    handleBrandDialog () {
      this.addClassOrEdit = true
      this.grandTitle = '添加品牌分类'
      this.brandName = ''
      this.classificationName = ''
      this.dialogVisibleAddBrand = true
    },
    // 删除品牌
    deleGrand (id) {
      console.log(id)
      this.brandId = id
      this.delFlag = true
      this.delDialogVisible = true
    },
    // 二次删除弹窗确认事件
    handleToTowDel () {
      if (this.delFlag) {
        brandDeleteGetRequest(this.brandId).then((res) => {
          console.log(res)
          if (res.error === 0) {
            this.$message.success({
              message: '删除成功',
              type: 'success'
            })
            this.handleSXevent()
          }
        })
      } else {
        pagingBrandDelRequest(this.classifyId).then((res) => {
          console.log(res)
          if (res.error === 0) {
            this.$message.success({
              message: '删除成功',
              type: 'success'
            })
            this.defaultPageingGrand()
          }
        })
      }
      this.delDialogVisible = false
    },
    // 筛选
    handleSXevent () {
      let obj = {
        'brandName': this.state3,
        'startAddTime': startOrEndDayWithFormat(this.brandStartTime, true),
        'endAddTime': startOrEndDayWithFormat(this.brandEndTime, false),
        'classifyId': this.valueClss,
        'isRecommend': this.valueIsClss,
        'currentPage': 1,
        'pageRows': 20
      }
      console.log(obj)
      brandAllGetRequest(obj).then((res) => {
        if (res.error === 0) {
          this.trList = res.content.dataList
          this.totalRows = res.content.page.totalRows
          this.tbodyFlag = true
          if (res.content.page.totalRows === 0) {
            console.log(1)
            this.tbodyFlag = false
          }
        }
        console.log(res)
      })
    },
    // 点击编辑
    handleEditGoods (id) {
      this.$router.push({
        name: 'addBrand'
      })
      console.log(id, 11111)
      this.transmitEditGoodsId(id)
      let obj = {
        index: 2,
        turnIndex: null
      }
      this.$emit('turnComponents', obj)
    },
    // 品牌分类点击筛选
    handleToScreen () {
      this.defaultPageingGrand()
    }
  }
}
</script>
<style scoped lang='scss'>
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.showDiv {
  display: flex;
  flex-direction: column;
  height: 50px;
  justify-content: space-between;
}
.specialDiv {
  display: flex;
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
.grandSelectClass {
  padding-left: 30px;
}
.zwiclass {
  height: 1px;
  width: 10px;
}
.brandManagementContent {
  padding: 10px;
  // padding-right: 23px;
  min-width: 1000px;
  font-size: 14px;
  // height: 100%;
  position: relative;
}
.brandManagementContent_main,
.firstTab {
  position: relative;
  background-color: #fff;
  height: 100%;
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
  flex-wrap: wrap;
}
.topUl li {
  white-space: nowrap;
  margin-bottom: 10px;
}
.topUl li:nth-of-type(1) {
  margin-right: 10px;
}
.topUl li:nth-of-type(2) {
  margin-right: 10px;
}
.topUl li:nth-of-type(3) {
  margin-right: 10px;
}
.topUl li:nth-of-type(4) {
  margin-right: 10px;
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
  width: 20%;
}
.brandTr td:nth-of-type(2) {
  width: 10%;
}
.brandTr td:nth-of-type(3) {
  width: 10%;
}
.brandTr td:nth-of-type(4) {
  width: 10%;
}
.brandTr td:nth-of-type(5) {
  width: 10%;
}
.brandTr td:nth-of-type(6) {
  width: 10%;
}
.brandTr td:nth-of-type(7) {
  width: 15%;
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
.dialogMain {
  margin: 30px 30px 0 30px;
}
.dialogMain p:nth-of-type(3) {
  margin-left: 97px;
  margin-top: 10px;
  line-height: 30px;
  color: #999;
}
.hiddenOverFlag {
  height: 100%;
}
</style>
<style lang="scss" scoped>
.dialogMain {
  /deep/ .el-input {
    width: 200px;
  }
}
</style>
<style>
.topUl .el-input {
  width: 170px;
}
.tapOneblock .el-pagination__editor {
  width: 70px !important;
}
.brandManagementContent .el-tabs__content {
  overflow: visible !important;
}
.is-active {
  background-color: null !important;
}
.brandManagementContent_main .el-dialog__header {
  background-color: #f3f3f3 !important;
  text-align: center !important;
}
.ra_div .el-radio {
  margin-right: 0 !important;
}
.operateSpan {
  font-size: 22px;
  color: #5a8bff;
  cursor: pointer !important;
}
</style>
