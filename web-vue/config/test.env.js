'use strict';
const merge = require('webpack-merge');
const prodEnv = require('./prod.env');

module.exports = merge(prodEnv, {
  NODE_ENV: '"testing"',
  API_DOMAIN: '"jmptest.weipubao.cn"',
  IMAGE_DOMAIN: '"jmptestimg.weipubao.cn"'
});
