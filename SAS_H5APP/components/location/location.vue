<template>
	<view class="sameLine" @click.stop="toMap">
		<view v-if="left_right === '左'" class="sameLine">
			<span class="iconfont icondingwei"></span>
		</view>
		<view class="sameLine" :style="{'display': labelStyle.display, 'font-size': labelStyle.fontSize}">{{label}}</view>
		<view v-if="left_right === '右'" class="sameLine">
			<span class="iconfont icondingwei"></span>
		</view>
	</view>
</template>

<script>
	import * as dd from 'dingtalk-jsapi'
	import {GetJsapiTicket} from '@/api/ddjsapi.js'
	export default {
		name: 'location',
		data() {
			return {
				timeStamp: '',
				nonceStr: '',
				signature: ''
			}
		},
		components: {
		},
		props: {
			'labelStyle': Object,
			'label': String,
			'left_right': String,
			'long': Number,
			'lati': Number
		},
		computed: {
		},
		methods: {
			toMap() {
				let params = {
					url: this.$url
				}
				GetJsapiTicket(params).then(res => {
					this.timeStamp = res.data.result.timeStamp;
					this.nonceStr = res.data.result.nonceStr;
					this.signature = res.data.result.signature;
					this.getLocation()
				})
			},
			getLocation() {
				var _this = this;
				_this.DDConfig();
				dd.ready(() => {
					dd.biz.map.view({
					    latitude: _this.lati, // 纬度
					    longitude: _this.long, // 经度
					    title: _this.label // 地址/POI名称
					});
				});
			},
			//钉钉config 配置
			DDConfig() {
				var _this = this;
			
				dd.config({
					agentId: _this.$agentId,
					corpId: _this.$corpId,
					timeStamp: _this.timeStamp,
					nonceStr: _this.nonceStr,
					signature: _this.signature,
					jsApiList: ["biz.map.view"]
				});
			}
		}
	}
</script>

<style>
	.sameLine {
		display: inline;
		margin-right: 35upx;
	}
</style>
