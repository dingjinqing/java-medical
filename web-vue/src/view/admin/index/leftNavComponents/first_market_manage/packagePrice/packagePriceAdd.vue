<!--
* 打包一口价-新增打包一口价活动
* @author:赵鑫
-->
<template>
  <div class="content">
    <div class="mian">
      <el-form
        ref="form"
        :model="param"
        :rules="formRules"
        label-width="130px"
        labelPosition="right"
      >
        <el-form-item
          label="活动类型："
          required
        >
          <div>
            <el-radio
              :label=0
              v-model="param.packageType"
            >一口价结算</el-radio>
            <span class="font-color">用户选择多件商品，以商家设置的结算价格进行打包购买</span>
          </div>
          <div>
            <el-radio
              :label=1
              v-model="param.packageType"
            >指定折扣结算</el-radio>
            <span class="font-color">用户选择多件商品，以商家设置的折扣进行打包购买</span>
          </div>
        </el-form-item>

        <el-form-item
          label="活动名称："
          prop="packageName"
        >
          <el-input
            size="small"
            style="width: 150px"
            v-model="param.packageName"
          ></el-input>
          <span class="font-color">&nbsp;&nbsp;&nbsp;只作为商家记录使用，用户不会看到这个名称</span>
        </el-form-item>

        <el-form-item
          label="活动时间："
          prop="validity"
        >
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
          <span class="font-color">&nbsp;&nbsp;&nbsp;同一时间可以有多个一口价活动</span>
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
          prop="totalMoney"
          v-if="param.packageType === 0"
        >
          <el-input
            size="small"
            style="width: 150px"
            v-model="param.totalMoney"
          ></el-input>&nbsp;&nbsp;元
        </el-form-item>

        <el-form-item
          label="指定折扣："
          prop="totalRatio"
          v-if="param.packageType === 1"
        >
          <el-input
            size="small"
            style="width: 100px"
            v-model="param.totalRatio"
          ></el-input>&nbsp;&nbsp;折
        </el-form-item>

        <el-form-item
          label="商品组："
          required
        >
          <section style="display: flex">
            <el-checkbox
              v-model="group1Flag"
              :checked="checkout"
              :disabled="disabled"
            >商品组1</el-checkbox>
            <div style="margin-left:20px">
              <div style="margin-left:14px">
                <span>名称</span>&nbsp;&nbsp;
                <el-input
                  size="small"
                  v-model="param.groupName"
                  maxlength="4"
                  show-word-limit
                  class="default_width"
                ></el-input>&nbsp;&nbsp;
                <span class="font-color">商品组名称，最多可填4个字</span>
              </div>
              <el-form-item
                prop="goodsNumber"
                :inline-message="true"
              >
                <span>需选择</span>&nbsp;&nbsp;
                <el-input
                  size="small"
                  v-model="param.goodsNumber"
                  class="default_width"
                ></el-input>&nbsp;&nbsp;件
                <span class="font-color">该商品组需要选购的商品数量，请填写正整数</span>
              </el-form-item>
            </div>
          </section>

          <section style="display: flex">
            <el-checkbox v-model="group2Flag">商品组2</el-checkbox>
            <div
              style="margin-left:20px"
              v-if="group2Flag === true"
            >
              <div style="margin-left:14px">
                <span>名称</span>&nbsp;&nbsp;
                <el-input
                  size="small"
                  v-model="param.groupName2"
                  maxlength="4"
                  show-word-limit
                  class="default_width"
                ></el-input>&nbsp;&nbsp;
                <span class="font-color">商品组名称，最多可填4个字</span>
              </div>
              <el-form-item
                prop="goodsNumber2"
                :inline-message="true"
              >
                <span>需选择</span>&nbsp;&nbsp;
                <el-input
                  size="small"
                  v-model="param.goodsNumber2"
                  class="default_width"
                ></el-input>&nbsp;&nbsp;件
                <span class="font-color">该商品组需要选购的商品数量，请填写正整数</span>
              </el-form-item>
            </div>
          </section>

          <section style="display: flex">
            <el-checkbox v-model="group3Flag">商品组3</el-checkbox>
            <div
              style="margin-left:20px"
              v-if="group3Flag === true"
            >
              <div style="margin-left:14px">
                <span>名称</span>&nbsp;&nbsp;
                <el-input
                  size="small"
                  v-model="param.groupName3"
                  maxlength="4"
                  show-word-limit
                  class="default_width"
                ></el-input>&nbsp;&nbsp;
                <span class="font-color">商品组名称，最多可填4个字</span>
              </div>
              <el-form-item
                prop="goodsNumber3"
                :inline-message="true"
              >
                <span>需选择</span>&nbsp;&nbsp;
                <el-input
                  size="small"
                  class="default_width"
                  v-model="param.goodsNumber3"
                ></el-input>&nbsp;&nbsp;件
                <span class="font-color">该商品组需要选购的商品数量，请填写正整数</span>
              </el-form-item>
            </div>
          </section>
        </el-form-item>

        <el-form-item
          label="添加商品："
          required
        >
          <div>
            <span class="font-color">请给每个商品组分别添加商品</span>
            <div class="goods-area">
              <!-- hello -->

              <el-tabs
                v-model="activeName"
                type="card"
              >
                <el-tab-pane
                  label="第一组"
                  name="first"
                >
                  <el-form-item>
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
                  </el-form-item>
                </el-tab-pane>

                <el-tab-pane
                  label="第二组"
                  name="second"
                  v-if="group2Flag === true"
                >
                  <section>
                    <div
                      class="add-btn"
                      @click="addGoods2"
                    >+ 添加商品</div>
                    <div v-if="goodsList2 && goodsList2.length >0">
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
                            v-for="(item, index) in goodsList2"
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
                            <td @click="deleteGoods2(index)">
                              <span style="cursor:pointer">删除</span>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </div>

                    <div
                      class="add-btn"
                      @click="addPlateForm2"
                    >+ 添加平台分类</div>
                    <table
                      class="brand_table"
                      v-if="platformList2 && platformList2.length"
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
                                @click="editPlateformClassification2"
                                v-if="!param.id"
                              >编辑</span>
                              <span
                                class="delete"
                                @click="deletePlateformClassfication2"
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
                            v-for="(item, index) in platformList2"
                            :key="index"
                          >
                            <span :class="{'first_cat': item.level === 0, 'second_cat': item.level === 1, 'third_cat': item.level === 2}">{{item.catName}}</span>
                          </div>
                        </tr>
                      </tbody>
                    </table>

                    <div
                      class="add-btn"
                      @click="selectBussiness2"
                    >+ 添加商家分类</div>
                    <table
                      class="brand_table"
                      v-if="bussinessList2 && bussinessList2.length > 0"
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
                                @click="editBussinessClassification2"
                                v-if="!param.id"
                              >编辑</span>
                              <span
                                class="delete"
                                @click="deleteBussinessClassfication2"
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
                            v-for="(item, index) in bussinessList2"
                            :key="index"
                          >
                            <span :class="{'first_cat': item.level === 0, 'second_cat': item.level === 1, 'third_cat': item.level === 2}">{{item.sortName}}</span>
                          </div>
                        </tr>
                      </tbody>
                    </table>
                  </section>
                </el-tab-pane>

                <el-tab-pane
                  label="第三组"
                  name="third"
                  v-if="group3Flag === true"
                >
                  <section>
                    <div
                      class="add-btn"
                      @click="addGoods3"
                    >+ 添加商品</div>
                    <div v-if="goodsList3 && goodsList3.length >0">
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
                            v-for="(item, index) in goodsList3"
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
                            <td @click="deleteGoods3(index)">
                              <span style="cursor:pointer">删除</span>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </div>

                    <div
                      class="add-btn"
                      @click="addPlateForm3"
                    >+ 添加平台分类</div>
                    <table
                      class="brand_table"
                      v-if="platformList3 && platformList3.length"
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
                                @click="editPlateformClassification3"
                                v-if="!param.id"
                              >编辑</span>
                              <span
                                class="delete"
                                @click="deletePlateformClassfication3"
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
                            v-for="(item, index) in platformList3"
                            :key="index"
                          >
                            <span :class="{'first_cat': item.level === 0, 'second_cat': item.level === 1, 'third_cat': item.level === 2}">{{item.catName}}</span>
                          </div>
                        </tr>
                      </tbody>
                    </table>

                    <div
                      class="add-btn"
                      @click="selectBussiness3"
                    >+ 添加商家分类</div>
                    <table
                      class="brand_table"
                      v-if="bussinessList3 && bussinessList3.length > 0"
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
                                @click="editBussinessClassification3"
                                v-if="!param.id"
                              >编辑</span>
                              <span
                                class="delete"
                                @click="deleteBussinessClassfication3"
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
                            v-for="(item, index) in bussinessList3"
                            :key="index"
                          >
                            <span :class="{'first_cat': item.level === 0, 'second_cat': item.level === 1, 'third_cat': item.level === 2}">{{item.sortName}}</span>
                          </div>
                        </tr>
                      </tbody>
                    </table>
                  </section>
                </el-tab-pane>
              </el-tabs>

            </div>
          </div>
        </el-form-item>
      </el-form>
    </div>

    <div class="footer">
      <el-button
        size="small"
        type="primary"
        class="button"
        @click="submitData"
      >保存</el-button>
    </div>

    <!-- 选择商品弹窗 -->
    <ChoosingGoods
      :tuneUpChooseGoods='tuneUpChooseGoodsDialog'
      :chooseGoodsBack="selectedGoodsIdList"
      @resultGoodsDatas="returnGoodsData"
    />
    <ChoosingGoods
      :tuneUpChooseGoods='tuneUpChooseGoodsDialog2'
      :chooseGoodsBack="selectedGoodsIdList2"
      @resultGoodsDatas="returnGoodsData2"
    />
    <ChoosingGoods
      :tuneUpChooseGoods='tuneUpChooseGoodsDialog3'
      :chooseGoodsBack="selectedGoodsIdList3"
      @resultGoodsDatas="returnGoodsData3"
    />

    <!-- 选择平台分类弹窗 -->
    <AddingBusClassDialog
      :dialogVisible.sync="tuneUpPlatformDialog"
      :backDataArr='platformIdList'
      :classFlag="2"
      @BusClassTrueDetailData="returnPlateformData"
    />
    <AddingBusClassDialog
      :dialogVisible.sync="tuneUpPlatformDialog2"
      :backDataArr='platformIdList2'
      :classFlag="2"
      @BusClassTrueDetailData="returnPlateformData2"
    />
    <AddingBusClassDialog
      :dialogVisible.sync="tuneUpPlatformDialog3"
      :backDataArr='platformIdList3'
      :classFlag="2"
      @BusClassTrueDetailData="returnPlateformData3"
    />

    <!-- 选择商家分类弹窗  -->
    <AddingBusClassDialog
      :dialogVisible.sync="tuneUpBussDialog"
      :backDataArr='bussinessIdList'
      :classFlag="1"
      @BusClassTrueDetailData="returnBusinessData"
    />
    <AddingBusClassDialog
      :dialogVisible.sync="tuneUpBussDialog2"
      :backDataArr='bussinessIdList2'
      :classFlag="1"
      @BusClassTrueDetailData="returnBusinessData2"
    />
    <AddingBusClassDialog
      :dialogVisible.sync="tuneUpBussDialog3"
      :backDataArr='bussinessIdList3'
      :classFlag="1"
      @BusClassTrueDetailData="returnBusinessData3"
    />

  </div>

