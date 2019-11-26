<template>
  <div>
    <el-table :data="showTableData">
      <el-table-column
        prop="area_text"
        label="可配送区域"
      >
        <template slot-scope="scope">
          <div>{{scope.row.area_text}}</div>
          <div>
            <el-button
              type="text"
              @click="editOneData(scope.row)"
            >编辑</el-button>
            <el-button
              type="text"
              style="color:red;"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </div>
          <LocatTP
            :location-list="editLocationList"
            :outer-visible="editOuterVisible"
            @close="editShowData"
            :inner-obj-j="editInnerObjJ"
            :check-list-t="editCheckList"
            @checkList="editRegionList"
          />
        </template>
      </el-table-column>
      <template v-if="isRegion">
        <el-table-column
          prop="first_num"
          label="首件（件）"
        >
          <template slot-scope="scope">
            <el-form
              :ref="'firstNum' + scope.$index"
              :model="scope.row"
            >
              <el-form-item
                prop="first_num"
                :rules="[{ required: true, validator: validateNum, message: '格式不正确', trigger: 'blur' }]"
              >
                <el-input
                  v-model.number="scope.row.first_num"
                  class="column-input"
                ></el-input>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          prop="first_fee"
          label="运费（元）"
        >
          <template slot-scope="scope">
            <el-form
              :ref="'firstFee' + scope.$index"
              :model="scope.row"
            >
              <el-form-item
                prop="first_fee"
                :rules="[{validator: validateMoney, message: '格式不正确', trigger: 'blur'}]"
              >
                <el-input
                  v-model.number="scope.row.first_fee"
                  class="column-input"
                ></el-input>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          prop="continue_num"
          label="续件（件）"
        >
          <template slot-scope="scope">
            <el-form
              :ref="'continueNum' + scope.$index"
              :model="scope.row"
            >
              <el-form-item
                prop="continue_num"
                :rules="[{validator: validateNum, message: '格式不正确', trigger: 'blur'}]"
              >
                <el-input
                  v-model.number="scope.row.continue_num"
                  class="column-input"
                ></el-input>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          prop="continue_fee"
          label="续费（元）"
        >
          <template slot-scope="scope">
            <el-form
              :ref="'continueFee' + scope.$index"
              :model="scope.row"
            >
              <el-form-item
                prop="continue_fee"
                :rules="[{validator: validateMoney, message: '格式不正确', trigger: 'blur'}]"
              >
                <el-input
                  v-model.number="scope.row.continue_fee"
                  class="column-input"
                ></el-input>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
      </template>
      <template v-else>
        <el-table-column
          prop="fee_0_condition"
          label="设置包邮条件"
          width="600px"
        >
          <template slot-scope="scope">
            <el-form
              :ref="'columnForm' + scope.$index"
              :model="scope.row"
              :inline="true"
            >
              <el-form-item>
                <el-select
                  v-model="scope.row.fee_0_condition"
                  class="column-input"
                >
                  <el-option
                    :value="1"
                    label="件数"
                  ></el-option>
                  <el-option
                    :value="2"
                    label="金额"
                  ></el-option>
                  <el-option
                    :value="3"
                    label="件数+金额"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item
                v-if="scope.row.fee_0_condition === 1"
                label="满"
                prop="fee_0_con1_num"
                :rules="[{ validator: feeConditionRule, message:'包邮件数不能小于1', trigger: 'blur' }]"
              >
                <el-input
                  v-model.number="scope.row.fee_0_con1_num"
                  style="width:80px"
                ></el-input>件包邮
              </el-form-item>
              <el-form-item
                v-if="scope.row.fee_0_condition === 2"
                label="满"
                prop="fee_0_con2_num"
                :rules="[{ validator: feeConditionRule, message:'包邮金额不能小于1', trigger: 'blur' }]"
              >
                <el-input
                  v-model.number="scope.row.fee_0_con2_num"
                  style="width:80px"
                ></el-input>元包邮
              </el-form-item>
              <el-form-item
                v-if="scope.row.fee_0_condition === 3"
                label="满"
                prop="fee_0_con3_num"
                :rules="[{ validator: feeConditionRule, message:'包邮件数不能小于1', trigger: 'blur' }]"
              >
                <el-input
                  v-model.number="scope.row.fee_0_con3_num"
                  style="width:80px"
                ></el-input>
              </el-form-item>
              <el-form-item
                v-if="scope.row.fee_0_condition === 3"
                label="件，"
                prop="fee_0_con3_fee"
                :rules="[{ validator: feeConditionRule,  message:'包邮金额不能小于1', trigger: 'blur' }]"
              >
                <el-input
                  v-model.number="scope.row.fee_0_con3_fee"
                  style="width:80px"
                ></el-input>元包邮
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
      </template>
    </el-table>
    <div class="appoint">
      <el-button
        type="text"
        @click="outerVisible=true"
      >{{appointContent}}</el-button>
    </div>
    <LocatTP
      :location-list="locationList"
      :outer-visible="outerVisible"
      @close="showData"
      :inner-obj-j="innerObjJ"
      :check-list-t="checkListT"
      @checkList="changeRegionList"
    />
  </div>
