<template>

  <el-form
    ref="form"
    :model="form"
    label-width="100px"
  >
    <el-form-item label="分销开关">
      <el-switch v-model="form.delivery"></el-switch>
      <div class="text">
        开关默认关闭，开启开关，则用户可以申请为店铺分销员，分销员邀请用户注册产生订单，购买者邀请人可获得佣金奖励。关闭开关，手机端个人中心”分销中心“菜单隐藏，用户下单，邀请人不再产生佣金奖励，系统分销机制关闭，邀请不再记录邀请关系。
      </div>
    </el-form-item>

    <el-form-item label="分销员审核">
      <el-switch v-model="form.delivery"></el-switch>
      <span>若开启审核，您需要配置推广文案内容） <span>推广文案配置</span></span>
      <div class="text">
        开启分销员审核功能后，普通用户申请成为分销员时需要经过商家审核。关闭则成为店铺分销员不需要申请审核，全部用户均默认为店铺分销员。
      </div>
      <template>
        <el-checkbox
          :indeterminate="isIndeterminate"
          v-model="checkAll"
          @change="handleCheckAllChange"
        >全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group
          v-model="checkedCities"
          @change="handleCheckedCitiesChange"
        >
          <el-checkbox
            v-for="city in cities"
            :label="city"
            :key="city"
          >{{city}}</el-checkbox>
        </el-checkbox-group>
      </template>
    </el-form-item>

    <el-form-item label="分销员排名">
      <el-switch v-model="form.delivery"></el-switch>
      <div class="text">
        开关默认关闭，开启开关，且拥有返利数据的分销员数大于等于3位时分销员中心显示分销员佣金排名。关闭则不显示分销员佣金排名页面。
      </div>
    </el-form-item>

    <el-form-item label="返利有效期">
      <!-- <div style="display: flex; height:30px; line-height: 30px"> -->
      <el-radio
        v-model="radio"
        label="1"
      >
      </el-radio>
      <!-- <el-input
            style="width: 200px;"
            size="mini"
          ></el-input> -->
      <!-- </div> -->
      <el-radio
        v-model="radio"
        label="2"
      >永久</el-radio>
      <div class="text">用户被分销员邀请注册开始计算，在该天数限制内该用户购买分销商品给分销员计算佣金返利，一旦超过该天数，则不再给分销员佣金返利，默认为空，为空表示不限制。</div>
    </el-form-item>

    <el-form-item label="返利有效期">
      <!-- <div style="display: flex; height:30px; line-height: 30px"> -->
      <el-radio
        v-model="radio"
        label="1"
      >
      </el-radio>
      <!-- <el-input
            style="width: 200px;"
            size="mini"
          ></el-input> -->
      <!-- </div> -->
      <el-radio
        v-model="radio"
        label="2"
      >永久</el-radio>
      <div class="text">用户被分销员邀请注册开始计算，在该天数限制内该用户购买分销商品给分销员计算佣金返利，一旦超过该天数，则不再给分销员佣金返利，默认为空，为空表示不限制。</div>
    </el-form-item>
    <el-form-item label="分销员保护期">
      <el-radio
        v-model="radio"
        label="1"
      >
      </el-radio>
      <el-radio
        v-model="radio"
        label="2"
      >永久</el-radio>
      <div class="text">在保护期内，分销员发展的客户不会变更绑定关系，保护期过后可通过分享链接重新绑定邀请关系。 超过保护期若未重新建立邀请关系，则原绑定关系仍然有效，可依据返利配置条件返利。若保护期设置为0天，则用户可随时通过他人分享进入小程序实现其邀请人的变更。</div>
    </el-form-item>
    <el-form-item label="分销中心页面名称">
      <el-input style="width: 200px"></el-input>
    </el-form-item>
    <el-form-item label="推荐商品">
      <el-radio
        v-model="recommend"
        label="1"
      >不显示</el-radio>
      <el-radio
        v-model="recommend"
        label="2"
      >默认</el-radio>
      <el-radio
        v-model="recommend"
        label="3"
      >自定义</el-radio>
      <div class="text">将从已选商品中随机抽取10个展示在小程序端“分销中心”，引导分销员推广商品</div>
      <div style="background: #ccc; width:120px;height: 30px;line-height: 30px; text-align: center;">+选择商品</div>
      <!-- <el-table
        :data="tableData"
        border
        style="width: 19%"
      >
        <el-table-column
          prop="goodsName"
          label="商品名称"
          width="150"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="price"
          label="价格"
          width="80"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="leftNumber"
          label="库存"
          width="80"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="operate"
          label="操作"
          width="80"
          align="center"
        >
        </el-table-column>
      </el-table> -->
    </el-form-item>
    <el-form-item label="自定义内容：">
      <span class="selectTemplate">选择模板</span>
      <span>刷新</span>
      <span>添加模板</span>
    </el-form-item>

    <el-collapse v-model="activeNames">
      <el-collapse-item
        title="展开更多配置"
        name="1"
      >
        <div style="width: 100%;height: 40px; line-height:40px;background:#f8f8f8">返利提现设置</div>
        <el-form-item label="返利提现开关">
          <el-switch v-model="form.delivery"></el-switch>
          <span>注：开启提现功能开关前，请阅读 </span>
          <span>《返利提现配置操作说明》</span>
          <div class="text">开关开启，分销员推广返利获得的佣金可提现到微信钱包，分销员在小程序发起返利申请，需后台审核通过才可提现到账 </div>
          <div>
            <el-radio
              v-model="recommend"
              label="1"
            >
              小程序
              <div>
                注意：使用返利提现功能，请确保小程序已开通微信支付，否则不可提现
                <span style="color: blue">去配置</span>
              </div>
            </el-radio>
          </div>
          <div>
            <el-radio
              v-model="recommend"
              label="2"
            >
              公众号
              <div>
                注意：使用返利提现功能，请确保小程序已绑定认证服务号并配置相关支付信息，否则不可提现，未关注公众号的用户将会提现失败
                <span style="color: blue">去配置</span>
              </div>
            </el-radio>
          </div>
        </el-form-item>

        <el-form-item label="返利最小提现金额">
          <el-input
            style="width:100px; margin-right:5px"
            size="small"
          ></el-input>元
          <div>分销员发起返利提现，单次申请最小提现金额。为防止分销员提现过于频繁，请设置单次最小提现金额。</div>
        </el-form-item>

        <div style="width: 100%;height: 40px; line-height:40px;background:#f8f8f8">分销中心推广海报背景图</div>

      </el-collapse-item>
    </el-collapse>

  </el-form>
