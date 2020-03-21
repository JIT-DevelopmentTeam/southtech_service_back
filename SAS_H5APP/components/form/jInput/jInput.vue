<template>
	<view>
		<input :value="value" :type="type" :password="password" :placeholder="placeholder" :disabled="disabled" :style="[classStyle]" />
	</view>
</template>

<script>
	export default{
		name:"jInput",
		data() {
			return {
			}
		},
		props:{
			value: {
				type: String,
				default: ''
			},
			type: {
				type: String,
				default: 'text'
			},
			password: {
				type: Boolean,
				default: false
			},
			placeholder: {
				type: String,
				default: ''
			},
			disabled: {
				type: Boolean,
				default: false
			},
			classStyle: {
				type: Object
			}
		}
	}
</script>

<style scoped>
</style>
