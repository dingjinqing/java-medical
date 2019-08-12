<template>
  <div class="templateList">
    <el-form
      ref="listForm"
      :model="listForm"
      label-width="140px"
    >
      <el-row>
        <el-col :span="3">
          <el-form-item label="店铺默认运费模板：">
            <el-select
              @change="handleChange"
              style="width:100px;"
              size="small"
              v-model="value"
              placeholder="请选择运费模板"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>

        </el-col>
        <el-col
          v-show="this.value === '统一运费'"
          :span="14"
          class="middle"
        >
          <el-form-item
            label="运费："
            class="deliver"
          >
            <el-input
              size="small"
              v-model="listForm.deliver"
              style="width:50px"
            ></el-input>元
          </el-form-item>
        </el-col>
        <el-col
          :span="16"
          v-show="this.value === '满额包邮'"
        >
          <el-form-item>
            订单金额>=
            <el-input
              size="small"
              v-model="listForm.moneny"
              style="width:60px"
            ></el-input>
            元时包邮， 否则运费为
            <el-input
              size="small"
              v-model="listForm.lim_moneny"
              style="width:50px"
            ></el-input>
            元
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item>
            <el-button
              class="right"
              type="primary"
              size="small"
              @click="handleSave"
            >保存配置</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-table
      :data="dataList"
      border
      style="width: 100%"
    >

      <el-table-column
        prop="area_text"
        label="可配送区域"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="first_num"
        label="首件（件）"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="first_fee"
        label="运费（元）"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="continue_num"
        label="续件（件）"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="continue_fee"
        label="续费（元）"
        width="180"
      >
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import { deliverTemplatelist } from '@/api/admin/goods_manage/deliver/deliver'
export default {
  name: 'templateList',
  data () {
    return {
      listForm: {
        deliver: 10,
        moneny: 400,
        lim_moneny: 10
      },
      options: [
        {
          label: '统一运费',
          value: '统一运费'
        },
        {
          label: '满额包邮',
          value: '满额包邮'
        }
      ],
      value: '统一运费',
      dataList: [{
        area_text: ``,
        first_num: 2,
        first_fee: 4,
        continue_num: 1,
        continue_fee: 2
      }]
    }
  },
  created () {

  },
  methods: {
    handleChange (val) {
      console.log(val)
    },

    handleSave () {
      let params = {
        // 'page': {
        //   'currentPage': '1',
        //   'pageRows': '20'
        // }
      }
      deliverTemplatelist(params).then(res => {
        console.log(res)
        // const { content: { page, dataList }, error } = res
        // if (error === 0) {
        //   console.log(page)
        //   console.log(dataList)
        //   dataList.forEach(item => {
        //     let newTemplateContent = JSON.parse(item.templateContent)
        //     console.log(newTemplateContent)
        //   })
        // }
        // let data = content.dataList[0].templateContent
        // data = JSON.parse(data)
        // console.log(data)
      }).catch(err => console.log(err))
    }
  },

  watch: {

  }
}
</script>
<style scoped>
.middle {
  margin-left: 50px;
  margin-right: 37.5px;
}
</style>
