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
        <el-form
          label-width="120px"
          :rules="rules"
          :model="params"
          ref="fullCutAddForm"
        >
          <!-- 活动名称 -->
          <el-form-item
            label="活动名称："
            prop="actName"
          >
            <el-input
              v-model="params.actName"
              placeholder="请输入活动名称"
              size="small"
              class="form_input"
            ></el-input>
          </el-form-item>

          <!-- 活动优先级 -->
          <el-form-item
            v-show="!params.id"
            label="活动优先级："
            prop="strategyPriority"
          >
            <el-input
              v-model="params.strategyPriority"
              placeholder="请输入活动优先级"
              class="form_input"
              size="small"
            ></el-input>
            <p class="form_tip">用于区分不同满折满减活动的优先级，请填写正整数，数值越大优先级越高</p>
          </el-form-item>

          <!-- 活动类型 -->
          <el-form-item
            label="活动类型："
            prop="type"
          >
            <el-radio-group
              v-model="params.type"
              :disabled="params.id"
            >
              <el-radio label="2">满减</el-radio>
              <el-radio label="1">每满减</el-radio>
              <el-radio label="3">满折</el-radio>
              <el-radio label="4">仅第X件打折</el-radio>
            </el-radio-group>
            <p class="form_tip actTypeSaveTips">保存后不可编辑</p>
          </el-form-item>

          <!-- 满减金额 -->
          <el-form-item
            label="满减金额："
            prop="conditionAddParams"
          >
            <div v-if="params.type == 2">
              <el-radio
                label="1"
                v-model="fullDeduction"
                :disabled="params.id"
              >满金额</el-radio>
              <div
                v-for="(item, index) in conditionAddParams2"
                :key="'info'+ index"
              >
                <div
                  class="flex-block"
                  v-if="item.fullMoney !== null && item.fullMoney !== undefined"
                >
                  <span>满</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    v-model="conditionAddParams2[index].fullMoney"
                    :disabled="fullDeduction == 2"
                  ></el-input>&nbsp;元，
                  <span>减</span>
                  &nbsp;<el-input
                    v-if="fullDeduction == 2"
                    class="form_input"
                    size="small"
                    :disabled="fullDeduction == 2"
                  ></el-input>
                  <el-input
                    v-else
                    class="form_input"
                    size="small"
                    v-model="conditionAddParams2[index].reduceMoney"
                    :disabled="fullDeduction == 2"
                  ></el-input>&nbsp;
                  <span>元</span>
                  <div class="iconAdd">
                    <img
                      v-if="index === 0"
                      :src="$imageHost + '/image/admin/sign_jia.png'"
                      alt=""
                      @click="addFullCutItemA()"
                    >
                    <img
                      v-else
                      style="cursor:pointer"
                      :src="$imageHost +'/image/admin/sign_del.png' "
                      @click="deleteFullCutItemA(index)"
                    >
                  </div>
                </div>
              </div>
              <el-radio
                label="2"
                v-model="fullDeduction"
                :disabled="params.id"
              >满件数</el-radio>
              <div
                v-for="(item, index) in conditionAddParams2"
                :key="'info2'+index"
              >
                <div
                  class="flex-block"
                  v-if="item.amount !== null && item.amount !== undefined"
                >
                  <span>满</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    v-model="conditionAddParams2[index].amount"
                    :disabled="fullDeduction == 1"
                  ></el-input>&nbsp;件，
                  <span>减</span>
                  &nbsp;<el-input
                    v-if="fullDeduction == 1"
                    class="form_input"
                    size="small"
                    :disabled="fullDeduction == 1"
                  ></el-input>
                  <el-input
                    v-else
                    class="form_input"
                    size="small "
                    v-model="conditionAddParams2[index].reduceMoney"
                    :disabled="fullDeduction == 1"
                  ></el-input>&nbsp;
                  <span>元</span>
                  <div class="iconAdd">
                    <img
                      v-if="index === 0"
                      :src="$imageHost + '/image/admin/sign_jia.png'"
                      alt=""
                      @click="addFullCutItemB()"
                    >
                    <img
                      v-else
                      style="cursor:pointer"
                      :src="$imageHost +'/image/admin/sign_del.png' "
                      @click="deleteFullCutItemB(index)"
                    >
                  </div>
                </div>
              </div>
            </div>
            <div v-else-if="params.type == 1">
              <el-radio
                label="1"
                v-model="fullDeduction"
              >满金额</el-radio>
              <div class="flex-block">
                <span>满</span>
                &nbsp;<el-input
                  class="form_input"
                  size="small"
                  v-model="conditionAddParams1[0].fullMoney"
                  :disabled="fullDeduction == 2"
                ></el-input>&nbsp;元，
                <span>减</span>
                &nbsp;<el-input
                  v-if="fullDeduction == 2"
                  class="form_input"
                  size="small"
                  :disabled="fullDeduction == 2"
                ></el-input>
                <el-input
                  v-else
                  class="form_input"
                  size="small"
                  v-model="conditionAddParams1[0].reduceMoney"
                  :disabled="fullDeduction == 2"
                ></el-input>&nbsp;
                <span>元</span>
              </div>
              <el-radio
                label="2"
                v-model="fullDeduction"
              >满件数</el-radio>
              <div class="flex-block">
                <span>满</span>
                &nbsp;<el-input
                  class="form_input"
                  size="small"
                  v-model="conditionAddParams1[0].amount"
                  :disabled="fullDeduction == 1"
                ></el-input>&nbsp;件，
                <span>减</span>
                &nbsp;<el-input
                  v-if="fullDeduction == 1"
                  class="form_input"
                  size="small"
                  :disabled="fullDeduction == 1"
                ></el-input>
                <el-input
                  v-else
                  class="form_input"
                  size="small "
                  v-model="conditionAddParams1[0].reduceMoney"
                  :disabled="fullDeduction == 1"
                ></el-input>&nbsp;
                <span>元</span>
              </div>
              <!-- <span>每满</span>
              &nbsp;<el-input
                class="form_input"
                size="small"
                v-model="conditionAddParams1.fullMoney"
              ></el-input>&nbsp;元，
              <span>减</span>
              &nbsp;<el-input
                class="form_input"
                size="small"
                v-model="conditionAddParams1.reduceMoney"
              ></el-input>&nbsp;元 -->
            </div>
            <div v-else-if="params.type == 3">
              <el-radio
                label="1"
                v-model="fullDeduction"
              >满金额</el-radio>
              <div
                v-for="(item, index) in conditionAddParams3"
                :key="'info1'+index"
              >
                <div
                  class="flex-block"
                  v-if="item.fullMoney !== null && item.fullMoney !== undefined"
                >
                  <span>满</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    v-model="conditionAddParams3[index].fullMoney"
                    :disabled="fullDeduction == 2"
                  ></el-input>&nbsp;元，
                  <span>打</span>
                  &nbsp;
                  <el-input
                    v-if="fullDeduction == 2"
                    class="form_input"
                    size="small"
                    :disabled="fullDeduction == 2"
                  ></el-input>
                  <el-input
                    v-else
                    class="form_input"
                    size="small "
                    v-model="conditionAddParams3[index].discount"
                    :disabled="fullDeduction == 2"
                  ></el-input>&nbsp;
                  <span>折</span>
                  <div class="iconAdd">
                    <img
                      v-if="index === 0"
                      :src="$imageHost + '/image/admin/sign_jia.png'"
                      alt=""
                      @click="addFullCutItemC()"
                    >
                    <img
                      v-else
                      style="cursor:pointer"
                      :src="$imageHost +'/image/admin/sign_del.png' "
                      @click="deleteFullCutItemC(index)"
                    >
                  </div>
                </div>
              </div>
              <el-radio
                label="2"
                v-model="fullDeduction"
              >满件数</el-radio>
              <div
                v-for="(item, index) in conditionAddParams3"
                :key="'info3'+index"
              >
                <div
                  class="flex-block"
                  v-if="item.amount !== null && item.amount !== undefined"
                >
                  <span>满</span>
                  &nbsp;<el-input
                    class="form_input"
                    size="small"
                    v-model="conditionAddParams3[index].fullMoney"
                    :disabled="fullDeduction == 1"
                  ></el-input>&nbsp;件，
                  <span>打</span>
                  &nbsp;
                  <el-input
                    v-if="fullDeduction == 1"
                    class="form_input"
                    size="small"
                    :disabled="fullDeduction == 1"
                  ></el-input>
                  <el-input
                    v-else
                    class="form_input"
                    size="small "
                    v-model="conditionAddParams3[index].reduceMoney"
                    :disabled="fullDeduction == 1"
                  ></el-input>&nbsp;
                  <span>折</span>
                  <div class="iconAdd">
                    <img
                      v-if="index === 0"
                      :src="$imageHost + '/image/admin/sign_jia.png'"
                      alt=""
                      @click="addFullCutItemD()"
                    >
                    <img
                      v-else
                      style="cursor:pointer"
                      :src="$imageHost +'/image/admin/sign_del.png' "
                      @click="deleteFullCutItemD(index)"
                    >
                  </div>
                </div>
              </div>
            </div>
            <div v-else-if="params.type == 4">
              <div>
                第&nbsp;<el-input
                  class="form_input"
                  size="small"
                  v-model="conditionAddParams4[0].amount"
                ></el-input>&nbsp;件，
                打&nbsp;<el-input
                  class="form_input"
                  size="small"
                  v-model="conditionAddParams4[0].discount"
                ></el-input>&nbsp;折
              </div>
            </div>
          </el-form-item>

          <!-- 有效期 -->
          <el-form-item
            label="有效期："
            prop="timeInterval"
          >
            <el-date-picker
              v-model="params.timeInterval"
              type="datetimerange"
              size="small"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00', '23:59:59']"
            ></el-date-picker>
          </el-form-item>

          <!-- 活动商品 -->
          <el-form-item
            label="活动商品："
            prop="actType"
          >
            <el-radio
              v-model="params.actType"
              label="0"
            >全部商品</el-radio>
            <br>
            <el-radio
              v-model="params.actType"
              label="1"
            >指定商品</el-radio>

            <div v-if="params.actType === '1'">
              <div
                class="add_goods_btn"
                @click="chooseGoodsHandler"
              > + 选择商品</div>
              <div
                class="goods_area"
                v-if="goodsList && goodsList.length > 0"
              >
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
                  <tbody class="tbody">
                    <tr
                      v-for="(item, index) in goodsList"
                      :key="item.name"
                    >
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
                      <td @click="deleteGoods(index)"><span class="edit-icon">删除</span></td>
                    </tr>
                  </tbody>
                </table>
              </div>

              <div
                class="add_goods_btn"
                @click="selectPlatform"
              > + 选择平台分类</div>
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
                class="add_goods_btn"
                @click="selectBussiness"
              > + 选择商家分类</div>
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

              <div
                class="add_goods_btn"
                @click="selectGoodsLabel"
              > + 选择商品标签</div>
              <table
                class="brand_table"
                v-if="labelNameList && labelNameList.length > 0"
              >
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
              <table
                class="brand_table"
                v-if="goodsNameList && goodsNameList.length > 0"
              >
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
            </div>
          </el-form-item>

          <!-- 会员专享活动 -->
          <el-form-item label="会员专享活动：">
            <el-checkbox v-model="checkCard">用户持会员卡可以参与活动</el-checkbox>
            <br>
            <el-form-item
              v-if="checkCard"
              prop="memberCards"
            >
              <el-select
                v-model="memberCardInfo"
                placeholder="请选择"
                size="small"
                :multiple='true'
                @change="getMemberCardName"
                style="float:left;"
              >
                <el-option
                  v-for="item in memberCardNameList"
                  :key="item.id"
                  :label="item.cardName"
                  :value="item.id"
                ></el-option>
              </el-select>
              <div style="padding-left: 10px; float:left;">
                <el-button
                  type="text"
                  @click="memberCardActivityName"
                >刷新</el-button>
                <el-divider direction="vertical"></el-divider>
                <!-- <el-button>新建会员卡</el-button> -->
                <router-link
                  class="link"
                  to="/admin/home/main/normalCardDetail?cardType=0"
                >新建会员卡</router-link>
                <el-divider direction="vertical"></el-divider>
                <!-- <el-button>管理会员卡</el-button> -->
                <router-link
                  class="link"
                  to="/admin/home/main/user_card"
                >管理会员卡</router-link>
              </div>
            </el-form-item>
          </el-form-item>
        </el-form>
      </div>

      <!-- 选择商品弹窗 -->
      <ChoosingGoods
        :tuneUpChooseGoods='tuneUpChooseGoodsDialog'
        :chooseGoodsBack="selectedGoodsIdList"
        @resultGoodsDatas="returnGoodsData"
      />

      <!-- 选择商家分类弹窗  -->
      <AddingBusClassDialog
        :dialogVisible.sync="tuneUpBussDialog"
        :backDataArr='bussinessIdList'
        :classFlag="1"
        @BusClassTrueDetailData="returnBusinessData"
      />

      <!-- 选择平台分类弹窗 -->
      <AddingBusClassDialog
        :dialogVisible.sync="tuneUpPlatformDialog"
        :backDataArr='platformIdList'
        :classFlag="2"
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
import { addFullCutActivityApi, updateFullCut, memberCardActivityName, getOneFullCutActivityInfo } from '@/api/admin/marketManage/fullDiscountFullCut'
import ChoosingGoods from '@/components/admin/choosingGoods'
import AddingBusClassDialog from '@/components/admin/addingBusClassDialog'
import AddBrandDialog from '@/components/admin/addBrandDialog'
import SelectGoodsLabel from '@/components/admin/addProductLabel'

