<template>
  <div>
    <el-dialog
      title="标签"
      :visible.sync="labelDialogVisible"
      :close-on-click-modal="false"
      width="300px"
      center
    >
      <div>
        <div style="overflow: hidden;">
          <span style="color: #a3a3a3;float:left;">请选择标签</span>
          <span style="float: right;">
            <span
              class="labelStyle"
              @click="refreshLabel"
            >刷新</span>
            <span class="labelStyle">/</span>
            <span
              class="labelStyle"
              @click="addLabel"
            >新建</span>
          </span>
        </div>

        <el-select
          v-model="labelValue"
          filterable
          multiple
          :multiple-limit="limitValue"
          size="small"
          style="margin-top: 10px;width: 100%;"
          placeholder="请选择"
        >
          <el-option
            v-for="item in labelList"
            :key="item.id"
            :label="item.value"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          @click="closeLabelDialog"
          size="small"
        >取 消</el-button>
        <el-button
          type="primary"
          size="small"
          @click="sureLabelDialog"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { allTagApi } from '@/api/admin/marketManage/messagePush'
export default {
  // 组件入参
  props: {
    // 弹窗是否显示
    dialogVisible: {
      type: Boolean,
      default: () => false
    },
    // 选择的标签id
    chooseLabelBack: {
      type: Array,
      default: () => []
    },
    // 限制个数
    multipleLimit: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      labelDialogVisible: false, // 标签弹窗
      limitValue: 0, // 限制个数
      labelList: [], // 标签列表
      labelValue: [], // 选中标签id值
      pickLabel: [] // 选中标签列表
    }
  },
  watch: {
    dialogVisible () {
      this.labelDialogVisible = true
      this.limitValue = this.multipleLimit
    },
    chooseLabelBack: {
      handler (newData) {
        console.log(newData)
        console.log('chooseLabelBack')
        this.labelValue = newData
      },
      deep: true,
      immediate: true
    }
  },
  mounted () {
    // 初始化数据
    this.getTagList()
  },
  methods: {
    // 获取标签列表
    getTagList () {
      allTagApi().then(res => {
        if (res.error === 0) {
          this.labelList = res.content
        }
      })
    },

    // 刷新标签
    refreshLabel () {
      this.getTagList()
      this.$nextTick(() => {
        this.$message.success('刷新成功')
      })
    },

    // 新建标签
    addLabel () {
      this.$router.push('/admin/home/main/labelManagement')
    },

    // 取消标签
    closeLabelDialog () {
      this.labelDialogVisible = false
      // 清空选择框
      this.labelValue = []
    },

    // 确定标签
    sureLabelDialog () {
      this.labelDialogVisible = false
      console.log(this.labelValue)
      this.pickLabel = []
      this.labelList.forEach(item => {
        this.labelValue.forEach(val => {
          if (item.id === val) {
            this.pickLabel.push(item)
          }
        })
      })
      this.$emit('resultLabelDatas', this.pickLabel)
      // 清空选择框
      this.labelValue = []
    }
  }
}
</script>
<style scoped>
.labelStyle {
  color: #5a8bff;
  cursor: pointer;
}
</style>
