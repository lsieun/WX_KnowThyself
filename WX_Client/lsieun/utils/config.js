'use strict';
const env = 'dev';// dev production

/*
 * 默认接口出错弹窗文案
 * @type {string}
 */
const defaultAlertMessage = '好像哪里出了小问题~ 请再试一次~';

/*
 * 默认分享文案
 * @type {{en: string}}
 */
const defaultShareText = {
  en: 'Know thyself, Nothing in excess (认识自己，万事有度)'
};

/*
 * 小程序默认标题栏文字
 * @type {string}
 */
const defaultBarTitle = {
  en: 'Know Thyself'
};

/*
 * 文章默认图片
 * @type {string}
 */
const defaultImg = {
  articleImg: '',
  coverImg: '',
  portraitImg: '/images/portrait_128x128.png'
};

let core = {
  env: env,
  defaultBarTitle: defaultBarTitle['en'],
  defaultImg: defaultImg,
  defaultAlertMsg: defaultAlertMessage,
  defaultShareText: defaultShareText['en'],
  isDev: env === 'dev',
  isProduction: env === 'production'
};

export default core;
