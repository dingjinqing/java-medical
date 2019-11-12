<template>
  <div class="layoutTableMain">
    <table
      class="gridtable"
      width='300px'
      height='300px'
    >
      <!--rowspan: 1,
       colspan: 1,-->
      <tbody>
        <tr
          v-for="(item,index) in columnData"
          :key="index"
          :style="'height:'+trHeight"
        >
          <td
            :style="((item['0IsChecked']||item['0IsSlide'])?'backgroundColor:#eaf0ff;':'')+((item['0IsBorder']&&item['0controlClick'])?'borderColor:#6e86cc;':'')+(item['0Dis']?'display:none;':'')"
            @click="handleToClick(index,0)"
            :colspan="item['0Col']"
            :rowspan="item['0Row']"
            @mouseenter="enter(index,0)"
            @mouseleave="leave(index,0)"
          >
            <div :style="item['0img_url']&&item['0IsBorder']?'background:url('+item['0img_url']+') no-repeat;background-size:cover;':''">
              <span v-if="!item['0IsBorder']&&!item['0img_url']">+</span><span v-else>{{!((item['0Col']==='1')&&(item['0Row']==='1')||item['0img_url'])?(Number(item['0Col'])*188+'x'+Number(item['0Row'])*188+$t('magicMap.pixelEquivalent')):''}}<i
                  @click.stop="handleToDelCell(index,0)"
                  :style="'background:url('+$imageHost+'/image/admin/icon_delete.png)'"
                  v-if="(item['0controlClick']&&item['0controlDelIconDis'])"
                ></i></span>
            </div>
          </td>
          <td
            :style="((item['1IsChecked']||item['1IsSlide'])?'backgroundColor:#eaf0ff;':'')+((item['1IsBorder']&&item['1controlClick'])?'borderColor:#6e86cc;':'')+(item['1Dis']?'display:none;':'')"
            @click="handleToClick(index,1)"
            :colspan="item['1Col']"
            :rowspan="item['1Row']"
            @mouseenter="enter(index,1)"
            @mouseleave="leave(index,1)"
          >
            <div :style="item['1img_url']&&item['1IsBorder']?'background:url('+item['1img_url']+') no-repeat;background-size:cover;':''">
              <span v-if="!item['1IsBorder']&&!item['1img_url']">+</span><span v-else>{{!((item['1Col']==='1')&&(item['1Row']==='1')||item['1img_url'])?(Number(item['1Col'])*188+'x'+Number(item['1Row'])*188+$t('magicMap.pixelEquivalent')):''}}<i
                  @click.stop="handleToDelCell(index,1)"
                  :style="'background:url('+$imageHost+'/image/admin/icon_delete.png)'"
                  v-if="(item['1controlClick']&&item['1controlDelIconDis'])"
                ></i></span>
            </div>
          </td>
          <td
            :style="((item['2IsChecked']||item['2IsSlide'])?'backgroundColor:#eaf0ff;':'')+((item['2IsBorder']&&item['2controlClick'])?'borderColor:#6e86cc;':'')+(item['2Dis']?'display:none;':'')"
            @click="handleToClick(index,2)"
            :colspan="item['2Col']"
            :rowspan="item['2Row']"
            @mouseenter="enter(index,2)"
            @mouseleave="leave(index,2)"
          >
            <div :style="item['2img_url']&&item['2IsBorder']?'background:url('+item['2img_url']+') no-repeat;background-size:cover;':''">
              <span v-if="!item['2IsBorder']&&!item['2img_url']">+</span><span v-else>{{!((item['2Col']==='1')&&(item['2Row']==='1')||item['2img_url'])?(Number(item['2Col'])*188+'x'+Number(item['2Row'])*188+$t('magicMap.pixelEquivalent')):''}}<i
                  @click.stop="handleToDelCell(index,2)"
                  :style="'background:url('+$imageHost+'/image/admin/icon_delete.png)'"
                  v-if="(item['2controlClick']&&item['2controlDelIconDis'])"
                ></i></span>
            </div>
          </td>
          <td
            :style="((item['3IsChecked']||item['3IsSlide'])?'backgroundColor:#eaf0ff;':'')+((item['3IsBorder']&&item['3controlClick'])?'borderColor:#6e86cc;':'')+(item['3Dis']?'display:none;':'')"
            @click="handleToClick(index,3)"
            :colspan="item['3Col']"
            :rowspan="item['3Row']"
            @mouseenter="enter(index,3)"
            @mouseleave="leave(index,3)"
          >
            <div :style="item['3img_url']&&item['3IsBorder']?'background:url('+item['3img_url']+') no-repeat;background-size:cover;':''">
              <span v-if="!item['3IsBorder']&&!item['3img_url']">+</span><span v-else>{{!((item['3Col']==='1')&&(item['3Row']==='1')||item['3img_url'])?(Number(item['3Col'])*188+'x'+Number(item['3Row'])*188+$t('magicMap.pixelEquivalent')):''}}<i
                  @click.stop="handleToDelCell(index,3)"
                  :style="'background:url('+$imageHost+'/image/admin/icon_delete.png)'"
                  v-if="(item['3controlClick']&&item['3controlDelIconDis'])"
                ></i></span>
            </div>
          </td>

          <td
            v-if="density===1||density===2||density===3"
            :style="((item['4IsChecked']||item['4IsSlide'])?'backgroundColor:#eaf0ff;':'')+((item['4IsBorder']&&item['4controlClick'])?'borderColor:#6e86cc;':'')+(item['4Dis']?'display:none;':'')"
            @click="handleToClick(index,4)"
            :colspan="item['4Col']"
            :rowspan="item['4Row']"
            @mouseenter="enter(index,4)"
            @mouseleave="leave(index,4)"
          >
            <div :style="item['4img_url']&&item['4IsBorder']?'background:url('+item['4img_url']+') no-repeat;background-size:cover;':''">
              <span v-if="!item['4IsBorder']&&!item['4img_url']">+</span><span v-else>{{!((item['4Col']==='1')&&(item['4Row']==='1')||item['4img_url'])?(Number(item['4Col'])*188+'x'+Number(item['4Row'])*188+$t('magicMap.pixelEquivalent')):''}}<i
                  @click.stop="handleToDelCell(index,4)"
                  :style="'background:url('+$imageHost+'/image/admin/icon_delete.png)'"
                  v-if="(item['4controlClick']&&item['4controlDelIconDis'])"
                ></i></span>
            </div>
          </td>

          <td
            v-if="density===2||density===3"
            :style="((item['5IsChecked']||item['5IsSlide'])?'backgroundColor:#eaf0ff;':'')+((item['5IsBorder']&&item['5controlClick'])?'borderColor:#6e86cc;':'')+(item['5Dis']?'display:none;':'')"
            @click="handleToClick(index,5)"
            :colspan="item['5Col']"
            :rowspan="item['5Row']"
            @mouseenter="enter(index,5)"
            @mouseleave="leave(index,5)"
          >
            <div :style="item['5img_url']&&item['5IsBorder']?'background:url('+item['5img_url']+') no-repeat;background-size:cover;':''">
              <span v-if="!item['5IsBorder']&&!item['5img_url']">+</span><span v-else>{{!((item['5Col']==='1')&&(item['5Row']==='1')||item['5img_url'])?(Number(item['5Col'])*188+'x'+Number(item['5Row'])*188+$t('magicMap.pixelEquivalent')):''}}<i
                  @click.stop="handleToDelCell(index,5)"
                  :style="'background:url('+$imageHost+'/image/admin/icon_delete.png)'"
                  v-if="(item['5controlClick']&&item['5controlDelIconDis'])"
                ></i></span>
            </div>
          </td>

          <td
            v-if="density===3"
            :style="((item['6IsChecked']||item['6IsSlide'])?'backgroundColor:#eaf0ff;':'')+((item['6IsBorder']&&item['6controlClick'])?'borderColor:#6e86cc;':'')+(item['6Dis']?'display:none;':'')"
            @click="handleToClick(index,6)"
            :colspan="item['6Col']"
            :rowspan="item['6Row']"
            @mouseenter="enter(index,6)"
            @mouseleave="leave(index,6)"
          >
            <div :style="item['6img_url']&&item['6IsBorder']?'background:url('+item['6img_url']+') no-repeat;background-size:cover;':''">
              <span v-if="!item['6IsBorder']&&!item['6img_url']">+</span><span v-else>{{!((item['6Col']==='1')&&(item['6Row']==='1')||item['6img_url'])?(Number(item['6Col'])*188+'x'+Number(item['6Row'])*188+$t('magicMap.pixelEquivalent')):''}}<i
                  @click.stop="handleToDelCell(index,6)"
                  :style="'background:url('+$imageHost+'/image/admin/icon_delete.png)'"
                  v-if="(item['6controlClick']&&item['6controlDelIconDis'])"
                ></i></span>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
