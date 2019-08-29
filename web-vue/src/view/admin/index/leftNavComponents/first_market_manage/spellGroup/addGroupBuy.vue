<!--
添加拼团活动
@author 孔德成
-->
<template>
    <wrapper>
        <el-form ref="form" :model="form" :rules="fromRules" label-width="150px" :label-position="'right'">
            <el-form-item label="拼团活动" prop="resource">
                <el-radio-group v-model="form.activityType">
                    <el-radio label="普通拼团"></el-radio>
                    <el-radio label="老带新拼团"></el-radio>
                </el-radio-group>
                <div class="prompt">
                    开关默认关闭，开启开关，则用户可以申请为店铺分销员，分销员邀请用户注册产生订单，购买者邀请人可获得佣金奖励。关闭开关，手机端个人中心”分销中心“菜单隐藏，用户下单，邀请人不再产生佣金奖励，系统分销机制关闭，邀请不再记录邀请关系。
                </div>
            </el-form-item>
            <el-form-item label="活动名称" prop="name">
                <el-col :span="8">
                    <el-input v-model="form.name"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="活动商品" prop="goodsId">
                <el-button size="small" @click="showChoosingGoods">选择商品</el-button>
                <el-col :span="8">
                    <el-input :disabled="true"
                              v-model="goodsRow.goodsName"
                              v-if="goodsRow.ischecked"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="团长优惠">
                <el-switch v-model="form.isGrouperCheap"></el-switch>
                <div class="prompt">开启团长(开团人)优惠后，团长将享受更优惠价格，有助于提高开团率和成团率。
                    <p>
                        <span style="color: red;">注：</span>
                        默认成团的团长也能享受团长优惠，为避免不必要的损失，请谨慎设置
                    </p>
                </div>
            </el-form-item>
            <el-form-item label="优惠规则设置">
                <el-table
                        border
                        style="width: 100%"
                >
                    <el-table-column
                            prop=""

                            label="商品名称,规格"
                    >
                        <template slot-scope="">
                            <el-input placeholder="请输入内容"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop=""
                            label="原价（元）"
                    >
                    </el-table-column>
                    <el-table-column
                            prop=""
                            label="拼团价（元）"
                    >
                    </el-table-column>
                    <el-table-column v-if="form.isGrouperCheap"
                                     prop=""
                                     label="团长优惠价（元）"
                    >
                    </el-table-column>
                    <el-table-column
                            prop=""
                            label="原库存"
                    >
                    </el-table-column>
                    <el-table-column
                            prop=""
                            label="拼团库存"
                    >
                    </el-table-column>
                </el-table>
                <div>
                    <el-button @click="setCurrent(1)">批量设置拼团价</el-button>
                    <el-button @click="setCurrent(2)">批量设置拼团库存</el-button>
                    <el-button @click="setCurrent(3)">批量设置团长优惠价（元）</el-button>
                </div>
            </el-form-item>

            <el-form-item label="有效期">
                <el-date-picker
                        v-model="validityDate"
                        type="datetimerange"
                        @change="dateChange(validityDate)"
                        :picker-options="pickerOptions"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        align="right"
                        value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
            </el-form-item>
            <el-form-item label="成团人数">
                <el-input-number
                        v-model="form.limitAmount"
                        controls-position="right"
                        :min="2"
                ></el-input-number>
                <div class="prompt">不可小于2人,保存后不可编辑</div>
            </el-form-item>
            <el-form-item label="下单商品数量">
            <div class="prompt">  单次下单购买拼团商品数量最小 </div>
            <el-input-number
                    v-model="form.limitBuyMinNum"
                    controls-position="right"
                    :min="0"
            ></el-input-number>
            <div class="prompt">件 请填写正整数，不填或为0表示不限制数量 </div>
        </el-form-item>
            <el-form-item  >
                <div class="prompt">  单次下单购买拼团商品数量最大 </div>
                <el-input-number
                        v-model="form.limitBuyMaxNum"
                        controls-position="right"
                        :min="0"
                ></el-input-number>
                <div class="prompt">件 请填写正整数，不填或为0表示不限制数量 </div>
            </el-form-item>
            <el-form-item label="参团限制">
                <div class="prompt">   每人最多参加   </div>
                <el-input-number
                        v-model="form.joinLimit"
                        controls-position="right"
                        :min="0"
                ></el-input-number>
                <div class="prompt">次新团 默认为0，0表示不限制数量。仅限制参与其他用户所开的团的数量 </div>
            </el-form-item>
            <el-form-item label="开团限制">
                <div class="prompt"> 每人最多开启 </div>
                <el-input-number
                        v-model="form.openLimit"
                        controls-position="right"
                        :min="0"
                ></el-input-number>
                <div class="prompt">次新团 默认为0，0表示不限制数量。仅限制同一用户的开团数量  </div>
            </el-form-item>
            <el-form-item  label="默认成团">
            <el-switch v-model="form.isDefault"></el-switch>
                <div class="prompt">开启默认成团后，24小时内人数未满的团，系统将会模拟“匿名买家”凑满人数，使该团成团。 你只需要对已付款参团的真实买家发货。建议合理开启，以提高成团率 </div>
            </el-form-item>
            <el-form-item label="运费设置">
                <el-radio-group v-model="form.freeFreight">
                    <el-radio label="免运费 "></el-radio>
                    <el-radio label="使用原商品运费模板"></el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="鼓励奖">

            </el-form-item>
            <el-form-item label="活动分享">
                <el-radio-group v-model="form.share">
                    <el-radio label="默认 "></el-radio>
                    <el-radio label="自定义样式"></el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="submitForm('form')">立即创建</el-button>
            </el-form-item>
        </el-form>
        <choosingGoods
                @resultGoodsRow="choosingGoodsResult"
        />
    </wrapper>
