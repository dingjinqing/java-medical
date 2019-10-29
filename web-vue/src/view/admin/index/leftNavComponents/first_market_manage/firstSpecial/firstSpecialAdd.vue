<template>
  <div class="content first-special-add-page">
    <div class="main">
      <div class="nav_list">
        <el-tabs
          v-model="form.activeName"
          @tab-click="handleClick"
        >
          <el-tab-pane
            label="全部首单特惠活动"
            name="0"
          ></el-tab-pane>
          <el-tab-pane
            label="进行中"
            name="1"
          ></el-tab-pane>
          <el-tab-pane
            label="未开始"
            name="2"
          ></el-tab-pane>
          <el-tab-pane
            label="已过期"
            name="3"
          ></el-tab-pane>
          <el-tab-pane
            label="已停用"
            name="4"
          ></el-tab-pane>
          <el-tab-pane
            label="添加首单特惠活动"
            name="5"
          ></el-tab-pane>
        </el-tabs>
      </div>
      <div class="first-special-add-content">
        <div class="info-top">
          <h3>说明</h3>
          <ol>
            <li>1.首单特惠价格仅“未在店铺内支付购买过任何商品”的用户可见，老用户查看首单特惠商品时将显示商品的当前价格。</li>
            <li>2.首单特惠活动建议与“开屏有礼-自定义”活动配合开展</li>
          </ol>
        </div>
        <el-form
          ref="firstSpecialAddForm"
          label-width="115px"
          size="small"
        >
          <el-form-item label="活动名称：">
            <el-input
              class="form_input"
              v-model="form.activeName"
            ></el-input>
            <p class="form_tip">只作为商家记录使用，用户不会看到这个名称</p>
          </el-form-item>
          <el-form-item
            class="clearfix"
            label="有效期："
          >
            <div
              class="fl"
              style="width: 95px;"
            >
              <el-radio-group v-model="form.datetype">
                <el-radio
                  label="0"
                  style="line-height:32px;"
                >固定时间</el-radio>
                <el-radio
                  label="1"
                  style="line-height:32px;"
                >永久有效</el-radio>
              </el-radio-group>
            </div>
            <div class="fl">
              <el-date-picker
                v-model="form.startTime"
                type="datetime"
                class="form_input"
                placeholder="选择日期时间"
              >
              </el-date-picker>
              至
              <el-date-picker
                v-model="form.endTime"
                type="datetime"
                class="form_input"
                placeholder="选择日期时间"
              >
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item label="活动优先级：">
            <el-input-number v-model="form.first"></el-input-number>
            <p class="form_tip">用于区分不同首单特惠活动的优先级，请填写正整数，数值越大优先级越高</p>
          </el-form-item>
          <el-form-item
            label="限购数量："
            style="width:95px;"
          >
            <el-radio-group v-model="form.limit">
              <el-radio
                :label="0"
                style="line-height:32px;"
              >不限制</el-radio>
              <el-radio
                :label="1"
                style="line-height:32px;"
              >
                限制数量
                <el-input-number
                  v-model="form.limitAmount"
                  style="margin-left: 10px;"
                ></el-input-number>
              </el-radio>
            </el-radio-group>
            <el-checkbox
              v-model="form.limitP"
              style="margin-left: 30px;"
            >超出限购数量后，买家不可继续添加购买该商品</el-checkbox>
          </el-form-item>
          <el-form-item
            label="活动商品："
            required
          >
            <el-button @click="selectGoodsHandle">+选择商品</el-button>
            <p class="form_tip">最多选择100个商品</p>
          </el-form-item>
          <!-- 设置商品首单优惠 -->
          <div
            class="add_content"
            v-if="tableData.length > 0"
          >
            <div class="table_head clearfix">
              <el-form-item
                class="table_head_setting fl"
                label-width="115px"
                label="设置折扣："
                required
              >
                <el-radio-group
                  class="setting_group"
                  v-model="form.setting"
                >
                  <el-radio label="0">
                    <span>
                      批量打<el-input class="num_input"></el-input>折
                    </span>
                  </el-radio>
                  <el-radio label="1">
                    <span>
                      批量减价<el-input class="num_input"></el-input>元
                    </span>
                  </el-radio>
                  <el-radio label="2">
                    <span>
                      批量首单价<el-input class="num_input"></el-input>元
                    </span>
                  </el-radio>
                </el-radio-group>
                <el-button
                  type="primary"
                  style="margin-left:20px;"
                >确定</el-button>
                <el-button>取消</el-button>
              </el-form-item>
              <div class="fr">
                <el-button type="text">批量删除</el-button>
                <el-button type="text">批量价格取整</el-button>
              </div>
            </div>
            <el-table
              :data="tableData"
              style="width:100%;"
              :header-cell-style="{
                'background-color':'#f5f5f5',
                'border':'none'
              }"
            >
              <el-table-column type="selection"></el-table-column>
              <el-table-column
                label="商品名称"
                prop="goodsName"
              ></el-table-column>
              <el-table-column
                label="原价"
                prop="shopPrice"
              ></el-table-column>
              <el-table-column
                label="库存"
                prop="goodsNumber"
              ></el-table-column>
              <el-table-column label="折扣">
                <template slot-scope="{row}">
                  <el-input
                    style="width:50px;"
                    size="small"
                    v-model="row.discount"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column label="减价">
                <template slot-scope="{row}">
                  <el-input
                    style="width:50px;"
                    size="small"
                    v-model="row.reducePrice"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column label="首单价">
                <template slot-scope="{row}">
                  <el-input
                    style="width:50px;"
                    size="small"
                    v-model="row.goodsPrice"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                label="操作"
                align="right"
              >
                <template>
                  <div style="align: right;">
                    <span
                      class="iconSpan"
                      style="font-size:14px;"
                      @click="deleteGood(row)"
                    >删除</span>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <el-button type="text">收起更多配置</el-button>
          <div class="more-setting">
            <el-form-item label="活动分享：">
              <el-radio-group>
                <el-radio>
                  默认样式
                  <!-- <router-link>查看示例</router-link> -->
                  <a
                    href="javascript:void(0);"
                    download
                  >下载海报</a>
                </el-radio>
                <el-radio>
                  <p></p>
                  自定义样式
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </div>
    <!--商品选择-->
    <choosingGoods
      @resultGoodsDatas="resultGoodsDatas"
      :tuneUpChooseGoods="tuneUpChooseGoods"
      :chooseGoodsBack="goodsIdList"
    />
  </div>
