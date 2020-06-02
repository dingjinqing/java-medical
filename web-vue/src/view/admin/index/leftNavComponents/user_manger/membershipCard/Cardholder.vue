<template>
  <div class="Cardholder">
    <div class="CardholderMain">
      <div class="CardholderTop">
        <div class="topDiv">
          <div>
            <span>{{$t('memberCard.memberId')}}</span>
            <el-input
              v-model="carIdInput"
              :placeholder="$t('memberCard.pleaseInput')"
              size="small"
            ></el-input>
          </div>
          <div>
            <span>{{$t('memberCard.username')}}</span>
            <el-input
              v-model="carNameInput"
              :placeholder="$t('memberCard.pleaseInput')"
              size="small"
            ></el-input>
          </div>
          <div>
            <span>{{$t('memberCard.mobile')}}</span>
            <el-input
              v-model="phoneInput"
              :placeholder="$t('memberCard.pleaseInput')"
              size="small"
            ></el-input>
          </div>
          <div>
            <span>{{$t('memberCard.cardNo')}}</span>
            <el-input
              v-model="cardNuberInput"
              :placeholder="$t('memberCard.pleaseInput')"
              size="small"
            ></el-input>
          </div>
        </div>
        <div class="topDiv">
          <div>
            <span>{{$t('memberCard.cardStatus')}}</span>
            <el-select
              v-model="statusValue"
              size="small"
            >
              <el-option
                v-for="(item,index) in $t('membershipIntroduction.cardStatusOpt')"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div>
            <span>{{$t('memberCard.receiveCardTime')}}</span>
            <date-time-picker
              :showPicker='3'
              @startTime="firstDateTime = $event"
              @endTime="secondDateTime = $event"
            />

          </div>
          <div
            v-if="showActivation"
            style="margin-left: 56px;"
          >
            <span style="width: auto;">{{$t('memberCard.examineSubmit')}}</span>
            <el-select
              v-model="submitValue"
              size="small"
            >
              <el-option
                v-for="(item,index) in submitExamineOpts"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </div>
        <div class="topDiv">
          <div v-if="showActivation">
            <span>{{$t('memberCard.examineStatus')}}</span>
            <el-select
              v-model="examineStatusValue"
              size="small"
            >
              <el-option
                v-for="(item,index) in examineStatusOpt"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div>
            <span>{{$t('memberCard.consumeRecord')}}</span>
            <el-select
              v-model="consumeRecordValue"
              size="small"
            >
              <el-option
                v-for="(item,index) in consumeRecordOpt"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div>
            <span>{{$t('memberCard.chargeRecord')}}</span>
            <el-select
              v-model="chargeRecordValue"
              size="small"
            >
              <el-option
                v-for="(item,index) in chargeRecordOpt"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </div>
        <div class="bottomDiv">
          <el-button
            type="primary"
            size="small"
            @click="handleTobtn(0)"
          >{{$t('memberCard.filter')}}</el-button>
          <el-button
            type="info"
            plain
            size="small"
            @click="handleTobtn(1)"
          >{{$t('memberCard.reset')}}</el-button>
          <el-button
            type="info"
            plain
            size="small"
            @click="handleTobtn(2)"
          >{{$t('memberCard.memberCardOut')}}</el-button>
        </div>
      </div>
    </div>
    <div class="tableMain">
      <el-table
        class="version-manage-table"
        :data="tableData"
        header-row-class-name="tableClss"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="userId"
          align="center"
          :label="$t('memberCard.memberId')"
          width="90"
        >
        </el-table-column>
        <el-table-column
          :label="$t('memberCard.username')"
          width="115"
          align="center"
        >
          <template slot-scope="scope">
            <span
              @click="handleToUserDetail(scope.row.userId)"
              style="cursor:pointer;color:#5a8bff"
            >
              {{scope.row.username}}
            </span>
          </template>
        </el-table-column>
        <el-table-column
          prop="mobile"
          :label="$t('memberCard.mobile')"
          width="115"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="invitedName"
          :label="$t('memberCard.invitedPerson')"
          width="120"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="createTime"
          :label="$t('memberCard.receiveCardTime')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="cardNo"
          :label="$t('memberCard.cardNo')"
          align="center"
          width="180"
        >
        </el-table-column>
        <el-table-column
          :label="$t('memberCard.examineSubmit')"
          align="center"
          v-if="showActivation"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.status">{{$t('memberCard.yes')}}</span>
            <span v-else>{{$t('memberCard.no')}}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('memberCard.examineStatus')"
          align="center"
          v-if="showActivation"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.status===1">{{$t('memberCard.examing')}}</span>
            <span v-else-if="scope.row.status===2">{{$t('memberCard.successAudit')}}</span>
            <span v-else-if="scope.row.status===3">{{$t('memberCard.examineFail')}}</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('memberCard.cardStatus')"
          align="center"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.flag === 0"> {{$t('memberCard.cardNomal')}} </span>
            <span v-else-if="scope.row.flag === 1"> {{$t('memberCard.alreadyDelete')}}({{scope.row.updateTime}}) </span>
            <span v-else-if="scope.row.flag === 2"> 转赠中 </span>
            <span v-else-if="scope.row.flag === 3"> {{$t('membershipIntroduction.cardGived')}}({{scope.row.getTime}});赠予:
              <span
                @click="handleToUserDetail(scope.row.getUserId)"
                style="color: #5a8bff;cursor: pointer;"
              >
                {{scope.row.giveName}}
              </span>;
            </span>
            <span v-else-if="scope.row.flag === 4"> {{$t('memberCard.cardExpired')}} </span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="operation">
              <span
                v-for="(item,index) in operation"
                :key="index"
                @click="handleToOperation(scope.row,index)"
              >
                <span v-if="Number(scope.row.cardType)!==2 && index<2">
                  <span
                    class="opt-item"
                    :class="[Number(scope.row.cardType)!==2?'content-left':'']"
                  >{{item}}</span>
                </span>
                <span v-else>
                  <span
                    v-if="scope.row.flag !== 1 && index===2"
                    class="opt-item"
                    :class="[Number(scope.row.cardType)!==2?'content-left':'']"
                  > {{item}}</span>
                  <span v-else>
                    <span
                      v-if="Number(scope.row.cardType)!==2"
                      class="opt-item"
                      :class="[Number(scope.row.cardType)!==2?'content-left':'']"
                    ></span>
                  </span>
                </span>
              </span>
            </div>
          </template>
        </el-table-column>

      </el-table>
      <Pagination
        :page-params.sync="pageParams"
        @pagination="search"
      />
    </div>
  </div>
