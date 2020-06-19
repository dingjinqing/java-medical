<!--
添加拼团活动
@author 孔德成
-->
<template>
  <div>
    <div class="wrapper">
      <el-form
        ref="form"
        :model="form"
        :rules="fromRules"
        label-width="150px"
        :label-position="'right'"
        v-if="!ruleShow"
      >
        <el-form-item
          :label="$t('groupBuy.groupBuyActivity') + '：'"
          prop="resource"
        >
          <el-radio-group
            :disabled='isEdite'
            v-model="form.activityType"
          >
            <el-radio :label=1>{{$t('groupBuy.grouponType')[0].label}}</el-radio>
            <el-radio :label=2>{{$t('groupBuy.grouponType')[1].label}}</el-radio>
          </el-radio-group>
          <div class="prompt">
            {{$t('groupBuy.groupBuyActivityComment')}}
          </div>
        </el-form-item>
        <el-form-item
          :label="$t('groupBuy.activityName') + '：'"
          prop="name"
        >
          <el-col :span="8">
            <el-input
              size="small"
              v-model="form.name"
              style="width: 170px"
              placeholder="请输入活动名称"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item
          :label="$t('groupBuy.activtiyLevel') + '：'"
          prop="level"
        >
          <el-col :span="13">
            <el-input
              v-model="form.level"
              controls-position="right"
              style="width:170px"
              size="small"
              placeholder="请输入活动优先级"
            >
            </el-input>
            <div class="prompt">
              {{$t('groupBuy.activtiyLevelComment')}}
            </div>
            <!-- <span>用于区分不同拼团活动的优先级，请填写正整数，数值越大优先级越高</span> -->
          </el-col>
        </el-form-item>
        <el-form-item
          :label="$t('groupBuy.goodsName') + '：'"
          prop="goodsId"
        >
          <el-input
            :disabled="true"
            v-model="goodsRow.goodsName"
            v-if="goodsRow.ischecked"
            size="small"
            style="width: 170px;"
          ></el-input>
          <el-input
            :disabled="true"
            v-if="false"
            v-model="form.goodsId"
            size="small"
            style="width: 170px;"
          ></el-input>

          <el-button
            :disabled="isEdite"
            size="small"
            @click="showChoosingGoods"
          >{{$t('groupBuy.selectGoods')}}
          </el-button>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.commanderDiscounts') + '：'">
          <section style="display: flex">
            <div style="width: 70px">
              <el-switch
                :disabled="isEdite"
                v-model="form.isGrouperCheap"
                @change="changeSwitchState"
                :active-value=1
                :inactive-value=0
                active-color="#f7931e"
              ></el-switch>
            </div>
            <div class="prompt">
              <div>
                {{$t('groupBuy.commanderDiscountsComment1')}}
              </div>
              <p style="line-height:20px">
                <span style="color: red;">{{$t('marketCommon.note')}}：</span>
                {{$t('groupBuy.commanderDiscountsComment2')}}
              </p>
            </div>
          </section>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.discountsOption') + '：'">
          <el-table
            header-row-class-name="tableHeader"
            :data="form.product"
            border
            style="width: 99%"
            :empty-text="$t('groupBuy.noData')"
          >
            <el-table-column
              align="center"
              prop="goodsName"
              label="商品名称"
            >
            </el-table-column>

            <!-- 原价 -->
            <el-table-column
              align="center"
              prop="shopPrice"
              :label="$t('groupBuy.originalPrice')"
            >
            </el-table-column>

            <!-- 拼团价 -->
            <el-table-column
              align="center"
              :label="$t('groupBuy.groupBuyPrice')"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'product.' +  scope.$index+ '.groupPrice'"
                  :rules="[
                    { required: true, message: '拼团价不能为空', trigger: 'blur' },
                    { validator: (rule, value, callback)=>{validateMoney(rule, value, callback, scope.row.shopPrice)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <div
                    class="input-error"
                    v-if="scope.row.priceErrorMsg"
                  >{{scope.row.priceErrorMsg}}</div>
                  <el-input
                    v-model="scope.row.groupPrice"
                    size="small"
                    :disabled="isEdite"
                    @input="changePriceInput(scope.row)"
                  />
                </el-form-item>
                <div
                  class="spec-tips"
                  @click="showSpec(scope.row)"
                  v-if="scope.row.goodsSpecProducts && scope.row.goodsSpecProducts.length > 0"
                >包含{{scope.row.goodsSpecProducts.length}}个规格</div>
              </template>
            </el-table-column>

            <!-- 团长价 -->
            <el-table-column
              align="center"
              :label="$t('groupBuy.commanderPrice')"
              v-if="form.isGrouperCheap === 1"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'product.' +  scope.$index+ '.grouperPrice'"
                  :rules="[
                    { required: true, message: '团长价不能为空', trigger: 'blur' },
                    { validator: (rule, value, callback)=>{validateGrouperMoney(rule, value, callback, scope.row.shopPrice)}, trigger: ['blur', 'change'] }
                  ]"
                  style="height: 56px;line-height: 56px;"
                >
                  <div
                    class="input-error"
                    v-if="scope.row.grouperPriceErrorMsg"
                  >{{scope.row.grouperPriceErrorMsg}}</div>
                  <el-input
                    v-model="scope.row.grouperPrice"
                    size="small"
                    :disabled="isEdite"
                    @input="changeGrouperPriceInput(scope.row)"
                  />
                </el-form-item>
                <div
                  class="spec-tips"
                  @click="showSpec(scope.row)"
                  v-if="scope.row.goodsSpecProducts && scope.row.goodsSpecProducts.length > 0"
                >包含{{scope.row.goodsSpecProducts.length}}个规格</div>
              </template>
            </el-table-column>

            <!-- 原库存 -->
            <el-table-column
              align="center"
              prop="goodsNumber"
              :label="$t('groupBuy.originalStock')"
            >
            </el-table-column>

            <!-- 拼团库存 -->
            <el-table-column
              align="center"
              prop="stock"
              :label="$t('groupBuy.groupBuyStock')"
            >
              <template slot-scope="scope">
                <el-form-item
                  :prop="'product.' +  scope.$index+ '.stock'"
                  :rules="[
                    { required: true, message: '拼团库存不能为空', trigger: 'blur' },
                    { validator: (rule, value, callback)=>{validateNum(rule, value, callback, scope.row.goodsNumber)}, trigger: ['blur', 'change'] }
                  ]"
                >
                  <div
                    class="input-error"
                    v-if="scope.row.stockErrorMsg"
                  >{{scope.row.stockErrorMsg}}</div>
                  <el-input
                    v-model="scope.row.stock"
                    size="small"
                    :disabled="isEdite"
                    @input="changeStockInput(scope.row)"
                  />
                </el-form-item>
                <div
                  class="spec-tips"
                  @click="showSpec(scope.row)"
                  style="margin-top: 15px;"
                  v-if="scope.row.goodsSpecProducts && scope.row.goodsSpecProducts.length > 0"
                >包含{{scope.row.goodsSpecProducts.length}}个规格；库存合计：{{scope.row.totalStock ? scope.row.totalStock : 0}}</div>
              </template>
            </el-table-column>

            <!-- 操作 -->
            <el-table-column
              label="操作"
              align="center"
              v-if="!isEdite"
            >
              <template slot-scope="scope">
                <div
                  v-if="scope.row.goodsId"
                  @click="deleteGoods(scope.row, scope.row.id)"
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
              <span>{{ this.$t('groupBuy.moreSettings') }}</span>
              <a
                :class="activeIndex === 1 ? '' : 'settings'"
                @click="setCurrent(1)"
              >{{ this.$t('groupBuy.groupBuyPrice') }}
              </a>
              <a
                :class="activeIndex === 2 ? '' : 'settings'"
                @click="setCurrent(2)"
              >{{ this.$t('groupBuy.commanderDiscounts') }}
              </a>
              <a
                :class="activeIndex === 3 ? '' : 'settings'"
                @click="setCurrent(3)"
              >{{ this.$t('groupBuy.groupBuyStock') }}
              </a>
            </div>
          </el-table>
        </el-form-item>

        <el-form-item
          :label="$t('groupBuy.validDate') + '：'"
          prop="validityDate"
        >
          <el-date-picker
            size="small"
            v-model="form.validityDate"
            type="datetimerange"
            @change="dateChange(form.validityDate)"
            :range-separator="$t('groupBuy.to')"
            :start-placeholder="$t('groupBuy.startDate')"
            :end-placeholder="$t('groupBuy.endDate')"
            value-format="yyyy-MM-dd HH:mm:ss"
            :default-time="['00:00:00','23:59:59']"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item
          label="活动预告："
          prop="preTime"
        >
          <div>
            <span class="noticeTip">活动开始前会在商品详情中展示活动预告信息</span>
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="$imageHost + '/image/admin/share/advance_seckill.jpg'"></el-image>
              <el-button
                slot="reference"
                type="text"
                style="margin: 0 20 0 0px"
              >查看示例</el-button>
            </el-popover>
          </div>
          <div>
            <el-radio
              v-model="form.preTime"
              :label="1"
              :disabled='isEdite'
              @change="preTimeChange"
            >活动开始前
              <el-input
                v-model="form.preTimeValue"
                :disabled='isEdite'
                style="width: 80px;"
                size="small"
              ></el-input>小时进行预告
            </el-radio>
            <el-radio
              v-model="form.preTime"
              :label="-1"
              @change="preTimeChange"
              :disabled='isEdite'
            >活动创建完成后即进行预告</el-radio>
            <el-radio
              v-model="form.preTime"
              :label="0"
              @change="preTimeChange"
              :disabled='isEdite'
            >不进行活动预告</el-radio>
          </div>
        </el-form-item>

        <el-form-item
          :label="$t('groupBuy.limitAmount') + '：'"
          prop="limitAmount"
        >
          <el-input-number
            :disabled="isEdite"
            v-model="form.limitAmount"
            controls-position="right"
            :min="2"
          ></el-input-number>
          <div class="prompt">{{$t('groupBuy.limitAmountComment')}}</div>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.orderGoodsNum') + '：'">
          <div class="prompt fontColor">{{$t('groupBuy.orderGoodsNumComment1')}}</div>
          <el-input-number
            v-model="form.limitBuyNum"
            controls-position="right"
            :min="0"
          ></el-input-number>
          <div class="prompt fontColor">
            {{$t('groupBuy.jian')}}
            <span class="prompt">{{$t('groupBuy.orderGoodsNumComment3')}}</span>
          </div>
        </el-form-item>
        <el-form-item>
          <div class="prompt fontColor"> {{$t('groupBuy.orderGoodsNumComment2')}}</div>
          <el-input-number
            v-model="form.limitMaxNum"
            controls-position="right"
            :min="0"
          ></el-input-number>
          <div class="prompt fontColor">
            {{$t('groupBuy.jian')}}
            <span class="prompt">{{$t('groupBuy.orderGoodsNumComment3')}}</span>
          </div>
        </el-form-item>
        <el-form-item
          :label="$t('groupBuy.joinLimit') + '：'"
          prop="joinLimit"
        >
          <div class="prompt fontColor"> {{$t('groupBuy.joinLimitComment1')}}</div>
          <el-input-number
            v-model="form.joinLimit"
            controls-position="right"
            :min="0"
          ></el-input-number>
          <div class="prompt fontColor">
            {{ $t('groupBuy.joinLimitComment2')}}
            <span class="prompt">{{ $t('groupBuy.joinLimitComment3')}}</span>
          </div>
        </el-form-item>
        <el-form-item
          :label=" $t('groupBuy.openLimit') + '：'"
          prop="openLimit"
        >
          <div class="prompt fontColor"> {{ $t('groupBuy.openLimitComment1')}}</div>
          <el-input-number
            v-model="form.openLimit"
            controls-position="right"
            :min="0"
          ></el-input-number>
          <div class="prompt fontColor">
            {{$t('groupBuy.joinLimitComment2')}}
            <span class="prompt"> {{$t('groupBuy.openLimitComment2')}}</span>
          </div>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.openIsDefault') + '：'">
          <el-switch
            v-model="form.isDefault"
            :active-value=1
            :inactive-value=0
            active-color="#f7931e"
          ></el-switch>
          <div class="prompt">{{$t('groupBuy.openIsDefaultComment')}}</div>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.shippingOption') + '：'">
          <el-radio-group v-model="form.shippingType">
            <el-radio :label=1>{{$t('groupBuy.freeShipping')}}</el-radio>
            <el-radio :label=2>{{$t('groupBuy.shippingOptionComment')}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('groupBuy.beginNum') + '：'">
          <el-input-number
            v-model="form.beginNum"
            controls-position="right"
            :min="0"
          >
          </el-input-number>
          <div class="prompt fontColor">
            {{$t('groupBuy.beginNumComment')}}
          </div>
        </el-form-item>

        <!-- 收起、展开更多配置 -->
        <div
          @click="handleToChangeArror()"
          style="margin: 0 0 10px 33px"
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
          <!-- 鼓励奖部分内容 -->
          <el-form-item :label="$t('groupBuy.consolationPrize') + '：'">
            <el-card class="box-card">
              <div class="fontColor"> {{$t('groupBuy.consolationPrizeComment1')}}</div>
              <div class="middleContainer">
                <div
                  v-for="(item,index) in rewardCouponList"
                  :key="index"
                  class="rewardCouponInfo"
                  style="margin-right: 5px;"
                >
                  <section
                    class="couponImgWrapper"
                    :class="item.status === 0 ? 'couponImgWrapper': 'couponImgWrapperGary'"
                    style="line-height: normal"
                  >
                    <div
                      class="coupon_list_top"
                      :class="item.status === 0 ? 'coupon_list_top': 'coupon_list_top_gray'"
                      v-if="item.actCode==='voucher'"
                    >
                      <span>￥</span>
                      <span
                        class="number"
                        :class="item.status === 0 ? 'number' : 'gray'"
                      >{{item.denomination}}</span>
                    </div>
                    <div
                      class="coupon_list_tops"
                      :class="item.status === 0 ? 'coupon_list_tops': 'coupon_list_tops_gray'"
                      v-if="item.actCode==='discount'"
                    >
                      <span class="discount_number">{{item.denomination}}</span>
                      <span>{{$t('payReward.discount')}}</span>
                    </div>
                    <div
                      class="coupon_list_top"
                      :class="item.status === 0 ? 'coupon_list_top': 'coupon_list_top_gray'"
                      v-if="item.actCode === 'random'"
                    >
                      ￥<span
                        class="number"
                        :class="item.status === 0 ? 'number' : 'gray'"
                      >{{item.randomMax}}</span>
                      <span
                        class="hightest"
                        :class="item.status === 0 ? 'hightest' : 'hightest-gray'"
                      >最高</span>
                    </div>
                    <div
                      class="coupon_center_limit"
                      :class="item.status === 0 ? 'coupon_center_limit': 'coupon_center_limit_gray'"
                    >{{item.useConsumeRestrict |
                      formatLeastConsume(item.leastConsume)}}
                    </div>
                    <!-- <div class="coupon_center_number">剩余{{item.surplus}}张</div> -->
                    <div
                      class="coupon_center_number"
                      :class="item.status === 0 ? 'coupon_center_number':'gray'"
                      v-if="item.surplus !==0"
                    >剩余{{item.surplus}}张</div>
                    <div
                      class="coupon_center_number"
                      :class="item.status === 0 ? 'coupon_center_number':'gray'"
                      v-if="item.surplus ===0"
                    >库存不限制</div>
                    <div
                      class="coupon_list_bottom"
                      :class="item.status === 0 ? 'coupon_list_bottom': 'coupon_list_bottom_gray'"
                      :style="`background-image: url(${$imageHost}/image/admin/coupon_border.png)`"
                    >
                      <!-- <span v-if="item.scoreNumber === 0">领取</span>
                      <div v-if="item.scoreNumber !== 0">
                        <span>{{item.scoreNumber}}</span>积分 兑换
                      </div> -->
                      <span>领取</span>
                    </div>
                  </section>
                  <span
                    class="deleteIcon"
                    @click="deleteCouponImg(index)"
                  >×
                  </span>
                </div>

                <div
                  class="rewardCouponInfo"
                  @click="handleToCallDialog()"
                  v-if="rewardCouponList.length<5"
                  style="line-height:normal"
                >
                  <div>
                    <el-image
                      fit="scale-down"
                      :src="imgHost+'/image/admin/shop_beautify/add_decorete.png'"
                      style="width:78px;height:78px;cursor:pointer;"
                    ></el-image>
                  </div>
                  <br>
                  <p class="textDesc">{{$t('groupBuy.addCoupon')}}</p>
                </div>
              </div>

              <div class="fontColor">{{$t('groupBuy.consolationPrizeComment2')}}</div>
            </el-card>
          </el-form-item>
          <!-- 引入活动分享模块 -->
          <el-form-item
            :label="$t('groupBuy.activitySharing') + '：'"
            prop="shareInfo"
          >
            <!-- <actShare :shareConfig="form.share" /> -->
            <div class="shareContent">
              <el-radio
                v-model="form.share.shareAction"
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
                  style="margin: 0 20 0 0px"
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
                v-model="form.share.shareAction"
                :label=2
              >自定义样式</el-radio>
              <div
                v-if="form.share.shareAction === 2"
                style="margin-left: 25px"
              >
                <span>文案：</span>
                <el-input
                  v-model="form.share.shareDoc"
                  size="small "
                  style="width: 180px;"
                ></el-input>
              </div>
              <div
                v-if="form.share.shareAction === 2"
                style="margin-left: 25px"
              >
                <span>分享图：</span>
                <el-radio
                  v-model="form.share.shareImgAction"
                  :label=1
                >活动商品信息图</el-radio>
                <div style="margin-left: 60px;">
                  <el-radio
                    v-model="form.share.shareImgAction"
                    :label=2
                  >自定义图片</el-radio>
                </div>

                <div
                  style="display: flex"
                  v-if="form.share.shareImgAction=== 2"
                >
                  <div
                    class="imgContent"
                    @click="addGoodsImg"
                  >
                    <div>
                      <img
                        v-if="form.share.shareImg === '' || form.share.shareImg === null"
                        :src="$imageHost + '/image/admin/btn_add.png'"
                        alt=""
                      >
                      <img
                        v-if="form.share.shareImg !== ''"
                        :src="$imageHost + '/' + form.share.shareImg"
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

          <el-form-item
            label="同步打标签："
            prop=""
          >
            <el-checkbox
              v-model="form.activityTag"
              :true-label="1"
              :false-label="0"
            >给参与活动用户打标签</el-checkbox>
            <span
              class="el-icon-question"
              style="color: #666;"
            ></span>
            <span
              class="labelStyle"
              @click="selectLabel"
            >选择标签</span>
            <div v-if="pickLabel.length > 0">
              <p style="color: #999;">最多可设置3个标签</p>
              <div
                v-for="(item, index) in pickLabel"
                :key="index"
                class="labelContent"
              >
                {{item.value}}
                <i
                  class="el-icon-close"
                  @click="deleteLabel(index)"
                  style="color: #999; margin-left: 3px;cursorL pointer;"
                ></i>
              </div>
            </div>
          </el-form-item>

          <el-form-item
            label="活动规则说明："
            prop=""
            :required="true"
          >
            <el-button
              type="primary"
              size="small"
              @click="friendHelpRule"
            >设置规则说明</el-button>
          </el-form-item>
        </div>
      </el-form>

      <!-- 规则说明 -->
      <ActivityRule
        v-if="ruleShow"
        @ActivityMsg="activityMsg"
        :sendMsg="sendMsg"
        :template="template"
      />

      <!--添加商品弹窗-->
      <choosingGoods
        @resultGoodsDatas="choosingGoodsResult"
        :chooseGoodsBack="goodsIdList"
        :tuneUpChooseGoods="isShowChoosingGoodsDialog"
        :singleElection="false"
        :showTips="true"
      />

      <!--添加优惠券-->
      <addCouponDialog
        @handleToCheck="handleToCheck"
        :tuneUpCoupon="showCouponDialog"
        :couponBack="couponIdList"
        :type="-1"
      />

      <!-- 选择图片弹框 -->
      <ImageDalog
        pageIndex='pictureSpace'
        :tuneUp="showImageDialog"
        @handleSelectImg='handleSelectImg'
        :imageSize="[800, 800]"
      />

      <div
        class="footer"
        v-if="!ruleShow"
      >
        <el-button
          size="small"
          type="primary"
          :disabled="submitStatus"
          @click="submitForm(form)"
        >{{$t('marketCommon.ok')}}
        </el-button>
      </div>
    </div>

    <!-- 规格信息弹框 -->
    <spellGroupDialog
      :productDialog.sync="showSpecDialog"
      :product-info="productInfo"
      :isEdit="isEdite"
      :isShowGrouperPrice="showGrouperPrice"
      @confrim="getProductdata"
    />

    <!-- 标签弹窗 -->
    <LabelDialog
      :dialogVisible="labelDialogVisible"
      :multipleLimit="3"
      @resultLabelDatas="resultLabelDatas"
      :chooseLabelBack="activityTagIdList"
    />

  </div>
</template>
<script>

import { mapActions } from 'vuex'
import choosingGoods from '@/components/admin/choosingGoods'
import addCouponDialog from '@/components/admin/addCouponDialog'
import ImageDalog from '@/components/admin/imageDalog'
import actShare from '@/components/admin/marketManage/marketActivityShareSetting'
import { addGroupBuyActivity, getGroupBuyDetail, updateGroupBuy } from '@/api/admin/marketManage/spellGroup.js'
import { getSelectGoods } from '@/api/admin/marketManage/distribution.js'

export default {
  components: {
    choosingGoods,
    addCouponDialog,
    actShare,
    ImageDalog,
    spellGroupDialog: () => import('./spellGroupDialog'),
    LabelDialog: () => import('@/components/admin/labelDialog'),
    ActivityRule: () => import('@/components/admin/activityRule')
  },
  props: ['isEdite', 'editId', 'isGoing'],
  filters: {
    formatLeastConsume (useConsumeRestrict, leastConsume) {
      if (useConsumeRestrict === 0) {
        return `不限制`
      } else {
        return `满${leastConsume}元可用`
      }
    }
  },
  data () {
    // 自定义校验器
    var dateValid = (rule, value, callback) => {
      console.log('校验时间')
      if (value === [] || value.length === 0) {
        return callback(new Error(this.$t('groupBuy.validityDateRules')))
      }
      callback()
    }
    var shareDocValid = (rule, value, callback) => {
      console.log('校验文案')
      console.log(rule, 'rule', value, 'value')
      if (value === [] || value.length === 0) {
        return callback(new Error('请输入活动文案'))
      }
      callback()
    }
    var shareInfoValid = (rule, value, callback) => {
      if (this.form.share.shareAction === 2 && (this.form.share.shareDoc === '' || this.form.share.shareDoc === null)) {
        return callback(new Error('请选择文案'))
      }
      if (this.form.share.shareImgAction === 2 && (this.form.share.shareImg === null || this.form.share.shareImg === '')) {
        return callback(new Error('请选择图片'))
      }
      callback()
    }
    var levelValid = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if (value === '') {
        return callback(new Error('请输入活动优先级'))
      } else if (!re.test(value)) {
        callback(new Error('请填写正整数'))
      } else {
        callback()
      }
    }
    // 自定义活动预告
    var validatePreTime = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if ((value !== 0) && (value !== -1) && (value !== 1)) {
        callback(new Error('请选择活动预告类型'))
      } else if (value === 1 && this.form.preTimeValue === '') {
        callback(new Error('请填写活动预告时间'))
      } else if (value === 1 && !re.test(this.form.preTimeValue)) {
        callback(new Error('活动预告时间填写不正确'))
      } else {
        callback()
      }
    }
    return {
      // from 表单数据
      form: {
        id: null,
        name: '',
        level: '',
        goodsId: '',
        limitAmount: 2,
        joinLimit: 0,
        openLimit: 0,
        isDefault: 0,
        startTime: '',
        endTime: '',
        validityDate: [],
        shippingType: 1,
        activityType: 1,
        isGrouperCheap: 0,
        rewardCouponId: '',
        limitMaxNum: 0,
        limitBuyNum: 0,
        beginNum: 0,
        share: {
          shareAction: 1,
          shareDoc: '',
          shareImgAction: 1,
          shareImg: ''
        },
        product: [],
        shareInfo: '',
        stock: 0, // 活动总库存
        preTime: 1, // 活动预告
        preTimeValue: '24', // 预告时间值
        activityTag: false, // 同步打标签
        activityTagId: null, // 标签id值

        // 规则说明
        activityCopywriting: {
          document: '',
          isUseDefault: 0
        }
      },
      goodsIdList: [],
      activityTagIdList: [],
      // 校验表单
      fromRules: {
        name: [
          { required: true, message: this.$t('groupBuy.activityNameRequiredRules'), trigger: 'blur' },
          { max: 20, message: this.$t('groupBuy.lengthMax20'), trigger: 'blur' }
        ],
        level: [
          { required: true, validator: levelValid, trigger: ['blur', 'change'] }
          // { max: 20, message: this.$t('groupBuy.lengthMax20'), trigger: 'blur' }
        ],
        goodsId: [
          { required: true, message: this.$t('groupBuy.goodsIdRequireRules'), trigger: 'change' }
        ],
        limitAmount: [
          { type: 'integer', required: true, message: this.$t('groupBuy.limitAmountRequireRules'), trigger: 'blur' }
        ],
        joinLimit: [
          { type: 'integer', required: true, message: this.$t('groupBuy.joinLimitRequireRules'), trigger: 'blur' }
        ],
        openLimit: [
          { type: 'integer', required: true, message: this.$t('groupBuy.openLimitRequireRules'), trigger: 'blur' }
        ],
        validityDate: [
          { required: true, validator: dateValid, trigger: 'blur' }
        ],
        'share.shareDoc': [{ validator: shareDocValid, trigger: 'blur' }],
        shareInfo: [{ required: true, validator: shareInfoValid, trigger: 'blur' }],
        preTime: [
          { required: true, validator: validatePreTime, trigger: 'change' }
        ]
      },
      // 选中商品id
      goodsRow: {},
      goodsIds: [],
      // 优惠券弹窗
      couponDialogFlag: false,
      couponList: [],

      rewardCouponList: [],
      rewardCouponIds: [],
      submitStatus: false,
      grouponType: [],
      isShowChoosingGoodsDialog: false,
      arrorFlag: true,
      ArrowArr: [
        {
          img_1: this.$imageHost + '/image/admin/show_more.png'
        },
        {
          img_2: this.$imageHost + '/image/admin/hid_some.png'
        }
      ],

      imgHost: `${this.$imageHost}`,
      showCouponDialog: false,
      couponIdList: [],

      activeIndex: 0,
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/pin_share.jpg`,
        src2: `${this.$imageHost}/image/admin/share/pin_pictorial.jpg`,
        src3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`
      },
      showImageDialog: false,
      showSpecDialog: false,
      productInfo: {},
      // disabledFlag: true, // 是否可编辑
      showGrouperPrice: false,

      labelDialogVisible: false, // 标签弹窗
      labelList: [], // 标签列表数据
      pickLabel: [], // 选中标签列表

      ruleShow: false, // 规则组件
      sendMsg: null, // 回显规则内容
      // 默认模板内容
      template: `
        <div style="line-height: 1.5;">
          <p>如何才算拼团成功？</p>
          <p>每个团的有效期为24小时，有效期内找到满足人数的小伙伴参与拼团，拼团即会成功。</p>
          <p>拼团失败怎么办？</p>
          <p>若24小时内未凑够拼团人数，则拼团失败，失败后系统会自动将所有支付的货款原路返回，具体到账时间按照各支付渠道为准。</p>
          <div>
            <p>拼团流程</p>
            <image src="${this.$imageHost}/image/wxapp/icon_group4.png" style="max-width: 100%;"></image>
          </div>
        </div>
      `
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    if (this.isGoing === true) {
      this.editActivityInit()
      this.arrorFlag = false
    }
  },
  watch: {
    lang () {
      this.grouponType = this.$t('groupBuy.grouponType')
    },
    'form.goodsId': function (value) {
      if (value) {
        this.$refs.form.validateField('goodsId')
      }
    }
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 校验表格
    validateMoney (rule, value, callback, shopPrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (value < 0) {
        callback(new Error('请填写非负数'))
      } else if (!re.test(value)) {
        callback(new Error('请保留两位小数'))
      } else if (value > shopPrice) {
        callback(new Error('拼团价不能大于原价'))
      } else {
        callback()
      }
    },
    validateGrouperMoney (rule, value, callback, shopPrice) {
      var re = /^\d+(\.\d{1,2})?$/
      if (value < 0) {
        callback(new Error('请填写非负数'))
      } else if (!re.test(value)) {
        callback(new Error('请保留两位小数'))
      } else if (value > shopPrice) {
        callback(new Error('团长价不能大于原价'))
      } else {
        callback()
      }
    },
    validateNum (rule, value, callback, prdNumber) {
      // var re = /(^0|\+?[1-9][0-9]\d*)$/
      var re = /^([1-9]\d*|[0]{1,1})$/
      if (!re.test(value)) {
        callback(new Error('请填写0和正整数'))
      } else {
        callback()
      }
    },
    validatePrdPrice (item) {
      if (item.prdPrice < item.groupPrice) return true
      return false
    },
    validatePrdStock (item) {
      if (item.prdNumber < item.stock) return true
      return false
    },

    // 编辑活动初始化
    editActivityInit () {
      getGroupBuyDetail({ id: this.editId }).then(res => {
        if (res.error === 0) {
          let data = res.content
          console.log(data, 'init data')
          this.form.id = data.id
          this.form.activityType = data.activityType
          this.form.name = data.name
          this.form.goodsId = data.goodsId
          this.form.level = data.level
          this.form.product = this.initEditProduct(data.goodsList)
          this.form.isGrouperCheap = data.isGrouperCheap
          this.form.startTime = data.startTime
          this.form.endTime = data.endTime
          this.form.validityDate = [data.startTime, data.endTime]
          this.form.limitAmount = data.limitAmount
          this.form.limitBuyNum = data.limitBuyNum
          this.form.limitMaxNum = data.limitMaxNum
          this.form.joinLimit = data.joinLimit
          this.form.openLimit = data.openLimit
          this.form.isDefault = data.isDefault
          this.form.shippingType = data.shippingType
          this.form.beginNum = data.beginNum
          this.rewardCouponList = data.couponViews
          this.goodsIdList = data.goodsList.map(item => item.goodsId)

          if (this.form.isGrouperCheap === 1) {
            this.showGrouperPrice = true
          } else {
            this.showGrouperPrice = false
          }
          this.form.share = data.share
          // 活动预告
          this.form.preTime = data.preTime
          if (this.form.preTime > 0) {
            this.form.preTimeValue = this.form.preTime
            this.form.preTime = 1
          } else if (this.form.preTime === 0) {
            this.form.preTime = 24
            this.form.preTime = 0
          } else {
            this.form.preTime = -1
          }

          // 标签id tagList
          if (data.tagList && data.tagList.length > 0) {
            this.pickLabel = data.tagList
            this.activityTagIdList = []
            data.tagList.forEach(item => {
              this.activityTagIdList.push(item.id)
            })
          } else {
            this.activityTagIdList = []
          }
          this.form.activityCopywriting = JSON.parse(data.activityCopywriting)
          this.form.activityTag = Boolean(data.activityTag)
        }
      })
    },

    // 获取商品信息
    getGoodsInfo (id) {
      getSelectGoods({ goodsId: id }).then((res) => {
        if (res.error === 0) {
          this.goodsRow = res.content
          this.goodsRow.ischecked = true
        }
      })
    },
    // 提交表单 保存
    submitForm (formName) {
      this.submitStatus = true
      if (this.rewardCouponIds) {
        this.form.rewardCouponId = this.rewardCouponIds.join(',')
      }
      this.$refs['form'].validate((valid) => {
        console.log('提交表单', formName)
        console.log('this.form', this.form)
        if (valid) {
          // 活动预告
          if (this.form.preTime === 1) {
            this.form.preTime = Number(this.form.preTimeValue)
          }
          this.form.activityTag = Number(this.form.activityTag)
          this.form.activityTagId = String(this.activityTagIdList)
          this.form.activityCopywriting = JSON.stringify(this.form.activityCopywriting)

          // let goodsId = this.form.goodsId.join(',')
          this.form.product.forEach((item, index) => {
            item.groupPrice = Number(item.groupPrice)
            item.grouperPrice = Number(item.grouperPrice)
          })

          // 提交的时候，把product里面的字段拼接上去
          let product = []
          this.form.stock = 0
          this.form.product.forEach(item => {
            if (item.goodsSpecProducts) {
              item.goodsSpecProducts.forEach(specItem => {
                let { prdId, groupPrice, grouperPrice, stock } = specItem
                let goodsId = item.goodsId
                product.push({ goodsId, prdId, groupPrice: Number(groupPrice), grouperPrice: Number(grouperPrice), stock: Number(stock) })
                this.form.stock += Number(stock)
              })
            } else {
              let { goodsId, prdId, groupPrice, grouperPrice, stock } = item
              product.push({ goodsId, prdId, groupPrice, grouperPrice, stock: Number(stock) })
              this.form.stock += Number(stock)
            }
          })
          console.log(product)

          if (this.isGoing) {
            console.log(this.form, 'form--')
            updateGroupBuy({ ...this.form, product }).then(res => {
              console.log('updateGroupBuy', res)
              if (res.error === 0) {
                this.$message.success(res.message)
                this.$emit('addGroupBuySubmit')
              } else {
                this.$message.warning(res.message)
              }
            }).catch(e => {
              console.log(e)
              this.$message.warning(e.message)
            })
          } else {
            addGroupBuyActivity({ ...this.form, product }).then(res => {
              console.log('addGroupBuyActivity', res)
              if (res.error === 0) {
                this.$message.success(res.message)
                this.$emit('addGroupBuySubmit')
              } else {
                this.$message.warning(res.message)
              }
            })
          }
        } else {
          this.$message.warning('请正确填写表单!')
        }
      })
      this.submitStatus = false
    },
    // 选择商品弹窗
    showChoosingGoods () {
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    // 获取商品ids
    choosingGoodsResult (row) {
      console.log(row, 'get row')
      this.goodsIdList = row.map(item => { return item.goodsId })
      this.goodsRow = row
      this.form.goodsId = this.goodsIdList.join(',')
      console.log(this.form.goodsId, 'goodsId--')
      this.form.product = row
    },
    changeSwitchState (val) {
      if (val === 1) {
        this.showGrouperPrice = true
      } else {
        this.showGrouperPrice = false
      }
    },
    getProductdata ({ goodsId, prdInfo }) {
      console.log(goodsId, prdInfo)
      console.log(this.form.product)
      let target = this.form.product.find(item => { return item.goodsId === goodsId })
      let index = this.form.product.findIndex(item => { return item.goodsId === goodsId })
      this.$set(this.form.product[index], 'groupPrice', prdInfo[0].groupPrice)
      console.log(this.form.product)
      this.$set(this.form.product[index], 'stock', prdInfo[0].stock)
      if (this.form.isGrouperCheap === 1) {
        this.$set(this.form.product[index], 'grouperPrice', prdInfo[0].grouperPrice)
      }
      target.goodsSpecProducts = prdInfo
      this.changePriceInput(target, true)
      this.changeStockInput(target, true)
      this.changeGrouperPriceInput(target, true)
    },
    initEditProduct (goods) {
      let newdata = []
      goods.forEach(item => {
        let expand = item.productList.length < 2 ? { ...item.productList[0], goodsName: item.goodsName } : { ...item.productList[0], goodsSpecProducts: item.productList, goodsName: item.goodsName }
        newdata.push({ ...item, ...expand })
      })
      newdata.forEach(item => {
        if (item.goodsSpecProducts && item.goodsSpecProducts.length > 0) {
          item.totalStock = 0
          item.goodsSpecProducts.forEach(val => {
            item.totalStock += parseInt(val.stock)
          })
        }
      })
      return newdata
    },
    showSpec (goodsInfo) {
      this.productInfo = goodsInfo
      this.showSpecDialog = true
      console.log(this.showSpecDialog)
    },
    changePriceInput (goodsInfo, isDialog = null) {
      if (goodsInfo.goodsSpecProducts && goodsInfo.goodsSpecProducts.length > 0) {
        goodsInfo.priceErrorMsg = null
        goodsInfo.goodsSpecProducts.forEach((item, index) => {
          if (!isDialog) item.groupPrice = goodsInfo.groupPrice
          // if (this.validatePrdPrice(item) && !goodsInfo.priceErrorMsg) {
          //   goodsInfo.priceErrorMsg = '有规格拼团价大于原价，请修改'
          // }
        })
      }
    },
    changeGrouperPriceInput (goodsInfo, isDialog = null) {
      if (goodsInfo.goodsSpecProducts && goodsInfo.goodsSpecProducts.length > 0) {
        goodsInfo.grouperPriceErrorMsg = null
        goodsInfo.goodsSpecProducts.forEach((item, index) => {
          if (!isDialog) item.grouperPrice = goodsInfo.grouperPrice
          // if (this.validatePrdPrice(item) && !goodsInfo.grouperPriceErrorMsg) {
          //   goodsInfo.grouperPriceErrorMsg = '有规格团长价大于原价，请修改'
          // }
        })
      }
    },
    changeStockInput (goodsInfo, isDialog = null) {
      if (goodsInfo.goodsSpecProducts && goodsInfo.goodsSpecProducts.length > 0) {
        goodsInfo.stockErrorMsg = null
        goodsInfo.totalStock = 0
        goodsInfo.goodsSpecProducts.forEach((item, index) => {
          if (!isDialog) item.stock = goodsInfo.stock
          goodsInfo.totalStock += parseInt(item.stock)
          if (this.validatePrdStock(item) && !goodsInfo.stockErrorMsg) {
            goodsInfo.stockErrorMsg = '有规格拼团库存大于原库存，请修改'
          }
        })
      } else {
        var re = /^([1-9]\d*|[0]{1,1})$/
        goodsInfo.stockErrorMsg = null
        if ((goodsInfo.stock > goodsInfo.goodsNumber) && !goodsInfo.stockErrorMsg) {
          goodsInfo.stockErrorMsg = '拼团库存不能大于原库存'
        } else if (goodsInfo.stock < 0) {
          goodsInfo.stockErrorMsg = '请输入非负数'
        } else if (!re.test(goodsInfo.stock)) {
          goodsInfo.stockErrorMsg = '请输入0和正整数'
        } else {

        }
      }
    },
    deleteGoods (data, id) {
      let idList = []
      this.form.product.map(item => {
        idList.push(item.goodsId)
      })
      this.goodsIdList = idList
      let index = this.goodsIdList.findIndex(item => {
        return item === id
      })
      this.goodsIdList.splice(index, 1)
      let goodsTarget = this.form.product.findIndex(item => {
        return id === item.id
      })
      this.form.product.splice(goodsTarget, 1)
    },
    // 确认选择优惠券-新增
    handleToCheck (data, index) {
      console.log(data, 'coupon data---', index, 'index---')
      if (this.rewardCouponList.length < 5) {
        let invalid = this.rewardCouponList.filter(item => item.status !== 0)
        this.rewardCouponList = data.concat(invalid)
        this.rewardCouponIds = []
        this.rewardCouponList.map((item, index) => {
          this.rewardCouponIds.push(item.id)
        })
      } else {
        this.$message.warning('优惠券不能超过五张')
        return false
      }
    },
    // 删除鼓励奖优惠券图片
    deleteCouponImg (index) {
      this.rewardCouponList.splice(index, 1)
      this.rewardCouponIds.splice(index, 1)
      this.couponIdList.splice(index, 1)
    },
    // 优惠券点击事件
    handleToCallDialog () {
      this.couponIdList = this.getCouponIdsArray(this.rewardCouponList)
      this.showCouponDialog = !this.showCouponDialog
    },
    // 活动时间时间选择
    dateChange (date) {
      this.form.startTime = date[0]
      this.form.endTime = date[1]
      console.log(date)
    },
    // 设置数据
    setCurrent (index) {
      // 拷贝一份数据
      let price = JSON.parse(JSON.stringify(this.form.product))
      switch (index) {
        case 1:
          price.forEach(row => {
            row.groupPrice = price[0].groupPrice
            console.log(row)
            this.changePriceInput(row)
          })
          this.activeIndex = 1
          break
        case 2:
          price.forEach(row => {
            row.grouperPrice = price[0].grouperPrice
            this.changeGrouperPriceInput(row)
          })
          this.activeIndex = 2
          break
        case 3:
          price.forEach(row => {
            row.stock = price[0].stock
            this.changeStockInput(row)
          })
          this.activeIndex = 3
          break
      }
      this.form.product = price
    },

    getCouponIdsArray (data) {
      let res = []
      data.forEach((item, index) => {
        res.push(item.id)
      })
      return res
    },

    // 改变箭头事件
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    },

    // 校验拼团价格
    checkGroupPriceVaild (rule, value, callback) {
      console.log('拼团价格校验', rule, value, callback)
      if (value === undefined || JSON.stringify(value) === '{}') {
        callback(new Error('拼团价格不能为空'))
      }
      callback()
    },
    addGoodsImg () {
      this.showImageDialog = !this.showImageDialog
    },
    // 图片点击回调函数
    handleSelectImg (res) {
      if (res != null) {
        this.form.share.shareImg = res.imgPath
      }
    },
    // 活动预告类型切换
    preTimeChange (e) {
      this.$refs['form'].validateField('preTime')
    },

    // 标签弹窗
    selectLabel () {
      if (this.isEdite === true) {
        return false
      }
      this.labelDialogVisible = !this.labelDialogVisible
    },

    // 删除标签
    deleteLabel (index) {
      if (this.isEdite === true) {
        this.$message.warning('编辑状态不可操作')
        return false
      }
      this.pickLabel.splice(index, 1)
      this.activityTagIdList = []
      this.pickLabel.forEach(item => {
        this.activityTagIdList.push(item.id)
        console.log(this.activityTagIdList)
      })
    },

    // 标签弹窗回调函数
    resultLabelDatas (row) {
      this.pickLabel = row
      this.activityTagIdList = []
      this.pickLabel.forEach(item => {
        this.activityTagIdList.push(item.id)
      })
    },

    // 设置规则说明
    friendHelpRule () {
      this.ruleShow = true
      this.sendMsg = this.form.activityCopywriting
    },
    // 规则说明回调函数
    activityMsg (data) {
      this.ruleShow = false
      this.form.activityCopywriting = data
    }
  }
}
</script>
<style scoped>
.prompt {
  color: #999;
  margin-left: 20px;
  display: inline;
}

