<template>
  <div>
    <!--<el-button type="text" @click=showData(true)>点击打开外层 Dialog</el-button>-->

    <el-dialog
      :visible.sync=outerVisible
      :close-on-click-modal=false
      :before-close="closeFunc"
    >
      <el-checkbox-group
        v-model="checkList"
        @change="checkChange()"
      >
        <div style="height: 420px;width: 100%;overflow:auto">
          <el-table
            :show-header="false"
            :data="locationList"
          >
            <el-table-column
              prop="area"
              label="可配送区域"
              width="200"
            >
              <template slot-scope="scope">
                <el-checkbox
                  :label="scope.row.provinceId"
                  :key="scope.row.provinceId"
                >
                  {{scope.row.provinceName}}
                </el-checkbox>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="首件（件）"
            >
              <template slot-scope="scope">
                <!--<el-checkbox :label="scope.row.provinceId" :key="scope.row.provinceId">-->
                <!-- {{scope.row.provinceName}}-->
                <!-- </el-checkbox>-->
                <el-checkbox
                  v-for="city in scope.row.areaCity"
                  :label="city.cityId"
                  :key="city.cityId"
                >
                  <el-button
                    type="text"
                    @click="showInner(scope,city.cityId)"
                  >
                    {{city.cityName}}({{city.areaDistrict.length}})
                  </el-button>
                </el-checkbox>
              </template>
            </el-table-column>
          </el-table>

        </div>
        <!--{{locationList}}-->
        <el-dialog
          width="40%"
          :visible.sync=innerVisible
          append-to-body
          :before-close="closeInner"
        >
          <el-checkbox
            v-for="city in areaList"
            :label="city.districtId"
            :key="city.districtId"
          >
            {{city.districtName}}
          </el-checkbox>
          <div
            slot="footer"
            class="dialog-footer"
          >
            <el-button @click=closeInner>取 消</el-button>
            <el-button
              type="primary"
              @click="confirInner"
            >确 定</el-button>
          </div>
        </el-dialog>
      </el-checkbox-group>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          @click=closeFunc
          size="small"
        >取 消</el-button>
        <el-button
          @click=reset
          size="small"
        >重 置</el-button>
        <el-button
          type="primary"
          @click="getCheckList"
          size="small"
        >确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

export default {
  name: 'locat-t-p',
  props: {
    locationList: Array,
    outerVisible: {
      type: Boolean,
      default: false
    },
    showData: {
      type: Function,
      default: function () {
        return false
      }

    }
  },
  watch: {
    locationList: {
      handler: function (newVal, oldVal) {
        this.cData = newVal // newVal即是chartData
      },
      immediate: true
    }
  },
  data () {
    return {
      // outerVisible:false,
      innerVisible: false,
      checkList: [],
      areaList: [],
      checkedSecond: 0,
      cData: []
    }
  },
  methods: {
    closeFunc: function () {
      this.checkList = []
      this.$emit('close', false)
    },
    getCheckList () {
      this.$emit('checkList', this.checkList)
      this.$emit('close', false)
    },
    checkChange () {
      for (let i = 0; i < this.checkList.length; i++) {
        if (this.checkList[i] === 1) {
          this.checkList = []
          for (let j = 0; j < this.cData.length; j++) {
            if (this.cData[j].provinceId !== 1) {
              this.checkList.push(this.cData[j].provinceId)
              for (let k = 0; k < this.cData[j].areaCity.length; k++) {
                this.checkList.push(this.cData[j].areaCity[k].cityId)
              }
            }
          }
          this.checkList.push(1)
          break
        }
      }
    },
    showInner (scope, id) {
      let list = scope.row.areaCity
      for (let i = 0; i < list.length; i++) {
        if (list[i].cityId === id) {
          this.areaList = list[i].areaDistrict
          this.checkedSecond = list[i].cityId
          break
        }
      }
      this.innerVisible = true
    },
    closeInner () {
      for (let i = 0; i < this.areaList.length; i++) {
        for (let j = 0; j < this.checkList.length; j++) {
          if (this.checkList[j] === this.areaList[i].districtId) {
            this.checkList.splice(j, 1)
          }
        }
      }
      this.innerVisible = false
    },
    reset () {
      this.checkList = []
    },
    confirInner () {
      for (let i = 0; i < this.areaList.length; i++) {
        for (let j = 0; j < this.checkList.length; j++) {
          if (this.checkList[j] === this.areaList[i].districtId) {
            this.checkList.push(this.checkedSecond)
            break
          }
        }
      }
      for (let i = 0; i < this.cData.length; i++) {
        if (this.cData[i].areaCity !== undefined) {
          for (let j = 0; j < this.cData[i].areaCity.length; j++) {
            if (this.cData[i].areaCity[j].cityId === this.checkedSecond) {
              console.log(this.checkedSecond)
              this.checkList.push(this.cData[i].provinceId)
            }
          }
        }
      }

      this.innerVisible = false
    }
  }

}

</script>
