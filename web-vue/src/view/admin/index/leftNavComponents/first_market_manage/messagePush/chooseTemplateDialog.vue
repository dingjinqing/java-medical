<template>
  <div class="chooseTemplateDialog">
    <el-dialog
      :title="title"
      :visible.sync="showDialog"
      :before-close="cancelDialog"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      width="50%"
      center
    >

      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          @click="handleSave"
          size="small"
        >确定</el-button>
        <el-button
          type="primary"
          @click="handleCancel"
          size="small"
        >取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { contentListApi } from '@/api/admin/marketManage/messagePush'

export default {
  name: 'memberListDialog',
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  props: {
    /**
     * 作为接口传给父组件，让父组件通过管理这个变量来操作子组件
     */
    showDialog: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      /**
       * 弹窗的数据
       */
      title: `选择内容模板`
    }
  },
  created () {
    // 初始化弹窗的数据
    this.fetchData()
  },
  methods: {
    // 获取数据
    fetchData () {
      contentListApi().then(res => {
        console.log(res)
      }).catch(err => console.log(err))
    },

    /**
     * 声明了一个函数的接口给父组件，让父组件可以通过一个函数操作子组件
     */
    cancelDialog () {
      this.$emit('dialog-cancel')
    },
    // 确定
    handleSave () {
      // 处理完确定之后的操作关闭弹窗
      this.$emit(`data`)
      this.cancelDialog()
    },
    // 取消
    handleCancel () {
      this.cancelDialog()
    }
  }
}

</script>
<style lang="scss" scoped>
</style>
