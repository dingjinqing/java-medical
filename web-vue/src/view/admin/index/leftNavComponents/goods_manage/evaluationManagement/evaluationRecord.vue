<template>
  <div class="evaluationRecordContent">
    <div class="table_box">
      <div class="filters">
        <div class="filters_item"><span>订单编号</span>
          <el-input
            v-model="searchParams.orderSn"
            placeholder="输入订单编号"
            size="small"
          ></el-input>
        </div>
        <div class="filters_item"><span>商品名称</span>
          <el-input
            v-model="searchParams.goodsName"
            placeholder="输入商品名称"
            size="small"
          ></el-input>
        </div>
        <div class="filters_item"><span>会员手机号</span>
          <el-input
            v-model="searchParams.mobile"
            placeholder="输入手机号"
            size="small"
          ></el-input>
        </div>
        <div class="filters_item"><span>评价星级</span>
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
        <div class="filters_item"><span>评价奖励</span>
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
        <el-table-column label="商品信息">
          <template slot-scope="scope">
            <div class="goods_info">
              <img
                :src="$imageHost+scope.row.goodsImg"
                alt=""
              >
              <div class="right_info">
                <div class="goods_name">{{scope.row.goodsName}}</div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="用户信息"
          width="170"
        >
          <template slot-scope="scope">
            <div class="user_info">
              <p class="user_name">用户名：<span>{{scope.row.username}}</span></p>
              <p>手机号：{{scope.row.mobile}}</p>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="评价内容"
          width="200"
        >
          <template slot-scope="scope">
            <div class="evaluation-info">
              <div class="evaluation-info_item"><span class="evaluation-info_title">评分：</span><span><i
                    class="el-icon-star-on"
                    v-for="index in scope.row.commstar"
                    :key="index"
                  ></i></span></div>
              <div class="evaluation-info_item"><span class="evaluation-info_title">评价：</span><span>{{scope.row.commNote}}</span></div>
              <div class="evaluation-info_item"><span></span></div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="评论回复">
          <template slot-scope="scope">
            <div class="evaluation_response">
              <span v-if="scope.row.content">回复：{{scope.row.content}}</span>
              <el-button
                type="primary"
                v-if="!scope.row.content"
                size="mini"
              >编写回复</el-button>
              <el-button
                type="default"
                v-else
                size="mini"
              >删除回复</el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="评价时间"
          prop="createTime"
          width="100"
        ></el-table-column>
        <el-table-column
          label="匿名评价"
          width="80"
        >
          <template slot-scope="scope">
            <span>{{scope.row.anonymousflag ? '是' : '否'}}</span>
          </template>
        </el-table-column>
        <el-table-column label="评价奖励">
          <template slot-scope="scope">
            <span>{{scope.row.lotteryAward ? scope.row.lotteryAward : '无'}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="120"
        >
          <template slot-scope="scope">
            <el-button
              type="default"
              :value="scope.row.id"
              size="mini"
            >删除评价</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      searchParams: {
        commstar: -1,
        chooseReward: -1,
        orderSn: null,
        goodsName: null,
        mobile: null
      },
      starLevel: [
        { key: -1, value: '全部' },
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
      dataList: [
        {
          'id': 3,
          'orderSn': 'P201903281652376611',
          'commstar': 4,
          'commNote': '味道很好闻！',
          'content': '',
          'createTime': '2019-07-08 15:11:07',
          'goodsName': '化妆品化妆品化妆品化妆品化妆品',
          'goodsImg': '/image/admin/head_icon.png',
          'username': '张三',
          'mobile': '13502085563',
          'anonymousflag': 1,
          'lotteryAward': '神秘奖品',
          'flag': 1
        },
        {
          'id': 4,
          'orderSn': 'P201903281652376611',
          'commstar': 4,
          'commNote': '味道很好闻！',
          'content': 'test agin',
          'createTime': '2019-07-08 15:11:07',
          'goodsName': '化妆品化妆品化妆品化妆品化妆品',
          'goodsImg': '/image/admin/head_icon.png',
          'username': '张三',
          'mobile': '13502085563',
          'anonymousflag': 1,
          'flag': 1
        },
        {
          'id': 5,
          'orderSn': 'P201903281652376611',
          'commstar': 4,
          'commNote': '味道很好闻！',
          'content': 'test agin',
          'createTime': '2019-07-08 15:11:07',
          'goodsName': '化妆品化妆品化妆品化妆品化妆品化妆品化妆品',
          'goodsImg': '/image/admin/head_icon.png',
          'username': '张三',
          'mobile': '13502085563',
          'anonymousflag': 1,
          'lotteryAward': '神秘奖品',
          'flag': 2
        },
        {
          'id': 6,
          'orderSn': 'P201903281652376611',
          'commstar': 4,
          'commNote': '味道很好闻！',
          'content': 'test agintest agintest agintest agintest agintest agin',
          'createTime': '2019-07-08 15:11:07',
          'goodsName': '化妆品',
          'goodsImg': '/image/admin/head_icon.png',
          'username': '张三',
          'mobile': '13502085563',
          'anonymousflag': 1,
          'lotteryAward': '神秘奖品',
          'flag': 0
        }
      ],
      loading: false
    }
  },
  methods: {
    initDataList () {

    }
  }
}
</script>

<style lang="scss" scoped>
.mini_select {
  width: 80px;
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
        width: 42px;
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
</style>
