<template>
	<view class="content">
		<car-box></car-box>
		<sub-section></sub-section>
	</view>
</template>

<script>
	export default{
		name:"mytask",
		data() {
			return {
				
			}
		},
		components: {
			carBox: () => import('@/components/carousel-box/carousel-box.vue'), 
			subSection: () => import('@/components/subsection/subsection.vue')
		},
		onLoad() {
			this.$store.dispatch('dic/GetStatusList', '工单状态').then(res=>{ 
				this.$store.dispatch('dic/GetTypeList', '工单类型').then(res=>{
					this.$store.dispatch('dic/GetFaultLocaList', '故障部位')
				})
			})
		}
	}
</script>

<style scoped>
</style>