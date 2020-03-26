<template>
	<span class="iconfont iconphone phone" @click.stop="call(phoneNum)"></span>
</template>

<script>
	export default {
		name: 'phone',
		data() {
			return {
			}
		},
		components: {
		},
		props: {
			phoneNum: String
		},
		computed: {
		},
		methods: {
			call(phoneNum) {
				uni.makePhoneCall({
				    phoneNumber: phoneNum //仅为示例
				});
			}
		}
	}
</script>

<style>
.phone {
	font-size: 70upx;
	position: absolute;
	top: 45px;
	right: 50upx;
}
</style>