export default {
  components: { ChoosingGoods, AddingBusClassDialog, AddBrandDialog, SelectGoodsLabel },
  data () {
    let that = this
    // 自定义校验规则
    var validateLevel = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if (!value) {
        callback(new Error('请输入活动优先级'))
      } else if (!re.test(value)) {
        callback(new Error('活动等级为正整数'))
      }
      callback()
    }
    var validateActType = (rule, value, callback) => {
      if (value === '1' && (that.goodsList.length === 0 && that.goodsNameList.length === 0 && that.labelNameList.length === 0 && that.bussinessList.length === 0 && that.platformList.length === 0)) {
        callback(new Error('请选择商品信息'))
      } else {
        callback()
      }
    }
    function validateCheckCard (rule, value, callback) {
      if (that.checkCard) {
        if (that.memberCardInfo === '' || (that.memberCardInfo && that.memberCardInfo.length === 0)) {
          callback(new Error('请选择专享会员卡'))
        }
        callback()
      }
    }
    function validateConditionAdd (rule, value, callback) {
      let conditionAddParams = that['conditionAddParams' + that.params.type]
      if (!conditionAddParams || conditionAddParams.length <= 0) {
        callback(new Error('请设置满减规则'))
      }
      conditionAddParams.forEach(item => {
        if (!((item.reduceMoney && item.fullMoney) || (item.amount && item.reduceMoney) || (item.reduceMoney && item.discount) || (item.amount && item.discount))) {
          callback(new Error('请完善满减规则'))
        }
      })
      callback()
    }
    return {
      params: {
        actName: '',
        strategyPriority: '',
        id: '',
        type: '2',
        condition: [],
        timeInterval: [],
        startTime: '',
        endTime: '',
        status: '',
        recommendGoods: null,
        memberCards: null,
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: '',
        actType: '0'
      },
      activeName: '5',
      fullDeduction: '1',
      aaa: 11,
      memberCardInfo: '',
      memberCardNameList: [], // 会员专享活动
      memberCardNameIdLidt: [],
      cardId: [], // 会员专享改变时的id值
      flag: null,
      conditionAddParams: [{
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: ''
      }],
      // 满x元减x元
      conditionAddParams1: [{
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: ''
      }],
      // 满x件减x元
      conditionAddParams2: [{
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: ''
      }],
      // 满x元打x折
      conditionAddParams3: [{
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: ''
      }],
      // 满x件打x折
      conditionAddParams4: [{
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: ''
      }],
      // 选择商品
      tuneUpChooseGoodsDialog: false,
      selectedGoodsIdList: [],
      goodsList: [],
      // 选择商品品牌
      tuneUpBrandDialog: false,
      goodsNameList: [],
      goodsBrandIdList: [],
      // 选择商品标签
      tuneUpSelectGoodsLabelDialog: false,
      labelNameList: [],
      labelNameIdList: [],
      // 选择商家分类
      tuneUpBussDialog: false,
      bussinessList: [],
      bussinessIdList: [],
      // 选择平台分类
      tuneUpPlatformDialog: false,
      platformList: [],
      platformIdList: [],
      checkCard: false, // 是否选择会员卡

      rules: {
        actName: [{ required: true, message: '请输入活动名称', trigger: 'blur' }],
        strategyPriority: { required: true, validator: validateLevel, trigger: 'blur' },
        type: [{ required: true, message: '请选择活动类型', trigger: 'change' }],
        timeInterval: [{ required: true, message: '请选择时间', trigger: 'blur' }],
        actType: { required: true, validator: validateActType, trigger: 'change' },
        conditionAddParams: [{ required: true, validator: validateConditionAdd, trigger: 'blur' }],
        memberCards: [{ validator: validateCheckCard, trigger: 'change' }]
      }
    }
  },
  watch: {
    'params.timeInterval': function (newVal) {
      this.params.startTime = newVal[0]
      this.params.endTime = newVal[1]
    },
    // 监听满金额满件数切换
    fullDeduction: function (newVal) {
      let type = this.params.type
      this['conditionAddParams' + type] = [{
        fullMoney: '',
        reduceMoney: '',
        amount: '',
        discount: ''
      }]
    }
  },
  mounted () {
    this.memberCardActivityName()
    console.log(this.$route.query, 'this.$router.id')
    if (this.$route.query.id > 0) {
      this.params.id = this.$route.query.id
      this.fetchCurrentActivityData()
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
    memberCardActivityName () {
      memberCardActivityName().then(res => {
        console.log(res, 'memberCardActivityName')
        this.memberCardNameList = res.content
        this.memberCardNameIdLidt = res.content.map(item => item.id)
        console.log(this.memberCardNameIdLidt, 'memberCardNameIdLidt')
      })
    },
    getMemberCardName (val) {
      console.log(val, 'get value')
      this.cardId = val
      console.log(this.cardId, 'saveMemberIdList')
    },
    getTabName () {
      if (this.$route.query.id) {
        return '修改满折满减活动'
      } else {
        return '添加满折满减活动'
      }
    },
    // 同步满折满减规则
    syncConditionAddParams () {
      let type = this.params.type
      let conditionAddParams = this['conditionAddParams' + type]
      this.conditionAddParams = conditionAddParams.filter((item, index) => {
        return (item.reduceMoney && item.fullMoney) || (item.amount && item.reduceMoney) || (item.reduceMoney && item.discount) || (item.amount && item.discount)
      })
    },
    // 提交
    submit () {
      let that = this
      // 校验
      this.$refs.fullCutAddForm.validate((valid) => {
        if (valid) {
          this.syncConditionAddParams()
          let obj = {
            actName: this.params.actName,
            type: this.params.type,
            actType: this.params.actType,
            conditionAddParams: this.conditionAddParams,
            startTime: this.params.startTime,
            endTime: this.params.endTime,
            strategyPriority: this.params.strategyPriority, // 优先级
            recommendGoodsId: String(this.selectedGoodsIdList), // 指定商品
            recommendCatId: String(this.platformIdList), // 指定平台
            recommendSortId: String(this.bussinessIdList), // 指定商家
            recommendBrandId: String(this.goodsBrandIdList), // 指定品牌
            cardId: String(this.cardId) // 会员专享活动
          }
          console.log(obj)
          // 更新满折满减活动
          if (that.params.id) {
            obj.id = that.params.id
            updateFullCut(obj).then(res => {
              console.log(res)
              if (res.error === 0) {
                that.$message.success('更新成功')
                that.$router.replace('/admin/home/main/fullDiscountFullCut')
              } else {
                that.$message.warning('更新失败')
              }
            })
          } else {
            // 添加满折满减活动
            addFullCutActivityApi(obj).then(res => {
              console.log(res)
              if (res.error === 0) {
                that.$message.success('保存成功')
                that.$router.replace('/admin/home/main/fullDiscountFullCut')
              } else {
                that.$message.warning('保存失败')
              }
            })
          }
        }
      })
    },
    // 添加满减：满X元减X元
    addFullCutItemA () {
      let obj = {
        'fullMoney': '',
        'reduceMoney': ''
      }
      this.conditionAddParams2.push(obj)
    },
    // 添加满减：满X元减X元
    deleteFullCutItemA (index) {
      if (index <= 0) return
      this.conditionAddParams2.splice(index, 1)
    },
    // 添加满减：满X件减X元
    addFullCutItemB () {
      let obj = {
        'amount': '',
        'reduceMoney': ''
      }
      this.conditionAddParams2.push(obj)
    },
    // 删除满减：满X件减X元
    deleteFullCutItemB (index) {
      if (index <= 0) return
      this.conditionAddParams2.splice(index, 1)
    },
    // 添加满折：满元打折
    addFullCutItemC () {
      let obj = {
        'fullMoney': '',
        'discount': ''
      }
      this.conditionAddParams3.push(obj)
    },
    // 删除满折：满元打折
    deleteFullCutItemC (index) {
      this.conditionAddParams3.splice(index, 1)
    },
    // 添加满折：满件打折
    addFullCutItemD () {
      let obj = {
        'amount': '',
        'discount': ''
      }
      this.conditionAddParams3.push(obj)
    },
    // 删除满折：满件打折
    deleteFullCutItemD (index) {
      this.conditionAddParams3.splice(index, 1)
    },
    // 选择商品数据处理
    chooseGoodsHandler () {
      this.tuneUpChooseGoodsDialog = !this.tuneUpChooseGoodsDialog
    },
    returnGoodsData (val) {
      console.log(val, 'goodsInfo')
      this.goodsList = val
      this.selectedGoodsIdList = val.map(item => item.goodsId)
    },
    deleteGoods (index) {
      console.log(index)
      this.goodsList.splice(index, 1)
      this.selectedGoodsIdList.splice(index, 1)
    },

    // 选择平台、商家分类数据处理
    selectPlatform () {
      this.tuneUpPlatformDialog = !this.tuneUpPlatformDialog
    },
    // 选择平台分类回调
    returnPlateformData (val) {
      console.log(val, 'platform data')
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
      this.goodsNameList = []
      this.idList = []
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
    },
    editSelectedLabel () {
      this.tuneUpSelectGoodsLabelDialog = !this.tuneUpSelectGoodsLabelDialog
    },
    deleteSelectedLabel () {
      this.labelNameList = []
      this.labelNameIdList = []
    },
    // 初始化数据
    fetchCurrentActivityData () {
      let that = this
      console.log(this.params.id)
      getOneFullCutActivityInfo({ id: this.params.id }).then(res => {
        if (res.error === 0) {
          let content = res.content
          that.params = Object.assign({}, that.params, content)
          // 优惠规则
          let type = content.type
          that.params.type = String(type)
          content.condition.forEach(obj => {
            for (const key in obj) {
              if (obj.hasOwnProperty(key)) {
                const value = obj[key]
                if (!value) {
                  obj[key] = ''
                }
              }
            }
          })
          that.conditionAddParams = content.condition || [{
            fullMoney: '',
            reduceMoney: '',
            amount: '',
            discount: ''
          }]
          that['conditionAddParams' + type] = content.condition || [{
            fullMoney: '',
            reduceMoney: '',
            amount: '',
            discount: ''
          }]
          // 有效期
          that.params.timeInterval = [content.startTime, content.endTime]
          // 活动范围类型
          that.params.actType = String(content.actType)
          // 指定商品
          that.selectedGoodsIdList = content.recommendGoodsIds || []
          that.goodsList = content.recommendGoods || []
          // 指定平台分类
          that.platformList = content.recommendCat || []
          that.platformIdList = content.recommendCatIds || []
          // 指定商家分类
          that.bussinessList = content.recommendSort || []
          that.bussinessIdList = content.recommendSortIds || []
          // 指定商品标签
          // 指定商品品牌
          that.labelNameList = content.recommendBrand || []
          that.labelNameIdList = content.recommendBrandIds || []
          // 指定会员卡
          that.memberCardInfo = content.cardIds || []
        }
      }).catch(err => console.log(err))
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
  .flex-block {
    display: flex;
    margin-left: 25px;
  }
  .edit-icon {
    cursor: pointer;
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
  .link {
    color: #409eff;
    text-decoration: none;
  }
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
        min-height: 100px;
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
