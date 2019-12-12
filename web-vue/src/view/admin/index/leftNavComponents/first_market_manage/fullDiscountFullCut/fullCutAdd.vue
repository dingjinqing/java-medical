<template>
  <div class="addActivity">
    <div class="addActivity_main">
      <div class="nav_list">
        <el-tabs
          v-model="activeName"
          @tab-click="handleClick"
        >
          <el-tab-pane
            label="全部满折满减活动"
            name="0"
          ></el-tab-pane>
          <el-tab-pane
            label="进行中"
            name="1"
          ></el-tab-pane>
          <el-tab-pane
            label="未开始"
            name="2"
          ></el-tab-pane>
          <el-tab-pane
            label="已过期"
            name="3"
          ></el-tab-pane>
          <el-tab-pane
            label="已停用"
            name="4"
          ></el-tab-pane>
          <el-tab-pane
            :label="getTabName()"
            name="5"
          ></el-tab-pane>
        </el-tabs>
      </div>

      <div class="full_cut_content">
        <el-form label-width="120px">
          <!-- 活动名称 -->
          <el-form-item label="活动名称：">
            <el-input
              v-model="activityName"
              placeholder="请输入活动名称"
              size="small"
              class="form_input"
            ></el-input>
          </el-form-item>

          <!-- 活动优先级 -->
          <el-form-item label="活动优先级：">
            <el-input
              v-model="activityLevel"
              placeholder="请输入活动优先级"
              class="form_input"
              size="small"
            ></el-input>
            <p class="form_tip">用于区分不同满折满减活动的优先级，请填写正整数，数值越大优先级越高</p>
          </el-form-item>

          <!-- 活动类型 -->
          <el-form-item label="活动类型：">
            <el-radio-group v-model="activityType">
              <el-radio label="2">满减</el-radio>
              <el-radio label="1">每满减</el-radio>
              <el-radio label="3">满折</el-radio>
              <el-radio label="4">仅第X件打折</el-radio>
            </el-radio-group>
            <p class="form_tip actTypeSaveTips">保存后不可编辑</p>
          </el-form-item>

          <!-- 满减金额 -->
          <el-form-item label="满减金额：">
            <div v-if="activityType !== '4'">
              <el-radio
                label="1"
                v-model="discount"
              >满金额</el-radio>
              <section style="display:flex;margin-left: 25px">
                <span v-if="activityType === '2' || activityType === '3'">满</span>
                <span v-if="activityType === '1'">每满</span>
                &nbsp;<el-input
                  class="form_input"
                  size="small"
                  v-model="fullMoney"
                ></el-input>&nbsp;元，
                <span v-if="activityType === '2' || activityType === '1'">减</span>
                <span v-if="activityType === '3'">打</span>
                &nbsp;<el-input
                  class="form_input"
                  size="small "
                  v-model="reduceMoney"
                ></el-input>&nbsp;
                <span v-if="activityType === '2' || activityType === '1'">元</span>
                <span v-if="activityType === '3'">折</span>
                <div
                  class="iconAdd"
                  @click="addFullCutItem()"
                >
                  <img
                    :src="$imageHost + '/image/admin/sign_jia.png'"
                    alt=""
                  >
                </div>
              </section>

              <el-radio
                label="2"
                v-model="discount"
              >满件数</el-radio>
              <section style="display:flex;margin-left: 25px">
                <span v-if="activityType === '2' || activityType === '3'">满</span>
                <span v-if="activityType === '1'">每满</span>
                &nbsp;<el-input
                  class="form_input"
                  size="small"
                  v-model="amount"
                ></el-input>&nbsp;件，
                <span v-if="activityType === '2' || activityType === '1'">减</span>
                <span v-if="activityType === '3'">打</span>
                &nbsp;<el-input
                  class="form_input"
                  size="small "
                  v-model="discounts"
                ></el-input>&nbsp;
                <span v-if="activityType === '2' || activityType === '1'">元</span>
                <span v-if="activityType === '3'">折</span>
                <div class="iconAdd">
                  <img
                    :src="$imageHost + '/image/admin/sign_jia.png'"
                    alt=""
                  >
                </div>
              </section>
            </div>

            <div v-if="activityType === '4'">
              <div>
                第&nbsp;<el-input
                  class="form_input"
                  size="small"
                ></el-input>&nbsp;件，
                打&nbsp;<el-input
                  class="form_input"
                  size="small"
                ></el-input>&nbsp;折
              </div>
            </div>
          </el-form-item>

          <!-- 有效期 -->
          <el-form-item label="有效期：">
            <el-date-picker
              v-model="timeInterval"
              type="datetimerange"
              size="small"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>

          <!-- 活动商品 -->
          <el-form-item label="活动商品：">
            <el-radio
              v-model="activityGoods"
              label="0"
            >全部商品</el-radio>
            <br>
            <el-radio
              v-model="activityGoods"
              label="1"
            >指定商品</el-radio>
            <section v-if="activityGoods === '1'">
              <div
                class="add_goods_btn"
                @click="chooseGoodsHandler"
              > + 选择商品</div>
              <div class="goods_area">
                <table
                  class="goods_table"
                  width="100%"
                >
                  <thead>
                    <tr>
                      <th width="45%">商品名称</th>
                      <th width="15%">价格</th>
                      <th width="15%">库存</th>
                      <th width="15%">操作</th>
                    </tr>
                  </thead>
                  <tbody
                    class="tbody"
                    v-for="item in goodsList"
                    :key="item.name"
                  >
                    <tr>
                      <td>
                        <div class="goods_info">
                          <div class="goods_img">
                            <img
                              :src="item.goodsImg"
                              alt=""
                            >
                          </div>
                          <div class="goods_name">{{item.goodsName}}</div>
                        </div>
                      </td>
                      <td>￥{{item.shopPrice}}</td>
                      <td>{{item.goodsNumber}}</td>
                      <td @click="deleteGoods()">删除</td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div
                class="add_goods_btn"
                @click="selectPlatform"
              > + 选择平台分类</div>
              <table class="brand_table">
                <tbody>
                  <tr>
                    <th
                      style="border-bottom: 1px solid #ddd"
                      width="100%"
                    >
                      平台分类
                      <div class="operate">
                        <span
                          class="edit"
                          @click="editPlateformClassification"
                        >编辑</span>
                        <span
                          class="delete"
                          @click="deletePlateformClassfication"
                        >删除</span>
                      </div>
                    </th>
                  </tr>
                  <tr>
                    <div class="exampleWrapper">
                      <span class="example">示例：</span>
                      <span class="first_cat">一级分类</span>
                      <span class="second_cat">二级分类</span>
                    </div>
                    <div
                      class="bussinessGoodsName"
                      v-for="(item, index) in platformList"
                      :key="index"
                    >
                      <span>{{item.catName}}</span>
                    </div>
                  </tr>
                </tbody>
              </table>

              <div
                class="add_goods_btn"
                @click="selectBussiness"
              > + 选择商家分类</div>
              <table class="brand_table">
                <tbody>
                  <tr>
                    <th
                      style="border-bottom: 1px solid #ddd"
                      width="100%"
                    >
                      商家分类
                      <div class="operate">
                        <span
                          class="edit"
                          @click="editBussinessClassification"
                        >编辑</span>
                        <span
                          class="delete"
                          @click="deleteBussinessClassfication"
                        >删除</span>
                      </div>
                    </th>
                  </tr>
                  <tr>
                    <div class="exampleWrapper">
                      <span class="example">示例：</span>
                      <span class="first_cat">一级分类</span>
                      <span class="second_cat">二级分类</span>
                    </div>
                    <div
                      class="bussinessGoodsName"
                      v-for="(item, index) in bussinessList"
                      :key="index"
                    >
                      <span>{{item.sortName}}</span>
                    </div>
                  </tr>
                </tbody>
              </table>

              <div
                class="add_goods_btn"
                @click="selectGoodsLabel"
              > + 选择商品标签</div>
              <table class="brand_table">
                <tbody>
                  <tr>
                    <th
                      style="border-bottom: 1px solid #ddd"
                      width="100%"
                    >
                      已选标签
                      <div class="operate">
                        <span
                          class="edit"
                          @click="editSelectedLabel"
                        >编辑</span>
                        <span
                          class="delete"
                          @click="deleteSelectedLabel"
                        >删除</span>
                      </div>
                    </th>
                  </tr>
                  <tr>
                    <div
                      class="goodsName"
                      v-for="(item, index) in labelNameList"
                      :key="index"
                    >
                      <span>{{item.name}}</span>
                    </div>
                  </tr>
                </tbody>
              </table>
              <div
                class="add_goods_btn"
                @click="seclectGoodsBrand"
              > + 选择商品品牌</div>
              <table class="brand_table">
                <tbody>
                  <tr>
                    <th
                      style="border-bottom: 1px solid #ddd"
                      width="100%"
                    >
                      已选品牌
                      <div class="operate">
                        <span
                          class="edit"
                          @click="editSelectedBrand"
                        >编辑</span>
                        <span
                          class="delete"
                          @click="deleteSelectedBrand"
                        >删除</span>
                      </div>
                    </th>
                  </tr>
                  <tr>
                    <div
                      class="goodsName"
                      v-for="(item, index) in goodsNameList"
                      :key="index"
                    >
                      <span>{{item.brandName}}</span>
                    </div>
                  </tr>
                </tbody>
              </table>
            </section>
          </el-form-item>

          <!-- 会员专享活动 -->
          <el-form-item label="会员专享活动：">
            <el-checkbox v-model="vipActivity">用户持会员卡可以参与活动</el-checkbox>
            <br>
            <el-select
              v-model="memberCardInfo"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>

        </el-form>
      </div>
      <!-- 选择商品弹窗 -->
      <ChoosingGoods
        :tuneUpChooseGoods='tuneUpChooseGoodsDialog'
        :chooseGoodsBack="selectedGoodsList"
        @resultGoodsDatas="returnGoodsData"
      />

      <!-- 选择商家分类弹窗  -->
      <AddingBusClassDialog
        :dialogVisible.sync="tuneUpBussDialog"
        :backDataArr='bussinessIdList'
        :classFlag=1
        @BusClassTrueDetailData="returnBusinessData"
      />
      <!-- 选择平台分类弹窗 -->
      <AddingBusClassDialog
        :dialogVisible.sync="tuneUpPlatformDialog"
        :backDataArr='platformIdList'
        :classFlag=2
        @BusClassTrueDetailData="returnPlateformData"
      />

      <!-- 选择商品标签 -->
      <SelectGoodsLabel
        :callAddProductLabel.sync="tuneUpSelectGoodsLabelDialog"
        :brandBackData="labelNameIdList"
        @handleToGetBackData="returnGoodsLabelData"
      />

      <!-- 选择商品品牌弹窗 -->
      <AddBrandDialog
        :callAddBrand.sync="tuneUpBrandDialog"
        :brandBackData="goodsBrandIdList"
        @handleToGetBackData="returnGoodsBrandData"
      />
    </div>

    <div class="save_button">
      <el-button
        type="primary"
        size="small"
        @click="submit"
      >保存</el-button>
    </div>
  </div>
