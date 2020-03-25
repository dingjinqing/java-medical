<template>
  <div class="cardFormPage">
    <div class="top">
      <el-button
        type="primary"
        size="small"
      >添加会员卡</el-button>
    </div>
    <div class="bottom">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="cardName"
          label="会员卡名称"
          align="center"
          width="160"
        >

        </el-table-column>
        <el-table-column
          prop="id"
          label="卡ID"
          align="center"
        >

        </el-table-column>
        <el-table-column
          label="有效期"
          align="center"
          prop="discountInputVal"
        >
          <template slot-scope="scope">
            {{scope.row.expiredType === 0?`${scope.row.startTime}-${scope.row.endTime}`:scope.row.expiredType === 1?(`自领取之日起${scope.row.receiveDay}${scope.row.dateType===0?'日':scope.row.dateType===1?'周':'月'}内有效`):'永久有效'}}
          </template>
        </el-table-column>
        <el-table-column
          prop="goodsName"
          label="卡权益"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.equity}}
          </template>
        </el-table-column>
        <el-table-column
          prop="goodsName"
          label="领取条件"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.conditions}}
          </template>
        </el-table-column>
        <el-table-column
          prop="goodsName"
          label="卡状态"
          align="center"
        >
          <template slot-scope="scope">
            <span>
              {{scope.row.flag === 1?$t('memberCard.tips'):scope.row.flag === 2?$t('memberCard.noUser'):$t('memberCard.expired')}}
            </span>
          </template>
        </el-table-column>
        <el-table-column
          prop="goodsName"
          label="已领取卡数量"
          align="center"
          width="200"
          :render-header="renderHeaderMethod"
        >

        </el-table-column>
        <el-table-column
          prop="goodsName"
          label="可正常使用卡数量"
          align="center"
          width="200"
          :render-header="renderHeaderMethodNum"
        >

        </el-table-column>
        <el-table-column
          prop="goodsName"
          label="操作"
          align="center"
          width="90"
        >
          <template slot-scope="scope">
            <div class="opeartion">
              <div @click="handleToTips(0,scope.row,scope.$index)">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="编辑"
                  placement="top-start"
                >
                  <span class="iconfont iconbianji"></span>
                </el-tooltip>
              </div>

              <div
                v-if="scope.row.flag !== 3"
                @click="handleToTips(2,scope.row,scope.$index)"
                style="margin-left:5px"
              >
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="scope.row.flag===2?'启用':'停用'"
                  placement="top-start"
                >
                  <span :class="scope.row.flag===2?'iconfont iconqiyong':'iconfont icontingyong'"></span>
                </el-tooltip>
              </div>
              <div
                v-if="scope.row.flag!==1"
                @click="handleToTips(1,scope.row,scope.$index,2)"
                style="margin-left:5px"
              >
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="删除"
                  placement="top-start"
                >
                  <span class="iconfont iconshanchu2"></span>
                </el-tooltip>
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
import { deleteCardRequest, getAllMemberCardRequest, changeCardStatueRequest } from '@/api/admin/memberManage/memberCard.js'
export default {
  props: {
    cardType: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      tableData: []// 表格数据
    }
  },
  watch: {
    cardType (newData) {
      console.log(newData)
      this.handleToQuery()
    }
  },
  mounted () {
    // 初始化请求数据
    this.handleToQuery()
  },
  methods: {
    // 初始化请求数据
    handleToQuery () {
      console.log(this.cardType)
      let obj = {
        'currentPage': 0,
        'pageRows': 100,
        'cardType': this.cardType
      }
      getAllMemberCardRequest(obj).then(res => {
        console.log(res)
        if (res.error === 0) {
          res.content.dataList.forEach((item, index) => {
            this.dealWithCardBehavior(item) // 处理权益
            this.dealWithReceiveConditions(item) // 处理领取条件
          })
          this.tableData = res.content.dataList
        }
      })
    },
    // 2- 处理会员卡权益
    dealWithCardBehavior (card) {
      let content = ''
      // 会员折扣
      if (card.powerCount === 1) {
        content += `${this.$t('membershipIntroduction.memberCount')}${card.disCount}${this.$t('membershipIntroduction.disCount')}`
      }
      // 会员专享商品
      if (card.powerPayOwnGood === 'on') {
        content += this.$t('membershipIntroduction.powerPayOwnGood')
      }
      // 充值奖励
      if (card.powerCard === 1) {
        content += this.$t('membershipIntroduction.powerCard')
      }
      // 积分奖励
      if (card.powerScore === 1) {
        content += this.$t('membershipIntroduction.powerScore')
      }
      // 门店兑换次数
      if (card.count > 0) {
        content += `${this.$t('membershipIntroduction.storeExchange')}${card.count}${this.$t('membershipIntroduction.times')}`
      }
      // 商品兑换次数
      if (card.exchangCount > 0) {
        content += `${this.$t('membershipIntroduction.goodsExchange')}${card.exchangCount}${this.$t('membershipIntroduction.times')}`
      }
      card.equity = content
    },
    // 3- 领取条件
    dealWithReceiveConditions (card) {
      let content = ''
      // 0：直接领取；1：需要购买；2：需要领取码
      if (card.isPay === 0) {
        content += this.$t('memberCard.receiveDirect') + ';'
      } else if (card.isPay === 1) {
        content += this.$t('memberCard.needBuy') + ';'
      } else if (card.isPay === 2) {
        content += this.$t('memberCard.needReceiveCode') + ';'
      }

      // 是否需要激活 0： 否；1： 是
      if (card.activation) {
        content += this.$t('memberCard.activationYes') + ';'
        //  是否审核 0： 否；1： 是
        if (card.examine) {
          content += this.$t('memberCard.examineYes') + ';'
        } else {
          content += this.$t('memberCard.examineNo') + ';'
        }
      } else {
        content += this.$t('memberCard.activationNo') + ';'
      }

      card.conditions = content
    },
    // 自定义表头
    renderHeaderMethod (h, { column }) {
      return h('span', {}, [
        h('span', {}, '已领取卡数量'),
        h('el-popover', { props: { placement: 'top-start', width: '230', trigger: 'hover', content: '已领取到用户账户中的卡数量' } }, [
          h('span', { slot: 'reference', class: 'el-icon-question' }, '')
        ])
      ])
    },
    renderHeaderMethodNum (h, { column }) {
      return h('span', {}, [
        h('span', {}, '可正常使用卡数量'),
        h('el-popover', { props: { placement: 'top-start', width: '230', trigger: 'hover', content: '用户下单时可立即使用的会员卡数量(不包括未激活)' } }, [
          h('span', { slot: 'reference', class: 'el-icon-question' }, '')
        ])
      ])
    },
    // 操作点击综合处理
    handleToTips (flag, item, index, type) {
      console.log(flag, type)
      switch (flag) {
        case 0:
          this.handleToCardDetail(item.cardType, item.id)
          break
        case 1:
          // 删除
          this.deleteCard(item.id)
          break
        case 2:
          // 停用-启动
          if (item.flag === 1) {
            // 使用中转化成停用
            this.stopCardStatus(item.id)
          } else {
            // 停止使用转化成使用
            this.powerCardStatus(item.id)
          }
      }
    },
    // 跳转到会员卡详情页
    handleToCardDetail (type, cardId) {
      let obj = { query: { 'cardType': type, cardId } }
      if (type === 0) {
        obj.name = 'normalCardDetail'
      } else if (type === 1) {
        obj.name = 'limitCardDetail'
      } else if (type === 2) {
        obj.name = 'gradeCardDetail'
      }
      console.log(obj)
      this.$router.push(obj)
    },
    // 停止或启动会员卡
    stopCardStatus (id) {
      let data = {
        id,
        'flag': 2
      }
      changeCardStatueRequest(data).then(res => {
        if (res.error === 0) {
          console.log('停止成功')
          this.$message.success({
            message: '操作成功',
            showClose: true
          })
          this.handleToQuery()
        }
      })
    },
    powerCardStatus (id) {
      let data = {
        id,
        'flag': 1
      }
      changeCardStatueRequest(data).then(res => {
        if (res.error === 0) {
          console.log('启动成功')
          this.$message.success({
            message: '操作成功',
            showClose: true
          })
          this.handleToQuery()
        }
      })
    },
    // 删除会员卡
    deleteCard (id) {
      this.$confirm('确认要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteCardReq(id)
      }).catch(() => {
        this.$message.info(
          '已取消删除'
        )
      })
    },
    deleteCardReq (id) {
      deleteCardRequest({ id }).then(res => {
        if (res.error === 0) {
          console.log('删除成功')
          this.$message.success({
            message: '操作成功',
            showClose: true
          })
          this.handleToQuery()
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.cardFormPage {
  .bottom {
    margin-top: 10px;
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
    }
    /deep/ .el-icon-question {
      color: #999;
    }
    .opeartion {
      color: #5a8bff;
      display: flex;
      justify-content: flex-start;
      div {
        cursor: pointer;
      }
      span {
        font-size: 20px;
      }
    }
  }
}
</style>
