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
      <div>用户被分销员邀请注册开始计算，在该天数限制内该用户购买分销商品给分销员计算佣金返利，一旦超过该天数，则不再给分销员佣金返利，默认为空，为空表示不限制。</div>
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
      <div>用户被分销员邀请注册开始计算，在该天数限制内该用户购买分销商品给分销员计算佣金返利，一旦超过该天数，则不再给分销员佣金返利，默认为空，为空表示不限制。</div>
    </el-form-item>

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
      radio: '1'
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
