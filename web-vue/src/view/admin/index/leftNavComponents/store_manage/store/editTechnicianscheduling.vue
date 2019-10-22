<template>
  <div class="edit_technician_sheduling_page">
    <el-button
      type="text"
      class="iconSpan"
      @click="clickHandle"
    >{{$t('schedulingManage.edit')}}</el-button>

    <!-- 编辑排班 -->
    <el-dialog
      :visible.sync="editSchedulingVisible"
      :title="$t('schedulingManage.addTechnicianSchedule')"
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
        >{{$t('schedulingManage.determine')}}</el-button>
        <el-button
          size="small"
          @click="editSchedulingVisible = false"
        >{{$t('schedulingManage.cancel')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAllShift, saveScheduleAPI } from '@/api/admin/storeManage/schedulingManage'
import '@/util/date.js'

export default {
  props: {
    storeId: [Number, String],
    technicianId: [Number, String],
    beginTime: [String, Date],
    datas: Object
  },
  data () {
    return {
      editSchedulingVisible: false,
      schedulingDatas: [
        {
          label: this.$t('schedulingManage.Monday'),
          scheduleId: ''
        },
        {
          label: this.$t('schedulingManage.Tuesday'),
          scheduleId: ''
        },
        {
          label: this.$t('schedulingManage.Wednesday'),
          scheduleId: ''
        },
        {
          label: this.$t('schedulingManage.Thursday'),
          scheduleId: ''
        },
        {
          label: this.$t('schedulingManage.Friday'),
          scheduleId: ''
        },
        {
          label: this.$t('schedulingManage.Saturday'),
          scheduleId: ''
        },
        {
          label: this.$t('schedulingManage.Sunday'),
          scheduleId: ''
        }
      ],
      schedulingSelectDatas: []
    }
  },
  created () {
    this.langDefault()
    this.initSelectData()
    this.initSelectsDate()
    if (this.datas) {
      this.initData(this.datas)
    }
  },
  watch: {
    editSchedulingVisible: function (newVal) {
      if (newVal) {
        this.initSelectData()
        this.initSelectsDate()
        if (this.datas) {
          this.initData(this.datas)
        }
      }
    }
  },
  methods: {
    clickHandle () {
      this.editSchedulingVisible = !this.editSchedulingVisible
    },
    // 初始化班次下拉
    initSelectData () {
      let params = {
        storeId: this.storeId
      }
      getAllShift(params).then(res => {
        if (res.error === 0) {
          this.schedulingSelectDatas = [...res.content]
        }
      })
    },
    // 初始化时间
    initSelectsDate () {
      let oneDay = 24 * 60 * 60 * 1000
      if (this.beginTime) {
        this.schedulingDatas = this.schedulingDatas.map((item, index) => {
          item.startDate = new Date(this.beginTime.getTime() + oneDay * index).format('yyyy-MM-dd')
          return item
        })
      }
    },
    // 初始化班次选择
    initData (datas) {
      this.schedulingDatas.forEach((item, index) => {
        this.$set(item, 'scheduleId', '')
      })
      for (const key in datas) {
        if (datas.hasOwnProperty(key)) {
          const data = datas[key]
          if (data.scheduleId) {
            switch (key) {
              case 'monday':
                this.schedulingDatas[0].scheduleId = data.scheduleId
                break
              case 'tuesday':
                this.schedulingDatas[1].scheduleId = data.scheduleId
                break
              case 'wednesday':
                this.schedulingDatas[2].scheduleId = data.scheduleId
                break
              case 'thursday':
                this.schedulingDatas[3].scheduleId = data.scheduleId
                break
              case 'friday':
                this.schedulingDatas[4].scheduleId = data.scheduleId
                break
              case 'saturday':
                this.schedulingDatas[5].scheduleId = data.scheduleId
                break
              case 'sunday':
                this.schedulingDatas[6].scheduleId = data.scheduleId
                break
            }
          }
        }
      }
    },
    submitHandle () {
      let params = {
        storeId: Number(this.storeId),
        technicianId: Number(this.technicianId),
        scheduleMap: {}
      }
      this.schedulingDatas.forEach((item, index) => {
        if (item.scheduleId) {
          params.scheduleMap[item.startDate] = item.scheduleId
        }
      })
      saveScheduleAPI(params).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('schedulingManage.updated'))
          this.$emit('change')
          this.editSchedulingVisible = false
        }
      })
    }
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
    .edit_list_label {
      width: 100px;
    }
  }
}
</style>
