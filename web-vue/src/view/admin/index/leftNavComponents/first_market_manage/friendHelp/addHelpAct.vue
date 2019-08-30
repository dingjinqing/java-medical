/* eslint-disable no-undef */
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
            prop="rewardType"
          >
            <el-radio
              v-model="form.rewardType"
              label="0"
            >
              赠送商品
            </el-radio>
            <el-radio
              v-model="form.rewardType"
              label="1"
            >折扣商品</el-radio>
            <el-radio
              v-model="form.rewardType"
              label="2"
            >赠送优惠券</el-radio>
            <el-col>
              <el-button
                size="small"
                type="primary"
                @click="showChoosingGoods"
              >+选择商品</el-button>
            </el-col>
            <div></div>
          </el-form-item>
          <el-form-item
            label="奖励设置："
            prop=""
          >

            <el-table
              class="version-manage-table"
              header-row-class-name="tableClass"
              :data="goodsInfo"
              border
              style="width: 38%"
            >
              <el-table-column
                width="200px"
                prop="goodsName"
                label="商品信息"
                align="center"
              >
              </el-table-column>

              <el-table-column
                width="125px"
                prop=""
                label="商品价格"
                align="center"
              >
              </el-table-column>

              <el-table-column
                width="125px"
                prop=""
                label="商品库存"
                align="center"
              >
              </el-table-column>

              <el-table-column
                width="125px"
                prop=""
                label="活动库存"
                align="center"
              >
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
          </el-form-item>
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
                      </div>
                      <!-- <div style="margin-left: 60px">
                      <img
                        src=""
                        alt=""
                      >
                    </div> -->
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
  </wrapper>
</template>

<script>
import { mapActions } from 'vuex'
import wrapper from '@/components/admin/wrapper/wrapper'
import choosingGoods from '@/components/admin/choosingGoods'
import { addActive } from '@/api/admin/marketManage/friendHelp.js'
import { getGoodsProductList } from '@/api/admin/brandManagement.js'

export default {
  components: {
    wrapper,
    choosingGoods
  },
  data () {
    return {
      // 表单
      form: {
        actName: '',
        rewardType: '0',
        rewardContent: {
          goodsIds: 0,
          rewardIds: '',
          marketPrice: '',
          marketStore: ''
        },
        startTime: '',
        endTime: '',
        ruleForm: {},
        rewardDuration: '',
        rewardDurationUnit: [{
          value: '0',
          label: '小时'
        }, {
          value: '1',
          label: '天'
        }, {
          value: '2',
          label: '周'
        }],
        rewardDurationUnitSelect: '',
        promoteType: '0',
        promoteAmount: '',
        promoteTimes: '',
        launchLimitDuration: '',
        launchLimitUnit: [{
          value: '0',
          label: '天'
        }, {
          value: '1',
          label: '周'
        }, {
          value: '2',
          label: '月'
        }, {
          value: '3',
          label: '年'
        }],
        launchLimitUnitSelect: '',
        launchLimitTimes: '',
        shareCreateTimes: '',
        promoteCondition: '0',
        failedSendType: '0',
        activityShareType: '0',
        customShareWord: '',
        shareImgType: '0'
      },
      // 选中商品id
      goodsIds: '',
      goodsName: '',
      goodsArray: null,
      goodsInfo: null,
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
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    addAct () {
      let addParam = {
        'actName': this.form.actName,
        'startTime': this.form.startTime,
        'endTime': this.form.endTime,
        'rewardType': this.form.rewardType,
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
        'failedSendType': this.form.failedSendType,
        'activityShareType': this.form.activityShareType,
        'customShareWord': this.form.customShareWord,
        'shareImgType': this.form.shareImgType
      }
      this.$refs['form'].validate((valid) => {
        console.log('submit', this.form)
        if (valid) {
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
        } else {
          this.$message.error('数据不合法')
          return false
        }
      })
    },

    // 选择商品弹窗
    showChoosingGoods () {
      this.transmitEditGoodsId(this.form.rewardContent.goodsIds)
      // console.log('初始化商品弹窗', this.form.rewardContent.goodsIds)
      this.$http.$emit('choosingGoodsFlag', true, 'choiseOne')
    },

    nextStep () {
      if (!this.validateParam()) {
        return
      }
      this.step++
      this.transmitEditGoodsId(this.form.rewardContent.goodsIds)
    },
    lastStep () {
      this.step--
      this.transmitEditGoodsId(this.form.rewardContent.goodsIds)
    },
    //  获取商品ids
    choosingGoodsResult (row) {
      console.log('获取商品行', row)
      this.goodsRow = row
      this.form.rewardContent.goodsIds = row.goodsId
      // console.log('goodsRow', this.goodsRow)
      // console.log('tmpGoodsIds', this.form.rewardContent.goodsIds)
      // 初始化规格表格
      let obj = {
        goodsId: this.form.rewardContent.goodsIds,
        currentPage: 1,
        pageRows: 1024
      }
      getGoodsProductList(obj).then(res => {
        console.log('res', res)
        this.goodsArray = res.content.dataList
        // console.log('goodsInfo!', this.goodsInfo[obj.goodsId].goodsId)
        // this.goodsInfo = this.goodsArray[obj.goodsId].goodsName
        const { content } = res
        console.log(content)
        const { dataList } = content
        console.log(dataList)
        // this.goodsInfo = dataList[obj.goodsId].goodsName
        console.log('name', this.goodsInfo)
        // this.goodsIds = this.goodsInfo[obj.goodsId].goodsId
        // this.goodsName = this.goodsInfo[obj.goodsId].goodsName
      })
    }
  }
}

</script>
<style lang="scss" scoped>
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
