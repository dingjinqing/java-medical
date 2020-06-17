<!--
* 创建定金膨胀活动
* @author 赵鑫
-->
<template>
  <div>
    <div class="tab-wrapper">
      <el-tabs
        v-model="tabStatus"
        :before-leave="beforeLeave"
      >
        <el-tab-pane
          v-for="(item, index) in labels"
          :key="index"
          :label="item.name"
          :name="item.status"
        ></el-tab-pane>
      </el-tabs>
    </div>
    <div class="wrapper">
      <el-form
        label-width="130px"
        ref="param"
        :model="param"
        :rules="formRules"
      >
        <el-form-item
          :label="$t('preSale.activityType')+ '：'"
          prop="presaleType"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-for="(item, index) in presaleTypes"
            :key="index"
            v-model="param.presaleType"
            :label="index"
            @change="changePreAct"
          >{{item}}</el-radio>
        </el-form-item>
        <el-form-item
          :label="$t('preSale.activityName')+ '：'"
          prop="presaleName"
        >
          <el-input
            v-model="param.presaleName"
            :placeholder="$t('preSale.inputActivityName')"
            size="small"
            style="width:180px"
          ></el-input>
          <span style="color:#999;margin-left:10px;">{{$t('preSale.activityNameRemark')}}</span>
        </el-form-item>

        <el-form-item
          :label="$t('preSale.level')+ '：'"
          prop="first"
        >
          <el-input
            v-model="param.first"
            size="small"
            :placeholder="$t('preSale.inputLevel')"
            style="width: 180px"
          ></el-input>
          <span style="color:#999;margin-left:10px;">{{$t('preSale.levelRemark')}}</span>
        </el-form-item>

        <!-- 定金膨胀 -->
        <el-form-item
          v-show="!isFullPay"
          :label="$t('preSale.activityTime')"
          :rules="[{required: true}]"
        >
          <template>
            <div style="color:#999">{{$t('preSale.activityTimeRemark')}}</div>
            <el-form-item
              :label="$t('preSale.prePayTime')+'：'"
              :rules="[{required: true, message:'请填写定金支付时间', trigger: ['blur','change']}]"
              :inline-message="true"
              prop="preTime1Range"
            >
              <el-date-picker
                :disabled="isEditeFlag"
                v-model="param.preTime1Range"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                @change="dateChange(param.preTime1Range)"
                value-format="yyyy-MM-dd HH:mm:ss"
                size="small"
                :default-time="['00:00:00', '23:59:59']"
              >
              </el-date-picker>
              <el-button
                size="small"
                v-show="!twoSteps&&!isEditeFlag"
                @click="handleTwoState"
              >{{$t('preSale.addPayTime')}}</el-button>
            </el-form-item>

            <!-- 定金支付时间 -->
            <el-form-item
              :label="$t('preSale.prePayTime')+ '：'"
              :rules="[{required: true, message:'请填写定金支付时间', trigger: ['blur','change']}]"
              :inline-message="true"
              prop="preTime2Range"
              v-if="twoSteps&&!isFullPay"
            >
              <el-date-picker
                v-model="param.preTime2Range"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                size="small"
                :disabled="isEditeFlag"
                :default-time="['00:00:00', '23:59:59']"
                @change="dateChange2(param.preTime2Range)"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
              <el-button
                v-if="!isEditeFlag"
                size="small"
                @click="handleDelete"
              >{{$t('preSale.delete')}}</el-button>
            </el-form-item>

            <!-- 尾款支付时间 -->
            <el-form-item
              :label="$t('preSale.payEndTime')+ '：'"
              prop="tailPayTimeRange"
              :rules="[{required: true, message:'请填写尾款支付时间', trigger: ['blur','change']}]"
              :inline-message="true"
              v-if="!isFullPay"
            >
              <el-date-picker
                v-model="param.tailPayTimeRange"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                @change="endMoneyTime(param.tailPayTimeRange)"
                value-format="yyyy-MM-dd HH:mm:ss"
                size="small"
                :disabled="isEditeFlag"
                :default-time="['00:00:00', '23:59:59']"
              >
              </el-date-picker>
            </el-form-item>
          </template>
        </el-form-item>

        <!-- 全款预售 -->
        <el-form-item
          v-show="isFullPay"
          :label="$t('preSale.prePayTime')"
          :rules="[{required: true, message:'请填写定金支付时间', trigger: ['blur','change']}]"
          prop="preTime1Range"
        >
          <el-date-picker
            v-model="param.preTime1Range"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            size="small"
            :disabled="isEditeFlag"
            :default-time="['00:00:00', '23:59:59']"
          >
          </el-date-picker>
        </el-form-item>

        <!-- 活动预告 -->
        <el-form-item :label="$t('preSale.activityForecast')+ '：'">
          <div>
            <span style="color:#999">{{$t('preSale.activityForecastRemark')}}</span>
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="srcList.src3"></el-image>
              <el-button
                slot="reference"
                type="text"
              >{{$t('preSale.viewExample')}}</el-button>
            </el-popover>
          </div>
          <div>
            <el-radio-group v-model="activityType">
              <el-radio
                :label="1"
                :disabled="isEditeFlag"
              >
                {{$t('preSale.beforeForecast')}}
                <el-input
                  v-model="param.preTime"
                  :disabled="isEditeFlag"
                  style="width:80px"
                  size="small"
                ></el-input>
                {{$t('preSale.forcast')}}
              </el-radio>
              <el-radio
                :label="-1"
                :disabled="isEditeFlag"
              >{{$t('preSale.afterForecast')}}</el-radio>
              <el-radio
                :label="0"
                :disabled="isEditeFlag"
              >{{$t('preSale.NotForeacast')}}</el-radio>
            </el-radio-group>
          </div>
        </el-form-item>

        <!-- 活动商品 -->
        <el-form-item
          :label="$t('preSale.activityGoods')+ '：'"
          prop="goodsId"
        >
          <el-input
            :disabled="true"
            v-model="param.goodsName"
            v-if="param.goodsName"
            size="small"
            style="width: 170px;"
          ></el-input>

          <el-input
            :disabled="true"
            v-model="param.goodsId"
            size="small"
            style="width: 170px;display:none"
          ></el-input>
          <el-button
            :disabled="isEditeFlag"
            size="small"
            @click="showChoosingGoods"
          >{{$t('preSale.selectGoods')}}
          </el-button>
        </el-form-item>
        <el-form-item
          :label="$t('preSale.deleveryTime')"
          prop="deliverType"
        >
          <div style="display: flex">
            <el-radio
              :disabled="isEditeFlag"
              v-model="param.deliverType"
              :label="1"
              style="line-height: 40px"
              @change="changTime"
            >&nbsp;{{$t('preSale.specifiedDeleveryTime')}}</el-radio>
            <el-date-picker
              :disabled="param.deliverType==2 || isEditeFlag"
              v-model="param.deliverTime"
              type="datetime"
              size="small"
              style="width:190px"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </div>
          <div style="display:flex">
            <el-radio
              :disabled="isEditeFlag"
              v-model="param.deliverType"
              :label="2"
              style="line-height:40px"
              @change="changTime"
            >
              <span v-if="this.param.presaleType === 0">&nbsp;{{$t('preSale.endMoneyComplete')}}</span>
              <span v-if="this.param.presaleType === 1">&nbsp;{{$t('preSale.payEnd')}}</span>
            </el-radio>
            <el-input
              :disabled="param.deliverType==1 || isEditeFlag"
              v-model="param.deliverDays"
              size="small"
              style="width:180px"
              :min=0
            />
            <span style="margin-left:10px">{{$t('preSale.startDeleveryGodos')}}</span>
          </div>
        </el-form-item>
        <el-form-item
          :label="$t('preSale.discountStrategy')"
          prop="discountType"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-model="param.discountType"
            v-for="(item, index) in discountType"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">{{$t('preSale.discountRemark')}}</span>
        </el-form-item>
        <el-form-item
          :label="$t('preSale.preMoneyStrategy')"
          prop="returnType"
          v-show="param.presaleType === 0"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-model="param.returnType"
            v-for="(item, index) in returnTypes"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">{{$t('preSale.preMoneyRemark')}}</span>
        </el-form-item>
        <el-form-item
          :label="$t('preSale.preSaleNumber')"
          prop="showSaleNumber"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-model="param.showSaleNumber"
            v-for="(item, index) in showSaleNumberTypes"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">{{$t('preSale.preSaleNumberRemark')}}</span>
        </el-form-item>
        <el-form-item
          :label="$t('preSale.goodsBuyType')"
          prop="buyType"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-model="param.buyType"
            v-for="(item, index) in buyTypes"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">{{$t('preSale.goodsBuyTypeRemark')}}</span>
        </el-form-item>
      </el-form>
      <el-form
        ref="param-s"
        :model="param"
      >
        <el-form-item>
          <el-table
            header-row-class-name="tableHeader"
            :data="param.products"
            border
            style="width: 100%"
            empty-text="暂无数据"
          >
            <el-table-column
              prop="goodsName"
              :label="$t('preSale.goodsName')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="shopPrice"
              :label="$t('preSale.goodsPrice')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="goodsNumber"
              :label="$t('preSale.goodsNumber')"
              align="center"
            >
            </el-table-column>

            <el-table-column
              align="center"
              :label="$t('preSale.activityPrice')"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.presalePrice'"
                  :rules="[{ validator: (rule, value, callback)=>{validateMoney(rule, value, callback, scope.row.shopPrice, scope.row)}, trigger: ['blur', 'change'] }]"
                >
                  <el-input
                    v-model="scope.row.presalePrice"
                    @input="changePriceInput(scope.row)"
                    size="small"
                  />
                </el-form-item>
                <div
                  class="spec-tips"
                  @click="showSpec(scope.row)"
                  v-if="scope.row.goodsSpecProducts&&scope.row.goodsSpecProducts.length > 0"
                >包含{{scope.row.goodsSpecProducts.length}}个规格</div>
              </template>
            </el-table-column>

            <!-- 活动库存 -->
            <el-table-column
              align="center"
              prop="presaleNumber"
              :label="$t('preSale.activityNumber')"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.presaleNumber'"
                  :rules="[{ validator: (rule, value, callback)=>{validateNum(rule, value, callback, scope.row.goodsNumber, scope.row, scope.$index)}, trigger: ['blur', 'change']}]"
                >
                  <el-input
                    v-model="scope.row.presaleNumber"
                    @input="changeStockInput(scope.row)"
                    size="small"
                  />
                </el-form-item>
                <div
                  class="spec-tips"
                  @click="showSpec(scope.row)"
                  v-if="scope.row.goodsSpecProducts&&scope.row.goodsSpecProducts.length > 0"
                >包含{{scope.row.goodsSpecProducts.length}}个规格；库存合计：{{scope.row.totalStock ? scope.row.totalStock : 0}}</div>
              </template>
            </el-table-column>

            <!-- 定金 -->
            <el-table-column
              align="center"
              prop="presaleMoney"
              :label="$t('preSale.preMoney')"
              v-if="param.presaleType===0"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.presaleMoney'"
                  :rules="[{ validator: (rule, value, callback)=>{validateReadyMoney(rule, value, callback, scope.row.presalePrice, scope.row)}, trigger: ['blur', 'change']}]"
                >
                  <el-input
                    v-model="scope.row.presaleMoney"
                    @input="changeEarnestMoney(scope.row)"
                    size="small"
                  />
                </el-form-item>
                <div
                  class="spec-tips"
                  @click="showSpec(scope.row)"
                  v-if="scope.row.goodsSpecProducts&&scope.row.goodsSpecProducts.length > 0"
                >包含{{scope.row.goodsSpecProducts.length}}个规格</div>
              </template>
            </el-table-column>

            <!-- 1阶段定金可抵扣金额 -->
            <el-table-column
              align="center"
              prop="preDiscountMoney1"
              :label="$t('preSale.state1')"
              v-if="!isFullPay"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.preDiscountMoney1'"
                  :rules="[{ validator: (rule, value, callback)=>{validateFirstStage(rule, value, callback, scope.row.presaleMoney, scope.row.presalePrice)}, trigger: ['blur', 'change']}]"
                >
                  <el-input
                    v-model="scope.row.preDiscountMoney1"
                    @input="changeDiscountMoney1(scope.row)"
                    size="small"
                  />
                </el-form-item>
                <div
                  class="spec-tips"
                  @click="showSpec(scope.row)"
                  v-if="scope.row.goodsSpecProducts&&scope.row.goodsSpecProducts.length > 0"
                >包含{{scope.row.goodsSpecProducts.length}}个规格</div>
              </template>
            </el-table-column>

            <!-- 2阶段定金可抵扣金额 -->
            <el-table-column
              align="center"
              prop="preDiscountMoney2"
              :label="$t('preSale.state2')"
              v-if="twoSteps&&!isFullPay"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.preDiscountMoney2'"
                  :rules="[{ validator: (rule, value, callback)=>{validateSecondStage(rule, value, callback, scope.row.presalePrice, scope.row.presaleMoney)}, trigger: ['blur', 'change']}]"
                >
                  <el-input
                    v-model="scope.row.preDiscountMoney2"
                    @input="changeDiscountMoney2(scope.row)"
                    size="small"
                  />
                </el-form-item>
                <div
                  class="spec-tips"
                  @click="showSpec(scope.row)"
                  v-if="scope.row.goodsSpecProducts&&scope.row.goodsSpecProducts.length > 0"
                >包含{{scope.row.goodsSpecProducts.length}}个规格</div>
              </template>
            </el-table-column>

            <!-- 操作 -->
            <el-table-column
              :label="$t('preSale.operate')"
              align="center"
              v-if="!isEditeFlag"
            >
              <template slot-scope="scope">
                <div
                  v-if="scope.row.goodsId"
                  @click="deleteGoods(scope.row, scope.row.goodsId)"
                  style="cursor:pointer;color:#409eff"
                >{{$t('preSale.delete')}}</div>
              </template>
            </el-table-column>

            <template
              slot="empty"
              style="height：0"
            >
            </template>
            <div
              slot="append"
              class="moreSetUp"
            >
              <span style="display: inline-block">{{$t('preSale.moreSetting')+'：'}}</span>
              <a
                :class="activeIndex === 1 ? '' : 'settings'"
                @click="setCurrent(1)"
              >{{$t('preSale.activityPrice')}}
              </a>
              <a
                :class="activeIndex === 2 ? '' : 'settings'"
                @click="setCurrent(2)"
              >{{$t('preSale.activityNumber')}}
              </a>
              <a
                :class="activeIndex === 3 ? '' : 'settings'"
                @click="setCurrent(3)"
                v-if="param.presaleType===0"
              >{{$t('preSale.preMoney')}}
              </a>
              <a
                :class="activeIndex === 4 ? '' : 'settings'"
                @click="setCurrent(4)"
                v-show="!isFullPay"
              >{{$t('preSale.state1')}}
              </a>
              <a
                :class="activeIndex === 5 ? '' : 'settings'"
                @click="setCurrent(5)"
                v-show="twoSteps&&!isFullPay"
              >{{$t('preSale.state2')}}
              </a>
            </div>

          </el-table>
        </el-form-item>
      </el-form>

      <!-- 收起、展开更多配置 -->
      <div
        @click="handleToChangeArror()"
        style="margin: 20px 0 10px"
      >
        <div
          v-if="arrorFlag"
          style="color:rgb(90, 139, 255);cursor:pointer"
        >{{$t('groupBuy.moreConfigure')}}&nbsp;<img :src="ArrowArr[0].img_1"></div>
        <div
          v-if="!arrorFlag"
          style="color:rgb(90, 139, 255);cursor:pointer"
        >{{$t('groupBuy.packUpConfigure')}}&nbsp;<img :src="ArrowArr[1].img_2"></div>
      </div>
      <div v-if="!arrorFlag">
        <el-form
          label-width="120px"
          v-show="showMore"
          ref="param-share"
          :model="param"
        >
          <el-form-item
            :label="$t('preSale.numberLimut')"
            prop="buyNumber"
            :rules="[{ validator: (rule, value, callback)=>{validateBuyNumber(rule, value, callback)}, trigger: ['blur', 'change']}]"
          >
            <div style="display:flex">
              <span>{{$t('preSale.singleUser')}}</span>
              <el-input
                :disabled="isEditeFlag"
                v-model="param.buyNumber"
                size="small"
                style="width:180px;margin:0 10px;"
              ></el-input>
              <span>{{$t('preSale.singleUserNumber')}}</span>
              <span
                class="textColor"
                style="margin-left:20px;"
              >{{$t('preSale.singleUserRemark')}}</span>
            </div>
          </el-form-item>
          <el-form-item :label="$t('preSale.actShare')">
            <div>
              <el-radio
                v-model="param.shareAction"
                :label=1
              >{{$t('preSale.defaultStyle')}}</el-radio>
              <el-popover
                placement="right-start"
                width="220"
                trigger="hover"
              >
                <el-image :src="srcList.src1"></el-image>
                <el-button
                  slot="reference"
                  type="text"
                  style="margin: 0 20px"
                >{{$t('preSale.viewExample')}}</el-button>
              </el-popover>
              <el-popover
                placement="right-start"
                width="220"
                trigger="hover"
              >
                <el-image :src="srcList.src2"></el-image>
                <el-button
                  slot="reference"
                  type="text"
                >{{$t('preSale.downloadPoster')}}</el-button>
              </el-popover>
            </div>
            <div>
              <el-radio
                v-model="param.shareAction"
                :label=2
              >{{$t('preSale.customStyle')}}</el-radio>
              <div
                v-if="param.shareAction === 2"
                style="margin-left: 25px"
              >
                <span>{{$t('preSale.document')+'：'}}</span>
                <el-input
                  v-model="param.shareDoc"
                  size="small"
                  style="width:150px"
                ></el-input>
              </div>
              <div
                v-if="param.shareAction === 2"
                style="margin-left: 25px"
              >
                <!-- <span>分享图：</span> -->
                <span>{{$t('preSale.sharePicture')+'：'}}</span>
                <el-radio
                  v-model="param.shareImgAction"
                  :label=1
                >{{$t('preSale.actGoodsPic')}}</el-radio>
                <div style="margin-left: 60px;">
                  <el-radio
                    v-model="param.shareImgAction"
                    :label=2
                  >{{$t('preSale.customPic')}}</el-radio>
                </div>

                <div
                  style="display: flex"
                  v-if="param.shareImgAction === 2"
                >
                  <div
                    class="imgContent"
                    @click="addGoodsImg"
                  >
                    <div>
                      <img
                        v-if="param.shareImg === '' || param.shareImg === null"
                        :src="$imageHost + '/image/admin/btn_add.png'"
                        alt=""
                      >
                      <img
                        v-if="param.shareImg !== ''"
                        :src="$imageHost + '/' + param.shareImg"
                        alt=""
                        class="shareImg"
                      >
                    </div>
                  </div>
                  <span class="picSizeTips">{{$t('preSale.pictureSize')}}</span>
                </div>

              </div>
            </div>
          </el-form-item>
        </el-form>
      </div>

      <div class="footer">
        <el-button
          size="small"
          type="primary"
          @click="add"
        >{{$t('preSale.save')}}
        </el-button>
      </div>

      <!--添加商品弹窗-->
      <choosingGoods
        @resultGoodsDatas="choosingGoodsResult"
        :chooseGoodsBack="goodsIdList"
        :tuneUpChooseGoods="isShowChoosingGoodsDialog"
        :singleElection="false"
        :showTips="true"
      />

      <!-- 选择图片弹框 -->
      <ImageDalog
        pageIndex='pictureSpace'
        :tuneUp="showImageDialog"
        @handleSelectImg='handleSelectImg'
        :imageSize="[800, 800]"
      />

      <!-- 信息规格弹窗 -->
      <preSaleDialog
        :productDialog.sync="showSpecDialog"
        :product-info="productInfo"
        :isShowTwoStageMoney="showTwoStageMoney"
        :isShowPreMoneyAct="showPreMoneyAct"
        @confrim="getProductdata"
      />
    </div>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import inputEdit from '@/components/admin/inputEdit'