.fontColor {
  color: #606266;
}

.box-card {
  width: 630px;
  background: #f5f5f5;
}

.middleContainer {
  display: flex;
}

.deleteIcon {
  position: relative;
  width: 17px;
  height: 17px;
  top: -114px;
  left: 47px;
  cursor: pointer;
  opacity: 0.8;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  border-radius: 50%;
  text-align: center;
}

.rewardCouponInfo {
  display: inline-block;
  position: relative;
  width: 100px;
  height: 96px;
  margin-bottom: 10px;
  background: #fff;
  border: 1px solid #e4e4e4;
  cursor: pointer;
  text-align: center;
  border-radius: 10px;
}

.picture {
  margin-top: 10px;
}

.textDesc {
  line-height: normal;
  margin-top: -38px;
  color: #999;
}

.couponImgWrapper {
  width: 100%;
  height: 100%;
  border: 1px solid #fbb;
  border-radius: 10px;
}
.couponImgWrapperGary {
  width: 100%;
  height: 100%;
  border: 1px solid #d5d7d9;
  border-radius: 10px;
}

.coupon_list_top {
  margin-top: 10px;
  color: #f60;
}
.coupon_list_top_gray {
  margin-top: 10px;
  color: #d5d7d9;
}

.coupon_list_tops {
  font-weight: bold;
  margin-top: 10px;
  color: #f60;
}
.coupon_list_tops_gray {
  font-weight: bold;
  margin-top: 10px;
  color: #d5d7d9;
}
.discount_number {
  font-size: 20px;
}

