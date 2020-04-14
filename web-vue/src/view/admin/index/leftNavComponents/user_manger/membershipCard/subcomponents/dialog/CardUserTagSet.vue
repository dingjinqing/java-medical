<template>
    <el-dialog
        :visible.sync="dialogVisiable"
        title="标签"
        width="355px"
        >
        <div>
            <div class="clearfix">
                <span class="tip">请选择标签</span>
                <span class="opt">
                    <span @click="refeshTag">刷新</span>/
                    <span @click="jumpToTagPage">新建</span>
                </span>
            </div>
            <el-select v-model="tagArr" size="mini" multiple placeholder="请选择" @change="checkTagLength">
                    <el-option
                     v-for="item in tagOptions"
                     :key="item.id"
                     :label="item.value"
                     :value="item"
                     :disabled="item.disabled">

                    </el-option>
            </el-select>
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button size="small" @click="dialogVisiable = false">取 消</el-button>
            <el-button size="small" type="primary" @click="chooseUserTag">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
import { allTagRequest } from '@/api/admin/membershipList.js'
export default {
  props: {
    visiable: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    dialogVisiable: {
      get () {
        return this.visiable
      },
      set (val) {
        this.$emit('update:visiable', val)
      }
    }
  },
  mounted () {
    this.getUserTags()
  },
  data () {
    return {
      tagArr: [],
      tagOptions: [],
      isRefresh: false
    }
  },
  methods: {
    getUserTags () {
      allTagRequest().then(res => {
        if (res.error === 0) {
          this.tagOptions = res.content
          if (this.isRefresh) {
            this.isRefresh = false
            this.$message.success('刷新成功')
          }
        }
      })
    },
    refeshTag () {
      this.isRefresh = true
      this.getUserTags()
    },
    jumpToTagPage () {
      let routeData = this.$router.resolve({
        name: 'user_tag'
      })
      window.open(routeData.href, '_blank')
    },
    checkTagLength () {
      if (this.tagArr.length >= 4) {
        this.tagArr.splice(this.tagArr.length - 1, 1)
        this.$message.warning('最多选择三个标签')
      } else {
        console.log(this.tagArr.length)
      }
    },
    chooseUserTag () {
      this.dialogVisiable = false
      this.$emit('chooseUserTag', this.tagArr)
    }
  }
}
</script>

<style scoped lang="scss">
/deep/ .el-select--mini{
    width: 100%;
}
.clearfix{
    margin-bottom: 10px;
}
.clearfix::after{
 content: "";
  clear: both;
  display: table;
}
.opt{
    float: right;
    color:#5A8BFF;
    cursor: pointer;
}
.tip{
 color: #9d9d9d;
}
</style>
