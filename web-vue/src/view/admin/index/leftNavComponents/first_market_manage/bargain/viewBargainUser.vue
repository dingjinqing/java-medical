<!--
* 砍价 - 用户信息列表页面
*
* @author:赵鑫
-->
<template>
  <div>
    <!-- 上半部分：筛选条件查询部分 -->
    <wrapper style="padding: 0 30px">
      <section class="bargainUserMain">
        <div class="bargainUserInfo">
          <span>用户昵称</span>
          <el-input
            size="small"
            class="inputWidth"
          ></el-input>
        </div>
        <div class="bargainUserInfo">
          <span>手机号</span>
          <el-input
            size="small"
            class="inputWidth"
          ></el-input>
        </div>
        <div class="bargainUserInfo">
          <span>砍价状态</span>
          <el-select
            v-model="value1"
            placeholder="请选择状态"
            size="small"
            class="inputWidth"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :value="item.value"
              :label="item.label"
            ></el-option>
          </el-select>
        </div>
      </section>
      <section class="bargainUserMain infoBottom">
        <div style="display:flex">
          <span style="height:32px;line-height:32px;margin-right:20px">发起时间</span>
          <dateTimePicker
            @time="handleGetTime"
            :showPicker=1
          />
        </div>
        <div style="margin:0 20px">
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
        </div>
        <div>
          <el-button size="small">导出表格</el-button>
        </div>
      </section>
    </wrapper>

    <!-- 下半部分：表格数据部分 -->
    <wrapper>
      <div class="table_list">
        <el-table
          header-row-class-name="tableClss"
          border
          style="width: 100%"
        >
          <el-table-column
            prop=""
            label="ID"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="商品名称"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="发起砍价用户昵称"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="手机号码"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="发起时间"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="已砍金额"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="待砍金额"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="参与砍价人数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="砍价状态"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="操作"
            align="center"
          >
          </el-table-column>
        </el-table>
      </div>
    </wrapper>

  </div>
</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import dateTimePicker from '@/components/admin/dateTimePicker/dateTimePicker'

export default {
  components: { wrapper, dateTimePicker },
  data () {
    return {
      value1: '',
      options: [
        {
          value: '1',
          label: '龙须面'
        }, {
          value: '2',
          label: '面包'
        }, {
          value: '3',
          label: '酸皮奶'
        }
      ]
    }
  },
  methods: {
    // 获取筛选的时间
    handleGetTime (val) {
      console.log(val)
    }
  }

}

</script>
<style lang="scss" scoped>
* {
  font-size: 14px;
}
.bargainUserMain {
  display: flex;
  .bargainUserInfo {
    margin-right: 50px;
    span {
      margin-right: 20px;
    }
    .inputWidth {
      width: 150px;
    }
  }
}
.infoBottom {
  margin-top: 20px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
}
</style>
