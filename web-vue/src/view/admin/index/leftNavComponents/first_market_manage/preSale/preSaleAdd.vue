<!--
* 创建定金膨胀活动
* @author 赵鑫
-->
<template>
  <div>
    <div class="wrapper">
      <el-form
        label-width="130px"
        ref="param"
        :model="param"
        :rules="formRules"
      >
        <el-form-item
          label="活动类型："
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
          label="活动名称："
          prop="presaleName"
        >
          <el-input
            v-model="param.presaleName"
            placeholder="请输入活动名称"
            size="small"
            style="width:180px"
          ></el-input>
          <span style="color:#999;margin-left:10px;">只作为商家记录使用，用户不会看到这个名称</span>
        </el-form-item>

        <el-form-item
          label="优先级："
          prop="first"
        >
          <el-input
            v-model="param.first"
            size="small"
            placeholder="请输入优先级"
            style="width: 180px"
          ></el-input>
          <span style="color:#999;margin-left:10px;">用于区分不同定金膨胀活动的优先级，请填写正整数，数值越大优先级越高</span>
        </el-form-item>

        <!-- 定金膨胀 -->
        <el-form-item
          v-show="!isFullPay"
          label="活动时间："
          :rules="[{required: true}]"
        >
          <template>
            <div style="color:#999">请设置定金支付时间以及尾款支付时间，最多可配置两个支付定金时段，定金支付的截止时间不能大于尾款支付的截止时间</div>
            <el-form-item
              label="定金支付时间："
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
              >添加定金支付时段</el-button>
            </el-form-item>

            <!-- 定金支付时间 -->
            <el-form-item
              label="定金支付时间："
              :rules="[{required: true, message:'请填写定金支付时间', trigger: ['blur','change']}]"
              :inline-message="true"
              prop="preTime2Range"
              v-show="twoSteps"
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
                size="small"
                @click="handleDelete"
                v-if="!isEditeFlag"
              >删除</el-button>
            </el-form-item>

            <!-- 尾款支付时间 -->
            <el-form-item
              label="尾款支付时间："
              prop="tailPayTimeRange"
              :rules="[{required: true, message:'请填写尾款支付时间', trigger: ['blur','change']}]"
              :inline-message="true"
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
          label="定金支付时间："
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
        <el-form-item label="活动预告：">
          <div>
            <span style="color:#999">活动开始前会在商品详情中展示活动预告信息</span>
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="srcList.src3"></el-image>
              <el-button
                slot="reference"
                type="text"
              >查看示例</el-button>
            </el-popover>
          </div>
          <div>
            <el-radio-group v-model="activityType">
              <el-radio
                :label="1"
                :disabled="isEditeFlag"
              >
                活动开始前
                <el-input
                  v-model="param.preTime"
                  :disabled="isEditeFlag"
                  style="width:80px"
                  size="small"
                ></el-input>
                小时进行预告
              </el-radio>
              <el-radio
                :label="-1"
                :disabled="isEditeFlag"
              >活动创建完成后即进行公告</el-radio>
              <el-radio
                :label="0"
                :disabled="isEditeFlag"
              >不进行活动预告</el-radio>
            </el-radio-group>
          </div>
        </el-form-item>

        <!-- 活动商品 -->
        <el-form-item
          label="活动商品："
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
          >选择商品
          </el-button>
        </el-form-item>
        <el-form-item
          label="发货时间："
          prop="deliverType"
        >
          <div style="display: flex">
            <el-radio
              :disabled="isEditeFlag"
              v-model="param.deliverType"
              :label="1"
              style="line-height: 40px"
            >&nbsp;指定发货开始时间</el-radio>
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
            >
              <span v-if="this.param.presaleType === 0">&nbsp;尾款支付完成</span>
              <span v-if="this.param.presaleType === 1">&nbsp;支付完成后</span>
            </el-radio>
            <el-input
              :disabled="param.deliverType==1 || isEditeFlag"
              v-model="param.deliverDays"
              size="small"
              style="width:180px"
              :min=0
            />
            <span style="margin-left:10px">天后发货</span>
          </div>
        </el-form-item>
        <el-form-item
          label="优惠叠加策略："
          prop="discountType"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-model="param.discountType"
            v-for="(item, index) in discountType"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">预售商品结算时是否可与会员卡折扣、优惠券叠加使用</span>
        </el-form-item>
        <el-form-item
          label="定金退款策略："
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
          <span class="textColor">选择自动退回定金，则在指定时间内未支付尾款的订单，将退回定金到原支付账户</span>
        </el-form-item>
        <el-form-item
          label="预售数量展示："
          prop="showSaleNumber"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-model="param.showSaleNumber"
            v-for="(item, index) in showSaleNumberTypes"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">当前活动商品的预售数量是否展示在商品详情页</span>
        </el-form-item>
        <el-form-item
          label="商品购买方式："
          prop="buyType"
        >
          <el-radio
            :disabled="isEditeFlag"
            v-model="param.buyType"
            v-for="(item, index) in buyTypes"
            :key="index"
            :label="index"
          >{{item}}</el-radio>
          <span class="textColor">活动进行中是否可直接以原价购买此商品</span>
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
              label="商品名称"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="shopPrice"
              label="商品原价(元)"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="goodsNumber"
              label="商品库存"
              align="center"
            >
            </el-table-column>

            <el-table-column
              align="center"
              label="活动价格"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.presalePrice'"
                  :rules="[
                    { required: true, message: '活动价格不能为空' },
                    { validator: (rule, value, callback)=>{validateMoney(rule, value, callback, scope.row.shopPrice)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
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
              label="活动库存"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.presaleNumber'"
                  :rules="[
                    { required: true, message: '活动库存不能为空' },
                    { validator: (rule, value, callback)=>{validateNum(rule, value, callback, scope.row.goodsNumber)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
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
                >包含{{scope.row.goodsSpecProducts.length}}个规格</div>
              </template>
            </el-table-column>

            <!-- 定金 -->
            <el-table-column
              align="center"
              prop="presaleMoney"
              label="定金"
              v-if="param.presaleType===0"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.presaleMoney'"
                  :rules="[
                    { required: true, message: '定金不能为空'},
                    { validator: (rule, value, callback)=>{validateReadyMoney(rule, value, callback, scope.row.shopPrice)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
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
              label="1阶段定金可抵扣金额"
              v-if="!isFullPay"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.preDiscountMoney1'"
                  :rules="[
                    { required: true, message: '1阶段定金不能为空'},
                    { validator: (rule, value, callback)=>{validateFirstStage(rule, value, callback, scope.row.shopPrice, scope.row.presaleMoney)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
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
              label="2阶段定金可抵扣金额"
              v-if="twoSteps&&!isFullPay"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'products.' +  scope.$index+ '.preDiscountMoney2'"
                  :rules="[
                    { required: true, message: '2阶段定金不能为空'},
                    { validator: (rule, value, callback)=>{validateSecondStage(rule, value, callback, scope.row.presalePrice, scope.row.presaleMoney)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
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
              label="操作"
              align="center"
              v-if="!isEditeFlag"
            >
              <template slot-scope="scope">
                <div
                  v-if="scope.row.goodsId"
                  @click="deleteGoods(scope.row, scope.row.goodsId)"
                  style="cursor:pointer;color:#409eff"
                >删除</div>
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
              <span style="display: inline-block">更多设置：</span>
              <a
                :class="activeIndex === 1 ? '' : 'settings'"
                @click="setCurrent(1)"
              >活动价格
              </a>
              <a
                :class="activeIndex === 2 ? '' : 'settings'"
                @click="setCurrent(2)"
              >活动库存
              </a>
              <a
                :class="activeIndex === 3 ? '' : 'settings'"
                @click="setCurrent(3)"
                v-if="param.presaleType===0"
              >定金
              </a>
              <a
                :class="activeIndex === 4 ? '' : 'settings'"
                @click="setCurrent(4)"
                v-show="!isFullPay"
              >1阶段定金可抵扣金额
              </a>
              <a
                :class="activeIndex === 5 ? '' : 'settings'"
                @click="setCurrent(5)"
                v-show="twoSteps&&!isFullPay"
              >2阶段定金可抵扣金额
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
        >
          <el-form-item label="购买数量限制：">
            <div style="display:flex">
              <span>单用户最多可以购买</span>
              <el-input
                :disabled="isEditeFlag"
                v-model="param.buyNumber"
                size="small"
                style="width:180px;margin:0 10px;"
              ></el-input>
              <span>件该商品</span>
              <span
                class="textColor"
                style="margin-left:20px;"
              >单用户可购买活动商品的数量，不填或填写0表示不限制</span>
            </div>
          </el-form-item>
          <el-form-item label="活动分享：">
            <div>
              <el-radio
                v-model="param.shareAction"
                :label=1
              >默认样式</el-radio>
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
                >查看示例</el-button>
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
                >下载海报</el-button>
              </el-popover>
            </div>
            <div>
              <el-radio
                v-model="param.shareAction"
                :label=2
              >自定义样式</el-radio>
              <div
                v-if="param.shareAction === 2"
                style="margin-left: 25px"
              >
                <span>文案：</span>
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
                <span>分享图：</span>
                <el-radio
                  v-model="param.shareImgAction"
                  :label=1
                >活动商品信息图</el-radio>
                <div style="margin-left: 60px;">
                  <el-radio
                    v-model="param.shareImgAction"
                    :label=2
                  >自定义图片</el-radio>
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
                  <span class="picSizeTips">建议尺寸：800*800像素</span>
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
        >保存
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
        @confirm="getProductdata"
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
      if (value === 1 && this.param.deliverTime === null) {
        callback(new Error('请选择发货开始时间'))
      } else if (value === 2 && (!this.param.deliverDays || this.param.deliverDays === null)) {
        callback(new Error('请填写尾款发货时间'))
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
        deliverDays: null, // 几天后发货
        discountType: 0, // 优惠叠加策略
        returnType: 0, // 定金退款策略
        showSaleNumber: 0, // 预售数量展示
        buyType: 0, // 商品购买方式
        buyNumber: null, // 购买数量限制
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
      }
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
    validateMoney (rule, value, callback, prdPrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > prdPrice) {
        callback(new Error('活动价格不能大于商品原价'))
      } else {
        callback()
      }
    },
    validateNum (rule, value, callback, goodsNumber) {
      var re = /^[1-9]\d*$/
      if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else if (value > goodsNumber) {
        callback(new Error('活动库存不能大于商品库存'))
      } else {
        callback()
      }
    },
    validateReadyMoney (rule, value, callback, shopPrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > Number(shopPrice)) {
        callback(new Error('定金不能大于活动价格'))
      } else {
        callback()
      }
    },
    validateFirstStage (rule, value, callback, shopPrice, presaleMoney) {
      var re = /^\d+(\.\d{1,2})?$/
      if (!re.test(value)) {
        callback(new Error('请填写非负数, 可以保留两位小数'))
      } else if (value > Number(shopPrice)) {
        callback(new Error('1阶段定金不能大于活动价格'))
      } else if (value < Number(presaleMoney)) {
        callback(new Error('1阶段定金不能小于定金'))
      } else {
        callback()
      }
    },
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
    // 保存
    add () {
      this.param.buyNumber = Number(this.param.buyNumber)
      this.param.first = Number(this.param.first)
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
      // this.param.stock = 0
      this.param.products.forEach(item => {
        if (item.goodsSpecProducts) {
          console.log(item.goodsSpecProducts)
          item.goodsSpecProducts.forEach(specItem => {
            let { prdId, presalePrice, presaleNumber, presaleMoney, preDiscountMoney1, preDiscountMoney2, stock } = specItem
            let goodsId = item.goodsId
            products.push({ goodsId, productId: prdId, presalePrice: Number(presalePrice), presaleNumber: Number(presaleNumber), presaleMoney: Number(presaleMoney), preDiscountMoney1: Number(preDiscountMoney1), preDiscountMoney2: Number(preDiscountMoney2), stock: Number(stock) })
            // this.param.stock += Number(stock)
          })
        } else {
          let { goodsId, prdId, presalePrice, presaleNumber, presaleMoney, preDiscountMoney1, preDiscountMoney2, stock } = item
          products.push({ goodsId, productId: prdId, presalePrice, presaleNumber, presaleMoney, preDiscountMoney1, preDiscountMoney2, stock: Number(stock) })
          // this.param.stock += Number(stock)
        }
      })

      const { param } = this

      console.log(param, 'get param')
      this.formatParam()
      if (!this.validateParam()) {
        return
      }
      if (this.update) {
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
          }
        })
      }
    },
    formatParam () {
      this.formatTimes()
    },
    formatTimes () {
      const { isFullPay, payTimeRange, twoSteps } = this
      if (isFullPay) {
        this.param.startTime = format(payTimeRange[0])
        this.param.endTime = format(payTimeRange[1])
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
        if (content) {
          if (content.presaleType === 1) {
            // 全款购买 - 定金支付时间
            this.param.preTime1Range = [content.preStartTime, content.preEndTime]
          } else {
            // 定金膨胀 - 定金支付时间
            this.param.preTime1Range = [content.preStartTime, content.preEndTime]
            this.param.preTime2Range = [content.preStartTime2, content.preEndTime2]
            this.showTwoStageMoney = true
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
      // todo ......
      this.$refs['param'].validate((valid) => {
        console.log(valid, 'valid')
        if (valid) {
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
            if (this.param.startTime < this.param.prrStartTime) {
              this.$message.warning('尾款支付开始时间应大于定金支付的开始时间')
              return false
            }
          } else {
            return false
          }
          if (this.param.deliverType === 1) {
            if (this.param.deliverTime < this.param.endTime) {
              this.$message.warning('指定发货时间应大于尾款支付时间')
            }
          }
        } else {
          console.log('error submit')
          return false
        }
      })

      this.$refs['param-s'].validate((valid) => {
        console.log(valid, 'valid')
        if (valid) {
          // alert('111')
        } else {
          // alert('222')
        }
      })
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
      console.log(this.price, 'get-price')

      switch (index) {
        case 1:
          price.forEach(row => {
            row.presalePrice = Number(price[0].presalePrice)
          })
          this.activeIndex = 1
          break
        case 2:
          price.forEach(row => {
            row.presaleNumber = Number(price[0].presaleNumber)
          })
          this.activeIndex = 2
          break
        case 3:
          price.forEach(row => {
            row.presaleMoney = Number(price[0].presaleMoney)
          })
          this.activeIndex = 3
          break
        case 4:
          price.forEach(row => {
            row.preDiscountMoney1 = Number(price[0].preDiscountMoney1)
          })
          this.activeIndex = 4
          break
        case 5:
          price.forEach(row => {
            row.preDiscountMoney2 = Number(price[0].preDiscountMoney2)
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
      console.log(goodsInfo)
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
        this.preTime = 24
      } else if (val === 0) {
        this.activityType = 0
        this.preTime = 24
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
    'param.deliverType': function (value) {
      if (value) {
        this.$refs.param.validateField('deliverType')
      }
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
    if (this.$route.query.id > 0) {
      // 编辑定金膨胀活动
      this.isEditeFlag = true // 编辑时部分信息不可以修改
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
</style>
