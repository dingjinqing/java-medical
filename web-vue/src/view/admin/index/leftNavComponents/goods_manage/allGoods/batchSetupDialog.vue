<template>
  <div class="batchSetup">
    <el-dialog
      title="商品管理/出售中/批量设置"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      width="53%"
      :before-close="handleToCloseDialog"
    >
      <div class="batchSetupMain">
        <ul class="left">
          <li
            v-for="(item,index) in liData"
            :key="index"
            @click="handleClickLeftLi(index)"
          >
            <span :class="nowIndex===index?'hover':''">{{item}}</span>
          </li>
        </ul>
        <div class="right">
          <div class="br_title">
            已选：{{checkGoodsData.length}}件商品<i>最多可批量设置20件商品</i>
          </div>
          <!--右侧动态内容-->
          <div class="dynamic">
            <!--商品价格-->
            <div
              v-if="nowIndex===0"
              class="price"
            >
              <div class="title">批量设置：<i>折扣</i><i>涨/降价</i><i>改价金额</i><span>填写"负值"即降价</span></div>
              <div class="bottom">
                <el-table
                  class="version-manage-table"
                  header-row-class-name="tableClss"
                  :data="goodsPriceShowData"
                  border
                  style="width: 100%"
                >
                  <el-table-column
                    prop="goodsName"
                    label="商品规格"
                    align="center"
                    width="160"
                  >
                    <template slot-scope="scope">
                      <div class="goodsDiv">
                        <div class="item_goods_img">
                          <img :src="scope.row.goodsImg">
                        </div>
                        <div class="goods_message">
                          <div class="item_goods_name">{{scope.row.goodsName}}</div>
                          <div class="item_goods_desc">
                            <span>{{scope.row.prdDesc}}</span>
                          </div>
                        </div>

                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="shopPrice"
                    label="原价"
                    align="center"
                  >

                  </el-table-column>
                  <el-table-column
                    label="折扣"
                    align="center"
                    prop="discountInputVal"
                  >
                    <template slot-scope="scope">
                      <div>
                        <div class="discount">
                          <el-input
                            v-model="scope.row.discountInputVal"
                            size="small"
                            @blur="handleToBlur(scope,0)"
                            onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                          ></el-input>&nbsp;&nbsp;折
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="goodsName"
                    label="涨/降价"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <div>
                        <div class="discount">
                          <el-input
                            v-model="scope.row.priceIncreaseVal"
                            size="small"
                            @blur="handleToBlur(scope,1)"
                            onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                          ></el-input>&nbsp;&nbsp;元
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="goodsName"
                    label="改价金额"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <div>
                        <div class="discount">
                          <el-input
                            @blur="handleToBlur(scope,2)"
                            v-model="scope.row.priceRevisionVal"
                            size="small"
                            onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                          ></el-input>&nbsp;&nbsp;元
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </div>

            </div>
            <!--商家分类-->
            <div
              v-if="nowIndex===1"
              class="classification"
            >
              <sortCatTreeSelect
                ref="sortTree"
                :filterGoodsInfo="initSortCatParams"
                treeType="sort"
                :selectedId.sync="sortId"
                :labelStyle="true"
              />
            </div>
            <!--运费模板-->
            <div
              v-if="nowIndex===2"
              class="template"
            >
              <div class="title">
                <div class="name">运费模板：</div>
                <el-select
                  v-model="templateValue"
                  size="small"
                >
                  <el-option
                    v-for="item in templateOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
                <div class="rightOptions">
                  <span @click="handleToClickTemplate(0)">刷新</span>|<span
                    @click="handleToClickTemplate(1)"
                    style="width:80px"
                  >新建模板</span>|<span
                    @click="handleToClickTemplate(2)"
                    style="width:80px"
                  >模板管理</span>
                </div>
              </div>
              <!--选中运费模板后显示模块-->
              <div
                class="hiddenTemplate"
                v-if="templateValue!==null"
              >
                <div class="content">
                  <div class="top">
                    <span>店铺统一运费：0元</span>
                    <span class="toDetail">查看详情</span>
                  </div>
                  <div
                    class="bottomContent"
                    v-if="templateValue===1"
                  >
                    <div class="title">指定条件包邮可配送区域运费:</div>
                    <div class="hiddencontent">太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮</div>
                  </div>
                  <div
                    class="bottomContent"
                    v-if="templateValue===1"
                  >
                    <div class="title">指定条件包邮可配送区域运费:</div>
                    <div class="hiddencontent">：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：</div>
                  </div>
                </div>

              </div>
            </div>
            <!--限购数量-->
            <div
              v-if="nowIndex===3"
              class="limitNum"
            >
              <div class="content">
                <span>最小限购数量：</span>
                <el-input
                  v-model="MinPurchaseInputVal"
                  size="small"
                  onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                ></el-input>
                <i>0或不填表示不限制购买数量</i>
              </div>
              <div
                class="content"
                style="margin-top:10px"
              >
                <span>最大限购数量：</span>
                <el-input
                  v-model="MaxPurchaseInputVal"
                  size="small"
                  onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                ></el-input>
                <i>0或不填表示不限制购买数量</i>
              </div>
            </div>
            <!--上架时间-->
            <div
              v-if="nowIndex===4"
              class="onSaleTime"
            >
              <div class="onSaleTimeLeft">
                上下架：
              </div>
              <div class="onSaleTimeRight">
                <el-radio
                  v-model="onSaleRadio"
                  label="1"
                >不修改</el-radio>
                <el-radio
                  v-model="onSaleRadio"
                  label="2"
                >立即上架售卖</el-radio>
                <div class="custom">
                  <el-radio
                    v-model="onSaleRadio"
                    label="3"
                  >自定义上架售卖</el-radio>
                  <div class="tips">
                    <el-date-picker
                      size="small"
                      v-model="customTime"
                      type="datetime"
                      placeholder="选择日期时间"
                      default-time="12:00:00"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                    >
                    </el-date-picker>
                    <span style="margin-left:10px">选择上架售卖时间</span>
                  </div>

                </div>

                <el-radio
                  v-model="onSaleRadio"
                  label="4"
                >暂不售卖，放入仓库</el-radio>
              </div>
            </div>
            <!--商品详情-->
            <div
              v-if="nowIndex===5 || nowIndex===7"
              class="commodityDetails"
            >
              <div
                class="temPosition"
                v-if="nowIndex===5"
              >
                <span>模板位置：</span>
                <el-radio
                  v-model="goodsRadio"
                  label="1"
                >不修改</el-radio>
                <el-radio
                  v-model="goodsRadio"
                  label="2"
                >自定义内容在上</el-radio>
                <el-radio
                  v-model="goodsRadio"
                  label="3"
                >商品详情在上</el-radio>
              </div>
              <div class="customContent">
                <div class="customTitle">
                  {{nowIndex===5?'自定义内容：':'商品品牌：'}}
                </div>
                <div class="customMiddle">
                  <div style="color:#999">设置商品详情页显示的自定义内容</div>
                  <div class="customFooter">
                    <el-button
                      size="small"
                      @click="handleToClickCustom(0)"
                    >{{nowIndex===5?'选择模板':'选择品牌'}}</el-button>
                    <div class="rightContent">
                      <span @click="handleToClickCustom(1)">刷新</span>|<span
                        @click="handleToClickCustom(2)"
                        style="width:80px"
                      >{{nowIndex===5?'添加模板':'添加品牌'}}</span>|<span
                        @click="handleToClickCustom(3)"
                        style="width:80px"
                      >{{nowIndex===5?'管理模板':'管理品牌'}}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!--商品详情和商品品牌公共表格-->
            <div v-if="(nowIndex===5&&isShowCommonTableFive) || (nowIndex===7&&isShowCommonTableSeven)">
              <div class="tatle">
                <div class="tableLeft">
                  <span>{{nowIndex===5?'页面名称':'品牌名称'}}</span>
                  <el-input
                    v-if="nowIndex===5"
                    size="small"
                    v-model="tableInput[0]"
                    :placeholder="'请输入页面名称'"
                  ></el-input>
                  <el-input
                    v-if="nowIndex===7"
                    size="small"
                    v-model="tableInput[1]"
                    :placeholder="'请输入品牌名称'"
                  ></el-input>
                </div>
                <div class="tableRight">
                  <span>{{nowIndex===5?'页面分类':'品牌分类'}}</span>
                  <el-select
                    size="small"
                    v-if="nowIndex===5"
                    v-model="commonTableValue[0]"
                  >
                    <el-option
                      v-for="item in commonTableOptionsFive"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                  <el-select
                    size="small"
                    v-if="nowIndex===7"
                    v-model="commonTableValue[1]"
                  >
                    <el-option
                      v-for="item in commonTableOptionsSeven"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                  <el-button
                    size="small"
                    type="primary"
                  >搜索</el-button>
                </div>
              </div>
              <!--表格-->
              <div
                class="commonTable"
                v-if="nowIndex===5"
                :key="1"
              >
                <el-table
                  class="version-manage-table"
                  header-row-class-name="tableClss"
                  :data="commonTableDataFive"
                  border
                  style="width: 100%"
                >
                  <el-table-column
                    prop="name"
                    label="页面名称"
                    align="center"
                  >
                  </el-table-column>
                  <el-table-column
                    prop="time"
                    label="创建时间"
                    align="center"
                  >
                  </el-table-column>
                  <el-table-column
                    prop="isFirst"
                    label="是否首页"
                    align="center"
                  >
                  </el-table-column>
                </el-table>
              </div>
              <div
                class="commonTable"
                v-if="nowIndex===7"
                :key="2"
              >
                <el-table
                  class="version-manage-table"
                  header-row-class-name="tableClss"
                  :data="commonTableDataSeven"
                  border
                  style="width: 100%"
                >
                  <el-table-column
                    prop="name"
                    label="品牌名称"
                    align="center"
                  >
                  </el-table-column>
                  <el-table-column
                    prop="classify"
                    label="品牌分类"
                    align="center"
                  >
                  </el-table-column>
                  <el-table-column
                    prop="time"
                    label="创建时间"
                    align="center"
                  >
                  </el-table-column>
                </el-table>
              </div>

            </div>
            <!--商品标签-->
            <div
              class="commodityDetails"
              v-if="nowIndex===6"
            >
              <div class="customContent">
                <div class="customMiddle">
                  <div
                    class="customFooter"
                    style="margin-top:0"
                  >
                    <div class="label">商品标签：</div>
                    <el-select
                      v-model="labelValue"
                      size="small"
                      @change="handleToSelect"
                    >
                      <el-option
                        v-for="item in labelOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                    </el-select>
                    <div
                      class="rightContent"
                      style="width:auto"
                    >
                      <span @click="handleToClickCustom(1)">刷新</span>|<span
                        @click="handleToClickCustom(2)"
                        style="padding:0 10px;white-space:nowrap;display: inline-block;width:auto"
                      >新建商品标签</span>|<span
                        @click="handleToClickCustom(3)"
                        style="padding:0 10px;white-space:nowrap;display: inline-block;width:auto"
                      >管理商品标签</span>
                    </div>
                  </div>
                </div>
              </div>
              <!--选中商品标签后显示模块-->
              <div
                class="showLabelContent"
                v-if="labelValueCheckArr.length"
              >
                <div class="labelLeft">
                  已选：
                </div>
                <div class="labelRight">
                  <div
                    v-for="(item,index) in labelValueCheckArr"
                    :key="index"
                    class="list"
                  >{{item.label}}<img
                      @click="handleToClickDel(index,item)"
                      :src="$imageHost+'/image/admin/icon_delete.png'"
                      class="label-delete"
                    ></div>

                </div>
              </div>
            </div>
            <!--会员专享-->
            <div
              v-if="nowIndex===8"
              class="membershipExclusive"
            >
              <div class="membershipTop">
                <div style="margin-bottom:20px">会员专享商品：</div>
                <div>
                  <el-radio
                    v-model="membershipTopRadio"
                    label="1"
                  >将已选商品设置为会员专享商品</el-radio>
                  <el-radio
                    v-model="membershipTopRadio"
                    label="2"
                  >将已选会员专享商品设置为普通商品</el-radio>
                </div>
              </div>
              <div
                class="membershipBottom"
                v-if="membershipTopRadio==='1'"
              >
                <div class="membershipTitle">
                  <span>设置会员卡(非必选)</span>
                  <i>用户持有指定会员卡才可以购买已选商品</i>
                </div>
                <div class="membershipMiddle">
                  <el-select
                    v-model="membershipValue"
                    size="small"
                    @change="handleToMemberSelect"
                  >
                    <el-option
                      v-for="item in membershipOptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                  <div
                    class="rightContent"
                    style="width:auto;color:#5a8bff"
                  >
                    <span
                      @click="handleToClickCustom(1)"
                      style="padding:0 10px"
                    >刷新</span>|<span
                      @click="handleToClickCustom(2)"
                      style="padding:0 10px;white-space:nowrap;display: inline-block;width:auto"
                    >新建会员卡</span>|<span
                      @click="handleToClickCustom(3)"
                      style="padding:0 10px;white-space:nowrap;display: inline-block;width:auto"
                    >管理会员卡</span>
                  </div>
                </div>
                <div style="color:#999">注：设置会员卡只增加可购买已选商品的会员卡数量，不会修改商品已设置的会员卡。</div>
                <!--选中会员卡后显示模块-->
                <div
                  class="showLabelContent"
                  v-if="membershipValueCheckArr.length"
                >
                  <div class="labelLeft">
                    已选：
                  </div>
                  <div class="labelRight">
                    <div
                      v-for="(item,index) in membershipValueCheckArr"
                      :key="index"
                      class="list"
                    >{{item.label}}<img
                        @click="handleToClickMemberDel(index,item)"
                        :src="$imageHost+'/image/admin/icon_delete.png'"
                        class="label-delete"
                      ></div>

                  </div>
                </div>
              </div>
            </div>
            <!--发货地-->
            <div
              v-if="nowIndex===9"
              class="placeOfDelivery"
            >
              <div class="placeOfDeliveryContent">
                <div class="name">发货地：</div>
                <el-input
                  v-model="placeOfDeliveryInput"
                  :maxlength="15"
                  placeholder="最多设置15字"
                  size="small"
                ></el-input>
              </div>
            </div>
          </div>
          <!--右侧动态内容end-->
        </div>
      </div>
      <!--编辑离开提示弹窗-->
      <el-dialog
        width="30%"
        title="提醒"
        :visible.sync="innerVisible"
        append-to-body
      >
        <span style="width:100%;text-align:center;display: block">确定要退出编辑吗？</span>
        <div slot="footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button @click="handleToCloseInnerDialog()">确定</el-button>
        </div>
      </el-dialog>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="handleToSave()"
        >保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getGoodsInfosByGoodIds } from '@/api/admin/goodsManage/allGoods/allGoods'
