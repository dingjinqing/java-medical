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
            <el-radio-group v-model="data.name_set">
              <el-radio label="0">默认：拼团抽奖</el-radio>
              <el-radio label="1">自定义：<el-input
                  v-model="data.group_draw_name"
                  style="width:150px;"
                ></el-input>
              </el-radio>
            </el-radio-group>
            <p class="tip">最多可输入8个字，为空则不显示</p>
          </el-form-item>
          <el-form-item label="活动有效期：">
            <el-radio-group v-model="data.show_clock">
              <el-radio label="0">隐藏</el-radio>
              <el-radio label="1">显示</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="活动底图：">
            <el-radio-group v-model="data.module_bg">
              <el-radio label="0">默认底图</el-radio>
              <el-radio label="1">自定义</el-radio>
            </el-radio-group>
            <div class="add-bgs">
              <div v-if="!data.module_img">
                <p class="add-bgs-text">+添加一个背景图</p>
                <p class="add-bgs-tip">建议宽度720像素以内，高度260像素以内</p>
              </div>
              <el-image
                v-if="data.module_img"
                :src="data.module_img"
              ></el-image>
            </div>
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
          <el-form-item
            label="添加拼团抽奖活动"
            required
          >
            <el-select v-model="data.group_draw_id">
              <el-option
                label="请选择拼团抽奖活动"
                value=""
              ></el-option>
              <el-option
                v-for="item in fightGroupSelects"
                :key="item.id"
                :label="item.actName"
                :value="item.id"
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
import { getFightGroup } from '@/api/admin/smallProgramManagement/pictureSetting/pictureSetting'
export default {
  name: 'RightFightGroup',
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      data: {
        module_name: 'm_group_draw',
        group_draw_id: '',
        name_set: '0',
        group_draw_name: '拼团抽奖',
        show_clock: '1',
        font_color: '#ffffff',
        module_bg: '0',
        module_img: ''
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
      default_module_img: this.$imageHost + '/image/admin/fighting_group_draw1.jpg',
      fightGroupSelects: []
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log('newData:', newData, this.modulesData)
        this.$nextTick(() => {
          this.data = this.modulesData
        })
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
    },
    initFightGroup () {
      let that = this
      getFightGroup().then(res => {
        if (res.error === 0) {
          console.log(res.content)
          that.fightGroupSelects = res.content
        }
      })
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
  .add-bgs {
    width: 300px;
    height: 100px;
    line-height: 1;
    text-align: center;
    background: #fff;
    border: 1px dashed #e5e5e5;
    position: relative;
    .add-bgs-text {
      color: #5a8bff;
      margin: 27px 0 5px 0;
    }
    .add-bgs-tip {
      color: #999;
      font-size: 12px;
    }
  }
}
</style>