</template>

<script>
import choosingGoods from '@/components/admin/choosingGoods'
export default {
  components: { choosingGoods },
  data () {
    return {
      form: {
        activeName: '',
        name: '',
        isForever: 0,
        startTime: '',
        endTime: '',
        first: '',
        limit: 0,
        limitAmount: 1,
        limitP: false,
        firstSpecialGoodsParams: [], // 改价的商品数组
        batchDiscount: 10, // 批量打几折
        batchReduce: 0, // 批量减多少
        batchFinalPrice: '', // 批量折后价
        isBatchInteger: false, // 是否批量取整
        shareConfig: {}, // 分享设置
        setting: 0 // 批量优惠类型
      },
      tuneUpChooseGoods: false,
      goodsIdList: [],
      tableData: [],
      selectGoods: []
    }
  },
  methods: {
    handleClick () {

    },
    selectGoodsHandle () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    // 选择商品后，展示商品列表
    resultGoodsDatas (datas) {
      console.log(datas)
      if (datas.length) {
        this.tableData = datas
      }
    },
    deleteGood (good) {
      console.log(good)
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
  }
  .form_input {
    width: 165px;
  }
  .form_tip {
    display: inline-block;
    color: #999;
  }
  .clearfix:after {
    content: "";
    display: block;
    height: 0;
    clear: both;
  }
  .fl {
    float: left;
  }
  .fr {
    float: right;
  }
  .num_input {
    width: 50px;
  }
  .iconSpan {
    font-size: 20px;
    color: #5a8bff;
    cursor: pointer !important;
  }
}
.first-special-add-page {
  .first-special-add-content {
    .info-top {
      padding: 10px 15px;
      background: #f5f5f5;
      margin-bottom: 15px;
      h3 {
        font-size: 16px;
        color: #333;
        font-weight: bold;
        margin-bottom: 10px;
      }
      li {
        font-size: 13px;
        margin-bottom: 5px;
      }
    }
    .add_content {
      border: 1px solid #eee;
      .table_head {
        position: relative;
        height: 60px;
        line-height: 60px;
        .table_head_setting {
          position: absolute;
          top: 50%;
          transform: translateY(-50%);
          margin-bottom: 0;
        }
      }
    }
  }
}
</style>
