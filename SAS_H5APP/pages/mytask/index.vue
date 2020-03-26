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
			this.$store.dispatch('dic/GetStatusList', 'work_order_status').then(res=>{ 
				this.$store.dispatch('dic/GetTypeList', 'work_order_type').then(res=>{
					this.$store.dispatch('dic/GetFaultLocaList', 'work_order_detail_fault_location')
				})
			})
		}
	}
</script>

<style scoped>
</style>