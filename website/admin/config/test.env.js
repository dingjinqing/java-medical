'use strict';
const merge = require('webpack-merge');
const prodEnv = require('./prod.env');

module.exports = merge(prodEnv, {
  NODE_ENV: '"testing"',
  API_DOMAIN: '"medicaltest.weipubao.cn"',
  IMAGE_DOMAIN: '"medicaltestimg.weipubao.cn"'
});
