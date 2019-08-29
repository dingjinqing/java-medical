<!--
* 创建赠品活动
*
* @author 郑保乐
-->
<template>
  <div>
    <wrapper>
        <el-steps :active="step"  align-center finish-status="finish" style="margin-bottom:20px">
            <el-step v-for="(item, index) in steps" :key="index" :title="`${index+1}. ${item}`" ></el-step>
        </el-steps>
        <!-- 设置赠品规则 -->
        <div v-if="step===1">
        <el-row style="margin-bottom:20px">
          <el-col :span="2">
            <span class="label">基础配置</span>
          </el-col>
          <el-col :span="11">
            <el-form label-width="100px" >
              <el-form-item label="活动名称">
                <el-input v-model="param.name"></el-input>
              </el-form-item>
              <el-form-item label="活动优先级">
                <el-input type="number" v-model="param.level" :disabled="ongoing"></el-input>
              </el-form-item>
              <el-form-item label="活动时间">
                <el-date-picker
                  v-model="dateRange"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :disabled="ongoing">
                </el-date-picker>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="10" class="description">
            <el-form label-width="20px" >
              <el-form-item>
                <template>
                  只作为商家记录使用，用户不会看到这个名称
                </template>
              </el-form-item>
              <el-form-item>
                <template>
                  用于区分不同赠品活动的优先级，请填写正整数，数值越大优先级越高
                </template>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <el-row style="margin-bottom:20px">
          <el-col :span="2">
            <span class="label">赠品策略</span>
          </el-col>
          <el-col :span="11">
            <el-form label-width="100px" >
              <el-form-item label="活动商品">
                <template>
                  <el-radio v-for="(item, index) in goodsRanges" :key="index"
                    v-model="goodsRange" :label="index" :disabled="ongoing">{{item}}</el-radio>
                  <el-button v-show="goodsRange===1" size="small"
                    @click="showChoosingGoods">{{goodsBtnName}}</el-button>
                  <span v-show="goodsRange===1">已选{{goodsIdsLength}}</span>
                </template>
              </el-form-item>
              <el-form-item label="赠品规则">
                <el-select v-model="selectedRules" multiple :multiple-limit="3" :disabled="ongoing">
                  <el-option
                    v-for="(item, index) in rules"
                    :disabled="ongoing"
                    :key="index"
                    :label="item.label"
                    :value="index">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="满金额赠送" v-show="contains(0)">
                  <span>满</span>
                  <el-input type="number" v-model="param.rules.fullPrice" class="input" :disabled="ongoing"></el-input>
                  <span>元，送赠品</span>
              </el-form-item>
              <el-form-item label="满数量赠送" v-show="contains(1)">
                  <span>满</span>
                  <el-input type="number" v-model="param.rules.fullNumber" class="input" :disabled="ongoing"></el-input>
                  <span>件，送赠品</span>
              </el-form-item>
              <el-form-item label="会员标签" v-show="contains(2)">
                  <el-select v-model="param.rules.tagId" multiple :disabled="ongoing">
                    <el-option
                      v-for="item in tags"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
              </el-form-item>
              <el-form-item label="会员卡" v-show="contains(3)">
                  <el-select v-model="param.rules.cardId" multiple :disabled="ongoing">
                    <el-option
                      v-for="item in cards"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
              </el-form-item>
              <el-form-item label="付款排名" v-show="contains(4)">
                  <el-input type="number" v-model="param.rules.payTop" class="input" :disabled="ongoing"></el-input>
              </el-form-item>
              <el-form-item label="已购买次数" v-show="contains(5)">
                  <el-input type="number" v-model="param.rules.minPayNum" class="input" :disabled="ongoing"></el-input>
                  <span>至</span>
                  <el-input type="number" v-model="param.rules.maxPayNum" class="input" :disabled="ongoing"></el-input>
              </el-form-item>
              <el-form-item label="付款时间" v-show="contains(6)">
                <el-date-picker
                  v-model="payDateRange"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :disabled="ongoing">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="用户类别" v-show="contains(7)">
                  <el-select v-model="param.rules.userAction" :disabled="ongoing">
                    <el-option
                      v-for="item in userAction"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
              </el-form-item>
              <el-form-item label="赠品规则说明">
                <el-input
                  type="textarea"
                  :rows="5"
                  v-model="param.explain"
                  placeholder="此提示将在小程序前端展示，请根据配置的赠品策略谨慎编写赠品规则说明，最多可填写200字。
  例：前100名付款用户可获得赠品，送完即止。">
                </el-input>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="10" class="description">
            <el-form label-width="20px" >
              <el-form-item>
                <template>
                  以下条件满足其一即可获得赠品，最多可选择 3 类
                </template>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        </div>
        <!-- 设置赠品 -->
        <div v-if="step===2">
          <el-row v-show="!ongoing">
            <el-col>
              <el-button size="small" type="primary" @click="showChoosingGoods">添加赠品商品</el-button>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-table
                class="version-manage-table"
                header-row-class-name="tableHeader"
                :data="tableData"
                border
                style="width: 100%"
              >
                <el-table-column
                  prop="goodsName"
                  label="商品名称"
                  align="center"
                >
                  <template slot-scope="scope">
                    <div class="name_cell">
                      <img :src="scope.row.goodsImg" class="goods_img">
                      <div>{{scope.row.goodsName}}</div>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="prdDesc"
                  label="规格"
                  align="center"
                > </el-table-column>
                <el-table-column
                  prop="prdPrice"
                  label="商品原价"
                  align="center"
                > </el-table-column>
                <el-table-column
                  prop="prdNumber"
                  label="商品库存"
                  align="center"
                > </el-table-column>
                <el-table-column
                  prop="productNumber"
                  label="赠品库存 (当前库存/初始库存)"
                  align="center"
                >
                  <template slot-scope="scope">
                    <inputEdit v-model="scope.row.productNumber"
                      :disabled="ongoing"
                      :init="Number(scope.row.offerNumber||0)+Number(scope.row.productNumber)"
                      @update="checkProductNumber(scope.row.prdNumber, scope.row.productNumber, scope.row.offerNumber)">
                      <div slot="before">
                        {{scope.row.productNumber - scope.row.offerNumber}} /
                      </div>
                    </inputEdit>
                  </template>
                </el-table-column>
                <el-table-column
                  label = "操作"
                  align="center"
                  v-if="!ongoing"
                >
                  <template slot-scope="scope">
                    <el-button size="small"
                    v-show="!ongoing"
                    @click="tableData.splice(tableData.findIndex(r=>r.productId===scope.row.productId),1)">
                    删除</el-button>
                  </template>
                </el-table-column>
                </el-table>
            </el-col>
          </el-row>
        </div>
        <div style="margin-top:20px">
          <el-row>
            <el-col :offset="stepButtonOffset">
              <el-button type="primary" @click="lastStep" v-show="step > 1">上一步</el-button>
              <el-button type="primary" @click="nextStep" v-show="step < steps.length">下一步</el-button>
              <el-button type="primary" @click="addGift" v-show="step === steps.length">保存</el-button>
            </el-col>
          </el-row>
        </div>
        <div v-if="1===step">
          <choosingGoods/>
        </div>
        <div v-if="2===step">
          <choosingGoods :loadProduct="true"/>
        </div>
    </wrapper>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import wrapper from '@/components/admin/wrapper/wrapper'
