<template>
  <div class="layoutTableMain">
    <table
      class="gridtable"
      width='100%'
    >
      <!--rowspan: 1,
       colspan: 1,-->
      <tbody>
        <tr
          v-for="(item,index) in columnData"
          :key="index"
        >
          <td
            :style="item.firstIsChecked?'backgroundColor:#eaf0ff':''"
            @click="handleToClick(index,'first')"
          ><span>+</span></td>
          <td
            :style="item.secondIsChecked?'backgroundColor:#eaf0ff':''"
            @click="handleToClick(index,'second')"
          ><span>+</span></td>
          <td
            :style="item.thirdIsChecked?'backgroundColor:#eaf0ff':''"
            @click="handleToClick(index,'third')"
          ><span>+</span></td>
          <td
            :style="item.fourIsChecked?'backgroundColor:#eaf0ff':''"
            @click="handleToClick(index,'four')"
          ><span>+</span></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
export default {
  props: {
    density: { // 布局类型flag
      type: Number,
      default: () => 0
    }
  },
  data () {
    return {
      timesNumber: [4, 5, 6, 7], // 行布局类型尺寸
      columnData: [], // 表格数据
      arrFour: ['first', 'second', 'third', 'four'], // 4X4数据
      isOpenLayout: false, // 是否开始布局
      nowCheckedArr: [], // 当前选中的项集合
      firstClickIndex: null, // 第一次选中的项所在的行
      lastClickIndex: null //  最后一次选中的项所在的行
    }
  },
  mounted () {
    //  初始化数据
    this.defaultData()
  },
  methods: {
    // 初始化数据
    defaultData () {
      // 4X4
      let obj1 = {}
      this.arrFour.forEach((item, index) => {
        obj1[`${item}Row`] = '1'
        obj1[`${item}Col`] = '1'
        obj1[`${item}Dis`] = false
        obj1[`${item}IsChecked`] = false
      })
      this.arrFour.forEach((item, index) => {
        let obj = JSON.parse(JSON.stringify(obj1))
        this.columnData.push(obj)
      })
      console.log(this.columnData)
    },
    // 点击表格单元
    handleToClick (index, item) {
      this.handleToScreen(index) // 首次筛选判断当前点击是不是首次点击
      if (!this.nowCheckedArr.length) {
        this.columnData[index][`${item}IsChecked`] = true
        this.nowCheckedArr.push(`${item}`)
        this.firstClickIndex = index //  记录第一次点击的行
      } else {
        if (this.firstClickIndex === index) { // 判断第二次点击是否点击的还是同行
          this.columnData[index][`${item}IsChecked`] = true
          this.handleToScreen()
          if (this.nowCheckedArr[0] === this.nowCheckedArr[1]) { // 若是同行则检查选中的项集合是否重复
            // 处理两次点击同一单元格事件
            console.log(this.nowCheckedArr)
            this.nowCheckedArr = []
          } else {
            // 处理两次点击不同单元格事件
            console.log(this.nowCheckedArr)
          }
        } else { // 若第二次点击的是不同行的单元格
          console.log(this.nowCheckedArr)
        }
      }
      // this.isOpenLayout = true
      // this.columnData[index][`${item}IsChecked`] = true
      // 筛选表格数据选中的项
      // this.handleToScreen()
      console.log(this.nowCheckedArr)
    },
    // 筛选出当前表格数据项选中的项
    handleToScreen (index) {
      this.columnData.forEach((itemC, indexC) => {
        let isCheckedArr = this.arrFour.filter((itm, ind) => {
          console.log(itemC[`${itm}IsChecked`])
          return itemC[`${itm}IsChecked`]
        })
        console.log(isCheckedArr)
        this.nowCheckedArr = isCheckedArr
        console.log(this.nowCheckedArr)
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.layoutTableMain {
  border: none !important;
  .gridtable {
    // border-collapse: collapse;
    // border: 1px solid #dddddd;
    tr {
      height: 75px;
      td {
        cursor: pointer;
        background-color: #fff;
        border: 1px solid #dddddd;
        padding-bottom: none;
        vertical-align: middle !important;
        span {
          display: inline-block;
        }
      }
    }
  }
}
</style>
