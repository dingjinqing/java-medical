<template>
    <el-dialog
        :visible.sync="dialogVisiable"
        title="标签"
        width="355px"
        id="card-user-tag-dialog"
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
    },
    tags: {
      type: Array,
      default: () => []
    },
    limitNum: {
      type: Number,
      default: 3
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
  watch: {
    dialogVisiable (val) {
      if (val) {
        if (this.tags.length > 0) {
          for (const item of this.tags) {
            if (this.tagOptions.length > 0) {
              this.tagOptions.forEach(localTag => {
                if (localTag.id === item) {
                  this.tagArr.push({...localTag})
                }
              })
            }
          }
        }
      } else {
        this.tagArr = []
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
      if (this.isRefresh) {
        var target = document.getElementById('card-user-tag-dialog')
        var loading = this.loading = this.$loading({
          target,
          lock: true,
          text: '加载中...',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0)'
        })
      }
      allTagRequest().then(res => {
        if (res.error === 0) {
          this.tagOptions = res.content
          if (this.isRefresh) {
            this.isRefresh = false
            setTimeout(() => {
              loading.close()
            }, 500)
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
      if (this.tagArr.length - 1 >= this.limitNum) {
        this.tagArr.splice(this.tagArr.length - 1, 1)
        this.$message.warning(`最多选择${this.limitNum}个标签`)
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
/deep/ .el-loading-mask .el-loading-spinner{
  font-size: 36px;
  top: 45%;
}
</style>
