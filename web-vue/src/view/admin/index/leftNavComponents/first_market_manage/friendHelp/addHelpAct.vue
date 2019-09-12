
<template>
  <wrapper>
    <div class="content">
      <div class="main">
        <el-form
          :model="form"
          label-width="150px"
          labelPosition='right'
          :rules="formRules"
          ref="form"
        >
          <el-form-item
            label="活动名称："
            prop="actName"
          >
            <el-input
              size="small"
              placeholder="请填写活动名称"
              class="morelength"
              v-model="form.actName"
            ></el-input>
            <span style="margin-left: 10px">查看活动规则</span>
          </el-form-item>
          <el-form-item
            label="活动有效期："
            prop=""
            required
          >
            <el-form-item prop="startTime">
              <el-date-picker
                v-model="form.startTime"
                type="datetime"
                placeholder="选择日期时间"
                class="morelength"
                size="small"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
            </el-form-item>
            <span style="margin: 0 5px">至</span>
            <el-form-item prop="endTime">
              <el-date-picker
                v-model="form.endTime"
                type="datetime"
                placeholder="选择日期时间"
                class="morelength"
                size="small"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
            </el-form-item>
          </el-form-item>
          <el-form-item
            label="奖励类型："
            prop=""
          >
            <el-radio
              v-model="form.rewardType"
              label=0
            >
              赠送商品
            </el-radio>
            <el-radio
              v-model="form.rewardType"
              label=1
            >折扣商品</el-radio>
            <el-radio
              v-model="form.rewardType"
              label=2
            >赠送优惠券</el-radio>
            <el-col v-if="form.rewardType==0 || form.rewardType==1">
              <el-button
                size="small"
                type="primary"
                @click="showChoosingGoods"
              >+ 选择商品</el-button>
            </el-col>
            <el-col v-if="form.rewardType==2">
              <el-button
                size="small"
                type="primary"
                @click="isEditFlag?'':handleToCallDialog()"
              >+选择优惠券</el-button>
            </el-col>
            <div></div>
          </el-form-item>
          <el-form-item
            label="奖励设置："
            prop=""
          >
            <el-table
              v-if="form.rewardType==0 || form.rewardType==1"
              class="version-manage-table"
              header-row-class-name="tableClass"
              :data="form.goodsInfo"
              border
              style="width: 50%"
            >
              <el-table-column
                width="150%"
                prop="goodsName"
                label="商品信息"
                align="center"
              >
              </el-table-column>

              <el-table-column
                width="150%"
                prop="shopPrice"
                label="商品价格"
                align="center"
              >
              </el-table-column>

              <el-table-column
                width="150%"
                prop="goodsNumber"
                label="商品库存"
                align="center"
              >
              </el-table-column>

              <el-table-column
                width="150%"
                prop=""
                label="活动库存"
                align="center"
              >
                <el-input v-model="form.goodsInfo.market_store"></el-input>
              </el-table-column>

              <el-table-column
                v-if="form.rewardType==1"
                width="150%"
                label="活动价"
                align="center"
              >
                <el-input v-model="form.goodsInfo.market_price"></el-input>
              </el-table-column>
            </el-table>

            <!-- <div v-if="form.rewardType == '2'">hello world</div> -->

            <el-table
              v-if="form.rewardType==2"
              :data="coupon_info"
              border
              style="width: 20%"
            >
              <el-table-column
                label="优惠券信息"
                width="180%"
              >
                <template slot-scope="scope">
                  <div class="coupon_info">
                    <span class="coupon_name">{{scope.row.actName}}</span>
                    <div
                      class="coupon_price"
                      v-if="scope.row.actCode == 'voucher'"
                    >￥<span>{{scope.row.denomination}}</span></div>
                    <div
                      class="coupon_price"
                      v-else
                    ><span>{{scope.row.denomination}}</span>折</div>
                    <div class="coupon_rule">{{scope.row.useConsumeRestrict > 0? `满${scope.row.leastConsume}元可用`  : `不限制`}}</div>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                label="发券数量"
                width="120%"
              >
                <template slot-scope="scope">
                  <div>
                    <el-input
                      v-model="scope.row.send_num"
                      size="small"
                      style="width:100px;"
                    ></el-input>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>

          <el-form-item
            label="奖励有效期："
            prop=""
          >
            <div style="display:flex">
              <el-form-item prop="rewardDuration">
                <el-input
                  size="small"
                  v-model="form.rewardDuration"
                ></el-input>
              </el-form-item>
              <el-select
                size="small"
                v-model="form.rewardDurationUnitSelect"
                style="margin: 0 10px; width: 90px"
              >
                <el-option
                  v-for="item in form.rewardDurationUnit"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <div class="gray">用户获得奖励后在有效期内未领取则奖励失效，不可再领取</div>
            </div>
          </el-form-item>
          <el-form-item
            label="当次助力值："
            prop=""
          >
            <el-radio
              v-model="form.promoteType"
              label="0"
            >平均值</el-radio>
            <el-radio
              v-model="form.promoteType"
              label="1"
            >随机助力值</el-radio>
            <span>查看规则</span>
          </el-form-item>
          <el-form-item
            label="所需助力值："
            prop="promoteAmount"
          >
            <div style="display:flex">
              <el-input
                size="small"
                style="margin-right: 10px"
                v-model="form.promoteAmount"
              ></el-input>
              <div class="gray">用户发起抢购活动，助力值达到要求则助力成功，可领取奖励，建议填写大于100的整数</div>
            </div>
          </el-form-item>
          <el-form-item
            label="所需助力次数："
            prop="promoteTimes"
          >
            <div style="display:flex">
              <el-input
                size="small"
                style="margin-right: 10px"
                v-model="form.promoteTimes"
              ></el-input>
              <div class="gray">活动需要好友帮忙助力的总次数</div>
            </div>
          </el-form-item>
          <el-form-item
            label="发起次数限制："
            prop=""
          >
            <div style="display:flex">
              <span>用户在</span>
              <el-input
                style="margin: 0 5px"
                size="small"
                v-model="form.launchLimitDuration"
              ></el-input>
              <el-select
                size="small"
                v-model="form.launchLimitUnitSelect"
                style="margin-right:5px; width: 90px"
              >
                <el-option
                  v-for="item in form.launchLimitUnit"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <span>内最多可发起</span>
              <el-input
                size="small"
                style="margin:0 5px"
                v-model="form.launchLimitTimes"
              ></el-input>次
              <div
                style="margin-left:10px"
                class="gray"
              >用户在某时间段内最多可发起抢购活动的次数，填写0表示不限制</div>
            </div>
          </el-form-item>
          <el-form-item
            label="分享增加助力机会："
            prop="shareCreateTimes"
          >
            <div style="display:flex">
              <span>好友可通过分享获得最多</span>
              <el-input
                style="margin:0 5px"
                size="small"
                v-model="form.shareCreateTimes"
              ></el-input>
              <span>次助力机会</span>
              <div
                style="margin-left: 10px"
                class="gray"
              >好友通过帮忙分享可获得的助力次数，除分享获得助力次数外，默认每人最少1次助力机会 </div>
            </div>
          </el-form-item>
          <el-form-item
            label="好友助力条件："
            prop=""
          >
            <el-radio
              v-model="form.promoteCondition"
              label="0"
            >可不授权个人信息</el-radio>
            <el-radio
              v-model="form.promoteCondition"
              label="1"
            >需要授权个人信息</el-radio>
            <span class="gray">好友帮忙助力时，是否需要授权个人信息（头像+昵称）</span>
          </el-form-item>

          <el-form-item
            v-if="form.rewardType == 1"
            label="优惠叠加策略："
            prop=""
          >
            <el-radio
              v-model="form.useDiscount"
              label="1"
            >可叠加</el-radio>
            <el-radio
              v-model="form.useDiscount"
              label="0"
            >不可叠加</el-radio>
            <span class="gray">活动商品结算时是否可与会员卡折扣、优惠券叠加使用</span>
          </el-form-item>
          <el-form-item
            v-if="form.rewardType == 1"
            label="积分抵扣策略："
            prop=""
          >
            <el-radio
              v-model="form.useScore"
              label="1"
            >可抵扣</el-radio>
            <el-radio
              v-model="form.useScore"
              label="0"
            >不可抵扣</el-radio>
            <span class="gray">活动商品结算时是否可使用积分抵扣部分金额</span>
          </el-form-item>
          <el-form-item
            label="助力失败赠送："
            prop=""
          >
            <el-radio
              v-model="form.failedSendType"
              label="0"
            >
              不赠送
            </el-radio>
            <el-radio
              v-model="form.failedSendType"
              label="1"
            >优惠券</el-radio>
            <el-radio
              v-model="form.failedSendType"
              label="2"
            >积分</el-radio>
            <div v-if="form.failedSendType==1">
              <el-button
                size="small"
                type="primary"
                @click="isEditFlag?'':handleToCallDialog()"
              >+选择优惠券</el-button>
            </div>
            <div v-if="form.failedSendType==2">
              赠送积分值：
              <el-input
                size="small"
                type="primary"
                v-model="form.failedSendContent"
              ></el-input>
            </div>
            <el-table
              v-if="form.failedSendType==1"
              :data="coupon_info"
              border
              style="width: 10%"
            >
              <el-table-column
                label="优惠券信息"
                width="150%"
              >
                <template slot-scope="scope">
                  <div class="coupon_info">
                    <span class="coupon_name">{{scope.row.actName}}</span>
                    <div
                      class="coupon_price"
                      v-if="scope.row.actCode == 'voucher'"
                    >￥<span>{{scope.row.denomination}}</span></div>
                    <div
                      class="coupon_price"
                      v-else
                    ><span>{{scope.row.denomination}}</span>折</div>
                    <div class="coupon_rule">{{scope.row.useConsumeRestrict > 0? `满${scope.row.leastConsume}元可用`  : `不限制`}}</div>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>

          <div></div>
          <el-collapse>
            <el-collapse-item>
              <template slot="title">
                展开更多配置
              </template>
              <el-form-item
                label="活动分享："
                prop=""
              >
                <div>
                  <el-radio
                    v-model="form.activityShareType"
                    label="0"
                  >
                    默认样式
                    <span>分享预览</span>
                    <span>海报预览</span>
                  </el-radio>
                </div>
                <div>
                  <el-radio
                    v-model="form.activityShareType"
                    label="1"
                  >
                    自定义样式
                    <div v-if="form.activityShareType == 1">
                      <div style="margin: 15px 0">
                        <span>文案：</span>
                        <el-input
                          size="small"
                          style="width:200px"
                          v-model="form.customShareWord"
                        ></el-input>
                      </div>
                      <div>
                        <span>分享图：</span>
                        <el-radio
                          v-model="form.shareImgType"
                          label="0"
                        >活动商品信息图</el-radio>
                        <div style="margin: 10px 0 0 60px">
                          <el-radio
                            v-model="form.shareImgType"
                            label="1"
                          >自定义图片</el-radio>

                          <div
                            style="display: flex;align-items: center;flex-wrap: wrap;"
                            v-if="form.shareImgType == 1"
                          >
                            <div
                              v-for="(src,index) in goodsProductInfo.goodsImgs"
                              :key="index"
                              class="goodsImgWrap"
                            >
                              <el-image
                                fit="cover"
                                :src="src"
                                style="width: 78px; height: 78px;"
                              ></el-image>
                              <span
                                @click="deleteGoodsImg(index)"
                                class="deleteIcon"
                              >×</span>

                            </div>
                            <div
                              class="goodsImgWrap"
                              @click="addGoodsImg"
                              v-if="goodsProductInfo.goodsImgs.length < 1"
                            >
                              <el-image
                                fit="scale-down"
                                :src="imgHost+'/image/admin/add_img.png'"
                                style="width: 78px; height: 78px;cursor: pointer;"
                              />
                            </div>
                            <span class="inputTip">
                              建议尺寸：800*800像素
                            </span>
                          </div>

                        </div>
                      </div>
                    </div>

                  </el-radio>
                </div>
              </el-form-item>
            </el-collapse-item>
          </el-collapse>
        </el-form>
      </div>

      <div class="footer">
        <el-button
          type="primary"
          size="small"
          @click="addAct"
        >保存</el-button>
      </div>
    </div>
    <choosingGoods @resultGoodsRow="choosingGoodsResult">
    </choosingGoods>
    <!--添加优惠卷-->
    <AddCouponDialog
      singleElection=“true”
      @handleToCheck="handleToCheck"
    />
    <ImageDalog
      pageIndex='pictureSpace'
      @handleSelectImg='imgDialogSelectedCallback'
    />
  </wrapper>
