<template>
  <div>
    <!--    <img alt="Vue logo" src="../assets/logo.png">-->
    <!--    <HelloWorld msg="Welcome to Your Vue.js App"/>-->
    <el-table
      :data="tableData"
      style="width: 100%"
      highlight-current-row
    >
      <el-table-column
        prop="area"
        label="可配送区域"
        :show-overflow-tooltip="false"
      >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="dialogStat=true"
            v-if="scope.row.area==='指定配送区域和运费'"
          >{{ scope.row.area}}
          </el-button>
          <div v-if="scope.row.area!=='指定配送区域和运费'">{{ scope.row.area}}</div>
          <el-button
            type="text"
            @click="dialogStat=true"
            v-if="scope.row.area!=='指定配送区域和运费'"
          >编辑
          </el-button>
          <el-button
            type="text"
            @click="handleDel(scope.$index)"
            v-if="scope.row.area!=='指定配送区域和运费'"
          >删除
          </el-button>
        </template>
        <!--                <template slot-scope="scope" v-if="scope.row.area!=='指定配送区域和运费'">-->
        <!--                    {{ scope.row.area}}-->
        <!--                </template>-->
      </el-table-column>
      <el-table-column
        prop="name"
        label="首件（件）"
      >
      </el-table-column>
      <el-table-column
        prop="address"
        label="运费（元）"
      >
      </el-table-column>
    </el-table>
    <!--        {{this.locatList}}-->
    <locat-t-p
      :location-list=this.locatList
      :outer-visible=this.dialogStat
      @close="this.showData"
      :inner-obj-j="valueA.innerObj"
      :check-list-t="valueA.checkList"
      @checkList="this.getCheckList"
    ></locat-t-p>
  </div>
</template>

<script>
// @ is an alias to /src
import { getAreaSelect } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate.js'
// import dataList from '../js/china-data'
import { Component, Vue } from 'vue-property-decorator'
import LocatTP from '@/components/admin/areaLinkage/LocatTP'

@Component({
  components: { LocatTP }
})
export default class Home extends Vue {
  // initial data
  dialogStat = false
  msg = 123
  tableData = [
    { area: '指定配送区域和运费' }
  ]
  locatList = [];
  checkeList = [];
  valueA = {}
  len = ``

  // // use prop values for initial data
  // helloMsg = 'Hello, ' + this.propMessage

  // lifecycle hook
  mounted () {
    this.getData()
  }
  created () {
    getAreaSelect().then(res => {
      const { error, content } = res
      if (error === 0) {
        content.unshift({ 'provinceId': 1, 'provinceName': '全选' })
        // console.log(content)
        this.locatList = content
      }
    }).catch(err => console.log(err))
  }
  showData (flag) {
    this.dialogStat = flag
  }
  handleDel (index) {
    console.log(index)
    console.log(this.tableData)
    this.tableData.splice(index, 1)
  }
  getCheckList (value) {
    // 获取的id数组
    this.valueA = value
    this.checkeList = value.checkList
    console.log(this.checkeList)
    // console.log(this.locatList[0][`state`])

    console.log(value)
    console.log(value.areaList.length)
    if (value.areaList.length > 0) {
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].area === '指定配送区域和运费') {
          // this.tableData[i] = { area: value.showArr.toString() }
          console.log('___>', this.tableData)
        }
      }

      this.tableData.push({ area: '指定配送区域和运费' })

      console.log('___>', this.tableData)
    }
    for (let i = 0; i < this.locatList.length; i++) {
      if (value.checkList.findIndex(item => item === this.locatList[i].provinceId) !== -1) {
        this.locatList[i]['state'] = true
      }
      let areaCity = this.locatList[i].areaCity
      if (areaCity !== undefined) {
        for (let j = 0; j < areaCity.length; j++) {
          if (value.checkList.findIndex(item => item === areaCity[j].cityId) !== -1) {
            areaCity[j]['state'] = true
          }
          // this.innerObj[areaCity[j].cityId]=0
          let distn = areaCity[j].areaDistrict
          for (let k = 0; k < distn.length; k++) {
            if (value.checkList.findIndex(item => item === distn[k].districtId) !== -1) {
              distn[k]['state'] = true
            }
          }
        }
      }
    }
  }

  // // computed
  get comput () {
    this.dialogStat = true
    return this.dialogStat
  }

  // method
  getData () {
    console.log(this.locatList)
  }
}

</script>