</template>
<script>
import { getAllCardHolders, exportExcel, deleteUserCardRequest } from '@/api/admin/memberManage/memberCard.js'
import { download } from '@/util/excelUtil.js'
import DateTimePicker from '@/components/admin/dateTimePicker/dateTimePicker'
export default {
  components: { Pagination: () => import('@/components/admin/pagination/pagination'), DateTimePicker },

  data () {
    return {
      pageParams: {
        totalRows: null,
        currentPage: 1,
        pageRows: 20
      },
      cardId: null, // 会员卡id
      carIdInput: '',
      carNameInput: '',
      phoneInput: '',
      cardNuberInput: '',
      submitExamineOpts: [
        {
          value: -1,
          label: '全部'
        },
        {
          value: 1,
          label: '是'
        },
        {
          value: 0,
          label: '否'
        }
      ],
      examineStatusOpt: [
        {
          value: 0,
          label: '全部'
        },
        {
          value: 1,
          label: '待审核'
        },
        {
          value: 2,
          label: '审核通过'
        },
        {
          value: 3,
          label: '审核失败'
        }
      ],
      consumeRecordOpt: [
        {
          value: -1,
          label: '全部'
        },
        {
          value: 1,
          label: '有消费记录'
        },
        {
          value: 0,
          label: '无消费记录'
        }
      ],
      chargeRecordOpt: [
        {
          value: -1,
          label: '全部'
        },
        {
          value: 1,
          label: '有充值记录'
        },
        {
          value: 0,
          label: '无充值记录'
        }
      ],
      statusValue: -1, // 卡状态默认值
      submitValue: -1, // 是否提交审核申请
      examineStatusValue: 0, // 卡审核状态
      consumeRecordValue: -1, // 有无消费记录
      chargeRecordValue: -1, // 有无充值记录
      firstDateTime: null, // 领取时间
      secondDateTime: null, // 领取时间
      tableData: [],
      operation: ['充值明细', '消费明细', '废除'],
      cardType: null,
      activation: null
    }
  },
  computed: {
    showActivation () {
      return this.activation === 1
    }
  },
  created () {
    this.cardId = this.$route.query.cardId
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    // 1- 加载默认的数据
    defaultData () {
      let obj = {
        'pageRows': this.pageParams.pageRows,
        'currentPage': this.pageParams.currentPage,
        'cardId': this.cardId,
        'userId': this.carIdInput,
        'username': this.carNameInput,
        'mobile': this.phoneInput,
        'cardNo': this.cardNuberInput,
        'flag': this.statusValue,
        'firstDateTime': this.secondDateTime,
        'secondDateTime': this.secondDateTime,
        'submitValue': this.submitValue,
        'examineStatusValue': this.examineStatusValue,
        'consumeRecordValue': this.consumeRecordValue,
        'chargeRecordValue': this.chargeRecordValue
      }
      console.log(obj)
      // 获取api
      getAllCardHolders(obj).then(res => {
        if (res.error === 0) {
          // 成功
          this.tableData = res.content.data.dataList
          // 分页信息
          this.pageParams = res.content.data.page
          this.cardType = res.content.cardType
          this.activation = res.content.activation
        }
      })
    },
    // 2- 顶部按钮系列点击
    handleTobtn (index) {
      switch (index) {
        case 0:
          // 筛选
          this.defaultData()
          break
        case 1:
          // 重置
          this.carIdInput = ''
          this.carNameInput = ''
          this.phoneInput = ''
          this.cardNuberInput = ''
          this.statusValue = -1
          this.firstDateTime = null
          this.secondDateTime = null
          this.submitValue = -1
          this.examineStatusValue = 0
          this.consumeRecordValue = -1
          this.chargeRecordValue = -1
          break
        case 2:
          this.exportInfo()
          break
      }
    },
    // 3- 处理分页信息传递出来的分页数据
    search (data) {
      console.log(data)
      this.defaultData()
    },
    // 操作部分点击
    handleToOperation (row, index) {
      console.log(row, index)
      if (index < 2) {
        //  充值明细  消费明细
        this.$router.push({
          name: 'refillDetailsItem',
          query: {
            cardNo: row.cardNo,
            cardType: row.cardType,
            username: row.username,
            userId: row.userId,
            activeName: index + 1
          }
        })
      } else if (index === 2) {
        //  废除
        deleteUserCardRequest({ cardNo: row.cardNo }).then(res => {
          if (res.error === 0) {
            this.$message.success(this.$t('membershipIntroduction.deleteCardSuccess'))
            this.defaultData()
          }
        })
      }
    },

    // 跳转到会员详情页
    handleToUserDetail (userId) {
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId: userId
        }
      })
    },
    exportInfo () {
      let obj = {
        'pageRows': this.pageParams.pageRows,
        'currentPage': this.pageParams.currentPage,
        'cardId': this.cardId,
        'userId': this.carIdInput,
        'username': this.carNameInput,
        'mobile': this.phoneInput,
        'cardNo': this.cardNuberInput,
        'flag': this.statusValue,
        'firstDateTime': this.firstDateTime,
        'secondDateTime': this.secondDateTime
      }
      exportExcel(obj).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : 'template.xlsx'
        console.log('文件名：' + fileName)
        download(res, decodeURIComponent(fileName))
      }).catch((err, data) => {
        console.error('err:', err)
      })
    }

  }
}
</script>
<style lang="scss" scoped>
.Cardholder {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .CardholderMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .topDiv {
      display: flex;
      margin-bottom: 20px;
      div {
        /deep/ .el-input {
          width: 140px;
        }
        span {
          white-space: nowrap;
          display: inline-block;
          width: 80px;
          text-align: right;
          margin-right: 20px;
        }
        display: flex;
        align-items: center;
      }
    }
    .bottomDiv {
      display: flex;
      /deep/ .el-button {
        width: 85px;
      }
    }
  }
  .tableMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    margin-top: 10px;
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
      .el-checkbox {
        margin-left: -4px;
      }
    }
    .operation {
      display: flex;
      justify-content: center;
      margin-left: 30px;
      span {
        cursor: pointer;
        color: #5a8bff;
      }
      .opt-item {
        width: 58px;
        display: block;
      }

      .content-left {
        text-align: left;
        margin-left: 3px;
      }
    }
  }
}
</style>
