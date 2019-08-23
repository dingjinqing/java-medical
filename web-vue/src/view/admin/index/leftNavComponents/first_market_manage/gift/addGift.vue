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
          <el-col :span="10">
            <el-form label-width="100px" >
              <el-form-item label="活动名称">
                <el-input v-model="param.name"></el-input>
              </el-form-item>
              <el-form-item label="活动优先级">
                <el-input v-model="param.level"></el-input>
              </el-form-item>
              <el-form-item label="活动名称">
                <el-date-picker
                  v-model="dateRange"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <el-row style="margin-bottom:20px">
          <el-col :span="2">
            <span class="label">赠品策略</span>
          </el-col>
          <el-col :span="10">
            <el-form label-width="100px" >
              <el-form-item label="活动商品">
                <template>
                  <el-radio v-for="(item, index) in goodsRanges" :key="index" v-model="goodsRange" :label="index">{{item}}</el-radio>
                  <el-button v-show="goodsRange===1" size="small">添加商品</el-button>
                  <span v-show="goodsRange===1">已选{{param.goodsIds.length}}</span>
                </template>
              </el-form-item>
              <el-form-item label="赠品条件">
              </el-form-item>
              <el-form-item label="赠品规则说明">
                <el-input
                  type="textarea"
                  :rows="5"
                  v-model="param.explain">
                </el-input>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        </div>
        <!-- 设置赠品 -->
        <div v-if="step===2"></div>
        <el-row>
          <el-col :offset="4">
            <el-button type="primary" @click="step-=1" v-show="step > 1">上一步</el-button>
            <el-button type="primary" @click="step+=1" v-show="step < steps.length">下一步</el-button>
            <el-button type="primary" @click="addGift" v-show="step === steps.length">保存</el-button>
          </el-col>
        </el-row>
    </wrapper>
  </div>
</template>
<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import { format } from '@/util/date'
import { addGift, getGiftDetail, updateGift } from '@/api/admin/marketManage/gift'
export default {
  components: {
    wrapper
  },
  data () {
    return {
      id: null,
      step: 1,
      steps: ['设置活动规则', '设置赠品'],
      dateRange: [],
      goodsRange: 0,
      goodsRanges: ['全部商品', '指定商品'],
      update: false,
      param: {
        'name': '买则送赠品',
        'level': 1,
        'startTime': '2019-08-19 17:49:36',
        'endTime': '2019-09-19 17:49:36',
        'goodsIds': [],
        'explain': 'Come on !!!',
        'rules': {
          'fullPrice': null,
          'fullNumber': null,
          'tagId': null,
          'userAction': null,
          'payTop': null,
          'minPayNum': 1,
          'maxPayNum': 10,
          'cardId': [ 1, 2, 3 ],
          'payStartTime': '2019-08-19 18:07:54',
          'payEndTime': '2019-08-19 18:08:01'
        },
        'gifts': [
          {
            'productId': 1,
            'productNumber': 1
          }, {
            'productId': 2,
            'productNumber': 1
          }
        ]
      }
    }
  },
  methods: {
    addGift () {
      const then = r => this.gotoGifts()
      const { param } = this
      const { dateRange } = this
      const startTime = dateRange[0]
      const endTime = dateRange[1]
      this.param.startTime = format(startTime)
      this.param.endTime = format(endTime)
      if (this.update) {
        updateGift(param).then(then)
      } else {
        addGift(param).then(then)
      }
    },
    loadData () {
      const { id } = this.$route.params
      getGiftDetail(id).then(({ content }) => {
        this.param = content
        const { startTime, endTime } = content
        this.dateRange.push(startTime)
        this.dateRange.push(endTime)
        const { goodsIds } = content
        if (!goodsIds) {
          this.goodsRange = 0
        }
      })
    },
    gotoGifts () {
      this.$router.replace('/admin/home/main/gift')
    }
  },
  watch: {
    goodsRange (v) {
      if (v === 1) {
        this.param.goodsIds = []
      }
    }
  },
  mounted () {
    const id = this.$route.params.id
    this.update = !!id
    if (this.update) {
      this.loadData()
    }
  }
}
</script>
<style lang="scss" scoped>
  .label {
    line-height: 40px
  }
</style>
