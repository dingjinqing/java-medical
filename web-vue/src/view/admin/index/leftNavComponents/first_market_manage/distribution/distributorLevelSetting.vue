<template>
  <div class="content">
    <div class="main">
      <div class="mainInfo">
        <i
          class="el-icon-warning"
          style="color: #E6A23C;margin-right: 5px;"
        ></i>提示：每次修改分销员等级，将会有大量分销员受到影响，请谨慎操作</div>
    </div>

    <el-button
      type="text"
      @click="centerDialogVisible = true"
    >升级规则</el-button>

    <el-dialog
      title="提醒"
      :visible.sync="centerDialogVisible"
      width="25%"
      center
      :close-on-click-modal="false"
    >
      <div class="textInfo">累计邀请用户数：分销员累积邀请的用户数。</div>
      <div class="textInfo">累积推广金：分销员推广商品的订单累计金额。</div>
      <div class="textInfo">累积消费金：分销员在店铺累积消费金额。</div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="centerDialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>

    <div class="table_list">
      <el-table
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="level"
          label="等级"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="等级名称"
          align="center"
        >
          <template slot-scope="scope">
            <el-input v-model="scope.row.levelName"></el-input>
          </template>
        </el-table-column>

        <el-table-column
          label="升级规则"
          align="center"
          width="300px"
        >
          <template slot-scope="scope">
            <div v-if="scope.row.level === '1'">成为分销员后，默认即是该等级</div>
            <el-radio-group
              v-model="scope.row.rule"
              v-if="scope.row.level !== '1'"
            >
              <el-radio :label="1">自动升级</el-radio>
              <el-radio :label="2">手动升级</el-radio>
            </el-radio-group>
            <div
              v-if="scope.row.rule === 1 && scope.row.level !== '1'"
              style="margin: 15px 0;"
            >
              <div>累计邀请用户数达 <el-input
                  size="mini"
                  style="width: 50px;"
                ></el-input> 个</div>
              <div>或</div>
              <div>累计推广金达 <el-input
                  size="mini"
                  style="width: 50px;"
                ></el-input> 元</div>
              <div>或</div>
              <div>累积推广金与消费金总和达 <el-input
                  size="mini"
                  style="width: 50px;"
                ></el-input> 元</div>
            </div>
            <div
              v-if="scope.row.rule === 2 && (scope.row.level === '2' || scope.row.level === '3')"
              style="margin: 15px 0;"
            >
              <el-button
                size="mini"
                @click="addDistributor(scope.row.id)"
              ><i class="el-icon-plus"></i> 添加分销员</el-button>
            </div>
          </template>
        </el-table-column>

        <el-table-column
          label="分销员数量"
          align="center"
        >
          <template slot-scope="scope">
            <a href="javascript:void(0);">{{ scope.row.num }}</a>
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <p v-if="scope.row.status === 1">已启用</p>
            <p v-if="scope.row.status === 0">已停用</p>
            <el-button
              type="primary"
              size="mini"
              v-if="scope.row.status === 0"
            >启用</el-button>
            <el-button
              type="primary"
              plain
              size="mini"
              v-if="scope.row.status === 1"
            >停用</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="listFooter">
      <el-button
        type="primary"
        size="small"
      >保存</el-button>
    </div>

    <!-- 添加分销员弹窗 -->
    <el-dialog
      title="添加分销员"
      :visible.sync="addDialogVisible"
      :close-on-click-modal="false"
      width="70%"
      center
    >
      <el-form label-width="140px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="分销员手机号：">
              <el-input
                size="small"
                placeholder="请输入手机号"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分销员昵称：">
              <el-input
                size="small"
                placeholder="请输入昵称"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="真实姓名：">
              <el-input
                size="small"
                placeholder="请输入姓名"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="分销员ID：">
              <el-input
                size="small"
                placeholder="请输入分销员ID"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分销员等级：">
              <el-select
                class="optionInput"
                size="small"
                v-model="valueLevel"
                placeholder="请选择等级"
              >
                <el-option
                  v-for="level in groupLevelList"
                  :key="level.levelId"
                  :label="level.label"
                  :value="level.levelName"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分销员分组：">
              <el-select
                class="optionInput"
                size="small"
                v-model="valueGroup"
                placeholder="请选择分组"
              >
                <el-option
                  v-for="group in groupNameList"
                  :key="group.id"
                  :label="group.groupName"
                  :value="group.groupName"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="text-align: center;">
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
          <el-button
            type="primary"
            size="small"
            plain
          >重置</el-button>
        </el-row>
      </el-form>

      <el-table
        :data="distributorList"
        border
        style="margin-top: 20px;"
      >
        <el-table-column
          type="selection"
          width="55"
        >
        </el-table-column>
        <el-table-column
          prop="userId"
          label="分销员ID"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="mobile"
          label="分销员手机号"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="username"
          label="分销员昵称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="realName"
          label="真实姓名"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="nextNumber"
          label="下级用户数"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="totalFanliMoney"
          label="累计获得佣金金额"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="groupName"
          label="分销员组"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="levelName"
          label="当前等级"
          align="center"
        >
        </el-table-column>
      </el-table>
      <Pagination
        :page-params.sync="pageParams"
        @pagination="addDistributor"
      />
      <span slot="footer">
        <el-button @click="cancelHandler()">取 消</el-button>
        <el-button
          type="primary"
          @click="addHandler()"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { distributorList, distributorLevelList, distributorGroupList } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    Pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      // 表格数据
      tableData: [{
        level: '1',
        levelName: '分销员测试',
        rule: 1,
        num: '36',
        status: 0
      }, {
        level: '2',
        levelName: 'v2',
        rule: 1,
        num: '22',
        status: 0
      }, {
        level: '3',
        levelName: '分销员组3',
        rule: 2,
        num: '2',
        status: 1
      }, {
        level: '4',
        levelName: '分销员组4',
        rule: 2,
        num: '2',
        status: 1
      }, {
        level: '5',
        levelName: '分销员组5',
        rule: 2,
        num: '2',
        status: 0
      }],
      centerDialogVisible: false, // 规则弹框
      addDialogVisible: false, // 添加分销员弹框
      pageParams: {}, // 分页
      valueLevel: '',
      valueGroup: '',
      groupLevelList: [], // 分销员等级
      groupNameList: [], // 分销员分组
      distributorList: [] // 表格
    }
  },
  methods: {
    // 显示分销员弹窗
    addDistributor (id) {
      this.addDialogVisible = true
      // 等级下拉框
      distributorLevelList().then(res => {
        if (res.error === 0) {
          this.groupLevelList = res.content
        }
      })
      // 分组下拉框
      distributorGroupList().then(res => {
        if (res.error === 0) {
          this.groupNameList = res.content
        }
      })
      // 表格
      distributorList(this.pageParams).then(res => {
        if (res.error === 0) {
          this.distributorList = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },

    // 取消
    cancelHandler () {
      this.addDialogVisible = false
    },

    // 添加分销员
    addHandler () {
      this.addDialogVisible = false
    }
  }

}

</script>
<style lang="scss" scoped>
a {
  text-decoration: none;
  color: #5a8bff;
}
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    .mainInfo {
      width: 100%;
      height: 40px;
      line-height: 37px;
      border: 1px solid #f2e1c8;
      background: #fff7ec;
      color: #666;
      margin-bottom: 10px;
      padding-left: 12px;
    }
  }
  .textInfo {
    margin-bottom: 20px;
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
  background-color: #fff;
  .footer_right {
    padding: 20px 0 20px 20px;
    display: flex;
    justify-content: flex-end;
    span {
      display: block;
      height: 32px;
      line-height: 32px;
    }
  }
}

.el-input {
  width: 180px;
}

.listFooter {
  position: fixed;
  bottom: 0;
  right: 27px;
  width: 87.8%;
  margin: 0 auto;
  height: 50px;
  line-height: 50px;
  background: #fff;
  text-align: center;
  z-index: 99;
}
</style>
