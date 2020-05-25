<template>
  <div class="getUserDialog">
    <!-- 获取人群列表 -->
    <el-dialog
      title="选择用户"
      :visible.sync="dialogVisible"
      width="800px"
      :before-close="handleClose"
      center
    >
      <div class="title">
        注：因微信小程序官方限制，本日部分用户可接收的消息数量已达上限，故接收不到本次消息推送
      </div>
      <div>
        <el-form
          size="small"
          :inline="true"
          :form="formData"
        >
          <el-form-item :label="labels.id">
            <el-input v-model="formData.id"></el-input>
          </el-form-item>
          <el-form-item :label="labels.userName">
            <el-input v-model="formData.userName"></el-input>
          </el-form-item>
          <el-form-item :label="labels.phone">
            <el-input v-model="formData.phone"></el-input>
          </el-form-item>
          <el-form-item :label="labels.isVisit">
            <el-select
              v-model="value"
              placeholder="是否关注公众号"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              size="small"
              type="primary"
              @click="handleToSerch()"
            >搜索</el-button>
          </el-form-item>
        </el-form>

      </div>
      <!-- table -->
      <div>
        <el-table>
          <el-table
            :data="tableData"
            style="width: 100%"
            border
          >
            <!-- 表格无数据的时候 -->
            <template slot="empty">
              <el-image
                style="width: 30px; height: 30px"
                :src="urls.url1"
              ></el-image>
            </template>

            <el-table-column
              prop="id"
              label="id"
            >
            </el-table-column>
            <el-table-column
              prop="userName"
              label="昵称"
            >
            </el-table-column>
            <el-table-column
              prop="phone"
              label="手机号"
            >
            </el-table-column>
            <el-table-column
              prop="id"
              label="可接受信息数量"
            >
            </el-table-column>
            <el-table-column
              prop="isVisit"
              label="是否关注公众号"
            >
            </el-table-column>
          </el-table>
        </el-table>
        <div class="footer">
          <span>{{$t('formStatisticsHome.currentPage')}}{{currentPage}}/{{totalPage}}，{{$t('formStatisticsHome.generalRecord')}}{{totalRows}}{{$t('formStatisticsHome.strip')}}</span>
          <el-pagination
            @current-change="fetchData"
            :current-page.sync="currentPage"
            :page-size="20"
            layout="prev, pager, next, jumper"
            :total="totalRows"
          >
          </el-pagination>
        </div>
      </div>
      <div>

      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="handleSave"
        >确 定</el-button>
        <el-button
          @click="handleCancle"
          size="small"
        >取 消</el-button>

      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getUserArrayApi } from '@/api/admin/marketManage/messagePush.js'
export default {
  name: `getUserDialog`,
  props: {
    /**
     * 作为接口传给父组件，让父组件通过管理这个变量来操作子组件
     */
    dialogVisible: {
      type: Boolean,
      default: false
    },
    userKey: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      labels: {
        id: `id`,
        userName: `昵称`,
        phone: `手机号`,
        isVisit: `是否关注公众号`
      },
      formData: {
        id: ``,
        userName: ``,
        phone: ``,
        currentPage: ``,
        pageRows: ``,
        isVisit: ``,
        userKey: null
      },
      value: null,
      options: [
        {
          label: '是否关注公众号',
          value: null
        },
        {
          label: `是`,
          value: true,
          falg: 1
        },
        {
          label: `否`,
          value: false,
          falg: 0
        }
      ],
      currentPage: 1, // 当前页
      totalPage: 1, // 总页数
      totalRows: 0, // 总条数
      /**
       * 表格数据
       */
      tableData: [],
      urls: {
        url1: `${this.$imageHost}/image/admin/no_data.png`
      }

    }
  },
  watch: {
    dialogVisible (newData) {
      if (newData) {
        this.fetchData()
      }
    }
  },
  mounted () {

  },
  methods: {
    // 初始化数据
    fetchData () {
      console.log(this.userKey)
      if (!this.userKey) return
      let { id, userName, phone } = this.formData
      let params = {}
      if (this.value !== null) {
        params = {
          userKey: this.userKey,
          id: id,
          userName: userName,
          phone: phone,
          isVisit: this.value,
          currentPage: this.currentPage
        }
      } else {
        params = {
          userKey: this.userKey,
          id: id,
          userName: userName,
          phone: phone,
          currentPage: this.currentPage
        }
      }
      getUserArrayApi(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content
        }
      }).catch(error => console.log(error))
    },
    // 点击搜索
    handleToSerch () {
      this.fetchData()
    },
    handleClose () {
      this.$emit('dialog-cancel')
    },
    handleCancle () {
      this.handleClose()
    }
  }
}
</script>

<style lang="scss" scoped>
.getUserDialog {
  .title {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 5px;
  }
  .footer {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    margin-top: 10px;
  }
}
</style>
