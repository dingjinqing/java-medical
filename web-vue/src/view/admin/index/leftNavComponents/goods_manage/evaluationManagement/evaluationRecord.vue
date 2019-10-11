<template>
  <div class="evaluationRecordContent">
    <div class="table_box">
      <div class="filters">
        <div class="filters_item"><span>{{$t('evaluation.orderSn')}}</span>
          <el-input
            v-model="searchParams.orderSn"
            placeholder="输入订单编号"
            size="small"
          ></el-input>
        </div>
        <div class="filters_item"><span>{{$t('evaluation.goodsName')}}</span>
          <el-input
            v-model="searchParams.goodsName"
            placeholder="输入商品名称"
            size="small"
          ></el-input>
        </div>
        <div class="filters_item"><span>{{$t('evaluation.mobile')}}</span>
          <el-input
            v-model="searchParams.mobile"
            placeholder="输入手机号"
            size="small"
          ></el-input>
        </div>
        <div class="filters_item"><span>{{$t('evaluation.evaluationGrade')}}</span>
          <el-select
            v-model="searchParams.commstar"
            size="small"
            class="mini_select"
          >
            <el-option
              v-for="item in starLevel"
              :key="item.key"
              :label="item.value"
              :value="item.key"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item"><span>{{$t('evaluation.evaluationTable.evaluationReward')}}</span>
          <el-select
            v-model="searchParams.chooseReward"
            size="small"
            class="mini_select"
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
      <el-table
        v-loading="loading"
        :data="dataList"
        style="width:100%;"
        border
        :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
        :cell-style="{
            'text-align':'center'
          }"
      >
        <el-table-column
          type="selection"
          width="55"
        >
        </el-table-column>
        <el-table-column :label="$t('evaluation.evaluationTable.productInformation')">
          <template slot-scope="scope">
            <div class="orderSn">
              <span v-if="scope.row.bogusUsername">商家添加评价</span>
              <span v-else>{{$t('evaluation.orderSn')}}：<span>{{scope.row.orderSn}}</span></span>
            </div>
            <div class="goods_info">
              <img
                :src="$imageHost+'/'+scope.row.goodsImg"
                alt=""
              >
              <div class="right_info">
                <div class="goods_name">{{scope.row.goodsName}}</div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.userInfo')"
          width="170"
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
          width="200"
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
        <el-table-column :label="$t('evaluation.evaluationTable.evaluationReply')">
          <template slot-scope="scope">
            <div class="evaluation_response">
              <span v-if="scope.row.content">{{$t('evaluation.reply')}}：{{scope.row.content}}</span>
              <el-button
                type="primary"
                v-if="!scope.row.content"
                size="mini"
              >{{$t('evaluation.writeReply')}}</el-button>
              <el-button
                type="default"
                v-else
                size="mini"
              >{{$t('evaluation.deleteReply')}}</el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.evaluationTime')"
          prop="createTime"
          width="100"
        ></el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.anonymousEvaluation')"
          width="80"
        >
          <template slot-scope="scope">
            <span>{{scope.row.anonymousflag ? $t('evaluation.yes') : $t('evaluation.no')}}</span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('evaluation.evaluationTable.evaluationReward')">
          <template slot-scope="scope">
            <span>{{scope.row.lotteryAward ? scope.row.lotteryAward : $t('evaluation.null')}}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('evaluation.evaluationTable.operating')"
          width="150"
        >
          <template slot-scope="scope">
            <el-button
              type="default"
              size="mini"
              @click="delEvaluation(scope.row.id)"
            >{{$t('evaluation.deleteEvaluation')}}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination
      :page-params.sync="pageParams"
      @pagination="initDataList"
    />
  </div>
</template>

<script>
import { getCommentList, goodsCommentDelete } from '@/api/admin/goodsManage/evaluationManagement/evaluationManagement'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      searchParams: {
        commstar: 0,
        chooseReward: -1,
        orderSn: null,
        goodsName: null,
        mobile: null
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
      pageParams: {
      },
      dataList: [
      ],
      loading: false
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    initDataList () {
      let obj = {
        ...this.searchParams,
        page: { ...this.pageParams },
        chooseReward: this.searchParams.chooseReward === -1 ? null : this.searchParams.chooseReward
      }
      getCommentList(obj).then(res => {
        this.pageParams = res.content.page
        this.dataList = res.content.dataList.sort((a, b) => {
          return (b.id - a.id)
        })
      })
    },
    delEvaluation (id) {
      goodsCommentDelete({ id: id }).then(res => {
        if (res.error === 0) {
          this.dataList = this.dataList.filter(item => item.id !== id)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
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
        color: red;
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
.orderSn {
  text-align: left;
  span {
    display: inline-block;
  }
}
</style>
