<template>
  <!-- class="evaluationRecordContent" -->
  <div>
    <div
      class="table_box"
      style="background: #fff;margin-bottom: 10px;height: 50px;"
    >
      <div
        class="filters"
        style="width: 100%;margin-bottom: 0; margin-left: 0;"
      >
        <div class="filters_item"><span>{{$t('evaluation.orderSn') + '：' }}</span>
          <el-input
            v-model="searchParams.orderSn"
            :placeholder="$t('evaluation.input',[$t('evaluation.orderSn')])"
            size="small"
            style="width: 170px;"
          ></el-input>
        </div>
        <div class="filters_item"><span>{{$t('evaluation.goodsName') + '：' }}</span>
          <el-input
            v-model="searchParams.goodsName"
            :placeholder="$t('evaluation.input',[$t('evaluation.goodsName')])"
            size="small"
            style="width: 170px;"
          ></el-input>
        </div>
        <div class="filters_item"><span>{{$t('evaluation.mobile') + '：' }}</span>
          <el-input
            v-model="searchParams.mobile"
            :placeholder="$t('evaluation.input',[$t('evaluation.mobile')])"
            size="small"
            style="width: 170px;"
          ></el-input>
        </div>
        <div
          class="filters_item"
          v-if="target === 'Record'"
        >
          <span>{{$t('evaluation.auditState')}}</span>
          <el-select
            v-model="searchParams.flag"
            size="small"
            class="mini_select"
            style="width: 170px;"
          >
            <el-option
              v-for="item in auditFlag"
              :key="item.key"
              :label="item.value"
              :value="item.key"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item"><span>{{$t('evaluation.evaluationGrade') + '：'}}</span>
          <el-select
            v-model="searchParams.commstar"
            size="small"
            class="mini_select"
            style="width: 170px;"
          >
            <el-option
              v-for="item in starLevel"
              :key="item.key"
              :label="item.value"
              :value="item.key"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item"><span>{{$t('evaluation.evaluationTable.evaluationReward') + '：' }}</span>
          <el-select
            v-model="searchParams.awardActivityId"
            size="small"
            class="mini_select"
            style="width: 170px;"
          >
            <el-option
              v-for="item in evaluationRewardList"
              :key="item.id"
              :label="item.value"
              :value="item.id"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item">
          <el-button
            @click="initDataList"
            type="primary"
            size="small"
          >{{$t('marketCommon.filter')}}</el-button>
        </div>
      </div>
    </div>
    <div style="width: 100%; padding:10px;background: #fff;">
      <el-table
        v-loading="loading"
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="dataList"
        border
      >
        <el-table-column
          type="selection"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.productInformation')"
          align="center"
          width="200px"
        >
          <template slot-scope="scope">
            <div class="orderSn">
              <span v-if="scope.row.bogusUsername">{{$t('evaluation.merchantAddComment')}}</span>
              <span v-else>{{$t('evaluation.orderSn')}}：<span>{{scope.row.orderSn}}</span></span>
            </div>
            <div class="goods_info">
              <img
                :src="$imageHost+'/'+scope.row.goodsImg"
                alt=""
              >
              <div class="right_info">
                <div class="goods_name">{{scope.row.goodsName}}</div>
                <div
                  class="goods_desc"
                  v-if="scope.row.prdDesc"
                >{{scope.row.prdDesc}}</div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.userInfo')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="user_info">
              <p class="user_name">{{$t('evaluation.userName')}}：<span>{{scope.row.bogusUsername ? scope.row.bogusUsername : scope.row.username }}</span></p>
              <p v-if="!scope.row.bogusUsername">{{$t('evaluation.mobile')}}：{{scope.row.mobile}}</p>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.evaluationContent')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="evaluation-info">
              <div class="evaluation-info_item"><span class="evaluation-info_title">{{$t('evaluation.grade')}}：</span><span><i
                    class="el-icon-star-on"
                    v-for="index in scope.row.commstar"
                    :key="index"
                  ></i></span></div>
              <div class="evaluation-info_item"><span class="evaluation-info_title">{{$t('evaluation.evaluation')}}：</span><span>{{scope.row.commNote}}</span></div>
              <div class="evaluation-info_item"><span></span></div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.evaluationReply')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="evaluation_response">
              <span v-if="scope.row.content">{{$t('evaluation.reply')}}：{{scope.row.content}}</span>
              <el-button
                type="primary"
                v-if="!scope.row.content"
                size="mini"
                @click="writeReply(scope.row.id)"
              >{{$t('evaluation.writeReply')}}</el-button>
              <el-button
                type="default"
                v-else
                size="mini"
                @click="deleteReply(scope.row.id)"
              >{{$t('evaluation.deleteReply')}}</el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.evaluationTime')"
          prop="createTime"
          align="center"
        ></el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.anonymousEvaluation')"
          align="center"
        >
          <template slot-scope="scope">
            <span>{{scope.row.anonymousflag ? $t('evaluation.yes') : $t('evaluation.no')}}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.evaluationReward')"
          align="center"
        >
          <template slot-scope="scope">
            <span>{{scope.row.lotteryAward ? scope.row.lotteryAward : $t('evaluation.null')}}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.auditState')"
          v-if="target === 'Record'"
          align="center"
        >
          <template slot-scope="scope">
            <div>{{scope.row.flag | auditStatus}}</div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.operating')"
          align="center"
        >
          <template slot-scope="scope">
            <el-tooltip
              :content="$t('evaluation.deleteEvaluation')"
              placement="top"
            >
              <span
                class="el-icon-delete operateSpan"
                @click="delEvaluation(scope.row.id)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="$t('evaluation.pass')"
              placement="top"
              v-if="target === 'Record' && scope.row.flag === 0"
            >
              <span
                class="el-icon-remove-outline operateSpan"
                @click="evaluationRefuse(scope.row.id)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="$t('evaluation.refuse')"
              placement="top"
              v-if="target === 'Record' && scope.row.flag === 0"
            >
              <span
                class="el-icon-circle-plus-outline operateSpan"
                @click="evaluationRefuse(scope.row.id)"
              ></span>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination
      :page-params.sync="pageParams"
      @pagination="initDataList"
    />
    <el-dialog
      :title="$t('evaluation.reply')"
      :visible.sync="showReply"
      custom-class="custom"
      width="40%"
    >
      <el-input
        type="textarea"
        v-model="replyContent"
        resize="none"
        rows="10"
      ></el-input>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="showReply = false">{{$t('evaluation.cancel')}}</el-button>
        <el-button
          type="primary"
          @click="confirmReply"
        >{{$t('evaluation.confirm')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getCommentList, goodsCommentDelete, CommentPass, CommentRefuse, CommentAnswer, delAnswer } from '@/api/admin/goodsManage/evaluationManagement/evaluationManagement'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  props: {
    target: {
      type: String
    }
  },
  data () {
    return {
      searchParams: {
        commstar: 0,
        awardActivityId: -1,
        orderSn: null,
        goodsName: null,
        mobile: null,
        flag: -1
      },
      starLevel: [
        { key: 0, value: '全部' },
        { key: 1, value: '一星' },
        { key: 2, value: '二星' },
        { key: 3, value: '三星' },
        { key: 4, value: '四星' },
        { key: 5, value: '五星' }
      ],
      evaluationRewardList: [
        { id: -1, value: '全部' },
        { id: 1, value: 'xxxx' },
        { id: 2, value: 'aaaa' },
        { id: 3, value: 'dddd' },
        { id: 4, value: 'cccc' }
      ],
      auditFlag: [
        { key: -1, value: '全部' },
        { key: 0, value: '待审核' },
        { key: 1, value: '已通过' },
        { key: 2, value: '未通过' }
      ],
      pageParams: {
      },
      dataList: [
      ],
      loading: false,
      showReply: false,
      replyContent: null,
      replyId: null
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    // 初始化列表 评价列表 待评价列表
    initDataList () {
      this.loading = true
      let obj = {
        ...this.searchParams,
        page: { ...this.pageParams },
        awardActivityId: this.searchParams.awardActivityId === -1 ? null : this.searchParams.awardActivityId
      }
      if (this.target !== 'Record') {
        delete obj.flag
      }
      getCommentList(obj).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.dataList = res.content.dataList.sort((a, b) => {
            return (b.id - a.id)
          })
          this.loading = false
        }
      })
    },
    // 删除评价
    delEvaluation (id) {
      goodsCommentDelete({ id: id }).then(res => {
        if (res.error === 0) {
          this.dataList = this.dataList.filter(item => item.id !== id)
          this.$message.success({
            message: '删除成功',
            duration: '2000'
          })
        }
      })
    },
    // 通过审核
    evaluationPass (id) {
      console.log(id)
      CommentPass({ id: id }).then(res => {
        if (res.error === 0) {
          let targetData = this.dataList.find(item => id === item.id)
          targetData.flag = 1
        }
      })
    },
    // 拒绝审核
    evaluationRefuse (id) {
      console.log(id)
      CommentRefuse({ id: id }).then(res => {
        if (res.error === 0) {
          let targetData = this.dataList.find(item => id === item.id)
          targetData.flag = 2
        }
      })
    },
    // 调起添加回复弹框
    writeReply (id) {
      this.showReply = true
      this.replyContent = null
      this.replyId = id
    },
    // 添加回复
    confirmReply () {
      let obj = {
        commentId: this.replyId,
        content: this.replyContent
      }
      CommentAnswer(obj).then(res => {
        if (res.error === 0) {
          this.$message.success({
            message: '回复成功',
            duration: '2000'
          })
          this.showReply = false
          let targetData = this.dataList.find(item => item.id === this.replyId)
          targetData.content = this.replyContent
        }
      })
    },
    // 删除回复
    deleteReply (id) {
      delAnswer({ id: id }).then(res => {
        if (res.error === 0) {
          this.$message.success({
            message: '删除成功',
            duration: '2000'
          })
          let targetData = this.dataList.find(item => item.id === id)
          targetData.content = null
        }
      })
    }
  },
  filters: {
    auditStatus (flag) {
      switch (flag) {
        case 1:
          return `通过`
        case 2:
          return `未通过`
        default:
          return `待审核`
      }
    }
  }
}
</script>

