<template>
  <div>

    <el-dialog
      :visible.sync="dialogVisiable"
      width="380px"
      title="提示"
    >
      <div class="top-tip">
        <el-alert
          title="根据以下条件筛选出1条数据,是否确认导出？"
          type="warning"
          :closable="false"
          show-icon
          class="tip"
        >
        </el-alert>
      </div>

      <div class="condition">
        <p>筛选条件: <span v-show="!hasCondition">无</span></p>
        <ul
          v-show="hasCondition"
          style="margin-top: 5px;"
        >
          <li
            v-for="(item,index) in queryContent"
            :key="index"
          >
            <div v-if="item.value">
              <span class="title">{{item.title}}:</span>
              <span class="content">{{item.value}}</span>
            </div>
          </li>

        </ul>
      </div>
      <div class="
              dashed-line"></div>
      <div class="number">
        <p class="num-tip"><strong>导出条数</strong>（一次最多导出5000条数据）</p>
        <el-input-number
          v-model="startNum"
          size="small"
          controls-position="right"
          style="width:100px;"
        ></el-input-number>
        <span>至</span>
        <el-input-number
         v-model="endNum"
          size="small"
          controls-position="right"
          style="width:100px;"
        ></el-input-number>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          type="primary"
          @click="handleExport"
        >确 定</el-button>
        <el-button
          @click="dialogVisiable = false"
          size="small"
        >取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { download } from '@/util/excelUtil.js'
import { exportExcelForCardExamine } from '@/api/admin/memberManage/memberCard.js'
export default {
  props: {
    visiable: {
      type: Boolean,
      default: false
    },
    queryContent: {
      type: Array,
      default: () => { return [{ title: null, value: null }] }
    },
    maxNum: {
      type: Number,
      default: null
    },
    cardId: {
      required: true,
      default: null
    },
    status: {
      type: Number,
      required: true,
      default: null
    }
  },
  data () {
    return {
      startNum: 1,
      endNum: 2
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
    },
    hasCondition () {
      if (this.queryContent === null || this.queryContent.length === 0) {
        return false
      } else {
        return this.queryContent.some(item => Boolean(item.value))
      }
    }
  },
  watch: {
    maxNum (newValue, oldValue) {
      if (this.maxNum > 0) {
        this.endNum = this.maxNum
      }
    }
  },
  methods: {
    handleExport () {
      this.dialogVisiable = false
      let obj = {
        'startNum': this.startNum,
        'endNum': this.endNum,
        'cardId': this.cardId,
        'status': this.status,
        'realName': this.queryContent[0].value,
        'mobile': this.queryContent[1].value,
        'firstTime': this.queryContent[2].value,
        'secondTime': this.queryContent[3].value
      }
      exportExcelForCardExamine(obj).then(res => {
        console.log('success')
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : 'template.xlsx'
        console.log('文件名：' + fileName)
        download(res, decodeURIComponent(fileName))
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.top-tip {
  border: 1px solid rgb(255, 213, 163);
  margin-bottom: 15px;
}
/deep/ .el-dialog__body {
  padding: 0 20px 30px 20px;
}

.dashed-line {
  margin: 15px 4px;
  border: 0.5px dashed rgba($color: #000000, $alpha: 0.5);
}
p.num-tip {
  margin-bottom: 25px;
}
.condition li {
  margin: 5px 0;
  .title {
    margin-right: 10px;
  }
}
</style>