</template>

<script>
import { addFullCutActivityApi, memberCardActivityName } from '@/api/admin/marketManage/fullDiscountFullCut'
import ChoosingGoods from '@/components/admin/choosingGoods'
import AddingBusClassDialog from '@/components/admin/addingBusClassDialog'
import AddBrandDialog from '@/components/admin/addBrandDialog'
import SelectGoodsLabel from '@/components/admin/addProductLabel'

export default {
  components: { ChoosingGoods, AddingBusClassDialog, AddBrandDialog, SelectGoodsLabel },
  data () {
    return {
      activeName: '5',
      aaa: 1,
      activityName: '',
      activityLevel: '',
      activityType: '1',
      timeInterval: [],
      activityGoods: '1',
      fullMoney: '',
      reduceMoney: '',
      amount: '',
      discounts: '',
      discount: '1',
      vipActivity: '',
      memberCardInfo: '',
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }],
      tuneUpChooseGoodsDialog: false,
      selectedGoodsList: [],
      conditionAddParams: [],
      goodsList: [],
      tuneUpBussDialog: false,
      tuneUpPlatformDialog: false,
      startTime: '',
      endTime: '',
      tuneUpBrandDialog: false,
      tuneUpSelectGoodsLabelDialog: false,
      goodsNameList: [],
      goodsBrandIdList: [],
      labelNameList: [],
      labelNameIdList: [],
      bussinessList: [],
      bussinessIdList: [],
      platformList: [],
      platformIdList: []

    }
  },
  watch: {
    timeInterval: function (newVal) {
      this.startTime = newVal[0]
      this.endTime = newVal[1]
    }
  },
  methods: {
    handleClick (tab) {
      console.log(tab, 'tabChange')
      this.$nextTick(() => {
        if (tab.index !== '5') {
          this.$router.push({
            path: '/admin/home/main/fullDiscountFullCut',
            query: {
              tabIndex: tab.index
            }
          })
        }
      })
    },
    getMemberCardName () {
      memberCardActivityName().then(res => {
        console.log(res)
      })
    },
    getTabName () {
      // if (this.$route.query.id) {
      if (this.aaa) {
        return '添加满折满减活动'
      } else {
        return '修改满折满减活动'
      }
      // console.log(111)
    },
    submit () {
      let obj = {
        actName: this.activityName,
        type: this.activityType,
        actType: this.activityGoods,
        conditionAddParams: [
          {
            fullMoney: 333,
            reduceMoney: 32
          },
          {
            fullMoney: 503,
            reduceMoney: 102
          }
        ],
        startTime: this.startTime,
        endTime: this.endTime,
        strategyPriority: this.activityLevel
      }
      addFullCutActivityApi(obj).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success('保存成功')
        } else {
          this.$message.warning()
        }
      })
    },
    addFullCutItem () {
      let obj = {
        'fullMoney': '',
        'reduceMoney': '',
        'amount': '',
        'discount': ''
      }
      this.conditionAddParams.push(obj)
    },
    // 选择商品数据处理
    chooseGoodsHandler () {
      this.tuneUpChooseGoodsDialog = !this.tuneUpChooseGoodsDialog
    },
    returnGoodsData (val) {
      console.log(val, 'goodsInfo')
      this.goodsList = val
      this.selectedGoodsList = val.map(item => item.goodsId)
      console.log(this.selectedGoodsList, 'selectedGoodsList')
    },
    // selectedGoodsIdList (data) {
    //   console.log(data, 'goodsId')
    // },
    deleteGoods () { },

    // 选择平台、商家分类数据处理
    selectPlatform () {
      this.tuneUpPlatformDialog = !this.tuneUpPlatformDialog
    },
    returnPlateformData (val) {
      console.log(val, 'platform data')
      this.platformList = val
      // this.platformIdList = val.map(item)
    },
    editPlateformClassification () {
      this.tuneUpPlatformDialog = !this.tuneUpPlatformDialog
    },
    deletePlateformClassfication () {

    },

    selectBussiness () {
      this.tuneUpBussDialog = !this.tuneUpBussDialog
    },
    returnBusinessData (val) {
      console.log(val, 'buiness data--')
      this.bussinessList = val
      this.bussinessIdList = val.map(item => item.sortId)
      console.log(this.bussinessIdList, 'bussinessIdList')
    },
    editBussinessClassification () {
      this.tuneUpBussDialog = !this.tuneUpBussDialog
    },
    deleteBussinessClassfication () {

    },

    // 选择商品品牌数据处理
    seclectGoodsBrand () {
      this.tuneUpBrandDialog = !this.tuneUpBrandDialog
    },
    returnGoodsBrandData (val) {
      this.goodsNameList = val
      let idList = val.map(item => item.id)
      this.goodsBrandIdList = idList
    },
    editSelectedBrand () {
      this.tuneUpBrandDialog = !this.tuneUpBrandDialog
    },
    deleteSelectedBrand () {
      console.log('deleteSelectedBrand')
    },

    // 选择商品标签数据处理
    selectGoodsLabel () {
      this.tuneUpSelectGoodsLabelDialog = true
    },
    returnGoodsLabelData (val) {
      console.log(val, 'label id')
      this.labelNameList = val
      let labelIdList = val.map(item => item.id)
      this.labelNameIdList = labelIdList
      console.log(this.labelNameIdList, 'label name id list')
    },
    editSelectedLabel () {
      this.tuneUpSelectGoodsLabelDialog = !this.tuneUpSelectGoodsLabelDialog
    },
    deleteSelectedLabel () {

    }
  }

}