export default {
  components: {
    sortCatTreeSelect: () => import('@/components/admin/sortCatTreeSelect') // 商家分类
  },
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    },
    checkGoodsData: { // 已选中的商品
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      liData: ['商品价格', '商家分类', '运费模板', '限购数量', '上架时间', '商品详情', '商品标签', '商品品牌', '会员专属', '发货地'],
      nowIndex: 0,
      innerVisible: false, // 内层
      judgeIsEdit: false, // 判断商品价格是否被修改过
      initSortCatParams: {
        needGoodsNum: true,
        isOnSale: 1,
        isSaleOut: false,
        selectType: 1
      },
      sortId: null,
      templateOptions: [{
        value: null,
        label: '不修改'
      }, {
        value: 0,
        label: '腾飞测试1'
      }, {
        value: 1,
        label: '腾飞测试2'
      }],
      templateValue: null, // 运费模板selectVal
      MinPurchaseInputVal: '', // 最小限购数量
      MaxPurchaseInputVal: '', // 最大限购数量
      onSaleRadio: '1', // 上架时间radio
      customTime: '', // 自定义上架售卖时间
      goodsRadio: '1', // 商品详情头部radio
      isShowCommonTableFive: false, // 商品详情和商品品牌公共表格显示
      tableInput: ['', ''], // 公共表格表头input值
      commonTableValue: [1, ''], // 商品详情和商品品牌公共selectVal
      commonTableOptionsFive: [{ // 商品详情和商品品牌公共selectOptions
        value: -1,
        label: '腾飞测试1'
      }, {
        value: 1,
        label: '腾飞测试2'
      }],
      commonTableOptionsSeven: [{ // 商品详情和商品品牌公共selectOptions
        value: -1,
        label: '腾飞测试1'
      }, {
        value: 1,
        label: '腾飞测试2'
      }],
      commonTableDataFive: [
        {
          time: '2016-05-02 12:00:00',
          name: '王小虎1',
          isFirst: '是'
        }, {
          time: '2016-05-02 12:00:00',
          name: '王小虎2',
          isFirst: '是'
        }, {
          time: '2016-05-02 12:00:00',
          name: '王小虎3',
          isFirst: '是'
        }, {
          time: '2016-05-02 12:00:00',
          name: '王小虎4',
          isFirst: '是'
        }
      ], // 公共表格数据
      commonTableDataSeven: [
        {
          time: '2016-05-02 12:00:00',
          name: '王小虎1',
          classify: '运动系列'
        }, {
          time: '2016-05-02 12:00:00',
          name: '王小虎2',
          classify: '运动系列'
        }, {
          time: '2016-05-02 12:00:00',
          name: '王小虎3',
          classify: '运动系列'
        }, {
          time: '2016-05-02 12:00:00',
          name: '王小虎4',
          classify: '运动系列'
        }
      ],
      isShowCommonTableSeven: false, // 控制显示
      labelValue: -1,
      labelOptions: [
        {
          value: -1,
          label: '请选择商品标签'
        },
        {
          value: 1,
          label: '腾飞测试1'
        },
        {
          value: 2,
          label: '测试1'
        },
        {
          value: 3,
          label: '测试22'
        },
        {
          value: 4,
          label: '测试222'
        },
        {
          value: 5,
          label: '测试23'
        },
        {
          value: 6,
          label: '测试244444'
        }
      ],
      labelValueCheckArr: [], // 商品标签下拉框选中集合
      membershipTopRadio: '', // 会员专享radio
      membershipValue: -1,
      membershipOptions: [
        {
          value: -1,
          label: '请选择会员卡'
        },
        {
          value: 1,
          label: '腾飞测试1'
        },
        {
          value: 2,
          label: '测试1'
        },
        {
          value: 3,
          label: '测试22'
        },
        {
          value: 4,
          label: '测试222'
        },
        {
          value: 5,
          label: '测试23'
        },
        {
          value: 6,
          label: '测试244444'
        }
      ],
      membershipValueCheckArr: [], // 会员专享下拉框选中集合
      placeOfDeliveryInput: '', // 发货地
      goodsPriceShowData: [] // 商品价格模块渲染数据
    }
  },
  watch: {
    dialogVisible (newData) {
      console.log(newData)
      if (newData) {
        console.log(this.checkGoodsData)
        this.checkGoodsData.forEach((item, index) => {
          this.$set(this.checkGoodsData[index], 'priceRevisionVal', '')
          this.$set(this.checkGoodsData[index], 'priceIncreaseVal', '')
          this.$set(this.checkGoodsData[index], 'discountInputVal', '')
        })
        this.judgeIsEdit = false
        this.handleToInit()
      }
    },
    customTime (newData) {
      console.log(newData)
    }
  },
  mounted () {
    console.log(new Date().format('yyyy-MM-dd hh:mm:ss'))
  },
  methods: {
    // 初始请求数据
    handleToInit () {
      let params = []
      this.checkGoodsData.forEach((item, index) => {
        params.push(item.goodsId)
      })
      getGoodsInfosByGoodIds({ goodsIds: params }).then((res) => {
        console.log(res)
        if (res.error === 0) {
          res.content.forEach((item, index) => {
            item.goodsSpecProducts.forEach((itemC, indexC) => {
              console.log(itemC)
              let obj = Object.assign(item, itemC)
              console.log(item, itemC, obj)
              this.goodsPriceShowData.push(obj)
            })
          })
        }
      })
      console.log(this.goodsPriceShowData)
    },
    // 内层判断是否编辑弹窗确认事件
    handleToCloseInnerDialog () {
      this.innerVisible = false
      this.$emit('update:dialogVisible', false)
    },
    // 弹窗左侧点击
    handleClickLeftLi (index) {
      console.log(index)
      this.nowIndex = index
    },
    // 商品价格输入框失焦
    handleToBlur (scope, flag) {
      console.log(scope, flag)
      let initPrice = scope.row.shopPrice
      console.log(scope)
      let reg = /[^\d.]/g
      switch (flag) {
        case 0:
          if (reg.test(scope.row.discountInputVal)) return
          this.judgeIsEdit = true // 判断是否编辑过
          let nowPrice = (initPrice * (Number(scope.row.discountInputVal) / 10)).toFixed(2)
          this.$set(this.checkGoodsData[scope.$index], 'priceRevisionVal', nowPrice)
          this.$set(this.checkGoodsData[scope.$index], 'priceIncreaseVal', (nowPrice - initPrice).toFixed(2))
          break
        case 1:
          if (reg.test(scope.row.priceIncreaseVal)) return
          this.judgeIsEdit = true// 判断是否编辑过
          let nowPrice2 = (initPrice + Number(scope.row.priceIncreaseVal)).toFixed(2)
          this.$set(this.checkGoodsData[scope.$index], 'priceRevisionVal', nowPrice2)
          this.$set(this.checkGoodsData[scope.$index], 'discountInputVal', ((nowPrice2 / initPrice) * 10).toFixed(2))
          break
        case 2:
          if (reg.test(scope.row.priceRevisionVal)) return
          this.judgeIsEdit = true// 判断是否编辑过
          let nowPrice3 = (Number(scope.row.priceRevisionVal) - initPrice).toFixed(2)
          this.$set(this.checkGoodsData[scope.$index], 'priceIncreaseVal', nowPrice3)
          this.$set(this.checkGoodsData[scope.$index], 'discountInputVal', ((Number(scope.row.priceRevisionVal) / initPrice) * 10).toFixed(2))
          break
      }

      console.log(scope.row)
    },
    // 保存点击
    handleToSave () {
      console.log('触发')
      this.$emit('update:dialogVisible', false)
    },
    // 父弹窗右上角点击关闭icon
    handleToCloseDialog () {
      console.log('2222')
      if (this.judgeIsEdit) {
        this.innerVisible = true
      } else {
        this.$emit('update:dialogVisible', false)
      }
    },
    // 运费模板右侧文字点击综合处理
    handleToClickTemplate (flag) {
      switch (flag) {
        case 0:

          break
        case 1:

          break
        case 2:

          break
      }
    },
    // 商品详情自定义内容中部综合点击处理
    handleToClickCustom (flag) {
      switch (flag) {
        case 0:
          console.log(flag)
          if (this.nowIndex === 5) {
            this.isShowCommonTableFive = true
          }
          if (this.nowIndex === 7) {
            this.isShowCommonTableSeven = true
          }

          break
        case 1:

          break
        case 2:

          break
        case 3:

          break
      }
    },
    // 商品标签select选中值变化
    handleToSelect (val) {
      console.log(val)
      if (val === -1) return
      let data = JSON.parse(JSON.stringify(this.labelOptions))
      data.forEach((item, index) => {
        if (item.value === val) {
          this.labelValueCheckArr.push(item)
          this.labelOptions.splice(index, 1)
        }
      })
      this.labelValue = -1
    },
    // 商品标签点击icon删除
    handleToClickDel (index, item) {
      this.labelOptions.push(item)
      this.labelValueCheckArr.splice(index, 1)
    },
    // 会员卡选中值变化
    handleToMemberSelect (val) {
      if (val === -1) return
      let data = JSON.parse(JSON.stringify(this.membershipOptions))
      data.forEach((item, index) => {
        if (item.value === val) {
          this.membershipValueCheckArr.push(item)
          this.membershipOptions.splice(index, 1)
        }
      })
      this.membershipValue = -1
    },
    // 点击删除
    handleToClickMemberDel (index, item) {
      this.membershipOptions.push(item)
      this.membershipValueCheckArr.splice(index, 1)
    }
  }
}
</script>
<style scoped lang="scss">
.batchSetup {
  /deep/ .el-dialog__header {
    text-align: left;
  }
  /deep/ .el-dialog__body {
    padding: 0;
  }
  .batchSetupMain {
    height: 100%;
    display: flex;
    .left {
      width: 100px;
      border-right: 1px solid #eee;
      font-size: 14px;
      li {
        text-align: center;
        margin-top: 20px;
        span {
          display: inline-block;
          padding-bottom: 10px;
          text-decoration: none;
          color: #333;
          border-bottom: 1px solid #fff;
          font-size: 14px;
          cursor: pointer;
          &:hover {
            color: #5a8bff;
          }
        }
      }
      .hover {
        color: #5a8bff;
        border-bottom: 1px solid #5a8bff;
      }
    }
    .right {
      width: 100%;
      padding: 10px 20px 0 20px;
      .br_title {
        background: #f5f5f5;
        color: #333;
        font-size: 13px;
        margin-bottom: 20px;
        height: 40px;
        line-height: 40px;
        width: 96%;
        padding-left: 10px;
        i {
          color: #999;
          margin-left: 30px;
        }
      }
      .dynamic {
        width: 100%;
        height: 100%;
        // padding-bottom: 60px;
        .price {
          height: 100%;
          .title {
            margin-bottom: 20px;
            i {
              color: #5a8bff;
              margin-right: 10px;
            }
            span {
              color: #999;
            }
          }
          .bottom {
            height: 337px;
            overflow-y: auto;
            .goodsDiv {
              display: flex;
              .item_goods_img {
                width: 40px;
                height: 40px;
                margin-right: 5px;
                img {
                  width: 100%;
                  height: 100%;
                }
              }
              .goods_message {
                width: 90px;
                height: 40px;
                display: flex;
                flex-direction: column;
                justify-content: space-around;
                text-align: left;
                font-size: 13px;
                .item_goods_name {
                  width: 100%;
                  text-overflow: ellipsis;
                  white-space: nowrap;
                  overflow: hidden;
                  color: #000;
                }
                .item_goods_desc {
                  color: #666;
                  text-overflow: ellipsis;
                  overflow: hidden;
                  white-space: nowrap;
                  width: 100%;
                  font-size: 12px;
                }
              }
            }

            .discount {
              display: flex;
              align-items: center;
              /deep/ .el-input {
                width: 80px;
              }
            }
          }
          /deep/ .tableClss th {
            background-color: #f5f5f5;
            border: none;
            height: 36px;
            font-weight: bold;
            color: #000;
            padding: 8px 10px;
          }
        }
        .classification {
          /deep/ .el-form-item__label {
            width: 83px !important;
          }
        }
        .template {
          .title {
            display: flex;
            .name {
              display: flex;
              align-items: center;
            }
            .rightOptions {
              display: flex;
              width: 230px;
              color: #5a8bff;
              align-items: center;
              span {
                display: inline-block;
                width: 60px;
                display: flex;
                align-items: center;
                justify-content: center;
                cursor: pointer;
                padding: 0 10px;
              }
            }
          }
          .hiddenTemplate {
            padding: 10px 0 0 70px;
            .content {
              width: 455px;
              background: #f5f5f5;
              padding: 10px 10px;
              max-height: 330px;
              overflow-y: auto;
              .top {
                display: flex;
                justify-content: space-between;
                .toDetail {
                  color: #5a8bff;
                }
              }
              .bottomContent {
                border-top: 1px solid #e6e6e6;
                margin-top: 10px;
                padding-top: 8px;
                .title {
                  margin-bottom: 8px;
                }
                .hiddencontent {
                  line-height: 18px;
                  text-align: justify;
                }
              }
            }
          }
        }
        .limitNum {
          .content {
            display: flex;
            /deep/ .el-input {
              width: 80px;
              margin: 0 10px;
            }
            span,
            i {
              display: flex;
              align-items: center;
            }
            i {
              color: #999;
            }
          }
        }
        .onSaleTime {
          display: flex;
          .onSaleTimeRight {
            display: flex;
            flex-direction: column;
            /deep/ .el-radio {
              margin-bottom: 20px;
            }
            .custom {
              display: flex;
              align-items: center;
              .tips {
                display: flex;
                align-items: center;
                position: relative;
                top: -10px;
              }
            }
          }
        }
        .commodityDetails {
          .temPosition {
            display: flex;
            margin-bottom: 20px;
          }
          .customContent {
            display: flex;
            .customMiddle {
              display: flex;
              flex-direction: column;
            }
            .customFooter {
              display: flex;
              margin-top: 20px;
              .label {
                display: flex;
                align-items: center;
              }
              .rightContent {
                display: flex;
                width: 230px;
                color: #5a8bff;
                align-items: center;
                span {
                  display: inline-block;
                  width: 60px;
                  display: flex;
                  align-items: center;
                  justify-content: center;
                  cursor: pointer;
                  padding: 0 10px;
                }
              }
            }
          }
        }
        .tatle {
          display: flex;
          padding: 20px 0 10px 10px;
          /deep/ .el-input {
            width: 140px;
            margin-left: 5px;
          }
          /deep/ .el-button {
            margin-left: 10px;
          }
          .tableLeft {
            display: flex;
            margin-right: 30px;
            span {
              white-space: nowrap;
              display: flex;
              align-items: center;
            }
          }
          .tableRight {
            display: flex;
            span {
              white-space: nowrap;
              display: flex;
              align-items: center;
            }
          }
        }
        .commonTable {
          width: 90%;
          height: 210px;
          overflow-y: auto;
          /deep/ .tableClss th {
            background-color: #f5f5f5;
            border: none;
            height: 36px;
            font-weight: bold;
            color: #000;
            padding: 8px 10px;
          }
        }
        .membershipExclusive {
          .membershipBottom {
            margin-top: 20px;
            .membershipTitle {
              i {
                color: #999;
                margin-left: 10px;
                display: inline-block;
              }
            }
            .membershipMiddle {
              display: flex;
              margin: 20px 0;
              /deep/ .el-input {
                width: 150px;
              }
              .rightContent {
                display: flex;
                align-items: center;
                margin-left: 10px;
              }
            }
            .showLabelContent {
              padding-left: 10px;
              background: #f5f5f5;
              margin-top: 20px;
            }
          }
        }
        .placeOfDelivery {
          .placeOfDeliveryContent {
            display: flex;
            .name {
              display: flex;
              align-items: center;
            }
            /deep/ .el-input {
              width: 180px;
            }
          }
        }
      }
    }
    .showLabelContent {
      padding: 10px 0 0 70px;
      display: flex;
      .labelLeft {
        display: flex;
        padding-top: 4px;
      }
      .labelRight {
        width: 350px;
        display: flex;
        flex-wrap: wrap;
        .list {
          padding: 0 3px;
          height: 22px;
          line-height: 22px;
          text-align: center;
          margin-left: 10px;
          border: 1px solid #ccc;
          margin-bottom: 10px;
          position: relative;
          img {
            position: absolute;
            right: -10px;
            top: -7px;
            cursor: pointer;
          }
        }
      }
    }
  }
  .dialog-footer {
    display: block;
    width: 100%;
    text-align: center;
  }
}
</style>
