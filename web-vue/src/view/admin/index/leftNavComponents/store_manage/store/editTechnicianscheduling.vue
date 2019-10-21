<template>
  <div class="edit_technician_sheduling_page">
    <el-button
      type="text"
      class="iconSpan"
      @click="clickHandle"
    >编辑</el-button>

    <!-- 编辑排班 -->
    <el-dialog
      :visible.sync="editSchedulingVisible"
      title="添加技师排班"
      :close-on-click-modal="false"
      width="720"
    >
      <div>
        <ul class="edit_list_ul">
          <li
            class="edit_list_li"
            v-for="(item,index) in schedulingDatas"
            :key="index"
          >
            <label class="edit_list_label">{{item.label}}：</label>
            <el-select
              size="small"
              style="width: 160px; color:#666;"
              v-model="item.scheduleId"
            >
              <el-option
                v-for="data in schedulingSelectDatas"
                :key="data.scheduleId"
                :label="data.scheduleName"
                :value="data.scheduleId"
              ></el-option>
            </el-select>
          </li>
        </ul>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="submitHandle"
        >确定</el-button>
        <el-button
          size="small"
          @click="editSchedulingVisible = false"
        >取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAllShift } from '@/api/admin/storeManage/schedulingManage'
export default {
  props: {
    storeId: [Number, String],
    datas: Object,
    beginTime: Date
  },
  data () {
    return {
      editSchedulingVisible: false,
      schedulingDatas: [
        {
          label: '周一'
        },
        {
          label: '周二'
        },
        {
          label: '周三'
        },
        {
          label: '周四'
        },
        {
          label: '周五'
        },
        {
          label: '周六'
        },
        {
          label: '周日'
        }
      ],
      schedulingSelectDatas: []
    }
  },
  created () {
    this.initSelectData()
    this.initSelectsDate()
  },
  watch: {
    editSchedulingVisible: function (newVal) {
      if (newVal) {
        this.initSelectData()
      }
    }
  },
  methods: {
    clickHandle () {
      this.editSchedulingVisible = !this.editSchedulingVisible
    },
    initSelectData () {
      console.log('init...')
      let params = {
        storeId: this.storeId
      }
      getAllShift(params).then(res => {
        if (res.error === 0) {
          this.schedulingSelectDatas = [...res.content]
        }
      })
    },
    initSelectsDate () {
      this.schedulingDatas = this.schedulingDatas.map((item, index) => {
        item.startDate = this.beginTime
      })
    },
    submitHandle () { }
  }
}
</script>

<style lang="scss" scoped>
.edit_technician_sheduling_page {
  .iconSpan {
    color: #5a8bff;
    text-decoration: none;
    cursor: pointer !important;
  }
}
.edit_list_ul {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  .edit_list_li {
    margin-top: 15px;
    margin-right: 6px;
  }
}
</style>
