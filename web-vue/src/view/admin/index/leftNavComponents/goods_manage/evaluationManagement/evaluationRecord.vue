<template>
  <!-- class="evaluationRecordContent" -->
  <div>
    <el-row class="filter-status" style="background-color:#fff">
      <el-col :span="22" >
        <el-tabs
          v-model="tabDefaultStatus"
          @tab-click="handleClick"
        >
          <template v-for="item in tabsStatus">
            <el-tab-pane
              :label="item.label"
              :name="item.value"
              :key="item.value"
              v-if="item.value === '0'"
            >
              <span slot="label">
                <span>全部</span>
              </span>
            </el-tab-pane>
            <el-tab-pane
              :label="item.label"
              :name="item.value"
              :key="item.value"
              v-else
            >
            </el-tab-pane>
          </template>
        </el-tabs>
      </el-col>
      <el-col :span="2" class="audit-button" v-if="target === 'Record'">
        <el-button type="primary" size="small" @click="handleShowReviewDialog">审核设置</el-button>
      </el-col>
    </el-row>
    <div style="width: 100%; padding:10px;background: #fff;margin-top:10px">
      <div class="table_box" style="background: #fff;">
        <el-row>
          <el-col :span="6">
            <div class="filters_item">
              <span>{{ $t("evaluation.orderSn") + "：" }}</span>
              <el-input
                v-model="searchParams.orderSn"
                :placeholder="$t('evaluation.input', [$t('evaluation.orderSn')])"
                size="small"
                style="width: 170px;"
              ></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="filters_item">
              <span>{{ $t("evaluation.goodsName") + "：" }}</span>
              <el-input
                v-model="searchParams.goodsName"
                :placeholder="
                  $t('evaluation.input', [$t('evaluation.goodsName')])
                "
                size="small"
                style="width: 170px;"
              ></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="filters_item">
              <span>{{ $t("evaluation.mobile") + "：" }}</span>
              <el-input
                v-model="searchParams.mobile"
                :placeholder="$t('evaluation.input', [$t('evaluation.mobile')])"
                size="small"
                style="width: 170px;"
              ></el-input>
            </div>
          </el-col>
          <el-col :span="6" v-if="target === 'Record'">
            <div class="filters_item">
              <span>{{ $t("evaluation.auditState") + "：" }}</span>
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
          </el-col>
          <el-col :span="6">
            <div class="filters_item">
              <span>{{ $t("evaluation.evaluationGrade") + "：" }}</span>
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
          </el-col>
          <el-col :span="6">
            <div class="filters_item">
              <span>{{
                $t("evaluation.evaluationTable.evaluationAct") + "："
              }}</span>
              <el-select
                v-model="searchParams.awardActivityId"
                size="small"
                class="mini_select"
                style="width: 170px;"
              >
                <el-option
                  v-for="item in evaluationRewardList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="filters_item">
              <el-button @click="initDataList" type="primary" size="small">{{
                $t("marketCommon.filter")
              }}</el-button>
            </div>
          </el-col>
        </el-row>
      </div>
      <el-table
        v-loading="loading"
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="dataList"
        border
      >
        <el-table-column type="selection" align="center" width="58px">
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.productInformation')"
          align="center"
          width="250px"
        >
          <template slot-scope="scope">
            <div class="orderSn">
              <span v-if="scope.row.bogusUsername">{{
                $t("evaluation.merchantAddComment")
              }}</span>
              <span v-else
                >{{ $t("evaluation.orderSn") }}：<span>{{
                  scope.row.orderSn
                }}</span></span
              >
            </div>
            <div class="goods_info">
              <img :src="$imageHost + '/' + scope.row.goodsImg" alt="" />
              <div class="right_info">
                <div class="goods_name">{{ scope.row.goodsName }}</div>
                <div class="goods_desc" v-if="scope.row.prdDesc">
                  {{ scope.row.prdDesc }}
                </div>
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
              <p
                :class="scope.row.bogusUsername ? 'fake_user' : 'user_name'"
                @click="
                  !scope.row.bogusUsername && goUserCenter(scope.row.userId)
                "
              >
                {{ $t("evaluation.userName") }}：<span>{{
                  scope.row.bogusUsername || scope.row.username
                }}</span>
              </p>
              <p v-if="!scope.row.bogusUsername">
                {{ $t("evaluation.mobile") }}：{{ scope.row.mobile }}
              </p>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.evaluationContent')"
          align="center"
          width="200px"
        >
          <template slot-scope="scope">
            <div class="evaluation-info">
              <div class="evaluation-info_item">
                <span class="evaluation-info_title"
                  >{{ $t("evaluation.grade") }}：</span
                ><span
                  ><i
                    class="el-icon-star-on"
                    v-for="index in scope.row.commstar"
                    :key="index"
                  ></i
                ></span>
              </div>
              <div class="evaluation-info_item">
                <span class="evaluation-info_title"
                  >{{ $t("evaluation.evaluation") }}：</span
                ><span>{{
                  scope.row.commNote || $t("evaluation.noExperience")
                }}</span>
              </div>
              <div
                class="evaluation-info_item"
                v-if="scope.row.commImg.length > 0"
              >
                <div class="evaluation-pic">
                  <template v-for="(picItem, picIndex) in scope.row.commImg">
                    <el-image
                      :key="picIndex"
                      lazy
                      style="width: 50px; height: 50px"
                      :src="picItem"
                      :preview-src-list="scope.row.commImg"
                    >
                    </el-image>
                  </template>
                </div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.evaluationReply')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="evaluation_response">
              <span v-if="scope.row.content"
                >{{ $t("evaluation.reply") }}：{{ scope.row.content }}</span
              >
              <el-button
                type="primary"
                v-if="!scope.row.content"
                size="mini"
                @click="writeReply(scope.row.id)"
                >{{ $t("evaluation.writeReply") }}</el-button
              >
              <el-button
                type="default"
                v-else
                size="mini"
                @click="deleteReply(scope.row.id)"
                >{{ $t("evaluation.deleteReply") }}</el-button
              >
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
            <span>{{
              scope.row.anonymousflag
                ? $t("evaluation.yes")
                : $t("evaluation.no")
            }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.evaluationReward')"
          align="center"
        >
          <template slot-scope="scope">
            <span>{{
              scope.row.awardType | awardType
            }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.auditState')"
          v-if="target === 'Record'"
          align="center"
        >
          <template slot-scope="scope">
            <div>{{ scope.row.flag | auditStatus }}</div>
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
              v-if="
                target === 'Record' &&
                  (scope.row.flag === 0 || scope.row.flag === 2)
              "
            >
              <span
                class="el-icon-success operateSpan"
                @click="evaluationPass(scope.row.id)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="$t('evaluation.refuse')"
              placement="top"
              v-if="
                target === 'Record' &&
                  (scope.row.flag === 0 || scope.row.flag === 1)
              "
            >
              <span
                class="el-icon-error operateSpan"
                @click="evaluationRefuse(scope.row.id)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="置顶"
              placement="top"
              v-if="scope.row.isTop === 0"
            >
              <span
                class="el-icon-top operateSpan"
                @click="evaluationTop(scope.row.id)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="取消"
              placement="top"
              v-if="scope.row.isTop === 1"
            >
              <span
                class="el-icon-bottom operateSpan"
                @click="evaluationCancelTop(scope.row.id)"
              ></span>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination :page-params.sync="pageParams" @pagination="initDataList" />
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
      <span slot="footer" class="dialog-footer">
        <el-button @click="showReply = false">{{
          $t("evaluation.cancel")
        }}</el-button>
        <el-button type="primary" @click="confirmReply">{{
          $t("evaluation.confirm")
        }}</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="审核设置"
      :visible.sync="showReviewSet"
      custom-class="custom"
      width="30%"
    >
      <div class="reviewSetInfo">
        <div>
          <el-radio-group
            v-model="dialogReviewStatus"
          >
            <el-radio :label="0">不用审核</el-radio>
            <el-radio :label="1">先发后审</el-radio>
            <el-radio :label="2">先审后发</el-radio>
          </el-radio-group>
        </div>
        <div>
          <el-switch
          v-model="dialogHideEvaluation"
          active-color="#f7931e"
          ></el-switch>
          <span>{{dialogHideEvaluation ? '已开启':'已关闭'}}</span>
          <span class="tips">设置前端是否隐藏未填写心得的评价</span>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showReviewSet = false">{{
          $t("evaluation.cancel")
        }}</el-button>
        <el-button type="primary" @click="confirmReviewSet">{{
          $t("evaluation.confirm")
        }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getCommentList,
  goodsCommentDelete,
  CommentPass,
  CommentRefuse,
  CommentAnswer,
  delAnswer,
  getCommentConfig,
  getCommentSwitch,
  CommentCheckConfig,
  CommentSwitchConfig,
  setTop,
  cancelTop
} from '@/api/admin/goodsManage/evaluationManagement/evaluationManagement'
import { getEvaluationGiftList } from '@/api/admin/marketManage/evaluationGift.js'
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
        flag: -1,
        isTop: 0
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
        { type: -1, value: '全部' },
        { type: 1, value: '积分' },
        { type: 15, value: '优惠券' },
        { type: 3, value: '余额' },
        { type: 4, value: '幸运大抽奖' },
        { type: 5, value: '自定义' }
      ],
      auditFlag: [
        { key: -1, value: '全部' },
        { key: 0, value: '待审核' },
        { key: 1, value: '已通过' },
        { key: 2, value: '未通过' }
      ],
      tabDefaultStatus: '0',
      tabsStatus: [
        { value: '0', label: '全部' },
        { value: '1', label: '置顶评论' }
      ],
      pageParams: {},
      dataList: [],
      loading: false,
      showReply: false,
      showReviewSet: false,
      replyContent: null,
      replyId: null,
      shopHelperParams: {},
      reviewStatus: null,
      hideEvaluation: null,
      dialogReviewStatus: 0,
      dialogHideEvaluation: false
    }
  },
  mounted () {
    if (this.$route.query.award_activity_id) {
      this.$set(
        this.searchParams,
        'awardActivityId',
        this.$route.query.award_activity_id
      )
    }
    if (this.$route.query.orderSn) {
      this.$set(this.searchParams, 'orderSn', this.$route.query.orderSn)
    }
    if (this.$route.params.flag) {
      this.$set(this.shopHelperParams, 'shopAssistantFlag', this.$route.params.flag)
    }
    if (this.$route.params.IntegerDays) {
      this.$set(this.shopHelperParams, 'nDays', this.$route.params.IntegerDays)
    }
    if (this.target === 'Record') {
      getCommentConfig().then(res => {
        console.log(res)
        if (res.error === 0) {
          this.reviewStatus = res.content
        }
      })
      getCommentSwitch().then(res => {
        if (res.error === 0) {
          this.hideEvaluation = !!res.content
        }
      })
    }
    this.initDataList()
    this.langDefault()
  },
  methods: {
    // 初始化列表 评价列表 待评价列表
    initDataList () {
      this.loading = true
      let obj = {
        ...this.searchParams,
        page: { ...this.pageParams },
        awardActivityId:
          this.searchParams.awardActivityId === -1
            ? null
            : this.searchParams.awardActivityId,
        ...this.shopHelperParams
      }
      if (this.target !== 'Record') {
        delete obj.flag
      }
      getCommentList(obj).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.dataList = res.content.dataList.map(item => {
            let commImg = null
            try {
              commImg = JSON.parse(item.commImg)
            } catch (error) {
              commImg = item.commImg ? item.commImg.split(',') : []
            }
            if (commImg.length > 0) {
              commImg = commImg.map(item => {
                return this.$imageHost + '/' + item
              })
            }
            return { ...item, commImg }
          })
          // this.dataList = res.content.dataList
          console.log(this.dataList)
          this.loading = false
        }
      })
      getEvaluationGiftList({navType: 0, pageRows: 300}).then(res => {
        if (res.error === 0) {
          let dataList = res.content.dataList.map(item => {
            return {id: item.id, name: item.name}
          })
          dataList.unshift({id: -1, name: '全部'})
          console.log(dataList)
          this.evaluationRewardList = dataList
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
    handleClick (data) {
      console.log(data)
      this.tabDefaultStatus = String(data.name)
      this.searchParams.isTop = data.name === '1' ? 1 : 0
      this.pageParams.currentPage = 1
      this.initDataList()
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
    confirmReviewSet () {
      CommentCheckConfig({ v: this.dialogReviewStatus }).then(res => {
        if (res.error === 0) {
          this.reviewStatus = this.dialogReviewStatus
          this.$message.success('修改成功')
        }
      })
      CommentSwitchConfig({ v: this.dialogHideEvaluation ? 1 : 0 }).then(res => {
        this.hideEvaluation = this.dialogHideEvaluation
        if (res.error === 0) {
          this.$message.success('修改成功')
        }
      })
      this.pageParams.currentPage = 1
      this.initDataList()
      this.showReviewSet = false
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
    },
    evaluationTop (id) {
      setTop({id}).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success({
            message: '置顶成功',
            duration: '2000'
          })
          let targetData = this.dataList.find(item => item.id === id)
          targetData.isTop = 1
        }
      })
    },
    evaluationCancelTop (id) {
      cancelTop({id}).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success({
            message: '已取消',
            duration: '2000'
          })
          let targetData = this.dataList.find(item => item.id === id)
          targetData.isTop = 0
        }
      })
    },
    // 去用户中心
    goUserCenter (id) {
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId: id
        }
      })
    },
    handleShowReviewDialog () {
      this.showReviewSet = true
      this.dialogReviewStatus = this.reviewStatus
      this.dialogHideEvaluation = this.hideEvaluation
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
    },
    awardType (type) {
      switch (type) {
        case 1:
          return `积分`
        case 2:
          return `优惠券`
        case 3:
          return `余额`
        case 4:
          return `幸运大抽奖`
        case 5:
          return `自定义`
        default:
          return '无'
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.el-row {
  padding-left: 10px;
}
.el-col {
  margin-bottom: 10px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.mini_select {
  width: 170px !important;
}
.filters_item {
  > span {
    font-size: 14px;
  }
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
.filter-status{
  /deep/ .el-tabs__nav-wrap::after{
    content:none;
  }
  /deep/ .el-col{
    margin-bottom: 0;
  }
  /deep/ .audit-button{
    padding-right: 10px;
    .el-button--small{
      float: right;
      margin-top: 5px;
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
      cursor: pointer;
    }
  }
  .fake_user {
    > span {
      color: #666;
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
.evaluation-pic {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  margin-top: -6px;
  /deep/ .el-image {
    margin-top: 6px;
    margin-left: 14px;
    &.el-image:nth-child(3n + 1) {
      margin-left: 0;
    }
  }
}
.reviewSetInfo{
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}
.reviewSetInfo > div+div{
  margin-top: 20px;
}
</style>
