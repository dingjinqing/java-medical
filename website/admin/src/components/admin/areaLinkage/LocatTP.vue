<template>
  <div>
    <!--        <el-button type="text" @click=showData(true)>点击打开外层 Dialog</el-button>-->

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
                  @change="checkedSingleChange"
                  :disabled="scope.row.state"
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
                <!--                                <el-checkbox :label="scope.row.provinceId" :key="scope.row.provinceId">-->
                <!--                                    {{scope.row.provinceName}}-->
                <!--                                </el-checkbox>-->
                <el-checkbox
                  v-for="city in scope.row.areaCity"
                  :label="city.cityId"
                  :key="city.cityId"
                  @change="checkedSingleChange"
                  :disabled="city.state"
                >
                  <el-button
                    type="text"
                    @click="showInner(scope,city.cityId)"
                  >
                    {{city.cityName}}<span
                      v-if="innerObj[city.cityId]!==undefined&&innerObj[city.cityId]!==null&&innerObj[city.cityId]!==0"
                      style="color: crimson"
                    >({{innerObj[city.cityId]}})</span>
                  </el-button>
                </el-checkbox>
              </template>
            </el-table-column>
          </el-table>

        </div>
        <!--            {{locationList}}-->
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
            @change="checkedSingleChange"
            :disabled="city.state"
          >
            {{city.districtName}}
          </el-checkbox>
          <div
            slot="footer"
            class="dialog-footer"
          >
            <el-button
              @click=closeInner
              size="small"
            >取 消</el-button>
            <el-button
              type="primary"
              @click="confirInner"
              size="small"
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
        <!--                <el-button @click=reset>重 置</el-button>-->
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
    checkListT: Array,
    innerObjJ: Object,
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
        let a = 0
        for (let i = 0; i < this.cData.length; i++) {
          let areaCity = this.cData[i].areaCity
          a++
          if (areaCity !== undefined) {
            for (let j = 0; j < areaCity.length; j++) {
              a++
              // this.innerObj[areaCity[j].cityId]=0
              let distn = areaCity[j].areaDistrict
              for (let k = 0; k < distn.length; k++) {
                a++
              }
            }
          }
        }
        this.total = a
      },
      immediate: true
    },
    innerObjJ: {
      handler: function (newVal, oldVal) {
        // console.log(newVal)
        if (newVal !== undefined) {
          this.innerObj = newVal
        }
      },
      immediate: true
    },
    checkListT: {
      handler: function (newVal, oldVal) {
        // console.log(newVal)
        if (newVal !== undefined) {
          this.checkList = newVal
        }
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
      cData: [],
      checkedObj: {},
      total: 0,
      innerTemp: [],
      innerObj: [],
      allListTemp: []
    }
  },
  methods: {
    closeFunc: function () {
      this.checkList = []
      this.$emit('close', false)
    },
    getCheckList () {
      /* eslint-disable */
      let str = ''
      let areaList = []
      let idList = []
      let showArr = new Set()
      if (this.checkList.length === this.total) {
        for (let i = 0; i < this.cData.length; i++) {
          if (this.cData[i].provinceId !== 1) {
            idList.push(this.cData[i].provinceId)
            areaList.push(this.cData[i].provinceName)
            showArr.add(this.cData[i].provinceName)
            str += this.cData[i].provinceName
            str += ','
          }
        }
      } else {
        for (let i = 0; i < this.cData.length; i++) {
          // this.checkList.push(this.cData[i].provinceId);
          let areaCity = this.cData[i].areaCity
          if (areaCity !== undefined) {
            let arCount = 0
            let arArr = []
            let arStr = ''
            let arArea = []
            let arSet = new Set()
            for (let j = 0; j < areaCity.length; j++) {
              // this.checkList.push(areaCity[j].cityId);
              let distn = areaCity[j].areaDistrict
              let disCount = 0
              let disArr = []
              let disStr = ''
              let disArea = []
              for (let k = 0; k < distn.length; k++) {
                if (this.checkList.findIndex(item => item === distn[k].districtId) !== -1) {
                  disCount++
                  disArr.push(distn[k].districtId)
                  disArea.push(distn[k].districtName)
                  disStr += distn[k].districtName
                  disStr += ','
                }
              }
              if (disCount === distn.length && disCount !== 0) {
                arArr.push(areaCity[j].cityId)
                arArea.push(areaCity[j].cityName)
                str += areaCity[j].cityName
                str += ','
                arSet.add(areaCity[j].cityName)
              } else {
                arArr = arArr.concat(disArr)
                arArea = arArea.concat(disArea)
                if (disCount > 0) {
                  arSet.add(areaCity[j].cityName)
                }
              }
              // console.log("set-->>>",arSet)
              if (this.checkList.findIndex(item => item === areaCity[j].cityId) !== -1) {
                arCount++
                arArr.push(areaCity[j].cityId)
                arArea.push(areaCity[j].cityName)
                arStr += areaCity[j].cityName
                arStr += ','
              }
            }
            // console.log("set-->>>",arSet)
            if (arCount === areaCity.length && arCount !== 0) {
              idList.push(this.cData[i].provinceId)
              areaList.push(this.cData[i].provinceName)
              showArr.add(this.cData[i].provinceName)
              str += this.cData[i].provinceName
            } else {
              idList = idList.concat(arArr)
              areaList = areaList.concat(arArea)
              // showArr.add(this.cData[i].provinceName);
              // console.log("arSet-->",arSet)
              showArr = new Set([...showArr, ...arSet])
              // areaList.push(areaCity[j].cityName);
            }
          }
        }
      }

      // for (let i = 0; i < this.checkList.length; i++) {
      //     for (let j = 0; j < this.cData.length; j++) {
      //         if (this.checkList[i] !== 0) {
      //             if (this.checkList[i] === this.cData[j].provinceId) {
      //                 str += this.cData[j].provinceName;
      //                 str += ','
      //             }
      //         }
      //     }
      // }
      let set = new Set()
      let set2 = new Set()
      let param = {
        checkList: this.checkList,
        areaList: Array.from(new Set(areaList)),
        idList: Array.from(new Set(idList)),
        innerObj: this.innerObj,
        showArr: Array.from(showArr)
      }
      this.$emit('checkList', param)
      this.$emit('close', false)
    },
    checkChange () {
      // for (let i = 0; i < this.checkList.length; i++) {
      //     if (this.checkList[i] === 1) {
      //
      //         break;
      //     }
      // }
    },
    showInner (scope, id) {
      this.innerTemp = this.checkList
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
      // for (let i = 0; i < this.areaList.length; i++) {
      //     for (let j = 0; j < this.checkList.length; j++) {
      //         if (this.checkList[j] === this.areaList[i].districtId) {
      //             this.checkList.splice(j, 1)
      //         }
      //     }
      // }
      this.checkList = this.innerTemp
      this.innerVisible = false
    },
    reset () {
      this.checkList = []
    },
    confirInner () {
      // for (let i = 0; i < this.areaList.length; i++) {
      //     for (let j = 0; j < this.checkList.length; j++) {
      //         if (this.checkList[j] === this.areaList[i].districtId) {
      //             this.checkList.push(this.checkedSecond);
      //             break;
      //         }
      //     }
      // }
      // for (let i = 0; i < this.cData.length; i++) {
      //     let areaCity = this.cData[i].areaCity
      //     if (areaCity !== undefined) {
      //         for (let j = 0; j < areaCity.length; j++) {
      //             if (areaCity[j].cityId === this.checkedSecond) {
      //                 this.checkList.push(this.cData[i].provinceId);
      //             }
      //             let distn = areaCity[j].areaDistrict
      //             for (let k = 0; k < distn.length; k++) {
      //
      //             }
      //         }
      //     }
      // }
      this.innerVisible = false
    },
    checkedSingleChange (val, b) {
      let value = b.target._value
      let obj = Object.assign({}, this.innerObj)
      if (this.checkList.length !== this.total) {
        if (this.checkList.findIndex(item => item === 1) !== -1) {
          this.checkList.splice(this.checkList.findIndex(item => item === 1), 1)
        }
      }
      if (this.checkList.length === (this.total - 1)) {
        this.checkList.push(1)
      }
      if (val) {
        // this.checkedObj[b.target._value] = val;
        if (b.target._value === 1) {
          this.checkList = []
          for (let i = 0; i < this.cData.length; i++) {
            this.checkList.push(this.cData[i].provinceId)
            let areaCity = this.cData[i].areaCity
            if (areaCity !== undefined) {
              for (let j = 0; j < areaCity.length; j++) {
                this.checkList.push(areaCity[j].cityId)
                if (obj[areaCity[j].cityId] === undefined) {
                  obj[areaCity[j].cityId] = 0
                }
                let distn = areaCity[j].areaDistrict
                for (let k = 0; k < distn.length; k++) {
                  this.checkList.push(distn[k].districtId)
                  obj[areaCity[j].cityId]++
                }
              }
            }
          }
        } else {
          let dataCount = 0
          for (let i = 0; i < this.cData.length; i++) {
            let areaCity = this.cData[i].areaCity
            if (this.cData[i].provinceId === value) {
              if (areaCity !== undefined) {
                for (let j = 0; j < areaCity.length; j++) {
                  if (this.checkList.findIndex(item => item === areaCity[j].cityId) !== -1) {
                    this.checkList.splice(this.checkList.findIndex(item => item === areaCity[j].cityId), 1)
                  }
                  // if (obj[areaCity[j].cityId]===undefined) {
                  obj[areaCity[j].cityId] = 0
                  // }

                  this.checkList.push(areaCity[j].cityId)
                  let distn = areaCity[j].areaDistrict
                  for (let k = 0; k < distn.length; k++) {
                    if (this.checkList.findIndex(item => item === distn[k].districtId) !== -1) {
                      this.checkList.splice(this.checkList.findIndex(item => item === distn[k].districtId), 1)
                    }
                    this.checkList.push(distn[k].districtId)
                    obj[areaCity[j].cityId]++
                  }
                }
              }
              break
            } else {
              if (areaCity !== undefined) {
                let arCount = 0
                for (let j = 0; j < areaCity.length; j++) {
                  if (obj[areaCity[j].cityId] === undefined) {
                    obj[areaCity[j].cityId] = 0
                  }
                  if (areaCity[j].cityId === value) {
                    let distn = areaCity[j].areaDistrict

                    for (let k = 0; k < distn.length; k++) {
                      if (this.checkList.findIndex(item => item === distn[k].districtId) !== -1) {
                        this.checkList.splice(this.checkList.findIndex(item => item === distn[k].districtId), 1)
                      }
                      this.checkList.push(distn[k].districtId)
                      obj[areaCity[j].cityId]++
                    }
                  } else {
                    let distn = areaCity[j].areaDistrict
                    if (distn !== undefined) {
                      let disCount = 0
                      for (let k = 0; k < distn.length; k++) {
                        if (this.checkList.findIndex(item => item === distn[k].districtId) !== -1) {
                          disCount++
                        }
                        if (distn[k].districtId === value) {
                          obj[areaCity[j].cityId]++
                        }
                      }
                      if (disCount === distn.length && distn.length !== 0) {
                        if (this.checkList.findIndex(item => item === areaCity[j].cityId) !== -1) {
                          this.checkList.splice(this.checkList.findIndex(item => item === areaCity[j].cityId), 1)
                        }
                        this.checkList.push(areaCity[j].cityId)
                      }
                    }
                  }
                  if (this.checkList.findIndex(item => item === areaCity[j].cityId) !== -1) {
                    arCount++
                  }
                }
                if (arCount === areaCity.length && areaCity.length !== 0) {
                  if (this.checkList.findIndex(item => item === this.cData[i].provinceId) !== -1) {
                    this.checkList.splice(this.checkList.findIndex(item => item === this.cData[i].provinceId), 1)
                  }
                  this.checkList.push(this.cData[i].provinceId)
                }
              }
            }
          }
          for (let i = 0; i < this.cData.length; i++) {
            if (this.checkList.findIndex(item => item === this.cData[i].provinceId) !== -1) {
              dataCount++
            }
          }
          if (dataCount + 1 === this.cData.length) {
            this.checkList.push(1)
          }
        }
      } else {
        // delete this.checkedObj[b.target._value];
        if (b.target._value === 1) {
          this.checkList = []
          obj = {}
        } else {
          for (let i = 0; i < this.cData.length; i++) {
            let areaCity = this.cData[i].areaCity
            if (this.cData[i].provinceId === value) {
              if (areaCity !== undefined) {
                for (let j = 0; j < areaCity.length; j++) {
                  if (this.checkList.findIndex(item => item === areaCity[j].cityId) !== -1) {
                    this.checkList.splice(this.checkList.findIndex(item => item === areaCity[j].cityId), 1)
                  }
                  let distn = areaCity[j].areaDistrict
                  for (let k = 0; k < distn.length; k++) {
                    if (this.checkList.findIndex(item => item === distn[k].districtId) !== -1) {
                      this.checkList.splice(this.checkList.findIndex(item => item === distn[k].districtId), 1)
                      obj[areaCity[j].cityId] = obj[areaCity[j].cityId] - 1
                    }
                  }
                }
              }
              break
            } else {
              if (areaCity !== undefined) {
                for (let j = 0; j < areaCity.length; j++) {
                  if (areaCity[j].cityId === value) {
                    let distn = areaCity[j].areaDistrict
                    for (let k = 0; k < distn.length; k++) {
                      if (this.checkList.findIndex(item => item === distn[k].districtId) !== -1) {
                        this.checkList.splice(this.checkList.findIndex(item => item === distn[k].districtId), 1)
                        obj[areaCity[j].cityId] = obj[areaCity[j].cityId] - 1
                      }
                      if (this.checkList.findIndex(item => item === this.cData[i].provinceId) !== -1) {
                        this.checkList.splice(this.checkList.findIndex(item => item === this.cData[i].provinceId), 1)
                      }
                    }
                    break
                  } else {
                    let distn = areaCity[j].areaDistrict
                    if (distn !== undefined) {
                      for (let k = 0; k < distn.length; k++) {
                        if (distn[k].districtId === value) {
                          obj[areaCity[j].cityId] = obj[areaCity[j].cityId] - 1
                          if (this.checkList.findIndex(item => item === this.cData[i].provinceId) !== -1) {
                            this.checkList.splice(this.checkList.findIndex(item => item === this.cData[i].provinceId), 1)
                          }
                          if (this.checkList.findIndex(item => item === areaCity[j].cityId) !== -1) {
                            this.checkList.splice(this.checkList.findIndex(item => item === areaCity[j].cityId), 1)
                          }
                          break
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      this.innerObj = obj
      // console.log(this.innerObj)
    }
  }
}

</script>
