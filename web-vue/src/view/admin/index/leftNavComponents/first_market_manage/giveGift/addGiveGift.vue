<!--
* 我要送礼-- 添加/编辑页面
*
* @author 孔德成
-->
<template>
    <wrapper style="margin:10px 0px 0px 0px">
        <el-form label-width="100px"
                 ref="requestParams"
                 :model="requestParams"
                 :rules="fromRules">
            <el-row>
                <el-col :span="24">
                    <div class="grid-content-info " style="background-color: #F2F6FC">
                        {{this.$t('giveGift.addGiveGiftTips')}}
                        <el-link type="primary" @click="myCenterJump">{{this.$t('giveGift.MyCenter')}}</el-link>
                        {{this.$t('giveGift.addGiveGiftTips1')}}
                        <el-link type="primary" @click="myCenterJump">{{this.$t('giveGift.MyCenterJump')}}</el-link>
                    </div>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-form-item :label="$t('giveGift.activityName')" prop="actName">
                    <el-col :span="5">
                    <el-input
                            v-model="requestParams.actName"
                            :placeholder="$t('giveGift.activityName')"
                            size="small"
                            clearable
                    ></el-input>
                    </el-col>
                </el-form-item>
            </el-row>
            <el-row :gutter="20">
                <el-col :span="20">
                    <el-form-item :label="$t('giveGift.validDate')" prop="dueTimeType">
                        <el-radio-group v-model="requestParams.dueTimeType" >
                            <el-radio :label="0"  >{{this.$t('giveGift.fixedTime')}}</el-radio>
                            <el-date-picker
                                    :disabled="!add"
                                    v-if="requestParams.dueTimeType=== 0"
                                    v-model="validityDate"
                                    type="datetimerange"
                                    @change="dateChange(validityDate)"
                                    range-separator="~"
                                    :start-placeholder="$t('giveGift.startDate')"
                                    :end-placeholder="$t('giveGift.endDate')"
                                    align="left"
                                    size="small"
                                    value-format="yyyy-MM-dd HH:mm:ss">
                            </el-date-picker>
                            <br>
                            <el-radio  :label="1">{{this.$t('giveGift.everTime')}}</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                    <el-form-item :label="$t('giveGift.priority')" prop="level">
                        <el-col :span="5">
                        <el-input
                                v-model="requestParams.level"
                                :placeholder="$t('giveGift.priority')"
                                size="small"
                                clearable
                        ></el-input>
                        </el-col>
                        <el-col :span="13" style="color: #909399;font-size: small"><span>{{this.$t('giveGift.priorityTips')}}</span></el-col>
                    </el-form-item>

            </el-row>
            <el-row :gutter="24">
                <el-col :span="24">
                    <el-form-item :label="$t('giveGift.activityType')" prop="actTypeFirstServed">
                        <el-checkbox v-model="requestParams.actTypeFirstServed" :false-label="0" :true-label="1" :label="$t('giveGift.actTypeFirstServed')"></el-checkbox>
                        <el-checkbox v-model="requestParams.actTypeTimingOpen" :false-label="0" :true-label="1"   :label="$t('giveGift.actTypeTimingOpen')"></el-checkbox>
                        <el-checkbox v-model="requestParams.actTypeDirectGiving" :false-label="0" :true-label="1"  :label="$t('giveGift.actTypeDirectGiving')"></el-checkbox>
                        <span style="color: #909399;font-size: small">
                            <br>
                            {{this.$t('giveGift.actTypeTips1')}}
                            <br>
                            {{this.$t('giveGift.actTypeTips2')}}
                            <br>
                            {{this.$t('giveGift.actTypeTips3')}}
                        </span>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="24">
                <el-col :span="24">
                    <el-form-item :label="$t('giveGift.customService')">
                        <span style="color: #909399;font-size: small">
                            {{this.$t('giveGift.customServiceTips1')}}
                            <br>
                            {{this.$t('giveGift.customServiceTips2')}}
                            <el-link type="primary"  href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=685&extra=page%3D1%26filter%3Dsortid%26sortid%3D17">
                                {{this.$t('giveGift.tutorial')}}
                            </el-link>
                        </span>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-col :span="20">

                    <el-form-item :label="$t('giveGift.commodity')" prop="recommendGoodsId">
                        <el-button
                                size="small"
                                @click="showChoosingGoods()"
                        >{{$t('giveGift.choosingGoods')}}</el-button>
                        <el-input
                                v-model="requestParams.recommendGoodsId"
                                v-if="false"
                        ></el-input>
                        <span v-if="goodsNum!== null"    @click="onlyShowChoosingGoods()"
                              style="color: #e4393c">{{this.$t('giveGift.choosingGoodsTips',[this.goodsNum])}}</span>
                        <span style="color: #909399;font-size: small">
                            <ul>
                                {{this.$t('marketCommon.note')}}:
                                <li>{{this.$t('giveGift.commodityTips1')}}}</li>
                                <li>{{this.$t('giveGift.commodityTips2')}}}</li>
                            </ul>
                        </span>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-col :offset="7" :span="5">
                    <el-button
                            type="primary"
                            style="width: 100%"
                            :disabled="submitStatus"
                            @click="submitForm()"
                    >{{$t('giveGift.save')}}</el-button>
                </el-col>
            </el-row>
        </el-form>
        <!--添加商品弹窗-->
        <choosingGoods
                @resultGoodsIds="choosingGoodsResult"
                :tuneUpChooseGoods='isShowChoosingGoodsDialog'
                :chooseGoodsBack="goodsIdArr"
                :onlyShowChooseGoods="isOnlyShowChooseGoods"/>
    </wrapper>
