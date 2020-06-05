<template>
	<button class="mini-btn" size="mini" :style="{backgroundColor: modelColor}">{{ modelLabel }}</button>
</template>

<script>
	export default {
		name: 'modelLabel',
		data() {
			return {
			}
		},
		components: {
		},
		props: {
			modelLabel: String
		},
		computed: {
			modelColor() {
				let result
				switch (this.modelLabel){
					case '维修工单':
						result = '#09a0f7'
						break;
					case '安装工单':
						result = '#f28f79'
						break;
					case '保养工单':
						result = '#97f079'
						break;
					case '远程服务':
						result = '#aa55ff'
						break;
					case '回访工单':
						result = '#f37ae5'
						break;
					default:
						result = ''
						break;
				}
				return result
			}
		},
		methods: {
		}
	}
</script>

<style scoped>
	.mini-btn {
		position: absolute;
		top: 15upx;
		right: 10upx;
		color: #FFFFFF;
	}
</style>