.coupon_center_limit {
  color: #f60;
  font-size: 12px !important;
}
.coupon_center_limit_gray {
  color: #d5d7d9;
}

.coupon_center_number {
  height: 18px;
  color: #fbb;
  font-size: 12px;
}

.gray {
  height: 18px;
  color: #d5d7d9;
  font-size: 12px;
}

.coupon_list_bottom {
  height: 24px;
  line-height: 30px;
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;
  color: #fff;
  background: #f66;
  font-size: 12px;
  background-repeat: repeat-x;
}
.coupon_list_bottom_gray {
  background: #d5d7d9;
  background-repeat: repeat-x;
}
.hightest {
  font-size: 12px;
  font-weight: bold;
}
.hightest-gray {
  font-size: 12px;
  font-weight: bold;
  color: #d5d7d9;
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

.wrapper {
  margin: 10px 0;
  padding: 10px 0;
  margin-bottom: 80px;
  background: #fff;
}

.moreSetUp a {
  margin-right: 10px;
  cursor: pointer;
}

.settings {
  color: #5a8bff;
}

.el-table tbody .el-table__row {
  height: 200px !important;
}
.shareContent a {
  text-decoration: none;
  color: #409eff;
}
.imgContent {
  width: 70px;
  height: 70px;
  text-align: center;
  line-height: 65px;
  margin-left: 60px;
  border: 1px solid #e4e4e4;
  cursor: pointer;
  box-sizing: border-box;
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
.spec-tips {
  text-align: center;
  color: #409eff;
  cursor: pointer;
}
.input-error {
  text-align: center;
  color: red;
  line-height: 1;
}
.number {
  font-size: 20px;
  font-weight: bold;
}
.labelStyle {
  color: #5a8bff;
  cursor: pointer;
}
.labelContent {
  display: inline-block;
  height: 30px;
  background: rgba(235, 241, 255, 1);
  border: 1px solid rgba(180, 202, 255, 1);
  border-radius: 2px;
  text-align: center;
  line-height: 30px;
  padding: 0 10px;
  margin-right: 10px;
  color: #666;
}
.hightest {
  font-size: 12px;
  font-weight: bold;
}
</style>
<style lang="scss" scoped>
/deep/ .tableHeader th {
  line-height: 0 !important;
  height: 36px !important;
  font-weight: bold;
  padding: 8px 10px;
  background-color: #f5f5f5;
  color: #000;
  border: none;
}
</style>