</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import choosingGoods from '@/components/admin/choosingGoods'
import {mapActions} from 'vuex'

import {addGiveGift, getGiveGiftById, updateGiveGift} from '@/api/admin/marketManage/giveGift'

export default {
  components: {
    wrapper,
    choosingGoods
  },
  data: function () {
    // 校验有效期
    var checkVailDate = (rule, value, callback) => {
      console.log('checkVailDate', value, this.validityDate)
      if (value === 0) {
        // 固定时间必须选时间
        if (this.validityDate === undefined || this.validityDate === null || this.validityDate.length === 0) {
          callback(new Error(this.$t('giveGift.requiredValidDate')))
        } else {
          callback()
        }
      } else if (value === 1) {
        callback()
      } else {
        return callback(new Error(this.$t('giveGift.requiredValidDate')))
      }
    }
    // 校验活动玩法
    var activityType = (rule, value, callback) => {
      console.log('activityType', this.requestParams.actTypeFirstServed, this.requestParams.actTypeTimingOpen, this.requestParams.actTypeDirectGiving)
      if (this.requestParams.actTypeFirstServed || this.requestParams.actTypeTimingOpen || this.requestParams.actTypeDirectGiving) {
        callback()
      } else {
        return callback(new Error(this.$t('giveGift.activityTypeVaild')))
      }
    }
    return {
      requestParams: {
        id: null,
        currentPage: 1,
        actName: null,
        dueTimeType: 0,
        startTime: null,
        endTime: null,
        level: 0,
        actTypeFirstServed: false,
        actTypeTimingOpen: false,
        actTypeDirectGiving: false,
        recommendGoodsId: null
      },
      // 校验表单
      fromRules: {
        actName: [
          {required: true, message: this.$t('groupBuy.activityNameRequiredRules'), trigger: 'change'},
          {max: 20, message: this.$t('groupBuy.lengthMax20'), trigger: 'change'}
        ],
        dueTimeType: [
          {required: true, message: this.$t('giveGift.required'), trigger: 'change'},
          {validator: checkVailDate, trigger: 'change'}
        ],
        level: [
          {required: true, message: this.$t('giveGift.required'), trigger: 'change'}

        ],
        actTypeFirstServed: [
          {required: true, message: this.$t('giveGift.required'), trigger: 'change'},
          {validator: activityType, trigger: 'change'}
        ],
        recommendGoodsId: [
          {required: true, message: this.$t('giveGift.required'), trigger: 'change'}
        ]
      },
      validityDate: [],
      add: true,
      submitStatus: false,
      isShowChoosingGoodsDialog: false,
      isOnlyShowChooseGoods: false,
      goodsNum: null,
      goodsIdArr: []
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    if (this.$route.query.id !== undefined && this.$route.query.id !== null) {
      this.requestParams.id = this.$route.query.id
      this.add = false
    }
    console.log('addGiveGift', 'this.$route', this.$route)
    console.log('addGiveGift', 'this.$route.query.id ', this.$route.query.id)
    this.initData()
  },
  watch: {
    lang () {
      this.initData()
    }
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    //  初始化页面
    initData () {
      if (!this.add) {
        this.initEditData()
      }
    },
    // 提交数据
    submitForm (data) {
      this.submitStatus = true
      this.$refs['requestParams'].validate(valid => {
        this.submitStatus = false
        if (valid) {
          if (this.add) {
            addGiveGift(this.requestParams).then(res => {
              this.$emit('submitFormJump')
            })
          } else {
            updateGiveGift(this.requestParams).then(res => {
              this.$emit('submitFormJump')
            })
          }
        } else {
          console.log('error submit!!', this.submitStatus)
          return false
        }
      })
      this.submitStatus = false
    },
    // 跳转个人中心
    myCenterJump () {
      // 送礼明细
      console.log('跳转个人中心页面 id = ', this.requestParams.id)
      this.$router.push({
        name: 'giveGift_details',
        params: {
          id: this.requestParams.id
        }
      })
    },
    // 活动时间时间选择
    dateChange (date) {
      console.log(date)
      if (date !== null) {
        this.requestParams.startTime = date[0]
        this.requestParams.endTime = date[1]
      }
    },
    // 初始化编辑页面数据
    initEditData () {
      console.log('initEditData')
      getGiveGiftById({id: this.requestParams.id}).then(res => {
        if (res.error === 0) {
          this.$message.success(res.message)
          this.requestParams = res.content
          this.validityDate = [res.content.startTime, res.content.endTime]
          if (this.requestParams.recommendGoodsId !== null) {
            this.goodsNum = res.content.recommendGoodsId.split(',').length
          }
        } else {
          this.$message.warning(res.error, res.message)
        }
        this.loading = false
      }).catch(e => {
        this.$message.warning(e.toString())
        this.loading = false
      })
    },
    // 初始化商品弹窗
    showChoosingGoods () {
      console.log('初始化商品弹窗', this.requestParams.recommendGoodsId)
      // this.transmitEditGoodsId(goodsIdArr)
      this.isOnlyShowChooseGoods = false
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    // 初始化商品弹窗部分商品
    onlyShowChoosingGoods () {
      console.log('初始化商品弹窗部分商品', this.requestParams.recommendGoodsId)
      // this.transmitEditGoodsId(goodsIdArr)
      this.isOnlyShowChooseGoods = true
      this.isShowChoosingGoodsDialog = !this.isShowChoosingGoodsDialog
    },
    // 接收商品弹窗放回数据
    choosingGoodsResult (row) {
      console.log('接收商品弹窗放回数据', row)
      console.log('接收商品弹窗放回数据', row.toString())
      this.requestParams.recommendGoodsId = row.toString()
      this.goodsIdArr = row
      this.goodsNum = row.length
    }
  }
}
</script>

<style scoped>
    .el-row {
        margin-bottom: 20px;
    }
    .el-col {
        border-radius: 4px;
    }
    .bg-purple-dark {
        background: #99a9bf;
    }
    .bg-purple {
        background: #d3dce6;
    }
    .bg-purple-light {
        background: #e5e9f2;
    }
    .grid-content {
        border-radius: 4px;
        min-height: 36px;
    }
    .grid-content-info{
        border-radius: 4px;
        min-height: 36px;
        padding-top: 10px;
        padding-left: 10px;
    }
    .row-bg {
        padding: 10px 0;
        background-color: #f9fafc;
    }
</style>
