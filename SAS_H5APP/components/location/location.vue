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
	export default {
		name: 'location',
		data() {
			return {}
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
				let _this = this;
				window.location.href = 'https://uri.amap.com/navigation?to=' + _this.long + ',' + _this.lati + ',' + _this.label + '&callnative=1';
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