</template>
<script>

import {mapActions} from 'vuex'
import wrapper from '@/components/admin/wrapper/wrapper'
import choosingGoods from '@/components/admin/choosingGoods'

export default {
  components: {
    wrapper,
    choosingGoods
  },
  data () {
    return {
      // from 表单数据
      form: {
        id: null,
        name: '',
        goodsId: '',
        limitAmount: '',
        joinLimit: '',
        openLimit: '',
        isDefault: '',
        startTime: '',
        endTime: '',
        stock: '',
        freeFreight: '',
        activityType: '',
        isGrouperCheap: '',
        rewardCouponId: '',
        limitBuyMaxNum: '',
        limitBuyMinNum: '',
        share: {},
        product: [{}]
      },
      // 校验表单
      fromRules: {
        name: [
          {required: true, message: '请输入活动名称', trigger: 'blur'},
          {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ]
      },
      // 选中商品id
      goodsRow: {},
      goodsIds: [],
      tmpGoodsIds: '',
      goodsName: '',
      // 规格表数据
      productTableData: {},
      // 时间控件
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      validityDate: ''
    }
  },
  mounted () {

  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    // 提交表单
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!')
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 选择商品弹窗
    showChoosingGoods () {
      this.transmitEditGoodsId(this.tmpGoodsIds)
      console.log('初始化商品弹窗', this.tmpGoodsIds)
      this.$http.$emit('choosingGoodsFlag', true, 'choiseOne')
    },

    nextStep () {
      if (!this.validateParam()) {
        return
      }
      this.step++
      this.transmitEditGoodsId(this.tmpGoodsIds)
    },
    lastStep () {
      this.step--
      this.transmitEditGoodsId(this.tmpGoodsIds)
    },
    // 获取商品ids
    choosingGoodsResult (row) {
      console.log('获取商品行', row)
      this.goodsRow = row
      this.tmpGoodsIds = row.goodsId
      console.log('goodsRow', this.goodsRow)
      console.log('tmpGoodsIds', this.tmpGoodsIds)
    },
    // 活动时间时间选择
    dateChange (date) {
      console.log(date)
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
</style>