</template>

<script>
const cityOptions = ['真实姓名', '手机号', '身份证号码', '性别', '生日', '婚姻状况', '教育程度', '所在行业', '所在地', '备注', '图片上传']
export default {
  data () {
    return {
      value1: true,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      checkAll: false,
      checkedCities: ['上海', '北京'],
      cities: cityOptions,
      isIndeterminate: true,
      radio: '1',
      recommend: '1',
      activeNames: ['1']
      // tableData: [{
      //   goodsName: '苹果手机',
      //   price: '4299',
      //   leftNumber: ' 198',
      //   operate: '删除'
      // }, {
      //   goodsName: '苹果手机',
      //   price: '4299',
      //   leftNumber: ' 198',
      //   operate: '删除'
      // }, {
      //   goodsName: '苹果手机',
      //   price: '4299',
      //   leftNumber: ' 198',
      //   operate: '删除'
      // }, {
      //   goodsName: '苹果手机',
      //   price: '4299',
      //   leftNumber: ' 198',
      //   operate: '删除'
      // }]
    }
  },
  methods: {
    handleCheckAllChange (val) {
      this.checkedCities = val ? cityOptions : []
      this.isIndeterminate = false
    },
    handleCheckedCitiesChange (value) {
      let checkedCount = value.length
      this.checkAll = checkedCount === this.cities.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length
    }
  }

}

</script>
<style scoped>
</style>
