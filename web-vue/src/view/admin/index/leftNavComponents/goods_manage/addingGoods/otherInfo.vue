<template>
  <!-- 其他信息 -->
  <div class="otherInfo">
    <el-form
      :model="formData"
      :rules="rules"
      ref="form"
      label-width="130px"
      class="other"
    >
      <el-form-item label="会员专享商品：">
        <el-checkbox v-model="checked">用户持有会员卡才可以购买此商品</el-checkbox>
      </el-form-item>
      <el-form-item
        label=""
        class="isCard"
        v-show="checked"
      >
        <el-select
          multiple
          size="small"
          v-model="value"
          placeholder="请选择会员卡"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <span class="el-link">
          <el-link
            type="primary"
            class="link"
            :underline="false"
          >刷新</el-link>|
          <el-link
            class="link"
            type="primary"
            :underline="false"
          >新建会员卡</el-link>|
          <el-link
            class="link"
            type="primary"
            :underline="false"
          >管理会员卡</el-link>
        </span>

      </el-form-item>
      <el-form-item label="上下架：">
        <el-radio-group v-model="radio">
          <el-row>
            <el-radio :label="1">立即上架售卖</el-radio>
          </el-row>
          <el-row class="middle">
            <el-col :span="12">
              <el-radio :label="2">自定义上架时间</el-radio>
            </el-col>
            <el-col :span="9">
              <el-date-picker
                style="width:180px"
                size="small"
                v-model="picker_value"
                align="right"
                type="date"
                placeholder="选择上架售卖时间"
                :picker-options="pickerOptions"
              >
              </el-date-picker>
            </el-col>
          </el-row>
          <el-row>
            <el-radio :label="3">暂不售卖，放入仓库</el-radio>
          </el-row>

        </el-radio-group>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  name: 'otherInfo',
  created () {

  },
  mounted () {

  },
  data () {
    return {
      formData: {},
      rules: {},
      checked: false,
      radio: 1,
      picker_value: '',
      value: '',
      options: [{
        value: '会员卡一',
        label: '会员卡一'
      }, {
        value: '会员卡二',
        label: '会员卡二'
      }],
      pickerOptions: {

        shortcuts: [{
          text: '今天',
          onClick (picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      }
    }
  },
  methods: {

  }
}
</script>
<style scoped>
.middle {
  display: flex;
  align-items: center;
  margin: 10px 0;
}

.el-link .link {
  margin: 0 5px;
}
.isCard {
  margin-top: -25px;
}
</style>