</script>
<style lang="scss" scoped>
.addActivity {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .addActivity_main {
    background-color: #fff;
    padding: 15px;
    margin-bottom: 55px;
    .full_cut_content {
      .form_input {
        width: 185px;
      }
      .form_tip {
        display: inline;
        color: #999;
        margin-left: 20px;
      }
      .actTypeSaveTips {
        margin-left: 50px;
      }
      .iconAdd {
        margin: 3px 0 0 10px;
        cursor: pointer;
      }
      .add_goods_btn {
        width: 120px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        color: #5a8bff;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        margin: 20px 0;
      }
      .goods_area {
        max-width: 528px;
        height: 300px;
        padding-right: 10px;
        overflow-x: hidden !important;
        .goods_table {
          border: 1px solid #ddd;
          thead tr {
            background: #f8f8f8;
            font-weight: bold;
            color: #333;
            text-align: center;
            th {
              border: 1px solid #ddd;
            }
          }
          tbody tr {
            text-align: center;
            td {
              padding: 10px;
              border: 1px solid #ddd;
              .goods_info {
                display: flex;
                .goods_img {
                  width: 40px;
                  height: 40px;
                  margin-right: 10px;
                  img {
                    width: 100%;
                    height: 100%;
                  }
                }
              }
            }
          }
        }
      }
      .goods_area::-webkit-scrollbar {
        width: 4px;
        height: 4px;
      }
      .goods_area::-webkit-scrollbar-track {
        background: #fff;
        border-radius: 2px;
      }
      .goods_area::-webkit-scrollbar-thumb {
        background: #dddddd;
        border-radius: 2px;
      }
      .goods_area::-webkit-scrollbar-thumb:hover {
        background: #747474;
      }
      .goods_area::-webkit-scrollbar-corner {
        background: #fff;
      }
      .platformClassification {
        max-width: 528px;
        border: 1px solid #ddd;
        thead tr {
          background: #f8f8f8;
        }
      }
      .brand_table {
        width: 528px;
        margin-bottom: 10px;
        border: 1px solid #ddd;
        background: #fff;
        th {
          padding: 10px 5px;
          text-align: center;
          background-color: #f8f8f8;
          line-height: 20px;
          .operate {
            margin-right: 6px;
            float: right;
            span {
              cursor: pointer;
            }
            .edit {
              margin-right: 10px;
            }
          }
        }
        .exampleWrapper {
          line-height: 50px;
          height: 50px;
          border-bottom: 1px solid #eee;
          .example {
            margin-left: 20px;
          }
          span {
            padding: 4px 10px;
            border-radius: 20px;
            color: #666;
          }
          .first_cat {
            border: 1px solid #b9d2ff;
            background-color: #f0f5ff;
          }
          .second_cat {
            margin-left: 15px;
            border: 1px solid #ffe2b8;
            background-color: #fffaf2;
          }
        }
        .goodsName {
          display: inline-block;
          span {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 5px;
            margin-left: 10px;
            display: inline-block;
            line-height: 20px;
          }
        }
        .bussinessGoodsName {
          display: inline-block;
          padding: 0 0 16px 20px;
          span {
            display: inline-block;
            padding: 4px 10px;
            margin: 16px 10px 0 0;
            border-radius: 20px;
            line-height: 35px;
            color: #666;
            border: 1px solid #b9d2ff;
            background-color: #f0f5ff;
          }
        }
      }
    }
  }
  .save_button {
    position: fixed;
    bottom: 0;
    right: 27px;
    left: 160px;
    text-align: center;
    background: #fff;
    padding: 10px;
  }
}
</style>
