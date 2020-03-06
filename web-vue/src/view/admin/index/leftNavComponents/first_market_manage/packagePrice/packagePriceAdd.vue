<template>
  <div class="content">
    <el-form
      ref="form"
      :model="param"
      label-width="130px"
      labelPosition="right"
    >
      <el-form-item label="活动类型：">
        <div>
          <el-radio
            :label=1
            v-model="param.actType"
          >
            一口价结算
          </el-radio>
          <span class="font-color">用户选择多件商品，以商家设置的结算价格进行打包购买</span>
        </div>
        <div>
          <el-radio
            :label=2
            v-model="param.actType"
          >指定折扣结算</el-radio>
          <span class="font-color">用户选择多件商品，以商家设置的折扣进行打包购买</span>
        </div>
      </el-form-item>

      <el-form-item label="活动名称：">
        <el-input
          size="small"
          style="width: 150px"
          v-model="param.packageName"
        ></el-input>
        <span class="font-color">只作为商家记录使用，用户不会看到这个名称</span>
      </el-form-item>

      <el-form-item label="活动时间：">
        <el-date-picker
          v-model="param.validity"
          type="datetimerange"
          value-format="yyyy-MM-dd HH:mm:ss"
          format="yyyy-MM-dd HH:mm:ss"
          :range-separator="$t('shipping.to')"
          :start-placeholder="$t('ordinaryCoupon.startTime')"
          :end-placeholder="$t('ordinaryCoupon.endTime')"
          :default-time="['00:00:00','23:59:59']"
          size="small"
          style="width: 350px"
        >
        </el-date-picker>
        <span class="font-color">同一时间可以有多个一口价活动</span>
      </el-form-item>

      <el-form-item label="活动规则：">
        <section>
          <div class="font-color">最多可配置3个商品组，买家在每组商品中分别选择指定件数，即可以“结算总价格”结算</div>
          <div
            class="font-color"
            style="line-height: 15px;"
          >
            例如：商家可设置3个商品组，买家需在每个商品组中选择1件，3组共3件以总价100元结算
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
              style="margin-left: 10px;"
            >
              <el-image :src="srcList.src1"></el-image>
              <el-button
                slot="reference"
                type="text"
              >查看示例</el-button>
            </el-popover>
          </div>
        </section>
      </el-form-item>

      <el-form-item
        label="结算总价格："
        v-if="param.actType === 1"
      >
        <el-input
          size="small"
          style="width: 150px"
          v-model="param.totalMoney"
        ></el-input>&nbsp;&nbsp;元
      </el-form-item>

      <el-form-item
        label="指定折扣："
        v-if="param.actType === 2"
      >
        <el-input
          size="small"
          style="width: 100px"
        ></el-input>&nbsp;&nbsp;折
      </el-form-item>

      <el-form-item label="商品组：">
        <section style="display: flex">
          <el-checkbox v-model="param.group1">商品组1</el-checkbox>
          <div
            style="margin-left:20px"
            v-if="param.group1 === true"
          >
            <div>
              <span>名称</span>&nbsp;&nbsp;
              <el-input
                size="small"
                style="width: 80px"
                v-model="param.groupName"
              ></el-input>&nbsp;&nbsp;
              <span class="font-color">商品组名称，最多可填4个字</span>
            </div>
            <div>
              <span>需选择</span>&nbsp;&nbsp;
              <el-input
                size="small"
                style="width: 80px"
                v-model="param.goodsNumber"
              ></el-input>&nbsp;&nbsp;件
              <span class="font-color">该商品组需要选购的商品数量，请填写正整数</span>
            </div>
          </div>
        </section>

        <section style="display: flex">
          <el-checkbox v-model="param.group2">商品组2</el-checkbox>
          <div
            style="margin-left:20px"
            v-if="param.group2 === true"
          >
            <div>
              <span>名称</span>&nbsp;&nbsp;
              <el-input
                size="small"
                style="width: 80px"
                v-model="param.groupName"
              ></el-input>&nbsp;&nbsp;
              <span class="font-color">商品组名称，最多可填4个字</span>
            </div>
            <div>
              <span>需选择</span>&nbsp;&nbsp;
              <el-input
                size="small"
                style="width: 80px"
                v-model="param.goodsNumber"
              ></el-input>&nbsp;&nbsp;件
              <span class="font-color">该商品组需要选购的商品数量，请填写正整数</span>
            </div>
          </div>
        </section>

        <section style="display: flex">
          <el-checkbox v-model="param.group3">商品组3</el-checkbox>
          <div
            style="margin-left:20px"
            v-if="param.group3 === true"
          >
            <div>
              <span>名称</span>&nbsp;&nbsp;
              <el-input
                size="small"
                style="width: 80px"
                v-model="param.groupName"
              ></el-input>&nbsp;&nbsp;
              <span class="font-color">商品组名称，最多可填4个字</span>
            </div>
            <div>
              <span>需选择</span>&nbsp;&nbsp;
              <el-input
                size="small"
                style="width: 80px"
                v-model="param.goodsNumber"
              ></el-input>&nbsp;&nbsp;件
              <span class="font-color">该商品组需要选购的商品数量，请填写正整数</span>
            </div>
          </div>
        </section>
      </el-form-item>

      <el-form-item label="添加商品：">
        <div>
          <span>请给每个商品组分别添加商品</span>
          <div class="goods-area">
            <div
              style="display: flex"
              class="goods-num"
            >
              <div>商品组1</div>
              <div v-if="param.group2 === true">商品组2</div>
              <div v-if="param.group3 === true">商品组3</div>
            </div>
            <div
              class="add-btn"
              @click="addGoods"
            >+ 添加商品</div>
            <div v-if="goodsList && goodsList.length >0">
              <table class="add-goods">
                <thead>
                  <tr>
                    <th width="45%">商品名称</th>
                    <th width="15%">价格</th>
                    <th width="15%">库存</th>
                    <th width="15%">操作</th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="(item, index) in goodsList"
                    :key="item.name"
                  >
                    <td>
                      <div class="goods-info">
                        <div class="goods-img">
                          <img
                            :src="item.goodsImg"
                            alt=""
                          >
                        </div>
                        <div class="goods-name">{{item.goodsName}}</div>
                      </div>
                    </td>
                    <td>￥{{item.shopPrice}}</td>
                    <td>{{item.goodsNumber}}</td>
                    <td @click="deleteGoods(index)">
                      <span style="cursor:pointer">删除</span>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <!-- v-if="platformList && platformList.length" -->
            <div
              class="add-btn"
              @click="addPlateForm"
            >+ 添加平台分类</div>
            <table
              class="brand_table"
              v-if="platformList && platformList.length"
            >
              <tbody>
                <tr>
                  <th
                    style="border-bottom: 1px solid #ddd"
                    width="100%"
                  >
                    平台分类
                    <div class="operate">
                      <span
                        class="edit-icon edit"
                        @click="editPlateformClassification"
                        v-if="!param.id"
                      >编辑</span>
                      <span
                        class="delete"
                        @click="deletePlateformClassfication"
                        v-if="!param.id"
                      >删除</span>
                    </div>
                  </th>
                </tr>
                <tr>
                  <div class="exampleWrapper">
                    <span class="example">示例：</span>
                    <span class="first_cat">一级分类</span>
                    <span class="second_cat">二级分类</span>
                    <span class="third_cat">三级分类</span>
                  </div>
                  <div
                    class="bussinessGoodsName"
                    v-for="(item, index) in platformList"
                    :key="index"
                  >
                    <span :class="{'first_cat': item.level === 0, 'second_cat': item.level === 1, 'third_cat': item.level === 2}">{{item.catName}}</span>
                  </div>
                </tr>
              </tbody>
            </table>

            <div
              class="add-btn"
              @click="selectBussiness"
              v-if="!param.id"
            >+ 添加商家分类</div>
            <table
              class="brand_table"
              v-if="bussinessList && bussinessList.length > 0"
            >
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
                        v-if="!param.id"
                      >编辑</span>
                      <span
                        class="delete"
                        @click="deleteBussinessClassfication"
                        v-if="!param.id"
                      >删除</span>
                    </div>
                  </th>
                </tr>
                <tr>
                  <div class="exampleWrapper">
                    <span class="example">示例：</span>
                    <span class="first_cat">一级分类</span>
                    <span class="second_cat">二级分类</span>
                    <span class="third_cat">三级分类</span>
                  </div>
                  <div
                    class="bussinessGoodsName"
                    v-for="(item, index) in bussinessList"
                    :key="index"
                  >
                    <span :class="{'first_cat': item.level === 0, 'second_cat': item.level === 1, 'third_cat': item.level === 2}">{{item.sortName}}</span>
                  </div>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </el-form-item>
    </el-form>

    <!-- 选择商品弹窗 -->
    <ChoosingGoods
      :tuneUpChooseGoods='tuneUpChooseGoodsDialog'
      :chooseGoodsBack="selectedGoodsIdList"
      @resultGoodsDatas="returnGoodsData"
    />

    <!-- 选择平台分类弹窗 -->
    <AddingBusClassDialog
      :dialogVisible.sync="tuneUpPlatformDialog"
      :backDataArr='platformIdList'
      :classFlag="2"
      @BusClassTrueDetailData="returnPlateformData"
    />

    <!-- 选择商家分类弹窗  -->
    <AddingBusClassDialog
      :dialogVisible.sync="tuneUpBussDialog"
      :backDataArr='bussinessIdList'
      :classFlag="1"
      @BusClassTrueDetailData="returnBusinessData"
    />

  </div>
