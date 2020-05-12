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
      <!-- 内容区域 -->
      <div class="container">
        <ul
          class="ulList"
          v-loading="loading"
        >
          <el-radio-group
            v-model="radio"
            @change="handleChange"
          >
            <li
              style="width:100%"
              v-for="(item,index) in content"
              :key="index"
            >
              <el-radio
                :label="item.id"
                style="min-width:750px"
              >{{item.content}}</el-radio>
            </li>
          </el-radio-group>
        </ul>
      </div>

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
      title: `选择内容模板`,
      radio: `偷偷告诉你，花旗西洋参今晚7点准时开抢，库存有限，错过就涨价，赶快加购物车吧`,
      'content': [

      ],
      val: ``,
      loading: false
    }
  },
  created () {
    // 初始化弹窗的数据
    this.fetchData()
  },
  methods: {
    // 获取数据
    fetchData () {
      this.loading = true

      // 2:商家活动通知
      contentListApi({
        'action': 2
      }).then(res => {
        console.log(res)
        const { error, content } = res
        if (error === 0) {
          this.content = content
        }
        this.loading = false
      }).catch(err => console.log(err))
    },
    // 获取模板内容选中的值
    handleChange (val) {
      console.log(val)
      const res = this.content.find(item => item.id === val)
      this.val = res
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
      if (this.val === ``) {
        this.$emit(`content`, this.content[0])
      } else {
        this.$emit(`content`, this.val)
      }

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
.chooseTemplateDialog {
  .container {
    .ulList {
      li {
        height: 43px;
        padding: 12px 0;
        border-bottom: 1px solid #eee;
        list-style: none;
        font-size: 12px;
      }
    }
  }
}
</style>
