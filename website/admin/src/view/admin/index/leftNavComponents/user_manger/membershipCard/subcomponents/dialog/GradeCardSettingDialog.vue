<template>
    <div id="gradeStop">
        <el-dialog
        title="提示"
        :visible.sync="visiable"
        width="30%"
        >
            <div class="top">
                共{{userNum}}位会员拥有该卡
            </div>
            <div class="middle">
                <el-radio v-model="radio" :label="0">直接停用</el-radio>
                <el-radio v-model="radio" :label="1">停用后置换为其他等级卡</el-radio>
            </div>
            <div class="bottom">
                <p v-if="radio===0">
                    直接停用本卡，则已经领取的等级用户也会停用该等级卡，不再享受等级会员权限
                </p>
                <div v-else-if="radio === 1">
                    <p>
                        <span class="stop-tip">停用后拥有该卡用户默认设置为:</span>
                        <el-select v-model="choosedCardId" size="small" style="width: 130px;">
                            <el-option
                              v-for="item in cardList"
                              :key="item.id"
                              :label="item.card_name"
                              :value="item.id"
                              >
                            </el-option>
                        </el-select>
                    </p>
                    <p>
                        直接停用本卡，拥有本卡的用户批量更换为设置的新等级卡
                    </p>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="handleOk">确 定</el-button>
                <el-button @click="visiable = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {getAllValidGradeCard} from '@/api/admin/memberManage/memberCard.js'
export default {
  props: {
    dialogVisiable: {
      type: Boolean,
      default: false
    },
    currentCard: {
      type: Object,
      required: true
    }
  },
  computed: {
    visiable: {
      get () {
        return this.dialogVisiable
      },
      set (val) {
        this.clearData()
        return this.$emit('update:dialogVisiable', val)
      }
    },
    userNum () {
      return this.currentCard.cardUseStats.haveNormalNum
    }
  },
  watch: {
    dialogVisiable (newValue, oldValue) {
      if (newValue) {
        this.initValidCard()
      }
    }
  },
  data () {
    return {
      radio: 0,
      cardList: [],
      choosedCardId: null
    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {
    initValidCard () {
      getAllValidGradeCard().then(res => {
        if (res.error === 0) {
          this.cardList = res.content
          //  等级排序
          if (res.content.length > 1) {
            this.cardList = res.content.sort((a, b) => {
              return a.grade > b.grade ? 1 : b.grade > a.grade ? -1 : 0
            })
          }
          //  过滤掉本张卡
          this.cardList = this.cardList.filter(item => item.grade !== this.currentCard.grade)
        }
      })
    },
    handleOk () {
      this.$emit('getStopCardCfg', {currentCardId: this.currentCard.id, stopPlan: this.radio, anotherNewCardId: this.choosedCardId})
      this.visiable = false
    },
    clearData () {
      this.radio = 0
      this.choosedCardId = null
    }
  }
}
</script>

<style lang="scss" scoped>
#gradeStop{
    font-size: 12px;
    line-height: 24px;
}
.top{
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}
.middle{
    margin: 20px 0;
    /deep/ .el-radio:last-child{
        margin-left: 22px;
    }
}
.bottom{
    height: 70px;
    background-color: #F3F3F3;
    padding: 10px;
    span.stop-tip{
        margin-right: 5px;
    }
    display: flex;
    align-items: center;
    justify-content: left;

}
</style>
