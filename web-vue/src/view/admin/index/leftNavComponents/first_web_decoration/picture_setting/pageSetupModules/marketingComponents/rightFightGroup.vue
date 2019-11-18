<template>
  <!-- 拼团抽奖设置 -->
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>拼团抽奖</h2>
      <!-- 模块私有区域 -->
      <div class="main right-fight-group-module">
        <el-form
          label-width="140px"
          size="small"
        >
          <el-form-item label="活动标题：">
            <el-radio-group>
              <el-radio>默认：拼团抽奖</el-radio>
              <el-radio>自定义：<el-input
                  v-model="data.group_draw_name"
                  style="width:100px;"
                ></el-input>
              </el-radio>
            </el-radio-group>
            <p class="tip">最多可输入8个字，为空则不显示</p>
          </el-form-item>
          <el-form-item label="活动有效期：">
            <el-radio-group>
              <el-radio>隐藏</el-radio>
              <el-radio>显示</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="活动底图：">
            <el-radio-group>
              <el-radio>默认底图</el-radio>
              <el-radio>自定义</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="字体颜色">
            <el-color-picker
              v-model="data.font_color"
              show-alpha
              :predefine="predefineColors"
            >
            </el-color-picker>
            <el-button
              class="reset-btn"
              @click="resetFontColor"
            >重置</el-button>
          </el-form-item>
          <el-form-item label="添加拼团抽奖活动">
            <el-select v-model="data.group_draw_id">
              <el-option
                label="请选择"
                value=""
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <!-- 模块私有区域End -->
    </div>
  </div>
</template>

<script>
import vcolorpicker from 'vcolorpicker'
import Vue from 'vue'
Vue.use(vcolorpicker)
export default {
  name: 'RightFightGroup',
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      data: {
        group_draw_id: '',
        name_set: 0,
        group_draw_name: '拼团抽奖',
        show_clock: 0,
        font_color: '#ffffff',
        module_bg: 0,
        module_img: this.default_module_img
      },
      predefineColors: [ // 颜色选择器预定义颜色池
        '#ff4500',
        '#ff8c00',
        '#ffd700',
        '#90ee90',
        '#00ced1',
        '#1e90ff',
        '#c71585',
        'rgba(255, 69, 0, 0.68)',
        'rgb(255, 120, 0)',
        'hsv(51, 100, 98)',
        'hsva(120, 40, 94, 0.5)',
        'hsl(181, 100%, 37%)',
        'hsla(209, 100%, 56%, 0.73)',
        '#c7158577'
      ],
      default_module_img: this.$imageHost + '/image/admin/fighting_group_draw1.jpg'
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log('newData:', newData, this.modulesData)
        this.data = this.modulesData
      },
      immediate: true
    },
    // 监听数据变换
    data: { // 模块公共
      handler (newData) {
        console.log('update:', newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {
    resetFontColor () {
      this.$set(this.data, 'font_color', '#ffffff')
    }
  }
}
</script>

<style lang="scss" scoped>
.rightCommodity {
  .rightCommodityMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
  }
}
.right-fight-group-module {
  .el-radio {
    display: block;
    line-height: 30px;
  }
  .tip {
    color: #999;
  }
  .reset-btn {
    vertical-align: top;
  }
}
</style>