</template>

<script>
import ChoosingGoods from '@/components/admin/choosingGoods'
import AddingBusClassDialog from '@/components/admin/addingBusClassDialog'

export default {
  components: { ChoosingGoods, AddingBusClassDialog },
  data () {
    return {
      param: {
        id: '',
        actType: 1, // 活动类型
        packageName: '', // 活动名称
        validity: '', // 活动时间
        startTime: '',
        endTime: '',
        totalMoney: '', // 总结算价格
        group1: '',
        group2: false,
        group3: false,
        groupName: '', // 商品组名称
        goodsNumber: '' // 至少需要选择件数
      },
      srcList: {
        src1: `${this.imageHost}/image/admin/new_preview_image/packagesale.jpg`
      },
      // 选择商品
      tuneUpChooseGoodsDialog: false,
      goodsList: [],
      selectedGoodsIdList: [],
      // 选择平台分类
      tuneUpPlatformDialog: false,
      platformList: [],
      platformIdList: [],
      // 选择商家分类
      tuneUpBussDialog: false,
      bussinessList: [],
      bussinessIdList: []
    }
  },
  methods: {
    // 添加商品-弹窗唤起
    addGoods () {
      this.tuneUpChooseGoodsDialog = !this.tuneUpChooseGoodsDialog
    },
    // 添加商品弹窗-数据回显
    returnGoodsData (val) {
      console.log(val, 'goodsInfo')
      this.goodsList = val
      this.selectedGoodsIdList = val.map(item => item.goodsId)
    },
    // 添加商品-删除数据
    deleteGoods (index) {
      console.log(index, 'index-data')
      this.goodsList.splice(index, 1)
      this.selectedGoodsIdList.splice(index, 1)
    },

    // 添加平台分类-调起
    addPlateForm () {
      this.tuneUpPlatformDialog = !this.tuneUpPlatformDialog
    },
    // 选择平台分类-弹窗数据回显
    returnPlateformData (val) {
      console.log(val, 'platform-data')
      this.platformList = val
      this.platformIdList = val.map(item => item.catId)
    },
    editPlateformClassification () {
      this.tuneUpPlatformDialog = !this.tuneUpPlatformDialog
    },
    deletePlateformClassfication () {
      this.platformList = []
      this.platformIdList = []
    },

    // 商家分类弹窗相关逻辑处理
    selectBussiness () {
      this.tuneUpBussDialog = !this.tuneUpBussDialog
    },
    // 商家分类弹窗回调
    returnBusinessData (val) {
      console.log(val, 'buiness data--')
      this.bussinessList = val
      this.bussinessIdList = val.map(item => item.sortId)
    },
    editBussinessClassification () {
      this.tuneUpBussDialog = !this.tuneUpBussDialog
    },
    deleteBussinessClassfication () {
      this.bussinessList = []
      this.bussinessIdList = []
    }
  }
}

