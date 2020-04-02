<template>
  <div class="main-container" v-html="data">

  </div>
</template>

<script>
import { getMiniProgramServices } from '@/api/admin/basicConfiguration/shopConfig'
export default {
  data () {
    return {
      shopId: null,
      data: "<p><span style=\"font-family: 'Microsoft YaHei'; font-size: 10pt;\">下面是有关微铺宝商城购物的服务条款，此条款用于您在使用本商城及服务时所须遵守的相关条件，在您注册成为会员之前请仔细阅读下列条款内容，如有疑问，请您及时与我们联系！</span></p>\n<p><span style=\"font-family: 'Microsoft YaHei'; font-size: 10pt;\">一、会员注册须知：</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">1、微铺宝购物商城注册完全免费；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10pt;\">2、会员注册资料填写：1）会员在注册本站时需要提供真实有效的个人资料和信息，如果会员有意提供错误资料和信息，本站将有权结束该会员资格；2）如果您的个人资料有变动，则需要及时修改会员中心的个人信息；3）注册本站会员将接受本站向您发送有关商城的促销、通知等相关信息。</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">3、会员账号及密码的安全性1）本站将对会员账号及密码保密；2）本站会员如果发现有任何非法盗用会员账号或存在安全漏洞的情况，请立即告知本站；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">如发现以下行为之一则将取消会员资格</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">1、违反本网站注册条款；<img src=\"http://mpdevimg2.weipubao.cn/upload/0/image/20190917/crop_91wQw3W6LfAwBMlA.jpeg\" alt=\"\" /></span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">2、会员有损公司利益的行为；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">3、会员提供虚假个人信息；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">4、通过盗取、诈骗等不正当手段获得其他会员账号的行为；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">5、转让、出售或拍卖会员账号而造成不利影响的行为；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">6、违反中华人民共和国法律、法规及违反互联网相关规定的行为；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">7、违反本站其他相关政策的行为。</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">二、会员的隐私制度微铺宝购物商城有义务对会员的信息等个人隐私进行保护，会员在本站登记的个人资料，将受到本站的保护。</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">1、公司在未经会员同意授权的情况下不会恶意编辑、公开、透露会员个人信息：</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">2、本站不公开、透露个人信息，除以下情况以外：1）本站会员同意授权本站工作人员公开或透露相关信息2）国家相关法律程序要求本站提供会员个人资料3）符合其他相关要求</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">三、交易过程本站会员在进行购物时必须遵守以下条款</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">1、本站所售商品的所有权及风险在产品交付指定收货人时将转移给买方；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">2、本站所售商品享受实体商场同样的质量保证，如果发现货品缺少、配送有误、破损等问题，请您当面退回，并及时与我们联系，逾期将不做受理；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">3、顾客收到货后3天内发现有质量问题，可以与我们联系，进行退换货流程；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">4、本站工作人员发现网站上所显示的产品、订单信息存在错误的情况下，有权单方面下架或撤销订单；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">5、交易过程的其他条款。</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">四、关于知识产权声明微铺宝购物商城提供的所有内容，包括：文字、图片、视频、软件等都属于本公司财产。微铺宝网站相关专利权、商标、著作权以及其他任何所有权均属于本网站所有，收到中华人民共和国《商标法》、《著作权法》和国际版权法等相关法律程序的保护。未经本站同意，任何个人、企业均无权下载、复制、传输、改动，也不允许公开恶意诋毁公司形象，否则将负法律责任。违反以上声明从而给公司造成损失的，本公司将依法追究其法律责任！</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">五、免责声明本网站有义务确保网站的正常运行，尽力避免因突发状况导致的网站无法正常运行或者服务中断，保证顾客能够顺利进行交易活动，但如遇以下情况，本站将无需承担任何责任</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">1、由于会员个人将账户密码告知他人从而导致的个人资料的泄漏；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">2、注册会员提供错误、不完整等信息所造成的任何损失；</span></p>\n<p style=\"font-family: 'Microsoft YaHei';\"><span style=\"font-size: 10px;\">3、任何由于计算机病毒、黑客攻击或政府叫停等不可抗力而造成的网站崩溃、交易服务中断或者相关信息丢失等；</span></p>"
    }
  },
  mounted () {
    console.log(this.$route.query.shop_id)
    this.shopId = this.$route.query.shop_id ? this.$route.query.shop_id : null
    // if (this.shopId) this.requestServices()
  },
  methods: {
    requestServices () {
      getMiniProgramServices({shopId: this.shopId}).then(res => {
        console.log(res)
      })
    }
  }
}
</script>

<style>
  .main-container{
    height: 100%;
    background-color: #fff;
    padding: 0 10px;
  }
</style>