import choosingGoods from '@/components/admin/choosingGoods'
import ImageDalog from '@/components/admin/imageDalog'
import status from '@/components/admin/marketManage/status/status'
import { format } from '@/util/date'
import { createPreSale, updatePreSale, getDetail } from '@/api/admin/marketManage/preSale'
import preSaleDialog from './preSaleDialog'

export default {
  components: {
    inputEdit,
    choosingGoods,
    ImageDalog,
    preSaleDialog
  },
  data () {
    // 发货时间校验
    var checkDeliverType = (rule, value, callback) => {
      console.log(value)
      var re = /^[1-9]\d*$/
      if (value === 1 && this.param.deliverTime === null) {
        callback(new Error('请选择发货开始时间'))
      } else if (value === 2 && (!this.param.deliverDays || this.param.deliverDays === null)) {
        callback(new Error('请填写尾款发货时间'))
      } else if (value === 2 && !re.test(this.param.deliverDays)) {
        callback(new Error('支付完成后发货日期必须大于0'))
      } else {
        callback()
      }
    }
    var validLevel = (rule, value, callback) => {
      var reg = /^(0|[1-9][0-9]*)$/
      if (value === '' || !value) {
        callback(new Error('请输入优先级'))
      } else if (!reg.test(value)) {
        callback(new Error('请输入0和正整数'))
      } else {
        callback()
      }
    }
    return {
      id: null,
      // 当前页为编辑页
      update: false,
      // 显示更多配置
      showMore: true,
      // 活动状态,
      status: null,
      // 全款支付时间
      payTimeRange: [],
      // 活动商品名称
      presaleTypes: ['定金膨胀', '全款预售'],
      discountType: ['不可叠加', '可叠加'],
      returnTypes: ['不自动退定金', '自动退定金'],
      showSaleNumberTypes: ['不展示', '展示'],
      buyTypes: ['不可原价购买', '可原价购买'],
      shareTypes: ['默认样式', '自定义样式'],
      shareImgTypes: ['活动商品信息图', '自定义图片'],
      isShowChoosingGoodsDialog: false,
      showImageDialog: false,
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/presale_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/presale_pictorial.jpg`,
        src3: `${this.$imageHost}/image/admin/share/advance_presale.jpg`
      },
      ArrowArr: [
        { img_1: this.$imageHost + '/image/admin/show_more.png' },
        { img_2: this.$imageHost + '/image/admin/hid_some.png' }
      ],
      arrorFlag: true,
      activeIndex: 0,
      isEditeFlag: false,
      statusFlag: true,
      goodsIdList: [],
      showSpecDialog: false,
      productInfo: {},
      showTwoStageMoney: false,
      showPreMoneyAct: false,
      activityType: 1,

      /**
       * 请求参数
       */
      param: {
        preTime1Range: [], // 1段定金时间
        preTime2Range: [], // 2段定金时间
        tailPayTimeRange: [], // 尾款支付时间
        presaleType: 0, // 活动类型
        presaleName: '', // 活动名称
        prePayStep: 1, // 定金期数
        preStartTime: null, // 定金支付开始时间
        preEndTime: null,
        preStartTime2: null, // 2段定金支付开始时间
        preEndTime2: null,
        startTime: null, // 尾款开始支付时间
        endTime: null,
        goodsId: '',
        deliverType: 1, // 发货时间类型 1：指定，2：尾款支付
        deliverTime: null, // 发货时间
        deliverDays: '', // 几天后发货
        discountType: 0, // 优惠叠加策略
        returnType: 0, // 定金退款策略
        showSaleNumber: 0, // 预售数量展示
        buyType: 0, // 商品购买方式
        buyNumber: '', // 购买数量限制
        shareAction: 1,
        shareDoc: '',
        shareImgAction: 1,
        shareImg: '',
        products: [],
        goodsName: '',
        first: '',
        preTime: 24
      },
      formRules: {
        presaleType: { required: true },
        presaleName: { required: true, message: '请填写活动名称', trigger: 'blur' },
        goodsId: [{ required: true, message: '请选择活动商品', trigger: 'change' }],
        deliverType: { required: true, validator: checkDeliverType, trigger: 'change' },
        discountType: { required: true },
        returnType: { required: true },
        showSaleNumber: { required: true },
        buyType: { required: true },
        first: [
          { required: true, validator: validLevel, trigger: ['blur', 'change'] }
        ]
      },
      labels: [
        {
          status: '0',
          name: '全部定金膨胀活动'
        },
        {
          status: '1',
          name: this.$t('firstSpecial.processing')
        },
        {
          status: '2',
          name: this.$t('firstSpecial.notStart')
        }, {
          status: '3',
          name: this.$t('firstSpecial.expired')
        }, {
          status: '4',
          name: this.$t('firstSpecial.terminated')
        },
        {
          status: '5',
          name: this.$route.query.id ? this.$t('promoteList.editEvent') : this.$t('promoteList.addEvent')
        }
      ],
      tabStatus: '5'
    }
  },
  computed: {
    ongoing () {
      return this.status === status[1].status
    },
    isFullPay () {
      return this.param.presaleType === 1
    },
    twoSteps () {
      return this.param.prePayStep === 2
    }
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 验证是否选择了商品
    validateMoney (rule, value, callback, prdPrice, row) {
      row.flag1 = false
      // 找到最低活动价格
      if (row.goodsSpecProducts && row.goodsSpecProducts.length > 0) {
        row.goodsSpecProducts.forEach(item => {
          if (prdPrice > item.shopPrice) {
            prdPrice = item.shopPrice
          }
        })
      }
      var re = /^\d+(\.\d{1,2})?$/
      if (!value) {
        callback(new Error('活动价格不能为空'))
      } else if (value < 0) {
        callback(new Error('活动价格不能为负数'))
      } else if (!re.test(value)) {
        callback(new Error('请输入正确价格，可以保留两位小数'))
      } else if (Number(value) > Number(prdPrice)) {
        callback(new Error('活动价格不能大于商品原价'))
      } else {
        callback()
        row.flag1 = true
      }
    },

    // 校验活动库存
    validateNum (rule, value, callback, goodsNumber, row, index) {
      row.flag2 = false
      // 找到最低规格库存
      if (row.goodsSpecProducts && row.goodsSpecProducts.length > 0) {
        row.goodsSpecProducts.forEach(item => {
          if (goodsNumber > item.prdNumber) {
            goodsNumber = item.prdNumber
          }
        })
      }

      var re = /^(0|\+?[1-9][0-9]*)$/
      if (!value) {
        callback(new Error('活动库存不能为空'))
      } else if (value < 0) {
        callback(new Error('活动库存不能为负数'))
      } else if (!re.test(value)) {
        callback(new Error('请填写0或正整数'))
      } else if (value > goodsNumber) {
        callback(new Error('活动库存不能大于商品库存'))
      } else {
        callback()
        row.flag2 = true
      }
    },

    // 校验定金
    validateReadyMoney (rule, value, callback, presalePrice, row) {
      console.log(row)
      var re = /^\d+(\.\d{1,2})?$/

      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > Number(presalePrice)) {
        callback(new Error('定金不能大于活动价格'))
      } else {
        callback()
      }
    },

    // 校验一阶段定金
    validateFirstStage (rule, value, callback, presaleMoney, presalePrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > Number(presalePrice)) {
        callback(new Error('1阶段定金不能大于活动价格'))
      } else if (value < Number(presaleMoney)) {
        callback(new Error('1阶段定金不能小于定金'))
      } else {
        callback()
      }
    },

    // 校验二阶段定金
    validateSecondStage (rule, value, callback, presalePrice, presaleMoney) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > Number(presalePrice)) {
        callback(new Error('2阶段定金不能大于活动价格'))
      } else if (value < Number(presaleMoney)) {
        callback(new Error('2阶段定金不能小于定金'))
      } else {
        callback()
      }
    },
    beforeLeave (activeName, oldActiveName) {
      if (activeName !== '5') {
        this.$router.push({
          path: '/admin/home/main/presale',
          query: {
            tab: activeName
          }
        })
        return false
      }
    },
    // 一阶段定金支付时间
    dateChange (date) {
      this.param.preStartTime = date[0]
      this.param.preEndTime = date[1]
    },
    // 二阶段定金支付时间
    dateChange2 (date) {
      this.param.preStartTime2 = date[0]
      this.param.preEndTime2 = date[1]
    },
    endMoneyTime (val) {
      this.param.startTime = val[0]
      this.param.endTime = val[1]
    },
    // 验证数量
    validateBuyNumber (rule, value, callback) {
      console.log(value)
      var re = /^([0]|[1-9][0-9]*)$/
      if (!value) {
        callback()
      }
      if (!re.test(value)) {
        callback(new Error('请输入正确的数字'))
      } else {
        callback()
      }
    },
    changTime (e) {
      this.$refs.param.validateField('deliverType')
    },
    // 保存
    add () {
      this.$refs['param'].validate((valid) => {
        if (valid) {
          this.$refs['param-s'].validate((valid) => {
            console.log(valid, 'valid')
            if (valid) {
              const { param } = this
              this.param.buyNumber = Number(this.param.buyNumber)
              console.log(param, 'get param')
              this.formatParam()

              if (!this.validateParam()) {
                return false
              } else {
                this.formatParam()

                this.param.buyNumber = Number(this.param.buyNumber)
                this.param.first = Number(this.param.first)
                this.param.deliverDays = Number(this.param.deliverDays)
                if (this.activityType === 0) {
                  this.param.preTime = 0
                } else if (this.activityType === -1) {
                  this.param.preTime = -1
                } else {
                  this.param.preTime = Number(this.param.preTime)
                }

                this.param.products.forEach((item, index) => {
                  console.log(item)
                  item.productId = Number(item.prdId)
                  item.presalePrice = Number(item.presalePrice)
                  item.presaleNumber = Number(item.presaleNumber)
                  item.presaleMoney = Number(item.presaleMoney)
                  item.preDiscountMoney1 = Number(item.preDiscountMoney1)
                  item.preDiscountMoney2 = Number(item.preDiscountMoney2)
                })

                let products = []
                this.param.stock = 0
                console.log(this.param.products)
                this.param.products.forEach(item => {
                  if (item.goodsSpecProducts) {
                    console.log(item.goodsSpecProducts)
                    item.goodsSpecProducts.forEach(specItem => {
                      console.log(specItem)
                      let { presalePrice, presaleNumber, presaleMoney, preDiscountMoney1, stock } = specItem
                      let goodsId = item.goodsId
                      let productId = (this.isEditeFlag && this.statusFlag) ? specItem.productId : specItem.prdId
                      let preDiscountMoney2 = specItem.preDiscountMoney2 === 0 ? 'null' : specItem.preDiscountMoney2
                      products.push({ goodsId, productId, presalePrice: Number(presalePrice), presaleNumber: Number(presaleNumber), presaleMoney: Number(presaleMoney), preDiscountMoney1: Number(preDiscountMoney1), preDiscountMoney2: Number(preDiscountMoney2), stock: Number(stock) })
                      this.param.stock += Number(stock)
                    })
                  } else {
                    if (this.isEditeFlag || this.statusFlag) {
                      item.productList.forEach(item => {
                        let { goodsId, productId, presalePrice, presaleNumber, presaleMoney, preDiscountMoney1, stock } = item
                        let preDiscountMoney2 = item.preDiscountMoney2 === 0 ? 'null' : item.preDiscountMoney2
                        products.push({ goodsId, productId, presalePrice, presaleNumber, presaleMoney, preDiscountMoney1, preDiscountMoney2, stock: Number(stock) })
                        this.param.stock += Number(stock)
                      })
                    } else {
                      let { goodsId, prdId, presalePrice, presaleNumber, presaleMoney, preDiscountMoney1, preDiscountMoney2, stock } = item
                      products.push({ goodsId, productId: prdId, presalePrice, presaleNumber, presaleMoney, preDiscountMoney1, preDiscountMoney2, stock: Number(stock) })
                      this.param.stock += Number(stock)
                    }
                  }
                })

                if (this.update) {
                  console.log(this.param)
                  console.log(products)
                  updatePreSale({ ...this.param, products }).then(res => {
                    if (res.error === 0) {
                      console.log(res)
                      this.$message.success('更新成功')
                      this.gotoHome()
                    } else {
                      this.$message.error('更新失败')
                    }
                  })
                } else {
                  createPreSale({ ...this.param, products }).then(res => {
                    if (res.error === 0) {
                      console.log(res)
                      this.$message.success('添加成功')
                      this.gotoHome()
                    } else {
                      this.$message.error(res.message)
                    }
                  })
                }
              }
            } else {
              this.$message.error('请正确填写表单')
              return false
            }
          })
        } else {
          this.$message.error('请正确填写表单')
          return false
        }
      })
    },
    formatParam () {
      this.formatTimes()
    },
    formatTimes () {
      const { isFullPay, twoSteps } = this
      if (isFullPay) {
        this.param.preStartTime = format(this.param.preTime1Range[0])
        this.param.preEndTime = format(this.param.preTime1Range[1])
      } else {
        this.param.startTime = format(this.param.tailPayTimeRange[0])
        this.param.endTime = format(this.param.tailPayTimeRange[1])
        this.param.preStartTime = format(this.param.preTime1Range[0])
        this.param.preEndTime = format(this.param.preTime1Range[1])
        if (twoSteps) {
          this.param.preStartTime2 = format(this.param.preTime2Range[0])
          this.param.preEndTime2 = format(this.param.preTime2Range[1])
        }
      }
    },
    // 编辑活动初始化-回显数据加载
    loadData () {
      const { id } = this.$route.params
      getDetail(id).then(({ content }) => {
        this.param = content
        this.param.products = this.initEditProduct(content.goodsList)
        this.param.preTime = this.initActivityNotice(content.preTime)
        this.param.goodsId = content.goodsId
        this.getImgeUrl(content.shareImg)

        this.loadStatus(content)
        // this.loadingGoods(content)
        console.log(this.param, 'get return param')
        this.goodsIdList = content.goodsId.split(',').map(Number)
        if (content) {
          if (content.presaleType === 1) {
            // 全款购买 - 定金支付时间
            this.param.preTime1Range = [content.preStartTime, content.preEndTime]
          } else {
            // 定金膨胀 - 定金支付时间
            this.param.preTime1Range = [content.preStartTime, content.preEndTime]
            this.param.preTime2Range = [content.preStartTime2, content.preEndTime2]
            if (content.preEndTime2) {
              this.showTwoStageMoney = true
            } else {
              this.showTwoStageMoney = false
            }
          }
          // 尾款支付时间
          this.param.tailPayTimeRange = [content.startTime, content.endTime]
        }
      })
    },
    loadStatus: ({ status }) => {
      this.status = status
    },
    // 参数校验
    validateParam () {
      this.formatParam()
      // 校验时间
      if (this.param.preStartTime2) {
        if (this.param.preEndTime > this.param.preStartTime2) {
          this.$message.warning('一期结束时间应小于二期开始时间')
          return false
        }
        if (this.param.startTime < this.param.preStartTime) {
          this.$message.warning('尾款支付开始时间应大于一期开始时间')
          return false
        }
        if (this.param.endTime < this.param.preEndTime2) {
          this.$message.warning('尾款支付结束时间应大于二期结束时间')
          return false
        }
      } else if (!this.param.preStartTime2 || this.param.preStartTime2 === '') {
        if (this.param.startTime < this.param.preStartTime) {
          this.$message.warning('尾款支付开始时间应大于定金支付的开始时间')
          return false
        }
      } else {
        return false
      }
      if (this.param.presaleType === 0 && this.param.deliverType === 1) {
        if (this.param.deliverTime < this.param.endTime) {
          this.$message.warning('指定发货时间应大于尾款支付时间')
          return false
        }
      }
      if (this.param.presaleType === 1 && this.param.deliverType === 1) {
        if (this.param.deliverTime < this.param.preEndTime) {
          this.$message.warning('指定发货时间应大于尾款支付时间')
          return false
        }
      }

      if (this.goodsIdList.length === 0) {
        this.$message.warning('请选择商品')
        return false
      }

      if (this.param.shareAction === 2 && !this.param.shareDoc) {
        this.$message.warning('请填写对应的分享文案')
        return false
      }

      if (this.param.shareImgAction === 2 && (this.param.shareImg === null || this.param.shareImg === '')) {
        this.$message.warning('请选择自定义图片')
        return false
      }
      return true
    },
    // 删除二阶段时间
    handleDelete () {
      this.param.prePayStep = 1
      this.param.preTime2Range = []
      this.param.preStartTime2 = ''
      this.param.preEndTime2 = ''
      this.showTwoStageMoney = false
    },
    // 添加定二阶段金支付时间按钮点击事件
    handleTwoState () {
      this.param.prePayStep = 2
      if (this.param.presaleType === 0) {
        this.showTwoStageMoney = true
      }
    },
    changePreAct (val) {
      console.log(val)
      if (val === 0) {
        this.showPreMoneyAct = true
      } else {
        this.showPreMoneyAct = false
      }
    },
    showChoosingGoods () {
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    gotoHome () {
      this.$router.push('/admin/home/main/presale')
    },
    setCurrent (index) {
      // 拷贝一份数据
      let price = JSON.parse(JSON.stringify(this.param.products))

      switch (index) {
        case 1:
          price.forEach(row => {
            this.$set(row, 'presalePrice', this.param.products[0].presalePrice)
            this.changePriceInput(row)
          })
          this.activeIndex = 1
          break
        case 2:
          price.forEach(row => {
            this.$set(row, 'presaleNumber', this.param.products[0].presaleNumber)
            this.changeStockInput(row)
          })
          this.activeIndex = 2
          break
        case 3:
          price.forEach(row => {
            this.$set(row, 'presaleMoney', this.param.products[0].presaleMoney)
            this.changeEarnestMoney(row)
          })
          this.activeIndex = 3
          break
        case 4:
          price.forEach(row => {
            this.$set(row, 'preDiscountMoney1', this.param.products[0].preDiscountMoney1)
            this.changeDiscountMoney1(row)
          })
          this.activeIndex = 4
          break
        case 5:
          price.forEach(row => {
            this.$set(row, 'preDiscountMoney2', this.param.products[0].preDiscountMoney2)
            this.changeDiscountMoney2(row)
          })
          this.activeIndex = 5
          break
      }
      this.param.products = price
    },
    // 删除商品
    deleteGoods (data, id) {
      let index = this.goodsIdList.findIndex(item => {
        return item === id
      })
      this.goodsIdList.splice(index, 1)
      let goodsTarget = this.param.products.findIndex(item => {
        return id === item.goodsId
      })
      this.param.products.splice(goodsTarget, 1)
    },
    getProductdata ({ goodsId, prdInfo }) {
      console.log(goodsId, prdInfo)
      console.log(this.param.products)
      let target = this.param.products.find(item => { return item.goodsId === goodsId })
      let index = this.param.products.findIndex(item => { return item.goodsId === goodsId })
      this.$set(this.param.products[index], 'presalePrice', prdInfo[0].presalePrice)
      this.$set(this.param.products[index], 'presaleNumber', prdInfo[0].presaleNumber)
      this.$set(this.param.products[index], 'presaleMoney', prdInfo[0].presaleMoney)
      this.$set(this.param.products[index], 'preDiscountMoney1', prdInfo[0].preDiscountMoney1)
      this.$set(this.param.products[index], 'preDiscountMoney2', prdInfo[0].preDiscountMoney2)
      target.goodsSpecProducts = prdInfo
      this.changePriceInput(target, true)
      this.changeStockInput(target, true)
      this.changeEarnestMoney(target, true)
      this.changeDiscountMoney1(target, true)
      this.changeDiscountMoney2(target, true)
    },
    showSpec (goodsInfo) {
      if (this.param.presaleType === 0) {
        this.showPreMoneyAct = true
      }
      this.productInfo = goodsInfo
      this.showSpecDialog = true
      console.log(this.param.presaleType)
      console.log(this.showSpecDialog)
    },
    // 改变活动价格
    changePriceInput (goodsInfo, isDialog = null) {
      if (goodsInfo.goodsSpecProducts && goodsInfo.goodsSpecProducts.length > 0) {
        goodsInfo.priceErrorMsg = null
        goodsInfo.goodsSpecProducts.forEach((item, index) => {
          console.log(item)
          if (!isDialog) item.presalePrice = goodsInfo.presalePrice
          // if (this.validatePrdPrice(item) && !goodsInfo.priceErrorMsg) {
          //   goodsInfo.priceErrorMsg = '有规格拼团价大于原价，请修改'
          // }
        })
      }
    },
    // 改变活动库存
    changeStockInput (goodsInfo, isDialog = null) {
      if (goodsInfo.goodsSpecProducts && goodsInfo.goodsSpecProducts.length > 0) {
        goodsInfo.stockErrorMsg = null
        goodsInfo.totalStock = 0
        goodsInfo.goodsSpecProducts.forEach((item, index) => {
          console.log(item)
          if (!isDialog) item.presaleNumber = goodsInfo.presaleNumber
          goodsInfo.totalStock += parseInt(item.presaleNumber)
          // if (this.validatePrdStock(item) && !goodsInfo.stockErrorMsg) {
          //   goodsInfo.stockErrorMsg = '有规格拼团库存大于原库存，请修改'
          // }
        })
      }
    },
    // 改变定金
    changeEarnestMoney (goodsInfo, isDialog = null) {
      if (goodsInfo.goodsSpecProducts && goodsInfo.goodsSpecProducts.length > 0) {
        goodsInfo.stockErrorMsg = null
        goodsInfo.goodsSpecProducts.forEach((item, index) => {
          if (!isDialog) item.presaleMoney = goodsInfo.presaleMoney
        })
      }
    },
    // 改变一阶段的金额
    changeDiscountMoney1 (goodsInfo, isDialog = null) {
      if (goodsInfo.goodsSpecProducts && goodsInfo.goodsSpecProducts.length > 0) {
        goodsInfo.stockErrorMsg = null
        goodsInfo.goodsSpecProducts.forEach((item, index) => {
          if (!isDialog) item.preDiscountMoney1 = goodsInfo.preDiscountMoney1
        })
      }
    },
    // 改变二阶段的金额
    changeDiscountMoney2 (goodsInfo, isDialog = null) {
      if (goodsInfo.goodsSpecProducts && goodsInfo.goodsSpecProducts.length > 0) {
        goodsInfo.stockErrorMsg = null
        goodsInfo.goodsSpecProducts.forEach((item, index) => {
          if (!isDialog) item.preDiscountMoney2 = goodsInfo.preDiscountMoney2
        })
      }
    },
    // 改变"收起、展开更多配置"事件
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },
    // 获取商品ids
    choosingGoodsResult (row) {
      console.log(row, 'goodsInfo')
      this.goodsIdList = row.map(item => { return item.goodsId })
      this.param.goodsId = this.goodsIdList.join(',')
      this.param.products = row
    },
    // 处理回显的所选商品数据显示
    initEditProduct (goods) {
      let newdata = []
      goods.forEach(item => {
        let expand = item.productList.length < 2 ? { ...item.productList[0], goodsName: item.goodsName } : { ...item.productList[0], goodsSpecProducts: item.productList, goodsName: item.goodsName }
        newdata.push({ ...item, ...expand })
      })
      return newdata
    },
    initActivityNotice (val) {
      console.log(val)
      if (val === -1) {
        this.activityType = -1
        this.param.preTime = 24
      } else if (val === 0) {
        this.activityType = 0
        this.param.preTime = 24
      } else if (val > 0) {
        this.activityType = 1
        this.param.preTime = val
      } else {
      }
      return this.param.preTime
    },
    // 分享 - 调起图片弹窗
    addGoodsImg () {
      this.showImageDialog = !this.showImageDialog
    },
    // 图片点击回调函数
    handleSelectImg (res) {
      if (res != null) {
        this.param.shareImg = res.imgPath
      }
    },
    getImgeUrl (data) {
      console.log(data)
      var imgUrl = data.split('//').pop().split('/').slice(1).join('/')
      this.param.shareImg = imgUrl
    }
  },
  watch: {
    'param.goodsId': function (value) {
      if (value) {
        this.$refs.param.validateField('goodsId')
      }
    },
    'param.presaleType': function (value) {
      if (value) {
        this.$refs.param.validateField('presaleType')
      }
    },
    'param.deliverType': function (value) {
      if (value) {
        this.$refs.param.validateField('deliverType')
      }
    },
    'lang': function (val) {
      this.labels = [{
        status: '0',
        name: '全部定金膨胀活动'
      },
      {
        status: '1',
        name: this.$t('firstSpecial.processing')
      },
      {
        status: '2',
        name: this.$t('firstSpecial.notStart')
      }, {
        status: '3',
        name: this.$t('firstSpecial.expired')
      }, {
        status: '4',
        name: this.$t('firstSpecial.terminated')
      },
      {
        status: '5',
        name: this.$route.query.id ? this.$t('promoteList.editEvent') : this.$t('promoteList.addEvent')
      }]
    }
  },
  mounted () {
    const { id } = this.$route.params
    this.update = !!id
    this.id = id || null
    if (this.update) {
      // 编辑回显
      this.loadData()
    }
    this.langDefault()
    console.log(this.$route)
    if (this.$route.query.id > 0) {
      // 编辑时部分信息不可以修改
      // 进行中状态
      if (this.$route.query.currentState === 1) {
        this.isEditeFlag = true
      }
      // 未开始状态
      if (this.$route.query.currentState === 2) {
        this.isEditeFlag = false
        this.statusFlag = true
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.wrapper {
  margin: 10px;
  padding: 20px;
  margin-bottom: 80px;
  background: #fff;
}
.textColor {
  color: #999;
}
.imgContent {
  width: 70px;
  height: 70px;
  text-align: center;
  line-height: 65px;
  margin-left: 60px;
  cursor: pointer;
}
.imgContent .shareImg {
  width: 100%;
  height: 100%;
}
.picSizeTips {
  display: block;
  line-height: 80px;
  margin-left: 20px;
  color: rgb(153, 153, 153);
}
.label {
  line-height: 40px;
}
.footer {
  position: absolute;
  bottom: 0;
  right: 27px;
  left: 160px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
}
.moreSetUp {
  height: 40px;
  line-height: 40px;
  margin-left: 15px;
}
.moreSetUp a {
  margin-right: 10px;
  cursor: pointer;
}
.settings {
  color: #5a8bff;
}
.spec-tips {
  text-align: center;
  color: #409eff;
  cursor: pointer;
}
/deep/ .el-form-item__error {
  position: relative;
  text-align: left;
}
.tab-wrapper {
  position: relative;
  background-color: #fff;
  margin: 10px;
  padding: 10px 20px 10px 20px;
  margin-bottom: 10px;
}
</style>