</script>
<style lang="scss" scoped>
.content {
  margin-top: 10px;
  padding: 15px;
  background: #fff;
  .font-color {
    color: #999;
  }
  .goods-area {
    border: 1px solid #ccc;
    width: 600px;
    .goods-num {
      height: 30px;
      line-height: 30px;
      border-bottom: 1px solid #ccc;
      background: #f5f5f5;
      div {
        width: 70px;
        border-right: 1px solid #ccc;
        background: #fff;
        text-align: center;
      }
    }
    .add-btn {
      width: 120px;
      height: 30px;
      line-height: 30px;
      text-align: center;
      color: #5a8bff;
      border: 1px solid #ccc;
      background: #fff;
      cursor: pointer;
      margin: 10px 0 10px 10px;
    }
    .add-goods {
      border: 1px solid #ddd;
      margin-left: 10px;
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
          .goods-info {
            display: flex;
            .goods-img {
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
    .brand_table {
      width: 528px;
      margin-bottom: 10px;
      margin-left: 10px;
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
  .first_cat {
    border: 1px solid #b9d2ff !important;
    background-color: #f0f5ff !important;
  }
  .second_cat {
    margin-left: 15px;
    border: 1px solid #ffe2b8 !important;
    background-color: #fffaf2 !important;
  }
  .third_cat {
    margin-left: 15px;
    border: 1px solid #ffc0cc !important;
    background-color: #fff6f8 !important;
  }
}
</style>
