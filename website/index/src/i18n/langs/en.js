// en.js
import enLocale from 'element-ui/lib/locale/lang/en'
// 官网
// 申请试用国际化
import {
  en as indexApply
} from './views/index/en/applyEn'
// 官网首页移动端国际化
import {
  en as indexMobile
} from './views/index/en/indexMobileEn'
const en = {
  messages: {
    lang: 'English',
    index_nav_main: 'home page',
    index_nav_new: 'News and Information',
    index_nav_forum: 'Community Forum',
    index_nav_link: 'About us',
    index_nav_apply: 'Apply for probation',
    index_nav_test: 'Test 1',
    index_application_title_1: 'Application of diversified marketing functions',
    index_application_content_1: 'Rich marketing tools, diversified marketing, to help enjoy 100 billion-grade flow',
    index_application_title_2: 'reservation service',
    index_application_content_2: 'Various booking modes can help you improve the customer experience and solve the problems of difficult and long waiting time automatically',
    index_application_title_3: 'Bargaining Activities',
    index_application_content_3: 'High diffusion, low price to benefit consumers, so as to publicize the brand, improve popularity, a large number of drainage, improve conversion rate',
    index_application_title_4: 'Distribution model',
    index_application_content_4: 'One-click sharing, invite subordinates, into a single commission. Massive User Forwarding and Drainage Traffic to Enhance Shop Exposure',
    index_application_title_5: 'Collaboration activities',
    index_application_content_5: 'Low price, limited time, multi-person group purchase, fast order, mass sharing. Help you accumulate membership, promote sales volume, make business easier',
    index_advantage_top_title: 'Core Functional Advantages of Small Programs',
    index_advantage_top_content: 'Numerous core functions, one key application, advance layout of Weixin new ecology, seize the dividend',
    index_advantage_title_1: 'Marketing activities',
    index_advantage_content_1: 'Split Coupons: Order to get coupons, share to groups, many people to get preferential treatment',
    index_advantage_content_2: 'Form Statistics: Questionnaire Survey, Simple Reservation Information Collection, Getting Customer Preferences',
    index_advantage_content_3: 'Full Discount: Stimulating Consumption, Promoting Consumption and Promoting Sales',
    index_advantage_content_4: 'Message template: Customize active message template to send widget message information to selected customers',
    index_advantage_content_5: 'Include: group, bargaining, second killing, distribution, lottery, coupons, points mall, activities',
    index_advantage_content_6: 'Rich marketing activities such as courtesy, payment courtesy, etc',
    index_advantage_title_2: 'Immediate use',
    index_advantage_2_title_1: 'Membership management',
    index_advantage_2_content_1: 'Membership grade, membership points, membership balance, membership card, limit card (the number of times required card)',
    index_advantage_2_content_2: 'Provide members with accurate marketing activities',
    index_advantage_2_content_3: 'Online and offline membership, full-time and full-time continuous service for users',
    index_advantage_2_title_2: 'Immediate use',
    index_advantage_3_title_1: 'Store management',
    index_advantage_3_content_1: 'Supporting multi-stores chain operation and increasing the exposure rate of stores',
    index_advantage_3_content_2: 'Segmentation of customer acquisition and clerk distribution in stores to reduce the cost of customer acquisition',
    index_advantage_3_content_3: 'Store reservation service is set separately to improve customer experience and reduce management costs',
    index_advantage_3_title_2: 'Immediate use',
    index_advantage_4_title_1: 'Formwork decoration',
    index_advantage_4_content_1: 'Various industry templates can be selected and used on demand, and the module can be dragged to complete the decoration',
    index_advantage_4_content_2: 'Navigation menu is set flexibly. No code editing is required',
    index_advantage_4_content_3: 'Photo Advertising, Membership Card, Coupon, Map Location Panel, One-click Add Display',
    index_advantage_4_title_2: 'Immediate use',
    index_advantage_5_title_1: 'data statistics',
    index_advantage_5_content_1: 'Comprehensive Statistics of Sales, Flow and Conversion Rate',
    index_advantage_5_content_2: 'Traffic page source, accurate statistics',
    index_advantage_5_content_3: 'User Portrait Image Display, Provide the Basis for Your Precision Analysis',
    index_advantage_5_title_2: 'Immediate use',
    index_advantage_6_title_1: 'Online Trading',
    index_advantage_6_content_1: 'Commodity Display, Online Ordering, Online Wechat Payment, Balance Payment, Points Cash',
    index_advantage_6_content_2: 'Complete online trading process; one-click access to stores and online stores, reduce operating costs, enhance user experience',
    index_advantage_6_title_2: 'Immediate use'
  },
  industry: {
    title_top: 'Covering multiple industries',
    content: 'More vertical industry solutions to meet a wide range of business needs and serve the public'
  },
  entrance: {
    title: 'Ten Flow Entrances for Small Programs',
    content: 'Numerous traffic entrances increase the stickiness of consumers and brands and share the whole ecosystem of Wechat'
  },
  case: {
    title: 'Industry case',
    content: 'Drive Products with Creativity, Win-win Business Opportunities and Work Together'
  },
  information: {
    title: 'News and Information',
    content: 'Insight into the new trends of the industry and grasp the new direction of the industry',
    more: 'See more'
  },
  // 底部footer组件内数据
  footer: {
    title: 'Beijing Handheld Pioneer Network Technology Co., Ltd',
    link: 'Contact us',
    server: '7*24-hour service hotline',
    adress: 'Company address',
    detail_adress: 'Fifth Floor of Yitai Building, 54 Xizhimen North Street, Haidian District, Beijing',
    content: 'Pay attention to the selection of micro-shop treasure, understand the dynamics of small programs',
    footer_con_1: 'Friendship Links',
    footer_con_2: 'Handheld First Opportunity',
    footer_con_3: 'B2C Mall',
    footer_con_4: 'B2B2C Mall',
    footer_con_5: 'POS Store System',
    footer_con_6: 'Free microshop',
    footer_con_7: 'Cloud computing data',
    footer_con_8: 'Tianjin Zhangshang Xianji Network Technology Co., Ltd',
    footer_con_9: 'Copyright',
    footer_con_10: 'Beijing ICP No. 14046261'
  },
  // 申请试用页面数据
  apply: {
    title: 'Free trial application',
    name: 'name',
    submit: 'Submission',
    mobile: 'Tel',
    content: 'Provide enterprise scale, multi-system new retail solutions',
    content_bottom: 'Multi-system seamless docking',
    placeholder_name: 'Please fill in your name',
    placeholder_tel: 'Please fill in your cell phone number'
  },
  // 登录页面数据
  login_reg: {
    login: 'Sign in',
    rej: 'register'
  },
  login_page: {
    login_main: 'Sign in',
    login_f: 'Subaccount login',
    main_name: 'Main account number',
    password: 'Password',
    z_phone: 'Subaccount username/cell phone number',
    subAccount: 'Sub account'
  },
  Recommend: 'Recommend',
  aboutUs: {
    title: 'Company Profile',
    tiliang: 'Service Store Volume',
    zengzhang: 'Customer growth rate for five consecutive years',
    kehu: 'Crown and above customers',
    xufei: 'Customer renewal rate',
    renzheng: 'International certification',
    p_1: 'Beijing Handheld Pioneer Network Technology Co., Ltd., a retail cloud service provider, based on cloud computing SaaS service mode, helps retail enterprises to upgrade their digital and intelligent management, and achieves the road of large-scale development.',
    p_2: "In 2012, Wangdian Tong was founded in Beijing. After that, it set up branches in 25 provinces and municipalities, including Tianjin, Shanghai, Guangzhou, Hangzhou and Yiwu, with nearly 1,000 employees. Its service scope radiates to the whole country and extends overseas. With technological innovation, product innovation, service innovation and market innovation, Wangdian Tong has achieved more than 100% annual customer growth; 100,000 + customers cover the top 500 companies in the world, such as COFCO, Johnson & Johnson, 3M, Budweiser, Zhou Heiya, MG elephant, water code, listed companies, well-known brands, TOP merchants, etc. The total volume of transactions in 2017 is nearly trillion. The advantages of large-scale customers and head customers give Wangdian Tong a higher and more complete industry perspective, market sensitivity and strong execution. Wangdian Tong's founding team is from the technical management of Internet listed companies.The product and R& D team account for more than 35%.The core members are 985 graduate students from Tsinghua University, Peking University and Beiyu University.Wangdian Tong has won awards in ACM, Mathematical Modeling and other heavyweight competitions, and has tens of millions of user operating experience.Over the years, Wangdian Tong has built internal and external resource pools such as 'Internet, e-commerce, management' and 'technology flow' industry.",
    p_3: "Strong technical foundation has enabled Wangdian Tong to pass CMMI3 international certification, ISO27001 information security certification, national high-tech enterprise certification, double soft certification and other certification, and won many honors: China's top 50 e-commerce service providers, China's top 10 SaaS service providers in the field of industrial innovation, e-commerce Olympics. Scarlett Golden Mac Award for Best Technical Service, China International E-Commerce Expo Award for Best E-Commerce Service Enterprise, Alibaba CCO AG Best Enabling Partner Award, Taobao Gold Medal Takeout Partner, Jingdong 'Beijing Excellence' Award, Suning Easy-to-buy 'Gold Medal Easy Partner' and so on, have been with Tianmao, Taobao, Jingdong and other 80 + mainstream. E-commerce, takeaway and other platforms have established strategic cooperative relations.",
    p_4: 'In the future, Wangdian Tong will continue to adhere to the development concept of people-oriented, customer-oriented, continuous innovation and win-win industrial development, and build the "ERP+" integrated retail enterprise service ecosystem.'
  },
  development: {
    title: 'development history',
    p_1_1: "In 2017, Wangdian Tong's flagship edition was launched, creating the SaaS era in the field of retail information services",
    p_1_2: 'In 2017, the small program SaaS platform was officially launched, and its services covered many industries.',
    p_1_3: 'In 2017, more than 800 employees and 25 branches were set up to serve the whole country and overseas.',
    p_1_4: 'In 2017, customers grew by more than 100% for five consecutive years. The total number of Shuang11 orders was 289 million, and the total volume of transactions was 40.2 billion, achieving five consecutive increases.',
    p_2_1: 'Over 100,000 online and offline stores in October 2016',
    p_2_2: 'In November 2016, the total transaction volume of Shuang11 customers was 23.77 billion yuan and the order volume was 168 million yuan.',
    p_2_3: '2016 E Fast Band, Wangdian Tong WMS, POS store management system on-line, service coverage of large, medium and small businesses',
    p_2_4: 'In 2016, Taobao was awarded the title of Gold Medal Taopai Partner in Taobao Service Market',
    p_3_1: 'In 2015, China Food and Agriculture Corporation, Johnson & Johnson, I miss you very much, Lai Yifen, Jun Lebao, Xiao Yiyi, October Mami, RIO Ruiao, Tongrentang, Jianqian and other top 500, listed companies, well-known brands and other heavyweight customers have reached cooperation.',
    p_3_2: 'In November 2015, the total transaction volume of Shuang11 customers exceeded 10 billion yuan, with a maximum order volume of 970,000 orders per customer.',
    p_3_3: 'In 2015, 15 branches were set up to serve all over the country.',
    p_4_1: 'In 2014, Wangdian Tong ERP Enterprise Edition, Micro Mall, B2B2C Mall and other products were launched, and began to lay out the service areas of e-commerce enterprises.',
    p_4_2: 'In 2014, Wangdian Tong and Zhou Hei Duck reached strategic cooperation to create a full-channel solution for orders.',
    p_4_3: 'In 2014, he was awarded the title of "Top 100 Enterprises of E-Commerce in China" by China Electronic Commerce Association.',
    p_5_1: 'In July 2013, the first hardware crown merchant in the whole network - Doudouyun signed Wangdian Tong',
    p_5_2: 'In 2013, Wangdian won many awards, such as Best E-commerce ERP Emerging Service Provider, Tomorrow Star of E-commerce Service, etc.',
    p_5_3: 'In 2013, more than 100 members of the enterprise set up branches in Shanghai, Guangzhou and Hangzhou.',
    p_6_1: 'SaaS Products Wangdian Tong ERP Professional Edition launched in November 2012',
    p_6_2: 'Beijing Handheld Pioneer Network Technology Co., Ltd. was formally established in Beijing in 2012.'
  },
  environment: 'Office environment',
  contact: {
    title: 'Contact us',
    adress: 'Fifth Floor of Yitai Building, 54 Xizhimen North Street, Haidian District, Beijing'
  },
  contact_right: {
    service: 'Customer Service Telephone: 400-010-1039',
    s_title: 'customer service telephone numbers',
    QQ: 'Consultation QQ: 3003715029',
    Q_title: 'QQ Consultation'
  },
  ...enLocale,
  indexApply, // 申请试用
  indexMobile //  官网首页移动端国际化
}
export default en