</template>

<script>
import { mapActions } from 'vuex'
import wrapper from '@/components/admin/wrapper/wrapper'
import choosingGoods from '@/components/admin/choosingGoods'
import { addActive, selectOneInfo, updateInfo } from '@/api/admin/marketManage/friendHelp.js'
import { getGoodsProductList } from '@/api/admin/brandManagement.js'
import ImageDalog from '@/components/admin/imageDalog'
export default {
  components: {
    wrapper,
    choosingGoods,
    ImageDalog,
    AddCouponDialog: () => import('@/components/admin/addCouponDialog')
  },
  data () {
    return {
      promoteId: '',
      show: false,
      radio: 'one',
      isEditFlag: false,
      goodsProductInfo: {
        // 基本信息
        goodsName: null,
        goodsAd: null,
        goodsSn: null,
        catId: null,
        goodsImgs: []

      },
      // 表单
      form: {
        test: '',
        actName: '',
        rewardType: '0',
        rewardContent: '',
        coupon_store: '',
        rewardSet: {
          goods_ids: '',
          reward_ids: '',
          market_price: '',
          market_store: ''
        },
        useDiscount: '0',
        useScore: '1',
        startTime: '',
        endTime: '',
        ruleForm: {},
        rewardDuration: '',
        rewardDurationUnit: [{
          value: 0,
          label: '小时'
        }, {
          value: 1,
          label: '天'
        }, {
          value: 2,
          label: '周'
        }],
        rewardDurationUnitSelect: '',
        promoteType: '0',
        promoteAmount: '',
        promoteTimes: '',
        launchLimitDuration: '',
        launchLimitUnit: [{
          value: 0,
          label: '天'
        }, {
          value: 1,
          label: '周'
        }, {
          value: 2,
          label: '月'
        }, {
          value: 3,
          label: '年'
        }],
        launchLimitUnitSelect: '',
        launchLimitTimes: '',
        shareCreateTimes: '',
        promoteCondition: '0',
        failedSendType: '0',
        failedSendContent: '',
        activityShareType: '0',
        customShareWord: '',
        shareImgType: '0',
        // customImgPath: '',
        // 选中商品id
        goodsInfo: [{
          goodsIds: '',
          goodsName: '',
          shopPrice: '',
          goodsNumber: '',
          rewardType: '',
          market_price: '',
          market_store: ''
        }]

      },
      // 优惠券
      coupon_msg: [],
      coupon_info: [],
      couponDialogFlag: false,
      couponSetDialogFlag: false,
      coupon_set: {
        immediatelyGrantAmount: 0,
        timingEvery: 0,
        timingAmount: 0,
        timingTime: '1',
        timingUnit: '0'
      },
      target: null,

      // 表单约束
      formRules: {
        actName: [
          { required: true, message: '此处不能为空！', trigger: 'blur' }
        ],
        startTime: [
          { required: true, message: '此处不能为空！', trigger: 'change' }
        ],
        endTime: [
          { required: true, message: '此处不能为空！', trigger: 'change' }
        ],
        rewardDuration: [
          { required: true, message: '此处不能为空！', trigger: 'blur' }
        ],
        promoteAmount: [
          { required: true, message: '此处不能为空！', trigger: 'blur' }
        ],
        promoteTimes: [
          { required: true, message: '此处不能为空！', trigger: 'blur' }
        ],
        shareCreateTimes: [
          { required: true, message: '此处不能为空！', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.form.rewardDurationUnitSelect = this.form.rewardDurationUnit[0].value
    this.form.launchLimitUnitSelect = this.form.launchLimitUnit[0].value
    this.promoteId = this.$route.params.id
    if (this.promoteId != null) {
      console.log('id:', this.promoteId)
      this.loadData(this.promoteId)
    }
  },
  mounted () {
    // console.log(222)
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    loadData (id) {
      let selectParam = {
        'id': id
      }
      selectOneInfo(selectParam).then(res => {
        console.log('pageInfo:', res.content[0])
        this.form.actName = res.content[0].actName
        this.form.startTime = res.content[0].startTime
        this.form.endTime = res.content[0].endTime
        this.form.rewardType = res.content[0].rewardType.toString()
        this.form.rewardDuration = res.content[0].rewardDuration
        this.form.rewardDurationUnitSelect = res.content[0].rewardDurationUnit
        this.form.promoteType = res.content[0].promoteType.toString()
        this.form.promoteAmount = res.content[0].promoteAmount
        this.form.promoteTimes = res.content[0].promoteTimes
        this.form.launchLimitDuration = res.content[0].launchLimitDuration
        this.form.launchLimitUnitSelect = res.content[0].launchLimitUnit
        this.form.launchLimitTimes = res.content[0].launchLimitTimes
        this.form.shareCreateTimes = res.content[0].shareCreateTimes
        this.form.promoteCondition = res.content[0].promoteCondition.toString()
        this.form.useDiscount = res.content[0].useDiscount.toString()
        this.form.useScore = res.content[0].useScore.toString()
        this.form.failedSendType = res.content[0].failedSendType.toString()
        this.form.failedSendContent = res.content[0].failedSendContent
        this.form.activityShareType = res.content[0].activityShareType.toString()
        this.form.customShareWord = res.content[0].customShareWord
        this.form.shareImgType = res.content[0].shareImgType.toString()
        this.form.customImgPath = res.content[0].customImgPath
      })
    },
    addAct () {
      console.log('this.form.rewardType:', this.form.rewardType)
      if (this.form.rewardType === '0' || this.form.rewardType === '1') {
        this.form.rewardSet.market_price = this.form.goodsInfo.market_price
        this.form.rewardSet.market_store = this.form.goodsInfo.market_store
        this.form.rewardContent = '[' + JSON.stringify(this.form.rewardSet) + ']'
        console.log('this.form.rewardSet.goods_ids:', this.form.rewardSet.goods_ids)
        console.log('rewardSet:', this.form.rewardSet)
        console.log('rewardContent:', this.form.rewardContent)
      }
      if (this.form.rewardType === '2') {
        this.form.rewardSet.market_store = this.coupon_info[0].send_num
        this.form.rewardContent = '[' + JSON.stringify(this.form.rewardSet) + ']'
        console.log('rewardSet:', this.form.rewardSet)
        console.log('rewardContent:', this.form.rewardContent)
      }
      let addParam = {
        'id': this.promoteId,
        'actName': this.form.actName,
        'startTime': this.form.startTime,
        'endTime': this.form.endTime,
        'rewardType': this.form.rewardType,
        'rewardContent': this.form.rewardContent,
        'rewardDuration': this.form.rewardDuration,
        'rewardDurationUnit': this.form.rewardDurationUnitSelect,
        'promoteType': this.form.promoteType,
        'promoteAmount': this.form.promoteAmount,
        'promoteTimes': this.form.promoteTimes,
        'launchLimitDuration': this.form.launchLimitDuration,
        'launchLimitUnit': this.form.launchLimitUnitSelect,
        'launchLimitTimes': this.form.launchLimitTimes,
        'shareCreateTimes': this.form.shareCreateTimes,
        'promoteCondition': this.form.promoteCondition,
        'useDiscount': this.form.useDiscount,
        'useScore': this.form.useScore,
        'failedSendType': this.form.failedSendType,
        'failedSendContent': this.form.failedSendContent,
        'activityShareType': this.form.activityShareType,
        'customShareWord': this.form.customShareWord,
        'shareImgType': this.form.shareImgType
      }
      this.$refs['form'].validate((valid) => {
        console.log('submit', this.form)
        if (valid) {
          if (this.promoteId != null) {
            console.log('I am updating!')
            updateInfo(addParam).then(res => {
              console.log(res)
              if (res.error === 0) {
                alert('修改成功!')
                this.$router.push({
                  name: 'promote'
                })
              }
            }).catch(() => {
              this.$message.error('操作失败')
            })
          } else {
            console.log('I am adding!')
            addActive(addParam).then(res => {
              console.log(res)
              if (res.error === 0) {
                alert('添加成功!')
                this.$router.push({
                  name: 'promote'
                })
              }
            }).catch(() => {
              this.$message.error('操作失败')
            })
          }
        } else {
          this.$message.error('数据不合法')
          return false
        }
      })
    },
    /* 添加图片点击事件，弹出图片选择组件 */
    addGoodsImg () {
      this.$http.$emit('dtVisible')
    },
    /* 商品图片点击回调函数 */
    imgDialogSelectedCallback (src) {
      if (this.goodsProductInfo.goodsImgs.length >= 1) {
        return
      }
      this.goodsProductInfo.goodsImgs.push(src)
    },
    /* 删除商品图片 */
    deleteGoodsImg (index) {
      this.goodsProductInfo.goodsImgs.splice(index, 1)
    },

    // 选择商品弹窗
    showChoosingGoods () {
      this.transmitEditGoodsId(this.form.goodsInfo.goodsIds)
      // console.log('初始化商品弹窗', this.form.rewardContent.goodsIds)
      this.$http.$emit('choosingGoodsFlag', true, 'choiseOne')
    },

    nextStep () {
      if (!this.validateParam()) {
        return
      }
      this.step++
      this.transmitEditGoodsId(this.form.goodsInfo.goodsIds)
    },
    lastStep () {
      this.step--
      this.transmitEditGoodsId(this.form.goodsInfo.goodsIds)
    },
    //  获取商品ids
    choosingGoodsResult (row) {
      console.log('获取商品行', row)
      this.goodsRow = row
      this.form.goodsInfo.goodsIds = row.goodsId
      this.form.rewardSet.goods_ids = row.goodsId
      // 初始化规格表格
      let obj = {
        goodsId: this.form.goodsInfo.goodsIds,
        currentPage: 1,
        pageRows: 1024
      }
      getGoodsProductList(obj).then(res => {
        const { content } = res
        const { dataList } = content

        this.form.goodsInfo = [dataList[obj.goodsId]]
      })
    },
    // 选择优惠券弹窗
    handleToCallDialog () {
      let obj = {
        couponDialogFlag: !this.couponDialogFlag,
        couponList: this.coupon_info
      }
      this.$http.$emit('V-AddCoupon', obj)
    },
    // 确认选择优惠券-新增-删除
    handleToCheck (data) {
      // console.log('couponInfo:', data)
      // this.form.rewardSet.reward_ids = data[0].id
      // console.log('data[0].id', data[0].id)
      let couponArr = this.formatCoupon(data)
      let oldArr = this.unique([...this.coupon_info, ...couponArr], 'id')
      let couponKey = []
      couponArr.map((item) => {
        couponKey.push(item.id)
      })
      this.coupon_info = oldArr.filter((item) => {
        return couponKey.includes(item.id)
      })
      console.log(this.coupon_info)
    },
    // 添加优惠券初始项
    formatCoupon (data) {
      let arry = []
      let couponData = {
        immediatelyGrantAmount: 0,
        timingEvery: 0,
        timingAmount: 0,
        timingTime: '1',
        timingUnit: '0'
      }
      data.map(item => {
        arry.push(Object.assign({}, item, { send_num: '', coupon_set: couponData }))
      })
      console.log(arry)
      return arry
    },
    // 设置优惠券内容弹窗
    handleCouponSet (scope) {
      let target = this.coupon_info[scope.$index]
      this.coupon_set = JSON.parse(JSON.stringify(target.coupon_set))
      this.couponSetDialogFlag = true
      this.target = scope.$index
    },
    // 确认设置优惠券
    confrimCouponSet () {
      this.coupon_info[this.target].coupon_set = JSON.parse(JSON.stringify(this.coupon_set))
      this.couponSetDialogFlag = false
    },
    // 同id去重
    unique (arr, key) {
      let map = new Map()
      arr.forEach((item, index) => {
        if (!map.has(item[key])) {
          map.set(item[key], item)
        }
      })
      return [...map.values()]
    }
  }
}

</script>
<style lang="scss" scoped>
.inputTip {
  color: #999;
  margin-left: 15px;
}
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  padding-bottom: 50px;
  .main {
    background-color: #fff;
    padding: 10px 20px 10px 20px;
    .el-form-item {
      margin-bottom: 16px;
    }
    .el-input {
      width: 90px;
    }
    .morelength {
      width: 200px;
    }
    .chooseGoods {
      width: 120px;
      height: 30px;
      line-height: 30px;
      text-align: center;
      border: 1px solid #ccc;
    }
    .gray {
      color: #999;
    }
  }
}
.goodsImgWrap {
  width: 80px;
  height: 80px;
  border: 1px solid #ccc;
  margin: 5px 5px;
  position: relative;
}
.goodsImgWrap .deleteIcon {
  width: 17px;
  height: 17px;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  border-radius: 50%;
  line-height: 17px;
  text-align: center;
  position: absolute;
  top: -8px;
  right: -8px;
  cursor: pointer;
  opacity: 0.8;
}
.goodsImgWrap .moveIcon {
  width: 17px;
  height: 17px;
  display: none;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  line-height: 17px;
  text-align: center;
  position: absolute;
  bottom: 0px;
  cursor: pointer;
  opacity: 0.8;
}
.goodsImgWrap:hover .moveIcon {
  display: block;
}
.selectedWrap {
  min-width: 70px;
  height: 22px;
  border: 1px solid #ccc;
  line-height: 22px;
  text-align: center;
  padding: 0px 5px;
  margin: 0px 5px;
  background-color: #fff;
  position: relative;
}
.footer {
  padding: 10px 0px 10px 0px;
  text-align: center;
  background: #f8f8f8;
  margin-top: 10px;
  position: fixed;
  bottom: 0;
  z-index: 1;
  width: 100%;
}
</style>