</template>

<script>
import ChoosingGoods from '@/components/admin/choosingGoods'
import AddingBusClassDialog from '@/components/admin/addingBusClassDialog'
import { addActivity, getActivityInfo, updateActivity } from '@/api/admin/marketManage/packagePrice.js'

export default {
  components: { ChoosingGoods, AddingBusClassDialog },
  props: ['isEdite', 'editId'],
  mounted () {
    // 编辑初始化
    if (this.isEdite === true) {
      this.editPackagePriceInit()
    }
  },
  data () {
    var checkPrice = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error('请输入结算总价格'))
      } else {
        if (value < 0) {
          callback(new Error('结算总价格应该大于0'))
        }
        let flag = new RegExp('^[0-9]([0-9])*$').test(value)
        if (!flag) {
          callback(new Error('请输入正整数'))
        }
        callback()
      }
    }
    var checkDiscount = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error('请输入折扣数字'))
      } else {
        if (value <= 0 || value >= 10) {
          callback(new Error('折扣结算请填写大于0小于10的数字'))
        }
        var re = /^\d+(\.\d{1,2})?$/
        if (!re.test(value)) {
          callback(new Error('折扣数值请保留两位小数'))
        }
        callback()
      }
    }
    var checkoutNumber = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error('请输入数字'))
      } else {
        var re = /^[1-9]\d*$/
        if (!re.test(value)) {
          callback(new Error('请输入正整数'))
        }
        callback()
      }
    }
    var checkoutNumber2 = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error('请输入数字'))
      } else {
        var re = /^[1-9]\d*$/
        if (!re.test(value)) {
          callback(new Error('请输入正整数'))
        }
        callback()
      }
    }
    var checkoutNumber3 = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error('请输入数字'))
      } else {
        var re = /^[1-9]\d*$/
        if (!re.test(value)) {
          callback(new Error('请输入正整数'))
        }
        callback()
      }
    }
    return {
      group1Flag: true,
      group2Flag: false,
      group3Flag: false,
      param: {
        id: '',
        packageType: 0, // 活动类型
        packageName: '', // 活动名称
        validity: '', // 活动时间
        startTime: '',
        endTime: '',
        totalRatio: '', // 指定折扣
        totalMoney: '', // 总结算价格
        group1: {},
        group2: {},
        group3: {},
        groupName: '', // 商品组名称
        goodsNumber: '', // 至少需要选择件数
        groupName2: '',
        goodsNumber2: '',
        groupName3: '',
        goodsNumber3: ''
      },
      srcList: {
        src1: `${this.imageHost}/image/admin/new_preview_image/packagesale.jpg`
      },
      // 选择商品
      tuneUpChooseGoodsDialog: false,
      goodsList: [],
      selectedGoodsIdList: [],

      tuneUpChooseGoodsDialog2: false,
      goodsList2: [],
      selectedGoodsIdList2: [],

      tuneUpChooseGoodsDialog3: false,
      goodsList3: [],
      selectedGoodsIdList3: [],

      // 选择平台分类
      tuneUpPlatformDialog: false,
      platformList: [],
      platformIdList: [],

      tuneUpPlatformDialog2: false,
      platformList2: [],
      platformIdList2: [],

      tuneUpPlatformDialog3: false,
      platformList3: [],
      platformIdList3: [],

      // 选择商家分类
      tuneUpBussDialog: false,
      bussinessList: [],
      bussinessIdList: [],

      tuneUpBussDialog2: false,
      bussinessList2: [],
      bussinessIdList2: [],

      tuneUpBussDialog3: false,
      bussinessList3: [],
      bussinessIdList3: [],

      checkout: true,
      disabled: true,
      activeName: 'first',
      formRules: {
        packageName: { required: true, message: '请输入活动名称', trigger: 'blur' },
        validity: { required: true, message: '请选择活动时间', trigger: 'blur' },
        totalMoney: { required: true, validator: checkPrice, trigger: ['blur', 'change'] },
        totalRatio: { required: true, validator: checkDiscount, trigger: ['blur', 'change'] },
        goodsNumber: { validator: checkoutNumber, trigger: 'blur' },
        goodsNumber2: { validator: checkoutNumber2, trigger: 'blur' },
        goodsNumber3: { validator: checkoutNumber3, trigger: 'blur' }
      }
    }
  },
  watch: {
    'param.packageType': function (value) {
      console.log(value, 'value----')
      if (value) {
        this.$refs.form.validateField('packageType')
      }
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
      console.log(this.selectedGoodsIdList, 'getid')
    },
    // 添加商品-删除数据
    deleteGoods (index) {
      console.log(index, 'index-data')
      this.goodsList.splice(index, 1)
      this.selectedGoodsIdList.splice(index, 1)
    },

    // 添加商品-弹窗唤起2
    addGoods2 () {
      this.tuneUpChooseGoodsDialog2 = !this.tuneUpChooseGoodsDialog2
    },
    returnGoodsData2 (val) {
      console.log(val, 'goodsInfo')
      this.goodsList2 = val
      this.selectedGoodsIdList2 = val.map(item => item.goodsId)
      console.log(this.selectedGoodsIdList, 'getid')
    },
    deleteGoods2 (index) {
      console.log(index, 'index-data')
      this.goodsList2.splice(index, 1)
      this.selectedGoodsIdList2.splice(index, 1)
    },

    // 添加商品-弹窗唤起3
    addGoods3 () {
      this.tuneUpChooseGoodsDialog3 = !this.tuneUpChooseGoodsDialog3
    },
    returnGoodsData3 (val) {
      console.log(val, 'goodsInfo')
      this.goodsList3 = val
      this.selectedGoodsIdList3 = val.map(item => item.goodsId)
      console.log(this.selectedGoodsIdList, 'getid')
    },
    deleteGoods3 (index) {
      console.log(index, 'index-data')
      this.goodsList3.splice(index, 1)
      this.selectedGoodsIdList3.splice(index, 1)
    },

    // 添加平台分类
    addPlateForm () {
      this.tuneUpPlatformDialog = !this.tuneUpPlatformDialog
    },
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

    // 添加平台分类2
    addPlateForm2 () {
      this.tuneUpPlatformDialog2 = !this.tuneUpPlatformDialog2
    },
    returnPlateformData2 (val) {
      console.log(val, 'platform-data')
      this.platformList2 = val
      this.platformIdList2 = val.map(item => item.catId)
    },
    editPlateformClassification2 () {
      this.tuneUpPlatformDialog2 = !this.tuneUpPlatformDialog2
    },
    deletePlateformClassfication2 () {
      this.platformList2 = []
      this.platformIdList2 = []
    },

    // 添加平台分类3
    addPlateForm3 () {
      this.tuneUpPlatformDialog3 = !this.tuneUpPlatformDialog3
    },
    returnPlateformData3 (val) {
      console.log(val, 'platform-data')
      this.platformList3 = val
      this.platformIdList3 = val.map(item => item.catId)
    },
    editPlateformClassification3 () {
      this.tuneUpPlatformDialog3 = !this.tuneUpPlatformDialog3
    },
    deletePlateformClassfication3 () {
      this.platformList3 = []
      this.platformIdList3 = []
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
    },

    // 添加商家分类2
    selectBussiness2 () {
      this.tuneUpBussDialog2 = !this.tuneUpBussDialog2
    },
    returnBusinessData2 (val) {
      console.log(val, 'buiness data--')
      this.bussinessList2 = val
      this.bussinessIdList2 = val.map(item => item.sortId)
    },
    editBussinessClassification2 () {
      this.tuneUpBussDialog2 = !this.tuneUpBussDialog2
    },
    deleteBussinessClassfication2 () {
      this.bussinessList2 = []
      this.bussinessIdList2 = []
    },

    // 添加商家分类3
    selectBussiness3 () {
      this.tuneUpBussDialog3 = !this.tuneUpBussDialog3
    },
    returnBusinessData3 (val) {
      console.log(val, 'buiness data--')
      this.bussinessList3 = val
      this.bussinessIdList3 = val.map(item => item.sortId)
    },
    editBussinessClassification3 () {
      this.tuneUpBussDialog3 = !this.tuneUpBussDialog3
    },
    deleteBussinessClassfication3 () {
      this.bussinessList3 = []
      this.bussinessIdList3 = []
    },

    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    // 提交前校验
    validParam () {
      // let validateGroup1 = this.goodsList.lenght > 0 || this.platformList.lenght > 0 || this.bussinessList.length > 0
      if (this.goodsList.lenght === 0 || this.platformList.lenght === 0 || this.bussinessList.length === 0) {
        this.$message.warning('请选择第一组的商品')
        return false
      }
      return true
    },
    submitData () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let obj = {
            'packageType': this.param.packageType,
            'packageName': this.param.packageName,
            'startTime': this.param.validity[0],
            'endTime': this.param.validity[1],
            'totalMoney': this.param.totalMoney,
            'totalRatio': this.param.totalRatio,
            'group1': {
              'groupName': this.param.groupName,
              'goodsNumber': this.param.goodsNumber,
              'goodsIdList': this.selectedGoodsIdList,
              'catIdList': this.platformIdList,
              'sortIdList': this.bussinessIdList
            },
            'group2': {
              'groupName': this.param.groupName2,
              'goodsNumber': this.param.goodsNumber2,
              'goodsIdList': this.selectedGoodsIdList2,
              'catIdList': this.platformIdList2,
              'sortIdList': this.bussinessIdList2
            },
            'group3': {
              'groupName': this.param.groupName3,
              'goodsNumber': this.param.goodsNumber3,
              'goodsIdList': this.selectedGoodsIdList3,
              'catIdList': this.platformIdList3,
              'sortIdList': this.bussinessIdList3
            }
          }
          if (this.isEdite === false) {
            // if (this.validParam()) {
            addActivity(obj).then(res => {
              if (res.error === 0) {
                console.log(res, 'add res')
                this.$message.success('添加成功')
                this.$emit('packagePriceAddSubmit')
              } else {
                this.$message.error(res.message)
              }
            }).catch(err => console.log(err))
            // }
          } else {
            let obj1 = {
              id: this.editId
            }
            let requestParam = Object.assign(obj, obj1)
            console.log(requestParam)
            // 更新活动
            updateActivity(requestParam).then((res) => {
              if (res.error === 0) {
                this.$message.success('更新成功')
                this.$emit('packagePriceAddSubmit')
              } else {
                this.$message.error(res.error)
              }
            }).catch(err => console.log(err))
          }
        }
      })
    },

    // 编辑初始化
    editPackagePriceInit () {
      getActivityInfo(this.editId).then((res) => {
        console.log(res)
        if (res.error === 0) {
          var data = res.content
          this.param = data
          if (data.group2.groupName || data.group2.goodsNumber) {
            this.group2Flag = true
          }
          if (data.group3.groupName || data.group3.goodsNumber) {
            this.group3Flag = true
          }

          this.param.validity = [data.startTime, data.endTime]
          this.param.groupName = data.group1.groupName
          this.param.goodsNumber = data.group1.goodsNumber
          this.goodsList = data.group1.goodsList
          data.group1.goodsList.map(item => {
            this.selectedGoodsIdList.push(item.goodsId)
          })
          this.platformList = data.group1.cateVoList
          this.platformIdList = data.group1.catIdList
          this.bussinessList = data.group1.sortVoList
          this.bussinessIdList = data.group1.sortIdList

          // 第二组
          this.param.groupName2 = data.group2.groupName
          this.param.goodsNumber2 = data.group2.goodsNumber
          this.goodsList2 = data.group2.goodsList
          data.group2.goodsList.map(item => {
            this.selectedGoodsIdList2.push(item.goodsId)
          })
          this.platformList2 = data.group2.cateVoList
          this.platformIdList2 = data.group2.catIdList
          this.bussinessList2 = data.group2.sortVoList
          this.bussinessIdList2 = data.group2.sortIdList

          // 第三组
          this.param.groupName3 = data.group3.groupName
          this.param.goodsNumber3 = data.group3.goodsNumber
          this.goodsList3 = data.group3.goodsList
          data.group3.goodsList.map(item => {
            this.selectedGoodsIdList3.push(item.goodsId)
          })
          this.platformList3 = data.group3.cateVoList
          this.platformIdList3 = data.group3.catIdList
          this.bussinessList3 = data.group3.sortVoList
          this.bussinessIdList3 = data.group3.sortIdList
          console.log(this.param, 'get-return-data')
        } else {
          this.$message.error(res.message)
        }
      })
    }
  }
}

</script>
<style lang="scss" scoped>
.content {
  margin-top: 10px;
  padding: 15px;
  background: #fff;
  .mian {
    position: relative;
    margin-bottom: 52px;
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
  .footer {
    position: absolute;
    width: 100%;
    height: 52px;
    bottom: 0;
    left: 10px;
    right: 27px;
    padding: 10px 0;
    background-color: #fff;
    text-align: center;
  }
  .default_width {
    width: 105px;
  }
}
</style>