<style lang="scss" scoped>
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.mini_select {
  width: 80px !important;
}
.goods_info {
  display: flex;
  border-bottom: 1px solid #eee;
  &:last-of-type {
    border-bottom: 0;
  }
  > img {
    width: 40px;
    height: 40px;
    margin-right: 5px;
  }
  > .right_info {
    flex: 1;
    display: flex;
    flex-direction: column;
    text-align: left;
    justify-content: space-between;
    .goods_name {
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      overflow: hidden;
      /*! autoprefixer: off */
      -webkit-box-orient: vertical;
      text-align: left;
      line-height: 1;
    }
  }
}
.user_info {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  text-align: left;
  .user_name {
    > span {
      color: #409eff;
    }
  }
}
.evaluation-info {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  text-align: left;
  > .evaluation-info_item {
    display: flex;
    > span {
      flex: 1;
      &.evaluation-info_title {
        flex: 0 1 auto;
        width: auto;
      }
      > .el-icon-star-on {
        color: #ff6666;
        font-size: 20px;
      }
    }
  }
}
.evaluation_response {
  display: flex;
  flex-direction: column;
  align-items: center;
  > .el-button {
    width: 90px;
  }
}
.operating {
  > p + p {
    margin-top: 5px;
  }
}
.orderSn {
  text-align: left;
  span {
    display: inline-block;
  }
}
.operateSpan {
  font-size: 22px;
  color: #5a8bff;
  cursor: pointer !important;
}
</style>