</template>

<script>
import LocatTP from './LocatTP.vue'
import RulesMixins from '@/mixins/RulesMixins' // mixin混入
import publicMixins from '@/mixins/publicMixins'
import chinaData from '../../../assets/china-data'
import { deepCloneObj } from '@/util/deepCloneObj'
// import { getAreaSelect } from '../../../api/admin/goodsManage/deliverTemplate/deliverTemplate.js'
export default {
  name: 'locatTPTable',
  components: { LocatTP },
  mixins: [RulesMixins, publicMixins],
  props: {
    // 判断是包邮还是配送
    isRegion: {
      type: Boolean,
      required: true
    },
    // 标题内容
    appointContent: {
      type: String,
      required: true
    },
    editLocation: {
      type: Array,
      required: false
    }
  },
  watch: {
    editLocation: {
      handler (newVal, oldVal) {
        this.affecta()
      },
      immediate: true
    }
  },
  data () {
    return {
      // 用与location-list的省市县三级数据
      locationList: [],
      // 用于显示隐藏添加指定配送区域和运费（outer-visible）
      outerVisible: false,
      // 用于inner-obj-j传入选中城市城区的数字,(选中后组件返回，在value中)
      innerObjJ: {},
      // check-list-t调用组件时传入已选中的对象（Array）(选中后组件返回，在value中)
      checkListT: [],
      // 请求报文的goodsDeliverTemplateFeeConditionParam数组数据
      tableData: [],
      // (用于展示)的请求报文的goodsDeliverTemplateFeeConditionParam数组数据
      showTableData: [],
      /**
       *  edit 编辑的模块
       */
      editLocationList: [],
      editInnerObjJ: {},
      editCheckList: [],
      area_list: '', // 需要修改的数据的area_list字符串
      editOuterVisible: false
    }
  },
  // 请求获取数据
  created () {
    this.affecta()
  },
  methods: {
    validateNum (rule, value, callback) {
      // 正整数
      var re = /^[1-9]\d*$/
      if (value === '') {
        callback(new Error('请填写件数'))
      } else if (!re.test(value)) {
        callback(new Error('格式不正确'))
      } else {
        callback()
      }
    },
    validateMoney (rule, value, callback) {
      // 非负数
      var re = /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/
      if (value === '') {
        callback(new Error('请填写运费'))
      } else if (!re.test(value)) {
        callback(new Error('格式不正确'))
      } else {
        callback()
      }
    },
    affecta () {
      this.locationList = deepCloneObj(chinaData)

      // getAreaSelect().then(res => {
      //   const { error, content } = res
      //   if (error === 0) {
      //     content.unshift({ provinceId: 1, provinceName: '全部' })
      //     this.locationList = content // 初始化参数 也就是请求获取的数据
      //   }
      // }).catch(err => console.log(err))
      // 判断props有没有传递参数什么的
      if (this.editLocation && this.editLocation.length > 0) {
        // 调用组件，更新this.locationList，innerObjJ，checkListT
        this.showTableData = this.editLocation.map(item => {
          return {
            ...item,
            area_list: JSON.stringify(item.area_list)
          }
        })

        this.tableData = this.editLocation
        this.updateData(this.showTableData)
      } else {
        this.showTableData = []
        this.tableData = []
      }
    },
    // 定义验证规则
    feeConditionRule (rule, value, callback) {
      if (Number.isInteger(Number(value)) && Number(value) > 0) {
        callback()
      } else {
        callback(new Error('必须为大于0'))
      }
    },
    // 验证规则，验证是否正确
    /* eslint-disable */
    isYes(area_list, key) {
      return (
        area_list.includes(key.toString().slice(0, 4)) ||
        (/\d{2}[0]{4},?/.test(area_list) &&
          area_list.includes(key.toString().slice(0, 2)))
      )
    },
    // 删除一行的功能
    handleDelete(row) {
      this.$message.success('删除区域成功!')
      // 删掉显示页面的数据
      this.showTableData = this.showTableData.filter(
        item => item.area_list !== row.area_list
      )
      // 同时删除返回给后端的数据
      this.tableData = this.tableData.filter(
        item => item.area_list !== row.area_list
      )
      // 删除选中的id
      this.checkListT = this.checkListT.filter(
        id => !this.isYes(row.area_list, id)
      )
      // 让要删除的innerObjJ对应的数值归0
      Object.keys(this.innerObjJ).forEach(key => {
        // 本次key是不是要删除的那个?
        if (this.isYes(row.area_list, key)) {
          this.innerObjJ[key] = 0 // 让对应的市的显示归0
        }
      })
      // 重新递归让删除的stats变成false
      this.locationList = this.addState(
        this.locationList,
        this.checkListT
      )
    },
    // 开启关闭省市县三级的模态框
    showData(flag) {
      // 取消 还原
      this.outerVisible = flag
    },
    // 获取返回的数据
    changeRegionList(value) {
      this.$message.success('添加区域成功!')
      // 当数据没有改变的话，不执行
      const check = value.checkList.filter(
        id => !this.checkListT.includes(id)
      )

      if (check.length === 0) return // 全部相同，没有发生改变 直接结束
      // 页面展示数据
      const {
        data,
        isAdd,
        showArrStr,
        checkIdArr
      } = this.selectTableData(
        value.showArr,
        value.idList,
        this.showTableData
      )
      if (isAdd) {
        // 是否添加
        this.showTableData.push(
          this.templateData(checkIdArr, showArrStr)
        )
      } else {
        // 替换
        this.showTableData = data
      }
      // 请求报文数据
      const areaData = this.selectTableData(
        value.areaList,
        value.idList,
        this.tableData
      )
      if (isAdd) {
        // 是否添加
        this.tableData.push(
          this.templateData(areaData.checkIdArr, areaData.showArrStr)
        )
      } else {
        // 替换
        this.tableData = areaData.data
      }
      this.checkListT.push(...value.checkList)
      this.innerObjJ = value.innerObj
      this.locationList = this.addState(
        this.locationList,
        this.checkListT
      )
    },
    // 数据模板
    templateData(checkIdArr, showArrStr) {
      let template = {}
      if (this.isRegion) {
        template = {
          first_num: 0,
          first_fee: 0,
          continue_num: 0,
          continue_fee: 0
        }
      } else {
        template = {
          fee_0_condition: 1, // 1.件数，2.金额，3：件数+金额
          fee_0_con1_num: 0, // 件数的时候的最小件数
          fee_0_con2_num: 0, // 金额的时候的最小金额
          fee_0_con3_num: 0, // 第三种的最小件数
          fee_0_con3_fee: 0 // 第三种的最小金额
        }
      }
      return {
        // 遍历筛选
        area_list: checkIdArr,
        area_text: showArrStr, // 切割剩下的就是这次添加的
        ...template
      }
    },
    // 给父组件触发的事件，用于获取子组件的事件
    getTableData() {
      let val = 0, // 用于验证是否全部通过验证,当val为0则表示全部验证通过，返回数据给父组件
        tableData = [] // 用于存储表格上面的数据，并返回给父组件
      // console.log(this.showTableData);
      this.showTableData.forEach((item, index) => {
        tableData[index] = {
          ...item,
          area_list: this.tableData[index].area_list,
          area_text: this.tableData[index].area_text
        }
        // 判断是指定包邮的还是指定配送的 ，然后验证
        if (this.isRegion) {
          this.$refs['firstNum' + index].validate(required => {
            if (!required) {
              return val++
            }
          })
          this.$refs['firstFee' + index].validate(required => {
            if (!required) {
              return val++
            }
          })
          this.$refs['continueNum' + index].validate(required => {
            if (!required) {
              return val++
            }
          })
          this.$refs['continueFee' + index].validate(required => {
            if (!required) {
              return val++
            }
          })
        } else {
          this.$refs['columnForm' + index].validate(required => {
            if (!required) {
              return val++
            }
          })
        }
      })

      var arr = []
      if (val === 0) {
        // 成功的情况下，全部归0，并在父组件发送请求
        arr = new Array(true, tableData);
      } else {
        arr = new Array(false, tableData);
      }
      return arr
    },

    editShowData(flag) {
      // 取消 还原
      this.locationList = this.addState(
        this.locationList,
        this.checkListT
      )
      this.editOuterVisible = flag
    },
    editOneData({ area_list }) {
      // console.log(area_list);
      this.area_list = area_list // 保存修改前的数据
      // 点击编辑，弹出弹窗，并且对editLocationList，editInnerObjJ，editCheckList进行赋值
      const newArea_list = []
      this.checkListT.forEach(id => {
        if (this.isYes(area_list, id)) {
          newArea_list.push(id)
        }
      })
      this.editCheckList = newArea_list
      // 选择出不是本次修改的拿来禁用
      const noCurrentList = this.checkListT.filter(
        id => !newArea_list.includes(id)
      )
      // 取消禁用
      this.editLocationList = this.addState(
        this.locationList,
        noCurrentList
      )
      const editInnerObjJ = {}
      Object.keys(this.innerObjJ).forEach(key => {
        if (this.isYes(area_list, key)) {
          editInnerObjJ[key] = this.innerObjJ[key]
        }
      })
      this.editInnerObjJ = editInnerObjJ
      this.editOuterVisible = true
    },
    // 确认后修改的数据
    editRegionList(val) {
      this.$message.success('修改区域成功!')
      // 修改后，存储状态和数据
      if (this.area_list === val.idList.toString()) return // 两者相等，就是没发生改变，不执行下面操作
      // 不相等 执行以下操作
      this.showTableData = this.showTableData.map((data, index) => {
        if (data.area_list === this.area_list) {
          // 对应的那个进行改变
          data.area_list = val.idList.toString()
          data.area_text = val.showArr.toString()
          // 同时改变需要传递给请求报文的数据
          this.tableData[index].area_list = val.idList.toString()
          this.tableData[index].area_text = val.areaList.toString()
        }
        return data
      })
      // 先删除掉之前的 然后把新获得的push进去
      this.checkListT = this.checkListT.filter(
        id => !this.editCheckList.includes(id)
      )
      this.checkListT.push(...val.checkList)
      // 禁用已经选中的 改变state
      this.locationList = this.addState(
        this.locationList,
        this.checkListT
      )
      // 改变innerObjJ
      Object.keys(this.innerObjJ).forEach(key => {
        if (this.innerObjJ[key] === this.editInnerObjJ[key]) {
          this.innerObjJ[key] = 0
        }
      })
      Object.keys(this.innerObjJ).forEach(key => {
        if (!this.innerObjJ[key] && val.innerObj[key]) {
          this.innerObjJ[key] = val.innerObj[key]
        }
      })
    },
    updateData(editLocation) {
      // this.locationList，innerObjJ，checkListT
      // 通过this.locationList 和 editLocation 获取到真正的checkListT,innerObjJ 然后显示
      // 选取出 选中的 id
      const checkListT = this.selectCheckList(editLocation)
      if (checkListT.length === 3647) {
        this.locationList[0].state = true
        // 全部省市区选中
        checkListT.push(1)
      }
      this.checkListT = checkListT
    },
    // 筛选出checkList
    selectCheckList(editLocation) {
      const checkList = []
      editLocation.forEach(item => {
        checkList.push(...JSON.parse(item.area_list))
      })
      return this.getIdList(checkList)
    },
    // 通过this.locationList拉取id , 选中this.locationList,以及...
    getIdList(checkList) {
      const idList = [] // 存储id
      const strIdList = checkList.toString() // 获取选中的list
      const innerObj = {} // 存储id对象
      this.locationList.forEach(item => {
        if (
          strIdList.includes(item.provinceId) &&
          item.provinceId !== 1
        ) {
          // 完全相等,表示自身和以下的省，区都遍历进idList
          idList.push(item.provinceId)
          item.state = true
          item.areaCity &&
            item.areaCity.forEach(city => {
              this.addCheckData(innerObj, city, idList)
            })
        }
        // 当和省级不想等的情况下
        else if (item.areaCity) {
          item.areaCity.forEach(city => {
            if (strIdList.includes(city.cityId)) {
              this.addCheckData(innerObj, city, idList)
            } else if (city.areaDistrict) {
              // 当和市不想等的情况下
              city.areaDistrict.forEach(district => {
                innerObj[city.cityId] = 0
                if (strIdList.includes(district.districtId)) {
                  idList.push(district.districtId)
                  innerObj[city.cityId]++
                  district.state = true
                }
              })
            }
          })
        }
      })
      this.innerObjJ = innerObj
      return idList
    },
    // 添加选中的各种
    addCheckData(innerObj, city, idList) {
      innerObj[city.cityId] = 0
      city.state = true
      idList.push(city.cityId)
      city.areaDistrict &&
        city.areaDistrict.forEach(district => {
          district.state = true
          innerObj[city.cityId]++
          idList.push(district.districtId)
        })
    }
  }
}
</script>
<style>
.appoint {
  padding: 15px 0;
  border-bottom: 1px solid #eee;
}
div.column-input > .el-input__inner {
  width: 80px;
}
div.el-select.column-input {
  width: 130px;
}
</style>