import inputEdit from '@/components/admin/inputEdit'
import choosingGoods from '@/components/admin/choosingGoods'
import status from '@/components/admin/status/status'
import { format, range } from '@/util/date'
import { addGift, getGiftDetail, updateGift, getMemberCardList, getTagList, getProductDetail } from '@/api/admin/marketManage/gift'

export default {
  components: {
    wrapper,
    inputEdit,
    choosingGoods
  },
  data () {
    return {
      id: null,
      step: 1,
      steps: ['设置活动规则', '设置赠品'],
      // 活动时间范围
      dateRange: [],
      // 支付时间范围
      payDateRange: [],
      tags: [],
      cards: [],
      // 0：全部商品，1：指定商品
      goodsRange: 0,
      goodsRanges: ['全部商品', '指定商品'],
      // 当前页为编辑页
      update: false,
      // 活动状态,
      status: null,
      param: {
        name: '',
        level: '',
        startTime: '',
        endTime: '',
        goodsIds: [],
        explain: '',
        rules: {
          fullPrice: null,
          fullNumber: null,
          tagId: null,
          userAction: null,
          payTop: null,
          minPayNum: 1,
          maxPayNum: 10,
          cardId: [],
          payStartTime: null,
          payEndTime: null
        },
        gifts: []
      },
      userAction: [{
        id: 1,
        name: '新用户'
      }, {
        id: 2,
        name: '老用户'
      }],
      // 系统中的全部赠品规则
      rules: [
        {
          label: '满金额赠送',
          keys: ['fullPrice']
        },
        {
          label: '满件数赠送',
          keys: ['fullNumber']
        },
        {
          label: '会员标签',
          keys: ['tagId']
        },
        {
          label: '会员卡',
          keys: ['cardId']
        },
        {
          label: '付款排名',
          keys: ['payTop']
        },
        {
          label: '已购买次数',
          keys: ['minPayNum', 'maxPayNum']
        },
        {
          label: '付款时间',
          keys: ['payStartTime', 'payEndTime']
        }, {
          label: '用户类别',
          keys: ['userAction']
        }
      ],
      // 当前已选规则序号
      selectedRules: [],
      // 赠品表格
      tableData: [],
      // 选中商品id
      tmpGoodsIds: [],
      // 选中赠品商品id
      tmpGiftGoodsIds: []
    }
  },
  computed: {
    goodsIdsLength () {
      return this.tmpGoodsIds.length
    },
    stepButtonOffset () {
      if (this.step === 2) {
        return 0
      }
      return 4
    },
    ongoing () {
      return this.status === status[1].status
    },
    goodsBtnName () {
      if (this.ongoing) {
        return '查看商品'
      }
      return '添加商品'
    }
  },
  methods: {
    ...mapActions(['transmitEditGoodsId']),
    nextStep () {
      this.formatParam()
      if (!this.validateParam()) {
        return
      }
      this.step++
      this.transmitEditGoodsId(this.tmpGiftGoodsIds)
    },
    lastStep () {
      this.step--
      this.transmitEditGoodsId(this.tmpGoodsIds)
    },
    // 保存
    addGift () {
      const then = r => this.gotoGifts()
      const { param } = this
      this.formatParam()
      if (!this.validateGiftParam()) {
        return
      }
      if (this.update) {
        updateGift(param).then(then)
      } else {
        addGift(param).then(then)
      }
    },
    formatParam () {
      this.formatTime()
      this.formatRules()
      this.formatGoods()
    },
    // 格式化入参时间
    formatTime () {
      const { dateRange, payDateRange } = this
      let startTime = range(dateRange).v1
      let endTime = range(dateRange).v2
      this.param.startTime = format(startTime)
      this.param.endTime = format(endTime)
      startTime = range(payDateRange).v1
      endTime = range(payDateRange).v2
      this.param.rules.payStartTime = format(startTime)
      this.param.rules.payEndTime = format(endTime)
    },
    // 处理商品规则
    formatRules () {
      const { selectedRules } = this
      // 将 param.rules 中，不在已选规则序号中的属性赋值 null
      Object.keys(this.param.rules).forEach(key => {
        const index = this.rules.findIndex(rule => undefined !== rule.keys.find(k => k === key))
        if (undefined === selectedRules.find(rule => rule === index)) {
          this.param.rules[key] = null
        }
      })
    },
    // 处理活动商品和赠品
    formatGoods () {
      // 活动商品
      this.param.goodsIds = this.tmpGoodsIds
      // 赠品
      const gifts = this.tableData
        .map(({ productId, productNumber }) => ({ productId, productNumber }))
      this.param.gifts = gifts
    },
    // 回显数据加载
    loadData () {
      const { id } = this.$route.params
      getGiftDetail(id).then(({ content }) => {
        this.param = content
        this.loadTime(content)
        this.loadRules(content)
        this.loadGoods(content)
        this.loadGifts(content)
        this.loadStatus(content)
      })
    },
    // 加载赠品规则
    loadRules (content) {
      const { rules } = content
      const { payStartTime, payEndTime } = rules
      if (payStartTime && payEndTime) {
        this.payDateRange.push(payStartTime, payEndTime)
      }
      this.param.rules.payStartTime = payStartTime || null
      this.param.rules.payEndTime = payEndTime || null
      // 获取接口返回结果的赠品规则中，属性值不为 null 的规则对应的序号
      Object.keys(rules).forEach(key => {
        if (rules[key] !== null) {
          const index = this.rules.findIndex(rule => undefined !== rule.keys.find(k => k === key))
          if (this.selectedRules.findIndex(rule => rule === index) === -1) {
            this.selectedRules.push(index)
          }
        }
      })
    },
    // 加载活动商品
    loadGoods (content) {
      let { goodsIds } = content
      if (!goodsIds || goodsIds.length === 0) {
        // 全部商品
        this.goodsRange = 0
      } else {
        // 指定商品
        this.goodsRange = 1
        this.tmpGoodsIds = goodsIds
        this.transmitEditGoodsId(goodsIds)
      }
    },
    loadTag () {
      getTagList().then(r => {
        const tags = r.content
        this.tags = tags
      })
    },
    loadMemberCard () {
      getMemberCardList().then(r => {
        const cards = r.content
        this.cards = cards
      })
    },
    loadTime (content) {
      const { startTime, endTime } = content
      this.dateRange.push(startTime)
      this.dateRange.push(endTime)
    },
    loadGifts ({ gifts }) {
      gifts.forEach(row => {
        const { productId, productNumber } = row
        getProductDetail(this.id, productId).then(({ content }) => {
          this.tableData.push({
            ...row,
            productNumber
          })
        })
      })
    },
    loadStatus ({ status }) {
      this.status = status
    },
    gotoGifts () {
      this.$router.replace('/admin/home/main/gift')
    },
    // 已选赠品规则中是否包含某个规则序号
    contains (ruleIndex) {
      return this.selectedRules.find(i => i === ruleIndex) !== undefined
    },
    // 查找规则
    findRule (key) {
      return this.rules.find(rule => rule.keys.findIndex(k => k === key) !== -1)
    },
    // 选择商品弹窗
    showChoosingGoods () {
      this.$http.$emit('choosingGoodsFlag', true)
      switch (this.step) {
        case 1:
          this.transmitEditGoodsId(this.tmpGoodsIds)
          break
        case 2:
          this.transmitEditGoodsId(this.tmpGiftGoodsIds)
          break
      }
    },
    // 添加一行赠品商品
    addProductRow (productId) {
      getProductDetail(this.id, productId).then(({ content }) => {
        const { goodsImg, prdImg, offerNumber } = content
        const row = {
          ...content,
          goodsImg: prdImg || goodsImg
        }
        this.tableData.splice(this.tableData.findIndex(row => row.productId === productId), 1)
        this.tableData.push({
          ...row,
          productNumber: row.prdNumber + offerNumber
        })
      })
    },
    // 参数校验
    validateParam () {
      const { param: { name, level, explain }, dateRange, goodsRange, selectedRules } = this
      const { tmpGoodsIds } = this
      if (!name) {
        this.fail('请输入活动名称')
        return false
      }
      if (!level) {
        this.fail('请输入活动优先级')
        return false
      }
      if (!dateRange || dateRange.length < 2) {
        this.fail('请选择活动时间')
        return false
      }
      if (goodsRange === 1 && (!tmpGoodsIds || tmpGoodsIds.length === 0)) {
        this.fail('请选择活动商品')
        return false
      }
      if (selectedRules.length < 1) {
        this.fail('请选择赠品规则')
        return false
      }
      if (!explain) {
        this.fail('请输入规则说明')
        return false
      }
      let result = true
      selectedRules.forEach(index => {
        const { label, keys } = this.rules[index]
        keys.forEach(key => {
          const value = this.param.rules[key]
          if (!value || (typeof value === 'object' && value.length < 1)) {
            this.fail(`请输入${label}`)
            result = false
          }
        })
      })
      return result
    },
    // 校验赠品参数
    validateGiftParam () {
      let result = true
      const { tableData } = this
      if (tableData.length < 1) {
        this.fail('请选择赠品')
        return false
      }
      this.tableData.forEach(row => {
        const { prdNumber, productNumber, offerNumber } = row
        if (!this.checkProductNumber(prdNumber, productNumber, offerNumber)) {
          result = false
        }
      })
      return result
    },
    fail (message) {
      this.$message({
        showClose: true,
        message,
        type: 'warning'
      })
    },
    handleChoosingGoods (ids) {
      if (this.ongoing) {
        return
      }
      this.tmpGoodsIds = ids
    },
    handleChoosingProduct (ids) {
      this.tmpGiftGoodsIds = ids
      ids.forEach(prdId => this.addProductRow(prdId))
    },
    /**
     * 校验库存输入
     *
     * @param prdNumber 原有库存
     * @param productNumber 输入库存
     * @param offerNumber 已赠送商品数
     */
    checkProductNumber (prdNumber, productNumber, offerNumber) {
      if (prdNumber < productNumber) {
        this.fail('赠品库存不能大于商品当前库存')
        return false
      }
      if (offerNumber > productNumber) {
        this.fail('初始库存不能小于已送赠品数')
        return false
      }
      return true
    },
    listenGoodsResult () {
      this.$http.$on('result', ids => {
        switch (this.step) {
          case 1:
            this.handleChoosingGoods(ids)
            break
          case 2:
            this.handleChoosingProduct(ids)
            break
        }
      })
    }
  },
  watch: {
    goodsRange (v) {
      if (v === 0) {
        // 选择了”全部商品“
        this.param.goodsIds = []
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
    this.loadTag()
    this.loadMemberCard()
    this.listenGoodsResult()
  }
}
</script>
<style lang="scss" scoped>
  .label {
    line-height: 40px
  }
  .input {
    margin-right: 10px;
    width: 70px;
  }
  .name_cell {
    display: flex;
    div {
      line-height: 45px;
      margin-left: 10px;
    }
  }
  .goods_img {
    width: 45px;
    height: 45px;
  }
  .description {
    color: #999;
  }
</style>
