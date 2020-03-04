<template>
  <div class="content">
    <el-form
      ref="form"
      :model="param"
      label-width="130px"
      labelPosition="right"
    >
      <el-form-item label="活动类型：">
        <!-- <el-radio-group v-model="param.actType"> -->
        <div>
          <el-radio
            :label=1
            v-model="param.actType"
          >
            一口价结算
          </el-radio>
          <span>用户选择多件商品，以商家设置的结算价格进行打包购买</span>
        </div>
        <div>
          <el-radio
            :label=2
            v-model="param.actType"
          >指定折扣结算</el-radio>
          <span>用户选择多件商品，以商家设置的折扣进行打包购买</span>
        </div>
        <!-- </el-radio-group> -->
      </el-form-item>

      <el-form-item label="活动名称：">
        <el-input
          size="small"
          style="width: 150px"
          v-model="param.packageName"
        ></el-input>
        <span>只作为商家记录使用，用户不会看到这个名称</span>
      </el-form-item>

      <el-form-item label="活动时间：">
        <el-date-picker
          v-model="param.validity"
          type="datetimerange"
          value-format="yyyy-MM-dd HH:mm:ss"
          format="yyyy-MM-dd HH:mm:ss"
          :range-separator="$t('shipping.to')"
          :start-placeholder="$t('ordinaryCoupon.startTime')"
          :end-placeholder="$t('ordinaryCoupon.endTime')"
          :default-time="['00:00:00','23:59:59']"
          size="small"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item label="活动规则：">
        <section>
          <div>最多可配置3个商品组，买家在每组商品中分别选择指定件数，即可以“结算总价格”结算</div>
          <div>
            例如：商家可设置3个商品组，买家需在每个商品组中选择1件，3组共3件以总价100元结算
            <el-popover
              placement="right-start"
              width="220"
              trigger="hover"
            >
              <el-image :src="srcList.src1"></el-image>
              <el-button
                slot="reference"
                type="text"
              ></el-button>
            </el-popover>
          </div>
        </section>
      </el-form-item>

      <el-form-item label="结算总价格：">
        <el-input
          size="small"
          style="width: 150px"
          v-model="param.totalMoney"
        ></el-input>&nbsp;&nbsp;元
      </el-form-item>

      <el-form-item label="商品组：">
        <section style="display: flex">
          <el-checkbox v-model="param.group1">商品组1</el-checkbox>
          <div
            style="margin-left:20px"
            v-if="param.group1 === true"
          >
            <div>
              <span>名称</span>
              <el-input
                size="small"
                style="width: 80px"
                v-model="param.groupName"
              ></el-input>
              <span>商品组名称，最多可填4个字</span>
            </div>
            <div>
              <span>需选择</span>
              <el-input
                size="small"
                style="width: 80px"
                v-model="param.goodsNumber"
              ></el-input>&nbsp;件
              <span>该商品组需要选购的商品数量，请填写正整数</span>
            </div>
          </div>
        </section>
      </el-form-item>

      <el-form-item label="添加商品：">
        <div>
          <span>请给每个商品组分别添加商品</span>
          <div class="goods-area">
            <div
              style="display: flex"
              class="goods-num"
            >
              <div>商品组1</div>
              <div v-if="param.group2 === true">商品组2</div>
              <div v-if="param.group3 === true">商品组3</div>
            </div>
            <div
              class="add-btn"
              @click="addGoods"
            >+ 添加商品</div>
            <div>
              <table class="add-goods">
                <thead>
                  <tr>
                    <th width="45%">商品名称</th>
                    <th width="15%">价格</th>
                    <th width="15%">库存</th>
                    <th width="15%">操作</th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="(item, index) in goodsList"
                    :key="item.name"
                  >
                    <td>
                      <div class="goods-info">
                        <div class="goods-img">
                          <img
                            :src="item.goodsImg"
                            alt=""
                          >
                        </div>
                        <div class="goods-name">{{item.goodsName}}</div>
                      </div>
                    </td>
                    <td>￥{{item.shopPrice}}</td>
                    <td>{{item.goodsNumber}}</td>
                    <td @click="deleteGoods(index)">
                      <span>删除</span>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="add-btn">+ 添加平台分类</div>
            <div class="add-btn">+ 添加商家分类</div>
          </div>
        </div>
      </el-form-item>
    </el-form>

    <!-- 选择商品弹窗 -->
    <ChoosingGoods :tuneUpChooseGoods='tuneUpChooseGoodsDialog' />

  </div>
</template>

<script>
import ChoosingGoods from '@/components/admin/choosingGoods'

export default {
  components: { ChoosingGoods },
  data () {
    return {
      param: {
        actType: 1, // 活动类型
        packageName: '', // 活动名称
        validity: '', // 活动时间
        startTime: '',
        endTime: '',
        totalMoney: '', // 总结算价格
        group1: '',
        group2: false,
        group3: false,
        groupName: '', // 商品组名称
        goodsNumber: '' // 至少需要选择件数
      },
      srcList: {
        src1: `${this.imageHost}//image/admin/new_preview_image/packagesale.jp`
      },
      // 选择商品
      tuneUpChooseGoodsDialog: false,
      goodsList: []
    }
  },
  methods: {
    addGoods () {
      this.tuneUpChooseGoodsDialog = !this.tuneUpChooseGoodsDialog
    }
  }
}

</script>
<style lang="scss" scoped>
.content {
  margin-top: 10px;
  padding: 15px;
  background: #fff;
  .goods-area {
    border: 1px solid #ccc;
    width: 600px;
    .goods-num {
      height: 30px;
      line-height: 30px;
      border-bottom: 1px solid #ccc;
      background: #f5f5f5;
      div {
        width: 70px;
        border-right: 1px solid #ccc;
        background: #fff;
        text-align: center;
      }
    }
    .add-btn {
      width: 120px;
      height: 30px;
      line-height: 30px;
      text-align: center;
      color: #5a8bff;
      border: 1px solid #ccc;
      background: #fff;
      cursor: pointer;
      margin: 10px 0 10px 10px;
    }
    .add-goods {
      border: 1px solid #ddd;
      margin-left: 10px;
      thead tr {
        background: #f8f8f8;
        font-weight: bold;
        color: #333;
        text-align: center;
        th {
          border: 1px solid #ddd;
        }
      }
      tbody tr {
        text-align: center;
        td {
          padding: 10px;
          border: 1px solid #ddd;
          .goods-info {
            display: flex;
            .goods-img {
              width: 40px;
              height: 40px;
              margin-right: 10px;
              img {
                width: 100%;
                height: 100%;
              }
            }
          }
        }
      }
    }
  }
}
</style>