export default {
  props: {
    density: { // 控制布局类型flag
      type: Number,
      default: () => 0
    },
    jumpLink: String || Number, // 传来的跳转链接路径
    imgUrl: String, // 传来的图片路径
    customModulesBackData: Object // 回显数据
  },
  data () {
    return {
      timesNumber: [4, 5, 6, 7], // 行布局类型尺寸
      columnData: [], // 表格数据
      arrFour: [0, 1, 2, 3, 4, 5, 6],
      isOpenLayout: false, // 是否开始布局
      nowCheckedArr: [], // 当前选中的项集合
      firstClickRowIndex: null, // 第一次选中的项所在的行
      lastClickRowIndex: null, //  最后一次选中的项所在的行
      firstClickColindex: null, // 第一次选中的项在的列
      lastClickColindex: null, // 最后一次选中的项所在的列
      choiseCellNumber: 1, // 点击次数标识
      isOpenSlide: false, // 是否开启滑动flag
      nowCheckedCell: [null, null], // 当前高亮单元格坐标坐标
      trHeight: null // tr动态行高
    }
  },
  watch: {
    nowCheckedCell: {// 获取当前高亮单元格坐标
      handler (newData) { // 把当前高亮的跳转链接路径数据和图片数据传回父组件中
        console.log(newData)
        console.log(this.columnData[newData[0]][`${newData[1]}jump_link`])
        let obj = {
          jump_link: this.columnData[newData[0]][`${newData[1]}jump_link`],
          img_url: this.columnData[newData[0]][`${newData[1]}img_url`],
          cols: this.columnData[newData[0]][`${newData[1]}Col`],
          rows: this.columnData[newData[0]][`${newData[1]}Row`]
        }
        this.$emit('handleToGetLinkpathImgUrl', obj)
      },
      deep: true
    },
    density: {
      handler (newData) {
        console.log(newData)
        this.columnData = []
        switch (newData) {
          case 0:
            this.arrFour = [0, 1, 2, 3]
            this.trHeight = '75px'
            break
          case 1:
            this.arrFour = [0, 1, 2, 3, 4]
            this.trHeight = '60px'
            break
          case 2:
            this.arrFour = [0, 1, 2, 3, 4, 5]
            this.trHeight = '50px'
            break
          case 3:
            this.arrFour = [0, 1, 2, 3, 4, 5, 6]
            this.trHeight = '42px'
        }
        console.log(this.customModulesBackData)

        //  初始化数据
        this.defaultData()
      },
      immediate: true
    },
    jumpLink (newData) { // 监听跳转路径变化
      console.log(this.nowCheckedCell)
      if (this.nowCheckedCell[0] !== null && this.nowCheckedCell[1] !== null) {
        this.columnData[this.nowCheckedCell[0]][`${this.nowCheckedCell[1]}jump_link`] = newData
        this.handleToSaveData()
        console.log(newData)
      }
    },
    imgUrl (newData) {
      if (this.nowCheckedCell[0] !== null && this.nowCheckedCell[1] !== null) {
        this.columnData[this.nowCheckedCell[0]][`${this.nowCheckedCell[1]}img_url`] = newData
        this.handleToSaveData()
        console.log(this.columnData[this.nowCheckedCell[0]][`${this.nowCheckedCell[1]}img_url`])
      }
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 初始化数据
    defaultData () {
      let obj1 = {}
      console.log(this.density)
      this.arrFour.forEach((item, index) => {
        obj1[`${item}Row`] = '1' // 控制行合并
        obj1[`${item}Col`] = '1' // 控制列合并
        obj1[`${item}Dis`] = false // 控制显示隐藏
        obj1[`${item}IsChecked`] = false // 控制背景色
        obj1[`${item}IsBorder`] = false // 控制高亮
        obj1[`${item}IsSlide`] = false // 控制滑动显示背景色
        obj1[`${item}controlClick`] = true // 控制点击切换高亮
        obj1[`${item}controlDelIconDis`] = false // 控制删除icon鼠标滑过的显示隐藏
        obj1[`${item}y`] = item + 1 // 保存数据使用
        obj1[`${item}jump_link`] = '' // 保存数据使用
        obj1[`${item}img_url`] = '' // 保存数据使用
      })
      this.arrFour.forEach((item, index) => {
        let obj = JSON.parse(JSON.stringify(obj1))
        this.columnData.push(obj)
        this.arrFour.forEach((itemC, indexC) => {
          this.columnData[item][`${itemC}x`] = item + 1 // 保存数据使用
        })
      })
      if (this.customModulesBackData) { // 初始回显
        this.handleToEchoDisplay()
      }
      this.handleToSaveData()
      console.log(this.columnData)
    },
    // 点击表格单元
    handleToClick (index, item) {
      // 判断点击的是不是已合并的单元格单元格
      let JudgeHighet = this.handleToJudgeHighet(index, item)
      if (!JudgeHighet) return
      this.handleToScreen() // 首次筛选判断当前点击是不是首次点击
      if (this.choiseCellNumber === 1) {
        this.columnData[index][`${item}IsChecked`] = true
        this.isOpenSlide = true
        this.choiseCellNumber = 2
        this.nowCheckedArr.push(`${item}`)
        this.firstClickRowIndex = index//  记录第一次点击的行
        this.firstClickColindex = item //  记录第一次点击的列
      } else {
        this.lastClickRowIndex = index // 记录最后一次点击的行
        this.lastClickColindex = item // 记录最后一次点击的列
        if (this.firstClickRowIndex === index) { // 判断第二次点击是否点击的还是同行
          this.columnData[index][`${item}IsChecked`] = true
          this.handleToScreen()
          console.log(this.nowCheckedArr)
          if (this.firstClickColindex === item) { // 判断第二次点击的是否是同一个单元格
            // 处理两次点击同行同一单元格事件
            console.log(item)
            this.handleToResetAttr('controlClick') // 重置全局controlClick
            this.columnData[index][`${item}IsBorder`] = true // 若点击的是同一个则其高亮
            this.columnData[index][`${item}controlClick`] = true
            // 记录当前高亮坐标
            this.nowCheckedCell = []
            this.nowCheckedCell[0] = index
            this.nowCheckedCell[1] = item
            console.log(this.columnData)
            this.isOpenSlide = false
            this.nowCheckedArr = []
            console.log(this.nowCheckedArr)
          } else {
            console.log(this.nowCheckedArr)
            // 处理两次点击同行不同单元格事件
            let flag = this.handleToSameRowDifferentCell(index, item)
            if (!flag) return
            this.isOpenSlide = false
          }
        } else { // 若第二次点击的是不同行的单元格
          let flag = this.handleToDiffRowDiffCell(index, item)
          if (!flag) return
          this.isOpenSlide = false
        }
        // 处理全部数据  构造自定义布局保存数据
        this.handleToSaveData()
        this.choiseCellNumber = 1
      }
      console.log(this.nowCheckedArr)
      // 当前已选单元格数量
    },
    // 筛选出当前表格数据项选中的项
    handleToScreen () {
      this.nowCheckedArr = []
      this.columnData.forEach((itemC, indexC) => {
        let isCheckedArr = this.arrFour.filter((itm, ind) => {
          console.log(itemC[`${itm}IsChecked`])
          return itemC[`${itm}IsChecked`]
        })
        console.log(isCheckedArr)
        this.nowCheckedArr = this.nowCheckedArr.concat(isCheckedArr)
        console.log(this.nowCheckedArr)
      })
    },
    // 处理点击同行不同单元格函数
    handleToSameRowDifferentCell (index, item) {
      // 判断是否有效
      let { startRow, startCol, lastRow, lastCol, firstClickColindex } = this.handleToScreenPoints(index, item)
      let isEffective = this.handleToJudgeIsEffective(startRow, startCol, lastRow, lastCol)
      if (!isEffective) {
        this.$message.error({
          message: '请选择有效的正方形或长方形区域',
          showClose: true,
          duration: 1000
        })
        return false
      }
      // let firstClickColindex = Number(this.firstClickColindex)
      let NumItem = Number(item)
      let startingPoint = null // 起点
      if (firstClickColindex > NumItem) {
        startingPoint = NumItem
      } else {
        startingPoint = firstClickColindex
      }
      console.log(index, item, this.firstClickColindex)
      this.columnData[index][`${startingPoint}Col`] = (Math.abs(NumItem - firstClickColindex) + 1) // 第一次点击的单元格合并的列数
      this.handleToResetAttr('controlClick') // 重置全局controlClick
      this.columnData[index][`${startingPoint}IsBorder`] = true // 第一次点击的单元格高亮
      this.columnData[index][`${startingPoint}controlClick`] = true
      // 记录当前高亮坐标
      this.nowCheckedCell = []
      this.nowCheckedCell[0] = index
      this.nowCheckedCell[1] = startingPoint
      console.log((NumItem - firstClickColindex))
      let total = Math.abs(NumItem - firstClickColindex)
      let i = 1
      console.log(NumItem, firstClickColindex, i, total)
      for (i; i <= total; i++) { // 将第一次点的单元格和最后一次点的单元格之间的单元格全部隐藏
        console.log(i)
        startingPoint++
        console.log(startingPoint)
        this.columnData[index][`${startingPoint}Dis`] = true
      }
      // 第二次点击完重置
      this.nowCheckedArr = []
      console.log(this.columnData)
      return true
    },
    // 处理点击不同行不同单元格函数
    handleToDiffRowDiffCell (index, item) {
      console.log(this.firstClickRowIndex, this.firstClickColindex, index, item)
      let { startRow, startCol, lastRow, lastCol } = this.handleToScreenPoints(index, item)
      console.log(startRow, startCol, lastRow, lastCol)
      // 判断区域是否有效
      let isEffective = this.handleToJudgeIsEffective(startRow, startCol, lastRow, lastCol)
      if (!isEffective) {
        this.$message.error({
          message: '只能选择正方形或长方形区域',
          showClose: true,
          duration: 1000
        })
        return false
      }
      this.handleToResetAttr('controlClick') // 重置全局controlClick
      this.columnData[startRow][`${startCol}IsBorder`] = true // 起点单元格高亮
      this.columnData[startRow][`${startCol}controlClick`] = true
      // 记录当前高亮坐标
      this.nowCheckedCell = []
      this.nowCheckedCell[0] = startRow
      this.nowCheckedCell[1] = startCol
      this.columnData[startRow][`${startCol}IsChecked`] = true // 起始单元格高亮
      let startRowCopy = JSON.parse(JSON.stringify(startRow)) // 起始行原始保存
      let lastRowCopy = JSON.parse(JSON.stringify(lastRow)) // 起始行最后点行原始保存
      let startColCopy = JSON.parse(JSON.stringify(startCol)) // 其实列保存
      /*
      *循环处理每行合并列
      */
      let mergeRowNum = lastRow - startRow + 1 // 合并行数
      let mergeRowi = 0
      let colTotal = lastCol - startCol + 1 // 合并列数
      this.columnData[startRow][`${startCol}Col`] = colTotal
      console.log(mergeRowi, mergeRowNum, colTotal)
      let timesRowStart = JSON.parse(JSON.stringify(startRow))
      let coli = null
      console.log(this.columnData)
      for (mergeRowi; mergeRowi < mergeRowNum; mergeRowi++) { // 行循环
        if (timesRowStart === startRow) {
          coli = 1
        } else {
          coli = 0
        }
        console.log(this.columnData)
        let timesColStart = JSON.parse(JSON.stringify(startCol))
        console.log(timesRowStart, colTotal)
        for (coli; coli <= (colTotal - 1); coli++) { // 列循环
          console.log(coli)
          if (timesRowStart === startRow) {
            timesColStart++
            console.log(timesRowStart, timesColStart)
            this.columnData[timesRowStart][`${timesColStart}Dis`] = true
          } else {
            console.log(timesRowStart, timesColStart, coli, colTotal)
            this.columnData[timesRowStart][`${timesColStart}Dis`] = true
            timesColStart++
          }
        }
        timesRowStart++
      }
      console.log(this.columnData)
      // 合并行
      console.log(startRowCopy)
      this.columnData[startRowCopy][`${startColCopy}Row`] = (lastRowCopy - startRowCopy + 1) // 合并行数
      // 第二次点击完重置
      this.nowCheckedArr = []
      return true
    },
    // 判断第二次点击是否是有效位置
    handleToJudgeIsEffective (startRow, startCol, lastRow, lastCol) {
      console.log(this.columnData)
      let flag = true // 是否有效
      // 判断是所选区域内是否有已经隐藏的模块
      for (startRow; startRow <= lastRow; startRow++) { // 循环行
        for (startCol; startCol <= lastCol; startCol++) { // 列循环
          if (this.columnData[startRow][`${startCol}Dis`]) { // 若区域中间有隐藏的单元格则说明不合法
            flag = false
            console.log(111)
            this.columnData[this.lastClickRowIndex][`${this.lastClickColindex}IsChecked`] = false
          }
          if (this.columnData[startRow][`${startCol}IsBorder`]) {
            flag = false
          }
        }
      }
      if (this.columnData[this.lastClickRowIndex][`${this.lastClickColindex}IsBorder`]) { // 如果第二次点中的单元格高亮则恢复其背景色
        this.columnData[this.lastClickRowIndex][`${this.lastClickColindex}IsChecked`] = true
      }
      console.log(flag)
      return flag
    },
    // 鼠标移入
    enter (row, col) {
      if (this.isOpenSlide) {
        this.handleToHoverEnter(row, col)
      }
      // 控制删除icon显示隐藏的其中一个flag
      this.columnData[row][`${col}controlDelIconDis`] = true
    },
    // 鼠标移出  滑出事件只服务于删除icon的显示隐藏
    leave (row, col) {
      // 控制删除icon显示隐藏的其中一个flag
      this.columnData[row][`${col}controlDelIconDis`] = false
    },
    // 筛选出所选区域左上点和右下点坐标
    handleToScreenPoints (index, item) {
      let firstClickRowIndex = Number(this.firstClickRowIndex)
      let firstClickColindex = Number(this.firstClickColindex)
      let startRow = null // 起点行
      let startCol = null // 起点列
      let lastRow = null // 终点行
      let lastCol = null // 终点列
      if (firstClickRowIndex > index) {
        startRow = index
        lastRow = firstClickRowIndex
      } else {
        startRow = firstClickRowIndex
        lastRow = index
      }
      if (firstClickColindex > item) {
        startCol = item
        lastCol = firstClickColindex
      } else {
        startCol = firstClickColindex
        lastCol = item
      }
      return {
        startRow,
        startCol,
        lastRow,
        lastCol,
        firstClickColindex,
        firstClickRowIndex
      }
    },
    // 处理第一次点中单元格后鼠标移入单元格事件
    handleToHoverEnter (index, item) {
      this.handleToResetAttr('IsSlide') // 重置滑动flag
      console.log(index, item)
      let { startRow, startCol, lastRow, lastCol } = this.handleToScreenPoints(index, item)
      console.log(startRow, startCol, lastRow, lastCol)
      for (startRow; startRow <= lastRow; startRow++) { // 循环行
        console.log(startRow)
        let startColCopy = JSON.parse(JSON.stringify(startCol))
        console.log(startColCopy, lastCol)
        for (startColCopy; startColCopy <= lastCol; startColCopy++) { // 列循环
          console.log(startRow, startCol)
          this.columnData[startRow][`${startColCopy}IsSlide`] = true
        }
      }
    },
    // 处理第一次点中单元格后鼠标移出单元格事件
    handleToHoverLeave (index, item) {
      let { startRow, startCol, lastRow, lastCol } = this.handleToScreenPoints(index, item)
      if ((index > lastRow) || (index < startRow) || (index < startCol) || (index > lastCol)) {
        this.columnData[index][`${item}IsSlide`] = false
      }
    },
    // 重置全局表格数据某个属性
    handleToResetAttr (attr) {
      this.columnData.forEach((item, index) => {
        this.arrFour.forEach((itemC, indexC) => {
          item[`${itemC}${attr}`] = false
        })
      })
    },
    // 判断首次点击的是不是已经高亮的模块
    handleToJudgeHighet (row, col) {
      if (this.columnData[row][`${col}IsBorder`]) {
        this.handleToResetAttr('controlClick') // 重置全局controlClick
        this.columnData[row][`${col}controlClick`] = true //  激活当前点击的单元格的controlClick
        this.nowCheckedCell = []
        this.nowCheckedCell[0] = row
        this.nowCheckedCell[1] = col
        return false
      } else {
        return true
      }
    },
    // 删除已选中并合并的单元格 即重置合并单元格
    handleToDelCell (row, col) {
      console.log(row, col)
      let mergeRowNum = this.columnData[row][`${col}Row`]
      let mergeColNum = this.columnData[row][`${col}Col`]
      this.columnData[row][`${col}IsChecked`] = false
      this.columnData[row][`${col}IsBorder`] = false
      this.columnData[row][`${col}controlClick`] = true
      this.columnData[row][`${col}IsSlide`] = false
      this.columnData[row][`${col}controlDelIconDis`] = false
      console.log(mergeRowNum, mergeColNum)
      this.handleToResetMergeCell(mergeRowNum, mergeColNum, row, col)
    },
    // 重置合并单元格
    handleToResetMergeCell (mergeRowNum, mergeColNum, startRow, startCol) {
      // 重置行、列合并数
      this.columnData[startRow][`${startCol}Row`] = '1'
      this.columnData[startRow][`${startCol}Col`] = '1'
      // 重置图片
      this.columnData[startRow][`${startCol}img_url`] = ''
      // 重置跳转链接
      this.columnData[startRow][`${startCol}jump_link`] = ''
      /*
     *循环重置每行隐藏列
     */
      let mergeRowi = 0
      let timesRowStart = JSON.parse(JSON.stringify(startRow))
      let coli = null
      for (mergeRowi; mergeRowi < mergeRowNum; mergeRowi++) { // 循环每行
        if (timesRowStart === startRow) {
          coli = 1
        } else {
          coli = 0
        }
        let timesColStart = JSON.parse(JSON.stringify(startCol))
        // 列循环

        for (coli; coli <= (mergeColNum - 1); coli++) {
          if (timesRowStart === startRow) {
            timesColStart++
            this.columnData[timesRowStart][`${timesColStart}Dis`] = false
            this.columnData[timesRowStart][`${timesColStart}IsChecked`] = false
            this.columnData[timesRowStart][`${timesColStart}IsSlide`] = false
          } else {
            this.columnData[timesRowStart][`${timesColStart}Dis`] = false
            this.columnData[timesRowStart][`${timesColStart}IsChecked`] = false
            this.columnData[timesRowStart][`${timesColStart}IsSlide`] = false
            timesColStart++
          }
        }
        timesRowStart++
      }
      // 构造自定义布局保存数据
      this.handleToSaveData()
    },
    // 处理全部数据  构造自定义布局保存数据
    handleToSaveData () {
      console.log(this.columnData)
      // this.arrFour
      let obj = {}
      let blockNum = 0 // block计数
      let isAllCheckFull = true // 是否全部填满
      this.columnData.forEach((item, index) => {
        this.arrFour.forEach((itemC, indexC) => {
          console.log(this.columnData[index], itemC)
          if (this.columnData[index][`${itemC}IsBorder`]) {
            obj[`block_${blockNum}`] = {
              'name': `block_${blockNum}`,
              'x': this.columnData[index][`${itemC}x`],
              'y': this.columnData[index][`${itemC}y`],
              'rows': this.columnData[index][`${itemC}Row`],
              'cols': this.columnData[index][`${itemC}Col`],
              'img_url': this.columnData[index][`${itemC}img_url`],
              'jump_link': this.columnData[index][`${itemC}jump_link`]
            }
            blockNum++
          } else if (!this.columnData[index][`${itemC}Dis`]) {
            console.log(this.columnData[index][`${itemC}x`], this.columnData[index][`${itemC}y`])
            isAllCheckFull = false // 表格未填满则为flag
          }
        })
      })
      Object.keys(obj).forEach((item, index) => {
        console.log(obj[item].img_url)
        if (!obj[item].img_url) {
          isAllCheckFull = false
        }
      })
      console.log(obj, isAllCheckFull)
      this.$emit('handleToGetTabelData', { obj, isAllCheckFull }) // 传递整体数据
    },
    // 处理回显
    handleToEchoDisplay () {
      console.log(this.customModulesBackData)
      // 将具体数据回显到对应的单元格中
      Object.keys(this.customModulesBackData).forEach((item, index) => {
        console.log(this.customModulesBackData[item])
        console.log((this.customModulesBackData[item].x - 1), (this.customModulesBackData[item].y - 1))
        this.columnData[(this.customModulesBackData[item].x - 1)][`${(this.customModulesBackData[item].y - 1)}Dis`] = false
        this.columnData[(this.customModulesBackData[item].x - 1)][`${(this.customModulesBackData[item].y - 1)}IsBorder`] = true
        this.columnData[(this.customModulesBackData[item].x - 1)][`${(this.customModulesBackData[item].y - 1)}Row`] = this.customModulesBackData[item].rows
        this.columnData[(this.customModulesBackData[item].x - 1)][`${(this.customModulesBackData[item].y - 1)}Col`] = this.customModulesBackData[item].cols
        this.columnData[(this.customModulesBackData[item].x - 1)][`${(this.customModulesBackData[item].y - 1)}img_url`] = this.customModulesBackData[item].img_url
      })
      this.handleToEchoDisplayMerge(this.customModulesBackData)
    },
    handleToEchoDisplayMerge (initData) {
      Object.keys(initData).forEach((item, index) => {
        let mergeRowNum = initData[item].rows // 合并行数

        let mergeRowi = 0
        let colTotal = initData[item].cols // 合并列数
        let timesRowStart = JSON.parse(JSON.stringify((initData[item].x - 1)))
        let coli = null
        for (mergeRowi; mergeRowi < mergeRowNum; mergeRowi++) { // 行循环
          if (timesRowStart === (initData[item].x - 1)) {
            coli = 1
          } else {
            coli = 0
          }
          console.log(this.columnData)
          let timesColStart = JSON.parse(JSON.stringify((initData[item].y - 1)))
          console.log(timesRowStart, colTotal)
          for (coli; coli <= (colTotal - 1); coli++) { // 列循环
            console.log(coli)
            if (timesRowStart === (initData[item].x - 1)) {
              timesColStart++
              console.log(timesRowStart, timesColStart)
              this.columnData[timesRowStart][`${timesColStart}Dis`] = true
            } else {
              console.log(this.columnData, timesRowStart, timesColStart, coli, colTotal)
              this.columnData[timesRowStart][`${timesColStart}Dis`] = true
              timesColStart++
            }
          }
          timesRowStart++
        }
      })
      console.log(this.columnData)
    }
  }
}
</script>
<style lang="scss" scoped>
.layoutTableMain {
  border: none !important;
  background-color: #f8f8f8 !important;
  .gridtable {
    border-collapse: separate;
    table-layout: fixed;
    border: 1px solid #dddddd;
    tr {
      // height: 75px;
      td {
        cursor: pointer;
        background-color: #fff;
        border: 1px solid #dddddd;
        padding-bottom: none;
        vertical-align: middle !important;
        position: relative;
        div {
          width: 100%;
          height: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
          span {
            display: inline-block;
            word-break: break-all;
            i {
              width: 15px;
              height: 15px;
              display: block;
              position: absolute;
              right: -7px;
              top: -8px;
              background-size: 100%;
              z-index: 99;
            }
          }
        }
      }
    }
  }
}
</style>
